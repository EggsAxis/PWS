package com.veullustigpws.pws.ui.editor.parts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
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
		
		Dimension dim = new Dimension(800, 90);
		this.setMaximumSize(dim);
		this.setMinimumSize(dim);
		this.setPreferredSize(dim);
		
		this.setBackground(new Color(60, 62, 67));
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		
		initComponents();
	}

	private void initComponents() {
		inner = new JPanel();
		inner.setLayout(new BoxLayout(inner, BoxLayout.X_AXIS));
		inner.setFocusable(true);
		
		Dimension dim = new Dimension(500, 90);
		inner.setMaximumSize(dim);
		inner.setMinimumSize(dim);
		inner.setPreferredSize(dim);
		
		inner.add(Box.createRigidArea(new Dimension(padding, 0)));
		initFontsizePanel();
		inner.add(Box.createRigidArea(new Dimension(padding, 0)));
		initTextDecorationPanel();
		inner.add(Box.createRigidArea(new Dimension(padding, 0)));
		
		inner.add(Box.createGlue());
		initHandinButton();
		inner.add(Box.createRigidArea(new Dimension(2*padding, 0)));
		
		this.setViewportView(inner);
	}
	
	private void initHandinButton() {
		JButton handinBtn = new JButton("Inleveren");
		handinBtn.setFocusable(false);
		setComponentSize(handinBtn, new Dimension(110, 35));
		
		inner.add(handinBtn);
	}

	private void initFontsizePanel() {
		JPanel fontsizePanel = new JPanel();
		fontsizePanel.setFocusable(true);
		fontsizePanel.setLayout(new BorderLayout());
		setComponentSize(fontsizePanel, new Dimension(100, 55));
		
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
		setComponentSize(boldBtn, btnDim);
		setComponentSize(italicBtn, btnDim);
		setComponentSize(underlineBtn, btnDim);
		
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
	
	
	private void setComponentSize(JComponent c, Dimension size) {
		c.setPreferredSize(size);
		c.setMaximumSize(size);
		c.setMinimumSize(size);
	}
	
	
	
}

