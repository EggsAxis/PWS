package com.veullustigpws.pws.connection.hosting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import com.veullustigpws.pws.app.App;
import com.veullustigpws.pws.app.Debug;
import com.veullustigpws.pws.assignment.AssignmentOptions;
import com.veullustigpws.pws.assignment.ParticipantData;
import com.veullustigpws.pws.assignment.ParticipantWorkState;
import com.veullustigpws.pws.connection.Message;
import com.veullustigpws.pws.connection.Protocol;
import com.veullustigpws.pws.listeners.WorkStateListener;
import com.veullustigpws.pws.ui.FillUpScreen;
import com.veullustigpws.pws.ui.monitor.MonitorScreen;
import com.veullustigpws.pws.ui.monitor.ViewWorkScreen;

public class HostingManager {
	
	// Screens
	private MonitorScreen monitorScreen;
	private FillUpScreen fillUpScreen;
	private ViewWorkScreen viewWorkScreen;
	
	// Participants
	private ArrayList<ParticipantData> participants = new ArrayList<>();
	private HashMap<Integer, ParticipantWorkState> participantWorkStates = new HashMap<>();
	
	// Server info
	private AssignmentOptions assignmentOptions;
	private Server server;
	private String serverCode;
	
	// Timer
	private Timer requestTimer;
	private static final long requestTimerDelay = 5*1000;
	
	// Listeners
	private ArrayList<WorkStateListener> workStateListeners = new ArrayList<>();
	
	public HostingManager(AssignmentOptions options) {
		this.assignmentOptions = options;
		server = new Server(this);
		
		monitorScreen = new MonitorScreen(this);
		fillUpScreen = new FillUpScreen(this);
		viewWorkScreen = new ViewWorkScreen(this);
		
		addWorkStateListener(viewWorkScreen);
	}
	
	
	// EVENTS
	void participantEntered(ParticipantData pd) {
		participants.add(pd);
		Debug.log(pd.getName() + " entered.");
	}
	
	void participantLeft(int ID) {
		ParticipantData pd = getParticipantDataByID(ID);
		participants.remove(pd);
		Debug.log(pd.getName() + " left.");
	}
	
	public void startAssignment() {
		server.startAssignment();
		App.Window.setScreen(monitorScreen);
		monitorScreen.refreshParticipants();
		Debug.log("Started assignment.");
		
		// Start request timer
		startRequestTimer();
	}
	
	private void startRequestTimer() {
		requestTimer = new Timer();
		requestTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				server.broadcast(new Message(Protocol.RequestWork));
				Debug.log("Requested ParticipantWorkState");
			}
		}, requestTimerDelay, requestTimerDelay);
	}
	
	
	public void viewWork(int participantID) {
		if (!participantWorkStates.containsKey(participantID)) {
			Debug.error("Does not contain work of user ID " + participantID);
			return;
		}
		viewWorkScreen.shown = true;
		viewWorkScreen.setParticipantWorkState(participantWorkStates.get(participantID));
		App.Window.setScreen(viewWorkScreen);
	}
	
	public void receivedRequestedWork(ParticipantWorkState pws, int ID) {
		participantWorkStates.put(ID, pws);
		for (WorkStateListener wsl : workStateListeners)  {
			if (wsl == null) continue;
			wsl.changedWorkState(participantWorkStates);
		}
		Debug.log(" - Received work state of user " + ID);
	}
	
	public void returnToMonitorScreen() {
		App.Window.setScreen(monitorScreen);
	}
	public void openFillUpScreen() {
		App.Window.setScreen(fillUpScreen);
	}
	
	
	public void addWorkStateListener(WorkStateListener wsl) {
		workStateListeners.add(wsl);
	}
	
	// setters
	void setServerCode(String code) {
		this.serverCode = code;
	}
	
	
	// Getters
	public ArrayList<ParticipantData> getParticipants() {
		return participants;
	}
	public AssignmentOptions getAssignmentOptions() {
		return assignmentOptions;
	}
	public ParticipantData getParticipantDataByID(int ID) {
		for (ParticipantData pd : participants) {
			if (pd.getID() == ID) {
				 return pd;
			}
		}
		return null;
	}
	
	public int getWordCountByID(int ID) {
		ParticipantWorkState pws = participantWorkStates.get(ID);
		if (pws == null) return -1;
		return pws.getWordCount();
	}
	
}
