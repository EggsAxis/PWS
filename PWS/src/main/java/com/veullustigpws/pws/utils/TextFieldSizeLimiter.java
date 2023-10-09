package com.veullustigpws.pws.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

public class TextFieldSizeLimiter implements KeyListener {
	private JTextField tf;
	private int limit;
	
	public TextFieldSizeLimiter(JTextField tf, int limit) {
		this.limit = limit;
		this.tf = tf;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (tf.getText().length() >= limit) e.consume();
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
