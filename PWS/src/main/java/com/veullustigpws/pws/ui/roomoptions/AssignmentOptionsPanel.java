package com.veullustigpws.pws.ui.roomoptions;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.resources.fonts.AppFonts;
import com.veullustigpws.pws.ui.appearance.BigTextLabelUI;
import com.veullustigpws.pws.ui.appearance.CheckSlider;
import com.veullustigpws.pws.ui.appearance.DefaultLabelUI;
import com.veullustigpws.pws.utils.TextFieldIntegerInputFilter;
import com.veullustigpws.pws.utils.TextFieldSizeLimiter;

public class AssignmentOptionsPanel extends JPanel {
	private static final long serialVersionUID = -1949973482540721500L;
	
	private JTextField assignmentNameTF;
	private JTextArea descriptionTextArea;
	private JTextField durationTF;
	private JTextField wordCountTF;
	private CheckSlider timerReminderSlider;
	private JComboBox<Integer> reminderFreqCB;
	
	public AssignmentOptionsPanel(int width) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(width, 700));
		this.setBorder(BorderFactory.createEmptyBorder(50, 20, 0, 20));
		this.setBackground(ColorPalet.DefaultBackgroundColor); //background color
		initComponents();
		
		// DEBUG
		assignmentNameTF.setText("Test assignment");
		descriptionTextArea.setText("bla bla bla");
		durationTF.setText("60");
		wordCountTF.setText("100");
	}
	
	private void initComponents() {
		// Title
		JLabel title = new BigTextLabelUI("Opdracht");
		title.setForeground(ColorPalet.DefaultWhiteColor);
		this.add(title);
		
		this.add(Box.createRigidArea(new Dimension(0, 60)));
		
		// Room name
		JLabel assignmentNameLbl = new DefaultLabelUI("Naam opdracht");
		assignmentNameLbl.setForeground(ColorPalet.DefaultWhiteColor);
		assignmentNameTF = new JTextField();
		assignmentNameTF.setTransferHandler(null); // Disables copy-paste
		assignmentNameTF.setMaximumSize(new Dimension(200, 30));
		assignmentNameTF.setAlignmentX(Component.LEFT_ALIGNMENT);
		assignmentNameTF.addKeyListener(new TextFieldSizeLimiter(assignmentNameTF, 42));
		this.add(assignmentNameLbl);
		addLabelPadding();
		this.add(assignmentNameTF);
		
		
		addComponentPadding();
		
		
		// Assignment description
		JLabel descriptionLbl = new DefaultLabelUI("Opdrachtbeschrijving");
		descriptionLbl.setForeground(ColorPalet.DefaultWhiteColor);
		descriptionTextArea = new JTextArea();
		descriptionTextArea.setWrapStyleWord(true);
		descriptionTextArea.setLineWrap(true);
		JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
		descriptionScrollPane.setMaximumSize(new Dimension(360, 180));
		descriptionScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(descriptionLbl);
		addLabelPadding();
		this.add(descriptionScrollPane);
		
		
		addComponentPadding();
		
		
		// Duration
		JLabel durationLbl = new DefaultLabelUI("Lengte (minuten)");
		durationLbl.setForeground(ColorPalet.DefaultWhiteColor);
		durationTF = new JTextField();
		durationTF.setTransferHandler(null); // Disables copy-paste
		durationTF.setMaximumSize(new Dimension(40, 25));
		durationTF.setAlignmentX(Component.LEFT_ALIGNMENT);
		durationTF.addKeyListener(new TextFieldSizeLimiter(durationTF, 3));
		durationTF.addKeyListener(new TextFieldIntegerInputFilter());
		this.add(durationLbl);
		addLabelPadding();
		this.add(durationTF);
		
		
		addComponentPadding();
		
		
		// Word count
		JLabel wordCountLbl = new DefaultLabelUI("Woordendoel");
		wordCountLbl.setForeground(ColorPalet.DefaultWhiteColor);
		wordCountTF = new JTextField();
		wordCountTF.setTransferHandler(null); // Disables copy-paste
		wordCountTF.setMaximumSize(new Dimension(40, 25));
		wordCountTF.setAlignmentX(Component.LEFT_ALIGNMENT);
		wordCountTF.addKeyListener(new TextFieldSizeLimiter(wordCountTF, 4));
		wordCountTF.addKeyListener(new TextFieldIntegerInputFilter());
		this.add(wordCountLbl);
		addLabelPadding();
		this.add(wordCountTF);
		
		
		addComponentPadding();
		
		
		// Time reminder
		JLabel timeReminderLabel = new DefaultLabelUI("Tijdsherinnering");
		timeReminderLabel.setForeground(ColorPalet.DefaultWhiteColor);
		timerReminderSlider = new CheckSlider();
		timerReminderSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel freqLbl = new DefaultLabelUI("Frequentie");
		freqLbl.setForeground(ColorPalet.WhiteText);
		Integer[] options = {5, 10, 15, 30};
		reminderFreqCB = new JComboBox<Integer>(options);
		reminderFreqCB.setMaximumSize(new Dimension(40, 25));
		reminderFreqCB.setAlignmentX(Component.LEFT_ALIGNMENT);
		reminderFreqCB.setFocusable(false);
		this.add(timeReminderLabel);
		addLabelPadding();
		this.add(timerReminderSlider);
		addLabelPadding();
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
	public String getAssignmentName() {
		return assignmentNameTF.getText();
	}
	public String getAssignmentDescription() {
		return descriptionTextArea.getText();
	}
	public int getAssignmentDuration() {
		if (durationTF.getText().isEmpty()) return 0;
		return Integer.parseInt(durationTF.getText());
	}
	public int getWordCount() {
		if (wordCountTF.getText().isEmpty()) return 0;
		return Integer.parseInt(wordCountTF.getText());
	}
	public boolean getSendTimeReminder() {
		return timerReminderSlider.isSelected();
	}
	public int getTimeReminderFrequency() {
		return (Integer) reminderFreqCB.getSelectedItem();
	}
	
}
