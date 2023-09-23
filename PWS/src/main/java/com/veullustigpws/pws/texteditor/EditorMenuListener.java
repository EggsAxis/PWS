package com.veullustigpws.pws.texteditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

public class EditorMenuListener {
	
	private TextEditorManager textEditorManager;
	
	public EditorMenuListener(TextEditorManager textEditorManager) {
		this.textEditorManager = textEditorManager;
	}
	
	
	public class FontSizeListener implements ActionListener {
		private JTextField tf;
		
		public FontSizeListener(JTextField tf) {
			this.tf = tf;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int size = Integer.parseInt(e.getActionCommand());
			if (size < 8) size = 8;
			else if (size > 40) size = 40;
			tf.setText("" + size);
			textEditorManager.changeFontSize(size);
		}
	}
	
}
