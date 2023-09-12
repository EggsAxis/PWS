package com.veullustigpws.pws.connection;

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
	private ServerSocket server;
	private boolean done;
	private ExecutorService threadpool;
	
	public Server() {
		connections = new ArrayList<>();
		done = false;
	}
	
	@Override
	public void run() {
		try {
			server = new ServerSocket(0);
			System.out.println("Port: " + server.getLocalPort());
			
			threadpool = Executors.newCachedThreadPool();
			
			while (!done) {
				Socket client = server.accept();
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
		if (server.isClosed()) return;
		try {
			done = true;
			server.close();
			
			for (ConnectionHandler ch : connections) {
				ch.shutdown();
			}
			
		} catch (IOException e) {
			// Cannot handle
		}
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
