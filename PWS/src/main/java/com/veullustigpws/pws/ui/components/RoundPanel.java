package com.veullustigpws.pws.ui.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class RoundPanel extends JPanel {
	private static final long serialVersionUID = 1688344625527908444L;
	
	public static final int ALL_CORNERS = 0;
	public static final int BOTTOM_CORNERS = 1;
	public static final int TOP_CORNERS = 2;
	
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
		if (type == ALL_CORNERS) 			g.fillRoundRect(0, 0, getWidth(), getHeight(), roundness, roundness);
		else if (type == BOTTOM_CORNERS) 	g.fill(ShapeGenerator.createBottomRoundedRect(0, 0, getWidth(), getHeight(), roundness));
		else if (type == TOP_CORNERS) 		g.fill(ShapeGenerator.createTopRoundedRect(0, 0, getWidth(), getHeight(), roundness));
	}
}
