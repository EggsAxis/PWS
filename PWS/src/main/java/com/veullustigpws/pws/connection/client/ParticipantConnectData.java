package com.veullustigpws.pws.connection.client;

import com.veullustigpws.pws.assignment.ParticipantData;

public class ParticipantConnectData {
	private String code;
	private String password;
	private ParticipantData participantData;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ParticipantData getParticipantData() {
		return participantData;
	}
	public void setParticipantData(ParticipantData participantData) {
		this.participantData = participantData;
	}
}
