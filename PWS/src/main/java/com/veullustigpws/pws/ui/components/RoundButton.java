package com.veullustigpws.pws.ui.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import com.veullustigpws.pws.app.ColorPalet;

public class RoundButton extends JButton  {
	private static final long serialVersionUID = 6841410962003381037L;
	
	public static final int PLAY_PAUSE = 1;
	public static final int STOP = 2;
	
	private boolean paused = false;
	private int radius;
	private int type;
	
	public RoundButton(int radius, int type) {
		this.radius = radius;
		this.type = type;
		
		Dimension dim = new Dimension(2*radius, 2*radius);
		this.setPreferredSize(dim);
		this.setMaximumSize(dim);
		this.setMinimumSize(dim);
		this.setOpaque(false);
		
		if (type == PLAY_PAUSE) {
			this.addActionListener(e -> {
				paused = !paused;
				repaint();
			});
		}
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (type == STOP) paintStopButton(g);
		else if (type == PLAY_PAUSE) paintPlayPauseButton(g);
	}
	
	private void paintStopButton(Graphics2D g) {
		g.setColor(ColorPalet.StopButton);
		g.fillOval(0, 0, 2*radius, 2*radius);
	}
	
	private void paintPlayPauseButton(Graphics2D g) {
		if (paused) g.setColor(ColorPalet.PlayButton);
		else 		g.setColor(ColorPalet.PauseButton);
		g.fillOval(0, 0, 2*radius, 2*radius);
	}
	
}
