package com.veullustigpws.pws.connection.hosting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import com.veullustigpws.pws.app.Debug;
import com.veullustigpws.pws.assignment.ParticipantData;
import com.veullustigpws.pws.assignment.ParticipantWorkState;
import com.veullustigpws.pws.connection.ConnectData;
import com.veullustigpws.pws.connection.Message;
import com.veullustigpws.pws.connection.Protocol;
import com.veullustigpws.pws.utils.JoinCodeGenerator;

public class Server implements Runnable{
	private HostingManager manager;
	
	private ArrayList<ConnectionHandler> connections;
	private ServerSocket socket;
	private ExecutorService threadpool;
	
	private boolean assignmentStarted = false;
	
	public Server(HostingManager manager) {
		this.manager = manager;
		connections = new ArrayList<>();
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		try {
			socket = new ServerSocket(0);
			
			// Gather server information
			InetAddress ip = InetAddress.getLocalHost();
			int port = socket.getLocalPort();
			String code = JoinCodeGenerator.IPToCode(new ConnectData(ip.getHostAddress(), port));
			
			Debug.log("SERVER INFO:");
			Debug.log(" > IP: " + ip.getHostAddress());
			Debug.log(" > Name: " + ip.getHostName());
			Debug.log(" > Port: " + port);
			Debug.log(" > Code: " + code);
			
			
			manager.setServerCode(code);
			
			// Accept all clients
			threadpool = Executors.newCachedThreadPool();
			while (true) {
				Socket client = socket.accept();
				ConnectionHandler handler = new ConnectionHandler(client);
				connections.add(handler);
				Future<?> future = threadpool.submit(handler);
				handler.setFuture(future);
			}
		} catch (IOException e) {
			shutdown();
		}
		
	}
	
	public void startAssignment() {
		assignmentStarted = true;
		broadcast(new Message(Protocol.StartAssignment, manager.getAssignmentOptions()));
	}
	
	
	public void broadcast(Message msg) {
		for (ConnectionHandler ch : connections) {
			ch.sendMessage(msg);
		}
	}
	
	public void shutdown() {
		if (socket.isClosed()) return;
		try {
			socket.close();
			for (ConnectionHandler ch : connections) 
				ch.shutdown();
		} catch (IOException e) {
			// Cannot handle
		}
	}

	
	class ConnectionHandler implements Runnable {
		
		private Future<?> future;
		private boolean alive;
		private Socket client;
		private int ID = -1;
		
		private ObjectInputStream objIn;
		private ObjectOutputStream objOut;
		
		public ConnectionHandler(Socket client) {
			this.client = client;
			alive = true;
			ID = new Random().nextInt(1, Integer.MAX_VALUE);
		}
		@Override
		public void run() {
			try {
				objOut = new ObjectOutputStream(client.getOutputStream());
				objIn = new ObjectInputStream(client.getInputStream());
				
				// Continuously checking for messages
				Message msg;
				while ((msg = (Message) objIn.readObject()) != null && alive) {
					handleInput(msg);
				}
			} catch (IOException | ClassNotFoundException e) {
				shutdown();
			}
			
		}
		
		private void handleInput(Message msg) {
			try {
				switch (msg.getDescription()) {
				case Protocol.SendPassword:
					managePassword((String) msg.getContent());
					break;
				case Protocol.SendParticipantData:
					ParticipantData pd = (ParticipantData) msg.getContent();
					pd.setID(ID);
					manager.participantEntered(pd);
					
					// Start their session if the assignment had already started
					if (assignmentStarted) {
						manager.updateRunningTime();
						sendMessage(new Message(Protocol.StartAssignment, manager.getAssignmentOptions()));
					}
					break;
				case Protocol.ParticipantLeaves:
					manager.participantLeft(ID);
					breakConnection();
					break;
				case Protocol.SendRequestedWork:
					ParticipantWorkState pws = (ParticipantWorkState) msg.getContent();
					manager.receivedRequestedWork(pws, ID);
					
					break;
				default: 
					Debug.error("Unable to read protocol of server input.");
				}
			} catch (Exception e) {
				Debug.error("Unable to read protocol of server input.");
			}
			
		}
		
		private void managePassword(String password) {
			if (manager.getAssignmentOptions().getPassword().equals(password)) {
				sendMessage(Protocol.CorrectPassword);
			} else {
				sendMessage(Protocol.IncorrectPassword);
				Debug.log("User tried to enter with incorrect password.");
				breakConnection();
			}
		}
		
		public void sendMessage(Message msg) {
			try {
				objOut.writeObject(msg);
				objOut.reset();
			} catch (IOException e) {
				Debug.error("Unable to send message.");
			}
		}
		public void sendMessage(String msg) {
			try {
				objOut.writeObject(new Message(msg));
				objOut.reset();
			} catch (IOException e) {
				Debug.error("Unable to send message.");
			}
		}
		
		private void breakConnection() {
			connections.remove(this);
			alive = false;
			shutdown();
		}
		
		public void shutdown() {
			try {
				future.cancel(true);
				objIn.close();
				objOut.close();
				
				if (client.isClosed()) return;
				client.close();
			} catch (IOException e) {
				// ignore 
			}
		}
		
		void setFuture(Future<?> future) {
			this.future = future;
		}
	}
	
}
