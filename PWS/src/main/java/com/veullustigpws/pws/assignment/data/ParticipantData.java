package com.veullustigpws.pws.assignment.data;

import java.io.Serializable;

public class ParticipantData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String studentID;
	private int ID = -1;
	
	public ParticipantData(String name, String studentID) {
		this.name = name;
		this.studentID = studentID;
	}
	public ParticipantData(String name, String studentID, int ID) {
		this.name = name;
		this.studentID = studentID;
		this.ID = ID;
	}
	
	
	// Setters
	public void setID(int ID) {
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
