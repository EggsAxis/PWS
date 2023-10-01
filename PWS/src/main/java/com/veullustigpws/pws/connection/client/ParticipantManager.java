package com.veullustigpws.pws.connection.client;

import java.util.Scanner;
import com.veullustigpws.pws.app.App;
import com.veullustigpws.pws.app.Debug;
import com.veullustigpws.pws.assignment.ParticipantData;
import com.veullustigpws.pws.assignment.ParticipantWorkState;
import com.veullustigpws.pws.connection.Protocol;
import com.veullustigpws.pws.ui.ParticipantWaitingScreen;
import com.veullustigpws.pws.ui.editor.EditorScreen;

public class ParticipantManager {
	
	private EditorScreen editorScreen;
	private ParticipantWaitingScreen waitingScreen;
	
	private Client client;
	private ParticipantData participantData;
	
	
	public void startClient(ParticipantData participantData) {
		this.participantData = participantData;
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter server IP: ");
		String ip = scanner.nextLine();
		System.out.println("Enter server port: ");
		int port = scanner.nextInt();
		scanner.close();
		
		client = new Client(this);
		client.connect(ip, port);
	}
	
	
	public ParticipantWorkState getParticipantWorkState() {
		Debug.log("Sent ParticipantWorkState");
		return null;
	}
	
	public void leave() {
		Debug.log("Leaving the room.");
		
		client.sendMessageToServer(Protocol.ParticipantLeaves);
		client.shutdown();
		System.exit(0); // GO BACK TO START SCREEN
	}
	
	
	public void assignmentStarted() {
		editorScreen = new EditorScreen(this);
		App.Window.setScreen(editorScreen);
		
		Debug.log("Assignment has started.");
	}
	
	
	// Setters
	public void setWaitingScreen(ParticipantWaitingScreen waitingScreen) {
		this.waitingScreen = waitingScreen;
	}
	
	
	public ParticipantData getParticipantData() {
		return participantData;
	}
	
	
}
