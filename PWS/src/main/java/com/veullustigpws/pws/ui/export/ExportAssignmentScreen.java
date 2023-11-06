package com.veullustigpws.pws.ui.export;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import com.veullustigpws.pws.app.ColorPalet;
import com.veullustigpws.pws.assignment.ExportManager;
import com.veullustigpws.pws.ui.appearance.ColoredButtonUI;
import com.veullustigpws.pws.ui.components.CustomTextField;
import com.veullustigpws.pws.ui.components.RoundPanel;
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
		centerPnl.setLayout(new BoxLayout(centerPnl, BoxLayout.Y_AXIS));
		GUIUtils.setComponentSize(centerPnl, new Dimension(600, 500));
		centerPnl.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPnl.setBorder(BorderFactory.createEmptyBorder(60, 60, 40, 0));
		
		// File chooser panel
		JPanel fileChooserPnl = initFileChooserPanel();
		
		
		centerPnl.add(fileChooserPnl);
		
		this.add(Box.createVerticalGlue());
		this.add(centerPnl);
		this.add(Box.createVerticalGlue());
	}

	private JPanel initFileChooserPanel() {
		File home = FileSystemView.getFileSystemView().getHomeDirectory();
		fileChooser = new JFileChooser(home);
		fileChooser.addPropertyChangeListener(new FileChooseListener());
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int height = 40;
		JPanel pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.X_AXIS));
		pnl.setOpaque(false);
		pnl.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		fileChooserBtn = new JButton("Kies");
		fileChooserBtn.setUI(new ColoredButtonUI(ColorPalet.GreenButton, fileChooserBtn));
		GUIUtils.setComponentSize(fileChooserBtn, new Dimension(60, height));
		fileChooserBtn.addActionListener(fileChooseListener);
		
		fileChooserTF = new CustomTextField();
		fileChooserTF.addActionListener(fileChooseListener);
		fileChooserTF.setText(home.getAbsolutePath());
		GUIUtils.setComponentSize(fileChooserTF, new Dimension(300, height));
		
		pnl.add(fileChooserTF);
		pnl.add(Box.createRigidArea(new Dimension(10, 0)));
		pnl.add(fileChooserBtn);
		
		return pnl;
	}
	
	public JFileChooser getFileChooser() {
		return fileChooser;
	}
	public JTextField getFileChooseTF() {
		return fileChooserTF;
	}
	
	class FileChooseListener implements ActionListener, PropertyChangeListener {
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

		@Override
		public void propertyChange(PropertyChangeEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
