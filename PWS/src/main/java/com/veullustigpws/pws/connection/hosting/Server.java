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
import com.veullustigpws.pws.assignment.data.ParticipantData;
import com.veullustigpws.pws.assignment.data.ParticipantWorkState;
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
			shutdown("An unexpected error occured on the server.");
		}
		
	}
	
	public void startAssignment() {
		assignmentStarted = true;
		broadcast(new Message(Protocol.StartAssignment, manager.getAssignmentOptions()));
	}

	public void sendMessageToClient(int ID, Message msg) {
		for (ConnectionHandler ch : connections) {
			if (ch.ID != ID) continue;
			ch.sendMessage(msg);
		}
	}
	
	public void broadcast(Message msg) {
		for (ConnectionHandler ch : connections) {
			ch.sendMessage(msg);
		}
	}
	public void broadcast(String msg) {
		for (ConnectionHandler ch : connections) {
			ch.sendMessage(msg);
		}
	}
	
	public void kickUser(int ID, String reason) {
		for (ConnectionHandler ch : connections) {
			if (ch.getID() != ID) continue;
			ch.sendMessage(new Message(Protocol.KickUser, reason));
			ch.shutdown();
			
			connections.remove(ch);
			return;
		}
	}
	
	public void shutdown(String reason) {
		if (socket.isClosed()) return;
		try {
			for (ConnectionHandler ch : connections) {
				ch.sendMessage(new Message(Protocol.KickUser, reason));
				ch.shutdown();
			}
			socket.close();
		} catch (IOException e) {
			// Cannot handle
		}
	}
	
	public void shutdown() {
		if (socket.isClosed()) return;
		try {
			for (ConnectionHandler ch : connections) {
				ch.shutdown();
			}
			socket.close();
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
				case Protocol.FinalWork:
					ParticipantWorkState finalWork = (ParticipantWorkState) msg.getContent();
					manager.participantHandedIn(ID, finalWork);
					break;
					
				default: 
					Debug.error("Unable to read protocol of server input.");
				}
			} catch (Exception e) {
				Debug.error("An error occured when reading server input.");
				e.printStackTrace();
			}
			
		}
		
		private void managePassword(String password) {
			if (manager.getAssignmentOptions().getPassword().equals(password)) {
				sendMessage(new Message(Protocol.CorrectPassword, ID));
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
		
		int getID() {
			return ID;
		}
	}
	
}
