package citmatel.cu.visual_Pack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SpinnerDateModel;

import java.awt.Component;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JSpinner;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.JButton;

import org.jdesktop.swingx.JXDatePicker;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;

import citmatel.cu.class_Pack.Controler;
import citmatel.cu.class_Pack.License;
import citmatel.cu.class_Pack.Utils;

public class Generatekey extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private TextField entitytextField = null;
	private TextField idTextField = null;
	private JButton generatejButton = null;
	private TextField textField = null;
	private JButton jButton = null;
	private JLabel jLabel = null;

	private JXDatePicker xSwingDateChooser = null;

	/**
	 * @param owner
	 */
	public Generatekey(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(343, 260);
		this.setTitle("Gestión de Licencias");
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
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(23, 131, 66, 16));
			jLabel.setText("Guardar en:");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(19, 101, 70, 16));
			jLabel3.setText("Válida hasta");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(38, 33, 51, 16));
			jLabel2.setText("Empresa");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(18, 67, 71, 16));
			jLabel1.setText("Identificador");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(Color.white);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getEntitytextField(), null);
			jContentPane.add(getMactextField(), null);
			jContentPane.add(getGeneratejButton(), null);
			jContentPane.add(getTextField(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(jLabel, null);

			jContentPane.add(getXSwingDateChooser(), null);
		}
		return jContentPane;
	}

	/**
	 * Calendar
	 */
	private JXDatePicker getXSwingDateChooser() {
		if (this.xSwingDateChooser == null) {
			this.xSwingDateChooser = new JXDatePicker();
			this.xSwingDateChooser.setBounds(new Rectangle(99, 97, 145, 25));

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
	 * This method initializes entitytextField
	 * 
	 * @return java.awt.TextField
	 */
	private TextField getEntitytextField() {
		if (entitytextField == null) {
			entitytextField = new TextField();
			entitytextField.setBounds(new Rectangle(99, 27, 218, 28));

			entitytextField.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					showPopup(e);
				}

				public void mouseReleased(MouseEvent e) {
					showPopup(e);
				}

				private void showPopup(MouseEvent e) {
					if (e.isPopupTrigger()) {
						JPopupMenu popup = Provider.getJPopupMenuCopyAndPaste();
						Component c = e.getComponent();
						// System.out.println("Component text field:" + c);
						// System.out.println("Is Focusable" + c.isFocusable());
						popup.show(e.getComponent(), e.getX(), e.getY());
						Provider
								.setComponentThatTriggerCopyPasteMenu((TextField) c);
					}
				}
			});
		}
		return entitytextField;
	}

	/**
	 * This method initializes mactextField
	 * 
	 * @return java.awt.TextField
	 */
	private TextField getMactextField() {
		if (idTextField == null) {
			idTextField = new TextField();
			idTextField.setBounds(new Rectangle(99, 62, 145, 28));

			idTextField.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					showPopup(e);
				}

				public void mouseReleased(MouseEvent e) {
					showPopup(e);
				}

				private void showPopup(MouseEvent e) {
					if (e.isPopupTrigger()) {
						JPopupMenu popup = Provider.getJPopupMenuCopyAndPaste();
						Component c = e.getComponent();
						// System.out.println("Component text field:" + c);
						// System.out.println("Is Focusable" + c.isFocusable());
						popup.show(e.getComponent(), e.getX(), e.getY());
						Provider
								.setComponentThatTriggerCopyPasteMenu((TextField) c);
					}
				}
			});
		}
		return idTextField;
	}

	private Boolean hasValidFormat(String identifier) {
		return identifierPattern.matcher(identifier).matches();
	}

	private static Pattern identifierPattern = Pattern
			.compile("[0-9]{4}[-][0-9]{4}[-][0-9]{4}[-][0-9]{4}"); // @jve:decl-index=0:

	/**
	 * This method initializes generatejButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getGeneratejButton() {
		if (generatejButton == null) {
			generatejButton = new JButton();
			generatejButton.setEnabled(false);
			generatejButton.setBounds(new Rectangle(107, 195, 131, 26));
			generatejButton.setText("Generar Licencia");
			generatejButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (generatejButton.getText().equals("Aceptar")) {
								Generatekey.this.setVisible(false);
								return;
							}

							String entityData = entitytextField.getText();
							String idData = idTextField.getText();
							Date licenseDate = xSwingDateChooser.getDate();

							// checking data
							if (entityData.isEmpty() || idData.isEmpty()) {
								JOptionPane
										.showMessageDialog(
												null,
												"Debe introducir correctamente los datos.",
												"Generar licencia",
												JOptionPane.WARNING_MESSAGE);
								return;
							}

							if (!hasValidFormat(idData)) {
								JOptionPane
										.showMessageDialog(
												null,
												"<html> Debe insertar un formato válido para el identificador. <br> Ejemplo: 3291-5689-2484-3561. </html>",
												"Generar licencia",
												JOptionPane.WARNING_MESSAGE);
								return;
							}

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

							License license = new License();
							license.setEntity(entityData);
							try {
								license.setInstalationID(idData);
							} catch (Exception e2) {
							}
							license.setValidDate(licenseDate);

							// generate license
							try {
								Controler.SaveObject(getTextField().getText(),
										license);

								int result = JOptionPane
										.showOptionDialog(
												null,
												"La licencia se ha generado correctamente. \n ¿Desea generar otra licencia?",
												"Generar licencia",
												JOptionPane.YES_NO_OPTION,
												JOptionPane.INFORMATION_MESSAGE,
												null,
												new Object[] { "Sí", "No" },
												"OK");
								if (result == 1) {
									Generatekey.this.setVisible(false);

								}

							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					});
		}
		return generatejButton;
	}

	/**
	 * This method initializes textField
	 * 
	 * @return java.awt.TextField
	 */
	private TextField getTextField() {
		if (textField == null) {
			textField = new TextField();
			textField.setBounds(new Rectangle(23, 156, 254, 28));
			textField.setEditable(false);
		}
		return textField;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(285, 154, 43, 28));
			jButton.setText("...");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser jfc = new JFileChooser();
					jfc.setAcceptAllFileFilterUsed(false);
					jfc.setFileFilter(new LicenseFilesFilter());
					if (jfc.showSaveDialog(null) == 0) {
						String fileName = jfc.getSelectedFile()
								.getAbsolutePath();
						if (Utils.getExtension(new File(fileName)) == null)
							fileName += "." + LicenseFilesFilter.validExtension;
						textField.setText(fileName);
						generatejButton.setEnabled(true);
						generatejButton.setText("Generar licencia");
					}
				}
			});
		}
		return jButton;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
