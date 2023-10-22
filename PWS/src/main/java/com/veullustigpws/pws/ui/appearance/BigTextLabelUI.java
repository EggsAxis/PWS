package com.veullustigpws.pws.ui.appearance;

import javax.swing.JLabel;

import com.veullustigpws.pws.resources.fonts.AppFonts;

public class BigTextLabelUI extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public BigTextLabelUI(String string){ 
		this.setText(string);
		this.setForeground(getForeground());
		this.setFont(AppFonts.BigDefaultFont);
	
		
	}
}

	
