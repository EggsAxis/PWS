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

public class DefaultLabelUI_doesntwork extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private Font font = AppFonts.DefaultFont.deriveFont(Font.PLAIN , 16); 
	
	@Override
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		
		g.setColor(ColorPalet.WhiteText);
		g.setFont(AppFonts.DefaultFont);
	
	}
}

	
