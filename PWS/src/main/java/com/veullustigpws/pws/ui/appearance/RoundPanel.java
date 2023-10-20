package com.veullustigpws.pws.ui.appearance;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class RoundPanel extends JPanel {
	private static final long serialVersionUID = 1688344625527908444L;
	
	public static final int ALL_EDGES = 0;
	public static final int BOTTOM_EDGES = 1;
	
	private int roundness;
	private int type;
	
	
	public RoundPanel(int roundness, int type) {
		this.roundness = roundness;
		this.type = type;
	}
	
	
	@Override
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(getBackground());
		if (type == ALL_EDGES) 			g.fillRoundRect(0, 0, getWidth(), getHeight(), roundness, roundness);
		else if (type == BOTTOM_EDGES) 	g.fill(ShapeGenerator.createBottomRoundedRect(0, 0, getWidth(), getHeight(), roundness));
	}
}
