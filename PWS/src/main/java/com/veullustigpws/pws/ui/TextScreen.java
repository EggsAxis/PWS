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

public class TextScreen extends JPanel {
	private static final long serialVersionUID = 7772887531960513484L;
	
	private JLabel label;
	
	public TextScreen() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(ColorPalet.LightBackgroundColor);
		
		initComponents();
	}

	private void initComponents() {
		RoundPanel centerPnl = new RoundPanel(90, RoundPanel.ALL_CORNERS);
		centerPnl.setBackground(ColorPalet.DarkBackgroundColor);
		centerPnl.setLayout(new BoxLayout(centerPnl, BoxLayout.Y_AXIS));
		GUIUtils.setComponentSize(centerPnl, new Dimension(400, 230));
		centerPnl.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPnl.setBorder(BorderFactory.createEmptyBorder(60, 0, 40, 0));
		
		// Label
		label = new WhiteLabel("", 17);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Button
		JButton btn = new JButton("Terug naar startscherm");
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.setUI(new ColoredButtonUI(ColorPalet.BlueButton, btn));
		GUIUtils.setComponentSize(btn, new Dimension(200, 50));
		btn.addActionListener(e -> {
			App.Window.setStartScreen();
		});
		
		centerPnl.add(label);
		centerPnl.add(Box.createVerticalGlue());
		centerPnl.add(btn);
		
		
		this.add(Box.createVerticalGlue());
		this.add(centerPnl);
		this.add(Box.createVerticalGlue());
	}
	
	public void setText(String text) {
		label.setText(text);
	}
	
}
