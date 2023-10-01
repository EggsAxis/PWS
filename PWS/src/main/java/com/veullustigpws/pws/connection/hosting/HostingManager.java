package com.veullustigpws.pws.connection.hosting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import com.veullustigpws.pws.app.Debug;
import com.veullustigpws.pws.assignment.ParticipantData;
import com.veullustigpws.pws.assignment.ParticipantWorkState;
import com.veullustigpws.pws.ui.FillUpScreen;
import com.veullustigpws.pws.ui.monitor.MonitorScreen;

public class HostingManager {
	
	// Screens
	private MonitorScreen monitorScreen;
	private FillUpScreen fillUpScreen;
	
	// Participants
	private ArrayList<ParticipantData> participants = new ArrayList<>();
	private HashMap<Integer, ParticipantWorkState> participantWorkStates = new HashMap<>();
	
	// Server info
	private Server server;
	private String localIP;
	private int portNumber;
	
	// Timer
	private Timer requestTimer;
	private static final long requestTimerDelay = 5*1000;
	
	
	public HostingManager() {
		server = new Server(this);
	}
	
	
	void participantEntered(ParticipantData pd) {
		participants.add(pd);
		Debug.log(pd.getName() + " entered.");
	}
	
	public void participantLeft(int ID) {
		ParticipantData pd = getParticipantDataByID(ID);
		participants.remove(pd);
		Debug.log(pd.getName() + " left.");
	}
	
	public void startAssignment() {
		server.startAssignment();
		Debug.log("Started assignment.");
		
		// Start request timer
		requestTimer = new Timer();
		requestTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				participantWorkStates = server.gatherRequestedWork();
				Debug.log("Requested ParticipantWorkState");
			}
		}, requestTimerDelay, requestTimerDelay);
	}
	
	
	// Setters
	public void setMonitorScreen(MonitorScreen monitorScreen) {
		this.monitorScreen = monitorScreen;
	}
	public void setFillUpScreen(FillUpScreen fillUpScreen) {
		this.fillUpScreen = fillUpScreen;
	}
	
	void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	void setLocalIP(String localIP) {
		this.localIP = localIP;
	}
	
	
	// Getters
	public ArrayList<ParticipantData> getParticipants() {
		return participants;
	}
	
	public ParticipantData getParticipantDataByID(int ID) {
		for (ParticipantData pd : participants) {
			if (pd.getID() == ID) {
				 return pd;
			}
		}
		return null;
	}
	
}
