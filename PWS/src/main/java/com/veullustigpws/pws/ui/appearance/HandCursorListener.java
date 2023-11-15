package com.veullustigpws.pws.ui.appearance;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

public class HandCursorListener implements MouseListener {
	
	private JComponent c;
	
	public HandCursorListener(JComponent c) {
		this.c = c;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		c.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		c.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
}
