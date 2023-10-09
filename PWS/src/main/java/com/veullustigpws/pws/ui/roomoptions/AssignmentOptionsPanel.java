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
import com.veullustigpws.pws.utils.TextFieldIntegerInputFilter;
import com.veullustigpws.pws.utils.TextFieldSizeLimiter;

public class AssignmentOptionsPanel extends JPanel {
	private static final long serialVersionUID = -1949973482540721500L;
	
	public AssignmentOptionsPanel(int width) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(width, 800));
		this.setBorder(BorderFactory.createEmptyBorder(50, 20, 0, 20));
//		this.setBackground(AppConstants.defaultBackgroundColor);
		
		initComponents();
	}
	
	private void initComponents() {
		// Title
		JLabel title = new JLabel("Opdracht");
		this.add(title);
		
		this.add(Box.createRigidArea(new Dimension(0, 60)));
		
		// Room name
		JLabel assignmentNameLbl = new JLabel("Naam opdracht");
		JTextField assignmentNameTF = new JTextField();
		assignmentNameTF.setMaximumSize(new Dimension(200, 30));
		assignmentNameTF.setAlignmentX(Component.LEFT_ALIGNMENT);
		assignmentNameTF.addKeyListener(new TextFieldSizeLimiter(assignmentNameTF, 16));
		this.add(assignmentNameLbl);
		addLabelPadding();
		this.add(assignmentNameTF);
		
		
		addComponentPadding();
		
		
		// Assignment description
		JLabel descriptionLbl = new JLabel("Opdrachtbeschrijving");
		JTextArea descriptionTextArea = new JTextArea();
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
		JLabel durationLbl = new JLabel("Lengte (minuten)");
		JTextField durationTF = new JTextField();
		durationTF.setMaximumSize(new Dimension(40, 25));
		durationTF.setAlignmentX(Component.LEFT_ALIGNMENT);
		durationTF.addKeyListener(new TextFieldSizeLimiter(durationTF, 3));
		durationTF.addKeyListener(new TextFieldIntegerInputFilter());
		this.add(durationLbl);
		addLabelPadding();
		this.add(durationTF);
		
		
		addComponentPadding();
		
		
		// Word count
		JLabel wordCountLbl = new JLabel("Woordendoel");
		JTextField wordCountTF = new JTextField();
		wordCountTF.setMaximumSize(new Dimension(40, 25));
		wordCountTF.setAlignmentX(Component.LEFT_ALIGNMENT);
		wordCountTF.addKeyListener(new TextFieldSizeLimiter(wordCountTF, 4));
		wordCountTF.addKeyListener(new TextFieldIntegerInputFilter());
		this.add(wordCountLbl);
		addLabelPadding();
		this.add(wordCountTF);
		
		
		addComponentPadding();
		
		
		// Time reminder
		JLabel timeReminderLabel = new JLabel("Tijdsherinnering");
		CheckSlider timerReminderSlider = new CheckSlider();
		timerReminderSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel freqLbl = new JLabel("Frequentie");
		Integer[] options = {5, 10, 15, 30};
		JComboBox<Integer> freqCB = new JComboBox<Integer>(options);
		freqCB.setMaximumSize(new Dimension(40, 25));
		freqCB.setAlignmentX(Component.LEFT_ALIGNMENT);
		freqCB.setFocusable(false);
		this.add(timeReminderLabel);
		addLabelPadding();
		this.add(timerReminderSlider);
		addLabelPadding();
		this.add(freqLbl);
		addLabelPadding();
		this.add(freqCB);
		
		
		
	}
	
	private void addComponentPadding() {
		this.add(Box.createRigidArea(new Dimension(0, RoomOptionsScreen.COMPONENT_PADDING)));
	}
	private void addLabelPadding() {
		this.add(Box.createRigidArea(new Dimension(0, RoomOptionsScreen.LABEL_PADDING)));
	}
	
}
