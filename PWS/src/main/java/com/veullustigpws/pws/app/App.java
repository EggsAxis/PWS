package com.veullustigpws.pws.app;

import java.util.Scanner;
import com.veullustigpws.pws.connection.Client;
import com.veullustigpws.pws.connection.Server;

public class App {
	public static void main(String[] args) {
		new UIFrame();
		
		Scanner scanner = new Scanner(System.in);
		String response;
		while (true) {
			System.out.println("Do you want to run a server or a client? [S / C]");
			response = scanner.nextLine();
			if (response.equals("S")) {
				Server server = new Server();
				server.run();
				System.out.println("Executed a new server.");
				break;
			} else if (response.equals("C")) {
				Client client = new Client();
				System.out.println("Executed a new client.");
				break;
			}
		}
	}
}
