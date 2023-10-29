package com.veullustigpws.pws.ui.roomoptions;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.veullustigpws.pws.ui.components.CheckSlider;
import com.veullustigpws.pws.ui.components.WhiteLabel;
import com.veullustigpws.pws.utils.TextFieldSizeLimiter;

public class RoomOptionsPanel extends JPanel {
	private static final long serialVersionUID = -5178622122847540349L;
	
	private JTextField roomNameTF;
	private JTextField passwordTF;
	private CheckSlider fraudDetectionSlider;
	private CheckSlider timerReminderSlider;
	private JComboBox<Integer> reminderFreqCB;
	
	
	public RoomOptionsPanel(int width) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(width, 700));
		this.setBorder(BorderFactory.createEmptyBorder(50, 20, 0, 20));
		this.setOpaque(false);
		initComponents();
		
		
		// DEBUG
		roomNameTF.setText("Test room");
		passwordTF.setText("password");
	}

	private void initComponents() {
		// Title
		JLabel title = new WhiteLabel("Digitale Omgeving", true);
		this.add(title);
		
		this.add(Box.createRigidArea(new Dimension(0, 60)));
		
		// Room name
		WhiteLabel roomNameLbl = new WhiteLabel("Omgevingsnaam");
		roomNameTF = new JTextField();
		roomNameTF.setMaximumSize(new Dimension(200, 30));
		roomNameTF.setAlignmentX(Component.LEFT_ALIGNMENT);
		roomNameTF.addKeyListener(new TextFieldSizeLimiter(roomNameTF, 42));
		this.add(roomNameLbl);
		addLabelPadding();
		this.add(roomNameTF);
		
		
		addComponentPadding();
		
		
		// Password
		JLabel passwordLbl = new WhiteLabel("Wachtwoord");
		passwordTF = new JTextField();
		passwordTF.setMaximumSize(new Dimension(200, 30));
		passwordTF.setAlignmentX(Component.LEFT_ALIGNMENT);
		passwordTF.addKeyListener(new TextFieldSizeLimiter(passwordTF, 42));
		this.add(passwordLbl);
		addLabelPadding();
		this.add(passwordTF);
		
		
		addComponentPadding();
		
		
		// Fraud detection
		JLabel fraudDetectionLbl = new WhiteLabel("Fraudedetectie");
		fraudDetectionSlider = new CheckSlider();
		fraudDetectionSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(fraudDetectionLbl);
		addLabelPadding();
		this.add(fraudDetectionSlider);
		
		addComponentPadding();
		
		// Time reminder
		JLabel timeReminderLabel = new WhiteLabel("Tijdsherinnering");
		timerReminderSlider = new CheckSlider();
		timerReminderSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel freqLbl = new WhiteLabel("Frequentie");
		Integer[] options = {5, 10, 15, 30};
		reminderFreqCB = new JComboBox<Integer>(options);
		reminderFreqCB.setMaximumSize(new Dimension(40, 25));
		reminderFreqCB.setAlignmentX(Component.LEFT_ALIGNMENT);
		reminderFreqCB.setFocusable(false);
		this.add(timeReminderLabel);
		addLabelPadding();
		this.add(timerReminderSlider);
		addComponentPadding();
		this.add(freqLbl);
		addLabelPadding();
		this.add(reminderFreqCB);
	}
	
	
	private void addComponentPadding() {
		this.add(Box.createRigidArea(new Dimension(0, RoomOptionsScreen.COMPONENT_PADDING)));
	}
	private void addLabelPadding() {
		this.add(Box.createRigidArea(new Dimension(0, RoomOptionsScreen.LABEL_PADDING)));
	}
	
	// Getters
	public String getRoomName() {
		return roomNameTF.getText();
	}
	public String getRoomPassword() {
		return passwordTF.getText();
	}
	public boolean getDetectionEnabled() {
		return fraudDetectionSlider.isSelected();
	}
	public boolean getSendTimeReminder() {
		return timerReminderSlider.isSelected();
	}
	public int getTimeReminderFrequency() {
		return (Integer) reminderFreqCB.getSelectedItem();
	}
}
