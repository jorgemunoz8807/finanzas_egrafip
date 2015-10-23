package citmatel.cu.visual_Pack;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JButton;
import citmatel.cu.class_Pack.Controler;
import citmatel.cu.class_Pack.LicenseState;
import citmatel.cu.class_Pack.ManagerDoc;
import citmatel.cu.class_Pack.Utils;

import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.TextField;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JProgressBar;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LicenseActivation extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel1 = null;
	private JButton jButtonBrowse = null;
	private JButton jButtonActivate = null;
	private JButton jButtonCancel = null;
	private TextField textField = null;
	private JButton jButtonRenew = null;
	private Client parent = null;
	private ProgressBarPanel pbar = null;
	/**
	 * This is the default constructor
	 */
	public LicenseActivation(Client parent) {
		super();
		this.parent = parent;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(369, 180);
		this.setContentPane(getJContentPane());
		this.setTitle("Gestor de licencias");
		this.setModal(true);
		this.setResizable(false);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				LicenseActivation.this.setVisible(false);
				if (Controler.getApplicationLicenseState() != LicenseState.valid)
					parent.showExpiredLicenseMessage(Controler
							.getApplicationLicenseState(), "license activation window closing");
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
			jLabel1.setBounds(new Rectangle(14, 12, 249, 21));
			jLabel1.setText("Ubicación del fichero de licencia:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(Color.white);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJButtonBrowse(), null);
			jContentPane.add(getJButtonActivate(), null);
			jContentPane.add(getJButtonCancel(), null);
			jContentPane.add(getTextField(), null);
			jContentPane.add(getJButtonRenew(), null);
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
			jButtonBrowse.setBounds(new Rectangle(310, 38, 37, 25));
			jButtonBrowse.setText("...");
			jButtonBrowse
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							JFileChooser jfc = new JFileChooser();
							jfc.setAcceptAllFileFilterUsed(false);
							jfc
									.addChoosableFileFilter(new LicenseFilesFilter());
							jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
							if (jfc.showOpenDialog(null) == 0) {
								textField.setText(jfc.getSelectedFile()
										.getAbsolutePath());
								jButtonActivate.setEnabled(true);
								jButtonActivate.setText("Activar");

							}
						}

					});
		}
		return jButtonBrowse;
	}

	/**
	 * This method initializes jButtonUpdate
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonActivate() {
		if (jButtonActivate == null) {
			jButtonActivate = new JButton();
			jButtonActivate.setBounds(new Rectangle(170, 112, 85, 26));
			jButtonActivate.setEnabled(false);
			jButtonActivate.setText("Activar");
			jButtonActivate
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (jButtonActivate.getText().equals("Aceptar")) {
								LicenseActivation.this.setVisible(false);
								return;
							}

							pbar.setVisible(true);
							String licenseFileName = textField.getText();
							File licenseFile = new File(licenseFileName);

							// Inexistent update file
							if (!licenseFile.exists()) {
								JOptionPane
										.showMessageDialog(null,
												"No se encuentra el fichero especificado");
								return;
							}
							pbar.updateBar(5);

							String activateFileExtension = Utils
									.getExtension(licenseFile);

							// Incorrect update file
							if (!activateFileExtension
									.equals(LicenseFilesFilter.validExtension)
									&& !activateFileExtension
											.equals(CompactUpdateFilesFilter.validExtension)) {
								JOptionPane
										.showMessageDialog(
												null,
												"Fichero de actualización incorrecto. La extensión válida es: "
														+ "'."
														+ LicenseFilesFilter.validExtension
														+ "'.");
								return;
							}
							pbar.updateBar(10);

							// Importing licence
							if (activateFileExtension
									.equals(LicenseFilesFilter.validExtension)) {
								try {
									Controler.importLicense(licenseFileName, pbar);
									jButtonActivate.setText("Aceptar");
									DateFormat df = new SimpleDateFormat(
											"dd-MM-yyyy");
									JOptionPane
											.showMessageDialog(
													null,
													"Su licencia ha sido activada y es válida hasta: "
															+ df
																	.format(Controler
																			.getApplicationLicense()
																			.getValidDate())
															+ ".",
													"Activación de licencia",
													JOptionPane.INFORMATION_MESSAGE);
									LicenseActivation.this.setVisible(false);

								} catch (Exception e1) {
									if (e1.getMessage() != null) {
										String message = null;
										if (e1.getMessage().equals(
												"importing expired license")) {
											message = convertToMultiline("La licencia que está tratando de activar está vencida.\n Por favor, contacte con su proveedor para obtener una licencia válida.");
											JOptionPane.showMessageDialog(null,
													message,
													"Licencia inválida",
													JOptionPane.ERROR_MESSAGE);
											return;
										}

										if (e1.getMessage().equals(
												"importing incorrect license")) {
											message = convertToMultiline("La licencia que está tratando de activar es incorrecta para la instalación de su máquina.\n Por favor, contacte con su proveedor para obtener una licencia válida.");
											JOptionPane.showMessageDialog(null,
													message,
													"Licencia inválida",
													JOptionPane.ERROR_MESSAGE);
											return;
										}
										
										// the current os is incompatible with Windows or Linux
										if (e1.getMessage().equals("incompatible os")) {
											JOptionPane.showMessageDialog(null, "Ha ocurrido un error determinando el identificador de su máquina. El SO actual es incompatible.","Validando licencia", JOptionPane.ERROR_MESSAGE);
											return;
												}
									}

									JOptionPane
											.showMessageDialog(
													null,
													"Ha ocurrido un error durante la activación de la licencia. \n El fichero de licencia especificado tiene un formato incorrecto.",
													"Activación de licencia",
													JOptionPane.ERROR_MESSAGE);
								}
							}

						}
					});

		}
		return jButtonActivate;
	}

	private static String convertToMultiline(String text) {
		return "<html>" + text.replaceAll("\n", "<br>") + "</html>";
	}

	/**
	 * This method initializes jButtonCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setBounds(new Rectangle(262, 112, 85, 26));
			jButtonCancel.setText("Cancelar");
			jButtonCancel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							LicenseActivation.this.setVisible(false);
							if (Controler.getApplicationLicenseState() != LicenseState.valid)
								parent.showExpiredLicenseMessage(Controler
										.getApplicationLicenseState(), "license activation cancel");
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
			textField.setBounds(new Rectangle(14, 38, 291, 25));
			textField.addTextListener(new java.awt.event.TextListener() {
				public void textValueChanged(java.awt.event.TextEvent e) {
					jButtonActivate
							.setEnabled(textField.getText().length() > 0);
				}
			});
		}
		return textField;
	}

	public static void WriteIdentifier(String AddressFile, String id)
			throws Exception {
		if (id != null) {
			// create file
			File f = new File(AddressFile);
			f.delete();
			if (!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(AddressFile);
			OutputStreamWriter oos = new OutputStreamWriter(fos, "utf-8");
			oos.write(id);
			oos.flush();
			oos.close();
		}
	}

	private static File getSelectedFileWithExtension(JFileChooser c) {
		File file = c.getSelectedFile();
		if (c.getFileFilter() instanceof FileNameExtensionFilter) {
			String[] exts = ((FileNameExtensionFilter) c.getFileFilter())
					.getExtensions();
			String nameLower = file.getName().toLowerCase();
			for (String ext : exts) { // check if it already has a valid
				// extension
				if (nameLower.endsWith('.' + ext.toLowerCase())) {
					return file; // if yes, return as-is
				}
			}
			// if not, append the first extension from the selected filter
			file = new File(file.toString() + '.' + exts[0]);
		}
		return file;
	}
	
	
	private void showLicenseIdentifierMessage() {
		String id = null;
		try {
			pbar.setVisible(true);
			id = Utils.getIdentifierForLicense(pbar, 0, 90);
		} catch (Exception e) {
			pbar.setVisible(false);
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error determinando el identificador de su máquina. El SO actual es incompatible.","Identificador para nueva licencia", JOptionPane.ERROR_MESSAGE);
			return;
		}
		pbar.updateBar(100);
		JLabel messageArea = new JLabel(
				convertToMultiline("Para solicitar una nueva licencia, usted debe contactar con el proveedor de la aplicación \n y brindarle el identificador para esta instalación. \n \n ¿Cómo desea obtener su identificador?"));
		JPanel pane = new JPanel();
		pane.add(messageArea);
		int result = JOptionPane.showOptionDialog(null, pane,
				"Renovación de licencia", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, new Object[] {
						"Guardar", "Copiar",
						"Cancelar" }, "Copiar al portapapeles");
		pbar.setVisible(false);

		if (result == 0) // "Save"
		{
			String path = null;
			// String fileName = "IdentificadorParaLicencia.txt";
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Texto plano (*.txt)", "txt");
			jfc.setFileFilter(filter);
			jfc.setMultiSelectionEnabled(false);
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (jfc.showSaveDialog(null) == 0) {

				File selectedFile = getSelectedFileWithExtension(jfc);
				// path = jfc.getSelectedFile().getAbsolutePath();
				path = selectedFile.getAbsolutePath();

				/*
				 * System.out.println("ABS" + path); try {
				 * System.out.println("CAN" + selectedFile.getCanonicalPath());
				 * } catch (IOException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */

				try {
					WriteIdentifier(path, id);
					// File f = new File(path);
					// f.setReadOnly();
					// f.setWritable(false, false);

				} catch (Exception excep) {
					JOptionPane
							.showMessageDialog(
									LicenseActivation.this,
									"Ha ocurrido un error al escribir el identificador para su nueva licencia en el fichero especificado.",
									"Guardando identificador",
									JOptionPane.ERROR_MESSAGE);
					return;
				}

				int locateOnDiskResult = JOptionPane
						.showConfirmDialog(
								null,
								convertToMultiline("El fichero con su identificador ha sido guardado en: \n"
										+ path
										+ ". \n\nContacte con el proveedor con dicho identificador para solicitar una nueva licencia. \n \n ¿Desea localizarlo en el explorador?"),
								"Identificador guardado",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);

				if (locateOnDiskResult == 0) {
					// Locate on disk the file
					try {
						new ProcessBuilder("explorer.exe", "/select,", path)
								.start();

					} catch (IOException exep) {
						// TODO Auto-generated catch block
						exep.printStackTrace();
						JOptionPane
								.showMessageDialog(
										null,
										convertToMultiline("Ha ocurrido un problema abriendo el explorador. El fichero ha sido guardado en: \n"
												+ path + "."),
										"Localizando fichero",
										JOptionPane.INFORMATION_MESSAGE);
					}
				}

				LicenseActivation.this.setVisible(false);
				LicenseState state = Controler.getApplicationLicenseState();
				if (state != LicenseState.valid)
					LicenseActivation.this.parent
							.showExpiredLicenseMessage(Controler
									.getApplicationLicenseState(), "license activation save");
			} else
				showLicenseIdentifierMessage();

		}

		if (result == 1)// "Copy"
		{
			StringSelection stringSelection = new StringSelection(id);
			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents(stringSelection, null);

			JOptionPane
					.showMessageDialog(
							null,
							"El identificador para solicitar una nueva licencia ha sido copiado en el portapapeles. \nContacte con el proveedor con dicho identificador para solicitar una nueva licencia.",
							"Identificador copiado",
							JOptionPane.INFORMATION_MESSAGE);
			LicenseActivation.this.setVisible(false);
			LicenseState state = Controler.getApplicationLicenseState();
			if (state != LicenseState.valid)
				LicenseActivation.this.parent
						.showExpiredLicenseMessage(Controler
								.getApplicationLicenseState(), "license activation copy"); // only if
																// licence is
																// not valid

		}
	}

	/**
	 * This method initializes jButtonRenew
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonRenew() {
		if (jButtonRenew == null) {
			jButtonRenew = new JButton();
			jButtonRenew.setBounds(new Rectangle(14, 112, 116, 26));
			jButtonRenew.setText("Nueva licencia");
			jButtonRenew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showLicenseIdentifierMessage();
				}
			});
		}
		return jButtonRenew;
	}

	/**
	 * This method initializes pbar	
	 * 	
	 * @return citmatel.cu.visual_Pack.ProgressBarPanel	
	 */
	private ProgressBarPanel getPbar() {
		if (pbar == null) {
			pbar = new ProgressBarPanel();
			pbar.setBounds(new Rectangle(15, 82, 332, 19));
			pbar.setStringPainted(true);
			pbar.setVisible(false);
		}
		return pbar;
	}

} // @jve:decl-index=0:visual-constraint="19,10"
