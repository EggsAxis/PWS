package com.veullustigpws.pws.texteditor;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import com.veullustigpws.pws.ui.editor.parts.DocumentEditor;

public class TextEditorManager {
	
	private DocumentEditor docEditor;
	
	public TextEditorManager(DocumentEditor docEditor) {
		this.docEditor = docEditor;
	}
	
	
	public void changeFontSize(int newSize) {
		StyledDocument doc = docEditor.getStyledDocument();
		int start = docEditor.getSelectionStart();
		int end = docEditor.getSelectionEnd();
		
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setFontSize(set, newSize);
		
		doc.setCharacterAttributes(start, end, set, false);
	}

}
