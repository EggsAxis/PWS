package com.veullustigpws.pws.connection.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import com.veullustigpws.pws.app.Debug;
import com.veullustigpws.pws.assignment.ParticipantWorkState;
import com.veullustigpws.pws.connection.Protocol;

public class Client implements Runnable {
	
	private String ip;
	private int serverPort;
	private Socket client;
	private ParticipantManager manager;
	
	private BufferedReader strIn;
	private PrintWriter strOut;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private boolean alive;
	
	public Client(ParticipantManager manager) {
		this.manager = manager;
		alive = true;
	}
	
	public void connect(String ip, int port) {
		this.serverPort = port;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void sendMessageToServer(String msg) {
		strOut.println(msg);
	}
	
	@Override
	public void run() {
		try {
			client = new Socket(ip, serverPort);
			strOut = new PrintWriter(client.getOutputStream(), true);
			strIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
			objOut = new ObjectOutputStream(client.getOutputStream());
			objIn = new ObjectInputStream(client.getInputStream());
			
			InputHandler handler = new InputHandler();
			Thread t = new Thread(handler);
			t.start();
			
			Debug.log("Client has been ran successfully.");
			
			// Send ParticipantData
			strOut.println(Protocol.SendParticipantData);
			objOut.writeObject(manager.getParticipantData());
			
		} catch (IOException e) {
			e.printStackTrace();
			shutdown();
		}
	}
	
	public void shutdown() {
		try {
			alive = false;
			strIn.close();
			strOut.close();
			objIn.close();
			objOut.close();
		} catch (IOException e) {
			// Ignore
		}
	}
	
	class InputHandler implements Runnable {
		@Override
		public void run() {
			String msg;
			try {
				while (alive && (msg = strIn.readLine()) != null) {
					handleInput(msg);
				}
			} catch (IOException e) {
				manager.leave();
			}
		}
		
		private void handleInput(String msg) {
			String[] input = msg.split(Protocol.ConnectChar);
			String inputType = input[0];
			
			switch (inputType) {
			case Protocol.StartAssignment:
				manager.assignmentStarted();
				break;
			case Protocol.RequestWork:
				requestWork();
				break;
			default:
				Debug.error("Unable to handle input.");
			}
		}
		
		private void requestWork() {
			try {
				ParticipantWorkState pws = manager.getParticipantWorkState();
				objOut.writeObject(pws);
			} catch (IOException e) {
				Debug.error("Unable to send ParticipantWorkState");
			}
		}
	}
	
	
}
