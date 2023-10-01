package com.veullustigpws.pws.connection.hosting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.veullustigpws.pws.app.Debug;
import com.veullustigpws.pws.assignment.ParticipantData;
import com.veullustigpws.pws.assignment.ParticipantWorkState;
import com.veullustigpws.pws.connection.Protocol;

public class Server implements Runnable{
	
	private boolean joinable;
	private HostingManager manager;
	
	private ArrayList<ConnectionHandler> connections;
	private ServerSocket socket;
	private ExecutorService threadpool;
	
	
	public Server(HostingManager manager) {
		this.manager = manager;
		joinable = true;
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
			
			Debug.log("SERVER INFO:");
			Debug.log(" > IP: " + ip.getHostAddress());
			Debug.log(" > Name: " + ip.getHostName());
			Debug.log(" > Port: " + port);
			
			manager.setLocalIP(ip.getHostAddress());
			manager.setPortNumber(port);
			
			// Accept all clients
			threadpool = Executors.newCachedThreadPool();
			
			while (joinable) {
				Socket client = socket.accept();
				ConnectionHandler handler = new ConnectionHandler(client);
				connections.add(handler);
				threadpool.execute(handler);
			}
		} catch (IOException e) {
			shutdown();
		}
		
	}
	
	public void startAssignment() {
		joinable = false;
		broadcast(Protocol.StartAssignment);
	}
	
	public HashMap<Integer, ParticipantWorkState> gatherRequestedWork() {
		broadcast(Protocol.RequestWork);
		HashMap<Integer, ParticipantWorkState> pwsList = new HashMap<>();
		
		for (ConnectionHandler c : connections) {
			ParticipantWorkState pws = (ParticipantWorkState) c.receiveObject();
			pwsList.put(c.ID, pws);
		}
		
		return pwsList;
	}
	
	public void broadcast(String msg) {
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
		
		private boolean alive;
		private Socket client;
		private int ID = -1;
		
		private BufferedReader strIn;
		private PrintWriter strOut;
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
				strOut = new PrintWriter(client.getOutputStream(), true);
				strIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
				objOut = new ObjectOutputStream(client.getOutputStream());
				objIn = new ObjectInputStream(client.getInputStream());
				
				String message;
				while ((message = strIn.readLine()) != null && alive) {
					handleInput(message);
				}
			} catch (IOException e) {
				shutdown();
			}
			
		}
		
		private void handleInput(String msg) {
			try {
				String[] input = msg.split(Protocol.ConnectChar);
				String inputType = input[0];
				
				switch (inputType) {
				case Protocol.SendParticipantData:
					ParticipantData pd = (ParticipantData) receiveObject();
					pd.setID(ID);
					manager.participantEntered(pd);
					break;
				case Protocol.ParticipantLeaves:
					manager.participantLeft(ID);
					breakConnection();
					break;
				default: 
					Debug.error("Unable to read protocol of server input.");
				}
			} catch (Exception e) {
				Debug.error("Unable to read protocol of server input.");
			}
			
		}
		
		public void sendMessage(String msg) {
			strOut.println(msg);
		}
		
		private void breakConnection() {
			connections.remove(this);
			alive = false;
			shutdown();
		}
		
		private Object receiveObject() {
			Object o = null;
			try {
				 o = objIn.readObject();
			} catch (ClassNotFoundException | IOException e) {
				Debug.error("Failed to receive object.");
			}
			return o;
		}
		
		public void shutdown() {
			try {
				strIn.close();
				strOut.close();
				objIn.close();
				objOut.close();
				
				if (client.isClosed()) return;
				client.close();
			} catch (IOException e) {
				// ignore 
			}
			
		}
	}
	
}
