package com.veullustigpws.pws.filters;

import java.awt.Color;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class HighlightCaret extends DefaultCaret {

    private static final Highlighter.HighlightPainter unfocusedPainter = new DefaultHighlighter.DefaultHighlightPainter(new Color(107, 157, 255));
    private static final Highlighter.HighlightPainter focusedPainter = new DefaultHighlighter.DefaultHighlightPainter(new Color(107, 157, 255));
    private static final long serialVersionUID = 1L;
    private boolean isFocused;

    @Override
    protected Highlighter.HighlightPainter getSelectionPainter() {
        setBlinkRate(500); 
        return isFocused ? focusedPainter/*super.getSelectionPainter()*/ : unfocusedPainter;
    }

    @Override
    public void setSelectionVisible(boolean hasFocus) {
        if (hasFocus != isFocused) {
            isFocused = hasFocus;
            super.setSelectionVisible(false);
            super.setSelectionVisible(true);
        }
    }
}
