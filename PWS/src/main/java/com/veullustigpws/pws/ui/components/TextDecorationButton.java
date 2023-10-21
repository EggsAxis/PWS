package com.veullustigpws.pws.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import com.veullustigpws.pws.resources.fonts.AppFonts;
import com.veullustigpws.pws.ui.appearance.HandCursorListener;

public class TextDecorationButton extends JButton {
	private static final long serialVersionUID = -8142119369572835790L;
	
	public static final int ITALIC = 1;
	public static final int BOLD = 2;
	public static final int UNDERLINE = 3;
	
	private int type;
	private Dimension dim;
	
	public TextDecorationButton(int type, Dimension dim) {
		super();
		this.type = type;
		this.dim = dim;
		
		this.enableInputMethods(true);   		
		this.setPreferredSize(dim);
		this.setMaximumSize(dim);
		this.setMinimumSize(dim);
		
		this.setOpaque(false);
		this.addMouseListener(new HandCursorListener(this));
		
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Font font = null;
		String txt = "";
		int fontSize = 15;
		switch (type) {
		case BOLD:
			font = AppFonts.DefaultFont.deriveFont(Font.BOLD, fontSize);
			txt = "B";
			break;
		case ITALIC:
			font = AppFonts.DefaultFont.deriveFont(Font.ITALIC, fontSize);
			txt = "I";
			break;
		case UNDERLINE:
			font = AppFonts.DefaultFont.deriveFont(Font.PLAIN, fontSize);
			txt = "U";
			break;
		}
		
		g.setColor(Color.white);
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		int textX = (dim.width - fm.stringWidth(txt)) / 2;
		int textY = (dim.height - fm.getHeight()) / 2 + fm.getAscent();
		
		g.drawString(txt, textX, textY);
		
		if (type == UNDERLINE) {
			g.drawLine(textX, textY + fm.getHeight() + 2, textX + fm.stringWidth(txt), textY + fm.getHeight() + 2);
			g.drawLine(textX, textY+2, textX + fm.stringWidth(txt), textY+2);
		}
	}
}
