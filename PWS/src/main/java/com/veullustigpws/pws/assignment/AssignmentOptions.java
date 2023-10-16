package com.veullustigpws.pws.assignment;

public class AssignmentOptions {
	
	// Assignment options
	private String assignmentName;
	private String assignmentDescription;
	private int assignmentDuration;
	private int wordCount;
	private boolean sendTimeReminder;
	private int timeReminderFrequency;
	
	// Room options
	private String roomName;
	private String password;
	private boolean detectionEnabled;
	
	// Getters and setters
	public String getAssignmentName() {
		return assignmentName;
	}
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}
	public String getAssignmentDescription() {
		return assignmentDescription;
	}
	public void setAssignmentDescription(String assignmentDescription) {
		this.assignmentDescription = assignmentDescription;
	}
	public int getAssignmentDuration() {
		return assignmentDuration;
	}
	public void setAssignmentDuration(int assignmentDuration) {
		this.assignmentDuration = assignmentDuration;
	}
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
	public boolean isSendTimeReminder() {
		return sendTimeReminder;
	}
	public void setSendTimeReminder(boolean sendTimeReminder) {
		this.sendTimeReminder = sendTimeReminder;
	}
	public int getTimeReminderFrequency() {
		return timeReminderFrequency;
	}
	public void setTimeReminderFrequency(int timeReminderFrequency) {
		this.timeReminderFrequency = timeReminderFrequency;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isDetectionEnabled() {
		return detectionEnabled;
	}
	public void setDetectionEnabled(boolean detectionEnabled) {
		this.detectionEnabled = detectionEnabled;
	}
	
	
	
}
