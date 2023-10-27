package com.veullustigpws.pws.app;

import javax.swing.SwingUtilities;
import com.veullustigpws.pws.ui.UIFrame;

public class App {
	
	public static UIFrame Window;
	public static AppManager Manager;
	
	public static boolean RunningServer = false; 	// If false -> app is running client
	
	public static void main(String[] args) {
		Manager = new AppManager();
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Window = new UIFrame();
                Window.open();
            }
        });
		
		
	}
}
