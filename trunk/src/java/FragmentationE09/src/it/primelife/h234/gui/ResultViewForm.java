package it.primelife.h234.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class ResultViewForm extends JDialog {
	
	public String Result = "";
	
	private class MyDefaultTableModel extends DefaultTableModel {
		private static final long serialVersionUID = -894416835477195193L;

		public MyDefaultTableModel() {
			super();
		}
		
		public boolean isCellEditable(int row, int col) {
			return false;
		}
	}
	
	private MyDefaultTableModel MyTable = new MyDefaultTableModel();
	
	public void setVisible(boolean b) {
		if (false == b) {
			super.setVisible(b);
			return;
		}
		
		if ( null != Result && 0 != Result.length() ) {
			MyTable = new MyDefaultTableModel();
			
			Scanner ResultScanner = new Scanner( Result.trim() );
			
			boolean Header = true;
			while ( ResultScanner.hasNext() ) {
				String Line = ResultScanner.nextLine();
				
				String[] Elements = Line.split("\t");
				
				if (Header) {
					MyTable.setColumnCount(Elements.length);
					MyTable.setColumnIdentifiers(Elements);
					
					Header = false;
				} else {
					MyTable.addRow(Elements);
				}
			}
			
			ResultTable.setModel(MyTable);	
		}
		
		super.setVisible(b);
	}

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel TablePanel = null;
	private JPanel ButtonsPanel = null;
	private JButton SaveButton = null;
	private JButton CancelButton = null;
	private JScrollPane ScrollTablePanel = null;
	private JTable ResultTable = null;

	/**
	 * @param owner
	 */
	public ResultViewForm(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(400, 300);
		this.setLocationRelativeTo( this.getParent() );
		this.setTitle("Experiments Result");
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
			jContentPane.add(getTablePanel(), BorderLayout.CENTER);
			jContentPane.add(getButtonsPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes TablePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTablePanel() {
		if (TablePanel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			TablePanel = new JPanel();
			TablePanel.setLayout(new GridBagLayout());
			TablePanel.add(getScrollTablePanel(), gridBagConstraints);
		}
		return TablePanel;
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
			ButtonsPanel.add(getSaveButton(), null);
			ButtonsPanel.add(getCancelButton(), null);
		}
		return ButtonsPanel;
	}

	/**
	 * This method initializes SaveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveButton() {
		if (SaveButton == null) {
			SaveButton = new JButton();
			SaveButton.setText("Save...");
			SaveButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if ( null == Result || 0 == Result.length() ) {
						return;
					}
					
					final JFileChooser Saver = new JFileChooser();
					
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Result text file", "txt");

					Saver.setFileFilter(filter);

					//In response to a button click:
					int returnVal = Saver.showSaveDialog(null);
					if (JFileChooser.APPROVE_OPTION == returnVal) {
						try {
							Writer Out = new BufferedWriter( new FileWriter( Saver.getSelectedFile().getAbsolutePath() ) );
							
							Out.write(Result);
							
							Out.flush();
							Out.close();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(getParent(), "The Result can not be save", "WARNING - Result saving", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			});
		}
		return SaveButton;
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
	 * This method initializes ScrollTablePanel	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollTablePanel() {
		if (ScrollTablePanel == null) {
			ScrollTablePanel = new JScrollPane();
			ScrollTablePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			ScrollTablePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			ScrollTablePanel.setViewportView(getResultTable());
		}
		return ScrollTablePanel;
	}

	/**
	 * This method initializes ResultTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getResultTable() {
		if (ResultTable == null) {
			ResultTable = new JTable();
			ResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		}
		return ResultTable;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
