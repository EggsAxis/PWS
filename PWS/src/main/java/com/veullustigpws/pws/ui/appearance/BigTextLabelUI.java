package com.veullustigpws.pws.ui.appearance;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.w3c.dom.Text;

import com.veullustigpws.pws.app.App;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.resources.fonts.AppFonts;

public class BigTextLabelUI extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private Font font = AppFonts.DefaultFont.deriveFont(Font.PLAIN , 16); 
	
	public BigTextLabelUI(String string){ 
		this.setText(string);
		this.setForeground(getForeground());
		this.setFont(AppFonts.BigDefaultFont);
	
		
	}
}

	
