package com.veullustigpws.pws.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.veullustigpws.pws.app.App;
import com.veullustigpws.pws.assignment.ParticipantData;
import com.veullustigpws.pws.connection.client.ParticipantManager;
import com.veullustigpws.pws.connection.hosting.HostingManager;

public class UIFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private HostingManager hostingManager;
	private ParticipantManager participantManager;
	

	public UIFrame() {
		super();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.setTitle("PWS - Milan Veul & Samuel Lustig");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {}
		
		initComponents();
	}
	
	private void initComponents() {
		if (App.runServer) {
			hostingManager = new HostingManager();
			
		} else {
			participantManager = new ParticipantManager();
			participantManager.startClient(new ParticipantData("Milan Veul", "mv25949"));
		}
	}
	
	public void open() {
		if (App.runServer) {
			hostingManager.openFillUpScreen();
		}
		else {
			participantManager.openWaitingScreen();
		}
		this.setMinimumSize(new Dimension(700, 600));
		this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
	}
	
	public void setScreen(JPanel panel) {
		this.getContentPane().removeAll();
		this.add(panel, BorderLayout.CENTER);
		this.pack();
		this.repaint();
	}
}
