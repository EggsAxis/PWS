package com.veullustigpws.pws.assignment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import com.veullustigpws.pws.app.Debug;
import com.veullustigpws.pws.assignment.data.ParticipantData;
import com.veullustigpws.pws.assignment.data.ParticipantWorkState;

public class ExportAssignmentManager {
	
	private ArrayList<File> rtfFiles;
	private File zippedFile;
	
	public ExportAssignmentManager() {
		loadScreens();
	}

	private void loadScreens() {
	}
	
	
	public void generateRTFDocuments(File dir, HashMap<Integer, ParticipantWorkState> participantWorkStates) {
		rtfFiles = new ArrayList<File>();
		
		for (Map.Entry<Integer, ParticipantWorkState> set : participantWorkStates.entrySet()) {
			ParticipantWorkState pws = set.getValue();
			ParticipantData pd = pws.getParticipantData();
			
			// Generate file
			String fileName = pd.getStudentID() + '-' + pd.getName();
			String filePath = dir.getAbsolutePath() + "/assignment/" + fileName + ".rtf";
			File rtfFile = new File(filePath);
			
			// Generate write file
			RTFEditorKit kit = new RTFEditorKit();
			StyledDocument doc = pws.getDocument();
			try {
				FileOutputStream outputStream = new FileOutputStream(rtfFile);
				kit.write(outputStream, doc, 0, doc.getEndPosition().getOffset());
				outputStream.close();
			} catch (Exception e) {
				Debug.error("Could not generate rtf file.");
			}
		}
		
//		zipFiles(rtfFiles);
	}
	
	private void zipFiles(File dir) {
		
	}
}
