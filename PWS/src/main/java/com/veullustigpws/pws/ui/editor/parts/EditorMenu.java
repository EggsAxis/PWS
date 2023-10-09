package com.veullustigpws.pws.ui.editor.parts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.StyledEditorKit;
import com.veullustigpws.pws.app.AppConstants;
import com.veullustigpws.pws.listeners.EditorMenuListener;
import com.veullustigpws.pws.texteditor.TextEditorManager;
import com.veullustigpws.pws.utils.TextFieldIntegerInputFilter;

public class EditorMenu extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	private EditorMenuListener menuListener;
	private TextEditorManager textEditorManager;
	
	private JPanel inner;
	
	private final int padding = 7;

	public EditorMenu(TextEditorManager textEditorManager) {
		this.textEditorManager = textEditorManager;
		menuListener = new EditorMenuListener(textEditorManager);
		
		this.setPreferredSize(new Dimension(9999, 90));
		this.setBackground(new Color(245, 245, 245));
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);		
		TitledBorder title = BorderFactory.createTitledBorder("Menu");
		title.setTitleFont(AppConstants.jPanelTitleFont);
		this.setBorder(title);

		
		initComponents();
	}

	private void initComponents() {
		inner = new JPanel();
		inner.setLayout(new BoxLayout(inner, BoxLayout.X_AXIS));
		inner.setFocusable(true);
		
		inner.add(Box.createRigidArea(new Dimension(padding, 0)));
		initFontsizePanel();
		inner.add(Box.createRigidArea(new Dimension(padding, 0)));
		initTextDecorationPanel();
		inner.add(Box.createRigidArea(new Dimension(padding, 0)));
		
		this.setViewportView(inner);
	}
	
	private void initFontsizePanel() {
		JPanel fontsizePanel = new JPanel();
		fontsizePanel.setMaximumSize(new Dimension(100, 55));
		fontsizePanel.setFocusable(true);
		fontsizePanel.setLayout(new BorderLayout());
		
		TitledBorder title = BorderFactory.createTitledBorder("Lettergrootte");
		title.setTitleFont(AppConstants.jPanelTitleFont);
		fontsizePanel.setBorder(title);
		
		JTextField fontSizeTF = new JTextField();
		fontSizeTF.setText("12");
		fontSizeTF.setFont(AppConstants.textFieldFont);
		fontSizeTF.setHorizontalAlignment(JTextField.RIGHT);
		fontSizeTF.addKeyListener(new TextFieldIntegerInputFilter());
		
		fontSizeTF.addActionListener(menuListener.new FontSizeListener(fontSizeTF));
		
		
		fontsizePanel.add(fontSizeTF, BorderLayout.CENTER);
		inner.add(fontsizePanel);
	}
	
	private void initTextDecorationPanel() {
		JPanel decPanel = new JPanel();
		decPanel.setMaximumSize(new Dimension(105, 55));
		decPanel.setFocusable(true);
		decPanel.setLayout(new BoxLayout(decPanel, BoxLayout.X_AXIS));
		
		TitledBorder title = BorderFactory.createTitledBorder("Decoratie");
		title.setTitleFont(AppConstants.jPanelTitleFont);
		decPanel.setBorder(title);
		
		JButton boldBtn = new JButton("B");
		JButton italicBtn = new JButton("I");
		JButton underlineBtn = new JButton("U");
		
		Dimension btnDim = new Dimension(30, 30);
		boldBtn.setMaximumSize(btnDim);
		italicBtn.setMaximumSize(btnDim);
		underlineBtn.setMaximumSize(btnDim);
		
		boldBtn.setMargin(new Insets(0,0,0,0));
		italicBtn.setMargin(new Insets(0,0,0,0));
		underlineBtn.setMargin(new Insets(0,0,0,0));
		
		boldBtn.setFocusable(false);
		italicBtn.setFocusable(false);
		underlineBtn.setFocusable(false);
		
		boldBtn.addActionListener(new StyledEditorKit.BoldAction());
		italicBtn.addActionListener(new StyledEditorKit.ItalicAction());
		underlineBtn.addActionListener(new StyledEditorKit.UnderlineAction());
		
		decPanel.add(boldBtn);
		decPanel.add(italicBtn);
		decPanel.add(underlineBtn);
		
		inner.add(decPanel);
	}
	
	
	
	
	
	
}

