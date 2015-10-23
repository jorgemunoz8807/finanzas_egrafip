package citmatel.cu.visual_Pack;

import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Rectangle;
import java.awt.Dimension;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import java.awt.Point;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.awt.SystemColor;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.zip.GZIPOutputStream;

import javax.swing.JButton;
import javax.swing.JProgressBar;

import citmatel.cu.DBConection.DB_Conection;
import citmatel.cu.DBConection.DB_Query;
import citmatel.cu.DBConection.DB_Service;
import citmatel.cu.class_Pack.CompactData;
import citmatel.cu.class_Pack.Controler;
import citmatel.cu.class_Pack.ManagerSettings;
import citmatel.cu.class_Pack.Document;
import citmatel.cu.class_Pack.ManagerDoc;
import citmatel.cu.class_Pack.Manual;
import citmatel.cu.utils.FTPDownloadFile;
import citmatel.cu.utils.ImportingBackup;

import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ConfigDB extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelDB = null;
	private JPanel jPanelDocs = null;
	private JLabel jLabelDBDir = null;
	private JLabel jLabelDBName = null;
	private JLabel jLabelDBUser = null;
	private JLabel jLabelDBPassw = null;
	private JButton jButtonFindDocs = null;
	private JButton jButtonImport = null;
	private TextField dirtextField = null;
	private TextField nametextField = null;
	private TextField ussertextField = null;
	private JPasswordField passjPasswordField = null;
	private JLabel portjLabel = null;
	private TextField porttextField1 = null;
	private TextField docstextField = null;
	private JButton savejButton1 = null;
	private ProgressBarPanel pbar = null;
	private JCheckBox frombckjCheckBox = null;

	/**
	 * @param owner
	 */
	public ConfigDB(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(344, 368);
		this.setResizable(false);
		this.setTitle("Configuración de la BD");
		this.setContentPane(getJContentPane());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);

		this.setModal(true);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				if (docstextField.getText().isEmpty()) {

					int result = JOptionPane.showOptionDialog(null,
							"No ha especificado la ruta de los documentos, "
									+ "no podrá visualizar la información. \n "
									+ "¿Desea guardarla ahora?",
							"Configuración", JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null,
							new Object[] { "Sí", "No" }, "OK");
					if (result == 0) {
						JFileChooser jfc = new JFileChooser();
						jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						if (jfc.showOpenDialog(null) == 0) {
							docstextField.setText(jfc.getSelectedFile()
									.getPath());
						}
						try {
							wait();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}
			}

			public void windowOpened(java.awt.event.WindowEvent e) {

				try {

					String dirIP = ManagerSettings.getDir();
					int port = ManagerSettings.getPort();
					String nameDB = ManagerSettings.getName();

					String usserDB = ManagerSettings.getUsser();
					String passDB = ManagerSettings.getPass();

					String pathDoc = ManagerSettings.getPathDocument();

					dirtextField.setText(dirIP);
					nametextField.setText("1egrafip");
					if (port == 0) {
						porttextField1.setText("3306");
					} else {
						porttextField1.setText(String.valueOf(port));
					}
					ussertextField.setText(usserDB);
					passjPasswordField.setText(passDB);
					docstextField.setText(pathDoc);
					nametextField.setText(nameDB);

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);

			// jContentPane.setBorder(BorderFactory.createTitledBorder(null,
			// "Acceso a la BD", TitledBorder.DEFAULT_JUSTIFICATION,
			// TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12),
			// new Color(51, 51, 51)));
			jContentPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			// jContentPane.setBackground(new Color(10));
			jContentPane.setBackground(Color.white);
			jContentPane.add(getJPanelDB(), null);
			jContentPane.add(getJPanelDocs(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelDB
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDB() {
		if (jPanelDB == null) {
			portjLabel = new JLabel();
			portjLabel.setBounds(new Rectangle(194, 82, 41, 21));
			portjLabel.setText("Puerto");
			jLabelDBPassw = new JLabel();
			jLabelDBPassw.setText("Contraseña");
			jLabelDBPassw.setBounds(new Rectangle(5, 139, 92, 16));
			jLabelDBPassw.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelDBUser = new JLabel();
			jLabelDBUser.setText("Usuario");
			jLabelDBUser.setBounds(new Rectangle(5, 111, 92, 16));
			jLabelDBUser.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelDBName = new JLabel();
			jLabelDBName.setText("Nombre");
			jLabelDBName.setHorizontalTextPosition(SwingConstants.CENTER);
			jLabelDBName.setBounds(new Rectangle(5, 83, 92, 16));
			jLabelDBName.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelDBDir = new JLabel();
			jLabelDBDir.setText("Servidor");
			jLabelDBDir.setSize(new Dimension(92, 16));
			jLabelDBDir.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelDBDir.setLocation(new Point(5, 55));
			jPanelDB = new JPanel();
			jPanelDB.setLayout(null);
			jPanelDB.setBounds(new Rectangle(12, 15, 313, 207));
			jPanelDB.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createLineBorder(new Color(51, 153, 0)), "Acceso a la BD",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelDB.setBackground(Color.white);
			jPanelDB.add(jLabelDBDir, null);
			jPanelDB.add(jLabelDBName, null);
			jPanelDB.add(jLabelDBUser, null);
			jPanelDB.add(jLabelDBPassw, null);
			jPanelDB.add(getDirtextField(), null);
			jPanelDB.add(getNametextField(), null);
			jPanelDB.add(getUssertextField(), null);
			jPanelDB.add(getPassjPasswordField(), null);
			jPanelDB.add(portjLabel, null);
			jPanelDB.add(getPorttextField1(), null);
			jPanelDB.add(getJButtonImport(), null);
			jPanelDB.add(getPbar(), null);
			jPanelDB.add(getFrombckjCheckBox(), null);
		}
		return jPanelDB;
	}

	/**
	 * This method initializes jPanelDocs
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDocs() {
		if (jPanelDocs == null) {
			jPanelDocs = new JPanel();
			jPanelDocs.setLayout(null);
			jPanelDocs.setBounds(new Rectangle(12, 228, 313, 106));
			jPanelDocs.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createLineBorder(new Color(51, 153, 0)),
					"Acceso a los documentos",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelDocs.setBackground(Color.white);
			jPanelDocs.add(getJButtonFindDocs(), null);
			jPanelDocs.add(getDocstextField(), null);
			jPanelDocs.add(getSavejButton1(), null);
		}
		return jPanelDocs;
	}

	/**
	 * This method initializes jButtonFindDocs
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonFindDocs() {
		if (jButtonFindDocs == null) {
			jButtonFindDocs = new JButton();
			jButtonFindDocs.setText("...");
			jButtonFindDocs.setSize(new Dimension(43, 26));
			jButtonFindDocs.setLocation(new Point(260, 34));
			jButtonFindDocs
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							JFileChooser jfc = new JFileChooser();
							jfc
									.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							if (jfc.showOpenDialog(null) == 0) {
								docstextField.setText(jfc.getSelectedFile()
										.getPath());
							}
						}
					});

		}
		return jButtonFindDocs;
	}

	/**
	 * This method initializes jButtonImport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonImport() {
		if (jButtonImport == null) {
			jButtonImport = new JButton();
			jButtonImport.setText("Importar datos");
			jButtonImport.setBounds(new Rectangle(180, 170, 118, 26));
			jButtonImport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							ManagerSettings.setDir(getDirtextField().getText());
							ManagerSettings.setName(getNametextField()
									.getText());
							ManagerSettings.setUsser(getUssertextField()
									.getText());
							ManagerSettings.setPass(getPassjPasswordField()
									.getText());
							ManagerSettings.setPort(Integer
									.valueOf(getPorttextField1().getText()));
							// ManagerSettings.setPathDocument(getDocstextField()
							// .getText());
							// pbar.updateBar(5);

							if (dirtextField.getText().isEmpty()
									|| nametextField.getText().isEmpty()
									|| porttextField1.getText().isEmpty()
									|| ussertextField.getText().isEmpty()) {
								JOptionPane
										.showMessageDialog(
												null,
												"Verifique los datos de configuración del servidor",
												"Configuración",
												JOptionPane.WARNING_MESSAGE);
							} else
								try {
									/**
									 * Guarda la configuracion de la base de
									 * datos en fichero
									 */

									CompactData compactData = new CompactData();

									/**
									 * Salva los datos de la configuracion de la
									 * BD en ficheros
									 */
									compactData
											.setDir(ManagerSettings.getDir());
									compactData.setName(ManagerSettings
											.getName());
									compactData.setPass(ManagerSettings
											.getPass());
									compactData.setPort(ManagerSettings
											.getPort());
									compactData.setUsser(ManagerSettings
											.getUsser());
									compactData
											.setPathDocument(getDocstextField()
													.getText());
									// pbar.updateBar(5);

									/**
									 * Importa los datos de los documentos de BD
									 */

									pbar.setVisible(true);
									pbar.setStringPainted(true);

									// Importa backup en MySQL
									if (frombckjCheckBox.isSelected()) {
										String dir = null;

										JFileChooser jfc = new JFileChooser();
										jfc.setAcceptAllFileFilterUsed(false);
										jfc
												.addChoosableFileFilter(new FilterBCK());
										jfc
												.setFileSelectionMode(JFileChooser.FILES_ONLY);

										if (jfc.showOpenDialog(null) == 0) {
											dir = jfc.getSelectedFile()
													.getAbsolutePath();
										}
										ImportingBackup.ImportBackup(dir,
												dirtextField.getText(), Integer
														.valueOf(porttextField1
																.getText()));
									}
									Controler.LoadDataBase(pbar, 97);
									compactData.SetCompactData();

									/**
									 * Salva todos los datos de la base de datos
									 * en ficheros
									 */
									try {
										Controler
												.SaveObject("All_Settings.cfg",
														compactData);
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										JOptionPane
												.showMessageDialog(
														null,
														"Ha ocurrido un problema al guardar los datos de configuración",
														"Guardando configuración",
														JOptionPane.ERROR_MESSAGE);
									}

									// Icons
									ImageIcon rootIcon = null;
									ImageIcon manualIcon = new ImageIcon(
											getClass()
													.getResource(
															"/citmatel/cu/icons/Manual.png"));
									ImageIcon sectionIcon = new ImageIcon(
											getClass()
													.getResource(
															"/citmatel/cu/icons/Seccion.png"));
									ImageIcon chapterIcon = new ImageIcon(
											getClass()
													.getResource(
															"/citmatel/cu/icons/Capitulo.png"));
									ImageIcon documentIcon = new ImageIcon(
											getClass()
													.getResource(
															"/citmatel/cu/icons/documento.png"));

									Provider.docjTree
											.setCellRenderer(new DocTreeRenderer(
													rootIcon, manualIcon,
													sectionIcon, chapterIcon,
													documentIcon));
									Provider.modjTree
											.setCellRenderer(new ModificationTreeRenderer(
													rootIcon, documentIcon));

									Provider.ReloadDataInDocTree();
									// JOptionPane.showMessageDialog(null,
									// "Termino ReloadDataInDocTree");
									pbar.setValue(99);
									Provider.ReloadDataInModTree();
									// JOptionPane.showMessageDialog(null,
									// "Termino ReloadDataInModTree");
									pbar.setValue(100);
									ConfigDB.this.repaint();

									JOptionPane
											.showMessageDialog(
													null,
													"Los datos fueron importados exitosamente.",
													"Importación concluida",
													JOptionPane.INFORMATION_MESSAGE);

								} catch (SQLException e2) {
									// TODO Auto-generated catch block
									JOptionPane
											.showMessageDialog(
													null,
													"No se pudo establecer conexión con la Base de Datos",
													"Conectando con BD",
													JOptionPane.ERROR_MESSAGE);
									System.out.println(e2.getMessage());

								} catch (IOException e1) {
									// TODO Auto-generated catch block
									JOptionPane
											.showMessageDialog(
													null,
													"No se encuentra el backup de la Base de Datos",
													"Cargando BD",
													JOptionPane.ERROR_MESSAGE);
									e1.printStackTrace();
								}

							// Auto-generated
							// Event
							// stub
							// actionPerformed()
						}
					});
		}
		return jButtonImport;
	}

	/**
	 * This method initializes dirtextField
	 * 
	 * @return java.awt.TextField
	 */
	private TextField getDirtextField() {
		if (dirtextField == null) {
			dirtextField = new TextField();
			dirtextField.setBounds(new Rectangle(98, 51, 200, 25));

			dirtextField.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					showPopup(e);
				}

				public void mouseReleased(MouseEvent e) {
					showPopup(e);
				}

				private void showPopup(MouseEvent e) {
					if (e.isPopupTrigger()) {
						JPopupMenu popup = ((Provider) ConfigDB.this
								.getParent()).getJPopupMenuCopyAndPaste();
						Component c = e.getComponent();
						// System.out.println("Component text field:" + c);
						// System.out.println("Is Focusable" + c.isFocusable());
						popup.show(e.getComponent(), e.getX(), e.getY());
						((Provider) ConfigDB.this.getParent())
								.setComponentThatTriggerCopyPasteMenu((TextField) c);
					}
				}
			});
		}
		return dirtextField;
	}

	/**
	 * This method initializes nametextField
	 * 
	 * @return java.awt.TextField
	 */
	private TextField getNametextField() {
		if (nametextField == null) {
			nametextField = new TextField();
			nametextField.setBounds(new Rectangle(98, 80, 93, 25));

			nametextField.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if (nametextField.isEditable())
						showPopup(e);
				}

				public void mouseReleased(MouseEvent e) {
					if (nametextField.isEditable())
						showPopup(e);
				}

				private void showPopup(MouseEvent e) {
					if (e.isPopupTrigger()) {
						JPopupMenu popup = ((Provider) ConfigDB.this
								.getParent()).getJPopupMenuCopyAndPaste();
						Component c = e.getComponent();
						// System.out.println("Component text field:" + c);
						// System.out.println("Is Focusable" + c.isFocusable());
						popup.show(e.getComponent(), e.getX(), e.getY());
						((Provider) ConfigDB.this.getParent())
								.setComponentThatTriggerCopyPasteMenu((TextField) c);
					}
				}
			});

		}
		return nametextField;
	}

	/**
	 * This method initializes ussertextField
	 * 
	 * @return java.awt.TextField
	 */
	private TextField getUssertextField() {
		if (ussertextField == null) {
			ussertextField = new TextField();
			ussertextField.setBounds(new Rectangle(98, 109, 200, 25));
			ussertextField.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					showPopup(e);
				}

				public void mouseReleased(MouseEvent e) {
					showPopup(e);
				}

				private void showPopup(MouseEvent e) {
					if (e.isPopupTrigger()) {
						JPopupMenu popup = ((Provider) ConfigDB.this
								.getParent()).getJPopupMenuCopyAndPaste();
						Component c = e.getComponent();

						// System.out.println("Component text field:" + c);
						// System.out.println("Is Focusable" + c.isFocusable());
						popup.show(e.getComponent(), e.getX(), e.getY());
						((Provider) ConfigDB.this.getParent())
								.setComponentThatTriggerCopyPasteMenu((TextField) c);
					}
				}
			});

		}
		return ussertextField;
	}

	/**
	 * This method initializes passjPasswordField
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private JPasswordField getPassjPasswordField() {
		if (passjPasswordField == null) {
			passjPasswordField = new JPasswordField();
			passjPasswordField.setBounds(new Rectangle(98, 138, 200, 25));

		}
		return passjPasswordField;
	}

	/**
	 * This method initializes porttextField1
	 * 
	 * @return java.awt.TextField
	 */
	private TextField getPorttextField1() {
		if (porttextField1 == null) {
			porttextField1 = new TextField();
			porttextField1.setBounds(new Rectangle(239, 80, 59, 25));
			porttextField1.setText("3306");

			porttextField1.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					showPopup(e);
				}

				public void mouseReleased(MouseEvent e) {
					showPopup(e);
				}

				private void showPopup(MouseEvent e) {
					if (e.isPopupTrigger()) {
						JPopupMenu popup = ((Provider) ConfigDB.this
								.getParent()).getJPopupMenuCopyAndPaste();
						Component c = e.getComponent();
						// System.out.println("Component text field:" + c);
						// System.out.println("Is Focusable" + c.isFocusable());
						popup.show(e.getComponent(), e.getX(), e.getY());
						((Provider) ConfigDB.this.getParent())
								.setComponentThatTriggerCopyPasteMenu((TextField) c);
					}
				}
			});

		}
		return porttextField1;
	}

	/**
	 * This method initializes docstextField
	 * 
	 * @return java.awt.TextField
	 */
	private TextField getDocstextField() {
		if (docstextField == null) {
			docstextField = new TextField();
			docstextField.setBounds(new Rectangle(7, 34, 245, 25));
			docstextField.setEditable(false);
		}
		return docstextField;
	}

	/**
	 * This method initializes savejButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSavejButton1() {
		if (savejButton1 == null) {
			savejButton1 = new JButton();
			savejButton1.setText("Salvar");
			savejButton1.setBounds(new Rectangle(111, 68, 70, 26));
			savejButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					CompactData compactData = new CompactData();

					ManagerSettings.setPathDocument(getDocstextField()
							.getText());

					/**
					 * Utiliza los mismo datos de la configuracion de BD
					 * existente, pero agrega la ruta de los documentos
					 * modificada
					 */
					compactData.setDir(ManagerSettings.getDir());
					compactData.setName(ManagerSettings.getName());
					compactData.setPass(ManagerSettings.getPass());
					compactData.setPort(ManagerSettings.getPort());
					compactData.setUsser(ManagerSettings.getUsser());

					compactData.setPathDocument(getDocstextField().getText());
					/**
					 * Utiliza los mismo datos de la configuracion de las listas
					 * existente
					 */
					compactData.setListM(ManagerDoc.getManuals());
					compactData.setListS(ManagerDoc.getSections());
					compactData.setListC(ManagerDoc.getChapters());
					compactData.setListD(ManagerDoc.getDocuments());
					compactData.setListModif(ManagerDoc.getModifications());

					/**
					 * Importa los datos de los documentos de BD
					 */
					try {
						if (docstextField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null,
									"Especifique la ruta de los documentos.",
									"Configuración",
									JOptionPane.WARNING_MESSAGE);
						} else {
							Controler.SaveObject("All_Settings.cfg",
									compactData);
							// JOptionPane
							// .showMessageDialog(null,
							// "La ruta de los documentos se ha guardado correctamente");
							int result = JOptionPane
									.showOptionDialog(
											null,
											"La ruta de los documentos se ha guardado correctamente. \n ¿Desea realizar otra operación?",
											"Configuración",
											JOptionPane.YES_NO_OPTION,
											JOptionPane.INFORMATION_MESSAGE,
											null, new Object[] { "Sí", "No" },
											"OK");
							if (result == 1) {
								ConfigDB.this.setVisible(false);

							}

						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});
		}
		return savejButton1;
	}

	/**
	 * This method initializes jProgressBar
	 * 
	 * @return javax.swing.JProgressBar
	 */
	private ProgressBarPanel getPbar() {
		if (pbar == null) {
			pbar = new ProgressBarPanel();
			pbar.setBounds(new Rectangle(17, 174, 157, 18));
			pbar.setVisible(false);
		}
		return pbar;
	}

	/**
	 * This method initializes frombckjCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getFrombckjCheckBox() {
		if (frombckjCheckBox == null) {
			frombckjCheckBox = new JCheckBox();
			frombckjCheckBox.setBounds(new Rectangle(13, 20, 199, 23));
			frombckjCheckBox.setText("Importar desde un backup");
			// frombckjCheckBox.setSelected(true);
			frombckjCheckBox.setBackground(Color.white);
			frombckjCheckBox
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.out.println("actionPerformed()"); // TODO
							// Auto-generated
							// Event
							// stub
							// actionPerformed()
							if (frombckjCheckBox.isSelected()) {
								nametextField.setEditable(false);
								nametextField.setText("1egrafip");
							} else {
								nametextField.setEditable(true);
								nametextField
										.setText(ManagerSettings.getName());
							}

						}
					});
		}
		return frombckjCheckBox;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
