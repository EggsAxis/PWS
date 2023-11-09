package com.veullustigpws.pws.ui.components;

import java.awt.Font;
import javax.swing.JLabel;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.resources.fonts.AppFonts;

public class WhiteLabel extends JLabel {
	private static final long serialVersionUID = -3380327339957541541L;

	public WhiteLabel(String name) {
		super(name);
		
		this.setFont(AppFonts.DefaultFont);
		this.setForeground(ColorPalet.WhiteText);
	}
	public WhiteLabel(String name, boolean bigText) {
		super(name);
		
		if (bigText) {
			this.setFont(AppFonts.BigDefaultFont);
		} else {
			this.setFont(AppFonts.DefaultFont);
		}
		
		this.setForeground(ColorPalet.WhiteText);
	}
	public WhiteLabel(String name, float textSize) {
		super(name);
		
		this.setFont(AppFonts.DefaultFont.deriveFont(Font.BOLD, textSize));
		this.setForeground(ColorPalet.WhiteText);
	}

}
