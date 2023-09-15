package com.veullustigpws.pws.ui.editor;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import com.veullustigpws.pws.app.UIFrame;

public class EditorScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private UIFrame frame;
	private JScrollPane scrollTextPane;
	private JTextPane textPane;
	
	public EditorScreen(UIFrame frame) {
		this.frame = frame;
		
		this.setFocusable(true);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		initComponents();
		this.repaint();
	}
	
	private void initComponents() {
		
		textPane = new JTextPane();
		
		textPane.setContentType("text/html");
		textPane.setText("Test<br> test");
		
		scrollTextPane = new JScrollPane(textPane);
		scrollTextPane.setMaximumSize(new Dimension(1000, 9999));
		scrollTextPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
//		this.add(Box.createRigidArea(new Dimension(200, 9999)));
		this.add(Box.createHorizontalGlue());
		this.add(scrollTextPane);
		this.add(Box.createHorizontalGlue());
//		this.add(Box.createRigidArea(new Dimension(200, 9999)));
	}
}
