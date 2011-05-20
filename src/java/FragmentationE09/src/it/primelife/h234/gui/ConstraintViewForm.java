package it.primelife.h234.gui;

import it.primelife.h234.project.project;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ConstraintViewForm extends JDialog {
	
	public boolean isOk			= false;
	public project MyProgect	= null;
	public String Constraint	= "";  //  @jve:decl-index=0:
	public boolean isModifyMode	= false;
	
	List<JCheckBox> Attributes = new ArrayList<JCheckBox>();
	

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel CAttributesPanel = null;
	private JScrollPane CAttributesListPanel = null;
	private JPanel ButtonsPanel = null;
	private JButton AddModifyButton = null;
	private JButton CancelButton = null;
	private JLabel ConstraintAttributesLabel = null;

	/**
	 * @param owner
	 */
	public ConstraintViewForm(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 150);
		this.setLocationRelativeTo( this.getParent() );
		this.setModal(true);
		this.setTitle("Constraint");
		this.setName("ConstraintDialog");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			ConstraintAttributesLabel = new JLabel();
			ConstraintAttributesLabel.setText("Attributes:");
			ConstraintAttributesLabel.setFont(new Font("Dialog", Font.BOLD, 14));
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getCAttributesListPanel(), BorderLayout.CENTER);
			jContentPane.add(getButtonsPanel(), BorderLayout.SOUTH);
			jContentPane.add(ConstraintAttributesLabel, BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes CAttributesPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getCAttributesPanel() {
		if (CAttributesPanel == null) {
			CAttributesPanel = new JPanel();
			CAttributesPanel.setLayout(new FlowLayout());
		}
		return CAttributesPanel;
	}

	/**
	 * This method initializes CAttributesListPanel	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getCAttributesListPanel() {
		if (CAttributesListPanel == null) {
			CAttributesListPanel = new JScrollPane();
			CAttributesListPanel.setViewportView(getCAttributesPanel());
		}
		return CAttributesListPanel;
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
			ButtonsPanel.add(getAddModifyButton(), null);
			ButtonsPanel.add(getCancelButton(), null);
		}
		return ButtonsPanel;
	}

	/**
	 * This method initializes AddModifyButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddModifyButton() {
		if (AddModifyButton == null) {
			AddModifyButton = new JButton();
			AddModifyButton.setText("Add-Modify");
			AddModifyButton.addActionListener(new java.awt.event.ActionListener() {   
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = true;
					
					Constraint = "";
					
					for (JCheckBox JCB : Attributes) {
						if ( JCB.isSelected() ) {
							Constraint = Constraint + "1";
						} else {
							Constraint = Constraint + "0";
						}
					}
					
					setVisible(false);
				}
			
			});
		}
		return AddModifyButton;
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
					isOk = false;
					setVisible(false);
				}
			});
		}
		return CancelButton;
	}
	
	public void setVisible(boolean b) {
		if (null == MyProgect) {
			JOptionPane.showMessageDialog(this.getParent(), "The Project is not created yet", "WARNING - Constraints", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if (false == b) {
			super.setVisible(false);
			return;
		}
		
		for(int i = 1; i <= MyProgect.getMaxNumberAttributes(); ++i) {
			JCheckBox Tmp = new JCheckBox("[" + i + "]");
			Tmp.setToolTipText("Attributes number " + i);
			Tmp.setSelected(false);
			CAttributesPanel.add(Tmp);
			Attributes.add(Tmp);
		}
		
		
		if (isModifyMode) {
			setTitle("Modify Constraint View");
			AddModifyButton.setText("Modify");
			
			for(int i = 0; i < Constraint.length(); ++i) {
				if ( '1' == Constraint.toCharArray()[i] ) {
					Attributes.get(i).setSelected(true);
				}
			}
		} else {
			setTitle("Add Constraint View");
			AddModifyButton.setText("Add");			
		}
		
		super.setVisible(true);
	}

}  //  @jve:decl-index=0:visual-constraint="10,28"
