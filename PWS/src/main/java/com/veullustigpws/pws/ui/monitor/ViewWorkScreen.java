package com.veullustigpws.pws.ui.monitor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.assignment.data.ParticipantWorkState;
import com.veullustigpws.pws.connection.hosting.HostingManager;
import com.veullustigpws.pws.listeners.WorkStateListener;
import com.veullustigpws.pws.ui.appearance.ColoredButtonUI;
import com.veullustigpws.pws.ui.components.MenuPanel;
import com.veullustigpws.pws.ui.components.WhiteLabel;
import com.veullustigpws.pws.ui.editor.parts.DocumentEditor;
import com.veullustigpws.pws.utils.GUIUtils;

public class ViewWorkScreen extends JPanel implements WorkStateListener {
	private static final long serialVersionUID = 1L;
	
	private DocumentEditor docEditor;
	private HostingManager hostingManager;
	private JLabel nameLbl;
	
	public boolean shown = false;
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
		MenuPanel topMenu = new MenuPanel(new Dimension(400, 60), 40, MenuPanel.SCREEN_LOCATION_TOP);
		
		topMenu.getMenu().setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 50));
		
		// return button
		JButton returnBtn = new JButton("< Ga terug");
		returnBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
		GUIUtils.setComponentSize(returnBtn, new Dimension(120, 32));
		returnBtn.setUI(new ColoredButtonUI(ColorPalet.BlueButton, returnBtn));
		returnBtn.addActionListener(e -> {
			hostingManager.returnToMonitorScreen();
			shown = false;
		});
		
		// Name labek
		nameLbl = new WhiteLabel("Gerrit");
		
		topMenu.addComponent(returnBtn);
		topMenu.addGlue();
		topMenu.addComponent(nameLbl);
		
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
		this.add(topMenu, BorderLayout.NORTH);
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
		if (!shown) return;
		setParticipantWorkState(participantWorkStates.get(participantID));
	}
}
