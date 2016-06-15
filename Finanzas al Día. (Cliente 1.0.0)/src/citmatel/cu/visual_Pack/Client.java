package citmatel.cu.visual_Pack;
test
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.help.DefaultHelpBroker;
import javax.help.HelpSet;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane; //testing
import javax.swing.LookAndFeel;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import citmatel.cu.class_Pack.Chapter;
import citmatel.cu.class_Pack.Controler;
import citmatel.cu.class_Pack.Document;
import citmatel.cu.class_Pack.LicenseState;
import citmatel.cu.class_Pack.LoadDataResultType;
import citmatel.cu.class_Pack.ManagerDoc;
import citmatel.cu.class_Pack.Manual;
import citmatel.cu.class_Pack.Modification;
import citmatel.cu.class_Pack.Section;
import citmatel.cu.class_Pack.Utils;
import citmatel.cu.utils.DesktopApi;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;
import org.omg.CORBA.Environment;

import sun.swing.UIAction;
import java.awt.GridBagLayout;

/**
 * @author jorgem
 * 
 */
public class Client extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private static JTree docjTree = null;
	private static JTree modjTree = null;
	private JScrollPane docjScrollPane = null;
	private JScrollPane modjScrollPane = null;
	private JPanel searchjPanel = null;
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
	private DefaultListModel<Document> defaultListModel = null; // @jve:decl-index=0:visual-constraint="1052,78"
	private JList<Document> jListFind = null;
	private JScrollPane resumejScrollPane1 = null;
	private JTextPane jTextPane = null;

	// Icons for trees
	private static ImageIcon rootIcon = null; // @jve:decl-index=0:
	private static ImageIcon manualIcon = null; // @jve:decl-index=0:
	private static ImageIcon sectionIcon = null; // @jve:decl-index=0:
	private static ImageIcon chapterIcon = null;
	private static ImageIcon documentIcon = null; // @jve:decl-index=0:
	private JXDatePicker fromJXDatePicker = null;
	private JXDatePicker toJXDatePicker = null;
	private JCheckBox dateTojCheckBox = null;
	private JPanel jPanel = null;
	private static JLabel findjLabel1 = null; // @jve:decl-index=0:visual-constraint="1039,10"

	public static JLabel getFindjLabel1() {
		return findjLabel1;
	}

	public static void setFindjLabel1(JLabel findjLabel1) {
		findjLabel1 = findjLabel1;
	}

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
	private JPopupMenu jPopupMenuCopyAndPaste = null; // @jve:decl-index=0:visual-constraint="458,840"
	Component componentThatTriggerCopyPasteMenu = null;

	public Component getComponentThatTriggerCopyPasteMenu() {
		return componentThatTriggerCopyPasteMenu;
	}

	public void setComponentThatTriggerCopyPasteMenu(
			Component whoTriggerCopyPasteMenu) {
		this.componentThatTriggerCopyPasteMenu = whoTriggerCopyPasteMenu;
	}

	/**
	 * This method initializes jPopupMenuCopyAndPaste
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	public JPopupMenu getJPopupMenuCopyAndPaste() {
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

					if (selectedNode == null)
						return;

					if (selectedNode.getLevel() == 0) {
						jTextPane.setText("");
						jTextPane.updateUI();
						if (!Controler.existDataToShow())
							JOptionPane
									.showMessageDialog(
											null,
											"La aplicación no ha cargado los datos necesarios para funcionar correctamente. Realice una actualización.",
											"Cargando datos",
											JOptionPane.WARNING_MESSAGE);

					}

					// !selectedNode.toString().equals("Manuales y Normas")
					if (selectedNode.isLeaf() && selectedNode.getLevel() != 0) {
						Document document = (Document) selectedNode
								.getUserObject();

						if (e.getClickCount() == 2) {
							if (e.getButton() == MouseEvent.BUTTON1) {

								try {
									Desktop.getDesktop()
											.open(new File(
													Controler.documentsPath
															+ File.separator
															+ document
																	.getTitle_alias()));
								} catch (Exception e1) {
									System.out.println(e1.getClass());
									System.out.println(e1.getMessage());
									JOptionPane
											.showMessageDialog(
													null,
													"Este documento no está disponible.",
													"Mensaje",
													JOptionPane.WARNING_MESSAGE);

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
					if (selectedNode == null)
						return;

					if (selectedNode.getLevel() == 0) {
						if (!Controler.existDataToShow())
							JOptionPane
									.showMessageDialog(
											null,
											"La aplicación no ha cargado los datos necesarios para funcionar correctamente. Realice una actualización.",
											"Cargando datos",
											JOptionPane.WARNING_MESSAGE);
					}
					if (e.getClickCount() == 2) {
						if (e.getButton() == MouseEvent.BUTTON1) {

							// !selectedNode.toString().equals("Modificaciones")
							if (selectedNode.isLeaf()
									&& selectedNode.getLevel() != 0) {

								Modification modification = (Modification) selectedNode
										.getUserObject();
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

									Desktop.getDesktop().open(
											new File(Controler.documentsPath
													+ File.separator
													+ modification
															.getTitleAlias()));
								} catch (Exception e2) {
									JOptionPane
											.showMessageDialog(null,
													"Este documento no está disponible.");

									// TODO Auto-generated catch block
									// e2.printStackTrace();
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
			// searchjPanel.setEnabled(Controler.existDataToShow());
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
			searchjPanel.add(getFromJXDatePicker(), null);
			searchjPanel.add(getToJXDatePicker(), null);
			searchjPanel.add(getDateTojCheckBox(), null);
			searchjPanel.add(findjLabel1, null);
		}
		return searchjPanel;
	}

	List<String> queryInTitle = null;
	List<String> queryInSummary = null;
	Boolean activatedTitleQuery = false;
	Boolean activatedSummaryQuery = false;

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
			fromDateFilter = fromJXDatePicker.getDate();
		}
		if (dateTojCheckBox.isSelected()) {
			untilDateFilter = toJXDatePicker.getDate();
		}

		// validating search
		// criteria-------------------------------------------------
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
		if (datejCheckBox.isSelected() && dateTojCheckBox.isSelected()
				&& fromJXDatePicker.getDate().after(toJXDatePicker.getDate())) {
			JOptionPane
					.showMessageDialog(
							null,
							"Debe insertar un rango de fechas válido. \n Ejemplo: Desde 31/12/2013,  Hasta 31/12/2014",
							"Búsqueda", JOptionPane.WARNING_MESSAGE);
			return;
		}
		// ---------------------------------------------------------------------------------------

		// findjLabel1.setVisible(true);
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

		// the search return none document
		if (defaultListModel.size() == 0)
			JOptionPane
					.showMessageDialog(
							null,
							"No se ha encontrado ningún documento que cumpla con los criterios especificados.",
							"Resultado de la búsqueda",
							JOptionPane.INFORMATION_MESSAGE);

	}

	/**
	 * This method initializes titletextField
	 * 
	 * @return java.awt.TextField
	 */
	private TextField getTitletextField() {
		if (titletextField == null) {
			titletextField = new TextField();
			titletextField.setEnabled(false);
			titletextField.setBounds(new Rectangle(34, 46, 246, 27));
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
			contenttextField.setEnabled(false);
			contenttextField.setBounds(new Rectangle(34, 104, 246, 27));
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(128, 226, 75, 26));
			jButton.setText("Buscar");
			// jButton.setEnabled(Controler.existDataToShow());
			jButton.setFont(new Font("Dialog", Font.BOLD, 12));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(java.awt.event.ActionEvent e) {
					findjLabel1.setVisible(true);
					// searchjPanel.update(searchjPanel.getGraphics());
					findjLabel1.update(findjLabel1.getGraphics());
					executeSearchAction();
					findjLabel1.setVisible(false);
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes titlejCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getTitlejCheckBox() {
		if (titlejCheckBox == null) {
			titlejCheckBox = new JCheckBox();
			titlejCheckBox.setBounds(new Rectangle(13, 19, 121, 27));
			titlejCheckBox.setText("Título");
			titlejCheckBox.setFont(new Font("Dialog", Font.BOLD, 12));
			titlejCheckBox.setBackground(Color.white);
			titlejCheckBox
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							titletextField.setEnabled(titlejCheckBox
									.isSelected());
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

	private Boolean searchActivated() {
		return (titlejCheckBox.isSelected() || contentjCheckBox.isSelected()
				|| datejCheckBox.isSelected() || dateTojCheckBox.isSelected());
	}

	/**
	 * This method initializes contentjCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getContentjCheckBox() {
		if (contentjCheckBox == null) {
			contentjCheckBox = new JCheckBox();
			contentjCheckBox.setBounds(new Rectangle(13, 75, 116, 25));
			contentjCheckBox.setText("Resumen");
			contentjCheckBox.setFont(new Font("Dialog", Font.BOLD, 12));
			contentjCheckBox.setBackground(Color.white);
			contentjCheckBox
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							contenttextField.setEnabled(contentjCheckBox
									.isSelected());
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
			datejCheckBox.setBounds(new Rectangle(13, 144, 88, 24));
			datejCheckBox.setText("Desde");
			datejCheckBox.setFont(new Font("Dialog", Font.BOLD, 12));
			datejCheckBox.setBackground(Color.white);
			datejCheckBox
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							fromJXDatePicker.setEnabled(datejCheckBox
									.isSelected());
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
	private DefaultListModel<Document> getDefaultListModel() {
		if (defaultListModel == null) {
			defaultListModel = new DefaultListModel<Document>();
		}
		return defaultListModel;
	}

	@SuppressWarnings("unchecked")
	/*
	 * private TreePath find(DefaultMutableTreeNode root, Document d) {
	 * Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
	 * while (e.hasMoreElements()) { DefaultMutableTreeNode node =
	 * e.nextElement(); try { Document currentDocument = (Document)
	 * node.getUserObject(); if (currentDocument.getId() == d.getId()) return
	 * new TreePath(node.getPath()); } catch(Exception e2) {} } return null; }
	 */
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

	/**
	 * This method initializes jListFind
	 * 
	 * @return java.awt.List
	 */
	private JList<Document> getJListFind() {
		if (jListFind == null) {
			jListFind = new JList<Document>();
			jListFind.setModel(getDefaultListModel()); // setting the model to
			// the list
			jListFind.setFont(jListFind.getFont().deriveFont(Font.PLAIN));
			jListFind.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) { // left click

						if (jListFind.getSelectedValue() != null) {
							Document document = jListFind.getSelectedValue();

							if (e.getClickCount() == 1) {
								System.out.println("SingleClick");
								// show resume
								String resume = (!activatedSummaryQuery) ? document
										.getIntrotext()
										: "<html>"
												+ Utils.getSummaryInBoldOf(
														document,
														queryInSummary)
												+ "</html>";
								// jTextPane.setText("<html><b>"+document.getIntrotext()+"</b></html>");
								// jTextPane.setText(resume.replaceAll("&nbsp;",
								// ""));
								jTextPane.setText(resume);
								jTextPane.updateUI();

								// search and show in jtree
								TreePath path = find(
										(DefaultMutableTreeNode) docjTree
												.getModel().getRoot(), document);
								docjTree.setSelectionPath(path);
								docjTree.scrollPathToVisible(path);
							}

							if (e.getClickCount() == 2) {
								System.out.println("DobleClick");
								if (document.getTitle_alias().equals("")) {
									JOptionPane
											.showMessageDialog(null,
													"Este documento no está disponible");
									return;
								}

								try {
									Desktop.getDesktop()
											.open(new File(
													Controler.documentsPath
															+ File.separator
															+ document
																	.getTitle_alias()));
								} catch (Exception e2) {
									// TODO Auto-generated catch block
									JOptionPane
											.showMessageDialog(null,
													"Este documento no está disponible");
								}
							}

						}

					}

				}

				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent arg0) {

				}

				@Override
				public void mouseReleased(MouseEvent arg0) {

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
		 * Persomalizando arboles
		 */
		if (manualIcon != null && sectionIcon != null && chapterIcon != null
				&& documentIcon != null)
			docjTree.setCellRenderer(new DocTreeRenderer(rootIcon, manualIcon,
					sectionIcon, chapterIcon, documentIcon));

		if (documentIcon != null)
			modjTree.setCellRenderer(new ModificationTreeRenderer(rootIcon,
					documentIcon));
	}

	public static void ReloadDataInDocTree() {
		// Árbol de documentos
		DefaultTreeModel docTreeModel = (DefaultTreeModel) docjTree.getModel();
		DefaultMutableTreeNode rootDoc = new DefaultMutableTreeNode(
				"Manuales y Normas");
		docTreeModel.setRoot(rootDoc);

		Controler.LoadDocuments(docTreeModel, ManagerDoc.getManuals(),
				ManagerDoc.getSections(), ManagerDoc.getChapters(), ManagerDoc
						.getDocuments());
		docjTree.expandRow(0);
	}

	public static void ReloadDataInModTree() {
		// Árbol de modificaciones
		DefaultTreeModel modTreeModel = (DefaultTreeModel) modjTree.getModel();
		DefaultMutableTreeNode rootMod = new DefaultMutableTreeNode(
				"Modificaciones");
		modTreeModel.setRoot(rootMod);

		Controler
				.LoadModificaction(modTreeModel, ManagerDoc.getModifications());
		modjTree.expandRow(0);
	}

	/**
	 * This method initializes fromJXDatePicker
	 * 
	 * @return org.jdesktop.swingx.JXDatePicker
	 */
	private JXDatePicker getFromJXDatePicker() {
		if (fromJXDatePicker == null) {
			fromJXDatePicker = new JXDatePicker();

			fromJXDatePicker.setToolTipText("(Día/Mes/Año)");
			fromJXDatePicker.setEnabled(false);
			fromJXDatePicker.setBounds(new Rectangle(108, 143, 152, 25));
			fromJXDatePicker.setDate(new Date("01/01/2000"));

			DateFormat df1 = new SimpleDateFormat("EE dd/MM/yyyy");
			DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat df3 = new SimpleDateFormat("yyyy");
			fromJXDatePicker.setFormats(df1, df2, df3);
			fromJXDatePicker.getMonthView().setZoomable(true);

			fromJXDatePicker
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							executeSearchAction();
						}
					});
		}
		return fromJXDatePicker;
	}

	/**
	 * This method initializes toJXDatePicker
	 * 
	 * @return org.jdesktop.swingx.JXDatePicker
	 */
	private JXDatePicker getToJXDatePicker() {
		if (toJXDatePicker == null) {
			toJXDatePicker = new JXDatePicker();
			toJXDatePicker.setToolTipText("(Día/Mes/Año)");
			toJXDatePicker.setEnabled(false);
			toJXDatePicker.setBounds(new Rectangle(108, 185, 152, 25));
			toJXDatePicker.setDate(new Date());
			DateFormat df1 = new SimpleDateFormat("EE dd/MM/yyyy");
			DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat df3 = new SimpleDateFormat("yyyy");
			toJXDatePicker.setFormats(df1, df2, df3);
			toJXDatePicker.getMonthView().setZoomable(true);

			toJXDatePicker
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							executeSearchAction();
						}
					});

		}
		return toJXDatePicker;
	}

	public void enablingSidePanel() {
		Boolean state = ManagerDoc.HasDocuments();
		Client thisClient = Client.this;
		thisClient.searchjPanel.setEnabled(state);
		thisClient.titlejCheckBox.setEnabled(state);
		thisClient.contentjCheckBox.setEnabled(state);
		thisClient.datejCheckBox.setEnabled(state);
		thisClient.dateTojCheckBox.setEnabled(state);
		thisClient.jButton.setEnabled(state);

		thisClient.jListFind.setEnabled(state);
		thisClient.jScrollPane.setEnabled(state);

		thisClient.jTextPane.setEnabled(state);
		thisClient.resumejScrollPane1.setEnabled(state);
	}

	/**
	 * This method initializes dateTojCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDateTojCheckBox() {
		if (dateTojCheckBox == null) {
			dateTojCheckBox = new JCheckBox();
			dateTojCheckBox.setBounds(new Rectangle(13, 185, 88, 24));
			dateTojCheckBox.setFont(new Font("Dialog", Font.BOLD, 12));
			dateTojCheckBox.setText("Hasta");
			dateTojCheckBox.setBackground(Color.white);
			dateTojCheckBox
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							toJXDatePicker.setEnabled(dateTojCheckBox
									.isSelected());
							if (!searchActivated()) {
								// removing old found documents and cleaning
								// summary pane
								defaultListModel.removeAllElements();
								jTextPane.setText("");
							}
						}
					});

		}
		return dateTojCheckBox;
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
			jPanel.setBounds(new Rectangle(1008, 153, 11, 591));
		}
		return jPanel;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/**
		 * Loading application general settings
		 */
		Controler.LoadSettings();
		System.out.println("NEW CLIENT: Ag4");

		/*
		 * try { UIManager.setLookAndFeel(Configuration.applicationStyles
		 * .get(Controler.getApplicationSettings().getStyle())); //
		 * UIManager.setLookAndFeel("setLookAndFeel");
		 * 
		 * UIManager .put(CalendarHeaderHandler.uiControllerID,
		 * "org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler");
		 * UIManager.put(SpinningCalendarHeaderHandler.ARROWS_SURROUND_MONTH,
		 * Boolean.TRUE);
		 * 
		 * } catch (UnsupportedLookAndFeelException e1) { JOptionPane
		 * .showMessageDialog( null,
		 * "El estilo seleccionado no es soportado por la aplicación.",
		 * "Estilo de la aplicación", JOptionPane.ERROR_MESSAGE);
		 * e1.printStackTrace(); } catch (ClassNotFoundException e2) {
		 * JOptionPane .showMessageDialog( null,
		 * "No se encuentra el estilo especificado para la aplicación.",
		 * "Estilo de la aplicación", JOptionPane.ERROR_MESSAGE);
		 * e2.printStackTrace(); } catch (InstantiationException e3) {
		 * JOptionPane .showMessageDialog( null,
		 * "Ha ocurrido un error inicializando el estilo especificado para la aplicación."
		 * , "Estilo de la aplicación", JOptionPane.ERROR_MESSAGE);
		 * e3.printStackTrace(); } catch (IllegalAccessException e4) {
		 * JOptionPane .showMessageDialog( null,
		 * "Ha ocurrido un error cargando el estilo especificado para la aplicación."
		 * , "Estilo de la aplicación", JOptionPane.ERROR_MESSAGE);
		 * e4.printStackTrace(); }
		 */
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				/**
				 * Initializing client
				 */
				Client thisClass = new Client();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				LoadTreesInitialConfiguration(); // Setting data-trees initial
				// configuration

				/**
				 * Loading content
				 */
				LoadDataResultType loadingResult = null;
				try {
					loadingResult = Controler.LoadData(); // loading data to
					// MangerDoc and
					// validating
					// license
				} catch (Exception e) {
					thisClass.setVisible(true);
					thisClass.enablingSidePanel();
					JOptionPane
							.showMessageDialog(
									null,
									"Ha ocurrido un error al cargar los datos de la aplicación. \n El fichero de configuración tiene un formato incorrecto o no ha sido encontrado.",
									"Cargando datos", JOptionPane.ERROR_MESSAGE);
					// deleting the appData file which is with incorrect
					// format...
					File toDelete = new File(Controler.appDataFilePath);
					Boolean deleted = toDelete.delete();
					System.out.println("deleted: " + deleted);
					thisClass.showExpiredLicenseMessage(null,
							"catch de loading data en main");
					return;
				}

				/**
				 * Analyzing loading results
				 */
				thisClass.setVisible(true);
				thisClass.enablingSidePanel();
				/**
				 *Incompatible Operative System
				 */
				if (loadingResult == LoadDataResultType.IncompatibleOS) {
					JOptionPane
							.showMessageDialog(
									null,
									"<html> Ha ocurrido un error determinando el identificador de su máquina para validar la licencia de la aplicación. <br> El sistema operativo actual es incompatible. </html>",
									"SO incompatible",
									JOptionPane.ERROR_MESSAGE);
					thisClass.showExpiredLicenseMessage(null,
							"incompatible os in main");
					return;
				}

				/**
				 *Visualizing data in trees
				 */
				if (loadingResult == LoadDataResultType.CorrectDataLoaded) {
					ReloadDataInDocTree();
					ReloadDataInModTree();
					thisClass.setVisible(true);
					thisClass.enablingSidePanel();
				}

				/**
				 *License not deactivated
				 */
				if (loadingResult != LoadDataResultType.LicenseDeactivated) {

					// Checking license for warnings
					int daysToExpire = 0;
					daysToExpire = Controler
							.calculateDaysToExpireCurrentLicense();

					if (Controler.getApplicationLicenseState() != LicenseState.valid)// invalid
					// license
					{
						thisClass
								.showExpiredLicenseMessage(Controler
										.getApplicationLicenseState(),
										"licencia no valida (incorrecta o fuera de fecha) en el main");
						return;
					}

					if (daysToExpire <= Controler.getApplicationSettings()
							.getDaysToWarning()) // show warning if license is
						// about to expire
						JOptionPane.showMessageDialog(null,
								"Su licencia vence en " + daysToExpire
										+ " días.", "Activación de licencia",
								JOptionPane.WARNING_MESSAGE);

				}
				/**
				 *License deactivated
				 */
				else {
					thisClass.showExpiredLicenseMessage(null,
							"licencia desactivada en el main");
					return;
				}

				/**
				 * The data content from provider has been not loaded
				 */
				if (loadingResult == LoadDataResultType.DataUnloaded) {
					JOptionPane
							.showMessageDialog(
									null,
									"La aplicación no ha cargado los datos necesarios para funcionar correctamente.\n Por favor, realice una actualización.",
									"Cargando datos",
									JOptionPane.WARNING_MESSAGE);
					return;
				}

			}
		});
	}

	private static String convertToMultiline(String text) {
		return "<html>" + text.replaceAll("\n", "<br>") + "</html>";
	}

	public void showExpiredLicenseMessage(LicenseState state, String call) {

		System.out.println("llamado desde: " + call);
		String message = null;
		if (state == null)
			message = convertToMultiline("La licencia para esta aplicación está desactivada.\nPor favor realice una actualización que incluya licencia, o realice una activación.");
		else {
			switch (state) {
			case expired:
				message = convertToMultiline("La licencia de esta aplicación ya ha expirado.\nPor favor realice una actualización que incluya licencia, o realice una activación.");
				break;
			case incorrect:
				message = convertToMultiline("La licencia que tiene la aplicación es incorrecta para la instalación de su máquina.\nPor favor realice una actualización que incluya licencia, o realice una activación.");
				break;
			default:
				break;
			}
		}

		int result = JOptionPane.showOptionDialog(
				null,
				// convertToMultiline("La licencia para esta aplicación no ha sido activada, es incorrecta para esta instalación o ya ha expirado.\nPor favor realice una actualización que incluya licencia, o realice una activación."),
				message, "Licencia desactivada",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE,
				null, new Object[] { "Actualizar", "Activar", "Cancelar" },
				"Activar");

		if (result == -1)// Close window message
			Client.this.setVisible(false);

		if (result == 2)// Cancel option
			Client.this.setVisible(false);
		if (result == 0)// Update option
		{
			Update updateWindow = new Update(Client.this);
			updateWindow.setVisible(true);
		}
		if (result == 1)// Activate option
		{
			LicenseActivation licenceWindow = new LicenseActivation(Client.this);
			licenceWindow.setVisible(true);
		}
	}

	/**
	 * This is the default constructor
	 */
	public Client() {
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
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource(
						"/citmatel/cu/icons/LOGO-EGRAFIP-(PNG)16.png")));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Finanzas al Día. (Cliente 1.0.0)");

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
			jLabel12 = new JLabel();
			jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

				}

				public void mouseEntered(java.awt.event.MouseEvent e) {
					// Event stub
					// mouseEntered()
					jLabel12.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-2-Over_10.jpg")));

				}

				public void mouseExited(java.awt.event.MouseEvent e) {
					jLabel12.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-2-Over_10.jpg")));

					// Event stub
					// mouseExited()
					jLabel12.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-2_10.jpg")));
				}
			});
			jLabel12.setBounds(new Rectangle(695, 91, 323, 65));
			jLabel12.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge-2_10.jpg")));
			jLabel12.setText("");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(668, 91, 27, 65));
			jLabel11.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge-2_09.jpg")));
			jLabel11.setText("");
			jLabel10 = new JLabel();
			jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
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
					jLabel10.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-2-Over_08.jpg")));

				}

				public void mouseExited(java.awt.event.MouseEvent e) {
					jLabel10.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-2-Over_08.jpg")));

					// Event stub
					// mouseExited()
					jLabel10.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-2_08.jpg")));
				}
			});
			jLabel10.setBounds(new Rectangle(542, 91, 126, 65));
			jLabel10.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge-2_08.jpg")));
			jLabel10.setText("");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(514, 91, 28, 65));
			jLabel9.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge-2_07.jpg")));
			jLabel9.setText("");
			jLabel8 = new JLabel();
			jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					Configuration configuration = new Configuration(Client.this);
					configuration.setVisible(true);

					fromJXDatePicker.setEnabled(true);
					fromJXDatePicker.setEnabled(datejCheckBox.isSelected());
					toJXDatePicker.setEnabled(true);
					toJXDatePicker.setEnabled(dateTojCheckBox.isSelected());

				}

				public void mouseEntered(java.awt.event.MouseEvent e) {
					// Event stub
					// mouseEntered()
					jLabel8.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-2-Over_06.jpg")));

				}

				public void mouseExited(java.awt.event.MouseEvent e) {
					jLabel8.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-2-Over_06.jpg")));

					// Event stub
					// mouseExited()
					jLabel8.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-2_06.jpg")));
				}
			});
			jLabel8.setBounds(new Rectangle(383, 91, 131, 65));
			jLabel8.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge-2_06.jpg")));
			jLabel8.setText("");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(356, 91, 27, 65));
			jLabel7.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge-2_05.jpg")));
			jLabel7.setText("");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(197, 91, 159, 65));
			jLabel6.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge-2_04.jpg")));
			jLabel6.setText("");
			jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					LicenseActivation licenseWindow = new LicenseActivation(
							Client.this);
					licenseWindow.setVisible(true);

				}

				public void mouseEntered(java.awt.event.MouseEvent e) {
					// Event stub
					// mouseEntered()
					jLabel6.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-2-Over_04.jpg")));

				}

				public void mouseExited(java.awt.event.MouseEvent e) {
					jLabel6.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-2-Over_04.jpg")));

					// Event stub
					// mouseExited()
					jLabel6.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-2_04.jpg")));
				}
			});
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(171, 91, 26, 65));
			jLabel5.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge-2_03.jpg")));
			jLabel5.setText("");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(0, 91, 171, 65));
			jLabel2.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge-2_02.jpg")));
			jLabel2.setText("");
			jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					Update updateWindow = new Update(Client.this);
					updateWindow.setVisible(true);
					System.out.println("mouseClicked()"); // TODO Auto-generated
					// Event stub
					// mouseClicked()
				}

				public void mouseEntered(java.awt.event.MouseEvent e) {
					// Event stub
					// mouseEntered()
					jLabel2.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-2-Over_02.jpg")));

				}

				public void mouseExited(java.awt.event.MouseEvent e) {
					jLabel2.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-2_02.jpg")));

					// Event stub
					// mouseExited()
					jLabel2.setIcon(new ImageIcon(getClass().getResource(
							"/citmatel/cu/icons/Jorge-2_02.jpg")));
				}
			});
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(0, 0, 1018, 92));
			jLabel.setIcon(new ImageIcon(getClass().getResource(
					"/citmatel/cu/icons/Jorge-2_01.jpg")));
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
		}
		return jContentPane;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
