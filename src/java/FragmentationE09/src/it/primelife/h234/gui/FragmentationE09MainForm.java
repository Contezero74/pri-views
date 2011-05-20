package it.primelife.h234.gui;

import it.primelife.h234.project.project;
import it.primelife.h234.project.target;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Event;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FragmentationE09MainForm {
	
	private JFrame jFrame = null;
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu projectMenu = null;
	private JMenu helpMenu = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem aboutMenuItem = null;
	private JMenuItem addConstraintMenuItem = null;
	private JMenuItem addTargetMenuItem = null;
	private JMenuItem runProjectMenuItem = null;
	private JMenuItem saveAsMenuItem = null;
	private JDialog aboutDialog = null;
	private JPanel aboutContentPane = null;
	private JLabel aboutVersionLabel = null;
	private JSplitPane ConstraintsTargetConatinerPanel = null;
	private JPanel ConstraintsPanel = null;
	private JPanel TargetsPanel = null;
	private JLabel ConstraintsLabel = null;
	private JLabel TargetsLabel = null;

	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setName("FragmentationMainWindow");
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setSize(300, 200);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("Fragmentation-E-09");
		}
		return jFrame;
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
			jContentPane.add(getConstraintsTargetConatinerPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getProjectMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getNewMenuItem());
			fileMenu.addSeparator();
			fileMenu.add(getLoadMenuItem());
			fileMenu.add(getSaveAsMenuItem());
			fileMenu.add(getSaveMenuItem());
			fileMenu.addSeparator();
			fileMenu.add(getConfigMenu());
			fileMenu.addSeparator();
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getProjectMenu() {
		if (projectMenu == null) {
			projectMenu = new JMenu();
			projectMenu.setText("Project");
			projectMenu.setEnabled(false);
			projectMenu.add(getAddConstraintMenuItem());
			projectMenu.add(getAddTargetMenuItem());
			projectMenu.add(getRunProjectMenuItem());
		}
		return projectMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("?");
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About...");
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog aboutDialog = getAboutDialog();
					aboutDialog.pack();
					aboutDialog.setLocationRelativeTo(jFrame);
					aboutDialog.setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes aboutDialog	
	 * 	
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new JDialog(getJFrame(), true);
			aboutDialog.setTitle("About");
			aboutDialog.setContentPane(getAboutContentPane());
		}
		return aboutDialog;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(new BorderLayout());
			aboutContentPane.add(getAboutVersionLabel(), BorderLayout.CENTER);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes aboutVersionLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getAboutVersionLabel() {
		if (aboutVersionLabel == null) {
			aboutVersionLabel = new JLabel();
			aboutVersionLabel.setText("@2009 by UniBG and UniMI");
			aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return aboutVersionLabel;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAddConstraintMenuItem() {
		if (addConstraintMenuItem == null) {
			addConstraintMenuItem = new JMenuItem();
			addConstraintMenuItem.setText("Add Constraint...");
			addConstraintMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					Event.CTRL_MASK, true));
			addConstraintMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addConstraintViaViewDialog();
				}

				
			});
		}
		return addConstraintMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAddTargetMenuItem() {
		if (addTargetMenuItem == null) {
			addTargetMenuItem = new JMenuItem();
			addTargetMenuItem.setText("Add Target...");
			addTargetMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
					Event.CTRL_MASK, true));
			addTargetMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addTargetViaViewDialog();
				}
			});
		}
		return addTargetMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getRunProjectMenuItem() {
		if (runProjectMenuItem == null) {
			runProjectMenuItem = new JMenuItem();
			runProjectMenuItem.setText("Run...");
			runProjectMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
					Event.CTRL_MASK, true));
			runProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if ( !MyProject.isSaved() ) {
						saveAs();
					}
					
					try {
						MyProject.save();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(jFrame, "Impossible to save the project", "WARNING - Project Run", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					RunViewForm RV = new RunViewForm( jFrame );
					RV.MyProgect = MyProject;
					
					RV.setVisible(true);
					
					if (RV.isOk) {
						if ( RV.Result.startsWith("Error") ) {
							JOptionPane.showMessageDialog(jFrame, RV.Result, "WARNING - Run Project", JOptionPane.WARNING_MESSAGE);
						} else {
							ResultViewForm ResV = new ResultViewForm(jFrame);
							
							ResV.Result = RV.Result;
							
							ResV.setVisible(true);
						}
					} 
				}
			});
		}
		return runProjectMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveAsMenuItem() {
		if (saveAsMenuItem == null) {
			saveAsMenuItem = new JMenuItem();
			saveAsMenuItem.setText("Save as...");
			saveAsMenuItem.setEnabled(false);
			saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveAs();
				}
			});
		}
		return saveAsMenuItem;
	}

	/**
	 * This method initializes ConstraintsTargetConatinerPanel	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getConstraintsTargetConatinerPanel() {
		if (ConstraintsTargetConatinerPanel == null) {
			ConstraintsTargetConatinerPanel = new JSplitPane();
			ConstraintsTargetConatinerPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
			ConstraintsTargetConatinerPanel.setDividerSize(10);
			ConstraintsTargetConatinerPanel.setDividerLocation( 100 );
			ConstraintsTargetConatinerPanel.setTopComponent(getConstraintsPanel());
			ConstraintsTargetConatinerPanel.setBottomComponent(getTargetsPanel());
			ConstraintsTargetConatinerPanel.setOneTouchExpandable(true);
		}
		return ConstraintsTargetConatinerPanel;
	}

	/**
	 * This method initializes ConstraintsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getConstraintsPanel() {
		if (ConstraintsPanel == null) {
			ConstraintsLabel = new JLabel();
			ConstraintsLabel.setText("Constraints");
			ConstraintsLabel.setFont(new Font("Dialog", Font.BOLD, 14));
			ConstraintsLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			ConstraintsPanel = new JPanel();
			ConstraintsPanel.setLayout(new BorderLayout());
			ConstraintsPanel.add(ConstraintsLabel, BorderLayout.NORTH);
			ConstraintsPanel.add(getConstrainstListScrollPanel(), BorderLayout.CENTER);
		}
		return ConstraintsPanel;
	}

	/**
	 * This method initializes TargetsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTargetsPanel() {
		if (TargetsPanel == null) {
			TargetsLabel = new JLabel();
			TargetsLabel.setText("Targets");
			TargetsLabel.setFont(new Font("Dialog", Font.BOLD, 14));
			TargetsPanel = new JPanel();
			TargetsPanel.setLayout(new BorderLayout());
			TargetsPanel.add(TargetsLabel, BorderLayout.NORTH);
			TargetsPanel.add(getTargetstListScrollPanel(), BorderLayout.CENTER);
		}
		return TargetsPanel;
	}

	/**
	 * This method initializes newMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getNewMenuItem() {
		if (newMenuItem == null) {
			newMenuItem = new JMenuItem();
			newMenuItem.setText("New...");
			newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
					Event.CTRL_MASK, true));
			newMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					NewProjectForm F = new NewProjectForm( jFrame );
					F.setVisible(true);
					
					if ( F.isOk ) {
						MyProject = new project(F.ProjectName, F.NumberOfAttributes);
						
						jFrame.setTitle( "Fragmentation-E-09 - " + MyProject.getProjectName() );
						
						saveAsMenuItem.setEnabled(true);
						projectMenu.setEnabled(true);
						
						refreshConstraintsList();
						refreshTargetsList();
					}
				}
			});
		}
		return newMenuItem;
	}

	/**
	 * This method initializes configMenu	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getConfigMenu() {
		if (configMenu == null) {
			configMenu = new JMenuItem();
			configMenu.setText("Preferences...");
			configMenu.setVisible(true);
			configMenu.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PreferencesForm CF = new PreferencesForm( jFrame  );
					CF.setVisible(true);
				}
			});
		}
		return configMenu;
	}

	/**
	 * This method initializes ConstrainstListScrollPanel	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getConstrainstListScrollPanel() {
		if (ConstrainstListScrollPanel == null) {
			ConstrainstListScrollPanel = new JScrollPane();
			ConstrainstListScrollPanel.setViewportView(getConstraintsList());
		}
		return ConstrainstListScrollPanel;
	}

	/**
	 * This method initializes TargetstListScrollPanel	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getTargetstListScrollPanel() {
		if (TargetstListScrollPanel == null) {
			TargetstListScrollPanel = new JScrollPane();
			TargetstListScrollPanel.setViewportView(getTargetsList());
		}
		return TargetstListScrollPanel;
	}

	/**
	 * This method initializes ConstraintsList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getConstraintsList() {
		if (ConstraintsList == null) {
			ConstraintsLM = new DefaultListModel();
			ConstraintsList = new JList(ConstraintsLM);
			ConstraintsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			ConstraintsList.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseReleased(java.awt.event.MouseEvent e) {
					maybeShowConstraintsPopup(e);
				}
			});
		}
		return ConstraintsList;
	}

	/**
	 * This method initializes TargetsList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getTargetsList() {
		if (TargetsList == null) {
			TargetsLM = new DefaultListModel();
			TargetsList = new JList(TargetsLM);
			TargetsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			TargetsList.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseReleased(java.awt.event.MouseEvent e) {
					maybeShowTargetsPopup(e);
				}
			});
		}
		return TargetsList;
	}

	/**
	 * This method initializes ConstraintsPopupMenu	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	private JPopupMenu getConstraintsPopupMenu() {
		if (ConstraintsPopupMenu == null) {
			ConstraintsPopupMenu = new JPopupMenu();
			ConstraintsPopupMenu.add(getAddConstraintPopup());
			ConstraintsPopupMenu.add(getModifyConstraintPopup());
			ConstraintsPopupMenu.addSeparator();
			ConstraintsPopupMenu.add(getDeleteConstraintPopup());
			ConstraintsPopupMenu.add(getDeleteAllConstraintsPopup());
		}
		return ConstraintsPopupMenu;
	}
	
	private void maybeShowConstraintsPopup(java.awt.event.MouseEvent e) {
        if ( e.isPopupTrigger() && null != MyProject ) {
        	getConstraintsPopupMenu().show(e.getComponent(), e.getX(), e.getY());
        }
    }
	
	private void maybeShowTargetsPopup(java.awt.event.MouseEvent e) {
        if ( e.isPopupTrigger() && null != MyProject ) {
        	getTargetsPopupMenu().show(e.getComponent(), e.getX(), e.getY());
        }
    }

	
	/**
	 * This method initializes addConstraintPopup	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAddConstraintPopup() {
		if (addConstraintPopup == null) {
			addConstraintPopup = new JMenuItem();
			addConstraintPopup.setText("Add Constraint...");
			addConstraintPopup.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addConstraintViaViewDialog();
				}
			});
		}
		return addConstraintPopup;
	}

	/**
	 * This method initializes deleteConstraintPopup	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getDeleteConstraintPopup() {
		if (deleteConstraintPopup == null) {
			deleteConstraintPopup = new JMenuItem();
			deleteConstraintPopup.setText("Delete Constraint");
			deleteConstraintPopup.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if ( !ConstraintsList.isSelectionEmpty() ) {
						int Index = ConstraintsList.getSelectedIndex();
						
						int Result = JOptionPane.showConfirmDialog(jFrame, "Are you sure to delete constraint number " + (Index + 1), "Delete Constraint", JOptionPane.YES_NO_OPTION);
					
						if (JOptionPane.YES_OPTION == Result) {
							MyProject.removeConstraint(Index);
							refreshConstraintsList();
						}
					}
				}
			});
		}
		return deleteConstraintPopup;
	}

	/**
	 * This method initializes modifyConstraintPopup	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getModifyConstraintPopup() {
		if (modifyConstraintPopup == null) {
			modifyConstraintPopup = new JMenuItem();
			modifyConstraintPopup.setText("Modify Constraint...");
			modifyConstraintPopup.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if ( !ConstraintsList.isSelectionEmpty() ) {
						int Index = ConstraintsList.getSelectedIndex();
												
						ConstraintViewForm CV = new ConstraintViewForm( jFrame );
						CV.isModifyMode = true;
						CV.Constraint = MyProject.getConstraint(Index);
						CV.MyProgect = MyProject;
						
						CV.setVisible(true);
						
						if (CV.isOk) {
							String Constraint = CV.Constraint.trim();
							if ( 0 != Constraint.length() ) {
								MyProject.modifyConstraint(Index, Constraint);
								
								refreshConstraintsList();
							}
						}
					}
				}
			});
		}
		return modifyConstraintPopup;
	}

	/**
	 * This method initializes deleteAllConstraintsPopup	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getDeleteAllConstraintsPopup() {
		if (deleteAllConstraintsPopup == null) {
			deleteAllConstraintsPopup = new JMenuItem();
			deleteAllConstraintsPopup.setText("Delete All Constraint");
			deleteAllConstraintsPopup
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if ( 0 != ConstraintsLM.size() ) {
								int Result = JOptionPane.showConfirmDialog(jFrame, "Are you sure to delete ALL constraints?", "Delete All Constraints", JOptionPane.YES_NO_OPTION);
								
								if (JOptionPane.YES_OPTION == Result) {
									MyProject.removeAllConstraints();
	
									refreshConstraintsList();
								}
							}
						}
					});
		}
		return deleteAllConstraintsPopup;
	}

	/**
	 * This method initializes TargetsPopupMenu	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	private JPopupMenu getTargetsPopupMenu() {
		if (TargetsPopupMenu == null) {
			TargetsPopupMenu = new JPopupMenu();
			TargetsPopupMenu.add(getAddTargetPopupMenu());
			TargetsPopupMenu.add(getModifyTargetPopupMenu());
			TargetsPopupMenu.addSeparator();
			TargetsPopupMenu.add(getDeleteTargetPopup());
			TargetsPopupMenu.add(getDeleteAllTargetsPopup());
		}
		return TargetsPopupMenu;
	}

	/**
	 * This method initializes addTargetPopupMenu	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAddTargetPopupMenu() {
		if (addTargetPopupMenu == null) {
			addTargetPopupMenu = new JMenuItem();
			addTargetPopupMenu.setText("Add Target...");
			addTargetPopupMenu.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addTargetViaViewDialog();
				}
			});
		}
		return addTargetPopupMenu;
	}

	/**
	 * This method initializes modifyTargetPopupMenu	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getModifyTargetPopupMenu() {
		if (modifyTargetPopupMenu == null) {
			modifyTargetPopupMenu = new JMenuItem();
			modifyTargetPopupMenu.setText("Modify Target...");
			modifyTargetPopupMenu.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if ( !TargetsList.isSelectionEmpty() ) {
						int Index = TargetsList.getSelectedIndex();
												
						TargetViewForm TV = new TargetViewForm( jFrame );
						TV.isModifyMode = true;
						
						target TmpTarget = MyProject.getTarget(Index);
						TV.Target = TmpTarget.getAttributes();
						TV.TargetCost = TmpTarget.getWeight();
						
						TV.MyProgect = MyProject;
						
						TV.setVisible(true);
						
						if (TV.isOk) {
							int TmpCost = TV.TargetCost;
							String Attributes = TV.Target.trim();
							
							if ( 0 != Attributes.length() ) {
								target NewTarget = new target();
								
								NewTarget.set(Attributes, TmpCost);
								
								MyProject.modifyTarget(Index, NewTarget);
								
								refreshTargetsList();
							}
						}
					}
				}
			});
		}
		return modifyTargetPopupMenu;
	}

	/**
	 * This method initializes deleteTargetPopup	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getDeleteTargetPopup() {
		if (deleteTargetPopup == null) {
			deleteTargetPopup = new JMenuItem();
			deleteTargetPopup.setText("Delete Target");
			deleteTargetPopup.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if ( !TargetsList.isSelectionEmpty() ) {
						int Index = TargetsList.getSelectedIndex();
						
						int Result = JOptionPane.showConfirmDialog(jFrame, "Are you sure to delete target number " + (Index + 1), "Delete Target", JOptionPane.YES_NO_OPTION);
					
						if (JOptionPane.YES_OPTION == Result) {
							MyProject.removeTarget(Index);
							refreshTargetsList();
						}
					}
				}
			});
		}
		return deleteTargetPopup;
	}

	/**
	 * This method initializes deleteAllTargetsPopup	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getDeleteAllTargetsPopup() {
		if (deleteAllTargetsPopup == null) {
			deleteAllTargetsPopup = new JMenuItem();
			deleteAllTargetsPopup.setText("Delete All Target");
			deleteAllTargetsPopup.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if ( 0  != TargetsLM.size() ) {
						int Result = JOptionPane.showConfirmDialog(jFrame, "Are you sure to delete ALL targets?", "Delete All Targets", JOptionPane.YES_NO_OPTION);
						
						if (JOptionPane.YES_OPTION == Result) {
							MyProject.removeAllTargets();
	
							refreshTargetsList();
						}
					}
				}
			});
		}
		return deleteAllTargetsPopup;
	}

	/**
	 * This method initializes saveMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Save");
			saveMenuItem.setEnabled(false);
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Event.CTRL_MASK, true));
			saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						MyProject.save();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(jFrame, "Impossible to save the project", "WARNING - Project Save", JOptionPane.WARNING_MESSAGE);
					}
				}
			});
		}
		return saveMenuItem;
	}

	/**
	 * This method initializes loadMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getLoadMenuItem() {
		if (loadMenuItem == null) {
			loadMenuItem = new JMenuItem();
			loadMenuItem.setText("Load...");
			loadMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					final JFileChooser Loader = new JFileChooser();
					
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Project file", "frp", "txt");

					Loader.setFileFilter(filter);

					//In response to a button click:
					int returnVal = Loader.showOpenDialog(null);
					if (JFileChooser.APPROVE_OPTION == returnVal) {
						try {
							project TmpProject = project.load( Loader.getSelectedFile().getAbsolutePath() );
							
							if (null != TmpProject) {
								MyProject = TmpProject;
								
								jFrame.setTitle( "Fragmentation-E-09 - " + MyProject.getProjectName() );
								
								saveAsMenuItem.setEnabled(true);
								projectMenu.setEnabled(true);
								saveMenuItem.setEnabled(true);
							}
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(jFrame, "Impossible to load the project", "WARNING - Project Save", JOptionPane.WARNING_MESSAGE);
						}
					}
					
					refreshConstraintsList();
					refreshTargetsList();
				}
			});
		}
		return loadMenuItem;
	}

	/**
	 * Launches this application
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FragmentationE09MainForm application = new FragmentationE09MainForm();
				application.getJFrame().setExtendedState(application.getJFrame().getExtendedState()|JFrame.MAXIMIZED_BOTH);
								
				application.getJFrame().setVisible(true);
			}
		});
	}
	
	private void addConstraintViaViewDialog() {
		ConstraintViewForm CV = new ConstraintViewForm( jFrame );
		CV.isModifyMode = false;
		CV.MyProgect = MyProject;
		
		CV.setVisible(true);
		
		if (CV.isOk) {
			String Constraint = CV.Constraint.trim();
			if ( 0 != Constraint.length() ) {
				MyProject.addConstraint(Constraint);
				
				refreshConstraintsList();
			}
		}
	}

	private void refreshConstraintsList() {
		ConstraintsLM.clear();
		
		for(int i = 0; i < MyProject.getNumberOfConstriants(); ++i) {
			ConstraintsLM.add(i, "[" + (i+1) + "] - " + MyProject.getConstraint(i) );
		}
	}
	
	private void addTargetViaViewDialog() {
		TargetViewForm TV = new TargetViewForm( jFrame );
		TV.isModifyMode = false;
		TV.MyProgect = MyProject;
		
		TV.setVisible(true);
		
		if (TV.isOk) {
			target NewTarget = new target();
			
			int Cost = TV.TargetCost;
			
			String Attributes = TV.Target.trim();
			if ( 0 != Attributes.length() ) {
				NewTarget.set(Attributes, Cost);
				
				MyProject.addTarget(NewTarget);
			}
			
			refreshTargetsList();
		}
	}

	private void refreshTargetsList() {
		TargetsLM.clear();
		
		for(int i = 0; i < MyProject.getNumberOfTargets(); ++i) {
			target Tmp = MyProject.getTarget(i);
			TargetsLM.add(i, "[" + (i+1) + "] - " + Tmp.getWeight() + " - "+ Tmp.getAttributes() );
		}
	}
	
	private void saveAs() {
		final JFileChooser Saver = new JFileChooser();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Project file", "frp", "txt");

		Saver.setFileFilter(filter);

		//In response to a button click:
		int returnVal = Saver.showSaveDialog(null);
		if (JFileChooser.APPROVE_OPTION == returnVal) {
			try {
				MyProject.save( Saver.getSelectedFile().getAbsolutePath() );
				saveMenuItem.setEnabled(true);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(jFrame, "Impossible to save the project", "WARNING - Project Save", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private project MyProject = null;
	private JMenuItem newMenuItem = null;
	private JMenuItem configMenu = null;
	private JScrollPane ConstrainstListScrollPanel = null;
	private JScrollPane TargetstListScrollPanel = null;
	private JList ConstraintsList = null;
	private DefaultListModel ConstraintsLM = null;
	private JList TargetsList = null;
	private DefaultListModel TargetsLM = null;
	private JPopupMenu ConstraintsPopupMenu = null;  //  @jve:decl-index=0:visual-constraint="433,117"
	private JMenuItem addConstraintPopup = null;
	private JMenuItem deleteConstraintPopup = null;
	private JMenuItem modifyConstraintPopup = null;
	private JMenuItem deleteAllConstraintsPopup = null;
	private JPopupMenu TargetsPopupMenu = null;  //  @jve:decl-index=0:visual-constraint="167,136"
	private JMenuItem addTargetPopupMenu = null;
	private JMenuItem modifyTargetPopupMenu = null;
	private JMenuItem deleteTargetPopup = null;
	private JMenuItem deleteAllTargetsPopup = null;
	private JMenuItem saveMenuItem = null;
	private JMenuItem loadMenuItem = null;
}
