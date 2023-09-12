package com.veullustigpws.pws.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
	
	private int serverPort;
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private boolean done;
	
	public Client() {
		run();
	}
	
	@Override
	public void run() {
		try {
			Socket client = new Socket("127.0.0.1", serverPort);
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			InputHandler handler = new InputHandler();
			Thread thread = new Thread(handler);
			thread.start();
			
			String inMsg;
			while ((inMsg = in.readLine()) != null) {
				System.out.println(inMsg);
			}
		} catch (IOException e) {
			e.printStackTrace();
			shutdown();
		}
		
	}
	
	public void shutdown() {
		
		try {
			done = true;
			in.close();
			out.close();
		} catch (IOException e) {
			// Ignore
		}
		
	}
	
	class InputHandler implements Runnable {
		@Override
		public void run() {
//			try {
//				
//			} catch (IOException e) {
//				shutdown();
//			}
			
		}
	}
	
	
}
