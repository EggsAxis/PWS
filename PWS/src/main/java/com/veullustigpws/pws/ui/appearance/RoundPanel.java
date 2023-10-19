package com.veullustigpws.pws.ui.appearance;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class RoundPanel extends JPanel {
	private static final long serialVersionUID = 1688344625527908444L;
	
	private int roundness;
	
	public RoundPanel(int roundness) {
		this.roundness = roundness;
	}
	
	
	@Override
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(getBackground());
		g.fillRoundRect(0, 0, getWidth(), getHeight(), roundness, roundness); 
	}
}
