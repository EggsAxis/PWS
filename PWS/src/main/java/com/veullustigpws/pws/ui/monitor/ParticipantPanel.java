package com.veullustigpws.pws.ui.monitor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ParticipantPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int borderX = 10;
	private static final int borderY = 2;
	
	// User data
	private String name;
	private int wordCount;
	private JLabel wordCountLabel;
	
	
	public ParticipantPanel(String name, int wordCount) {
		this.name = name;
		this.wordCount = wordCount;
		
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
}
