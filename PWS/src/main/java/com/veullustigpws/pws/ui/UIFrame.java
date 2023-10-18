package com.veullustigpws.pws.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.veullustigpws.pws.app.App;
import com.veullustigpws.pws.assignment.AssignmentOptions;
import com.veullustigpws.pws.connection.client.ParticipantConnectData;
import com.veullustigpws.pws.connection.client.ParticipantManager;
import com.veullustigpws.pws.connection.hosting.HostingManager;
import com.veullustigpws.pws.exceptions.WrongConnectionDataException;
import com.veullustigpws.pws.resources.fonts.AppFonts;
import com.veullustigpws.pws.ui.editor.EditorScreen;
import com.veullustigpws.pws.ui.login.LoginScreen;
import com.veullustigpws.pws.ui.roomoptions.RoomOptionsScreen;

public class UIFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private HostingManager hostingManager;
	private ParticipantManager participantManager;
	
	private RoomOptionsScreen roomOptionsScreen;
	private LoginScreen loginScreen;
	private JPanel currentPnl;

	public UIFrame() {
		super();
		
		AppFonts.LoadFonts();
		
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
			roomOptionsScreen = new RoomOptionsScreen();
		} else {
			loginScreen = new LoginScreen();
		}
	}
	
	public void open() {
		if (App.runServer) {
			setScreen(roomOptionsScreen);
		}
		else {
//			setScreen(loginScreen);
			setScreen(new EditorScreen(null));
			
		}
		this.setMinimumSize(new Dimension(800, 500));
		this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setLocationRelativeTo(null);
		
		
		this.setVisible(true);
	}
	
	public void startRoom(AssignmentOptions options) {
		hostingManager = new HostingManager(options);
		hostingManager.openFillUpScreen();
	}
	
	public void connectToRoom(ParticipantConnectData participantConnectData, LoginScreen loginScreen) throws WrongConnectionDataException {
		participantManager = new ParticipantManager(participantConnectData, loginScreen);
	}
	
	public void setScreen(JPanel panel) {
		this.add(panel, BorderLayout.CENTER);
		if (currentPnl != null) this.remove(currentPnl);
		this.revalidate();
		this.repaint();
		
		this.currentPnl = panel;
	}
}
