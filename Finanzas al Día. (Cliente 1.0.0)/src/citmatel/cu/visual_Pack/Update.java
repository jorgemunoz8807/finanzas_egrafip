package citmatel.cu.visual_Pack;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JButton;
import citmatel.cu.class_Pack.Controler;
import citmatel.cu.class_Pack.LicenseState;
import citmatel.cu.class_Pack.ManagerDoc;
import citmatel.cu.class_Pack.Utils;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.TextField;

public class Update extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel1 = null;
	private JButton jButtonBrowse = null;
	private JButton jButtonUpdate = null;
	private JButton jButtonCancel = null;
	private TextField textField = null;
	private ProgressBarPanel pbar = null;
	private Client parent;

	/**
	 * This is the default constructor
	 */
	public Update(Client parent) {
		super(parent);
		this.parent = parent;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(372, 180);
		this.setContentPane(getJContentPane());
		this.setTitle("Actualización");
		this.setModal(true);
		this.setResizable(false);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				Update.this.setVisible(false);
				if (Controler.getApplicationLicenseState() != LicenseState.valid)
					parent.showExpiredLicenseMessage(Controler
							.getApplicationLicenseState(), "update close");
			}
		});
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
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(16, 12, 249, 21));
			jLabel1.setText("Ubicación del fichero de actualización:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(Color.white);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJButtonBrowse(), null);
			jContentPane.add(getJButtonUpdate(), null);
			jContentPane.add(getJButtonCancel(), null);
			jContentPane.add(getTextField(), null);
			jContentPane.add(getPbar(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButtonBrowse
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonBrowse() {
		if (jButtonBrowse == null) {
			jButtonBrowse = new JButton();
			jButtonBrowse.setBounds(new Rectangle(317, 38, 34, 25));
			jButtonBrowse.setText("...");
			jButtonBrowse
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							JFileChooser jfc = new JFileChooser();
							jfc.setAcceptAllFileFilterUsed(false);
							
							if(ManagerDoc.HasDocuments()) //only allow import compact update if there are already documents in app
							{
								jfc
									.addChoosableFileFilter(new AllUpdateFilesFilter());
								jfc
								.addChoosableFileFilter(new CompactUpdateFilesFilter());
								
							}
							jfc
							.addChoosableFileFilter(new CompleteUpdateFilesFilter());
							

							jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
							if (jfc.showOpenDialog(null) == 0) {
								textField.setText(jfc.getSelectedFile()
										.getAbsolutePath());
								jButtonUpdate.setEnabled(true);
								jButtonUpdate.setText("Actualizar");

							}
						}

					});
		}
		return jButtonBrowse;
	}

	private static String convertToMultiline(String text) {
		return "<html>" + text.replaceAll("\n", "<br>") + "</html>";
	}

	/**
	 * This method initializes jButtonUpdate
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonUpdate() {
		if (jButtonUpdate == null) {
			jButtonUpdate = new JButton();
			jButtonUpdate.setBounds(new Rectangle(167, 109, 91, 26));
			jButtonUpdate.setEnabled(false);
			jButtonUpdate.setText("Actualizar");
			jButtonUpdate
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (jButtonUpdate.getText().equals("Aceptar")) {
								Update.this.setVisible(false);
								return;
							}

							pbar.setVisible(true);

							File updateFile = new File(textField.getText());

							// Inexistent update file
							if (!updateFile.exists()) {
								JOptionPane
										.showMessageDialog(null,
												"No se encuentra el fichero especificado");
								return;
							}

							String updateFileExtension = Utils
									.getExtension(updateFile);

							// Incorrect update file
							if (!updateFileExtension
									.equals(CompleteUpdateFilesFilter.validExtension)
									&& !updateFileExtension
											.equals(CompactUpdateFilesFilter.validExtension)) {
								JOptionPane
										.showMessageDialog(
												null,
												"Fichero de actualización incorrecto. Las extensiones válidas son: "
														+ "'."
														+ CompleteUpdateFilesFilter.validExtension
														+ "'"
														+ ", '."
														+ CompactUpdateFilesFilter.validExtension
														+ "'");
								return;
							}

							// Importing complete update......
							if (updateFileExtension
									.equals(CompleteUpdateFilesFilter.validExtension)) {
								try {
									System.out
											.println("Realizando act. completa");
									Boolean licenseIncluded = Controler
											.importCompleteUpdate(textField
													.getText(), pbar);
									// finished 90%
									Client.ReloadDataInDocTree();
									pbar.updateBar(96); // updating progressbar
									Client.ReloadDataInModTree();
									pbar.updateBar(100); // updating progressbar
									Update.this.parent.enablingSidePanel();// updating
									// sidebar
									showMessageOfImportCompleteUpdate(licenseIncluded);

								} catch (Exception e1) {
									System.out.println("excepcion en update en ImportComplete");
									System.out.println(e1.getMessage());
									e1.printStackTrace();
									// deleting temporals
									File temp = new File(Controler.appDataPath
											+ "\\generalInfo");
									temp.delete();
									File temp2 = new File(Controler.appDataPath
											+ "\\Documents");
									temp2.delete();

									manageImportingUpdatesExceptions(e1);
									jButtonUpdate.setEnabled(false);
									pbar.setVisible(false);
									pbar.updateBar(0);

								}
							}

							// Importing compact update......................
							
							if (updateFileExtension
									.equals(CompactUpdateFilesFilter.validExtension)) {
								
								//asking if delete old modifications
								if(Controler.getApplicationSettings().getAskIfDeleteOldModifications() && ManagerDoc.getModifications().size()>0)
								//if(Controler.getApplicationSettings().getAskIfDeleteOldModifications())
								{
									int result = showConfirmationForDeleteOldModifications();
									if(result == -1 || result == 2) //go back if close or cancel message box
									{
										pbar.setValue(0);
										pbar.setVisible(false);
										return;
									}
								}
								
								try {
									System.out
											.println("Realizando act. compacta");
									int initialModificationsCount = ManagerDoc
											.getModifications() == null ? 0
											: ManagerDoc.getModifications()
													.size();
									Boolean licenseIncluded = Controler
											.importCompactUpdate(textField
													.getText(), pbar);
									// finished 98%
									Client.ReloadDataInDocTree();
									pbar.updateBar(99); // updating progressbar
									Client.ReloadDataInModTree();
									pbar.updateBar(100); // updating
									// progress

									int newModificationsCount = ManagerDoc
											.getModifications().size();
									int newImportedModificationsCount = Controler
											.getApplicationSettings().getDeleteOldModifications() 
											? newModificationsCount
											: newModificationsCount
													- initialModificationsCount;
									showMessageOfImportCompactUpdate(
											licenseIncluded,
											newImportedModificationsCount);
								} catch (Exception e1) {
									// deleting temporals
									File temp = new File(Controler.appDataPath
											+ "\\generalInfo");
									temp.delete();
									File temp2 = new File(Controler.appDataPath
											+ "\\Documents");
									temp2.delete();

									manageImportingUpdatesExceptions(e1);
									pbar.updateBar(0);
									pbar.setVisible(false);

								}
							}

						}
					});

		}
		return jButtonUpdate;
	}
	
	private int showConfirmationForDeleteOldModifications()
	{	
		JPanel pane = new JPanel();
		//FlowLayout flowLayout = new FlowLayout();
		//flowLayout.setAlignment(java.awt.FlowLayout.TRAILING);
		//pane.setLayout(flowLayout);
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		JLabel jLabelMessage = new JLabel(convertToMultiline("¿Desea borrar las viejas modificaciones al importar esta actualización compacta?"));
		JCheckBox jCheckBoxRememberDecision = new JCheckBox("Recordar mi decisión.");
		pane.add(jLabelMessage);
		pane.add(jCheckBoxRememberDecision);
		
		int result = JOptionPane
							.showOptionDialog(
									null,
									pane,
									"Importando nuevas modificaciones", 
									JOptionPane.YES_NO_CANCEL_OPTION,
									JOptionPane.QUESTION_MESSAGE,
									null,
									new Object[] { "Sí", "No", "Cancelar" }, "No"
									);
		
		Boolean rememberMyDecision = jCheckBoxRememberDecision.isSelected();
		if(result == 0) //yes delete old modifications
			{
				try {
					Controler.SaveInSettings(true, !rememberMyDecision);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "No se pudo guardar la nueva configuración", "Guardado configuración", JOptionPane.ERROR_MESSAGE);
				}
			}
		if(result == 1) //no
			{
				try {
					Controler.SaveInSettings(false, !rememberMyDecision);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "No se pudo guardar la nueva configuración", "Guardado configuración", JOptionPane.ERROR_MESSAGE);
				}
			}
		
		return result;
		
								
	}
	private void showMessageOfImportCompactUpdate(Boolean licenseIncluded,
			int importingModificationsCount) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String licenseInfo = licenseIncluded ? "Su licencia ha sido activada y es válida hasta: "
				+ df.format(Controler.getApplicationLicense().getValidDate())
				+ "."
				: "";
		String importingInfo = null;
		if (importingModificationsCount == 0)
			importingInfo = "El fichero de actualización compacta importado no contiene ninguna nueva modificación.";
		else
			importingInfo = (importingModificationsCount == 1) ? "Se ha importado satisfactoriamente 1 modificación."
					: "Se han importado satisfactoriamente "
							+ importingModificationsCount
							+ " modificaciones.";
		
		String anotherUpdateQuestion = "¿Desea realizar otra actualización?";

		int result = JOptionPane.showOptionDialog(Update.this,
				convertToMultiline(importingInfo + "\n" + licenseInfo + "\n" + anotherUpdateQuestion),
				"Actualización concluida", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "Sí",
						"No" }, "No");
		if (result != 0)
			Update.this.setVisible(false);
		else // back to update window
		{
			jButtonUpdate.setEnabled(false);
			pbar.setValue(0);
			pbar.setVisible(false);
		}

		// jButtonUpdate.setText("Aceptar");

	}

	private void showMessageOfImportCompleteUpdate(Boolean licenseIncluded) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String licenseInfo = licenseIncluded ? convertToMultiline("\n Su licencia ha sido activada y es válida hasta: "
				+ df.format(Controler.getApplicationLicense().getValidDate())
				+ ".")
				: "";
		String message = convertToMultiline("Los datos de la aplicación han sido actualizados satisfactoriamente."
				+ licenseInfo)
				+ "\n ¿Desea realizar otra actualización?";
		int result = JOptionPane.showOptionDialog(Update.this, message,
				"Actualización concluida", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "Sí",
						"No" }, "No");
		if (result != 0)
			Update.this.setVisible(false);
		else // back to update window
		{
			jButtonUpdate.setEnabled(false);
			pbar.setValue(0);
			pbar.setVisible(false);
		}

	}

	/**
	 * Controlar las excepciones de los métodos Importar actualización completa
	 * e Importar actualización compacta
	 * 
	 * @param excep
	 */
	private static void manageImportingUpdatesExceptions(Exception excep) {
		String message = excep.getMessage();
		if(message != null)
		{
			
		// the current os is incompatible with Windows or Linux
		if (excep.getMessage().equals("incompatible os")) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error determinando el identificador de su máquina. El SO actual es incompatible.","Validando licencia", JOptionPane.ERROR_MESSAGE);
			return;
				}
		
		if (message.equals("license deactivated")) {
			JOptionPane
					.showMessageDialog(
							null,
							convertToMultiline("La licencia para esta aplicación está desactivada o es incorrecta y el fichero de actualización que está importando \n no contiene una activación de licencia."),
							"Licencia desactivada", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (message.equals("importing incorrect license")) {
			JOptionPane
					.showMessageDialog(
							null,
							convertToMultiline("El fichero de actualización que está importando contiene una activación de licencia \n que es incorrecta para la instalación de su máquina."),
							"Licencia inválida", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (message.equals("importing expired license")) {
			JOptionPane
					.showMessageDialog(
							null,
							convertToMultiline("El fichero de actualización que está importando contiene una activación de licencia vencida."),
							"Licencia inválida", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (message.equals("license expired")) {
			JOptionPane
					.showMessageDialog(
							null,
							convertToMultiline("La licencia para esta aplicación ha expirado. \n Importe una actualización que contenga activación de licencia o realice una activación de la licencia de su aplicación."),
							"Licencia expirada", JOptionPane.ERROR_MESSAGE);
			return;
		}
		}
		JOptionPane.showMessageDialog(null,
				"El formato del fichero de actualización es incorrecto.",
				"Error durante la actualización", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * This method initializes jButtonCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setBounds(new Rectangle(266, 109, 85, 26));
			jButtonCancel.setText("Cancelar");
			jButtonCancel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Update.this.setVisible(false);
							if (Controler.getApplicationLicenseState() != LicenseState.valid)
								parent.showExpiredLicenseMessage(Controler
										.getApplicationLicenseState(), "update cancel");
						}
					});
		}
		return jButtonCancel;
	}

	/**
	 * This method initializes textField
	 * 
	 * @return java.awt.TextField
	 */
	private TextField getTextField() {
		if (textField == null) {
			textField = new TextField();
			textField.setEditable(false);
			textField.setBounds(new Rectangle(16, 38, 291, 25));
			textField.addTextListener(new java.awt.event.TextListener() {
				public void textValueChanged(java.awt.event.TextEvent e) {
					jButtonUpdate.setEnabled(textField.getText().length() > 0);
				}

			});
		}
		return textField;
	}

	/**
	 * This method initializes pbar
	 * 
	 * @return javax.swing.ProgressBarPanel
	 */
	private ProgressBarPanel getPbar() {
		if (pbar == null) {
			pbar = new ProgressBarPanel();
			pbar.setBounds(new Rectangle(14, 84, 337, 17));
			pbar.setVisible(false);
			pbar.setStringPainted(true);
		}
		return pbar;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
