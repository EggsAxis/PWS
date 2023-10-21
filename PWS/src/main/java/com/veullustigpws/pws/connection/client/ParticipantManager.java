package com.veullustigpws.pws.connection.client;

import java.util.Timer;
import java.util.TimerTask;
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
import com.veullustigpws.pws.utils.AssignmentUtilities;

public class ParticipantManager {
	
	private LoginScreen loginScreen;
	private EditorScreen editorScreen;
	private ParticipantWaitingScreen waitingScreen;
	
	private Client client;
	private ParticipantData participantData;
	
	private AssignmentOptions assignmentOptions;
	private long startTime;
	private boolean paused = false;
	
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
	
	public void assignmentStarted(AssignmentOptions assignmentOptions) {
		this.assignmentOptions = assignmentOptions;
		startTime = System.currentTimeMillis() - assignmentOptions.getRunningTime();
		editorScreen.setAssignmentName(assignmentOptions.getAssignmentName());
		
		infoUpdateTimer();
		App.Window.setScreen(editorScreen);
		Debug.log("Assignment has started.");
	}
	
	public void assignmentPaused(long pauseDuration) {
		paused = !paused;
		editorScreen.setPaused(paused);
		
		if (paused) {
			Debug.log("Assignment was paused.");
		} else {
			startTime += pauseDuration;
			Debug.log("Assignment was resumed.");
		}
	}
	
	private void infoUpdateTimer() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (paused) return;
				int wordCount = getWordCount();
				
				editorScreen.setWordCount(wordCount);
				editorScreen.setRemainingTime(getRemainingTime());
			}
		}, 0, 1000);
		
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
		int wordCount = getWordCount();
		
		ParticipantWorkState pws = new ParticipantWorkState(participantData, doc, wordCount);
		Debug.log("Sent ParticipantWorkState");
		return pws;
	}
	
	public int getWordCount() {
		StyledDocument doc = editorScreen.getStyledDocument();
		return AssignmentUtilities.getWordCount(doc);
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
	
	
	
	public String getRemainingTime() {
		return AssignmentUtilities.getRemainingTime(startTime, assignmentOptions.getAssignmentDuration());
	}
	public AssignmentOptions getAssignmentOptions() {
		return assignmentOptions;
	}
	// Setters
	public ParticipantData getParticipantData() {
		return participantData;
	}
	
	
}
