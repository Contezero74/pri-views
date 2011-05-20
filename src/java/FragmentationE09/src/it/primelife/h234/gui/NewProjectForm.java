package it.primelife.h234.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewProjectForm extends JDialog {
	
	public boolean isOk = false;
	public String ProjectName = "";  //  @jve:decl-index=0:
	public int NumberOfAttributes = 0;
	

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel ProjectNameLabel = null;
	private JTextField ProjectNameText = null;
	private JLabel NumOfAttrsLabel = null;
	private JTextField NumOfAttrsText = null;
	private JPanel InterfacePanel = null;
	private JPanel ButtonsPanel = null;
	private JButton ButtonOk = null;
	private JButton ButtonCancel = null;

	/**
	 * @param owner
	 */
	public NewProjectForm(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 160);
		this.setLocationRelativeTo( this.getParent() );
		this.setModal(true);
		this.setResizable(false);
		this.setTitle("New Project");
		this.setVisible(false);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			NumOfAttrsLabel = new JLabel();
			NumOfAttrsLabel.setText("Num. Of Attributes:");
			ProjectNameLabel = new JLabel();
			ProjectNameLabel.setText("Project Name:");
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getInterfacePanel(), BorderLayout.CENTER);
			jContentPane.add(getButtonsPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes ProjectNameText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getProjectNameText() {
		if (ProjectNameText == null) {
			ProjectNameText = new JTextField(30);
			ProjectNameText.setColumns(20);
			ProjectNameText.setText("undefined");
		}
		return ProjectNameText;
	}

	/**
	 * This method initializes NumOfAttrsText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNumOfAttrsText() {
		if (NumOfAttrsText == null) {
			NumOfAttrsText = new JTextField(3);
			NumOfAttrsText.setHorizontalAlignment(JTextField.TRAILING);
			NumOfAttrsText.setText("10");
			NumOfAttrsText.setToolTipText("Insert an integer number representing the maximum number of attribute");
		}
		return NumOfAttrsText;
	}

	/**
	 * This method initializes InterfacePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getInterfacePanel() {
		if (InterfacePanel == null) {
			InterfacePanel = new JPanel();
			InterfacePanel.setLayout(new FlowLayout());
			InterfacePanel.add(ProjectNameLabel, null);
			InterfacePanel.add(getProjectNameText(), null);
			InterfacePanel.add(NumOfAttrsLabel, null);
			InterfacePanel.add(getNumOfAttrsText(), null);
		}
		return InterfacePanel;
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
			ButtonsPanel.add(getButtonOk(), null);
			ButtonsPanel.add(getButtonCancel(), null);
		}
		return ButtonsPanel;
	}

	/**
	 * This method initializes ButtonOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButtonOk() {
		if (ButtonOk == null) {
			ButtonOk = new JButton();
			ButtonOk.setText("OK");
			ButtonOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = true;
					ProjectName = ProjectNameText.getText().trim();
					try {
						NumberOfAttributes = Integer.parseInt( NumOfAttrsText.getText() );
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(getParent(), "Num. of Attributes has to be an integer number in the range 1-120", "WARNING - New Project", JOptionPane.WARNING_MESSAGE);
						
						return;
					}
					
					if ( 0 == ProjectName.length() ) {
						JOptionPane.showMessageDialog(getParent(), "The project name can not be empty", "WARNING - New Project", JOptionPane.WARNING_MESSAGE);
						
						return;
					}
					
					if (120 < NumberOfAttributes || 4 > NumberOfAttributes) {
						JOptionPane.showMessageDialog(getParent(), "Num. of Attributes has to be in the range 4-120", "WARNING - New Project", JOptionPane.WARNING_MESSAGE);
						
						return;
					}
					
					setVisible(false);
				}
			});
		}
		return ButtonOk;
	}

	/**
	 * This method initializes ButtonCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButtonCancel() {
		if (ButtonCancel == null) {
			ButtonCancel = new JButton();
			ButtonCancel.setText("Cancel");
			ButtonCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = false;
					setVisible(false);
				}
			});
		}
		return ButtonCancel;
	}
}
