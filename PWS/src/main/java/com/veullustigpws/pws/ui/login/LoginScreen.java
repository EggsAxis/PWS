package com.veullustigpws.pws.ui.login;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.veullustigpws.pws.app.App;
import com.veullustigpws.pws.app.AppConstants;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.app.Debug;
import com.veullustigpws.pws.assignment.ParticipantData;
import com.veullustigpws.pws.connection.client.ParticipantConnectData;
import com.veullustigpws.pws.exceptions.WrongConnectionDataException;
import com.veullustigpws.pws.resources.fonts.AppFonts;
import com.veullustigpws.pws.ui.appearance.CustomButtonUI;
import com.veullustigpws.pws.utils.TextFieldIntegerInputFilter;
import com.veullustigpws.pws.utils.TextFieldSizeLimiter;

public class LoginScreen extends JPanel {
	private static final long serialVersionUID = -3094851789506996929L;

	public static final Dimension size = new Dimension(650, 410);
	private static final int LABEL_PADDING = 8;
	private static final int COMPONENT_PADDING = 5;
	private static final int TEXTFIELD_INDENT = 10;
	
	private JPanel centerPnl;
	
	private JTextField nameTF;
	private JTextField studentNumberTF;
	private JTextField passwordTF;
	private JTextField codeTF;
	
	
	public LoginScreen() {
		// Test Change
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		initComponents();
		
		// DEBUG
		nameTF.setText("Milan Veul");
		studentNumberTF.setText("mv25949");
		passwordTF.setText("password");
	}

	private void initComponents() {
		
		centerPnl = new JPanel();
		centerPnl.setBackground(AppConstants.defaultBackgroundColor);
		centerPnl.setLayout(new BorderLayout());
		centerPnl.setMaximumSize(size);
		centerPnl.setMinimumSize(size);
		centerPnl.setPreferredSize(size);
		centerPnl.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		centerPnl.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.CENTER);
		
		createLeftPanel();
		createRightPanel();
		createButtonPanel();
		
		this.add(Box.createVerticalGlue());
		this.add(centerPnl);
		this.add(Box.createVerticalGlue());
	}
	
	private void createButtonPanel() {
		JButton loginBtn = new JButton("Login");
		Dimension btnDim = new Dimension(310, 45);
		loginBtn.setPreferredSize(btnDim);
		loginBtn.setMaximumSize(btnDim);
		loginBtn.setMinimumSize(btnDim);
		loginBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
		loginBtn.setFocusable(false);
		loginBtn.setUI(new CustomButtonUI(ColorPalet.DefaultBackgroundColor));
		loginBtn.addActionListener(e -> {
			connect();
		});
		
		JPanel bottomPnl = new JPanel();
		bottomPnl.setLayout(new BoxLayout(bottomPnl, BoxLayout.X_AXIS));
		bottomPnl.add(Box.createHorizontalGlue());
		bottomPnl.add(loginBtn);
		bottomPnl.add(Box.createHorizontalGlue());
		bottomPnl.setOpaque(false);
		
		Dimension dim = new Dimension(size.width, 130);
		bottomPnl.setMaximumSize(dim);
		bottomPnl.setMinimumSize(dim);
		bottomPnl.setPreferredSize(dim);
		
		centerPnl.add(bottomPnl, BorderLayout.SOUTH);
	}
	
	private void createLeftPanel() {
		JPanel leftPnl = new JPanel();
		leftPnl.setLayout(new BoxLayout(leftPnl, BoxLayout.Y_AXIS));
		leftPnl.setPreferredSize(new Dimension(size.width/2 -5, 400));
		leftPnl.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 0));
		leftPnl.setOpaque(false);
		
		leftPnl.add(Box.createVerticalGlue());
		
		// Name
		JLabel nameLbl = new JLabel("Naam");
		nameLbl.setFont(AppFonts.DefaultFont.deriveFont(Font.PLAIN, 14f));
		nameTF = new JTextField();
		initTextField(nameTF);
		leftPnl.add(nameLbl);
		addLabelPadding(leftPnl);
		leftPnl.add(addIndent(nameTF, TEXTFIELD_INDENT));
		
		addComponentPadding(leftPnl);
		
		// Student number
		JLabel studentNumberLbl = new JLabel("Leerlingnummer");
		studentNumberTF = new JTextField();
		initTextField(studentNumberTF);
		leftPnl.add(studentNumberLbl);
		addLabelPadding(leftPnl);
		leftPnl.add(addIndent(studentNumberTF, TEXTFIELD_INDENT));
		
		addComponentPadding(leftPnl);
		
		// password 
		JLabel passwordLbl = new JLabel("Wachtwoord");
		passwordTF = new JTextField();
		initTextField(passwordTF);
		leftPnl.add(passwordLbl);
		addLabelPadding(leftPnl);
		leftPnl.add(addIndent(passwordTF, TEXTFIELD_INDENT));
		
		centerPnl.add(leftPnl, BorderLayout.WEST);
	}
	
	private void createRightPanel() {
		JPanel rightPnl = new JPanel();
		rightPnl.setLayout(new BoxLayout(rightPnl, BoxLayout.Y_AXIS));
		rightPnl.setPreferredSize(new Dimension(size.width/2 -5, 400));
		rightPnl.setBorder(BorderFactory.createEmptyBorder(70, 10, 20, 0));
		rightPnl.setOpaque(false);
		
		rightPnl.add(Box.createVerticalGlue());
		
		// Code
		JLabel codeLbl = new JLabel("Code");
		codeTF = new JTextField();
		codeTF.addKeyListener(new TextFieldSizeLimiter(codeTF, 11));
		codeTF.addKeyListener(new TextFieldIntegerInputFilter());
		initTextField(codeTF);
		rightPnl.add(codeLbl);
		addLabelPadding(rightPnl);
		rightPnl.add(addIndent(codeTF, TEXTFIELD_INDENT));
		
		
		centerPnl.add(rightPnl, BorderLayout.EAST);	
	}
	
	private void initTextField(JTextField tf) {
		Dimension tfsize = new Dimension(180, 30);
		tf.setPreferredSize(tfsize);
		tf.setMaximumSize(tfsize);
		tf.setMinimumSize(tfsize);
		tf.setTransferHandler(null); // Disables copy-paste
		tf.setAlignmentX(Component.LEFT_ALIGNMENT);
		tf.addKeyListener(new TextFieldSizeLimiter(tf, 32));
	}
	
	private JPanel addIndent(JTextField tf, int indent) {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setBorder(BorderFactory.createEmptyBorder(0, indent, 0, 0));
		panel.add(tf);
		return panel;
	}
	
	
	private void addComponentPadding(JPanel pnl) {
		pnl.add(Box.createRigidArea(new Dimension(0, COMPONENT_PADDING)));
	}
	private void addLabelPadding(JPanel pnl) {
		pnl.add(Box.createRigidArea(new Dimension(0, LABEL_PADDING)));
	}
	
	private void connect() {
		ParticipantData participantData = new ParticipantData(nameTF.getText(), studentNumberTF.getText());
		ParticipantConnectData data = new ParticipantConnectData();
		data.setCode(codeTF.getText());
		data.setPassword(passwordTF.getText());
		data.setParticipantData(participantData);
		
		try {
			App.Window.connectToRoom(data, this);
		} catch (WrongConnectionDataException e) {
			Debug.error("Unable to connect to room.");
			
			switch (e.getType()) {
			case WrongConnectionDataException.INVALID_CODE:
				JOptionPane.showMessageDialog(null, "Geen room gevonden met code" + data.getCode() + ".", "Error", JOptionPane.OK_OPTION);
				break;
			case WrongConnectionDataException.WRONG_CODE_FORMAT: 
				JOptionPane.showMessageDialog(null, "Code moet 11 tekens lang zijn.", "Error", JOptionPane.OK_OPTION);
				break;
			case WrongConnectionDataException.UNKNOWN_HOST: 
				JOptionPane.showMessageDialog(null, "Laden van het lokale adres mislukt.", "Error", JOptionPane.OK_OPTION);
				break;
			}
		}
	}
	
	public void incorrectPassword() {
		JOptionPane.showMessageDialog(null, "Het wachtwoord is onjuist.", "Error", JOptionPane.OK_OPTION);
	}
}
