package com.veullustigpws.pws.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.veullustigpws.pws.connection.client.ParticipantManager;

public class ParticipantWaitingScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private ParticipantManager manager;
	
	private JButton leaveBtn;
	
	public ParticipantWaitingScreen(ParticipantManager manager) {
		this.manager = manager;
		this.setLayout(new BorderLayout());
		
		initComponents();
	}
	
	private void initComponents() {
		JLabel label = new JLabel("Je zit bent in de ruimte toegetreden! Aan het wachten op het begin.");
		label.setHorizontalAlignment(JLabel.CENTER);
		
		
		leaveBtn = new JButton("Verlaat");
		leaveBtn.setSize(new Dimension(9999, 299));
		leaveBtn.addActionListener(e -> {
			manager.leave();
		});
		
		
		this.add(label, BorderLayout.CENTER);
		this.add(leaveBtn, BorderLayout.SOUTH);
		
	}
}
