package com.veullustigpws.pws.ui.appearance;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.resources.fonts.AppFonts;

public class DefaultButton extends BasicButtonUI {
	
	private Font font = AppFonts.DefaultFont.deriveFont(Font.PLAIN, 14);

	private static final int roundness = 20;
	private Color backgroundColor;
	
	public DefaultButton(Color color) {
		backgroundColor = ColorPalet.DefaultButton;
	}
	
	@Override
	public void paint(Graphics graphics, JComponent c) {
		c.setOpaque(false);
		AbstractButton button = (AbstractButton) c;
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Rectangle
		g.setColor(backgroundColor);
		g.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), roundness, roundness);
		
		// Font
		g.setColor(Color.white);
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		int textX = ((button.getWidth() - fm.stringWidth(button.getText())) / 2);
		int textY = ((button.getHeight() - fm.getHeight()) / 2) + fm.getAscent();
		
		g.drawString(button.getText(), textX, textY);
	}
}
