package com.veullustigpws.pws.ui.monitor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.connection.hosting.HostingManager;
import com.veullustigpws.pws.ui.components.RoundButton;
import com.veullustigpws.pws.ui.components.RoundPanel;
import com.veullustigpws.pws.ui.components.WhiteLabel;

public class MonitorScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private HostingManager manager;
	
	
	private JLabel timeLbl;
	private JLabel codeLbl;
	private ParticipantListPanel partsPanel;
	
	public MonitorScreen(HostingManager manager) {
		this.manager = manager;
		
		this.setFocusable(true);
		this.setLayout(new BorderLayout());
		this.setBackground(ColorPalet.LightBackgroundColor);
		
		
		initComponents();
		this.repaint();
	}
	
	private void initComponents() {
		// Participants panel
		partsPanel = new ParticipantListPanel(manager);
		
		// Top menu
		JPanel topMenu = new JPanel();
		topMenu.setLayout(new BoxLayout(topMenu, BoxLayout.X_AXIS));
		topMenu.setOpaque(false);
		
		RoundPanel inner = new RoundPanel(30, RoundPanel.BOTTOM_CORNERS);
		inner.setLayout(new BoxLayout(inner, BoxLayout.X_AXIS));
		inner.setBackground(ColorPalet.DarkBackgroundColor);
		inner.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
		Dimension innerDim = new Dimension(600, 60);
		inner.setPreferredSize(innerDim);
		inner.setMaximumSize(innerDim);
		inner.setMinimumSize(innerDim);
		
		
		// Buttons
		RoundButton pauseBtn = new RoundButton(16, RoundButton.PLAY_PAUSE);
		RoundButton stopBtn = new RoundButton(16, RoundButton.STOP);
		pauseBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
		stopBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
		pauseBtn.addActionListener(e -> {
			manager.pauseAssignment();
		});
		
		// labels
		timeLbl = new WhiteLabel("");
		codeLbl = new WhiteLabel("");
		
		inner.add(pauseBtn);
		inner.add(Box.createRigidArea(new Dimension(20, 0)));
		inner.add(stopBtn);
		inner.add(Box.createHorizontalGlue());
		inner.add(timeLbl);
		inner.add(Box.createHorizontalGlue());
		inner.add(codeLbl);
		
		topMenu.add(Box.createHorizontalGlue());
		topMenu.add(inner);
		topMenu.add(Box.createHorizontalGlue());
		
		// Add together
		this.add(partsPanel, BorderLayout.CENTER);
		this.add(topMenu, BorderLayout.NORTH);
	}
	
	public void setTime(String time) {
		timeLbl.setText(time);
	}
	public void setCode(String code) {
		codeLbl.setText(code);
	}
	
	public void refreshParticipants() {
		partsPanel.refreshParticipants();
	}
	
}
