package com.veullustigpws.pws.ui.appearance;

import javax.swing.JLabel;

import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.resources.fonts.AppFonts;

public class WhiteLabel extends JLabel {
	
	public WhiteLabel(String name) {
		super(name);
		
		this.setFont(AppFonts.DefaultFont);
		this.setForeground(ColorPalet.WhiteText);
	}

}
