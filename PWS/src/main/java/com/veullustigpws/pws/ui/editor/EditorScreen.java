package com.veullustigpws.pws.ui.editor;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import com.veullustigpws.pws.app.UIFrame;

public class EditorScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private UIFrame frame;
	private JTextPane textPane;
	
	public EditorScreen(UIFrame frame) {
		this.frame = frame;
		
		this.setFocusable(true);
		
		initComponents();
		this.repaint();
	}
	
	private void initComponents() {
		textPane = new JTextPane();
		
		this.add(textPane);
	}
}
