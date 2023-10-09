package com.veullustigpws.pws.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TextFieldIntegerInputFilter implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {
		if (!Character.isDigit(e.getKeyChar())) e.consume();
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
