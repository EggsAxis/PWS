package com.veullustigpws.pws.app;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Debug {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
	
	public static void log(String msg) {
		System.out.println("[" + sdf.format(new Date()) + "] " + msg);
	}
	
	public static void error(String msg) {
		System.err.println("[" + sdf.format(new Date()) + "] [ERROR]: " + msg);
	}

}
