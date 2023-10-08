package com.veullustigpws.pws.connection.client;

import java.util.Scanner;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import com.veullustigpws.pws.app.App;
import com.veullustigpws.pws.app.Debug;
import com.veullustigpws.pws.assignment.ParticipantData;
import com.veullustigpws.pws.assignment.ParticipantWorkState;
import com.veullustigpws.pws.connection.Protocol;
import com.veullustigpws.pws.ui.ParticipantWaitingScreen;
import com.veullustigpws.pws.ui.editor.EditorScreen;
import com.veullustigpws.pws.utils.StringUtilities;

public class ParticipantManager {
	
	private EditorScreen editorScreen;
	private ParticipantWaitingScreen waitingScreen;
	
	private Client client;
	private ParticipantData participantData;
	
	public ParticipantManager() {
		waitingScreen = new ParticipantWaitingScreen(this);
		editorScreen = new EditorScreen(this);
	}
	
	public void openWaitingScreen() {
		App.Window.setScreen(waitingScreen);
	}
	
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
		StyledDocument doc = editorScreen.getStyledDocument();
		
		// Get word count
		String txt = "";
		try {
			txt = doc.getText(0, doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		int wordCount = StringUtilities.getWordCount(txt);
		
		ParticipantWorkState pws = new ParticipantWorkState(participantData, doc, wordCount);
		Debug.log("Sent ParticipantWorkState");
		return pws;
	}
	
	public void leave() {
		Debug.log("Leaving the room.");
		
		client.sendMessageToServer(Protocol.ParticipantLeaves);
		client.shutdown();
		System.exit(0); // GO BACK TO START SCREEN
	}
	
	
	public void assignmentStarted() {
		App.Window.setScreen(editorScreen);
		Debug.log("Assignment has started.");
	}
	
	// Setters
	public ParticipantData getParticipantData() {
		return participantData;
	}
	
	
}
