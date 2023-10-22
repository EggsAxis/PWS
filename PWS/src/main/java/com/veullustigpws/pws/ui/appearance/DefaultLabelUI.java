package com.veullustigpws.pws.ui.appearance;

import javax.swing.JLabel;

import com.veullustigpws.pws.resources.fonts.AppFonts;

public class DefaultLabelUI extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private Font font = AppFonts.DefaultFont.deriveFont(Font.PLAIN , 16); 
	
	public DefaultLabelUI(String string){ 
		this.setText(string);
		this.setForeground(getForeground());
		this.setFont(AppFonts.DefaultFont);
	
		
	}
}

	
