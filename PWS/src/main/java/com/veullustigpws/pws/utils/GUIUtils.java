package com.veullustigpws.pws.utils;

import java.awt.Dimension;
import javax.swing.JComponent;

public class GUIUtils {
	public static void setComponentSize(JComponent c, Dimension dim) {
		c.setPreferredSize(dim);
		c.setMinimumSize(dim);
		c.setMaximumSize(dim);
	}
}
