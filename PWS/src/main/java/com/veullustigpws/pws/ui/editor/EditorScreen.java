package com.veullustigpws.pws.ui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.StyledDocument;
import com.veullustigpws.pws.app.AppConstants;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.connection.client.ParticipantManager;
import com.veullustigpws.pws.resources.fonts.AppFonts;
import com.veullustigpws.pws.texteditor.TextEditorManager;
import com.veullustigpws.pws.ui.components.RoundPanel;
import com.veullustigpws.pws.ui.editor.parts.DocumentEditor;
import com.veullustigpws.pws.ui.editor.parts.EditorInfo;
import com.veullustigpws.pws.ui.editor.parts.EditorMenu;

public class EditorScreen extends JLayeredPane implements ComponentListener {

	private static final long serialVersionUID = 1L;
	
	private JPanel unpausedScene;
	private JPanel pausedScene;
	
	private DocumentEditor docEditor;
	private TextEditorManager textEditorManager;
	private EditorInfo editorInfoPanel;
	private EditorMenu editorMenu;
	
	private ParticipantManager manager;
	
	public EditorScreen(ParticipantManager manager) {
		this.manager = manager;
		this.setFocusable(true);
		this.addComponentListener(this);
		
		initUnpausedComponents();
		initPausedComponents();
		
		this.add(unpausedScene, 1);
		this.revalidate();
		this.repaint();
	}
	
	private void initPausedComponents() {
		
		pausedScene = new JPanel();
		pausedScene.setLayout(new BoxLayout(pausedScene, BoxLayout.X_AXIS));
		Color c = ColorPalet.DefaultBackgroundColor;
		pausedScene.setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue(), 100));
		
		RoundPanel centerPnl = new RoundPanel(30, RoundPanel.ALL_CORNERS);
		centerPnl.setLayout(new GridBagLayout());
		centerPnl.setBackground(ColorPalet.DefaultBackgroundColor);
		centerPnl.setAlignmentY(Component.CENTER_ALIGNMENT);
		Dimension centerSize = new Dimension(300, 100);
		centerPnl.setPreferredSize(centerSize);
		centerPnl.setMaximumSize(centerSize);
		centerPnl.setMinimumSize(centerSize);
		
		JLabel lbl = new JLabel();
		lbl.setFont(AppFonts.DefaultFont.deriveFont(Font.PLAIN, 17));
		lbl.setForeground(ColorPalet.WhiteText);
		lbl.setText("<html><p style='text-align: center;'>Opdracht is gepauzeerd.<br>Wacht tot je host hem weer start.</p></html>");
		
		centerPnl.add(lbl);
		
		pausedScene.add(Box.createHorizontalGlue());
		pausedScene.add(centerPnl);
		pausedScene.add(Box.createHorizontalGlue());
		pausedScene.setSize(this.getSize());
	}

	private void initUnpausedComponents() {
		unpausedScene = new JPanel();
		unpausedScene.setFocusable(true);
		unpausedScene.setLayout(new BorderLayout());
		unpausedScene.setBackground(AppConstants.defaultBackgroundColor);
		
		// Document editor
		docEditor = new DocumentEditor();
		
		// Text editor manager
		textEditorManager = new TextEditorManager(docEditor);
		
		// Menu
		editorMenu = new EditorMenu(textEditorManager);
		editorInfoPanel = new EditorInfo(manager);
		
		JScrollPane scrollTextPane = new JScrollPane(docEditor);
		scrollTextPane.setMaximumSize(new Dimension(1000, 9999));
		scrollTextPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollTextPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(scrollTextPane);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 120, 10, 120));
		centerPanel.setBackground(AppConstants.defaultBackgroundColor);
		
		// Add together
		unpausedScene.add(editorMenu, BorderLayout.SOUTH);
		unpausedScene.add(editorInfoPanel, BorderLayout.NORTH);
		unpausedScene.add(centerPanel, BorderLayout.CENTER);
		
		unpausedScene.setSize(this.getSize());
	}
	
	public void setPaused(boolean paused) {
		editorMenu.setEnabled(!paused);
		docEditor.setEditable(!paused);
		if (paused) {
			this.add(pausedScene, 0);
		}
		else {
			this.remove(pausedScene);
		}
		
		this.revalidate();
		this.repaint();
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

	@Override
	public void componentResized(ComponentEvent e) {
		unpausedScene.setSize(this.getSize());
		pausedScene.setSize(this.getSize());
	}
	@Override
	public void componentMoved(ComponentEvent e) {}
	@Override
	public void componentShown(ComponentEvent e) {}
	@Override
	public void componentHidden(ComponentEvent e) {}
}
