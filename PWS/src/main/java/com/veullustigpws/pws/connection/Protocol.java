package com.veullustigpws.pws.connection;

public class Protocol {
	
	public static final String ConnectChar = "~~";
	
	public static final String SendParticipantData = "pd";
	public static final String ParticipantLeaves = "pl";
	public static final String StartAssignment = "sa";
	public static final String RequestWork = "rw";
	public static final String SendRequestedWork = "sw";
	public static final String PausedAssignment = "pa";
	
	// End of assignment
	public static final String AssignmentEnded = "ea";
	public static final String FinalWork = "fw";
	public static final String ReceivedFinalWork = "rfw";
	
	public static final String SendPassword = "sp";
	public static final String IncorrectPassword = "wp";
	public static final String CorrectPassword = "cp";
	
	public static final String KickUser = "ku";
	
	
	public static String format(String command, String... args) {
		String str = command;
		for (String arg : args) {
			str += ConnectChar + arg;
		}
		return str;
	}
}
