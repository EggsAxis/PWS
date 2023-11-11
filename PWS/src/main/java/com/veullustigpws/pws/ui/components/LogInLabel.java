package com.veullustigpws.pws.ui.components;

import java.awt.Font;
import javax.swing.JLabel;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.resources.fonts.AppFonts;

public class LogInLabel extends JLabel {
	private static final long serialVersionUID = -3380327339957541541L;

	public LogInLabel(String name) {
		super(name);
		
		this.setFont(AppFonts.DefaultFont);
		this.setForeground(ColorPalet.LogInText);
	}
	public LogInLabel(String name, boolean bigText) {
		super(name);
		
		if (bigText) {
			this.setFont(AppFonts.BigDefaultFont);
		} else {
			this.setFont(AppFonts.DefaultFont);
		}
		
		this.setForeground(ColorPalet.LogInText);
	}
	public LogInLabel(String name, float textSize) {
		super(name);
		
		this.setFont(AppFonts.DefaultFont.deriveFont(Font.BOLD, textSize));
		this.setForeground(ColorPalet.LogInText);
	}

}
