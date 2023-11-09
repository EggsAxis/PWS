package com.veullustigpws.pws.connection.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import com.veullustigpws.pws.app.Debug;
import com.veullustigpws.pws.assignment.data.AssignmentOptions;
import com.veullustigpws.pws.assignment.data.ParticipantWorkState;
import com.veullustigpws.pws.connection.ConnectData;
import com.veullustigpws.pws.connection.Message;
import com.veullustigpws.pws.connection.Protocol;
import com.veullustigpws.pws.exceptions.WrongConnectionDataException;
import com.veullustigpws.pws.utils.JoinCodeGenerator;

public class Client {
	
	private Socket client;
	private ParticipantManager manager;
	private String serverPassword;
	
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private boolean alive;
	private Thread handlerThread;
	
	public Client(ParticipantManager manager) {
		this.manager = manager;
		alive = true;
	}
	
	public void connect(ParticipantConnectData participantConnectData) throws WrongConnectionDataException {
		
		ConnectData connectData = null;
		try {
			connectData = JoinCodeGenerator.codeToIP(participantConnectData.getCode(), InetAddress.getLocalHost().getHostAddress());
		} catch (WrongConnectionDataException e) {
			throw e;
		} catch (UnknownHostException e) {
			throw new WrongConnectionDataException(WrongConnectionDataException.UNKNOWN_HOST);
		}
		serverPassword = participantConnectData.getPassword();
		
		try {
			client = new Socket(connectData.getIp(), connectData.getPort());
		} catch (Exception e) {
			throw new WrongConnectionDataException(WrongConnectionDataException.INVALID_CODE);
		}
		System.out.println("Invalid code");
		initClient();
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
	
	private void initClient() throws WrongConnectionDataException {
		try {
			objOut = new ObjectOutputStream(client.getOutputStream());
			objIn = new ObjectInputStream(client.getInputStream());
			
			InputHandler handler = new InputHandler();
			handlerThread = new Thread(handler);
			handlerThread.start();
			
			Debug.log("Client has been ran successfully.");
			
			// Submit password
			Message passwordMsg = new Message(Protocol.SendPassword, serverPassword);
			objOut.writeObject(passwordMsg);
			
		} catch (IOException e) {
			e.printStackTrace();
			shutdown();
		}
	}
	
	public void shutdown() {
		try {
			handlerThread.interrupt();
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
				manager.assignmentStarted((AssignmentOptions) msg.getContent());
				break;
			case Protocol.PausedAssignment:
				long pauseDuration = (long) msg.getContent();
				manager.assignmentPaused(pauseDuration);
				break;
			case Protocol.RequestWork:
				sendWork();
				break;
			case Protocol.CorrectPassword:
				int ID = (int) msg.getContent();
				joinedRoom(ID);
				break;
			case Protocol.IncorrectPassword:
				manager.incorrectPassword();
				shutdown();
				break;
			case Protocol.AssignmentEnded: 
				manager.assignmentEnded();
				break;
			case Protocol.KickUser:
				String reason = (String) msg.getContent();
				manager.participantKicked(reason);
				shutdown();
				break;
			default:
				Debug.error("Unknown server input.");
			}
		}
		
		private void joinedRoom(int ID) {
			manager.joinedRoom(ID);
			
			
			// Send ParticipantData
			Message pdMsg = new Message(Protocol.SendParticipantData, manager.getParticipantData());
			sendMessage(pdMsg);
		}
		
		private void sendWork() {
			ParticipantWorkState pws = manager.getParticipantWorkState();
			Message work = new Message(Protocol.SendRequestedWork, pws);
			sendMessage(work);
		}
		
		private void sendMessage(Message msg) {
			try {
				objOut.writeObject(msg);
				objOut.reset();
			} catch (IOException e) {
				Debug.error("Unable to send message to server.");
				e.printStackTrace();
			}
		}
	}
	
	
}
