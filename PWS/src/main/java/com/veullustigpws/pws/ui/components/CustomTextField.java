package com.veullustigpws.pws.ui.components;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.resources.fonts.AppFonts;

public class CustomTextField extends JTextField {
	private static final long serialVersionUID = 7427274296535146661L;
	
	private static final int INDENT_X = 5;
	
	public CustomTextField() {
		this.setOpaque(false);
		this.setBorder(null);
		
		this.setForeground(ColorPalet.TextFieldText);
		this.setCaretColor(ColorPalet.TextFieldText);
		this.setFont(AppFonts.DefaultFont.deriveFont(15));
		
		this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(0, INDENT_X, 0, INDENT_X)));
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int yOffset = 5;
		g.setColor(ColorPalet.TextFieldLine);
		g.setStroke(new BasicStroke(2));
		g.drawLine(0, getHeight() - yOffset, getWidth(), getHeight() - yOffset);
	}
}
