package com.veullustigpws.pws.ui.monitor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.connection.hosting.HostingManager;
import com.veullustigpws.pws.ui.components.MenuPanel;
import com.veullustigpws.pws.ui.components.RoundButton;
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
		partsPanel = new ParticipantListPanel(1000, manager, ParticipantListPanel.MONITOR);
		
		// Top menu
		MenuPanel topMenu = new MenuPanel(new Dimension(600, 60), 30, MenuPanel.SCREEN_LOCATION_TOP);
		topMenu.getMenu().setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
		
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
		
		topMenu.addComponent(pauseBtn);
		topMenu.addRigidArea(20);
		topMenu.addComponent(stopBtn);
		topMenu.addGlue();
		topMenu.addComponent(timeLbl);
		topMenu.addGlue();
		topMenu.addComponent(codeLbl);
		
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
