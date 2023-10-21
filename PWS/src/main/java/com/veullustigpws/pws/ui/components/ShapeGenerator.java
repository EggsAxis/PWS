package com.veullustigpws.pws.ui.components;

import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class ShapeGenerator {
	
	public static Shape createBottomRoundedRect(int x, int y, int width, int height, int roundness) {
		Area a = new Area(new RoundRectangle2D.Float(x, y, width, height, roundness, roundness));
		a.add(new Area(new Rectangle2D.Float(x, y, width, height/2)));
		
		return a;
	}
	
	public static Shape createTopRoundedRect(int x, int y, int width, int height, int roundness) {
		Area a = new Area(new RoundRectangle2D.Float(x, y, width, height, roundness, roundness));
		a.add(new Area(new Rectangle2D.Float(x, y + height/2, width, height/2)));
		
		return a;
	}
}
