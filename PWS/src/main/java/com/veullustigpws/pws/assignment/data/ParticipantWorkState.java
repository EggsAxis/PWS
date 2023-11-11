package com.veullustigpws.pws.assignment.data;

import java.io.Serializable;
import javax.swing.text.StyledDocument;

public class ParticipantWorkState implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ParticipantData data;
	private StyledDocument document;
	private int wordCount;
	
	public ParticipantWorkState(ParticipantData data, StyledDocument document, int wordCount) {
		this.data = data;
		this.document = document;
		this.wordCount = wordCount;
	}
	
	// Getters
	public int getWordCount() {return wordCount;}
	public ParticipantData getParticipantData() {return data;}
	public StyledDocument getDocument() {return document;}
}
