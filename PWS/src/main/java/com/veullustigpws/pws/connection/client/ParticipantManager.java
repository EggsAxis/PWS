package com.veullustigpws.pws.connection.client;

import com.veullustigpws.pws.ui.ParticipantWaitingScreen;
import com.veullustigpws.pws.ui.editor.EditorScreen;

public class ParticipantManager {
	
	private EditorScreen editorScreen;
	private ParticipantWaitingScreen waitingScreen;
	
	private Client client;
	
	
	public void startClient(Participant participant, int port) {
		client = new Client(this);
		client.connect(port);
	}
	
	public void setEditorScreen(EditorScreen editor) {
		this.editorScreen = editor;
	}
	public void setWaitingScreen(ParticipantWaitingScreen waitingScreen) {
		this.waitingScreen = waitingScreen;
	}
}
