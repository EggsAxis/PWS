package com.veullustigpws.pws.ui.monitor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.veullustigpws.pws.app.AppConstants;
import com.veullustigpws.pws.app.UIFrame;

public class MonitorScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	private UIFrame frame;
	
	private JLabel timeLabel;
	
	public MonitorScreen(UIFrame frame) {
		this.frame = frame;
		
		this.setFocusable(true);
		this.setLayout(new BorderLayout());
		this.setBackground(AppConstants.defaultBackgroundColor);
		
		
		initComponents();
		this.repaint();
	}
	
	private void initComponents() {
		// Participants panel
		ParticipantListPanel partsPanel = new ParticipantListPanel();
		
		// Bottom menu
		JPanel bottomMenu = new JPanel();
		bottomMenu.setLayout(new BoxLayout(bottomMenu, BoxLayout.X_AXIS));
		bottomMenu.setPreferredSize(new Dimension(9999, 70));
		bottomMenu.setBackground(AppConstants.defaultBackgroundColor);
		bottomMenu.setBorder(BorderFactory.createEmptyBorder(5, 40, 10, 40));
		
		// Time label
		timeLabel = new JLabel("Resterend: 00:00");
		timeLabel.setFont(new Font("", Font.PLAIN, 20));
		
		// Buttons
		Font btnFont = new Font("", Font.BOLD, 18);
		JButton forceStopBtn = new JButton("Beëindig");
		JButton pauseBtn = new JButton("Pauzeer");
		forceStopBtn.setFocusable(false);
		pauseBtn.setFocusable(false);
		forceStopBtn.setFont(btnFont);
		pauseBtn.setFont(btnFont);
		
		
		bottomMenu.add(timeLabel);
		bottomMenu.add(Box.createHorizontalGlue());
		bottomMenu.add(pauseBtn);
		bottomMenu.add(Box.createHorizontalGlue());
		bottomMenu.add(forceStopBtn);
		
		// Add together
		this.add(partsPanel, BorderLayout.CENTER);
		this.add(bottomMenu, BorderLayout.SOUTH);
	}
	
}
