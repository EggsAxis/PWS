package com.veullustigpws.pws.resources.fonts;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import com.veullustigpws.pws.app.Debug;

public class AppFonts {
	
	public static Font DefaultFont;
	public static Font BigDefaultFont;
	
	public static void LoadFonts() {
		try {
			DefaultFont = Font.createFont(Font.TRUETYPE_FONT, new File(AppFonts.class.getResource("default_font.ttf").getFile()));
			DefaultFont = DefaultFont.deriveFont(Font.PLAIN, 16);
			BigDefaultFont = DefaultFont.deriveFont(Font.BOLD, 20);
			
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(DefaultFont);
		} catch (FontFormatException | IOException e) {
			Debug.error("Unable to import fonts."); 
		
		}
	}
}
