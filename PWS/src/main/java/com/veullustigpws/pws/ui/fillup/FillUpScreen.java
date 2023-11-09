package com.veullustigpws.pws.ui.fillup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.connection.hosting.HostingManager;
import com.veullustigpws.pws.ui.appearance.ColoredButtonUI;
import com.veullustigpws.pws.ui.components.MenuPanel;
import com.veullustigpws.pws.ui.components.WhiteLabel;
import com.veullustigpws.pws.ui.monitor.ParticipantListPanel;
import com.veullustigpws.pws.utils.GUIUtils;

public class FillUpScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private HostingManager manager;
	
	private JLabel codeLbl;
	private JLabel partCountLbl;
	private ParticipantListPanel partsListPnl;
	
	public FillUpScreen(HostingManager manager) {
		this.manager = manager;
		
		this.setLayout(new BorderLayout());
		this.setBackground(ColorPalet.LightBackgroundColor);
		
		initComponents();
	}
	
	private void initComponents() {
		// Top menu
		MenuPanel topMenu = new MenuPanel(new Dimension(350, 60), 40, MenuPanel.SCREEN_LOCATION_TOP);
		topMenu.getMenu().setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
		
		// CodeLabel
		codeLbl = new WhiteLabel("", 30);
		
		topMenu.addGlue();
		topMenu.addComponent(codeLbl);
		topMenu.addGlue();
		
		// Bottom menu
		MenuPanel bottomMenu = new MenuPanel(new Dimension(500, 60), 40, MenuPanel.SCREEN_LOCATION_BOTTOM);
		bottomMenu.getMenu().setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
		
		// Start assignment button
		JButton startBtn = new JButton("Start");
		GUIUtils.setComponentSize(startBtn, new Dimension(100, 30));
		startBtn.setUI(new ColoredButtonUI(ColorPalet.GreenButton, startBtn));
		startBtn.addActionListener(e -> {
			manager.startAssignment();
		});
		// Cancel assignment button
		JButton cancelBtn = new JButton("Cancel");
		GUIUtils.setComponentSize(cancelBtn, new Dimension(100, 30));
		cancelBtn.setUI(new ColoredButtonUI(ColorPalet.RedButton, cancelBtn));
		cancelBtn.addActionListener(e -> {
			manager.closeRoom();
		});
		// Participants label
		partCountLbl = new WhiteLabel("0 deelnemers");
		
		bottomMenu.addComponent(cancelBtn);
		bottomMenu.addGlue();
		bottomMenu.addComponent(partCountLbl);
		bottomMenu.addGlue();
		bottomMenu.addComponent(startBtn);
		
		// Parts list panel
		partsListPnl = new ParticipantListPanel(700, manager, ParticipantListPanel.FILL_UP);
		
		this.add(partsListPnl, BorderLayout.CENTER);
		this.add(topMenu, BorderLayout.NORTH);
		this.add(bottomMenu, BorderLayout.SOUTH);
	}
	
	
	public void setServerCode(String code) {
		codeLbl.setText(code);
	}
	
	public void refreshParticipants() {
		int participantsCnt = manager.getParticipants().size();
		partCountLbl.setText(participantsCnt + " deelnemers");
		
		partsListPnl.refreshParticipants();
		this.repaint();
	}
}
