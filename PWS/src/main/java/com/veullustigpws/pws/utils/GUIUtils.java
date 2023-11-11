package com.veullustigpws.pws.utils;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class GUIUtils {
	public static void setComponentSize(JComponent c, Dimension dim) {
		c.setPreferredSize(dim);
		c.setMinimumSize(dim);
		c.setMaximumSize(dim);
	}
	
	public static JPanel addIndent(JComponent c, int indent) {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setBorder(BorderFactory.createEmptyBorder(0, indent, 0, 0));
		panel.add(c);
		return panel;
	}
}
