package citmatel.cu.visual_Pack;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXDatePicker;

import citmatel.cu.class_Pack.Controler;
import citmatel.cu.class_Pack.License;
import citmatel.cu.class_Pack.ManagerSettings;
import citmatel.cu.class_Pack.Utils;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class CompleteUpdate extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	public TextField pathtextField = null;
	private JButton pathjButton = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private TextField entitytextField = null;
	private TextField dirMACtextField1 = null;
	private JCheckBox includejCheckBox = null;
	private JButton exportjButton = null;
	private ProgressBarPanel pBarPanel = null;

	private JXDatePicker xSwingDateChooser = null;
	public JCheckBox jCheckBoxOnDisk = null;

	/**
	 * @param owner
	 */
	public CompleteUpdate(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(363, 328);
		this.setTitle("Actualización completa");
		this.setContentPane(getJContentPane());
		this.setModal(true);
		this.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel3 = new JLabel();
			jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel3.setBounds(new Rectangle(6, 172, 96, 16));
			jLabel3.setEnabled(false);
			jLabel3.setText("Válida hasta");
			jLabel3.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabel2 = new JLabel();
			jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel2.setBounds(new Rectangle(6, 132, 86, 16));
			jLabel2.setEnabled(false);
			jLabel2.setText("Identificador");
			jLabel2.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabel1 = new JLabel();
			jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel1.setBounds(new Rectangle(6, 92, 79, 16));
			jLabel1.setEnabled(false);
			jLabel1.setText("Empresa");
			jLabel1.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(14, 8, 144, 16));
			jLabel.setText("Guardar actualización en:");
			jLabel.setFont(new Font("Dialog", Font.BOLD, 12));
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(Color.white);
			jContentPane.add(getPathtextField(), null);
			jContentPane.add(getPathjButton(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getEntitytextField(), null);
			jContentPane.add(getDirMACtextField1(), null);
			jContentPane.add(getIncludejCheckBox(), null);
			jContentPane.add(getExportjButton(), null);
			jContentPane.add(getPanelBar(), null);

			jContentPane.add(getXSwingDateChooser(), null);
			jContentPane.add(getJCheckBoxOnDisk(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes pathtextField
	 * 
	 * @return java.awt.TextField
	 */
	private TextField getPathtextField() {
		if (pathtextField == null) {
			pathtextField = new TextField();
			pathtextField.setLocation(new Point(14, 27));
			pathtextField.setSize(new Dimension(281, 25));
			pathtextField.setEditable(false);
			pathtextField.addTextListener(new java.awt.event.TextListener() {
				public void textValueChanged(java.awt.event.TextEvent e) {
					exportjButton
							.setEnabled(pathtextField.getText().length() > 0);
					System.out.println("textValueChanged()"); // TODO
					// Auto-generated
					// Event stub
					// textValueChanged()
				}
			});

		}
		return pathtextField;
	}

	/**
	 * This method initializes pathjButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getPathjButton() {
		if (pathjButton == null) {
			pathjButton = new JButton();
			pathjButton.setBounds(new Rectangle(302, 27, 43, 26));
			pathjButton.setText("...");
			pathjButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser jfc = new JFileChooser() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						public void approveSelection() {
							File selectedFile = getSelectedFile();
							if (Utils.getExtension(selectedFile) == null)
								setSelectedFile(new File(
										getSelectedFile().getAbsolutePath()
												+ "."
												+ CompleteUpdateFilesFilter.validExtension));
							if (getSelectedFile().exists()
									&& getDialogType() == SAVE_DIALOG) {
								int result = JOptionPane
										.showConfirmDialog(
												this,
												"Ya existe un fichero de actualización con el mismo nombre \n en la ubicación especificada. ¿Desea reemplazarlo?.",
												"Fichero existente",
												JOptionPane.YES_NO_CANCEL_OPTION);
								switch (result) {
								case JOptionPane.YES_OPTION:
									super.approveSelection();
									return;
								case JOptionPane.NO_OPTION:
									return;
								case JOptionPane.CLOSED_OPTION:
									return;
								case JOptionPane.CANCEL_OPTION:
									cancelSelection();
									return;
								}
							}
							super.approveSelection();
						}
					};
					jfc.setAcceptAllFileFilterUsed(false);
					jfc.setFileFilter(new CompleteUpdateFilesFilter());
					if (jfc.showSaveDialog(null) == 0) {
						String exportFileName = jfc.getSelectedFile()
								.getAbsolutePath();
						pathtextField.setText(exportFileName);
						exportjButton.setEnabled(true);
						exportjButton.setText("Exportar");

						// checking the path for the update file
						/*
						 * String updateFilePath = pathtextField.getText();
						 * if(new File(updateFilePath).exists()) { if
						 * (JOptionPane.showOptionDialog(CompleteUpdate.this,
						 * convertToMultiline(
						 * "Ya existe un fichero de actualización con el mismo nombre en la ubicación especificada. \n ¿Desea reemplazarlo?"
						 * ), "Guardar en", JOptionPane.OK_CANCEL_OPTION,
						 * JOptionPane.INFORMATION_MESSAGE, null, new
						 * String[]{"Sí", "No"}, // this is the array "default")
						 * != 0){return;}
						 * 
						 * }
						 */

					}

					System.out.println("actionPerformed()"); // TODO
					// Auto-generated
					// Event stub
					// actionPerformed()
				}
			});
		}
		return pathjButton;
	}

	/**
	 * This method initializes entitytextField
	 * 
	 * @return java.awt.TextField
	 */
	private TextField getEntitytextField() {
		if (entitytextField == null) {
			entitytextField = new TextField();
			entitytextField.setBounds(new Rectangle(112, 92, 210, 25));
			entitytextField.setEnabled(false);
			
			entitytextField.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if(entitytextField.isEditable())
						showPopup(e);
	            }
	 
				public void mouseReleased (MouseEvent e) {
					if(entitytextField.isEditable())
						showPopup(e);
	            }
	            private void showPopup(MouseEvent e) {
	                if (e.isPopupTrigger()) {
	                    JPopupMenu popup = ((Provider)CompleteUpdate.this.getParent()).getJPopupMenuCopyAndPaste();
	                    Component c = e.getComponent();
	                    //System.out.println("Component text field:" + c);
	                    //System.out.println("Is Focusable" + c.isFocusable());
	                    popup.show(e.getComponent(), e.getX(), e.getY());
	                    ((Provider)CompleteUpdate.this.getParent()).setComponentThatTriggerCopyPasteMenu((TextField)c);
	                }
	            }
			});
		}
		return entitytextField;
	}

	/**
	 * This method initializes dirMACtextField1
	 * 
	 * @return java.awt.TextField
	 */
	private TextField getDirMACtextField1() {
		if (dirMACtextField1 == null) {
			dirMACtextField1 = new TextField();
			dirMACtextField1.setBounds(new Rectangle(112, 128, 182, 25));
			dirMACtextField1.setEnabled(false);
			
			dirMACtextField1.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if(entitytextField.isEditable())
						showPopup(e);
	            }
	 
				public void mouseReleased (MouseEvent e) {
					if(entitytextField.isEditable())
						showPopup(e);
	            }
	            private void showPopup(MouseEvent e) {
	                if (e.isPopupTrigger()) {
	                    JPopupMenu popup = ((Provider)CompleteUpdate.this.getParent()).getJPopupMenuCopyAndPaste();
	                    Component c = e.getComponent();
	                    //System.out.println("Component text field:" + c);
	                    //System.out.println("Is Focusable" + c.isFocusable());
	                    popup.show(e.getComponent(), e.getX(), e.getY());
	                    ((Provider)CompleteUpdate.this.getParent()).setComponentThatTriggerCopyPasteMenu((TextField)c);
	                }
	            }
			});
		}
		return dirMACtextField1;
	}

	/**
	 * Calendar
	 */
	private JXDatePicker getXSwingDateChooser() {
		if (this.xSwingDateChooser == null) {
			this.xSwingDateChooser = new JXDatePicker();

			this.xSwingDateChooser.setBounds(new Rectangle(112, 166, 145, 25));
			this.xSwingDateChooser.setEnabled(false);

			xSwingDateChooser.setToolTipText("Día/Mes/Año");
			xSwingDateChooser.setDate(new Date());
			DateFormat df1 = new SimpleDateFormat("EE dd/MM/yyyy");
			DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat df3 = new SimpleDateFormat("yyyy");
			xSwingDateChooser.setFormats(df1, df2, df3);
			xSwingDateChooser.getMonthView().setZoomable(true);
		}
		return this.xSwingDateChooser;
	}

	/**
	 * This method initializes jSpinner
	 * 
	 * @return javax.swing.JSpinner
	 */

	/**
	 * This method initializes includejCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getIncludejCheckBox() {
		if (includejCheckBox == null) {
			includejCheckBox = new JCheckBox();
			includejCheckBox.setBounds(new Rectangle(14, 65, 113, 19));
			includejCheckBox.setText("Incluir licencia");
			includejCheckBox.setBackground(Color.white);
			includejCheckBox
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (includejCheckBox.isSelected()) {
								jLabel1.setEnabled(true);
								jLabel2.setEnabled(true);
								jLabel3.setEnabled(true);
								entitytextField.setEnabled(true);
								dirMACtextField1.setEnabled(true);
								getXSwingDateChooser().setEnabled(true);
							} else {
								jLabel1.setEnabled(false);
								jLabel2.setEnabled(false);
								jLabel3.setEnabled(false);
								entitytextField.setEnabled(false);
								dirMACtextField1.setEnabled(false);
								xSwingDateChooser.setEnabled(false);

							}
						}
					});
		}
		return includejCheckBox;
	}
	
	private Boolean hasValidFormat(String identifier)
	{
		return identifierPattern.matcher(identifier).matches();
	}
	
	private static Pattern identifierPattern = Pattern.compile("[0-9]{4}[-][0-9]{4}[-][0-9]{4}[-][0-9]{4}");  //  @jve:decl-index=0:


	/**
	 * This method initializes exportjButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getExportjButton() {
		if (exportjButton == null) {
			exportjButton = new JButton();
			exportjButton.setBounds(new Rectangle(262, 255, 83, 26));
			exportjButton.setEnabled(pathtextField.getText().length() > 0);
			exportjButton.setText("Exportar");
			exportjButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (exportjButton.getText().equals("Aceptar")) {
								CompleteUpdate.this.setVisible(false);
								return;
							}

							License license = null;
							if (includejCheckBox.isSelected())// include license
							{
								license = new License();
								license.setEntity(entitytextField.getText());
								try {
									license.setInstalationID(dirMACtextField1
											.getText());
								} catch (Exception e1) {
								}
								license.setValidDate(getXSwingDateChooser()
										.getDate());
								
								//validating identifier format
								if(!hasValidFormat(dirMACtextField1.getText()))
								{
									JOptionPane
									.showMessageDialog(
											null,
											"<html> Debe insertar un formato válido para el identificador. <br> Ejemplo: 3291-5689-2484-3561. </html>",
											"Generar licencia",
											JOptionPane.WARNING_MESSAGE);
							return;}

								Date licenseDate = xSwingDateChooser.getDate();
								if (licenseDate.before(Calendar.getInstance()
										.getTime()))// expired license date
								{
									int res = JOptionPane
											.showOptionDialog(
													null,
													"Está intentando generar una licencia con fecha de expiración vencida. ¿Desea continuar?",
													"Generar licencia",
													JOptionPane.YES_NO_CANCEL_OPTION,
													JOptionPane.QUESTION_MESSAGE,
													null, new Object[] { "Sí",
															"No", "Cancelar" },
													"No");
									if (res != 0)
										return;
								}
							}

							List<String> nonexistentDocs = null;

							// checking path to documents
							String path = ManagerSettings.getPathDocument();

							if (path == null) {
								JOptionPane
										.showMessageDialog(
												null,
												"Por favor, especifique la ruta de acceso a los documentos en la opción 'Configurar Sistema'.",
												"Exportando el fichero de actualización",
												JOptionPane.ERROR_MESSAGE);
								return;
							}
							
							

							File docsFile = new File(path);

							if (!docsFile.exists() || !docsFile.isDirectory()) {
								JOptionPane
										.showMessageDialog(
												null,
												"No se encuentra la ruta especificada para acceder a los documentos \n("
														+ ManagerSettings
																.getPathDocument()
														+ "). \nConfigure esta direccción en la opción 'Configurar Sistema'.",
												"Exportando el fichero de actualización",
												JOptionPane.ERROR_MESSAGE);
								return;
							}

							// the path to documents and the path of updateFile
							// are fine
							try {

								if (!Controler.existDataToShow()) {
									JOptionPane
											.showMessageDialog(
													null,
													convertToMultiline("La aplicación no ha cargado aún los datos para realizar actualizaciones. \n Realice la importación desde la base de datos en la opción 'Configurar Sistema'."),
													"No existen datos para exportar",
													JOptionPane.INFORMATION_MESSAGE);
									return;
								}

								pBarPanel.setVisible(true);
								pBarPanel.setStringPainted(true);

								// execute complete update
								// getting update file and modified but
								// nonexistent docs
								nonexistentDocs = Controler
										.exportCompleteUpdate(pathtextField
												.getText(), license, pBarPanel);
								// nonexistentDocs = new
								// ArrayList<String>();//Only for test the
								// message below...
								// no errors
								if (nonexistentDocs.size() == 0) {
									// JOptionPane.showMessageDialog(null,
									// "Concluida la exportación satisfactoriamente");
									int result = JOptionPane
											.showOptionDialog(
													null,
													// params,
													convertToMultiline("Concluida la exportación satisfactoriamente."),
													"Actualización completa",
													JOptionPane.YES_NO_OPTION,
													JOptionPane.INFORMATION_MESSAGE,
													null,
													new Object[] {
															"Volver a Exportar",
															"OK" }, "OK");

									finalizeExport(result);
									return;
								}

								// there are docs that not exist in the
								// specified documents folder
								if (nonexistentDocs.size() > 0) {
									UpdateExportWarnings warnings = new UpdateExportWarnings(
											CompleteUpdate.this,
											"Actualización completa concluida",
											null, nonexistentDocs);
									warnings.setVisible(true);
								}
							}

							catch (IOException e1) {
								// deleting temporals
								File temp = new File("generalInfo");
								File temp2 = new File("Documents");
								temp.delete();
								temp2.delete();

								JOptionPane
										.showMessageDialog(
												null,
												convertToMultiline("Ha ocurrido un error durante la exportación del fichero de actualización completa. \nPor favor, verifique la dirección y el nombre del fichero y vuelva a intentarlo."),
												"Exportación de actualización completa",
												JOptionPane.ERROR_MESSAGE);
								pBarPanel.setVisible(false);
								pBarPanel.setValue(0);
							}
						}
					});
		}
		return exportjButton;
	}

	private void finalizeExport(int result) {
		// OK
		if (result == 1) {
			CompleteUpdate.this.setVisible(false);
			if (jCheckBoxOnDisk.isSelected())// locate on disk update file
			{
				// Desktop.getDesktop().open(new
				// File(pathtextField.getText()).getParentFile());
				String quoted = "\"" + pathtextField.getText() + "\"";
				try {
					Process p = new ProcessBuilder("explorer.exe", "/select,",
							quoted).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane
							.showMessageDialog(
									null,
									"Ha ocurrido un problema abriendo el explorador desde este sistema operativo. El fichero de actualización ha sido guardado en: "
											+ pathtextField.getText() + ".");
				}
			}
		}
		// Reexport
		else {
			pBarPanel.setValue(0);
			pBarPanel.setVisible(false);
		}
	}

	private static String convertToMultiline(String text) {
		return "<html>" + text.replaceAll("\n", "<br>") + "</html>";
	}

	/**
	 * This method initializes jProgressBar
	 * 
	 * @return javax.swing.JProgressBar
	 */
	private ProgressBarPanel getPanelBar() {
		if (pBarPanel == null) {
			pBarPanel = new ProgressBarPanel();
			pBarPanel.setBounds(new Rectangle(15, 260, 241, 21));
			pBarPanel.setEnabled(true);
			pBarPanel.setVisible(false);
		}
		return pBarPanel;
	}

	/**
	 * This method initializes jCheckBoxOnDisk
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxOnDisk() {
		if (jCheckBoxOnDisk == null) {
			jCheckBoxOnDisk = new JCheckBox();
			jCheckBoxOnDisk.setBounds(new Rectangle(14, 213, 332, 21));
			jCheckBoxOnDisk
					.setText("Localizar en disco al concluir la exportación");
			jCheckBoxOnDisk.setBackground(Color.white);
		}
		return jCheckBoxOnDisk;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
