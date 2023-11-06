package com.veullustigpws.pws.assignment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import com.veullustigpws.pws.app.App;
import com.veullustigpws.pws.app.Debug;
import com.veullustigpws.pws.assignment.data.ParticipantData;
import com.veullustigpws.pws.assignment.data.ParticipantWorkState;
import com.veullustigpws.pws.connection.Protocol;
import com.veullustigpws.pws.connection.hosting.HostingManager;
import com.veullustigpws.pws.connection.hosting.Server;
import com.veullustigpws.pws.ui.export.ExportAssignmentScreen;

public class ExportManager {
	
	private ExportAssignmentScreen exportScreen;
	private HostingManager hostingManager;
	private HashMap<Integer, ParticipantWorkState> participantWork;
	
	private File dir;
	private ArrayList<File> rtfFiles;
	private File zippedFile;
	
	public ExportManager(HostingManager hostingManager) {
		this.hostingManager = hostingManager;
		dir = FileSystemView.getFileSystemView().getHomeDirectory();
		
		loadScreens();
		collectFinalData();
		setExportScreen();
	}

	private void loadScreens() {
		ExportManager manager = this;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				exportScreen = new ExportAssignmentScreen(manager);
			}
		});
	}
	
	public void setExportScreen() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				App.Window.setScreen(exportScreen);
			}
		});
	}
	
	public void collectFinalData() {
		Thread t = new Thread() {
			public void run() {
				Server server = hostingManager.getServer();
				server.broadcast(Protocol.RequestWork);
				
				try {		// Wait for the response
					Thread.sleep(500);
				} catch (InterruptedException e) {}
				
				server.broadcast(Protocol.AssignmentEnded);
				server.shutdown();
				
				participantWork = hostingManager.getParticipantWorkStates();
			}
		};
		t.start();
	}
	
	public void choseFile(int result) {
		if (result == JFileChooser.APPROVE_OPTION) {
			dir = exportScreen.getFileChooser().getSelectedFile();
			exportScreen.getFileChooseTF().setText(dir.getAbsolutePath());
			Debug.log("Selected file: " + dir.getAbsolutePath());
		}
	}
	
	public void choseFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			exportScreen.getFileChooseTF().setText(dir.getAbsolutePath());
			JOptionPane.showMessageDialog(exportScreen, "Kies een bestaande folder.", "Error", JOptionPane.OK_OPTION);
			return;
		}
		
		dir = file;
		exportScreen.getFileChooser().setCurrentDirectory(file);
		Debug.log("Selected file: " + file.getAbsolutePath());
	}
	
	
	public void generateRTFDocuments(File dir) {
		rtfFiles = new ArrayList<File>();
		
		for (Map.Entry<Integer, ParticipantWorkState> set : participantWork.entrySet()) {
			ParticipantWorkState pws = set.getValue();
			ParticipantData pd = pws.getParticipantData();
			
			// Generate file
			String fileName = pd.getStudentID() + '-' + pd.getName();
			String filePath = dir.getAbsolutePath() + "/assignment/" + fileName + ".rtf";
			File rtfFile = new File(filePath);
			
			// Write file
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
