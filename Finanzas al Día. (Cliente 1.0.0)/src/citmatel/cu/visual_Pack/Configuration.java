package citmatel.cu.visual_Pack;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Rectangle;
import javax.swing.JComboBox;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.plaf.synth.SynthLookAndFeel;

import citmatel.cu.class_Pack.Controler;

public class Configuration extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabelStyle = null;
	private JComboBox jComboBoxStyle = null;
	private JLabel jLabelDays = null;
	private JComboBox jComboBoxDays = null;
	private JCheckBox jCheckBox = null;
	private JButton jButtonAccept = null;
	private JButton jButtonCancel = null;
	public static final Map<String, String> applicationStyles;
	static {
		applicationStyles = new LinkedHashMap<String, String>();
		// applicationStyles.put("Mac",
		// "com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		applicationStyles.put("Marino",
				"javax.swing.plaf.nimbus.NimbusLookAndFeel");
		applicationStyles.put("Orange",
				"com.nilo.plaf.nimrod.NimRODLookAndFeel");
		applicationStyles.put("Sistema", UIManager.getSystemLookAndFeelClassName());
		// applicationStyles
		// .put("Estilo4", "com.digitprop.tonic.TonicLookAndFeel");
		// applicationStyles.put("Estilo5",
		// "de.muntjak.tinylookandfeel.TinyLookAndFeel");
	}
	private static final Map<String, Integer> timePeriodsToWarning;
	private JCheckBox jCheckBoxConfirmation = null;
	static {
		timePeriodsToWarning = new LinkedHashMap<String, Integer>();
		timePeriodsToWarning.put("30 Días", 30);
		timePeriodsToWarning.put("2 Meses", 60);
		timePeriodsToWarning.put("3 Meses", 90);
		timePeriodsToWarning.put("Nunca", 0);
	}

	/**
	 * @param owner
	 */
	public Configuration(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(350, 316);
		this.setTitle("Configuración de la aplicación");
		this.setModal(true);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
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
			jLabelDays = new JLabel();
			jLabelDays.setBounds(new Rectangle(21, 76, 311, 16));
			jLabelDays
					.setText("Tiempo para la advertencia de expiración de su licencia:");
			jLabelStyle = new JLabel();
			jLabelStyle.setBounds(new Rectangle(21, 17, 199, 16));
			jLabelStyle.setText("Estilo de diseño para la aplicación:");
			jContentPane = new JPanel();
			jContentPane.setBackground(Color.white);
			jContentPane.setLayout(null);
			jContentPane.add(jLabelStyle, null);
			jContentPane.add(getJComboBoxStyle(), null);
			jContentPane.add(jLabelDays, null);
			jContentPane.add(getJComboBoxDays(), null);
			jContentPane.add(getJCheckBox(), null);
			jContentPane.add(getJButtonAccept(), null);
			jContentPane.add(getJButtonCancel(), null);
			jContentPane.add(getJCheckBoxConfirmation(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBoxStyle
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxStyle() {
		if (jComboBoxStyle == null) {
			jComboBoxStyle = new JComboBox();
			jComboBoxStyle.setBounds(new Rectangle(21, 39, 166, 21));
			String selectedStyleName = null;
			String savedStyleName = Controler.getApplicationSettings().getStyle();
			for (Entry<String, String> entry : applicationStyles.entrySet()) {
				jComboBoxStyle.addItem(entry.getKey());
				if (savedStyleName.equals(entry.getKey()))
					selectedStyleName = entry.getKey();
			}
			if (selectedStyleName != null)
				jComboBoxStyle.setSelectedItem(selectedStyleName);
		}
		return jComboBoxStyle;
	}

	/**
	 * This method initializes jComboBoxDays
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxDays() {
		if (jComboBoxDays == null) {
			jComboBoxDays = new JComboBox();
			jComboBoxDays.setBounds(new Rectangle(21, 99, 125, 21));

			String selected = null;
			int savedDays = Controler.getApplicationSettings().getDaysToWarning();
			for (Entry<String, Integer> entry : timePeriodsToWarning.entrySet()) {
				jComboBoxDays.addItem(entry.getKey());
				if (savedDays == entry.getValue())
					selected = entry.getKey();
			}
			if (selected != null)
				jComboBoxDays.setSelectedItem(selected);

		}
		return jComboBoxDays;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(21, 139, 312, 45));
			jCheckBox.setBackground(Color.white);
			jCheckBox.setSelected(Controler.getApplicationSettings().getDeleteOldModifications());
			StringBuilder sb = new StringBuilder(64);
			sb
					.append("<html>Borrar viejas modificaciones al importar una actualización compacta</html>");
			jCheckBox.setText(sb.toString());
			
		}
		return jCheckBox;
	}
	
	private static String convertToMultiline(String text) {
		return "<html>" + text.replaceAll("\n", "<br>") + "</html>";
	}


	/**
	 * This method initializes jButtonAccept
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonAccept() {
		if (jButtonAccept == null) {
			jButtonAccept = new JButton();
			jButtonAccept.setBounds(new Rectangle(160, 249, 79, 26));
			jButtonAccept.setText("Aceptar");

			jButtonAccept
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								String selectedStyleName = (String) jComboBoxStyle
										.getSelectedItem();
								int selectedNumberOfDays = timePeriodsToWarning
										.get(jComboBoxDays.getSelectedItem());
								String message = jCheckBox.isSelected()?
										"Con la configuración seleccionada, al realizar una importación compacta, \n se borrarán las viejas modificaciones sin antes realizar una confirmación.":
										"Con la configuración seleccionada, al realizar una importación compacta, se agregarán \n las nuevas modificaciones a la lista existente sin antes realizar una confirmación.";
								if(!jCheckBoxConfirmation.isSelected())
								{
									int res = JOptionPane
									.showOptionDialog(
											null,
											convertToMultiline(message + "\n \n ¿Está seguro que desea guardar esta configuración?"),
											"Guardar configuración", 
											JOptionPane.YES_NO_CANCEL_OPTION,
											JOptionPane.QUESTION_MESSAGE,
											null,
											new Object[] { "Sí", "No" }, "OK"
											);
									if(res == 1 || res == -1)//back to configuration
										return;
								}
								
								Controler.SaveInSettings(selectedStyleName,
										selectedNumberOfDays, jCheckBox
												.isSelected(), jCheckBoxConfirmation.isSelected());

							} catch (IOException e1) {
								JOptionPane.showMessageDialog(
										Configuration.this,
										"Ha ocurrido un error guardando la configuración"
												+ e1.getMessage(),
										"Configuración",
										JOptionPane.ERROR_MESSAGE);
							}

							try {

								UIManager.setLookAndFeel(Configuration.applicationStyles.get(Controler.getApplicationSettings().getStyle()));// Changing
								// application
								// style
								// Configuration.this.getParent().invalidate();
								// SwingUtilities.updateComponentTreeUI(Configuration.this.getParent());
								/*
								 * for(Window window : JFrame.getWindows()) {
								 * SwingUtilities.updateComponentTreeUI(window);
								 * // window.pack(); // window.setVisible(true);
								 * }
								 */

								SwingUtilities
										.updateComponentTreeUI((JFrame) SwingUtilities
												.getWindowAncestor(Configuration.this));
								// Configuration.this.getParent().repaint();
							} catch (UnsupportedLookAndFeelException e1) {
								JOptionPane
										.showMessageDialog(
												Configuration.this,
												"El estilo seleccionado no es soportado por la aplicación.",
												"Estilo de la aplicación",
												JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
							} catch (ClassNotFoundException e2) {
								JOptionPane
										.showMessageDialog(
												Configuration.this,
												"No se encuentra el estilo especificado para la aplicación.",
												"Estilo de la aplicación",
												JOptionPane.ERROR_MESSAGE);
								e2.printStackTrace();
							} catch (InstantiationException e3) {
								JOptionPane
										.showMessageDialog(
												Configuration.this,
												"Ha ocurrido un error inicializando el estilo especificado para la aplicación.",
												"Estilo de la aplicación",
												JOptionPane.ERROR_MESSAGE);
								e3.printStackTrace();
							} catch (IllegalAccessException e4) {
								JOptionPane
										.showMessageDialog(
												Configuration.this,
												"Ha ocurrido un error cargando el estilo especificado para la aplicación.",
												"Estilo de la aplicación",
												JOptionPane.ERROR_MESSAGE);
								e4.printStackTrace();
							}
							Configuration.this.setVisible(false);
						}
					});

		}
		return jButtonAccept;
	}

	/**
	 * This method initializes jButtonCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setBounds(new Rectangle(247, 249, 85, 26));
			jButtonCancel.setText("Cancelar");
			jButtonCancel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Configuration.this.setVisible(false);
						}
					});

		}
		return jButtonCancel;
	}

	/**
	 * This method initializes jCheckBoxConfirmation	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxConfirmation() {
		if (jCheckBoxConfirmation == null) {
			jCheckBoxConfirmation = new JCheckBox();
			jCheckBoxConfirmation.setBounds(new Rectangle(21, 194, 312, 45));
			jCheckBoxConfirmation.setBackground(Color.white);
			jCheckBoxConfirmation.setSelected(Controler.getApplicationSettings().getAskIfDeleteOldModifications());
			StringBuilder sb = new StringBuilder(64);
			sb
					.append("<html>Confirmar si borrar viejas modificaciones en la ventana de actualización</html>");
			jCheckBoxConfirmation.setText(sb.toString());
			jCheckBox.setEnabled(!jCheckBoxConfirmation.isSelected());
			jCheckBoxConfirmation.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					jCheckBox.setEnabled(!jCheckBoxConfirmation.isSelected());
				}
			});
		}
		return jCheckBoxConfirmation;
	}

} // @jve:decl-index=0:visual-constraint="21,10"
