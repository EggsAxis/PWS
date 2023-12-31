package com.veullustigpws.pws.app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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
            	try {
        			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        			e.printStackTrace();
        		}
            	
                Window = new UIFrame();
                Window.open();
            }
        });
		
		
	}
}
