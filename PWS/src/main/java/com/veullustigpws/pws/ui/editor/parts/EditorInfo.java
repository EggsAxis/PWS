package com.veullustigpws.pws.ui.editor.parts;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.connection.client.ParticipantManager;
import com.veullustigpws.pws.resources.fonts.AppFonts;
import com.veullustigpws.pws.ui.components.RoundPanel;

public class EditorInfo extends JPanel {
	private static final long serialVersionUID = -736287837460504722L;
	
	private JLabel wordCountLbl;
	private JLabel timeLbl;
	private JLabel assignmentNameLbl;
	
	private ParticipantManager participantManager;
	
	public EditorInfo(ParticipantManager participantManager) {
		this.participantManager = participantManager;
		
		this.setPreferredSize(new Dimension(99999, 50));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setOpaque(false);
		
		initComponents();
	}
	
	private void initComponents() {
		RoundPanel inner = new RoundPanel(40, RoundPanel.BOTTOM_CORNERS);
		Dimension dim = new Dimension(500, 50);
		inner.setMaximumSize(dim);
		inner.setMinimumSize(dim);
		inner.setPreferredSize(dim);
		inner.setBackground(ColorPalet.DefaultBackgroundColor);
		inner.setLayout(new BoxLayout(inner, BoxLayout.X_AXIS));
		inner.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
		
		assignmentNameLbl = new JLabel();
		timeLbl = new JLabel("00:00:00");
		wordCountLbl = new JLabel("XX woorden");
		
		assignmentNameLbl.setAlignmentY(Component.CENTER_ALIGNMENT);
		timeLbl.setAlignmentY(Component.CENTER_ALIGNMENT);
		wordCountLbl.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		assignmentNameLbl.setFont(AppFonts.DefaultFont.deriveFont(Font.PLAIN, 18));
		timeLbl.setFont(AppFonts.DefaultFont.deriveFont(Font.PLAIN, 18));
		wordCountLbl.setFont(AppFonts.DefaultFont.deriveFont(Font.PLAIN, 18));
		
		assignmentNameLbl.setForeground(ColorPalet.WhiteText);
		timeLbl.setForeground(ColorPalet.WhiteText);
		wordCountLbl.setForeground(ColorPalet.WhiteText);
		
		inner.add(assignmentNameLbl);
		inner.add(Box.createHorizontalGlue());
		inner.add(timeLbl);
		inner.add(Box.createHorizontalGlue());
		inner.add(wordCountLbl);
		
		
		this.add(Box.createHorizontalGlue());
		this.add(inner);
		this.add(Box.createHorizontalGlue());
	}
	
	public void setRemainingTime(String time) {
		timeLbl.setText(time);
	}
	public void setWordCount(int wordcount) {
		wordCountLbl.setText(wordcount + " woorden");
	}
	public void setAssignmentName(String name) {
		assignmentNameLbl.setText(name);
	}

}
