package com.veullustigpws.pws.ui.monitor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.assignment.ParticipantWorkState;
import com.veullustigpws.pws.connection.hosting.HostingManager;
import com.veullustigpws.pws.listeners.WorkStateListener;
import com.veullustigpws.pws.ui.appearance.ColoredButtonUI;
import com.veullustigpws.pws.ui.components.RoundPanel;
import com.veullustigpws.pws.ui.components.WhiteLabel;
import com.veullustigpws.pws.ui.editor.parts.DocumentEditor;
import com.veullustigpws.pws.utils.GUIUtils;

public class ViewWorkScreen extends JPanel implements WorkStateListener {
	private static final long serialVersionUID = 1L;
	
	private DocumentEditor docEditor;
	private HostingManager hostingManager;
	private JLabel nameLbl;
	
	private int participantID;
	
	public ViewWorkScreen(HostingManager hostingManager) {
		this.hostingManager = hostingManager;
		
		this.setFocusable(true);
		this.setLayout(new BorderLayout());
		this.setBackground(ColorPalet.LightBackgroundColor);
		
		initComponents();
		this.repaint();
	}

	private void initComponents() {
		// Top menu
		JPanel topPnl = new JPanel();
		
		topPnl.setOpaque(false);
		topPnl.setLayout(new BoxLayout(topPnl, BoxLayout.X_AXIS));
		
		JPanel innerTop = new RoundPanel(40, RoundPanel.BOTTOM_CORNERS);
		GUIUtils.setComponentSize(innerTop, new Dimension(400, 60));
		innerTop.setBackground(ColorPalet.DarkBackgroundColor);
		innerTop.setLayout(new BoxLayout(innerTop, BoxLayout.X_AXIS));
		innerTop.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 50));
		
		// return button
		JButton returnBtn = new JButton("< Ga terug");
		returnBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
		GUIUtils.setComponentSize(returnBtn, new Dimension(120, 32));
		returnBtn.setUI(new ColoredButtonUI(ColorPalet.BlueButton));
		returnBtn.addActionListener(e -> {
			hostingManager.returnToMonitorScreen();
		});
		
		// Name labek
		nameLbl = new WhiteLabel("Gerrit");
		
		innerTop.add(returnBtn);
		innerTop.add(Box.createHorizontalGlue());
		innerTop.add(nameLbl);
		topPnl.add(Box.createHorizontalGlue());
		topPnl.add(innerTop);
		topPnl.add(Box.createHorizontalGlue());
		
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
		centerPanel.setBackground(ColorPalet.LightBackgroundColor);
		
		// Add together
		this.add(topPnl, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
	}
	
	public void setParticipantWorkState(ParticipantWorkState pws) {
		docEditor.setStyledDocument(pws.getDocument());
		participantID = pws.getParticipantData().getID();
		
		String participantName = hostingManager.getParticipantDataByID(participantID).getName();
		nameLbl.setText(participantName);
		this.repaint();
	}

	@Override
	public void changedWorkState(HashMap<Integer, ParticipantWorkState> participantWorkStates) {
		setParticipantWorkState(participantWorkStates.get(participantID));
	}
}
