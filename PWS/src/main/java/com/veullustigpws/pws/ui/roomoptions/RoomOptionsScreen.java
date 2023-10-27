package com.veullustigpws.pws.ui.roomoptions;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.veullustigpws.pws.app.App;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.assignment.AssignmentOptions;
import com.veullustigpws.pws.assignment.RoomOptionsValidator;
import com.veullustigpws.pws.ui.appearance.ColoredButtonUI;
import com.veullustigpws.pws.ui.components.RoundPanel;

public class RoomOptionsScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 800;
	public static final int LABEL_PADDING = 10;
	public static final int COMPONENT_PADDING = 30;
	
	private AssignmentOptionsPanel assignmentPnl;
	private RoomOptionsPanel roomPnl;
	
	
	public RoomOptionsScreen() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(ColorPalet.LightBackgroundColor);
		
		initComponents();
	}

	
	private void initComponents() {
		// Done & Cancel buttons
		JButton doneBtn = new JButton("Creëer");
		JButton cancelBtn = new JButton("Annuleer");
		doneBtn.setFocusable(false);
		cancelBtn.setFocusable(false);
		Dimension btnDim = new Dimension(100, 35);
		doneBtn.setPreferredSize(btnDim);
		cancelBtn.setPreferredSize(btnDim);
		doneBtn.setMinimumSize(btnDim);
		cancelBtn.setMinimumSize(btnDim);
		doneBtn.setMaximumSize(btnDim);
		cancelBtn.setMaximumSize(btnDim);
		
		doneBtn.setUI(new ColoredButtonUI(ColorPalet.GreenButton, doneBtn));
		cancelBtn.setUI(new ColoredButtonUI(ColorPalet.RedButton, cancelBtn));
		
		doneBtn.addActionListener(e -> {
			submit();
		});
		cancelBtn.addActionListener(e -> {
			App.Window.setStartScreen();
		});
		
		// Bottom button panel
		JPanel btnPnl = new JPanel();
		btnPnl.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 60));
		btnPnl.setOpaque(false);
		btnPnl.setLayout(new BoxLayout(btnPnl, BoxLayout.X_AXIS));
		btnPnl.setMaximumSize(new Dimension(9999, 200));
		btnPnl.add(Box.createHorizontalGlue());
		btnPnl.add(doneBtn);
		btnPnl.add(Box.createRigidArea(new Dimension(30, 0)));
		btnPnl.add(cancelBtn);
		
		// Option panel
		assignmentPnl = new AssignmentOptionsPanel(WIDTH/2);
		roomPnl = new RoomOptionsPanel(WIDTH/2);
		
		// Center panel
		RoundPanel centerPnl = new RoundPanel(50, RoundPanel.ALL_CORNERS);
		centerPnl.setBackground(ColorPalet.DarkBackgroundColor);
		centerPnl.setLayout(new BorderLayout());
		centerPnl.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.CENTER);
		centerPnl.add(assignmentPnl, BorderLayout.WEST);
		centerPnl.add(roomPnl, BorderLayout.EAST);
		centerPnl.add(btnPnl, BorderLayout.SOUTH);
		
		Dimension maxDim = new Dimension(WIDTH, 780);
		centerPnl.setPreferredSize(maxDim);
		centerPnl.setMaximumSize(maxDim);
		centerPnl.setMinimumSize(maxDim);
		
		// Put center panel into a scroll pane
		JScrollPane scrollPane = new JScrollPane(centerPnl);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setMaximumSize(new Dimension(WIDTH, 99999));
		scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		
		this.add(Box.createVerticalGlue());
		this.add(scrollPane);
		this.add(Box.createVerticalGlue());
	}
	
	private void submit() {
		Thread submitThread = new Thread() {
			public void run() {
				AssignmentOptions options = new AssignmentOptions();
				
				options.setAssignmentName(assignmentPnl.getAssignmentName());
				options.setAssignmentDescription(assignmentPnl.getAssignmentDescription());
				options.setAssignmentDuration(assignmentPnl.getAssignmentDuration());
				options.setWordCount(assignmentPnl.getWordCount());
				options.setSendTimeReminder(assignmentPnl.getSendTimeReminder());
				options.setTimeReminderFrequency(assignmentPnl.getTimeReminderFrequency());
				
				options.setRoomName(roomPnl.getRoomName());
				options.setPassword(roomPnl.getRoomPassword());
				options.setDetectionEnabled(roomPnl.getDetectionEnabled());
				
				boolean correctInput = RoomOptionsValidator.ValidateSubmission(options);
				
				
				if (!correctInput) {
					JOptionPane.showMessageDialog(null, "Vul alle velden in.", "Error", JOptionPane.OK_OPTION);
				} else {
					App.Manager.startRoom(options);
				}
			}
		};
		submitThread.start();
		
	}
}
