package it.primelife.h234.gui;

import it.primelife.h234.E09Config;
import it.primelife.h234.E09Config.OSType;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PreferencesForm extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel mainAreaPanel = null;
	private JPanel ButtonsPanel = null;
	private JButton OkButton = null;
	private JButton CancelButton = null;
	private JLabel OSTypeLabel = null;
	private JComboBox OSList = null;
	private JLabel CmdLabel = null;
	private JTextField CmdText = null;

	/**
	 * @param owner
	 */
	public PreferencesForm(Frame owner) {
		super(owner);
		initialize();
	}
	
	public void setVisible(boolean b) {
		if (false == b) {
			super.setVisible(false);
			return;
		}
		
		try {
			OSType OST = E09Config.getInstance().getOS();
			int index = 2;
			if (OSType.WIN == OST) {
				index = 0;
			} else if (OSType.UNIXLIKE == OST) {
				index = 1;
			}
			OSList.setSelectedIndex(index);
			
			CmdText.setText( E09Config.getInstance().getApplicationCmd() );
			
			super.setVisible(true);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(getParent(), "The property file is not present in the application directory", "WARNING - Config", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 144);
		this.setLocationRelativeTo( this.getParent() );
		this.setModal(true);
		this.setResizable(false);
		this.setTitle("Preferences");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getMainAreaPanel(), BorderLayout.CENTER);
			jContentPane.add(getButtonsPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes mainAreaPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMainAreaPanel() {
		if (mainAreaPanel == null) {
			CmdLabel = new JLabel();
			CmdLabel.setText("Application Cmd Name:");
			OSTypeLabel = new JLabel();
			OSTypeLabel.setText("Operative System Type:");
			mainAreaPanel = new JPanel();
			mainAreaPanel.setLayout(new FlowLayout());
			mainAreaPanel.add(OSTypeLabel, null);
			mainAreaPanel.add(getOSList(), null);
			mainAreaPanel.add(CmdLabel, null);
			mainAreaPanel.add(getCmdText(), null);
		}
		return mainAreaPanel;
	}

	/**
	 * This method initializes ButtonsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonsPanel() {
		if (ButtonsPanel == null) {
			ButtonsPanel = new JPanel();
			ButtonsPanel.setLayout(new FlowLayout());
			ButtonsPanel.add(getOkButton(), null);
			ButtonsPanel.add(getCancelButton(), null);
		}
		return ButtonsPanel;
	}

	/**
	 * This method initializes OkButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOkButton() {
		if (OkButton == null) {
			OkButton = new JButton();
			OkButton.setText("OK");
			OkButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					OSType OST = OSType.OTHER;
					if ( 0 == OSList.getSelectedIndex() ) {
						OST = OSType.WIN;
					} else if ( 1 == OSList.getSelectedIndex() ) {
						OST = OSType.UNIXLIKE;
					}
					
					String Cmd = CmdText.getText().trim();
					
					if ( !E09Config.getInstance().setOSAndApplication(OST, Cmd) ) {
						JOptionPane.showMessageDialog(getParent(), "The new configuraion can not be saved", "WARNING - Config", JOptionPane.WARNING_MESSAGE);
					}
					
					setVisible(false);
				}
			});
		}
		return OkButton;
	}

	/**
	 * This method initializes CancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton() {
		if (CancelButton == null) {
			CancelButton = new JButton();
			CancelButton.setText("Cancel");
			CancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
				}
			});
		}
		return CancelButton;
	}

	/**
	 * This method initializes OSList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JComboBox getOSList() {
		String[] OSTypes = {"Windows", "Unix like", "Others"};
		
		if (OSList == null) {
			OSList = new JComboBox(OSTypes);
			OSList.setEditable(false);
			OSList.setVisible(true);
			OSList.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if ( 2 == OSList.getSelectedIndex() ) {
						CmdText.setEnabled(true);
					} else {
						CmdText.setEnabled(false);
						
						if ( 0 == OSList.getSelectedIndex() ) {
							CmdText.setText("esorics09.exe");
						} else {
							CmdText.setText("esorics09");
						}
					}
				}
			});
		}
		return OSList;
	}

	/**
	 * This method initializes CmdText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getCmdText() {
		if (CmdText == null) {
			CmdText = new JTextField();
			CmdText.setColumns(10);
			CmdText.setEnabled(false);
		}
		return CmdText;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
