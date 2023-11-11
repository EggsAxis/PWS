package com.veullustigpws.pws.ui.roomoptions;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import com.veullustigpws.pws.ui.components.CustomTextField;
import com.veullustigpws.pws.ui.components.WhiteLabel;
import com.veullustigpws.pws.utils.GUIUtils;
import com.veullustigpws.pws.utils.TextFieldIntegerInputFilter;
import com.veullustigpws.pws.utils.TextFieldSizeLimiter;

public class AssignmentOptionsPanel extends JPanel {
	private static final long serialVersionUID = -1949973482540721500L;
	
	private CustomTextField assignmentNameTF;
	private JTextArea descriptionTextArea;
	private CustomTextField durationTF;
	private CustomTextField wordCountTF;
	
	
	
	public AssignmentOptionsPanel(int width) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(width, 700));
		this.setBorder(BorderFactory.createEmptyBorder(50, 30, 0, 20));
		this.setOpaque(false);
		initComponents();
		
		// DEBUG
		assignmentNameTF.setText("Test assignment");
		descriptionTextArea.setText("bla bla bla");
		durationTF.setText("60");
		wordCountTF.setText("100");
	}
	
	private void initComponents() {
		// Title
		JLabel title = new WhiteLabel("Opdracht", true);
		this.add(title);
		
		this.add(Box.createRigidArea(new Dimension(0, 60)));
		
		// Room name
		JLabel assignmentNameLbl = new WhiteLabel("Naam opdracht");
		assignmentNameTF = new CustomTextField();
		assignmentNameTF.setTransferHandler(null); // Disables copy-paste
		assignmentNameTF.setMaximumSize(new Dimension(200, 30));
		assignmentNameTF.setAlignmentX(Component.LEFT_ALIGNMENT);
		assignmentNameTF.addKeyListener(new TextFieldSizeLimiter(assignmentNameTF, 42));
		this.add(assignmentNameLbl);
		addLabelPadding();
		this.add(GUIUtils.addIndent(assignmentNameTF, RoomOptionsScreen.COMPONENT_INDENT));
		
		
		addComponentPadding();
		
		
		// Assignment description
		JLabel descriptionLbl = new WhiteLabel("Opdrachtbeschrijving");
		descriptionTextArea = new JTextArea();
		descriptionTextArea.setWrapStyleWord(true);
		descriptionTextArea.setLineWrap(true);
		JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
		descriptionScrollPane.setMaximumSize(new Dimension(360, 180));
		descriptionScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(descriptionLbl);
		addLabelPadding();
		this.add(GUIUtils.addIndent(descriptionScrollPane, RoomOptionsScreen.COMPONENT_INDENT));
		
		
		addComponentPadding();
		
		
		// Duration
		JLabel durationLbl = new WhiteLabel("Lengte (minuten)");
		durationTF = new CustomTextField();
		durationTF.setTransferHandler(null); // Disables copy-paste
		durationTF.setMaximumSize(new Dimension(40, 25));
		durationTF.setAlignmentX(Component.LEFT_ALIGNMENT);
		durationTF.addKeyListener(new TextFieldSizeLimiter(durationTF, 3));
		durationTF.addKeyListener(new TextFieldIntegerInputFilter());
		this.add(durationLbl);
		addLabelPadding();
		this.add(GUIUtils.addIndent(durationTF, RoomOptionsScreen.COMPONENT_INDENT));
		
		
		addComponentPadding();
		
		
		// Word count
		JLabel wordCountLbl = new WhiteLabel("Woordendoel");
		wordCountTF = new CustomTextField();
		wordCountTF.setTransferHandler(null); // Disables copy-paste
		wordCountTF.setMaximumSize(new Dimension(40, 25));
		wordCountTF.setAlignmentX(Component.LEFT_ALIGNMENT);
		wordCountTF.addKeyListener(new TextFieldSizeLimiter(wordCountTF, 4));
		wordCountTF.addKeyListener(new TextFieldIntegerInputFilter());
		this.add(wordCountLbl);
		addLabelPadding();
		this.add(GUIUtils.addIndent(wordCountTF, RoomOptionsScreen.COMPONENT_INDENT));
		
		
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
	
	
}
