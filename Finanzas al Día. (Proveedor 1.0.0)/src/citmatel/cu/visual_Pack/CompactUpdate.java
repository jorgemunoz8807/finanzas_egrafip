package citmatel.cu.visual_Pack;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.jdesktop.swingx.JXDatePicker;

import citmatel.cu.class_Pack.CompactUpdateInfoResult;
import citmatel.cu.class_Pack.Controler;
import citmatel.cu.class_Pack.License;
import citmatel.cu.class_Pack.ManagerSettings;
import citmatel.cu.class_Pack.Utils;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

public class CompactUpdate extends JDialog {

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
	private JCheckBox fromjCheckBox = null;
	private JCheckBox tojCheckBox = null;
	public ProgressBarPanel pbar = null;

	private JXDatePicker xSwingDateChooser = null;
	private JXDatePicker xSwingDateChooser1 = null;
	private JXDatePicker xSwingDateChooser11 = null;
	public JCheckBox jCheckBoxOnDisk = null;

	/**
	 * @param owner
	 */
	public CompactUpdate(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(341, 380);
		this.setTitle("Actualización compacta");
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
			jLabel3.setBounds(new Rectangle(4, 234, 94, 16));
			jLabel3.setEnabled(false);
			jLabel3.setText("Válida hasta");
			jLabel3.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabel2 = new JLabel();
			jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel2.setBounds(new Rectangle(4, 198, 94, 16));
			jLabel2.setEnabled(false);
			jLabel2.setText("Identificador");
			jLabel2.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(4, 164, 94, 16));
			jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel1.setEnabled(false);
			jLabel1.setText("Empresa");
			jLabel1.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(13, 14, 267, 16));
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
			jContentPane.add(getFromjCheckBox(), null);
			jContentPane.add(getToCheckBox(), null);
			jContentPane.add(getPbar(), null);
			jContentPane.add(getXSwingDateChooser(), null);
			jContentPane.add(getXSwingDateChooser1(), null);
			jContentPane.add(getXSwingDateChooser11(), null);
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
			pathtextField.setEditable(false);
			pathtextField.setBounds(new Rectangle(12, 39, 260, 25));
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

	private void allowExport() {
		exportjButton.setEnabled(true);
		exportjButton.setText("Exportar");
	}

	/**
	 * This method initializes pathjButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getPathjButton() {
		if (pathjButton == null) {
			pathjButton = new JButton();
			pathjButton.setBounds(new Rectangle(280, 40, 43, 25));
			pathjButton.setText("...");
			pathjButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					JFileChooser jfc = new JFileChooser() {
						private static final long serialVersionUID = 1L;

						@Override
						public void approveSelection() {
							File selectedFile = getSelectedFile();
							if (Utils.getExtension(selectedFile) == null)
								setSelectedFile(new File(
										getSelectedFile().getAbsolutePath()
												+ "."
												+ CompactUpdateFilesFilter.validExtension));
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
					jfc.setFileFilter(new CompactUpdateFilesFilter());
					if (jfc.showSaveDialog(null) == 0) {
						String exportFileName = jfc.getSelectedFile()
								.getAbsolutePath();
						if (Utils.getExtension(new File(exportFileName)) == null)
							exportFileName += "."
									+ CompactUpdateFilesFilter.validExtension;
						pathtextField.setText(exportFileName);
						allowExport();

					}
					System.out.println("mouseClicked()"); // TODO Auto-generated
					// Event stub
					// mouseClicked()
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
			entitytextField.setBounds(new Rectangle(105, 164, 199, 25));
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
	                    JPopupMenu popup = ((Provider)CompactUpdate.this.getParent()).getJPopupMenuCopyAndPaste();
	                    Component c = e.getComponent();
	                    //System.out.println("Component text field:" + c);
	                    //System.out.println("Is Focusable" + c.isFocusable());
	                    popup.show(e.getComponent(), e.getX(), e.getY());
	                    ((Provider)CompactUpdate.this.getParent()).setComponentThatTriggerCopyPasteMenu((TextField)c);
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
			dirMACtextField1.setBounds(new Rectangle(105, 198, 168, 25));
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
	                    JPopupMenu popup = ((Provider)CompactUpdate.this.getParent()).getJPopupMenuCopyAndPaste();
	                    Component c = e.getComponent();
	                    //System.out.println("Component text field:" + c);
	                    //System.out.println("Is Focusable" + c.isFocusable());
	                    popup.show(e.getComponent(), e.getX(), e.getY());
	                    ((Provider)CompactUpdate.this.getParent()).setComponentThatTriggerCopyPasteMenu((TextField)c);
	                }
	            }
			});
		}
		return dirMACtextField1;
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
			includejCheckBox.setBounds(new Rectangle(13, 139, 226, 19));
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
								xSwingDateChooser11.setEnabled(true);
							} else {
								jLabel1.setEnabled(false);
								jLabel2.setEnabled(false);
								jLabel3.setEnabled(false);
								entitytextField.setEnabled(false);
								dirMACtextField1.setEnabled(false);
								xSwingDateChooser11.setEnabled(false);

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
			exportjButton.setBounds(new Rectangle(240, 307, 83, 26));
			exportjButton.setText("Exportar");
			exportjButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							/*
							 * if(exportjButton.getText().equals("Aceptar")) {
							 * CompactUpdate.this.setVisible(false); return; }
							 */

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
								license.setValidDate(xSwingDateChooser11
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

								Date licenseDate = xSwingDateChooser11.getDate();
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

							// filter by date
							Date from = null;
							Date to = null;
							if (fromjCheckBox.isSelected())
								from = (Date) xSwingDateChooser.getDate();

							if (tojCheckBox.isSelected())
								to = (Date) xSwingDateChooser1.getDate();

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
								pbar.setVisible(true);
								pbar.setStringPainted(true);

								// exporting compact update
								CompactUpdateInfoResult info = Controler
										.exportCompactUpdate(pathtextField
												.getText(), license, from, to,
												pbar);

								// no modification fulfills with the filter of
								// dates
								if (info.getFilterModifications() == 0) {

									int result = JOptionPane
											.showOptionDialog(
													null,
													convertToMultiline("No existen ninguna modificación comprendida entre las fechas especificadas. \n No es necesario realizar una actualización compacta en este período."),
													"Actualización compacta",
													JOptionPane.INFORMATION_MESSAGE,
													JOptionPane.YES_NO_OPTION,
													null,
													new Object[] { "OK",
															"Volver a Exportar" },
													"OK");

									if (result == 0) // OK
										CompactUpdate.this.setVisible(false);
									else // Reexport
									{
										pbar.setValue(0);
										pbar.setVisible(false);
									}
									return;
								}

								// the update file has been created
								List<String> nonexistentDocs = info
										.getNonexistenDocs();
								List<String> nonInBDDocs = info
										.getNonInBDDocs();
								int countOfNonexistents = nonexistentDocs
										.size();
								int countOfNonInBD = nonInBDDocs.size();

								// no errors
								if (countOfNonexistents == 0
										&& countOfNonInBD == 0)

								{

									int result = JOptionPane
											.showOptionDialog(
													null,
													// params,
													convertToMultiline("Concluida la exportación satisfactoriamente. \n Han sido exportadas "
															+ info
																	.getFilterModifications()
															+ " modificaciones."),
													"Actualización compacta",
													JOptionPane.YES_NO_OPTION,
													JOptionPane.INFORMATION_MESSAGE,
													null,
													new Object[] {
															"Volver a Exportar",
															"OK" }, "OK");

									finalizeExport(result);
									return;
								}

								// there are docs that not exist in BD OR not
								// exist in specified the documents folder
								UpdateExportWarnings warnings = new UpdateExportWarnings(
										CompactUpdate.this,
										"Actualización compacta concluida",
										nonInBDDocs, nonexistentDocs);
								warnings.setVisible(true);

							} catch (IOException e1) {
								// deleting temporals
								File temp = new File("generalInfo");
								File temp2 = new File("Documents");
								temp.delete();
								temp2.delete();

								JOptionPane
										.showMessageDialog(
												null,
												convertToMultiline("Ha ocurrido un error durante la exportación del fichero de actualización compacta. \n Por favor, verifique la dirección y el nombre del fichero y vuelva a intentarlo."),
												"Exportación de actualización compacta",
												JOptionPane.ERROR_MESSAGE);
								pbar.setVisible(false);
								pbar.setValue(0);
							}
						}
					});
			exportjButton.setEnabled(pathtextField.getText().length() > 0);
		}
		return exportjButton;
	}

	private void finalizeExport(int result) {
		// OK
		if (result == 1) {
			CompactUpdate.this.setVisible(false);
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
			pbar.setValue(0);
			pbar.setVisible(false);
		}
	}

	private static String convertToMultiline(String text) {
		return "<html>" + text.replaceAll("\n", "<br>") + "</html>";
	}

	/**
	 * Calendar
	 */
	private JXDatePicker getXSwingDateChooser() {
		if (this.xSwingDateChooser == null) {
			this.xSwingDateChooser = new JXDatePicker();

			this.xSwingDateChooser.setEnabled(false);
			xSwingDateChooser.setSize(new Dimension(145, 25));
			xSwingDateChooser.setLocation(new Point(13, 99));

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
	 * This method initializes jProgressBar
	 * 
	 * @return javax.swing.JProgressBar
	 */
	private ProgressBarPanel getPbar() {
		if (pbar == null) {
			pbar = new ProgressBarPanel();
			pbar.setBounds(new Rectangle(13, 309, 208, 20));
			pbar.setVisible(false);
		}
		return pbar;
	}

	/**
	 * This method initializes xSwingDateChooser1
	 * 
	 * @return org.jdesktop.swingx.JXDatePicker
	 */
	private JXDatePicker getXSwingDateChooser1() {
		if (xSwingDateChooser1 == null) {
			xSwingDateChooser1 = new JXDatePicker();
			xSwingDateChooser1.setBounds(new Rectangle(181, 99, 145, 25));
			xSwingDateChooser1.setEnabled(false);

			xSwingDateChooser1.setToolTipText("Día/Mes/Año");
			xSwingDateChooser1.setDate(new Date());
			DateFormat df1 = new SimpleDateFormat("EE dd/MM/yyyy");
			DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat df3 = new SimpleDateFormat("yyyy");
			xSwingDateChooser1.setFormats(df1, df2, df3);
			xSwingDateChooser1.getMonthView().setZoomable(true);
		}
		return xSwingDateChooser1;
	}

	/**
	 * This method initializes xSwingDateChooser11
	 * 
	 * @return org.jdesktop.swingx.JXDatePicker
	 */
	private JXDatePicker getXSwingDateChooser11() {
		if (xSwingDateChooser11 == null) {
			xSwingDateChooser11 = new JXDatePicker();
			xSwingDateChooser11.setBounds(new Rectangle(105, 234, 145, 25));
			xSwingDateChooser11.setEnabled(false);

			xSwingDateChooser11.setToolTipText("Día/Mes/Año");
			xSwingDateChooser11.setDate(new Date());
			DateFormat df1 = new SimpleDateFormat("EE dd/MM/yyyy");
			DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
			xSwingDateChooser11.setFormats(df1, df2);
			xSwingDateChooser11.getMonthView().setZoomable(true);
		}
		return xSwingDateChooser11;
	}

	/**
	 * This method initializes fromjCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getFromjCheckBox() {
		if (fromjCheckBox == null) {
			fromjCheckBox = new JCheckBox();
			fromjCheckBox.setBounds(new Rectangle(13, 73, 134, 22));
			fromjCheckBox.setText("Desde");
			fromjCheckBox.setBackground(Color.WHITE);
			fromjCheckBox
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (fromjCheckBox.isSelected())
								xSwingDateChooser.setEnabled(true);
							else
								xSwingDateChooser.setEnabled(false);
							System.out.println("actionPerformed()"); // TODO
							// Auto-generated
							// Event
							// stub
							// actionPerformed()
						}
					});
		}
		return fromjCheckBox;
	}

	/**
	 * This method initializes toCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getToCheckBox() {
		if (tojCheckBox == null) {
			tojCheckBox = new JCheckBox();
			tojCheckBox.setBounds(new Rectangle(181, 73, 133, 21));
			tojCheckBox.setText("Hasta");
			tojCheckBox.setBackground(Color.WHITE);
			tojCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tojCheckBox.isSelected())
						xSwingDateChooser1.setEnabled(true);
					else
						xSwingDateChooser1.setEnabled(false);
					System.out.println("actionPerformed()"); // TODO
					// Auto-generated
					// Event stub
					// actionPerformed()
				}
			});
		}
		return tojCheckBox;
	}

	/**
	 * This method initializes jCheckBoxOnDisk
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxOnDisk() {
		if (jCheckBoxOnDisk == null) {
			jCheckBoxOnDisk = new JCheckBox();
			jCheckBoxOnDisk.setBounds(new Rectangle(13, 273, 317, 21));
			jCheckBoxOnDisk
					.setText("Localizar en disco al concluir la exportación");
			jCheckBoxOnDisk.setBackground(Color.white);
		}
		return jCheckBoxOnDisk;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
