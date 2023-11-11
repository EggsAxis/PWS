package com.veullustigpws.pws.ui.editor.parts;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.StyledEditorKit;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.listeners.EditorMenuListener;
import com.veullustigpws.pws.texteditor.TextEditorManager;
import com.veullustigpws.pws.ui.appearance.ColoredButtonUI;
import com.veullustigpws.pws.ui.components.CustomTextField;
import com.veullustigpws.pws.ui.components.RoundPanel;
import com.veullustigpws.pws.ui.components.TextDecorationButton;
import com.veullustigpws.pws.utils.GUIUtils;
import com.veullustigpws.pws.utils.TextFieldIntegerInputFilter;

public class EditorMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private EditorMenuListener menuListener;
	private TextEditorManager textEditorManager;
	
	private JPanel inner;
	private final int padding = 7;
	
	private TextDecorationButton boldBtn;
	private TextDecorationButton italicBtn;
	private TextDecorationButton underlineBtn;
	private CustomTextField fontSizeTF;

	public EditorMenu(TextEditorManager textEditorManager) {
		
		
		this.textEditorManager = textEditorManager;
		menuListener = new EditorMenuListener(textEditorManager);
		
		this.setMaximumSize(new Dimension(9999, 90));
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		initComponents();
	}

	private void initComponents() {
		inner = new RoundPanel(40, RoundPanel.TOP_CORNERS);
		inner.setLayout(new BoxLayout(inner, BoxLayout.X_AXIS));
		inner.setFocusable(true);
		inner.setBackground(ColorPalet.DarkBackgroundColor);
		inner.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		
		Dimension dim = new Dimension(500, 70);
		inner.setMaximumSize(dim);
		inner.setMinimumSize(dim);
		inner.setPreferredSize(dim);
		
		initFontsizeTextField();
		inner.add(Box.createRigidArea(new Dimension(padding, 0)));
		initTextDecorationPanel();
		
		inner.add(Box.createHorizontalGlue());
		initHandinButton();
		
		this.add(Box.createHorizontalGlue());
		this.add(inner);
		this.add(Box.createHorizontalGlue());
	}
	
	private void initHandinButton() {
		JButton handinBtn = new JButton("Inleveren");
		handinBtn.setFocusable(false);
		GUIUtils.setComponentSize(handinBtn, new Dimension(110, 35));
		handinBtn.setUI(new ColoredButtonUI(ColorPalet.GreenButton, handinBtn));
		
		inner.add(handinBtn);
	}

	private void initFontsizeTextField() {
		
		fontSizeTF = new CustomTextField();
		fontSizeTF.setText("12");
		fontSizeTF.setHorizontalAlignment(JTextField.RIGHT);
		fontSizeTF.addKeyListener(new TextFieldIntegerInputFilter());
		
		fontSizeTF.addActionListener(menuListener.new FontSizeListener(fontSizeTF));
		GUIUtils.setComponentSize(fontSizeTF, new Dimension(30, 30));
		
		inner.add(fontSizeTF);
	}
	
	private void initTextDecorationPanel() {
		JPanel decPanel = new JPanel();
		decPanel.setOpaque(false);
		decPanel.setLayout(new BoxLayout(decPanel, BoxLayout.X_AXIS));
		
		Dimension btnDim = new Dimension(30, 30);
		boldBtn = new TextDecorationButton(TextDecorationButton.BOLD, btnDim);
		italicBtn = new TextDecorationButton(TextDecorationButton.ITALIC, btnDim);
		underlineBtn = new TextDecorationButton(TextDecorationButton.UNDERLINE, btnDim);
		
		boldBtn.addActionListener(new StyledEditorKit.BoldAction());
		italicBtn.addActionListener(new StyledEditorKit.ItalicAction());
		underlineBtn.addActionListener(new StyledEditorKit.UnderlineAction());
		
		int btnPadding = 4;
		decPanel.add(boldBtn);
		decPanel.add(Box.createRigidArea(new Dimension(btnPadding, 0)));
		decPanel.add(italicBtn);
		decPanel.add(Box.createRigidArea(new Dimension(btnPadding, 0)));
		decPanel.add(underlineBtn);
		
		inner.add(decPanel);
	}
	
	public void setEnabled(boolean enabled) {
		underlineBtn.setEnabled(enabled);
		italicBtn.setEnabled(enabled);
		boldBtn.setEnabled(enabled);
		fontSizeTF.setEditable(enabled);
	}	
}

