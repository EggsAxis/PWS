package com.veullustigpws.pws.ui;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ParticipantWaitingScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public ParticipantWaitingScreen() {
		this.setLayout(new BorderLayout());
		
		initComponents();
	}
	
	private void initComponents() {
		JLabel label = new JLabel("You have joined the room!\nWaiting for the host to start...");
		this.add(label, BorderLayout.CENTER);
	}
}
