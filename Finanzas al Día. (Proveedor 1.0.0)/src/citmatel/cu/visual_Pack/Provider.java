package citmatel.cu.visual_Pack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.help.DefaultHelpBroker;
import javax.help.HelpSet;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;

import citmatel.cu.class_Pack.CompactData;
import citmatel.cu.class_Pack.Controler;
import citmatel.cu.class_Pack.Document;
import citmatel.cu.class_Pack.ManagerDoc;
import citmatel.cu.class_Pack.ManagerSettings;
import citmatel.cu.class_Pack.Modification;
import citmatel.cu.class_Pack.Utils;
import citmatel.cu.utils.AccentsAndToLowerCase;

/**
 * 
 * @author jorgem
 * 
 */

public class Provider extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	public static JTree docjTree = null;
	public static JTree modjTree = null;
	private JScrollPane docjScrollPane = null;
	private JScrollPane modjScrollPane = null;
	private JPanel searchjPanel = null;
	private JSpinner jSpinner = null;
	private TextField titletextField = null;
	private TextField contenttextField = null;
	private JButton jButton = null;
	private JCheckBox titlejCheckBox = null;
	private JCheckBox contentjCheckBox = null;
	private JCheckBox datejCheckBox = null;
	private JScrollPane jScrollPane = null;

	private JLabel jLabel = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;
	private JLabel jLabel8 = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel10 = null;
	private JLabel jLabel11 = null;
	private JLabel jLabel12 = null;
	private DefaultListModel defaultListModel = null; // @jve:decl-index=0:visual-constraint="1047,10"
	private JList jListFind = null;
	private JScrollPane resumejScrollPane1 = null;
	private JTextPane jTextPane = null;

	private JXDatePicker xSwingDateChooser = null;
	private JXDatePicker xSwingDateChooser1 = null;
	private JPanel jPanel = null;

	// Icons for trees
	private static ImageIcon rootIcon = null; // @jve:decl-index=0:
	private static ImageIcon manualIcon = null; // @jve:decl-index=0:
	private static ImageIcon sectionIcon = null; // @jve:decl-index=0:
	private static ImageIcon chapterIcon = null;
	private static ImageIcon documentIcon = null;

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */

	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
					getDocjScrollPane(), getModjScrollPane());
			jSplitPane.setBounds(new Rectangle(9, 156, 700, 585));
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setDividerSize(11);
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setComponentOrientation(ComponentOrientation.UNKNOWN);
			jSplitPane.setResizeWeight(1.0D);
			jSplitPane.setTopComponent(getDocjScrollPane());
			jSplitPane.setBottomComponent(getModjScrollPane());
			jSplitPane.setDividerLocation(400);
		}
		return jSplitPane;
	}

	// contextual menu
	private static JPopupMenu jPopupMenuCopyAndPaste = null; // @jve:decl-index=0:visual-constraint="458,840"
	static Component componentThatTriggerCopyPasteMenu = null;

	public static Component getComponentThatTriggerCopyPasteMenu() {
		return componentThatTriggerCopyPasteMenu;
	}

	public static void setComponentThatTriggerCopyPasteMenu(
			Component whoTriggerCopyPasteMenu) {
		componentThatTriggerCopyPasteMenu = whoTriggerCopyPasteMenu;
	}

	/**
	 * This method initializes jPopupMenuCopyAndPaste
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	public static JPopupMenu getJPopupMenuCopyAndPaste() {
		if (jPopupMenuCopyAndPaste == null) {
			jPopupMenuCopyAndPaste = new JPopupMenu();

			/**
			 * Copy menu item
			 */
			JMenuItem copyMenuItem = new JMenuItem("Copiar");
			copyMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					Component activeComponent = getComponentThatTriggerCopyPasteMenu();
					if (activeComponent != null) {
						TextComponent activeTextComponent = (TextComponent) activeComponent;
						Utils.copyToClipboard(activeTextComponent
								.getSelectedText());
					}

				}
			});
			jPopupMenuCopyAndPaste.add(copyMenuItem);

			/**
			 * Paste menu item
			 */
			// JMenuItem copyItem = new JMenuItem(new
			// DefaultEditorKit.PasteAction());
			JMenuItem pasteMenuItem = new JMenuItem("Pegar");
			// copyItem.setText("Pegar");
			// copyItem.setMnemonic(KeyEvent.VK_P);
			/*
			 * copyItem.addMouseListener(new java.awt.event.MouseAdapter() {
			 * public void mousePressed(MouseEvent e) {
			 * JOptionPane.showMessageDialog(null, "pressed");
			 * System.out.println(e.getComponent()); }});
			 */
			pasteMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// JOptionPane.showMessageDialog(null, "Pasted");
					/*
					 * Component c = getFocusOwner(); c =
					 * KeyboardFocusManager.getCurrentKeyboardFocusManager
					 * ().getFocusOwner(); System.out.println("getFocusOwner: "
					 * + c);
					 * 
					 * c = Provider.this.getMostRecentFocusOwner();
					 * System.out.println("getMostRecentFocusOwner: " + c);
					 */

					Component activeComponent = getComponentThatTriggerCopyPasteMenu();
					if (activeComponent != null) {
						String clipboardText = "";
						try {
							clipboardText = Utils.getFromClipboard();
						} catch (HeadlessException e1) {
						} catch (UnsupportedFlavorException e1) {
						} catch (IOException e1) {
						}
						TextComponent activeTextComponent = (TextComponent) activeComponent;

						String newText = replaceSelectedText(
								activeTextComponent.getText(),
								activeTextComponent.getSelectionStart(),
								activeTextComponent.getSelectionEnd(),
								clipboardText);
						activeTextComponent.setText(newText);
					}

				}
			});
			jPopupMenuCopyAndPaste.add(pasteMenuItem);

			/**
			 * Cut menu item
			 */
			JMenuItem cutMenuItem = new JMenuItem("Cortar");
			cutMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Component activeComponent = getComponentThatTriggerCopyPasteMenu();
					if (activeComponent != null) {
						TextComponent activeTextComponent = (TextComponent) activeComponent;
						Utils.copyToClipboard(activeTextComponent
								.getSelectedText());
						String newText = replaceSelectedText(
								activeTextComponent.getText(),
								activeTextComponent.getSelectionStart(),
								activeTextComponent.getSelectionEnd(), "");
						activeTextComponent.setText(newText);

					}

				}
			});
			jPopupMenuCopyAndPaste.add(cutMenuItem);
		}
		return jPopupMenuCopyAndPaste;
	}

	private static String replaceSelectedText(String mainText,
			int startSelection, int endSelection, String textToReplace) {
		String newText = mainText.substring(0, startSelection);
		newText += textToReplace;
		newText += mainText.substring(endSelection, mainText.length());
		return newText;
	}

	/**
	 * This method initializes docjTree
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getDocjTree() {
		if (docjTree == null) {
			docjTree = new JTree();
			docjTree.setExpandsSelectedPaths(true);

			docjTree.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {

					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) docjTree
							.getLastSelectedPathComponent();

					if (selectedNode.getLevel() == 0) {
						if (!Controler.existDataToShow())
							JOptionPane
									.showMessageDialog(
											null,
											convertToMultiline("La aplicación no ha cargado los datos necesarios para funcionar correctamente.\n Importe los datos en el menú 'Configurar Sistema'."),
											"Cargando datos",
											JOptionPane.INFORMATION_MESSAGE);
					}
					if (selectedNode.isLeaf() && selectedNode.getLevel() != 0) {
						Document document = (Document) selectedNode
								.getUserObject();

						if (e.getClickCount() == 2) {
							if (e.getButton() == MouseEvent.BUTTON1) {
								String pathName = ManagerSettings
										.getPathDocument()
										+ "\\" + document.getTitle_alias();
								System.out.println(pathName);
								Path path = Paths.get(pathName);
								try {
									 Desktop.getDesktop().browse(path.toUri());
//									Desktop.getDesktop().open(
//											new File(pathName));
									// JOptionPane.showMessageDialog(null,
									// "Disponible en: " + pathName);
								} catch (IOException e1) {
									JOptionPane
											.showMessageDialog(
													null,
													"Este documento no está disponible. \n Verifique la ruta de los documentos en Configurar Sistema"
											/* + e1.getMessage() */);
								}
							}

						}
						if (e.getClickCount() == 1) {
							String resume = document.getIntrotext();
							jTextPane.setText(resume);
							jTextPane.updateUI();
						}
					}
				}

			});
			docjTree
					.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
						public void mouseMoved(java.awt.event.MouseEvent e) {
							TreePath selectedPath = docjTree
									.getPathForLocation(e.getX(), e.getY());
							docjTree.setSelectionPath(selectedPath);
							// Auto-generated
							// Event stub
							// mouseMoved()
						}
					});
		}
		return docjTree;
	}

	/**
	 * This method initializes modjTree
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getModjTree() {
		if (modjTree == null) {
			modjTree = new JTree();
			modjTree
					.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
						public void mouseMoved(java.awt.event.MouseEvent e) {
							TreePath selectedPath = modjTree
									.getPathForLocation(e.getX(), e.getY());
							modjTree.setSelectionPath(selectedPath);
							// Auto-generated
							// Event stub
							// mouseMoved()
						}
					});

			modjTree.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) modjTree
							.getLastSelectedPathComponent();
					if (selectedNode.getLevel() == 0) {
						if (!Controler.existDataToShow())
							JOptionPane
									.showMessageDialog(
											null,
											convertToMultiline("La aplicación no ha cargado los datos necesarios para funcionar correctamente.\n Importe los datos en el menú 'Configurar Sistema'."),
											"Cargando datos",
											JOptionPane.INFORMATION_MESSAGE);
					}

					if (e.getClickCount() == 2) {

						if (selectedNode.isLeaf()
								&& selectedNode.getLevel() != 0) {
							Modification modification = (Modification) selectedNode
									.getUserObject();

							if (e.getButton() == MouseEvent.BUTTON1) {
								String pathName = ManagerSettings
										.getPathDocument()
										+ "\\" + modification.getTitleAlias();
								Path path = Paths.get(pathName);

								if (modification.getAction().equals("retiro")) {
									JOptionPane
											.showMessageDialog(
													null,
													"Este documento ha sido eliminado.",
													"Mensaje",
													JOptionPane.ERROR_MESSAGE);
									return;
								}
								try {
									Desktop.getDesktop().browse(path.toUri());
									// Desktop.getDesktop().open(new
									// File(pathName));
									// JOptionPane.showMessageDialog(null,
									// "Disponible en: " + pathName);
								} catch (IOException e1) {
									JOptionPane
											.showMessageDialog(
													null,
													"Este documento no está disponible. \n Verifique la ruta de los documentos en Configurar Sistema."
											/* + e1.getMessage() */);
								}
							}

						}
					}

				}
			});
		}
		return modjTree;
	}

	/**
	 * This method initializes docjScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getDocjScrollPane() {
		if (docjScrollPane == null) {
			docjScrollPane = new JScrollPane();
			docjScrollPane.setViewportView(getDocjTree());
			docjScrollPane.add(new JButton());
		}
		return docjScrollPane;
	}

	/**
	 * This method initializes modjScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getModjScrollPane() {
		if (modjScrollPane == null) {
			modjScrollPane = new JScrollPane();
			modjScrollPane.setViewportView(getModjTree());
		}
		return modjScrollPane;
	}

	/**
	 * This method initializes searchjPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getSearchjPanel() {
		if (searchjPanel == null) {
			findjLabel1 = new JLabel();
			findjLabel1.setBounds(new Rectangle(215, 227, 74, 25));
			findjLabel1.setFont(new Font("Dialog", Font.BOLD, 12));
			findjLabel1.setText("Buscando...");
			findjLabel1.setVisible(false);
			searchjPanel = new JPanel();
			searchjPanel.setLayout(null);

			searchjPanel.setBounds(new Rectangle(709, 156, 298, 585));
			searchjPanel.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(new Color(51, 153, 0), 2),
					"Buscar Documentos", TitledBorder.CENTER, TitledBorder.TOP,
					new Font("Dialog", Font.BOLD, 14), new Color(51, 51, 51)));

			searchjPanel.setBackground(Color.white);
			searchjPanel.add(getTitletextField(), null);
			searchjPanel.add(getContenttextField(), null);
			searchjPanel.add(getJButton(), null);
			searchjPanel.add(getTitlejCheckBox(), null);
			searchjPanel.add(getContentjCheckBox(), null);
			searchjPanel.add(getDatejCheckBox(), null);
			searchjPanel.add(getJScrollPane(), null);
			searchjPanel.add(getResumejScrollPane1(), null);

			searchjPanel.add(getXSwingDateChooser(), null);
			searchjPanel.add(getXSwingDateChooser1(), null);
			searchjPanel.add(getJCheckBox(), null);
			searchjPanel.add(findjLabel1, null);
		}
		return searchjPanel;
	}

	/**
	 * This method initializes titletextField
	 * 
	 * @return java.awt.TextField
	 */
	int flag = 0;
	private JCheckBox jCheckBox = null;
	private JLabel jLabel14 = null;
	private JLabel findjLabel1 = null;

	private TextField getTitletextField() {
		if (titletextField == null) {
			titletextField = new TextField();
			titletextField.setBounds(new Rectangle(35, 50, 246, 27));
			titletextField.setEnabled(false);
			titletextField
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							executeSearchAction();
						}
					});

			titletextField.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if (titletextField.isEditable())
						showPopup(e);
				}

				public void mouseReleased(MouseEvent e) {
					if (titletextField.isEditable())
						showPopup(e);
				}

				private void showPopup(MouseEvent e) {
					if (e.isPopupTrigger()) {
						JPopupMenu popup = getJPopupMenuCopyAndPaste();
						Component c = e.getComponent();
						// System.out.println("Component text field:" + c);
						// System.out.println("Is Focusable" + c.isFocusable());
						popup.show(e.getComponent(), e.getX(), e.getY());
						setComponentThatTriggerCopyPasteMenu((TextField) c);
					}
				}
			});

		}
		return titletextField;
	}

	/**
	 * This method initializes contenttextField
	 * 
	 * @return java.awt.TextField
	 */
	private TextField getContenttextField() {
		if (contenttextField == null) {
			contenttextField = new TextField();
			contenttextField.setBounds(new Rectangle(35, 108, 246, 27));
			contenttextField.setEnabled(false);

			contenttextField
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							executeSearchAction();
						}
					});

			contenttextField
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mousePressed(MouseEvent e) {
							if (contenttextField.isEditable())
								showPopup(e);
						}

						public void mouseReleased(MouseEvent e) {
							if (contenttextField.isEditable())
								showPopup(e);
						}

						private void showPopup(MouseEvent e) {
							if (e.isPopupTrigger()) {
								JPopupMenu popup = getJPopupMenuCopyAndPaste();
								Component c = e.getComponent();
								// System.out.println("Component text field:" +
								// c);
								// System.out.println("Is Focusable" +
								// c.isFocusable());
								popup
										.show(e.getComponent(), e.getX(), e
												.getY());
								setComponentThatTriggerCopyPasteMenu((TextField) c);
							}
						}
					});
		}
		return contenttextField;
	}

	/**
	 * Calendar
	 */
	private JXDatePicker getXSwingDateChooser() {
		if (this.xSwingDateChooser == null) {
			this.xSwingDateChooser = new JXDatePicker();
			this.xSwingDateChooser.setEnabled(false);
			xSwingDateChooser.setBounds(new Rectangle(107, 161, 145, 25));

			xSwingDateChooser.setToolTipText("Día/Mes/Año");
			xSwingDateChooser.setDate(new Date("01/01/2000"));
			DateFormat df1 = new SimpleDateFormat("EE dd/MM/yyyy");
			DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat df3 = new SimpleDateFormat("yyyy");
			xSwingDateChooser.setFormats(df1, df2, df3);
			xSwingDateChooser.getMonthView().setZoomable(true);

			xSwingDateChooser
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							executeSearchAction();
						}
					});
			// this.xSwingDateChooser.setLocation(new Point(120, 170));
			// this.xSwingDateChooser.setSize(144, 24);

		}
		return this.xSwingDateChooser;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(128, 226, 75, 26));
			jButton.setText("Buscar");
			jButton.setFont(new Font("Dialog", Font.BOLD, 12));
			jButton.setFocusTraversalPolicyProvider(true);
			jButton.addActionListener(new java.awt.event.ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (!new File("All_Settings.cfg").exists()) {
						JOptionPane
								.showMessageDialog(null,
										"No existen datos en la aplicación, \n realice la importación");

					} else {
						defaultListModel.removeAllElements();
						int flag = 0;

						Document document = new Document();
						LinkedList<Document> listD = new LinkedList<Document>();

						findjLabel1.setVisible(true);
						jContentPane.update(jContentPane.getGraphics());
						executeSearchAction();
						findjLabel1.setVisible(false);
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * Filtra documentos por criterio
	 * 
	 * @param document
	 * @param listD
	 */
	private void executeSearchAction() {

		fillQueries();
		// setting the cell renderer object to display each items in the list
		if (activatedTitleQuery) {
			System.out.println("my cell renderer");
			jListFind.setCellRenderer(new FoundDocumentsCellRenderer(
					queryInTitle));
		} else // default renderer if not query
		{
			System.out.println("default cell renderer");
			jListFind.setCellRenderer(new DefaultListCellRenderer());
		}

		// removing old found documents and cleaning summary pane
		defaultListModel.removeAllElements();
		jTextPane.setText("");

		// getting search info
		String titleFilter = null;
		String summaryFilter = null;
		Date fromDateFilter = null;
		Date untilDateFilter = null;
		if (titlejCheckBox.isSelected())
			titleFilter = titletextField.getText();
		if (contentjCheckBox.isSelected())
			summaryFilter = contenttextField.getText();
		if (datejCheckBox.isSelected()) {
			fromDateFilter = xSwingDateChooser.getDate();
		}
		if (jCheckBox.isSelected()) {
			untilDateFilter = xSwingDateChooser1.getDate();
		}

		// validating search criteria---------------------------------
		if (titleFilter == null && summaryFilter == null
				&& fromDateFilter == null && untilDateFilter == null) {
			JOptionPane.showMessageDialog(null,
					"Seleccione un criterio de búsqueda.", "Búsqueda",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if ((titleFilter != null && titleFilter.length() == 0)
				|| (summaryFilter != null && summaryFilter.length() == 0)) {
			JOptionPane
					.showMessageDialog(
							null,
							convertToMultiline("Uno de los criterios seleccionados no contiene ninguna consulta."),
							"Búsqueda", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if ((titleFilter != null && titleFilter.length() <= 1)
				|| (summaryFilter != null && summaryFilter.length() <= 1)) {
			JOptionPane
					.showMessageDialog(
							null,
							convertToMultiline("Debe insertar una consulta que contenga palabras con más de una letra."),
							"Búsqueda", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (!Controler.existDataToShow()) {
			JOptionPane
					.showMessageDialog(
							null,
							"No existen documentos en la aplicación. Por favor, realice una actualización.",
							"Resultado de la búsqueda",
							JOptionPane.INFORMATION_MESSAGE);

			return;
		}
		if (datejCheckBox.isSelected()
				&& jCheckBox.isSelected()
				&& xSwingDateChooser.getDate().after(
						xSwingDateChooser1.getDate())) {
			JOptionPane
					.showMessageDialog(
							null,
							"Debe insertar un rango de fechas válido. \n Ejemplo: Desde 31/12/2013,  Hasta 31/12/2014",
							"Búsqueda", JOptionPane.WARNING_MESSAGE);
			return;
		}
		// -----------------------------------------------------------

		// finding the documents that fulfills the search criteria
		try {
			for (Document d : Controler.findDocumentsFilteredBy(titleFilter,
					summaryFilter, fromDateFilter, untilDateFilter)) {
				defaultListModel.addElement(d);
				jListFind.updateUI();
			}
		} catch (Exception e) {
			if (e.getMessage() != null
					&& e.getMessage().equals("insignificant query")) {
				JOptionPane
						.showMessageDialog(
								null,
								convertToMultiline("La consulta realizada contiene palabras que son \n demasiado comunes y han sido ignoradas en la búsqueda."),
								"Búsqueda sin resultados",
								JOptionPane.WARNING_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(null,
					"Ha ocurrido un error durante la búsqueda", "Búsqueda",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// jListFind.updateUI();

		// the search return none document
		if (defaultListModel.size() == 0)
			JOptionPane
					.showMessageDialog(
							null,
							"No se ha encontrado ningún documento que cumpla con los criterios especificados.",
							"Resultado de la búsqueda",
							JOptionPane.INFORMATION_MESSAGE);
	}

	List<String> queryInTitle = null;
	List<String> queryInSummary = null;
	Boolean activatedTitleQuery = false;
	Boolean activatedSummaryQuery = false;
	private JLabel jLabel1 = null;

	private void fillQueries() {
		String[] titleQuery = null;
		String[] summaryQuery = null;
		activatedTitleQuery = false;
		activatedSummaryQuery = false;
		queryInTitle = new ArrayList<String>();
		queryInSummary = new ArrayList<String>();

		if (titlejCheckBox.isSelected()) // activated search by title
		{
			activatedTitleQuery = true;
			titleQuery = Utils.splitWithMultipleDelimeters(Utils
					.stripAccentsAndToLowerCase(titletextField.getText()));

			for (String s : titleQuery) {
				queryInTitle.add(s);
			}
		}

		if (contentjCheckBox.isSelected())// activated search by summary
		{
			activatedSummaryQuery = true;
			summaryQuery = Utils.splitWithMultipleDelimeters(Utils
					.stripAccentsAndToLowerCase(contenttextField.getText()));
			for (String s : summaryQuery) {
				queryInSummary.add(s);
			}
		}
	}

	/**
	 * This method initializes titlejCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getTitlejCheckBox() {
		if (titlejCheckBox == null) {
			titlejCheckBox = new JCheckBox();
			titlejCheckBox.setBounds(new Rectangle(14, 23, 159, 27));
			titlejCheckBox.setText("Título");
			titlejCheckBox.setFont(new Font("Dialog", Font.BOLD, 12));
			titlejCheckBox.setBackground(Color.white);
			titlejCheckBox
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.out.println("actionPerformed()"); // TODO
							// Auto-generated
							// Event
							// stub
							// actionPerformed()
							if (titlejCheckBox.isSelected()) {
								titletextField.setEnabled(true);
							} else {
								titletextField.setEnabled(false);

							}

							if (!searchActivated()) {
								// removing old found documents and cleaning
								// summary pane
								defaultListModel.removeAllElements();
								jTextPane.setText("");
							}

						}
					});
		}
		return titlejCheckBox;
	}

	/**
	 * This method initializes contentjCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getContentjCheckBox() {
		if (contentjCheckBox == null) {
			contentjCheckBox = new JCheckBox();
			contentjCheckBox.setBounds(new Rectangle(14, 79, 163, 25));
			contentjCheckBox.setText("Resumen");
			contentjCheckBox.setFont(new Font("Dialog", Font.BOLD, 12));
			contentjCheckBox.setBackground(Color.white);
			contentjCheckBox
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.out.println("actionPerformed()"); // TODO
							// Auto-generated
							// Event
							// stub
							// actionPerformed()
							if (contentjCheckBox.isSelected()) {
								contenttextField.setEnabled(true);
							} else {
								contenttextField.setEnabled(false);
							}

							if (!searchActivated()) {
								// removing old found documents and cleaning
								// summary pane
								defaultListModel.removeAllElements();
								jTextPane.setText("");
							}
						}
					});
		}
		return contentjCheckBox;
	}

	/**
	 * This method initializes datejCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDatejCheckBox() {
		if (datejCheckBox == null) {
			datejCheckBox = new JCheckBox();

			datejCheckBox.setBounds(new Rectangle(14, 161, 91, 24));
			datejCheckBox.setText("Desde");
			datejCheckBox.setFont(new Font("Dialog", Font.BOLD, 12));
			datejCheckBox.setBackground(Color.white);

			datejCheckBox
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.out.println("actionPerformed()"); // TODO
							// Auto-generated
							// Event
							// stub
							// actionPerformed()
							if (datejCheckBox.isSelected()) {
								xSwingDateChooser.setEnabled(true);
							} else {
								xSwingDateChooser.setEnabled(false);
							}

							if (!searchActivated()) {
								// removing old found documents and cleaning
								// summary pane
								defaultListModel.removeAllElements();
								jTextPane.setText("");
							}
						}
					});
		}
		return datejCheckBox;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();

			jScrollPane.setBounds(new Rectangle(7, 260, 284, 168));
			jScrollPane.setViewportView(getJListFind());
			jScrollPane.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(new Color(51, 153, 0), 2),
					"Documentos", TitledBorder.CENTER, TitledBorder.TOP,
					new Font("Dialog", Font.BOLD, 12), Color.black));

		}
		return jScrollPane;
	}

	/**
	 * This method initializes defaultListModel
	 * 
	 * @return javax.swing.DefaultListModel
	 */
	private DefaultListModel getDefaultListModel() {
		if (defaultListModel == null) {
			defaultListModel = new DefaultListModel();
		}
		return defaultListModel;
	}

	private Boolean searchActivated() {
		return (titlejCheckBox.isSelected() || contentjCheckBox.isSelected()
				|| datejCheckBox.isSelected() || jCheckBox.isSelected());
	}

	/**
	 * This method initializes jListFind
	 * 
	 * @return java.awt.List
	 */
	private JList getJListFind() {
		if (jListFind == null) {
			jListFind = new JList();
			jListFind.setModel(getDefaultListModel());
			jListFind.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					Document document = (Document) jListFind.getSelectedValue();
					if (e.getClickCount() == 1) {
						// show resume
						String resume = (!activatedSummaryQuery) ? document
								.getIntrotext() : Utils.getSummaryInBoldOf(
								document, queryInSummary);
						// jTextPane.setText("<html><b>"+document.getIntrotext()+"</b></html>");
						// jTextPane.setText(resume.replaceAll("&nbsp;", ""));
						jTextPane.setText(resume);
						jTextPane.updateUI();

						// search and show in jtree
						TreePath path = find((DefaultMutableTreeNode) docjTree
								.getModel().getRoot(), document);

						docjTree.setSelectionPath(path);
						docjTree.scrollPathToVisible(path);
					}
					if (e.getClickCount() == 2) {
						if (e.getButton() == MouseEvent.BUTTON1) {
							if (document.getTitle_alias().equals("")) {
								JOptionPane
										.showMessageDialog(
												null,
												"Este documento no está disponible. \n Verifique la ruta de los documentos en Configurar Sistema");
								return;
							}

							String pathName = ManagerSettings.getPathDocument()
									+ "\\" + document.getTitle_alias();
							Path path = Paths.get(pathName);
							try {
								Desktop.getDesktop().browse(path.toUri());

							} catch (IOException e1) {
								JOptionPane
										.showMessageDialog(
												null,
												"Este documento no está disponible. \n Verifique la ruta de los documentos en Configurar Sistema");
							}

						}

					}
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

			});
		}
		return jListFind;
	}

	/**
	 * This method initializes resumejScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getResumejScrollPane1() {
		if (resumejScrollPane1 == null) {
			resumejScrollPane1 = new JScrollPane();
			resumejScrollPane1.setBounds(new Rectangle(8, 429, 283, 150));
			resumejScrollPane1
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			resumejScrollPane1.setViewportView(getJTextPane());
			resumejScrollPane1.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(new Color(51, 153, 0), 2),
					"Resumen", TitledBorder.CENTER, TitledBorder.TOP, new Font(
							"Dialog", Font.BOLD, 12), Color.black));
		}
		return resumejScrollPane1;
	}

	/**
	 * This method initializes jTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setEditable(false);
			jTextPane.setContentType("text/html");
		}
		return jTextPane;
	}

	/**
	 * This method initializes xSwingDateChooser1
	 * 
	 * @return org.jdesktop.swingx.JXDatePicker
	 */
	private JXDatePicker getXSwingDateChooser1() {
		if (xSwingDateChooser1 == null) {
			xSwingDateChooser1 = new JXDatePicker();
			xSwingDateChooser1.setBounds(new Rectangle(107, 191, 145, 25));
			this.xSwingDateChooser1.setEnabled(false);

			xSwingDateChooser1.setToolTipText("Día/Mes/Año");
			xSwingDateChooser1.setDate(new Date());
			DateFormat df1 = new SimpleDateFormat("EE dd/MM/yyyy");
			DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat df3 = new SimpleDateFormat("yyyy");
			xSwingDateChooser1.setFormats(df1, df2, df3);
			xSwingDateChooser1.getMonthView().setZoomable(true);

			xSwingDateChooser1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							executeSearchAction();
						}
					});
		}
		return xSwingDateChooser1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setBackground(new Color(84, 166, 82));
			jPanel.setBounds(new Rectangle(1008, 155, 11, 589));
		}
		return jPanel;
	}

	private static String convertToMultiline(String text) {
		return "<html>" + text.replaceAll("\n", "<br>") + "</html>";
	}

	public static void LoadTreesInitialConfiguration() {
		/**
		 * Configuracion inicial de los arboles
		 */
		DefaultTreeModel docTreeModel = (DefaultTreeModel) docjTree.getModel();
		DefaultMutableTreeNode rootDoc = new DefaultMutableTreeNode(
				"Manuales y Normas");
		docTreeModel.setRoot(rootDoc);

		DefaultTreeModel modTreeModel = (DefaultTreeModel) modjTree.getModel();
		DefaultMutableTreeNode rootMod = new DefaultMutableTreeNode(
				"Modificaciones");
		modTreeModel.setRoot(rootMod);

		/**
		 * Personalizando arboles
		 */
		if (manualIcon != null && sectionIcon != null && chapterIcon != null
				&& documentIcon != null)
			docjTree.setCellRenderer(new DocTreeRenderer(rootIcon, manualIcon,
					sectionIcon, chapterIcon, documentIcon));

		if (documentIcon != null)
			modjTree.setCellRenderer(new ModificationTreeRenderer(rootIcon,
					documentIcon));
	}

	private static void EjecutarPHP() {
		try {
			/**
			 * Pongo tooooda la ruta hacia PHP.exe y hacia el script que quiero
			 * ejecutar
			 */
			Process p = Runtime.getRuntime().exec("http://www.egrafip.cu");

			p.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(p
					.getInputStream()));

			String line = reader.readLine();

			System.out.println(line);

			p.destroy(); // importantísimo para vaciar la memoria.

		}

		catch (Exception e) {
			System.out.println("Error al ejecutar comando de sistema");
			e.printStackTrace();
		}
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(14, 191, 91, 24));
			jCheckBox.setText("Hasta");
			jCheckBox.setBackground(Color.white);
			jCheckBox.setFont(new Font("Dialog", Font.BOLD, 12));
			jCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO
					// Auto-generated
					// Event
					// stub
					// actionPerformed()
					if (jCheckBox.isSelected()) {
						xSwingDateChooser1.setEnabled(true);
					} else {
						xSwingDateChooser1.setEnabled(false);

					}

					if (!searchActivated()) {
						// removing old found documents and cleaning summary
						// pane
						defaultListModel.removeAllElements();
						jTextPane.setText("");
					}

				}
			});
		}
		return jCheckBox;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
			UIManager
					.put(CalendarHeaderHandler.uiControllerID,
							"org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler");
			UIManager.put(SpinningCalendarHeaderHandler.ARROWS_SURROUND_MONTH,
					Boolean.TRUE);

		} catch (Exception ex) {
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Provider thisClass = new Provider();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				try {

					if (new File("All_Settings.cfg").exists()) {
						CompactData compactData = (CompactData) Controler
								.ReadObject("All_Settings.cfg");

						/**
						 * Cargo la configuracion de la base de datos desde los
						 * ficheros
						 */
						ManagerSettings.setDir(compactData.getDir());
						ManagerSettings.setName(compactData.getName());
						ManagerSettings.setPass(compactData.getPass());
						ManagerSettings.setPathDocument(compactData
								.getPathDocument());
						ManagerSettings.setPort(compactData.getPort());
						ManagerSettings.setUsser(compactData.getUsser());

						/**
						 * Cargo los listados con la informacion a los ficheros
						 */
						ManagerDoc.setManuals(compactData.getListM());
						ManagerDoc.setSections(compactData.getListS());
						ManagerDoc.setChapters(compactData.getListC());
						ManagerDoc.setDocuments(compactData.getListD());
						ManagerDoc.setModifications(compactData.getListModif());

					}

					if (!Controler.existDataToShow()) {
						LoadTreesInitialConfiguration();
						thisClass.setVisible(true);
						JOptionPane
								.showMessageDialog(
										null,
										convertToMultiline("La aplicación no ha cargado los datos necesarios para funcionar correctamente. \n Realice la importación desde la base de datos en el Menú 'Configurar Sistema'."),
										"Cargando datos",
										JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					/**
					 * CUSTOMIZAR EL ARBOL
					 */
					// Documents tree
					DefaultTreeModel docTree = (DefaultTreeModel) docjTree
							.getModel();
					DefaultMutableTreeNode rootDoc = new DefaultMutableTreeNode(
							"Manuales y Normas");
					docTree.setRoot(rootDoc);
					if (manualIcon != null && sectionIcon != null
							&& chapterIcon != null && documentIcon != null)
						docjTree.setCellRenderer(new DocTreeRenderer(rootIcon,
								manualIcon, sectionIcon, chapterIcon,
								documentIcon));
					Controler.LoadDocuments(docTree, rootDoc, ManagerDoc
							.getManuals(), ManagerDoc.getSections(), ManagerDoc
							.getChapters(), ManagerDoc.getDocuments());
					docjTree.expandRow(0);

					// Modifications tree
					DefaultTreeModel modTree = (DefaultTreeModel) modjTree
							.getModel();
					DefaultMutableTreeNode rootMod = new DefaultMutableTreeNode(
							"Modificaciones");
					modTree.setRoot(rootMod);
					if (documentIcon != null)
						modjTree.setCellRenderer(new ModificationTreeRenderer(
								rootIcon, documentIcon));
					Controler.LoadModificaction(modTree, rootMod, ManagerDoc
							.getModifications());
					modjTree.expandRow(0);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e) {

					// Documents tree
					DefaultTreeModel docTree = (DefaultTreeModel) docjTree
							.getModel();

					docTree.setRoot(null);

					// Modifications tree
					DefaultTreeModel modTree = (DefaultTreeModel) modjTree
							.getModel();

					modTree.setRoot(null);

					e.printStackTrace();
				}

				thisClass.setVisible(true);

			}

		});
	}

	/**
	 * This is the default constructor
	 */
	public Provider() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(1024, 768);
		// this.setSize(Toolkit.getDefaultToolkit().getScreenSize());

		// --

		// --
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource(
						"/citmatel/cu/icons/LOGO-EGRAFIP-(PNG)16.png")));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Finanzas al Día. (Proveedor 1.0.0)");
		this.setResizable(false);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);

		// Load icons for trees
		rootIcon = null;
		manualIcon = new ImageIcon(getClass().getResource(
				"/citmatel/cu/icons/Manual.png"));
		sectionIcon = new ImageIcon(getClass().getResource(
				"/citmatel/cu/icons/Seccion.png"));
		chapterIcon = new ImageIcon(getClass().getResource(
				"/citmatel/cu/icons/Capitulo.png"));
		documentIcon = new ImageIcon(getClass().getResource(
				"/citmatel/cu/icons/documento.png"));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {

			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(801, 92, 217, 65));
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge_11.jpg")));
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(801, 91, 217, 65));
			jLabel14.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge_11.jpg")));
			jLabel14.setText("");

			jLabel12 = new JLabel();
			jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					DefaultHelpBroker help = new DefaultHelpBroker();

					try {

						File fichero = new File("help/help_set.hs");
						URL hsURL = fichero.toURI().toURL();
						help.setHelpSet(new HelpSet(
								getClass().getClassLoader(), hsURL));
						help.initPresentation();
						// help.setActivationWindow(this);

						help.setDisplayed(true);

						// --------CENTRO AL JAVAHELP----
						Dimension screenSize = Toolkit.getDefaultToolkit()
								.getScreenSize();
						help.setSize(new Dimension(925, 760));
						Point point = new Point((screenSize.width - help
								.getSize().width) / 2,
								(screenSize.height - help.getSize().height) / 2);

						help.setLocation(point);

						// --------------------------------------------------
						// help.getWindowPresentation().getHelpWindow()
						// .addWindowListener(
						// new java.awt.event.WindowAdapter() {
						// public void windowClosing(
						// java.awt.event.WindowEvent e) {
						// // cierro el dialog
						// // por defecto y listo
						// dispose();
						// }
						// });
						help
								.getWindowPresentation()
								.getHelpWindow()
								.setIconImage(
										Toolkit
												.getDefaultToolkit()
												.getImage(
														getClass()
																.getResource(
																		"/citmatel/cu/icons/LOGO-EGRAFIP-(PNG)16.png")));

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				public void mouseEntered(java.awt.event.MouseEvent e) {
					// Event stub
					// mouseEntered()
					jLabel12.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-Over_10.jpg")));

				}

				public void mouseExited(java.awt.event.MouseEvent e) {
					jLabel12.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-Over_10.jpg")));

					// Event stub
					// mouseExited()
					jLabel12.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge_10.jpg")));
				}
			});
			jLabel12.setBounds(new Rectangle(695, 91, 106, 65));
			jLabel12.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge_10.jpg")));
			jLabel12.setText("");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(668, 91, 27, 65));
			jLabel11.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge_09.jpg")));
			jLabel11.setText("");
			jLabel10 = new JLabel();
			jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					ConfigDB configDB = new ConfigDB(Provider.this);
					configDB.setVisible(true);

				}

				public void mouseEntered(java.awt.event.MouseEvent e) {
					// Event stub
					// mouseEntered()
					jLabel10.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-Over_08.jpg")));

				}

				public void mouseExited(java.awt.event.MouseEvent e) {
					jLabel10.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-Over_08.jpg")));

					// Event stub
					// mouseExited()
					jLabel10.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge_08.jpg")));
				}
			});
			jLabel10.setBounds(new Rectangle(542, 91, 126, 65));
			jLabel10.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge_08.jpg")));
			jLabel10.setText("");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(514, 91, 28, 65));
			jLabel9.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge_07.jpg")));
			jLabel9.setText("");
			jLabel8 = new JLabel();
			jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					Generatekey generatekey = new Generatekey(null);
					generatekey.setVisible(true);

				}

				public void mouseEntered(java.awt.event.MouseEvent e) {
					// Event stub
					// mouseEntered()
					jLabel8.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-Over_06.jpg")));

				}

				public void mouseExited(java.awt.event.MouseEvent e) {
					jLabel8.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-Over_06.jpg")));

					// Event stub
					// mouseExited()
					jLabel8.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge_06.jpg")));
				}
			});
			jLabel8.setBounds(new Rectangle(383, 91, 131, 65));
			jLabel8.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge_06.jpg")));
			jLabel8.setText("");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(356, 91, 27, 65));
			jLabel7.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge_05.jpg")));
			jLabel7.setText("");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(197, 91, 159, 65));
			jLabel6.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge_04.jpg")));
			jLabel6.setText("");
			jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					CompleteUpdate update = new CompleteUpdate(Provider.this);
					update.setVisible(true);

				}

				public void mouseEntered(java.awt.event.MouseEvent e) {
					// Event stub
					// mouseEntered()
					jLabel6.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-Over_04.jpg")));

				}

				public void mouseExited(java.awt.event.MouseEvent e) {
					jLabel6.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-Over_04.jpg")));

					// Event stub
					// mouseExited()
					jLabel6.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge_04.jpg")));
				}
			});
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(171, 91, 26, 65));
			jLabel5.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge_03.jpg")));
			jLabel5.setText("");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(0, 91, 171, 65));
			jLabel2.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge_02.jpg")));
			jLabel2.setText("");
			jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					CompactUpdate update = new CompactUpdate(Provider.this);
					update.setVisible(true);
				}

				public void mouseEntered(java.awt.event.MouseEvent e) {
					// Event stub
					// mouseEntered()
					jLabel2.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-Over_02.jpg")));

				}

				public void mouseExited(java.awt.event.MouseEvent e) {
					jLabel2.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge_02.jpg")));

					// Event stub
					// mouseExited()
					jLabel2.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge_02.jpg")));
				}
			});
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(0, 0, 1018, 92));
			jLabel.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge_01.jpg")));
			jLabel.setText("");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(new Color(40, 143, 38));
			jContentPane.add(getJSplitPane(), null);
			jContentPane.add(getSearchjPanel(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getJPanel(), null);

			jContentPane.add(jLabel1, null);
		}
		return jContentPane;
	}

	public static void ReloadDataInDocTree() throws SQLException {
		// Árbol de documentos
		DefaultTreeModel docTreeModel = (DefaultTreeModel) docjTree.getModel();
		DefaultMutableTreeNode rootDoc = new DefaultMutableTreeNode(
				"Manuales y Normas");
		docTreeModel.setRoot(rootDoc);

		Controler.LoadDocuments(docTreeModel, rootDoc, ManagerDoc.getManuals(),
				ManagerDoc.getSections(), ManagerDoc.getChapters(), ManagerDoc
						.getDocuments());
		docjTree.expandRow(0);

	}

	public static void ReloadDataInModTree() throws SQLException {
		// Árbol de modificaciones
		DefaultTreeModel modTreeModel = (DefaultTreeModel) modjTree.getModel();
		DefaultMutableTreeNode rootMod = new DefaultMutableTreeNode(
				"Modificaciones");
		modTreeModel.setRoot(rootMod);

		Controler.LoadModificaction(modTreeModel, rootMod, ManagerDoc
				.getModifications());
		modjTree.expandRow(0);
	}

	@SuppressWarnings("unchecked")
	private TreePath find(DefaultMutableTreeNode root, Document doc) {
		Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = e.nextElement();
			if (node.toString().equalsIgnoreCase(
					doc.getTitle() + "   (" + doc.getDate() + ")")
					&& ((Document) node.getUserObject()).getId() == doc.getId()) {
				return new TreePath(node.getPath());

			}

		}
		return null;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
