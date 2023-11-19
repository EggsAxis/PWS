package com.veullustigpws.pws.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractButton;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.utils.GUIUtils;

public class CheckSlider extends AbstractButton implements MouseListener {
	private static final long serialVersionUID = -3508736475874392659L;
	
	
	private static final Dimension dimension = new Dimension(45, 20);
	
	private boolean selected;
	
	public CheckSlider() {
		GUIUtils.setComponentSize(this, dimension);
		this.addMouseListener(this);
	}
	
	@Override
	public int getWidth() {
		return dimension.width;
	}
	@Override
	public int getHeight() {
		return dimension.height;
	}
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (selected) 	g.setColor(new Color(87, 152, 255)); //Dark blue color
		else 			g.setColor(ColorPalet.RedButton);
		g.fillRoundRect(0, 0, dimension.width, dimension.height, dimension.height, dimension.height);
		
		
		
		//circles in checkslider
		g.setColor(ColorPalet.DarkBackgroundColor);
		if (selected) 	g.fillOval(((dimension.width/4)*3) -9 ,(dimension.height/2) -9 , 18, 18);
		else 			g.fillOval((dimension.width/4) -9 ,(dimension.height/2) -9 , 18, 18);
		
		//text in checkslider
		FontMetrics fm = g.getFontMetrics();
		int textXOff = ((dimension.width - fm.stringWidth(getText())) / 2);
		int textYOff = ((dimension.height - fm.getHeight()) / 2) + fm.getAscent();
		
		int textXOn = ((dimension.width - fm.stringWidth(getText())) / 6);
		int textYOn = ((dimension.height - fm.getHeight()) / 2) + fm.getAscent();
		
		if (selected) g.drawString("ON", textXOn, textYOn);
		else g.drawString("OFF", textXOff, textYOff);
		
		
		
	}
	
	
	// Getters
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {
		if (new Rectangle(dimension).contains(e.getPoint())) {
			selected = !selected;
			this.repaint();
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}