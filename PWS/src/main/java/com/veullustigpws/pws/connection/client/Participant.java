package com.veullustigpws.pws.connection.client;

public class Participant {
	
	private String name;
	private String studentID;
	private int ID;
	
	public Participant(String name, String studentID, int ID) {
		this.name = name;
		this.studentID = studentID;
		this.ID = ID;
	}
	
	
	
	// Getters
	public String getName() {
		return name;
	}
	public String getStudentID() {
		return studentID;
	}
	public int getID() {
		return ID;
	}
}
