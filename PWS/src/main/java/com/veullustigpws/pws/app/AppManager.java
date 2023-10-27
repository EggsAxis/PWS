package com.veullustigpws.pws.app;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.SwingUtilities;
import com.veullustigpws.pws.assignment.AssignmentOptions;
import com.veullustigpws.pws.connection.client.ParticipantConnectData;
import com.veullustigpws.pws.connection.client.ParticipantManager;
import com.veullustigpws.pws.connection.hosting.HostingManager;
import com.veullustigpws.pws.exceptions.WrongConnectionDataException;

public class AppManager implements WindowListener {
	
	private HostingManager hostingManager;
	private ParticipantManager participantManager;
	
	public boolean InAssignment = false;
	
	public void startRoom(AssignmentOptions options) {
		hostingManager = new HostingManager(options);
		InAssignment = true;
		App.RunningServer = true;
	}
	
	public void connectToRoom(ParticipantConnectData participantConnectData) throws WrongConnectionDataException {
		participantManager = new ParticipantManager(participantConnectData);
	}
	public void incorrectPassword() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				App.Window.getLoginScreen().incorrectPassword();
			}
		});
	}


	@Override
	public void windowClosing(WindowEvent e) {
		if (!InAssignment) {
			System.exit(0);
		}
		if (App.RunningServer) {
			hostingManager.exitProgram();
		} else {
			participantManager.exitProgram();
		}
	}
	
	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
}
