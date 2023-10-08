package com.veullustigpws.pws.ui.editor.parts;

import java.awt.Font;
import java.awt.Insets;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import com.veullustigpws.pws.utils.HighlightCaret;

public class DocumentEditor extends JTextPane {
	private static final long serialVersionUID = 1L;
	
	public DocumentEditor() {
		this.setCaret(new HighlightCaret());
		
		StyledDocument doc = new DefaultStyledDocument();
		StyledEditorKit editorKit = new StyledEditorKit();
		this.setStyledDocument(doc);
		this.setEditorKit(editorKit);
		this.setFont(new Font("", Font.PLAIN, 12));
		
		this.setMargin(new Insets(70, 50, 70, 50));
	}
	
	
	
}
