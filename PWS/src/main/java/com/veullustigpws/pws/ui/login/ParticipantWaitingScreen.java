package com.veullustigpws.pws.ui.login;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.connection.client.ParticipantManager;
import com.veullustigpws.pws.resources.fonts.AppFonts;
import com.veullustigpws.pws.ui.appearance.ColoredButtonUI;
import com.veullustigpws.pws.ui.components.RoundPanel;
import com.veullustigpws.pws.utils.GUIUtils;

public class ParticipantWaitingScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private ParticipantManager manager;
	
	private JButton leaveBtn;
	
	public ParticipantWaitingScreen(ParticipantManager manager) {
		this.manager = manager;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBackground(ColorPalet.LightBackgroundColor);
		
		initComponents();
	}
	
	private void initComponents() {
		JPanel centerPnl = new RoundPanel(50, RoundPanel.ALL_CORNERS);
		centerPnl.setAlignmentY(Component.CENTER_ALIGNMENT);
		GUIUtils.setComponentSize(centerPnl, new Dimension(350, 200));
		centerPnl.setBackground(ColorPalet.DarkBackgroundColor);
		centerPnl.setLayout(new BoxLayout(centerPnl, BoxLayout.Y_AXIS));
		centerPnl.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
		
		
		JLabel label = new JLabel("<html><p style='text-align: center;'>Je zit in de opdracht!<br>Wacht op het begin.</p></html>", JLabel.CENTER);
		label.setForeground(ColorPalet.WhiteText);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setFont(AppFonts.DefaultFont.deriveFont(Font.PLAIN, 19));
		
		leaveBtn = new JButton("Verlaat");
		GUIUtils.setComponentSize(leaveBtn, new Dimension(120, 35));
		leaveBtn.setUI(new ColoredButtonUI(ColorPalet.RedButton, leaveBtn));
		leaveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		leaveBtn.addActionListener(e -> {
			manager.leave();
		});
		
		
		centerPnl.add(label);
		centerPnl.add(Box.createVerticalGlue());
		centerPnl.add(leaveBtn);
		
		this.add(Box.createHorizontalGlue());
		this.add(centerPnl);
		this.add(Box.createHorizontalGlue());
	}
}
