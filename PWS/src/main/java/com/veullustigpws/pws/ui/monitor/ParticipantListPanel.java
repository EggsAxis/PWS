package com.veullustigpws.pws.ui.monitor;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.veullustigpws.pws.app.AppConstants;

public class ParticipantListPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<ParticipantPanel> participants;
	
	public ParticipantListPanel() {
		participants = new ArrayList<ParticipantPanel>();
		
		this.setBackground(AppConstants.defaultBackgroundColor);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(20, 120, 10, 120));
		
		
		
		JPanel partsPanel = new JPanel();
		partsPanel.setLayout(new BoxLayout(partsPanel, BoxLayout.Y_AXIS));
		
		
		JScrollPane scrollPartsPanel = new JScrollPane(partsPanel);
		scrollPartsPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPartsPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPartsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		scrollPartsPanel.setBackground(Color.pink);
		
		this.add(scrollPartsPanel);
		
		
		// FOR TESTING
		partsPanel.add(new ParticipantPanel("Peimilen Veul", 69));
		partsPanel.add(new ParticipantPanel("Boris Stokker", 0));
		partsPanel.add(new ParticipantPanel("Samwel Lusten", 420));
		
	}
	
}
