package com.veullustigpws.pws.ui.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.StyledDocument;
import com.veullustigpws.pws.app.AppConstants;
import com.veullustigpws.pws.connection.client.ParticipantManager;
import com.veullustigpws.pws.texteditor.TextEditorManager;
import com.veullustigpws.pws.ui.editor.parts.DocumentEditor;
import com.veullustigpws.pws.ui.editor.parts.EditorInfo;
import com.veullustigpws.pws.ui.editor.parts.EditorMenu;

public class EditorScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private DocumentEditor docEditor;
	private TextEditorManager textEditorManager;
	private EditorInfo editorInfoPanel;
	
	private ParticipantManager manager;
	
	public EditorScreen(ParticipantManager manager) {
		this.manager = manager;
		
		this.setFocusable(true);
		this.setLayout(new BorderLayout());
		this.setBackground(AppConstants.defaultBackgroundColor);
		
		initComponents();
		this.repaint();
	}
	
	private void initComponents() {
		
		// Document editor
		docEditor = new DocumentEditor();
		
		// Text editor manager
		textEditorManager = new TextEditorManager(docEditor);
		
		// Menu
		EditorMenu menu = new EditorMenu(textEditorManager);
		editorInfoPanel = new EditorInfo(manager);
		
		JScrollPane scrollTextPane = new JScrollPane(docEditor);
		scrollTextPane.setMaximumSize(new Dimension(1000, 9999));
		scrollTextPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollTextPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(scrollTextPane);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 120, 30, 120));
		centerPanel.setBackground(AppConstants.defaultBackgroundColor);
		
		// Add together
		this.add(menu, BorderLayout.SOUTH);
		this.add(editorInfoPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
	}
	
	public StyledDocument getStyledDocument() {
		return docEditor.getStyledDocument();
	}
	
	public void setRemainingTime(String time) {
		editorInfoPanel.setRemainingTime(time);
	}
	public void setWordCount(int wordcount) {
		editorInfoPanel.setWordCount(wordcount);
	}
	public void setAssignmentName(String name) {
		editorInfoPanel.setAssignmentName(name);
	}
}
