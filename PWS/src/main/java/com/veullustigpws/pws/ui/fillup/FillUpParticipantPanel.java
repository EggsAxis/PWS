package com.veullustigpws.pws.ui.fillup;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.assignment.data.ParticipantData;
import com.veullustigpws.pws.connection.hosting.HostingManager;
import com.veullustigpws.pws.ui.appearance.ColoredButtonUI;
import com.veullustigpws.pws.ui.components.WhiteLabel;
import com.veullustigpws.pws.utils.GUIUtils;

public class FillUpParticipantPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int borderX = 50;
	private static final int borderY = 2;

	private HostingManager hostingManager;
	
	// User data
	private ParticipantData participantData;
	
	
	public FillUpParticipantPanel(ParticipantData participantData, HostingManager hostingManager) {
		this.participantData = participantData;
		this.hostingManager = hostingManager;
		
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setMaximumSize(new Dimension(99999, 60));
		this.setBorder(BorderFactory.createEmptyBorder(borderY, borderX, borderY, borderX));
		
		initComponents();
	}
	
	private void initComponents() {
		
		// Name Label
		WhiteLabel nameLbl = new WhiteLabel(participantData.getName());
		
		// Student number label 
		WhiteLabel studentNumberLbl = new WhiteLabel(participantData.getStudentID());
		
		// Buttons
		JButton kickBtn = new JButton("Verwijder");
		kickBtn.setFocusable(false);
		kickBtn.setUI(new ColoredButtonUI(ColorPalet.RedButton, kickBtn));
		kickBtn.addActionListener(e -> {
			hostingManager.kickParticipant(participantData.getID());
		});
		
		Dimension dim = new Dimension(100, 32);
		GUIUtils.setComponentSize(kickBtn, dim);
		
		// Add together
		this.add(nameLbl);
		this.add(Box.createRigidArea(new Dimension(36, 0)));
		this.add(studentNumberLbl);
		this.add(Box.createHorizontalGlue());
		this.add(kickBtn);
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(ColorPalet.DarkBackgroundColor);
		g.fillRoundRect(10, borderY, getWidth() - 2*10, getHeight() - 2*borderY, 15, 15);
	}

}
