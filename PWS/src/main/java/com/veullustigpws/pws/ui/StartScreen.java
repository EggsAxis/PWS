package com.veullustigpws.pws.ui;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.veullustigpws.pws.app.App;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.ui.appearance.ColoredButtonUI;
import com.veullustigpws.pws.ui.components.RoundPanel;
import com.veullustigpws.pws.ui.components.WhiteLabel;
import com.veullustigpws.pws.utils.GUIUtils;

public class StartScreen extends JPanel {
	private static final long serialVersionUID = 7479056775672105159L;
	
	public StartScreen() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(ColorPalet.LightBackgroundColor);
		
		initComponents();
	}

	private void initComponents() {
		Dimension btnDim = new Dimension(220, 40);
		// join button
		JButton joinBtn = new JButton("Betreed room");
		joinBtn.setUI(new ColoredButtonUI(ColorPalet.GreenButton, joinBtn));
		GUIUtils.setComponentSize(joinBtn, btnDim);
		joinBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		joinBtn.addActionListener(e -> {
			App.Window.setLoginScreen();
		});
		// create button
		JButton createBtn = new JButton("Creëer room");
		createBtn.setUI(new ColoredButtonUI(ColorPalet.BlueButton, createBtn));
		GUIUtils.setComponentSize(createBtn, btnDim);
		createBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		createBtn.addActionListener(e -> {
			App.Window.setRoomOptionsScreen();
		});
		
		// label
		JLabel label = new WhiteLabel("of");
		
		
		// Center Panel
		JPanel centerPnl = new RoundPanel(90, RoundPanel.ALL_CORNERS);
		centerPnl.setBackground(ColorPalet.DarkBackgroundColor);
		centerPnl.setLayout(new BoxLayout(centerPnl, BoxLayout.Y_AXIS));
		GUIUtils.setComponentSize(centerPnl, new Dimension(400, 260));
		centerPnl.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPnl.setBorder(BorderFactory.createEmptyBorder(70, 0, 70, 0));
		
		centerPnl.add(joinBtn);
		centerPnl.add(Box.createRigidArea(new Dimension(0, 12)));
		centerPnl.add(label);
		centerPnl.add(Box.createRigidArea(new Dimension(0, 12)));
		centerPnl.add(createBtn);
		
		this.add(Box.createVerticalGlue());
		this.add(centerPnl);
		this.add(Box.createVerticalGlue());
	}
}
