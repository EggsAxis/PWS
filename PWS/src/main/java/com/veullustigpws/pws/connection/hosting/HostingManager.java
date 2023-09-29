package com.veullustigpws.pws.connection.hosting;

import com.veullustigpws.pws.ui.FillUpScreen;
import com.veullustigpws.pws.ui.monitor.MonitorScreen;

public class HostingManager {
	
	private MonitorScreen monitorScreen;
	private FillUpScreen fillUpScreen;
	private Server server;
	private int portNumber;
	
	
	public HostingManager() {
		server = new Server();
		portNumber = server.getPortNumber();
	}
	
	public void setMonitorScreen(MonitorScreen monitorScreen) {
		this.monitorScreen = monitorScreen;
	}
	public void setFillUpScreen(FillUpScreen fillUpScreen) {
		this.fillUpScreen = fillUpScreen;
	}
	
	
	
	
}
