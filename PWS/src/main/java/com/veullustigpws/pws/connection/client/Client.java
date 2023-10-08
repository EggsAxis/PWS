package com.veullustigpws.pws.connection.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.veullustigpws.pws.app.Debug;
import com.veullustigpws.pws.assignment.ParticipantWorkState;
import com.veullustigpws.pws.connection.Message;
import com.veullustigpws.pws.connection.Protocol;

public class Client implements Runnable {
	
	private String ip;
	private int serverPort;
	private Socket client;
	private ParticipantManager manager;
	
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
	
	public void sendMessageToServer(String protocol) {
		Message msg = new Message(protocol);
		try {
			objOut.writeObject(msg);
		} catch (IOException e) {
			Debug.error("Unable to send message to server.");
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			client = new Socket(ip, serverPort);
			objOut = new ObjectOutputStream(client.getOutputStream());
			objIn = new ObjectInputStream(client.getInputStream());
			
			InputHandler handler = new InputHandler();
			Thread t = new Thread(handler);
			t.start();
			
			Debug.log("Client has been ran successfully.");
			
			// Send ParticipantData
			Message msg = new Message(Protocol.SendParticipantData, manager.getParticipantData());
			objOut.writeObject(msg);
			
		} catch (IOException e) {
			e.printStackTrace();
			shutdown();
		}
	}
	
	public void shutdown() {
		try {
			alive = false;
			objIn.close();
			objOut.close();
		} catch (IOException e) {
			// Ignore
		}
	}
	
	class InputHandler implements Runnable {
		@Override
		public void run() {
			Message msg;
			try {
				while (alive && (msg = (Message) objIn.readObject()) != null) {
					handleInput(msg);
				}
			} catch (IOException | ClassNotFoundException e) {
				Debug.error("Unable to receive message from server.");
			}
		}
		
		private void handleInput(Message msg) {
			
			switch (msg.getDescription()) {
			case Protocol.StartAssignment:
				manager.assignmentStarted();
				break;
			case Protocol.RequestWork:
				sendWork();
				break;
			default:
				Debug.error("Unknown input.");
			}
		}
		
		private void sendWork() {
			try {
				ParticipantWorkState pws = manager.getParticipantWorkState();
				Debug.log("Sending text length = " + pws.getDocument().getLength());
				Message work = new Message(Protocol.SendRequestedWork, pws);
				objOut.writeObject(work);
			} catch (IOException e) {
				Debug.error("Unable to send ParticipantWorkState");
			}
		}
	}
	
	
}
