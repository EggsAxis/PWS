package com.veullustigpws.pws.app;

import java.util.Scanner;
import javax.swing.SwingUtilities;
import com.veullustigpws.pws.connection.Client;
import com.veullustigpws.pws.connection.Server;

public class App {
	
	public static boolean runServer;
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Do you want to run a server or a client? [s / c]");
			runServer = scanner.nextLine().toLowerCase().equals("s");
			if (runServer) {
				Server server = new Server();
				server.run();
				System.out.println("Executed a new server.");
				break;
			} else if (runServer) {
				Client client = new Client();
				System.out.println("Executed a new client.");
				break;
			}
		}
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UIFrame();
            }
        });
	}
}
