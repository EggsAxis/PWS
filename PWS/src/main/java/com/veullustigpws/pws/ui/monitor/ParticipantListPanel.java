package com.veullustigpws.pws.ui.monitor;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.assignment.data.ParticipantData;
import com.veullustigpws.pws.connection.hosting.HostingManager;
import com.veullustigpws.pws.ui.fillup.FillUpParticipantPanel;

public class ParticipantListPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final int FILL_UP = 1;
	public static final int MONITOR = 2;
	private int type;
	
	private HostingManager hostingManager;
	private JPanel partsPanel;
	
	public ParticipantListPanel(int maxWidth, HostingManager hostingManager, int type) {
		this.hostingManager = hostingManager;
		this.type = type;
		
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(20, 120, 10, 120));
		
		partsPanel = new JPanel();
		partsPanel.setLayout(new BoxLayout(partsPanel, BoxLayout.Y_AXIS));
		partsPanel.setBackground(ColorPalet.LightBackgroundColor);
		partsPanel.setMaximumSize(new Dimension(maxWidth, 9999));
		
		JScrollPane scrollPartsPanel = new JScrollPane(partsPanel);
		scrollPartsPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPartsPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPartsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.add(Box.createHorizontalGlue());
		this.add(scrollPartsPanel);
		this.add(Box.createHorizontalGlue());
		
		refreshParticipants();
	}
	
	
	public void refreshParticipants() {
		if (type == MONITOR) {
			partsPanel.removeAll();
			for (ParticipantData pd : hostingManager.getParticipants()) {
				MonitorParticipantPanel pp = new MonitorParticipantPanel(hostingManager, pd.getName(), pd.getID());
				partsPanel.add(pp);
				hostingManager.addWorkStateListener(pp);
			}
			
			this.revalidate();
			this.repaint();
			
		} else if (type == FILL_UP) {
			partsPanel.removeAll();
			for (ParticipantData pd : hostingManager.getParticipants()) {
				FillUpParticipantPanel pp = new FillUpParticipantPanel(pd, hostingManager);
				partsPanel.add(pp);
			}
			
			this.revalidate();
			this.repaint();
		}
		
	}
	
}
