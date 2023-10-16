package com.veullustigpws.pws.utils;

import com.veullustigpws.pws.connection.ConnectData;
import com.veullustigpws.pws.exceptions.WrongConnectionDataException;

public class JoinCodeGenerator {
	
	// <Port,ip[2],ip[3]>
	public static String IPToCode(ConnectData data) {
		int port = data.getPort();
		String ip = data.getIp();
		
		String[] ipParts = new String[4];
		
		// Convert ip to array of numbers
		int currentNumber = 0;
		String current = "";
		for (int i = 0; i < ip.length(); i++) {
			if (ip.charAt(i) == '.') {
				ipParts[currentNumber] = current;
				currentNumber++;
				current = "";
				continue;
			}
			current += ip.charAt(i);
		}
		ipParts[currentNumber] = current;
		
		// Make sure that everything is the right length
		while (ipParts[2].length() < 3) ipParts[2] = "0" + ipParts[2];
		while (ipParts[3].length() < 3) ipParts[3] = "0" + ipParts[3];
		
		// Generate code
		String code = Integer.toString(port);
		while (code.length() < 5) code = "0" + code;
		code += ipParts[2] + ipParts[3];
		
		return code;
	}
	
	public static ConnectData codeToIP(String code, String localIP) throws WrongConnectionDataException {
		if (code.length() != 11) throw new WrongConnectionDataException(WrongConnectionDataException.WRONG_CODE_FORMAT);
		
		int port = Integer.parseInt(code.substring(0, 5));
		String ip = "";
		
		int i, dotCount = 0;
		for (i = 0; i < localIP.length(); i++) {
			if (localIP.charAt(i) == '.') dotCount++;
			if (dotCount == 2) break;
		}
		ip += localIP.substring(0, i+1);
		ip += Integer.parseInt(code.substring(5, 8)) + "." + Integer.parseInt(code.substring(8, 11));
		
		return new ConnectData(ip, port);
	}
	
	
}
