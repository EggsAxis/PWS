package com.veullustigpws.pws.connection.hosting;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.veullustigpws.pws.app.App;
import com.veullustigpws.pws.app.Debug;
import com.veullustigpws.pws.assignment.ExportManager;
import com.veullustigpws.pws.assignment.data.AssignmentOptions;
import com.veullustigpws.pws.assignment.data.ParticipantData;
import com.veullustigpws.pws.assignment.data.ParticipantWorkState;
import com.veullustigpws.pws.connection.Message;
import com.veullustigpws.pws.connection.Protocol;
import com.veullustigpws.pws.listeners.WorkStateListener;
import com.veullustigpws.pws.ui.fillup.FillUpScreen;
import com.veullustigpws.pws.ui.monitor.MonitorScreen;
import com.veullustigpws.pws.ui.monitor.ViewWorkScreen;
import com.veullustigpws.pws.utils.AssignmentUtilities;

public class HostingManager {
	
	// Screens
	private MonitorScreen monitorScreen;
	private FillUpScreen fillUpScreen;
	private ViewWorkScreen viewWorkScreen;
	
	// Participants
	private ArrayList<ParticipantData> participants = new ArrayList<>();
	private HashMap<Integer, ParticipantWorkState> participantWorkStates = new HashMap<>();
	private HashMap<Integer, ParticipantWorkState> handedInWork = new HashMap<>();
	
	// Server info
	private AssignmentOptions assignmentOptions;
	private Server server;
	private String serverCode;
	
	// Pause
	private boolean paused = false;
	private long pauseTime;
	
	// Timer
	private Timer timeTimer;
	private Timer requestTimer;
	private static final long requestTimerDelay = 5*1000;
	private long startTime;
	
	private boolean inAssignment = false;
	
	// Listeners
	private ArrayList<WorkStateListener> workStateListeners = new ArrayList<>();
	
	public HostingManager(AssignmentOptions options) {
		this.assignmentOptions = options;
		initScreens();
		addWorkStateListener(viewWorkScreen);
		
		server = new Server(this);
	}
	
	
	private void initScreens() {
		HostingManager manager = this;
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					monitorScreen = new MonitorScreen(manager);
					fillUpScreen = new FillUpScreen(manager);
					viewWorkScreen = new ViewWorkScreen(manager);
					manager.openFillUpScreen();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}


	// EVENTS
	void participantEntered(ParticipantData pd) {
		participants.add(pd);
		if (inAssignment) {
			monitorScreen.refreshParticipants();
		} else {
			fillUpScreen.refreshParticipants();
		}
		
		Debug.log(pd.getName() + " entered.");
	}
	
	void participantLeft(int ID) {
		ParticipantData pd = getParticipantDataByID(ID);
		participants.remove(pd);
		
		refreshParticipantDisplay();
		Debug.log(pd.getName() + " left.");
	}
	
	public void startAssignment() {
		inAssignment = true;
		monitorScreen.refreshParticipants();
		monitorScreen.setCode(serverCode);
		
		startTime = System.currentTimeMillis();
		assignmentOptions.setRunningTime(0);
		server.startAssignment();
		
		// Start timers
		startRequestTimer();
		startTimeTimer();
		
		App.Window.setScreen(monitorScreen);
		Debug.log("Started assignment.");
	}
	
	public void pauseAssignment() {
		paused = !paused;
		
		if (paused) {
			pauseTime = System.currentTimeMillis();
			server.broadcast(new Message(Protocol.PausedAssignment, -1L));
			monitorScreen.assignmentPaused();
			} else {
			long pauseDuration = System.currentTimeMillis() - pauseTime;
			startTime += pauseDuration;
			server.broadcast(new Message(Protocol.PausedAssignment, pauseDuration));
		}
		
		if (paused) Debug.log("Paused the assignment.");
		else 		Debug.log("Resumed the assignment.");
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
	
	public void kickParticipant(int ID) {
		participants.remove(getParticipantDataByID(ID));
		participantWorkStates.remove(ID);
		
		server.kickUser(ID, "Je bent van de room verwijderd.");
		
		refreshParticipantDisplay();
		Debug.log("Kicked user " + ID);
	}
	
	private void startTimeTimer() {
		timeTimer = new Timer();
		timeTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (paused) return;
				String remainingTime = AssignmentUtilities.getRemainingTime(startTime, assignmentOptions.getAssignmentDuration());
				monitorScreen.setTime(remainingTime);
				
				if (remainingTime.equals("00:00:00")) {
					endAssignment(false);
				}
			}
		}, 1000, 1000);
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
	
	
	
	public void receivedRequestedWork(ParticipantWorkState pws, int ID) {
		if (pws == null) {
			Debug.error("Received ParticipantWorkState with value null.");
			return;
		}
		
		participantWorkStates.put(ID, pws);
		
		if (!inAssignment) return;
		for (WorkStateListener wsl : workStateListeners)  {
			if (wsl == null) continue;
			wsl.changedWorkState(participantWorkStates);
		}
//		Debug.log(" - Received work state of user " + ID);
	}
	
	public void participantHandedIn(int ID, ParticipantWorkState work) {
		handedInWork.put(ID, work);
		
		participants.remove(getParticipantDataByID(ID));
		participantWorkStates.remove(ID);
		
		server.sendMessageToClient(ID, new Message(Protocol.ReceivedFinalWork));
		server.kickUser(ID, "Handed in");
		
		refreshParticipantDisplay();
		Debug.log("User " + ID + " handed in their work.");
	}
	
	public void endAssignment(boolean isForced) {
		if (isForced) {
			int confirmed = JOptionPane.showConfirmDialog(null, 
					"Weet u zeker dat u de opdracht wilt stoppen?\nU kunt de opdracht later niet hervatten.", 
					"Weet u het zeker?", JOptionPane.YES_NO_OPTION);
			if (confirmed != JOptionPane.YES_OPTION) {
				return;
			}
		}
		timeTimer.cancel();
		requestTimer.cancel();
		
		inAssignment = false;
		ExportManager exportManager = new ExportManager(this);
	}
	
	public void exitProgram() {
		int confirmed = JOptionPane.showConfirmDialog(null, 
				"Weet u zeker dat u het programma wil sluiten?\nAlle gegevens van de opdracht zullen verloren gaan.", 
				"Weet u het zeker?", JOptionPane.YES_NO_OPTION);
		if (confirmed == JOptionPane.YES_OPTION) {
			server.shutdown("De opdracht is gesloten.");
			System.exit(0);
		}
	}
	
	public void closeRoom() {
		int confirmed = JOptionPane.showConfirmDialog(null, 
				"Weet u zeker dat u de opdracht wil sluiten?", 
				"Weet u het zeker?", JOptionPane.YES_NO_OPTION);
		if (confirmed == JOptionPane.YES_OPTION) {
			server.shutdown("De opdracht is gesloten.");
			App.Manager.InAssignment = false;
			App.Window.setStartScreen();
		}
	}
	
	public HashMap<Integer, ParticipantWorkState> getFinalWork() {
		HashMap<Integer, ParticipantWorkState> finalWork = new HashMap<>(handedInWork);
		
		for (Map.Entry<Integer, ParticipantWorkState> set : participantWorkStates.entrySet()) {
			if (finalWork.containsKey(set.getKey())) continue;
			finalWork.put(set.getKey(), set.getValue());
		}
		
		return finalWork;
	}
	
	private void refreshParticipantDisplay() {
		if (inAssignment) {
			monitorScreen.refreshParticipants();
		} else {
			fillUpScreen.refreshParticipants();
		}
	}
	public void returnToMonitorScreen() {
		App.Window.setScreen(monitorScreen);
		
	}
	public void openFillUpScreen() {
		App.Window.setScreen(fillUpScreen);
	}
	
	public void updateRunningTime() {
		assignmentOptions.setRunningTime(System.currentTimeMillis() - startTime);
	}
	
	public void addWorkStateListener(WorkStateListener wsl) {
		workStateListeners.add(wsl);
	}
	
	// Setters
	void setServerCode(String code) {
		fillUpScreen.setServerCode(code);
		this.serverCode = code;
	}
	
	
	// Getters
	public HashMap<Integer, ParticipantWorkState> getParticipantWorkStates() {
		return participantWorkStates;
	}
	public Server getServer() {
		return server;
	}
	public String getServerCode() {
		return serverCode;
	}
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
