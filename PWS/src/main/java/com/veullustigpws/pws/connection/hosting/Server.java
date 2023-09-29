package com.veullustigpws.pws.connection.hosting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
	
	private ArrayList<ConnectionHandler> connections;
	private ServerSocket socket;
	private boolean done;
	private ExecutorService threadpool;
	
	private int port;
	
	public Server() {
		connections = new ArrayList<>();
		done = false;
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		try {
			socket = new ServerSocket(0);
			
			port = socket.getLocalPort();
			
			System.out.println("Port: " + port);
			
			threadpool = Executors.newCachedThreadPool();
			
			while (!done) {
				Socket client = socket.accept();
				ConnectionHandler handler = new ConnectionHandler(client);
				connections.add(handler);
				threadpool.execute(handler);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
			shutdown();
		}
		
	}
	
	public void broadcast(String msg) {
		for (ConnectionHandler ch : connections) {
			ch.sendMessage(msg);
		}
	}
	
	public void shutdown() {
		if (socket.isClosed()) return;
		try {
			done = true;
			socket.close();
			
			for (ConnectionHandler ch : connections) {
				ch.shutdown();
			}
			
		} catch (IOException e) {
			// Cannot handle
		}
	}
	
	public int getPortNumber() {
		return port;
	}
	
	class ConnectionHandler implements Runnable {
		
		private Socket client;
		private BufferedReader in;
		private PrintWriter out;
		
		public ConnectionHandler(Socket client) {
			this.client = client;
		}
		@Override
		public void run() {
			try {
				out = new PrintWriter(client.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				
				String message;
				while ((message = in.readLine()) != null) {
					
				}
			} catch (IOException e) {
				shutdown();
			}
			
		}
		
		public void sendMessage(String msg) {
			out.println(msg);
		}
		
		public void shutdown() {
			try {
				in.close();
				out.close();
				
				if (client.isClosed()) return;
				client.close();
			} catch (IOException e) {
				// ignore
			}
			
		}
	}
	
}
