package com.veullustigpws.pws.ui.export;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.assignment.ExportManager;
import com.veullustigpws.pws.ui.appearance.ColoredButtonUI;
import com.veullustigpws.pws.ui.components.CustomTextField;
import com.veullustigpws.pws.ui.components.RoundPanel;
import com.veullustigpws.pws.ui.components.WhiteLabel;
import com.veullustigpws.pws.utils.GUIUtils;

public class ExportAssignmentScreen extends JPanel {
	private static final long serialVersionUID = 9153902267135783178L;
	
	private ExportManager manager;
	private FileChooseListener fileChooseListener;
	
	private JFileChooser fileChooser;
	private JTextField fileChooserTF;
	private JButton fileChooserBtn;
	
	public ExportAssignmentScreen(ExportManager manager) {
		this.manager = manager;
		fileChooseListener = new FileChooseListener();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(ColorPalet.LightBackgroundColor);
		
		initComponents();
	}

	private void initComponents() {
		RoundPanel centerPnl = new RoundPanel(90, RoundPanel.ALL_CORNERS);
		centerPnl.setBackground(ColorPalet.DarkBackgroundColor);
		centerPnl.setLayout(new BorderLayout());
		GUIUtils.setComponentSize(centerPnl, new Dimension(600, 350));
		centerPnl.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Options panel
		JPanel optionsPnl = new JPanel();
		optionsPnl.setLayout(new BoxLayout(optionsPnl, BoxLayout.Y_AXIS));
		optionsPnl.setOpaque(false);
		optionsPnl.setBorder(BorderFactory.createEmptyBorder(80, 60, 60, 0));
		
		
		// File chooser
		JLabel locationLbl = new WhiteLabel("Kies een locatie", 17);
		locationLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
		JPanel fileChooserPnl = initFileChooserPanel();
		
		// Button panel
		JPanel buttonPnl = initButtonPanel();
		
		optionsPnl.add(locationLbl);
		optionsPnl.add(fileChooserPnl);
		
		centerPnl.add(optionsPnl,BorderLayout.CENTER);
		centerPnl.add(buttonPnl, BorderLayout.SOUTH);
		
		this.add(Box.createVerticalGlue());
		this.add(centerPnl);
		this.add(Box.createVerticalGlue());
	}

	private JPanel initFileChooserPanel() {
		File dir = manager.getDirectory();
		fileChooser = new JFileChooser(dir);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int height = 40;
		JPanel pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.X_AXIS));
		pnl.setOpaque(false);
		pnl.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		fileChooserBtn = new JButton("Kies");
		fileChooserBtn.setUI(new ColoredButtonUI(ColorPalet.BlueButton, fileChooserBtn));
		GUIUtils.setComponentSize(fileChooserBtn, new Dimension(60, height));
		fileChooserBtn.addActionListener(fileChooseListener);
		
		fileChooserTF = new CustomTextField();
		fileChooserTF.addActionListener(fileChooseListener);
		fileChooserTF.setText(dir.getAbsolutePath());
		GUIUtils.setComponentSize(fileChooserTF, new Dimension(300, height));
		
		pnl.add(fileChooserTF);
		pnl.add(Box.createRigidArea(new Dimension(10, 0)));
		pnl.add(fileChooserBtn);
		
		return pnl;
	}
	
	
	private JPanel initButtonPanel() {
		JPanel buttonPnl = new JPanel();
		buttonPnl.setLayout(new BoxLayout(buttonPnl, BoxLayout.X_AXIS));
		buttonPnl.setOpaque(false);
		buttonPnl.setBorder(BorderFactory.createEmptyBorder(20, 70, 40, 70));
		
		// Download button
		JButton downloadBtn = new JButton("Download");
		downloadBtn.setUI(new ColoredButtonUI(ColorPalet.GreenButton, downloadBtn));
		GUIUtils.setComponentSize(downloadBtn, new Dimension(150, 50));
		downloadBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		// Download button
		JButton cancelBtn = new JButton("Verwijder");
		cancelBtn.setUI(new ColoredButtonUI(ColorPalet.RedButton, cancelBtn));
		GUIUtils.setComponentSize(cancelBtn, new Dimension(150, 50));
		cancelBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		downloadBtn.addActionListener(e -> {
			manager.downloadResults();
		});
		cancelBtn.addActionListener(e -> {
			manager.deleteResults();
		});
		
		buttonPnl.add(cancelBtn);
		buttonPnl.add(Box.createHorizontalGlue());
		buttonPnl.add(downloadBtn);
		
		return buttonPnl;
	}
	
	public JFileChooser getFileChooser() {
		return fileChooser;
	}
	public JTextField getFileChooseTF() {
		return fileChooserTF;
	}
	
	class FileChooseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == fileChooserBtn) {
				int result =  fileChooser.showSaveDialog(null);
				manager.choseFile(result);
				return;
			}
			
			if (e.getSource() == fileChooserTF) {
				manager.choseFile(fileChooserTF.getText());
			}
			
		}
	}
}
