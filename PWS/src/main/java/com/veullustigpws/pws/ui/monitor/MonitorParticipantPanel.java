package com.veullustigpws.pws.ui.monitor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.assignment.ParticipantWorkState;
import com.veullustigpws.pws.connection.hosting.HostingManager;
import com.veullustigpws.pws.listeners.WorkStateListener;
import com.veullustigpws.pws.ui.appearance.ColoredButtonUI;
import com.veullustigpws.pws.ui.components.WhiteLabel;
import com.veullustigpws.pws.utils.GUIUtils;

public class MonitorParticipantPanel extends JPanel implements WorkStateListener {

	private static final long serialVersionUID = 1L;
	private static final int borderX = 10;
	private static final int borderY = 2;
	
	private HostingManager manager;
	
	// User data
	private String name;
	private int wordCount;
	private WhiteLabel wordCountLabel;
	private int ID;
	
	
	public MonitorParticipantPanel(HostingManager manager, String name, int ID) {
		this.manager = manager;
		this.name = name;
		this.ID = ID;
		this.wordCount = 0;
		
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setMaximumSize(new Dimension(99999, 60));
		this.setBorder(BorderFactory.createEmptyBorder(borderY, borderX, borderY, borderX));
		
		initComponents();
	}
	
	private void initComponents() {
		
		// Name Label
		WhiteLabel nameLabel = new WhiteLabel(name);
		
		// Wordcount label
		wordCountLabel = new WhiteLabel(wordCount + " woorden");
		
		// Buttons
		JButton spectateBtn = new JButton("Bekijk");
		JButton kickBtn = new JButton("Verwijder");
		spectateBtn.setFocusable(false);
		kickBtn.setFocusable(false);
		spectateBtn.setUI(new ColoredButtonUI(ColorPalet.BlueButton, spectateBtn));
		kickBtn.setUI(new ColoredButtonUI(ColorPalet.RedButton, kickBtn));
		
		Dimension dim = new Dimension(100, 32);
		GUIUtils.setComponentSize(spectateBtn, dim);
		GUIUtils.setComponentSize(kickBtn, dim);
		
		spectateBtn.addActionListener(e -> {
			manager.viewWork(ID);
		});
		kickBtn.addActionListener(e -> {
			int confirmed = JOptionPane.showConfirmDialog(null, 
					"Weet u zeker dat je deze deelnemer wilt verwijderen?\nZijn of haar voortgang zal verloren gaan.", 
					"Weet u het zeker?", JOptionPane.YES_NO_OPTION);
			if (confirmed == JOptionPane.YES_OPTION) {
				manager.kickParticipant(ID);
			}
		});
		
		// Add together
		this.add(Box.createRigidArea(new Dimension(18, 0)));
		this.add(nameLabel);
		this.add(Box.createHorizontalGlue());
		this.add(wordCountLabel);
		this.add(Box.createRigidArea(new Dimension(24, 0)));
		this.add(spectateBtn);
		this.add(Box.createRigidArea(new Dimension(12, 0)));
		this.add(kickBtn);
		this.add(Box.createRigidArea(new Dimension(18, 0)));
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(ColorPalet.DarkBackgroundColor);
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
