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
import com.veullustigpws.pws.ui.editor.EditorScreen;
import com.veullustigpws.pws.ui.monitor.MonitorScreen;

public class UIFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private EditorScreen editorScreen;
	private MonitorScreen monitorScreen;
	

	public UIFrame() {
		super();
		
		this.setMinimumSize(new Dimension(700, 600));
		this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.setTitle("PWS - Milan Veul & Samuel Lustig");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {}
		
		initComponents();
		
		this.setVisible(true);
	}
	
	private void initComponents() {
		if (App.runServer) {
			HostingManager manager = new HostingManager();
			FillUpScreen fillUpScreen = new FillUpScreen(manager);
			manager.setFillUpScreen(fillUpScreen);
			this.setScreen(fillUpScreen);
		} else {
			ParticipantManager manager = new ParticipantManager();
			ParticipantWaitingScreen waitingScreen = new ParticipantWaitingScreen(manager);
			manager.setWaitingScreen(waitingScreen);
			
			manager.startClient(new ParticipantData("Milan Veul", "mv25949"));
			this.setScreen(waitingScreen);
		}
		
	}
	
	public void setScreen(JPanel panel) {
		this.getContentPane().removeAll();
		this.add(panel, BorderLayout.CENTER);
		this.repaint();
	}
}
