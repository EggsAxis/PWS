package com.veullustigpws.pws.assignment;

import com.veullustigpws.pws.assignment.data.AssignmentOptions;

public class RoomOptionsValidator {
	public static boolean ValidateSubmission(AssignmentOptions options) {
		if (options.getAssignmentName().isEmpty()) return false;
		if (options.getAssignmentDescription().isEmpty()) return false;
		if (options.getAssignmentDuration() <= 0) return false;
		if (options.getWordCount() <= 0) return false;
		if (options.getRoomName().isEmpty()) return false;
		if (options.getPassword().isEmpty()) return false;
		
		return true;
	}
}
