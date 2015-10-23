package citmatel.cu.visual_Pack;

import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import citmatel.cu.class_Pack.ManagerSettings;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.WindowConstants;

public class UpdateExportWarnings extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private String seeDetailsText = "Detalles >>"; // @jve:decl-index=0:
	private String unseeDetailsText = "<< Detalles"; // @jve:decl-index=0:
	private List<String> unfoundDocsInDB = null;
	private List<String> unfoundDocsInDisk = null;
	private String title = null;
	private int originalWidth = 390;
	private int originalHeight = 170;
	private JLabel jLabelHead = null;
	private JButton jButtonDetails = null;
	private JButton jButtonOK = null;
	private JPanel jButtonsPane = null;
	private JPanel jUpPane = null; // @jve:decl-index=0:visual-constraint="420,37"
	private JPanel jPanelOfWarnings = null;
	private JButton jButtonReexport = null;

	private static String convertToMultiline(String text) {
		return "<html>" + text.replaceAll("\n", "<br>") + "</html>";
	}

	/**
	 * This method initializes jButtonDetails
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonDetails() {
		if (jButtonDetails == null) {
			jButtonDetails = new JButton();
			jButtonDetails.setText(seeDetailsText);
			jButtonDetails.setBounds(new Rectangle(176, 5, 97, 26));
			jButtonDetails
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							String currentText = jButtonDetails.getText();

							/**
							 * See details...
							 */

							if (currentText.equals(seeDetailsText)) {
								/** increment window size */
								UpdateExportWarnings.this.setSize(
										originalWidth, originalHeight + 300);

								/** show warnings */
								jPanelOfWarnings = new JPanel();
								// jPanelOfWarnings.setLayout(null);
								jPanelOfWarnings
										.setSize(UpdateExportWarnings.this
												.getSize().width,
												UpdateExportWarnings.this
														.getSize().height);

								jPanelOfWarnings.setBackground(new Color(7, 7,
										7, 7));

								jPanelOfWarnings.setLayout(new BoxLayout(
										jPanelOfWarnings, BoxLayout.Y_AXIS));

								// Unfound documents in data base
								if (unfoundDocsInDB != null
										&& unfoundDocsInDB.size() > 0) {
									JLabel labelHead1 = new JLabel(
											convertToMultiline("Existen modificaciones hechas a documentos que no se encuentran en la BD:"));

									labelHead1.setBackground(Color.WHITE);
									labelHead1.setSize(300, 60);
									jPanelOfWarnings.add(labelHead1);

									JList list1 = new JList();
									jPanelOfWarnings.setBackground(new Color(6,
											6, 7, 7));
									JScrollPane scrollPane1 = new JScrollPane(
											list1);
									DefaultListModel dataModel1 = new DefaultListModel();
									for (String d : unfoundDocsInDB) {
										dataModel1.addElement(d);
									}
									list1.setModel(dataModel1);
									jPanelOfWarnings.add(new JLabel("   "));
									jPanelOfWarnings.add(scrollPane1);
								}

								// Unfound documents in disk
								if (unfoundDocsInDisk != null
										&& unfoundDocsInDisk.size() > 0) {
									jPanelOfWarnings.add(new JLabel("   "));
									JLabel labelHead2 = new JLabel(
											convertToMultiline("Los siguientes documentos no han sido encontrados en la dirección especificada ("
													+ ManagerSettings
															.getPathDocument()
													+ "):"));
									labelHead2.setBackground(Color.WHITE);
									labelHead2.setSize(300, 60);
									jPanelOfWarnings.add(labelHead2);

									JList list2 = new JList();
									jPanelOfWarnings.setBackground(new Color(6,
											6, 7, 7));
									JScrollPane scrollPane2 = new JScrollPane(
											list2);
									DefaultListModel dataModel2 = new DefaultListModel();
									for (String d : unfoundDocsInDisk) {
										dataModel2.addElement(d);
									}
									list2.setModel(dataModel2);
									jPanelOfWarnings.add(new JLabel("   "));
									jPanelOfWarnings.add(scrollPane2);
								}

								// add warnings to window
								jContentPane.removeAll();
								jContentPane.add(getJUpPane(),
										BorderLayout.NORTH);
								jContentPane.add(jPanelOfWarnings,
										BorderLayout.CENTER);

								/** change details button text */
								jButtonDetails.setText(unseeDetailsText);

								/** allow resize window */
								UpdateExportWarnings.this.setResizable(true);

								return;
							}

							/**
							 * Unsee details...
							 */
							if (currentText.equals(unseeDetailsText)) {

								jContentPane = null;
								jUpPane = null;
								// initialize();
								UpdateExportWarnings.this
										.setContentPane(getJContentPane());
								UpdateExportWarnings.this.setSize(
										originalWidth, originalHeight);
								jButtonDetails.setText(seeDetailsText);
								UpdateExportWarnings.this.setResizable(false);
								jPanelOfWarnings.setVisible(false);

							}

						}
					});
		}
		return jButtonDetails;
	}

	/**
	 * This method initializes jButtonOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null)
			jButtonOK = new JButton();
		jButtonOK.setText("OK");
		jButtonOK.setBounds(new Rectangle(120, 5, 51, 26));
		jButtonOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// OK button
				Container parentContainer = UpdateExportWarnings.this
						.getParent();
				Boolean locateOnDiskSelected = false;
				String updateFilePath = null;
				if (parentContainer.getClass().equals(CompactUpdate.class)) // the
				// control
				// parent
				// is
				// CompactUpdate
				{
					CompactUpdate parent = (CompactUpdate) UpdateExportWarnings.this
							.getParent();
					locateOnDiskSelected = parent.jCheckBoxOnDisk.isSelected();
					updateFilePath = parent.pathtextField.getText();
				}

				if (parentContainer.getClass().equals(CompleteUpdate.class))// the
				// control
				// parent
				// is
				// CompleteUpdate
				{
					CompleteUpdate parent = (CompleteUpdate) UpdateExportWarnings.this
							.getParent();
					locateOnDiskSelected = parent.jCheckBoxOnDisk.isSelected();
					updateFilePath = parent.pathtextField.getText();
				}

				if (locateOnDiskSelected)// Locate on disk the update file
				{
					try {
						new ProcessBuilder("explorer.exe", "/select,",
								updateFilePath).start();

					} catch (IOException exep) {
						// TODO Auto-generated catch block
						exep.printStackTrace();
						JOptionPane
								.showMessageDialog(
										null,
										"Ha ocurrido un problema abriendo el explorador desde este sistema operativo. El fichero de actualización ha sido guardado en: "
												+ updateFilePath + ".");
					}
				}
				UpdateExportWarnings.this.setVisible(false);
				parentContainer.setVisible(false);
			}
		});
		return jButtonOK;
	}

	/**
	 * This method initializes jButtonsPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJButtonsPane() {

		if (jButtonsPane == null) {
			jButtonsPane = new JPanel();
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
			jButtonsPane.setLayout(flowLayout);
			// jButtonsPane.setLayout(null);
			jButtonsPane.add(getJButtonReexport(), null);
			jButtonsPane.add(getJButtonDetails());
			jButtonsPane.setBackground(Color.WHITE);
			jButtonsPane.add(getJButtonOK(), getJButtonOK().getName());
		}
		return jButtonsPane;
	}

	/**
	 * This method initializes jUpPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJUpPane() {
		if (jUpPane == null) {
			jUpPane = new JPanel();
			// jUpPane.setBackground(Color.BLUE);
			// jUpPane.setSize(new Dimension(64, 57));
			// jUpPane.setLayout(new BoxLayout(jUpPane, BoxLayout.Y_AXIS));
			// jUpPane.setLayout(null);
			jUpPane.setLayout(new BorderLayout());
			jLabelHead = new JLabel();
			jLabelHead.setLocation(new Point(50, 10));
			jUpPane.setBackground(Color.WHITE);
			jLabelHead.setBackground(Color.WHITE);
			jLabelHead
					.setText(convertToMultiline("La exportación del fichero de actualización ha terminado con algunas advertencias. Haga click en detalles para ver la información adicional."));
			// jLabelHead.setAlignmentX(Component.LEFT_ALIGNMENT);
			jUpPane.add(jLabelHead, BorderLayout.NORTH);
			// JButton b = new JButton("Test");
			// JButton b2 = new JButton("Test2");
			// b2.setAlignmentX(Component.RIGHT_ALIGNMENT);
			// b.setAlignmentX(CENTER_ALIGNMENT);
			// jUpPane.add(b, BorderLayout.NORTH);
			// jUpPane.add(b2, BorderLayout.CENTER);
			jUpPane.add(getJButtonsPane(), BorderLayout.SOUTH);

		}
		return jUpPane;
	}

	/**
	 * This method initializes jButtonReexport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonReexport() {
		if (jButtonReexport == null) {
			jButtonReexport = new JButton();
			jButtonReexport.setText("Volver a exportar");
			jButtonReexport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// Volver a exportar
							UpdateExportWarnings.this.setVisible(false);
							System.out.println("actionPerformed()"); // TODO
							// Auto-generated
							// Event
							// stub
							// actionPerformed()
						}
					});
		}
		return jButtonReexport;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		List<String> docs = new ArrayList<String>();
		docs.add("d1");
		docs.add("d2");
		docs.add("d3");
		UpdateExportWarnings warningsWindow = new UpdateExportWarnings(null,
				"Probando", docs, docs);
		warningsWindow.setVisible(true);

		// testing
		// try {
		// Desktop.getDesktop().open(new
		// File("C:\\Documents and Settings\\melis\\Mis documentos\\toDelete"));
		File updateFile = new File(
				"C:\\Documents and Settings\\melis\\Mis documentos\\toDelete");
		String path = updateFile.getParent();
		String pathParent = updateFile.getParentFile().getPath();
		String name = updateFile.getName();

		// Process p = new ProcessBuilder("explorer.exe",
		// "/select,C:\\directory\\selectedFile").start();
		// Process p = new ProcessBuilder("explorer.exe",
		// "C:\\Documents and Settings\\melis\\Mis documentos\\toDelete\\compact.mod").start();
		// Runtime.getRuntime().exec("explorer.exe",new
		// String[]{"C:\\Documents and Settings\\melis\\Mis documentos\\toDelete"},
		// new
		// File("C:\\Documents and Settings\\melis\\Mis documentos\\toDelete\\compact.mod"));
		// Process p = new ProcessBuilder("explorer.exe",
		// "/select,D:\\Documentation\\Celia\\JAVA\\Java2.pdf").start();
		String pathString = "C:\\Documents and Settings\\melis\\Mis documentos\\toDelete\\dfh.all";
		String part1 = "D:\\Documentation\\Celia\\JAVA\\";
		String part2 = "Programacion en Java.pdf";
		// String escaped = part2.replace(" ", "\\ ");
		// String quoted = "\"" + part1 + part2 + "\"";
		String quoted = "\"" + pathString + "\"";
		String s = "/select," + quoted;
		System.out.println(s);
		// 1)Process p = new ProcessBuilder("explorer.exe", s).start();
		// 2)Process p = new ProcessBuilder("explorer.exe",
		// "/select,D:\\Documentation\\Celia\\JAVA\\Java2.pdf").start();
		// 3)Runtime.getRuntime().exec("explorer D:\\Documentation\\Celia\\JAVA");

		// begin
		// ProcessBuilder pb = new ProcessBuilder();
		// pb.command("explorer.exe", "/select,",
		// "\"c:\\test Space\\sample Spa.txt\"").start();
		// Runtime.getRuntime().exec("explorer.exe /select, \"c:\\test Space\\sample Spa.txt\"");
		// end

		Process p = new ProcessBuilder("explorer.exe", "/select,", quoted)
				.start();

		System.out.println(pathParent);
		// System.out
		// .println(name);
		// } catch (IOException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// System.out.println(e.getMessage());
		// }

		JCheckBox combobox = new JCheckBox(
				"Localizar en disco el fichero de actualización");

		String message = convertToMultiline("Concluida la exportación satisfactoriamente. \n Han sido exportadas "
				+ "x" + " modificaciones.");
		Object[] params = { message, combobox };
		// JOptionPane.showConfirmDialog(null, params, "Actualización compacta",
		// JOptionPane.PLAIN_MESSAGE);
		// JOptionPane.showConfirmDialog(null, "message", "Title",
		// JOptionPane.WARNING_MESSAGE);

		// JOptionPane
		// .showOptionDialog(null,
		// //convertToMultiline("Concluida la exportación satisfactoriamente. \n Han sido exportadas "
		// + "x" + " modificaciones."),
		// params,
		// "Actualización compacta",
		// JOptionPane.YES_NO_OPTION,
		// JOptionPane.INFORMATION_MESSAGE,
		// null,
		// new Object[]{"OK", "Volver a Exportar"},
		// "OK");

		// JOptionPane.showMessageDialog(null,
		// convertToMultiline("Concluida la exportación satisfactoriamente. \n Han sido exportadas "
		// + 5 + " modificaciones."), "Actualización compacta",
		// JOptionPane.WARNING_MESSAGE);

		/*
		 * JOptionPane .showConfirmDialog(null,convertToMultiline(
		 * "Concluida la exportación satisfactoriamente. \n Han sido exportadas "
		 * + "x" + " modificaciones."), "Actualización compacta",
		 * JOptionPane.DEFAULT_OPTION, JOptionPane.YES_NO_OPTION, null);
		 */

		// testing
		/*
		 * JTextArea mytext = new JTextArea();mytext.setText(
		 * "mytextline1\nmytextline2\nmytextline3\nmytextline4\nmytextline5\nmytextline6"
		 * ); mytext.setRows(5); mytext.setColumns(10);
		 * mytext.setEditable(true); JScrollPane mypane = new
		 * JScrollPane(mytext);
		 * 
		 * Object[] objarr = { new JLabel("Enter some text:"), mypane, };
		 * 
		 * //JOptionPane.showMessageDialog(null,
		 * "La licencia para esta aplicación no está activada \n y el fichero de actualización que está importando \n no contiene una activación. Por favor realice una activación que incluya la licencia, \n o active la licencia de su aplicación en el menú 'Activar'."
		 * , "Error durante la actualización", JOptionPane.ERROR_MESSAGE);
		 * 
		 * JOptionPane Optpane = new JOptionPane(objarr,
		 * JOptionPane.PLAIN_MESSAGE);
		 */

		// TODO Auto-generated method stub
	}

	/**
	 * @param owner
	 */
	public UpdateExportWarnings(JDialog owner, String windowTitle,
			List<String> unfoundDocsInDB, List<String> unfoundDocsInDisk) {
		super(owner);
		this.title = windowTitle;
		this.unfoundDocsInDB = unfoundDocsInDB;
		this.unfoundDocsInDisk = unfoundDocsInDisk;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setMinimumSize(new Dimension(originalWidth, originalHeight));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(originalWidth, originalHeight));
		this.setTitle(title);
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
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJUpPane(), BorderLayout.CENTER);
			jContentPane.setBackground(Color.WHITE);
		}
		return jContentPane;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
