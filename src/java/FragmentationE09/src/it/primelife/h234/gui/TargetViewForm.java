package it.primelife.h234.gui;

import it.primelife.h234.project.project;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class TargetViewForm extends JDialog {
	
	public boolean isOk			= false;
	public project MyProgect	= null;
	public String Target		= "";  //  @jve:decl-index=0:
	public int TargetCost			= 0;
	public boolean isModifyMode	= false;
	
	List<JCheckBox> Attributes = new ArrayList<JCheckBox>();
	

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel TAttributesPanel = null;
	private JScrollPane TAttributesListPanel = null;
	private JPanel ButtonsPanel = null;
	private JButton AddModifyButton = null;
	private JButton CancelButton = null;
	private JPanel TargetDataPanel = null;
	private JPanel CostPanel = null;
	private JPanel AttributesPanel = null;
	private JLabel CostLabel = null;
	private JTextField TargetCostText = null;
	private JLabel TargetAttributesLabel = null;

	/**
	 * @param owner
	 */
	public TargetViewForm(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 184);
		this.setLocationRelativeTo( this.getParent() );
		this.setModal(true);
		this.setTitle("Target");
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
			jContentPane.add(getButtonsPanel(), BorderLayout.SOUTH);
			jContentPane.add(getTargetDataPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes TAttributesPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTAttributesPanel() {
		if (TAttributesPanel == null) {
			TAttributesPanel = new JPanel();
			TAttributesPanel.setLayout(new FlowLayout());
		}
		return TAttributesPanel;
	}

	/**
	 * This method initializes TAttributesListPanel	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getTAttributesListPanel() {
		if (TAttributesListPanel == null) {
			TAttributesListPanel = new JScrollPane();
			TAttributesListPanel.setViewportView(getTAttributesPanel());
		}
		return TAttributesListPanel;
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
					
					Target = "";
					
					for (JCheckBox JCB : Attributes) {
						if ( JCB.isSelected() ) {
							Target = Target + "1";
						} else {
							Target = Target + "0";
						}
					}
					
					int TmpCost = 0;
					try {
						TmpCost = Integer.parseInt( TargetCostText.getText().trim() );
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(getParent(), "The cost Target has to be an positive intger or zero", "WARNING - Cost Target", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					if (0 > TmpCost) {
						JOptionPane.showMessageDialog(getParent(), "The cost Target has to be an positive intger or zero", "WARNING - Cost Target", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					TargetCost = TmpCost;
					
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
			JOptionPane.showMessageDialog(this.getParent(), "The Project is not created yet", "WARNING - Targets", JOptionPane.WARNING_MESSAGE);
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
			TAttributesPanel.add(Tmp);
			Attributes.add(Tmp);
		}
		
		
		if (isModifyMode) {
			setTitle("Modify Target View");
			AddModifyButton.setText("Modify");
			
			for(int i = 0; i < Target.length(); ++i) {
				if ( '1' == Target.toCharArray()[i] ) {
					Attributes.get(i).setSelected(true);
				}
			}
			
			getTargetCostText().setText( Integer.toString(TargetCost) );
		} else {
			setTitle("Add Target View");
			AddModifyButton.setText("Add");			
		}
		
		super.setVisible(true);
	}

	/**
	 * This method initializes TargetDataPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTargetDataPanel() {
		if (TargetDataPanel == null) {
			TargetDataPanel = new JPanel();
			TargetDataPanel.setLayout(new BoxLayout(getTargetDataPanel(), BoxLayout.Y_AXIS));
			TargetDataPanel.add(getCostPanel(), null);
			TargetDataPanel.add(getAttributesPanel(), null);
		}
		return TargetDataPanel;
	}

	/**
	 * This method initializes CostPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getCostPanel() {
		if (CostPanel == null) {
			CostLabel = new JLabel();
			CostLabel.setText("Weight:");
			CostPanel = new JPanel();
			CostPanel.setLayout(new FlowLayout());
			CostPanel.add(CostLabel, null);
			CostPanel.add(getTargetCostText(), null);
		}
		return CostPanel;
	}

	/**
	 * This method initializes AttributesPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getAttributesPanel() {
		if (AttributesPanel == null) {
			TargetAttributesLabel = new JLabel();
			TargetAttributesLabel.setText("Attributes:");
			TargetAttributesLabel.setFont(new Font("Dialog", Font.BOLD, 14));
			AttributesPanel = new JPanel();
			AttributesPanel.setLayout(new BorderLayout());
			AttributesPanel.add(getTAttributesListPanel(), BorderLayout.CENTER);
			AttributesPanel.add(TargetAttributesLabel, BorderLayout.NORTH);
		}
		return AttributesPanel;
	}

	/**
	 * This method initializes TargetCostText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTargetCostText() {
		if (TargetCostText == null) {
			TargetCostText = new JTextField();
			TargetCostText.setColumns(10);
			TargetCostText.setHorizontalAlignment(JTextField.TRAILING);
			TargetCostText.setText("0");
			TargetCostText.setToolTipText("Insert an integer representing the Target cost");
		}
		return TargetCostText;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
