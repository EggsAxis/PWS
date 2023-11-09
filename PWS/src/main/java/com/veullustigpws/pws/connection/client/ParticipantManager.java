package com.veullustigpws.pws.connection.client;

import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;
import javax.swing.text.StyledDocument;
import com.veullustigpws.pws.app.App;
import com.veullustigpws.pws.app.Debug;
import com.veullustigpws.pws.assignment.data.AssignmentOptions;
import com.veullustigpws.pws.assignment.data.ParticipantData;
import com.veullustigpws.pws.assignment.data.ParticipantWorkState;
import com.veullustigpws.pws.connection.Protocol;
import com.veullustigpws.pws.exceptions.WrongConnectionDataException;
import com.veullustigpws.pws.ui.editor.EditorScreen;
import com.veullustigpws.pws.ui.export.HandInScreen;
import com.veullustigpws.pws.ui.login.ParticipantWaitingScreen;
import com.veullustigpws.pws.utils.AssignmentUtilities;

public class ParticipantManager {

	private EditorScreen editorScreen;
	private ParticipantWaitingScreen waitingScreen;
	private HandInScreen handInScreen;
	
	private Client client;
	private ParticipantData participantData;
	
	private AssignmentOptions assignmentOptions;
	private long startTime;
	private boolean paused = false;
	private Timer infoUpdateTimer;
	
	public ParticipantManager(ParticipantConnectData connectData) throws WrongConnectionDataException {
		participantData = connectData.getParticipantData();
		
		startClient(connectData);
	}
	
	private void loadScreens() {
		ParticipantManager manager = this;
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					waitingScreen = new ParticipantWaitingScreen(manager);
					editorScreen = new EditorScreen(manager);
					handInScreen = new HandInScreen();
					openWaitingScreen();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
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
	
	public void assignmentEnded() {
		handInScreen.setTimeOver(true);
		closeClient();
	}
	
	public void closeClient() {
		infoUpdateTimer.cancel();
		client.shutdown();
		App.Window.setScreen(handInScreen);
		App.Manager.handedIn();
	}
	
	private void infoUpdateTimer() {
		infoUpdateTimer = new Timer();
		infoUpdateTimer.scheduleAtFixedRate(new TimerTask() {
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
			App.RunningServer = false;
			client = new Client(this);
			client.connect(participantConnectData);
		} catch (WrongConnectionDataException e) {
			throw e;
		}
	}
	
	public void participantKicked(String reason) {
		App.Window.setStartScreen();
		App.Window.notify(reason);
		App.Manager.InAssignment = false;
		Debug.log("Lost connection for reason: " + reason);
	}
	
	public void exitProgram() {
		leave();
		System.exit(0);
	}
	
	public ParticipantWorkState getParticipantWorkState() {
		StyledDocument doc = editorScreen.getStyledDocument();
		int wordCount = getWordCount();
		
		ParticipantWorkState pws = new ParticipantWorkState(participantData, doc, wordCount);
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
		App.Manager.InAssignment = false;
		App.Window.setStartScreen();
	}
	
	public void joinedRoom(int ID) {
		App.Manager.InAssignment = true;
		participantData.setID(ID);
		Debug.log("Correct password. Succesfully joined room.");
		loadScreens();
		App.RunningServer = false;
	}
	
	public void incorrectPassword() {
		App.Manager.incorrectPassword();
		App.Manager.InAssignment = false;
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
