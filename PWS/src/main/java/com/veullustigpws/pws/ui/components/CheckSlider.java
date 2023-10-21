package com.veullustigpws.pws.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractButton;

public class CheckSlider extends AbstractButton implements MouseListener {
	private static final long serialVersionUID = -3508736475874392659L;
	
	private static final String ON_TEXT = "ON";
	private static final String OFF_TEXT = "OFF";
	
	private static final Dimension dimension = new Dimension(45, 22);
	
	private boolean selected;
	
	public CheckSlider() {
		this.setMaximumSize(dimension);
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
		
		if (selected) 	g.setColor(Color.green);
		else 			g.setColor(Color.red);
		g.fillRoundRect(0, 0, dimension.width, dimension.height, dimension.height, dimension.height);
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
