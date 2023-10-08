package com.veullustigpws.pws.listeners;

import java.util.HashMap;
import com.veullustigpws.pws.assignment.ParticipantWorkState;

public interface WorkStateListener {
	
	public void changedWorkState(HashMap<Integer, ParticipantWorkState> participantWorkStates);
}
