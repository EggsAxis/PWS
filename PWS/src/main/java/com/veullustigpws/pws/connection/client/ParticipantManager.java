package com.veullustigpws.pws.connection.client;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import com.veullustigpws.pws.app.App;
import com.veullustigpws.pws.app.Debug;
import com.veullustigpws.pws.assignment.AssignmentOptions;
import com.veullustigpws.pws.assignment.ParticipantData;
import com.veullustigpws.pws.assignment.ParticipantWorkState;
import com.veullustigpws.pws.connection.Protocol;
import com.veullustigpws.pws.exceptions.WrongConnectionDataException;
import com.veullustigpws.pws.ui.ParticipantWaitingScreen;
import com.veullustigpws.pws.ui.editor.EditorScreen;
import com.veullustigpws.pws.ui.login.LoginScreen;
import com.veullustigpws.pws.utils.StringUtilities;

public class ParticipantManager {
	
	private LoginScreen loginScreen;
	private EditorScreen editorScreen;
	private ParticipantWaitingScreen waitingScreen;
	
	private Client client;
	private ParticipantData participantData;
	
	private AssignmentOptions assignmentOptions;
	
	public ParticipantManager(ParticipantConnectData connectData, LoginScreen loginScreen) throws WrongConnectionDataException {
		this.loginScreen = loginScreen;
		participantData = connectData.getParticipantData();
		
		startClient(connectData);
	}
	
	private void loadScreens() {
		waitingScreen = new ParticipantWaitingScreen(this);
		editorScreen = new EditorScreen(this);
	}
	
	public void openWaitingScreen() {
		App.Window.setScreen(waitingScreen);
	}
	
	public void startClient(ParticipantConnectData participantConnectData) throws WrongConnectionDataException {
		try {
			client = new Client(this);
			client.connect(participantConnectData);
		} catch (WrongConnectionDataException e) {
			throw e;
		}
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
	
	public void joinedRoom() {
		Debug.log("Correct password. Succesfully joined room.");
		loadScreens();
		openWaitingScreen();
	}
	public void incorrectPassword() {
		loginScreen.incorrectPassword();
	}
	
	
	public void assignmentStarted(AssignmentOptions assignmentOptions) {
		this.assignmentOptions = assignmentOptions;
		App.Window.setScreen(editorScreen);
		Debug.log("Assignment has started.");
	}
	
	// Setters
	public ParticipantData getParticipantData() {
		return participantData;
	}
	
	
}
