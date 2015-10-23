package citmatel.cu.class_Pack;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Util;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import citmatel.cu.DBConection.DB_Conection;
import citmatel.cu.DBConection.DB_Service;
import citmatel.cu.visual_Pack.CompactUpdate;
import citmatel.cu.visual_Pack.CompleteUpdate;
import citmatel.cu.visual_Pack.ProgressBarPanel;

public class Controler {
	private static ManagerSettings settings;

	public static ManagerSettings getSettings() {
		return settings;
	}

	public static void setSettings(ManagerSettings settings) {
		Controler.settings = settings;
	}

	/**
	 * Metodo que guarda un objeto dentro de un fichero, indicando la url del
	 * fichero
	 * 
	 * @param AddressFile
	 *            direccion url destino del objeto
	 * @param object
	 *            objeto a persistir <br>
	 *            ej: url del fichero-> C:\MARCAJE_RX_1.0\CONFIGURACION.agr, <br>
	 *            objeto del tipo-> clases.persistente.Configuracion, entonces, <br>
	 *            guarda en el fichero CONFIGURACION.agr el objeto de tipo
	 *            clases.persistente.Configuracion
	 * @see java.io.FileNotFoundException
	 * @see java.io.IOException
	 * */
	public static void SaveObject(String AddressFile, Object object)
			throws Exception {

		if (object != null) {
			CreateFile(AddressFile);
			FileOutputStream fos = new FileOutputStream(AddressFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			oos.close();
		}
	}

	/**
	 * Metodo que lee un objeto contenido en un fichero
	 * 
	 * @param AddressFile
	 *            direccion url del fichero donde se quiere leer
	 * @return el objeto leido <br>
	 *         ej: url del fichero-> C:\MARCAJE_RX_1.0\CONFIGURACION.agr,
	 *         entonces, <br>
	 *         lee en el fichero CONFIGURACION.agr un objeto
	 * @see java.io.FileNotFoundException
	 * @see java.io.IOException
	 * @see ClassNotFoundException
	 * */
	public static Object ReadObject(String AddressFile) throws Exception {
		Object obj = null;
		FileInputStream fis = new FileInputStream(AddressFile);
		if (fis.getChannel().size() > 0) {
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = ois.readObject();
			ois.close();
			return obj;
		} else {
			fis.close();
		}
		return obj;
	}

	/**
	 * Metodo que crea un fichero con un nombre y extension, en la direccion
	 * especificada
	 * 
	 * @param AddressFile
	 *            direccion url destino del objeto <br>
	 *            ej: C:\MARCAJE_RX_1.0\CONFIGURACION.agr, crea un fichero
	 *            nombrado CONFIGURACION, con extension .agr, dentro de
	 *            C:\MARCAJE_RX_1.0\
	 * */
	public static void CreateFile(String AddressFile) throws Exception {
		File f = new File(AddressFile);
		if (!f.exists()) {
			f.createNewFile();
		}
	}

	/**
	 * Método que crea una estructura en forma de arbol con los manuales
	 * secciones y documentos de la BD
	 * 
	 * @param arbol
	 * @param phather
	 * @throws SQLException
	 */
	public static void LoadDocuments(DefaultTreeModel arbol,
			DefaultMutableTreeNode phather, LinkedList<Manual> listM,
			LinkedList<Section> listS, LinkedList<Chapter> listC,
			LinkedList<Document> listD) throws SQLException {

		DefaultMutableTreeNode auxM = null;
		DefaultMutableTreeNode auxS = null;
		DefaultMutableTreeNode auxC = null;
		DefaultMutableTreeNode auxD = null;

		// lleno las listas con los daots en DB

		for (int i = listM.size() - 1; i >= 0; i--) {

			// creando un nodo con cada elemento de la lista de GET_MANUAL
			auxM = new DefaultMutableTreeNode(listM.get(i));
			// inserta el nodo hijo

			arbol.insertNodeInto(auxM, phather, 0);

			for (int j = listS.size() - 1; j >= 0; j--) {
				if (listS.get(j).getId_manual() == listM.get(i).getId_manual()) {

					auxS = new DefaultMutableTreeNode(listS.get(j));

					arbol.insertNodeInto(auxS, auxM, 0);

					for (int l = listC.size() - 1; l >= 0; l--) {
						if (listC.get(l).getIdSection().equals(
								String.valueOf(listS.get(j).getId_section()))) {

							auxC = new DefaultMutableTreeNode(listC.get(l));
							arbol.insertNodeInto(auxC, auxS, 0);

							for (int k = listD.size() - 1; k >= 0; k--) {

								if (listD.get(k).getChapteID() == listC.get(l)
										.getId()) {

									auxD = new DefaultMutableTreeNode(listD
											.get(k));
									arbol.insertNodeInto(auxD, auxC, 0);

									// DefaultTreeCellRenderer render =
									// (DefaultTreeCellRenderer) tree
									// .getCellRenderer();
									// render
									// .setLeafIcon("C:/Users/jorgem/Desktop/Old Icons/tag.jpeg");

								}
							}

						}
					}
				}
			}
		}
	}

	/**
	 * Método que crea una estructura en forma de arbol con las modificaciones
	 * de la BD
	 * 
	 * @param arbol
	 * @param padre
	 * @throws SQLException
	 */
	public static void LoadModificaction(DefaultTreeModel arbol,

	DefaultMutableTreeNode root, LinkedList<Modification> listModif)
			throws SQLException {

		DefaultMutableTreeNode aux = null;

		for (int i = 0; i < listModif.size(); i++) {

			// creando un nodo con cada elemento de la lista de Modificaciones

			aux = new DefaultMutableTreeNode(listModif.get(i));

			// inserta el nodo hijo
			arbol.insertNodeInto(aux, root, 0);
		}
	}

	private static List<String> getFileNameOfAllDocs() {
		List<String> fileNames = new ArrayList<String>();
		for (Document d : ManagerDoc.getDocuments()) {
			fileNames.add(d.getTitle_alias());
		}

		return fileNames;
	}

	public static List<String> exportCompleteUpdate(String zipFileName,
			License license, ProgressBarPanel pBarPanel) throws IOException {
		List<Object> info = new ArrayList<Object>();

		// saving in a temporal general info
		LinkedList<Manual> manuals = ManagerDoc.getManuals();
		LinkedList<Section> sections = ManagerDoc.getSections();
		LinkedList<Chapter> chapters = ManagerDoc.getChapters();
		LinkedList<Document> documents = ManagerDoc.getDocuments();
		info.add(manuals);
		info.add(sections);
		info.add(chapters);
		info.add(documents);
		// current date for license control
		info.add(Calendar.getInstance().getTime());
		// license info (null if not includes license)
		info.add(license);
		Utils.writeObjectsInZipFormat(info, "generalInfo", pBarPanel, 0, 20);
		// finished 20%

		// getting documents to update
		List<String> documentsToUpdate = new ArrayList<String>();
		String documentsPath = ManagerSettings.getPathDocument();
		List<String> nonexistentDocs = new ArrayList<String>();
		String completeDocName;
		List<String> docsToCopy = getFileNameOfAllDocs();
		int countOfDocs = getFileNameOfAllDocs().size();

		for (int i = 0; i < countOfDocs; i++) {
			String docName = docsToCopy.get(i);
			if (!docName.equals("")) {
				completeDocName = documentsPath + File.separator + docName;
				if (!(new File(completeDocName).exists()))

					nonexistentDocs.add(docName);
				else {
					if (!documentsToUpdate.contains(completeDocName))
						documentsToUpdate.add(completeDocName);
				}
			}

			pBarPanel.updateBar(20 + ((i + 1) * 5 / countOfDocs));
		}
		// finished 25%

		// compressing documents
		Utils.compressFiles(documentsToUpdate, "Documents", pBarPanel, 25, 70);
		// finished 95%

		// getting files to compress
		List<String> updateFiles = new ArrayList<String>();
		updateFiles.add("generalInfo");
		updateFiles.add("Documents");

		// getting update file
		Utils.compressFiles(updateFiles, zipFileName, pBarPanel, 95, 3);
		// finished 98%

		// deleting temporal files
		File temp = new File("generalInfo");
		File temp2 = new File("Documents");
		temp.delete();
		temp2.delete();
		pBarPanel.setValue(100);
		// finished 100%

		return nonexistentDocs;
	}

	private static List<Modification> getModifications(Date fromDate,
			Date toDate) {
		List<Modification> filterModifications = new ArrayList<Modification>();
		List<Modification> allModifications = new ArrayList<Modification>(
				ManagerDoc.getModifications());

		// modifications without restrictions
		if (fromDate == null && toDate == null)
			return allModifications;

		// modifications between two dates
		if (fromDate != null && toDate != null) {
			for (Modification mod : allModifications) {
				Date modDate = mod.getDate();
				if (modDate.equals(fromDate) || modDate.equals(toDate)
						|| (modDate.after(fromDate) && modDate.before(toDate)))
					filterModifications.add(mod);
			}
		}

		// all modifications since a date
		if (toDate == null && fromDate != null) {
			for (Modification mod : allModifications) {
				Date modDate = mod.getDate();
				if (modDate.equals(fromDate) || modDate.after(fromDate))
					filterModifications.add(mod);
			}
		}

		// all modifications until a date
		if (fromDate == null && toDate != null) {
			for (Modification mod : allModifications) {
				Date modDate = mod.getDate();
				if (modDate.before(toDate) || modDate.equals(toDate))
					filterModifications.add(mod);
			}
		}

		return filterModifications;

	}

	public static CompactUpdateInfoResult exportCompactUpdate(
			String zipFileName, License license, Date fromDate, Date toDate,
			ProgressBarPanel pbar) throws IOException {
		List<Modification> modifications = getModifications(fromDate, toDate); // getting
		// modifications
		// info

		// there are no modifications between the specified dates to make an
		// update.
		if (modifications.size() == 0)
			return new CompactUpdateInfoResult(0, null, null);

		// getting errors and removing bad modifications
		List<String> nonexistentDocs = new ArrayList<String>(); // the file name
		// of documents
		List<String> nonInBDDocs = new ArrayList<String>(); // the modification
		// id
		List<String> documentsToUpdate = new ArrayList<String>(); // the
		// complete
		// path

		String associatedDocument;
		String completeDocName;
		String documentsPath = ManagerSettings.getPathDocument();

		List<Modification> modificationToSend = new LinkedList<Modification>();
		
		// copying to documentsToUpdate, the documents associated to
		// modifications
		for (int i = 0; i < modifications.size(); i++) {
			Modification mod = modifications.get(i);
			associatedDocument = mod.getTitleAlias();

			// managing especial modifications
			if (!mod.getAction().equals("correccion"))
				modificationToSend.add(mod);

			// managing modifications that remove complete documents
			if (mod.getAction().equals("retiro"))
				continue;// do not copy

			if (associatedDocument == null) // the doc do not exist in BD
			{
				nonInBDDocs.add(mod.getId_modification());
				// modifications.remove(mod);
			} else // the doc is in the BD
			{
				if (!associatedDocument.equals("")) {
					completeDocName = documentsPath + File.separator
							+ associatedDocument;
					if (new File(completeDocName).exists()) {

						if (!documentsToUpdate.contains(completeDocName))
							documentsToUpdate.add(completeDocName);
					} else // the doc nonexist in documentsPath
					{
						nonexistentDocs.add(associatedDocument);
						// modifications.remove(mod);
					}
				}
			}
		}
		
		modifications = modificationToSend;

		// saving in a temporal general info
		List<Object> info = new ArrayList<Object>();
		info.add(ManagerDoc.getManuals());
		info.add(ManagerDoc.getSections());
		info.add(ManagerDoc.getChapters());
		info.add(ManagerDoc.getDocuments());
		info.add(modifications);
		// current date for license control
		info.add(Calendar.getInstance().getTime());
		// license info (null if not includes license)
		info.add(license);
		Utils.writeObjectsInZipFormat(info, "generalInfo", pbar, 0, 15);
		// finished 15%

		// compressing documents to be updated
		Utils.compressFiles(documentsToUpdate, "Documents", pbar, 15, 75);
		// finished 90%

		// getting files to compress
		List<String> updateFiles = new ArrayList<String>();
		updateFiles.add("generalInfo");
		updateFiles.add("Documents");

		// getting update zip file
		Utils.compressFiles(updateFiles, zipFileName, pbar, 90, 8);
		// finished 98%

		// deleting temporal files
		File temp = new File("generalInfo");
		File temp2 = new File("Documents");
		temp.delete();
		temp2.delete();
		pbar.setValue(100);
		// finished 100%

		return new CompactUpdateInfoResult(modifications.size(),
				nonexistentDocs, nonInBDDocs);
	}

	public static Boolean existDataToShow() {
		if (!new File("All_Settings.cfg").exists())
			return false;

		if (ManagerDoc.getManuals() == null && ManagerDoc.getSections() == null
				&& ManagerDoc.getChapters() == null
				&& ManagerDoc.getDocuments() == null
				&& ManagerDoc.getModifications() == null)
			return false;
		return true;
	}

	public static void LoadDataBase(ProgressBarPanel pbar, int percent)
			throws SQLException {

		// calculating percents of progress for the pbar
		int[] counts = new int[] { 
				DB_Service.getNumberOfManuals(),
				DB_Service.getNumberOfSections(),
				DB_Service.getNumberOfChapters(),
				DB_Service.getNumberOfModifications(),
				DB_Service.getNumberOfDocuments() };
		pbar.setValue(5);

		int total = 0;
		for (int i = 0; i < counts.length; i++) {
			total += counts[i];
		}
		int[] percents = new int[5];
		for (int i = 0; i < 4; i++) {
			percents[i] = Math.max(3, counts[i] * 100 / total);
		}
		percents[4] = percent
				- (percents[0] + percents[1] + percents[2] + percents[3] + 5);
		// int p = percent / 5;

		// loading data from data base
		int pbarCurrentValue = 5;
		ManagerDoc.setManuals(DB_Service.List_Manual(pbar, percents[0],
				pbarCurrentValue, counts[0]));
		ManagerDoc.setSections(DB_Service.List_Sections(pbar, percents[1],
				pbarCurrentValue += percents[0], counts[1]));
		ManagerDoc.setChapters(DB_Service.List_Chapter(pbar, percents[2],
				pbarCurrentValue += percents[1], counts[2]));
		ManagerDoc.setModifications(DB_Service.List_Modifications(pbar,
				percents[3], pbarCurrentValue += percents[2], counts[3]));
		ManagerDoc.setDocuments(DB_Service.List_Document(pbar, percents[4],
				pbarCurrentValue += percents[3], counts[4]));
	}

	/**
	 * Busca todos los documentos que cumplan con determinado criterio de
	 * búsqueda.
	 * 
	 * @param title
	 *            Texto a buscar en el título del documento (null si no se
	 *            quiere tener en cuenta este criterio)
	 * @param summary
	 *            Texto a buscar en el resumen del documento (null si no se
	 *            quiere tener en cuenta este criterio)
	 * @param fromDate
	 *            Fecha inicial para filtrar el documento (null si no se quiere
	 *            tener en cuenta este criterio)
	 * @param toDate
	 *            Fecha final para filtrar el documento (null si no se quiere
	 *            tener en cuenta este criterio)
	 * @return
	 * @throws Exception
	 */
	public static List<Document> findDocumentsFilteredBy(String title,
			String summary, Date fromDate, Date toDate) throws Exception {

		// documents to Search
		List<Document> documents = ManagerDoc.getDocuments();
		final Map<Document, Relevance> distanceOfFoundDocumentsToSearch = new LinkedHashMap<Document, Relevance>();
		List<Document> foundDocuments = new ArrayList<Document>();

		// get filters info--------
		List<String> titleFilter = null;
		List<String> summaryFilter = null;
		if (title != null) {
			title = Utils.stripAccentsAndToLowerCase(title);
			// titleFilter = new
			// ArrayList<String>(Arrays.asList(title.split(" ")));
			titleFilter = new ArrayList<String>(Arrays
					.asList(splitWithMultipleDelimeters(title)));
			titleFilter.removeAll(stopwords); // removing stopwords
			if (titleFilter.size() <= 0)
				throw new Exception("insignificant query");

		}
		if (summary != null) {
			summary = Utils.stripAccentsAndToLowerCase(summary);
			// summaryFilter = new
			// ArrayList<String>(Arrays.asList(title.split(" ")));
			summaryFilter = new ArrayList<String>(Arrays
					.asList(splitWithMultipleDelimeters(summary)));
			summaryFilter.removeAll(stopwords); // removing stopwords
			if (summaryFilter.size() <= 0)
				throw new Exception("insignificant query");
		}
		// -------------------------

		// search in all documents
		for (Document document : documents) {
			// if(document.getTitle().contains("Madera,"))
			// {
			Relevance relevanceInTitleSearch = title != null ? getRelevanceForTheSearch(
					document, "Title", titleFilter)
					: new Relevance(0, 0, 0);
			Relevance relevanceInSummarySearch = summary != null ? getRelevanceForTheSearch(
					document, "Summary", summaryFilter)
					: new Relevance(0, 0, 0);

			if (
			// filter by title
			((title == null) || (relevanceInTitleSearch.isValueRelevant()))
					&&
					// filter by summary
					((summary == null) || (relevanceInSummarySearch
							.isValueRelevant()))
					&&
					// filter by date
					((fromDate == null) || (fromDate != null && fulfillsWithFromDate(
							document, fromDate)))
					&& ((toDate == null) || (toDate != null && fulfillsWithToDate(
							document, toDate)))) {
				distanceOfFoundDocumentsToSearch.put(document, new Relevance(
						relevanceInTitleSearch.getVariety()
								+ relevanceInSummarySearch.getVariety(),
						relevanceInTitleSearch.getMatches()
								+ relevanceInSummarySearch.getMatches(),
						relevanceInTitleSearch.getContained()
								+ relevanceInSummarySearch.getContained()));
				foundDocuments.add(document);
			}
			// }
		}

		// organize search
		Collections.sort(foundDocuments, new Comparator<Document>() {
			@Override
			public int compare(Document d1, Document d2) {
				return distanceOfFoundDocumentsToSearch.get(d2).compareTo(
						distanceOfFoundDocumentsToSearch.get(d1));
			}
		});

		return foundDocuments;
	}

	/**
	 * Separa un texto teniendo en cuenta como delimitadores espacios en blanco
	 * y caracteres especiales.
	 * 
	 * @param text
	 *            Texto a dividir
	 * @return
	 */
	private static String[] splitWithMultipleDelimeters(String text) {
		// split with multiple delimeters (non-word chars)
		String[] parts = text.split("[\\W]");

		// removing white spaces...
		List<String> listOfParts = new ArrayList<String>(Arrays.asList(parts));
		listOfParts.removeAll(Arrays.asList("", null));

		return listOfParts.toArray(new String[listOfParts.size()]);
	}

	/**
	 * Calcula la relevancia de un documento dado para la búsqueda reralizada
	 * 
	 * @param d
	 *            Documento a procesar
	 * @param searchIn
	 *            Criterio de búsqueda (Se puede especificar: Title o Summary)
	 * @param filter
	 *            Texto para filtrar el documento en dependencia del criterio de
	 *            búsqueda establecido
	 * @return
	 */
	private static Relevance getRelevanceForTheSearch(Document d,
			String searchIn, List<String> filter) {
		String documentTitle = null;
		String documentSummary = null;
		String[] toSearchIn = null;

		if (searchIn == "Title") {
			documentTitle = Utils.stripAccentsAndToLowerCase(d.getTitle());
			toSearchIn = splitWithMultipleDelimeters(documentTitle);
		}

		if (searchIn == "Summary") {
			documentSummary = d.getIntrotext();
			documentSummary = Utils.replaceHtmlAccents(documentSummary);
			documentSummary = Utils.stripAccentsAndToLowerCase(documentSummary);
			toSearchIn = splitWithMultipleDelimeters(documentSummary);
		}

		int variety = 0; // count of words in search that appears in filter.
		int exactMatches = 0; // count of words in title that match exactly with
		// any word in filter.
		int containMatches = 0; // count of words in title that contains any
		// word in filter.

		for (String filterWord : filter) {
			Boolean found = false;
			for (String word : toSearchIn) {
				if (word.equals(filterWord)) {
					found = true;
					exactMatches++;
				} else {
					if (word.contains(filterWord))
						containMatches++;
				}
			}
			if (found)
				variety++;
		}
		return new Relevance(variety, exactMatches, containMatches);
	}

	/**
	 * Determina si la fecha de creación de un documento es posterior a una
	 * fecha determinada
	 * 
	 * @param d
	 *            Documento a analizar
	 * @param fromDate
	 *            Fecha inicial
	 * @return
	 */
	private static Boolean fulfillsWithFromDate(Document d, Date fromDate) {
		return d.getDate().after(fromDate);
	}

	/**
	 * Determina si la fecha de creación de un documento es anterior a una fecha
	 * determinada
	 * 
	 * @param d
	 *            Documento a analizar
	 * @param toDate
	 *            Fecha final
	 * @return
	 */
	private static Boolean fulfillsWithToDate(Document d, Date toDate) {
		return d.getDate().before(toDate);
	}

	private static List<String> stopwords = Arrays.asList("la", "el", "las",
			"los", "del", "al", "a", "de", "ante", "en", "con", "y", "para",
			"por");
	private static List<String> separators = Arrays.asList(" ", ",", ";", ".",
			":", "-", "_");

}
