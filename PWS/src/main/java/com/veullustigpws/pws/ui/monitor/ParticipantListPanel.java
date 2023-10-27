package com.veullustigpws.pws.ui.monitor;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.assignment.ParticipantData;
import com.veullustigpws.pws.connection.hosting.HostingManager;

public class ParticipantListPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private HostingManager hostingManager;
	private JPanel partsPanel;
	
	public ParticipantListPanel(HostingManager hostingManager) {
		this.hostingManager = hostingManager;
		
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(20, 120, 10, 120));
		
		partsPanel = new JPanel();
		partsPanel.setLayout(new BoxLayout(partsPanel, BoxLayout.Y_AXIS));
		partsPanel.setOpaque(false);
		
		JScrollPane scrollPartsPanel = new JScrollPane(partsPanel);
		scrollPartsPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPartsPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPartsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		scrollPartsPanel.setBackground(ColorPalet.LightBackgroundColor);
		
		this.add(scrollPartsPanel);
		
		refreshParticipants();
	}
	
	public void refreshParticipants() {
		partsPanel.removeAll();
		for (ParticipantData pd : hostingManager.getParticipants()) {
			ParticipantPanel pp = new ParticipantPanel(hostingManager, pd.getName(), pd.getID());
			partsPanel.add(pp);
			hostingManager.addWorkStateListener(pp);
		}
	}
	
}
