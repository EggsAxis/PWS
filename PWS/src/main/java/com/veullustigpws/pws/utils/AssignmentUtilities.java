package com.veullustigpws.pws.utils;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class AssignmentUtilities {
	
	private static int stringWordCount(String str) {
		return str.split("\\s+").length;
	}
	
	public static int getWordCount(StyledDocument doc) {
		// Get word count
		String txt = "";
		try {
			txt = doc.getText(0, doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		return stringWordCount(txt);
	}
	
	public static String getRemainingTime(long start, int duration) {
		long currentTime = System.currentTimeMillis();
		long timePassed = (currentTime - start)/1000; // In seconds
		long timeRemaining = duration * 60 - timePassed;
		
		String hours = (timeRemaining/60/60) + "";
		String minutes = (timeRemaining/60) + "";
		String seconds = (timeRemaining%60) + "";
		
		if (hours.length() < 2) 	hours = "0" + hours;
		if (minutes.length() < 2) 	minutes = "0" + minutes;
		if (seconds.length() < 2) 	seconds = "0" + seconds;
		
		String timeStr = hours + ":" + minutes + ":" + seconds;
		return timeStr;
	}
}
