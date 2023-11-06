package com.veullustigpws.pws.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.veullustigpws.pws.app.App;
import com.veullustigpws.pws.connection.hosting.HostingManager;
import com.veullustigpws.pws.resources.fonts.AppFonts;
import com.veullustigpws.pws.ui.login.LoginScreen;
import com.veullustigpws.pws.ui.roomoptions.RoomOptionsScreen;

public class UIFrame extends JFrame   {
	
	private static final long serialVersionUID = 1L;
	
	private HostingManager hostingManager;
	
	private StartScreen startScreen;
	private LoginScreen loginScreen;
	private RoomOptionsScreen roomOptionsScreen;
	private JComponent currentPnl;

	public UIFrame() {
		super();
		
		AppFonts.LoadFonts();
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.addWindowListener(App.Manager);
		
		this.setTitle("PWS - Milan Veul & Samuel Lustig");
		
		
		
		initComponents();
	}
	
	private void initComponents() {
		startScreen = new StartScreen();
		loginScreen = new LoginScreen();
		roomOptionsScreen = new RoomOptionsScreen();
	}
	
	public void open() {
		setStartScreen();
		
		this.setMinimumSize(new Dimension(850, 500));
		this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void notify(String message) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(null, message, message, JOptionPane.OK_OPTION);
			}
		});
	}
	
	public void setScreen(JComponent panel) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				add(panel, BorderLayout.CENTER);
				if (currentPnl != null) remove(currentPnl);
				revalidate();
				repaint();
				
				currentPnl = panel;
			}
		});
	}
	
	public void setRoomOptionsScreen() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				add(roomOptionsScreen, BorderLayout.CENTER);
				if (currentPnl != null) remove(currentPnl);
				revalidate();
				repaint();
				currentPnl = roomOptionsScreen;
			}
		});
	}
	public void setLoginScreen() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				add(loginScreen, BorderLayout.CENTER);
				if (currentPnl != null) remove(currentPnl);
				revalidate();
				repaint();
				currentPnl = loginScreen;
			}
		});
	}
	public void setStartScreen() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				add(startScreen, BorderLayout.CENTER);
				if (currentPnl != null) remove(currentPnl);
				revalidate();
				repaint();
				currentPnl = startScreen;
			}
		});
	}
	
	public LoginScreen getLoginScreen() {
		return loginScreen;
	}



}
