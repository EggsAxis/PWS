package com.veullustigpws.pws.ui.components;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.utils.GUIUtils;

public class MenuPanel extends JPanel {
	private static final long serialVersionUID = -8502878372842769520L;
	
	public static final int SCREEN_LOCATION_TOP = 1;
	public static final int SCREEN_LOCATION_BOTTOM = 2;
	
	private JPanel inner;
	
	public MenuPanel(Dimension dim, int roundness, int type) {
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setOpaque(false);
		
		inner = new RoundPanel(roundness, type);
		inner.setLayout(new BoxLayout(inner, BoxLayout.X_AXIS));
		inner.setBackground(ColorPalet.DarkBackgroundColor);
		GUIUtils.setComponentSize(inner, dim);
		
		this.add(Box.createHorizontalGlue());
		this.add(inner);
		this.add(Box.createHorizontalGlue());
	}
	
	public JPanel getMenu() {
		return inner;
	}
	
	
	public void addGlue() {
		inner.add(Box.createHorizontalGlue());
	}
	public void addRigidArea(int width) {
		inner.add(Box.createRigidArea(new Dimension(width, 0)));
	}
	public void addComponent(JComponent c) {
		c.setAlignmentY(Component.CENTER_ALIGNMENT);
		inner.add(c);
	}
}
