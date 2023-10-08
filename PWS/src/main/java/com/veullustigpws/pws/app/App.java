package com.veullustigpws.pws.app;

import java.util.Scanner;
import javax.swing.SwingUtilities;
import com.veullustigpws.pws.ui.UIFrame;

public class App {
	
	public static boolean runServer;
	public static UIFrame Window;
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to run a server or a client? [s / c]");
		runServer = scanner.nextLine().toLowerCase().equals("s");
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Window = new UIFrame();
                Window.open();
            }
        });
	}
}
