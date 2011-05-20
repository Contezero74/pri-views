package it.primelife.h234.gui;

import it.primelife.h234.project.project;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RunViewForm extends JDialog {
	
	public boolean isOk			= false;
	public project MyProgect	= null;
	public String Result		= "";
	

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel ButtonsPanel = null;
	private JButton RunButton = null;
	private JButton CancelButton = null;
	private JPanel AttributesPanel = null;
	private JLabel MinLabel = null;
	private JTextField MinText = null;
	private JLabel MaxLabel = null;
	private JTextField MaxText = null;
	private JCheckBox OnlyGreedyCheckBox = null;
	/**
	 * @param owner
	 */
	public RunViewForm(Frame owner) {
		super(owner);
		initialize();
	}
	
	public void setVisible(boolean b) {
		if (false == b) {
			super.setVisible(false);
			return;
		}
		
		if (null == MyProgect) {
			JOptionPane.showMessageDialog(getParent(), "The project is not yet defined", "WARNING - Run Experiments", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		MaxText.setText( Integer.toString( MyProgect.getMaxNumberAttributes() ) );
		
		super.setVisible(true);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 160);
		this.setLocationRelativeTo( this.getParent() );
		this.setResizable(false);
		this.setModal(true);
		this.setTitle("Run Experiments");
		this.setName("TargetDialog");
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
			jContentPane.add(getAttributesPanel(), BorderLayout.CENTER);
			jContentPane.add(getButtonsPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
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
			ButtonsPanel.add(getRunButton(), null);
			ButtonsPanel.add(getCancelButton(), null);
		}
		return ButtonsPanel;
	}

	/**
	 * This method initializes RunButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRunButton() {
		if (RunButton == null) {
			RunButton = new JButton();
			RunButton.setText("Run");
			RunButton.addActionListener(new java.awt.event.ActionListener() {   
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = true;
					
					int MinNumOfAttr = 0;					
					try {
						MinNumOfAttr = Integer.parseInt( MinText.getText().trim() );
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(getParent(), "The minimum number of attributes has to be a positive integer greater than zero", "WARNING - Minimum Attributes", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					if (4 > MinNumOfAttr) {
						JOptionPane.showMessageDialog(getParent(), "The minimum number of attributes has to be a positive integer greater than zero", "WARNING - Minimum Attributes", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					int MaxNumOfAttr = 0;					
					try {
						MaxNumOfAttr = Integer.parseInt( MaxText.getText().trim() );
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(getParent(), "The maximum number of attributes has to be a positive integer greater than zero", "WARNING - Maximum Attributes", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					if (0 > MaxNumOfAttr && MaxNumOfAttr >= MinNumOfAttr) {
						JOptionPane.showMessageDialog(getParent(), "The maximum number of attributes has to be a positive integer greater than zero and greater than the minimum", "WARNING - Maximum Attributes", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					if (120 < MaxNumOfAttr) {
						JOptionPane.showMessageDialog(getParent(), "The maximum number of attributes has to be a positive integer greater than zero and less or equal than 120", "WARNING - Maximum Attributes", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR) );
					try {
						Result = MyProgect.run( MinNumOfAttr, MaxNumOfAttr, OnlyGreedyCheckBox.isSelected() );
					} catch (Exception e1) {
						Result = "Error during experiments running";
					}
					setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR) );
					
					setVisible(false);
				}
			
			});
		}
		return RunButton;
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
	
	/**
	 * This method initializes AttributesPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getAttributesPanel() {
		if (AttributesPanel == null) {
			MaxLabel = new JLabel();
			MaxLabel.setText("Max Number of Attributes:");
			MinLabel = new JLabel();
			MinLabel.setText("Min Number of Attributes");
			AttributesPanel = new JPanel();
			AttributesPanel.setLayout(new FlowLayout());
			AttributesPanel.add(getOnlyGreedyCheckBox(), null);
			AttributesPanel.add(MinLabel, null);
			AttributesPanel.add(getMinText(), null);
			AttributesPanel.add(MaxLabel, null);
			AttributesPanel.add(getMaxText(), null);
		}
		return AttributesPanel;
	}

	/**
	 * This method initializes MinText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getMinText() {
		if (MinText == null) {
			MinText = new JTextField();
			MinText.setColumns(10);
			MinText.setHorizontalAlignment(JTextField.TRAILING);
			MinText.setText("4");
			MinText.setToolTipText("Insert an integer representing the minimum number of attributes");
		}
		return MinText;
	}

	/**
	 * This method initializes MaxText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getMaxText() {
		if (MaxText == null) {
			MaxText = new JTextField();
			MaxText.setColumns(10);
			MaxText.setHorizontalAlignment(JTextField.TRAILING);
			MaxText.setToolTipText("Insert an integer representing the maximum number of attributes");
			MaxText.setText("10");
		}
		return MaxText;
	}

	/**
	 * This method initializes OnlyGreedyCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getOnlyGreedyCheckBox() {
		if (OnlyGreedyCheckBox == null) {
			OnlyGreedyCheckBox = new JCheckBox();
			OnlyGreedyCheckBox.setText("Run Only Greedy Algorithm");
		}
		return OnlyGreedyCheckBox;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
