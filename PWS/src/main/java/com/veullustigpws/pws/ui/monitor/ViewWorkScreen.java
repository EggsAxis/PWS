package com.veullustigpws.pws.ui.monitor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import com.veullustigpws.pws.app.AppConstants;
import com.veullustigpws.pws.assignment.ParticipantWorkState;
import com.veullustigpws.pws.connection.hosting.HostingManager;
import com.veullustigpws.pws.listeners.WorkStateListener;
import com.veullustigpws.pws.ui.editor.parts.DocumentEditor;

public class ViewWorkScreen extends JPanel implements WorkStateListener {
	private static final long serialVersionUID = 1L;
	
	private DocumentEditor docEditor;
	private HostingManager hostingManager;
	
	public boolean shown;
	private int participantID;
	
	public ViewWorkScreen(HostingManager hostingManager) {
		this.hostingManager = hostingManager;
		
		this.setFocusable(true);
		this.setLayout(new BorderLayout());
		this.setBackground(AppConstants.defaultBackgroundColor);
		
		initComponents();
		this.repaint();
	}

	private void initComponents() {
		// Return btn
		JButton returnBtn = new JButton("< Ga terug");
		returnBtn.setMaximumSize(new Dimension(99999, 100));
		returnBtn.setFocusable(false);
		returnBtn.addActionListener(e -> {
			hostingManager.returnToMonitorScreen();
			shown = false;
		});
		
		// Document editor
		docEditor = new DocumentEditor();
		docEditor.setEditable(false);
		
		JScrollPane scrollTextPane = new JScrollPane(docEditor);
		scrollTextPane.setMaximumSize(new Dimension(1000, 9999));
		scrollTextPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollTextPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(scrollTextPane);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 120, 30, 120));
		centerPanel.setBackground(AppConstants.defaultBackgroundColor);
		
		// Add together
		this.add(returnBtn, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
	}
	
	public void setParticipantWorkState(ParticipantWorkState pws) {
		docEditor.setStyledDocument(pws.getDocument());
		participantID = pws.getParticipantData().getID();
		this.repaint();
	}

	@Override
	public void changedWorkState(HashMap<Integer, ParticipantWorkState> participantWorkStates) {
		if (!shown) return;
		setParticipantWorkState(participantWorkStates.get(participantID));
		
		
	}
}
