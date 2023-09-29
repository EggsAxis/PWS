package com.veullustigpws.pws.ui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.veullustigpws.pws.app.App;
import com.veullustigpws.pws.connection.hosting.HostingManager;
import com.veullustigpws.pws.ui.monitor.MonitorScreen;

public class FillUpScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private HostingManager manager;
	
	public FillUpScreen(HostingManager manager) {
		this.setLayout(new BorderLayout());
		
		initComponents();
	}
	
	private void initComponents() {
		JButton startButton = new JButton();
		startButton.setText("Start Assignment");
		startButton.addActionListener(e -> {
			System.out.println("Starting asignment.");
			App.Window.setScreen(new MonitorScreen(manager));
		});
		
		
		
		
		this.add(startButton, BorderLayout.CENTER);
	}
}
