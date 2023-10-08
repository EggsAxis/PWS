package com.veullustigpws.pws.ui.monitor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.veullustigpws.pws.assignment.ParticipantWorkState;
import com.veullustigpws.pws.connection.hosting.HostingManager;
import com.veullustigpws.pws.listeners.WorkStateListener;

public class ParticipantPanel extends JPanel implements WorkStateListener {

	private static final long serialVersionUID = 1L;
	private static final int borderX = 10;
	private static final int borderY = 2;
	
	private HostingManager manager;
	
	// User data
	private String name;
	private int wordCount;
	private JLabel wordCountLabel;
	private int ID;
	
	
	public ParticipantPanel(HostingManager manager, String name, int ID) {
		this.manager = manager;
		this.name = name;
		this.ID = ID;
		this.wordCount = 0;
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setMaximumSize(new Dimension(99999, 60));
		this.setBorder(BorderFactory.createEmptyBorder(borderY, borderX, borderY, borderX));
		
		initComponents();
	}
	
	private void initComponents() {
		Font font = new Font("", Font.PLAIN, 16);
		
		// Name Label
		JLabel nameLabel = new JLabel();
		nameLabel.setFont(font);
		nameLabel.setText(name);
		
		// Wordcount label
		wordCountLabel = new JLabel();
		wordCountLabel.setFont(font);
		wordCountLabel.setText(wordCount + " woorden");
		
		// Buttons
		JButton spectateBtn = new JButton("Bekijk");
		JButton kickBtn = new JButton("Verwijder");
		spectateBtn.setFocusable(false);
		kickBtn.setFocusable(false);
		spectateBtn.setFont(font);
		kickBtn.setFont(font);
		
		spectateBtn.addActionListener(e -> {
			manager.viewWork(ID);
		});
		
		// Add together
		this.add(Box.createRigidArea(new Dimension(12, 0)));
		this.add(nameLabel);
		this.add(Box.createHorizontalGlue());
		this.add(wordCountLabel);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(spectateBtn);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(kickBtn);
		this.add(Box.createRigidArea(new Dimension(12, 0)));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(new Color(191, 189, 189));
		g.fillRoundRect(borderX, borderY, getWidth() - 2*borderX, getHeight() - 2*borderY, 10, 10);
	}

	
	@Override
	public void changedWorkState(HashMap<Integer, ParticipantWorkState> participantWorkStates) {
		ParticipantWorkState pws = participantWorkStates.get(ID);
		if (pws == null) 	wordCount = -1;
		else 				wordCount = pws.getWordCount();
		wordCountLabel.setText(wordCount + " woorden");
		this.repaint();
	}
}
