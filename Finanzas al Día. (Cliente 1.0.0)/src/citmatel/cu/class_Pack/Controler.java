package citmatel.cu.class_Pack;

import java.io.File;
import java.io.IOException;
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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import citmatel.cu.visual_Pack.ProgressBarPanel;

public class Controler {

	// Data files--------
	public static String appDataPath = "EgrafipAppData";
	public static String documentsPath = appDataPath + File.separator +"All_Documents";
	public static String appDataFilePath = appDataPath + File.separator + "All_Data.cfg";
	public static String appSettingsFilePath = appDataPath + File.separator + "All_Settings";
	// -----------------------

	//License state
	private static License applicationLicense;
	private static LicenseState applicationLicenseState;
	public static License getApplicationLicense() {
		return applicationLicense;
	}
	public static LicenseState getApplicationLicenseState() {
		return applicationLicenseState;
	}
	
	//Settings
	private static Settings applicationSettings;
	public static Settings getApplicationSettings() {
		return applicationSettings;
	}

	private static Boolean existAppDataFolder() {
		File dataFile = new File(appDataPath);
		// return dataFile.exists() && dataFile.isDirectory() &&
		// dataFile.isHidden();
		return dataFile.exists() && dataFile.isDirectory();
	}

	private static Boolean existAppDataFile() {
		File dataFile = new File(appDataFilePath);
		// return dataFile.exists() && dataFile.isDirectory() &&
		// dataFile.isHidden();
		return dataFile.exists();
	}

	private static Boolean existAppSettingsFile() {
		File dataFile = new File(appSettingsFilePath);
		// return dataFile.exists() && dataFile.isDirectory() &&
		// dataFile.isHidden();
		return dataFile.exists();
	}

	public static Boolean existDataToShow() {
		if (!existAppDataFolder() || !existAppDataFile())
			return false;

		if (ManagerDoc.getManuals() == null && ManagerDoc.getSections() == null
				&& ManagerDoc.getChapters() == null
				&& ManagerDoc.getDocuments() == null
				&& ManagerDoc.getModifications() == null)
			return false;
		return true;
	}

	/**
	 * Cargar configuración por defecto de la aplicación
	 */
	private static void LoadSettingsByDefault() {
		Settings defaultSettings = new Settings();
		//defaultSettings.setStyleName(UIManager.getSystemLookAndFeelClassName());
		defaultSettings.setStyleName("Sistema");
		defaultSettings.setDaysToWarning(90);
		defaultSettings.setDeleteOldModifications(false);
		defaultSettings.setAskIfDeleteOldModifications(true);	
		applicationSettings = defaultSettings;
	}
		

	/**
	 * Cargar en memoria (Controler) la configuración general de la aplicación.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void LoadSettings() {
		Settings loadedSettings = null;
		try {
			loadedSettings = (Settings)Utils.readObjectFromZipFile(appSettingsFilePath);
		} catch (ClassNotFoundException e) {
			if (existAppSettingsFile())
				new File(appSettingsFilePath).delete();
			LoadSettingsByDefault();
			return;
		} catch (IOException e) {
			if (existAppSettingsFile())
				new File(appSettingsFilePath).delete();
			LoadSettingsByDefault();
			return;
		}
		//if no problem....load existent settings 
		applicationSettings = new Settings();
		//applicationSettings.setStyleName(Configuration.applicationStyles.get(loadedSettings.getStyle()));
		applicationSettings.setStyleName(loadedSettings.getStyle());
		applicationSettings.setDaysToWarning(loadedSettings.getDaysToWarning());
		applicationSettings.setDeleteOldModifications(loadedSettings.getDeleteOldModifications());
		applicationSettings.setAskIfDeleteOldModifications(loadedSettings.getAskIfDeleteOldModifications());
	}

	/**
	 * Salvar en un fichero la configuración especificada para la aplicación
	 * 
	 * @param styleName
	 *            Nombre del estilo seleccionado
	 * @param daysToWarning
	 *            Cantidad de días para lanzar advertencia de expiración de
	 *            licencia
	 * @param deleteOldModifications
	 *            Decidir si borrar modificaciones existentes al realizar una
	 *            importación compacta
	 * @param askIfDeleteOldModifications
	 *            Indica si sacar o no mensaje de confirmación en la ventana de actualización
	 * @throws IOException
	 *             En la escritura del fichero de configuración
	 */
	public static void SaveInSettings(String styleName, int daysToWarning,
			Boolean deleteOldModifications, Boolean askIfDeleteOldModifications) throws IOException {
		
		// saving in controler
		applicationSettings.setStyleName(styleName);
		applicationSettings.setDaysToWarning(daysToWarning);
		applicationSettings.setDeleteOldModifications(deleteOldModifications);
		applicationSettings.setAskIfDeleteOldModifications(askIfDeleteOldModifications);

		// saving in appSettings file
		Utils.writeObjectInZipFormat(applicationSettings, appSettingsFilePath);
	}
	
	/**
	 * Salvar en un fichero la configuración especificada para la aplicación
	
	 * @param deleteOldModifications
	 *            Decidir si borrar modificaciones existentes al realizar una
	 *            importación compacta
	 * @param askIfDeleteOldModifications
	 *            Indica si sacar o no mensaje de confirmación en la ventana de actualización
	 */
	public static void SaveInSettings(Boolean deleteOldModifications, Boolean askIfDeleteOldModifications) throws IOException
	{
		applicationSettings.setDeleteOldModifications(deleteOldModifications);
		applicationSettings.setAskIfDeleteOldModifications(askIfDeleteOldModifications);
		
		// saving in appSettings file
		Utils.writeObjectInZipFormat(applicationSettings, appSettingsFilePath);
	}

	/**
	 * Carga en memoria (ManagerDoc) los datos de la aplicación y valida la licencia de estar activada.
	 * @return Un resultado que indica si la licencia está desactivada (LoadDataResultType.LicenseDeactivated),
	 *  los datos no se encuentran en el fichero appData (LoadDataResultType.DataUnloaded),si los datos se cargaron correctamente en memoria (LoadDataResultType.CorrectDataLoaded) 
	 *  o si el SO no es válido para la aplicación (LoadDataResultType.IncompatibleOS. 
	 * @throws Exception Si ocurre algún problema leyendo el fichero de appData o validando la licencia.
	 */
	public static LoadDataResultType LoadData() throws Exception  {
		// inexistent appData file
		
		System.out.println("exist app data folder: " + existAppDataFolder());
		System.out.println("exist app data file: " + existAppDataFile());
		System.out.println(appDataFilePath);
		System.out.println(appDataPath);
		
		if (!existAppDataFolder() || !existAppDataFile()) {
			Controler.applicationLicenseState = null;
			return LoadDataResultType.LicenseDeactivated;
		}

		// loading data from appData file
		CompactData compactData;
			compactData = (CompactData) Utils
					.ReadObject(appDataFilePath);
		ManagerDoc.setManuals(compactData.getManuals());
		ManagerDoc.setSections(compactData.getSections());
		ManagerDoc.setChapters(compactData.getChapters());
		ManagerDoc.setDocuments(compactData.getDocuments());
		ManagerDoc.setModifications(compactData.getModifications());

		//getting license
		applicationLicense = compactData.getLicense();
		
		//appDataFile existent but null license
		if (applicationLicense == null) {
			applicationLicenseState = null;
			return LoadDataResultType.LicenseDeactivated;
		}
		
		//validating license
		try
		{
			applicationLicenseState = validate(applicationLicense, null, 0, 0);
		}
		catch(Exception e)
		{
			if(e.getMessage()!= null && e.getMessage().equals("imposible to get this Id for this OS"))
					return LoadDataResultType.IncompatibleOS;
			else
				throw e;
		}
		
		// the appDataFile only has license info.
		if (ManagerDoc.getManuals() == null && ManagerDoc.getSections() == null
				&& ManagerDoc.getChapters() == null
				&& ManagerDoc.getDocuments() == null
				&& ManagerDoc.getModifications() == null)
			return LoadDataResultType.DataUnloaded;
			
		return LoadDataResultType.CorrectDataLoaded;
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
			LinkedList<Manual> listM, LinkedList<Section> listS,
			LinkedList<Chapter> listC, LinkedList<Document> listD) {

		DefaultMutableTreeNode father = (DefaultMutableTreeNode) arbol
				.getRoot();

		DefaultMutableTreeNode auxM = null;
		DefaultMutableTreeNode auxS = null;
		DefaultMutableTreeNode auxC = null;
		DefaultMutableTreeNode auxD = null;

		// lleno las listas con los daots en DB

		for (int i = listM.size() - 1; i >= 0; i--) {
			// creando un nodo con cada elemento de la lista de GET_MANUAL
			auxM = new DefaultMutableTreeNode(listM.get(i));
			// inserta el nodo hijo

			arbol.insertNodeInto(auxM, father, 0);

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
			LinkedList<Modification> listModif) {

		DefaultMutableTreeNode root = (DefaultMutableTreeNode) arbol.getRoot();
		DefaultMutableTreeNode aux = null;

		//for (int i = listModif.size() - 1; i >= 0; i--) {

		for (int i = 0; i < listModif.size(); i++){
			// creando un nodo con cada elemento de la lista de Modificaciones

			aux = new DefaultMutableTreeNode(listModif.get(i));

			// inserta el nodo hijo
			arbol.insertNodeInto(aux, root, 0);
		}
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
			if(titleFilter.size()<=0)
				throw new Exception("insignificant query");
			
		}
		if (summary != null) {
			summary = Utils.stripAccentsAndToLowerCase(summary);
			// summaryFilter = new
			// ArrayList<String>(Arrays.asList(title.split(" ")));
			summaryFilter = new ArrayList<String>(Arrays
					.asList(splitWithMultipleDelimeters(summary)));
			summaryFilter.removeAll(stopwords); // removing stopwords
			if(summaryFilter.size()<=0)
				throw new Exception("insignificant query");
		}
		// -------------------------

		// search in all documents
		for (Document document : documents) {
			//if(document.getTitle().contains("113"))
			//{
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
			//}
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
			documentSummary = Utils
					.stripAccentsAndToLowerCase(documentSummary);
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
			"los", "del", "al", "a", "de", "ante", "en", "con", "y", "o", "para", "por");
	private static List<String> separators = Arrays.asList(" ", ",", ";", ".",
			":", "-", "_");

	/**
	 * Importa un fichero de actualización completa hacia la aplicación.
	 * 
	 * @param file
	 *            Nombre del fichero de actualizaación
	 * @throws Exception
	 *             Cuando el fichero de actualización viene sin licencia y no
	 *             existe en el sistema una licencia activada válida. (Exception
	 *             message: license deactivated) Cuando la licencia contenida en
	 *             el fichero de actualización es incorrecta o expirada.
	 *             (Exception messages: importing incorrect license or importing
	 *             expired license) Cuando la licencia actual de la aplicación
	 *             ha expirado en comparación con una fecha de control enviada
	 *             en el fichero de actualización (Exception message: license
	 *             expired)
	 *             
	 * @return Un bool que indica si la actualización incluye activación de
	 *         licencia.
	 */
	public static Boolean importCompleteUpdate(String file,
			ProgressBarPanel pbar) throws Exception {

		System.out.println("start complete update");
		Boolean licenseIncluded = false;
		File appDataFolder = new File(appDataPath);
		if (!appDataFolder.exists())
			appDataFolder.mkdir();
		Utils.decompressFiles(file, appDataPath, pbar, 10, 0);

		// getting data from completeUpdate file
		List<Object> importingData = Utils.readObjectsFromZipFile(appDataPath
				+ File.separator +"generalInfo");
		System.out.println("read general info");
		LinkedList<Manual> importingManuals = ((LinkedList<Manual>) importingData
				.get(0));
		LinkedList<Section> importingSections = ((LinkedList<Section>) importingData
				.get(1));
		LinkedList<Chapter> importingChapters = ((LinkedList<Chapter>) importingData
				.get(2));
		LinkedList<Document> importingDocuments = ((LinkedList<Document>) importingData
				.get(3));
		Date controlDate = (Date) importingData.get(4);
		License importingLicense = (License) importingData.get(5);
		LinkedList<Modification> importingModifications = new LinkedList<Modification>();

		pbar.updateBar(12); // updating progress bar

		// importing data
		CompactData newData = new CompactData();
		newData.setManuals(importingManuals);
		newData.setSections(importingSections);
		newData.setChapters(importingChapters);
		newData.setDocuments(importingDocuments);
		newData.setModifications(importingModifications);
		pbar.updateBar(13); // updating progress bar

		// updateFile includes license, update it in compactData.
		if (importingLicense != null) {
			licenseIncluded = true;
			importLicenseFromUpdateFile(importingLicense, newData);
		} else
			// updateFile without license
		
			checkLicenseActivation(controlDate, newData);
		
		pbar.updateBar(15); // updating progress bar

		// Updtating importing data---------
		Utils.decompressFiles(appDataPath + File.separator + "Documents", documentsPath, pbar,
				70, 15);// decompressing documents...finished 85%.
		Utils.SaveObject(appDataFilePath, newData);// updating data in appData
		// file.
		// updating data in app (ManagerDoc)
		ManagerDoc.setManuals(importingManuals);
		ManagerDoc.setSections(importingSections);
		ManagerDoc.setChapters(importingChapters);
		ManagerDoc.setDocuments(importingDocuments);
		ManagerDoc.setModifications(importingModifications);
		pbar.updateBar(88); // updating progress bar
		// -----------------------

		// deleting temporals
		File temp = new File(appDataPath + File.separator + "generalInfo");
		temp.delete();
		File temp2 = new File(appDataPath + File.separator +"Documents");
		temp2.delete();

		pbar.updateBar(90); // updating progress bar
		return licenseIncluded;
	}

	/**
	 * Comprueba la activación actual de la licencia en la aplicación con la fecha del proveedor
	 * 
	 * @param controlDate
	 *            Fecha con la que comparar la expiración de la licencia.
	 * @throws Exception
	 */
	private static void checkLicenseActivation(Date controlDate,
			CompactData newData) throws Exception {
		
		if (Controler.applicationLicenseState == null)// application license is
			// deactivated and the
			// importing file is
			// without a license
			throw new Exception("license deactivated");
		
		try{
			if(validate(Controler.getApplicationLicense(), null, 0, 0) != LicenseState.valid)
				throw new Exception("license deactivated");
		}
		
		catch(Exception e)
		{
			if(e.getMessage().equals("imposible to get this Id for this OS"))
				throw new Exception("incompatible os");
			else
				throw e;
		}
		
		// controlling current license activation with controlDate...
		if (!isInDateCurrentLicense(controlDate)) {
			Controler.applicationLicense = null;
			Controler.applicationLicenseState = null;
			newData.setLicense(null);
			Utils.SaveObject(appDataFilePath, newData);
			throw new Exception("license expired");
		}

		// maintaining the current license
		newData.setLicense(Controler.applicationLicense);
	}

	/**
	 * Importa la licencia que viene en el fichero de actualización
	 * 
	 * @param importingLicense
	 *            Licencia a importar
	 * @throws Exceptions importingExpiredLicense, importingIncorrectLicense 
	 */
	private static void importLicenseFromUpdateFile(License importingLicense,
			CompactData newData) throws Exception {
	
		LicenseState state = null;
		try
		{
			state = validate(importingLicense, null, 0, 0);
			switch (state) {
			case expired:
				throw new Exception("importing expired license");
			case incorrect:
				throw new Exception("importing incorrect license");
			case valid: {
				Controler.applicationLicense = importingLicense;
				Controler.applicationLicenseState = LicenseState.valid;
				newData.setLicense(importingLicense);
			}
			}
		}
		catch(Exception e){
			if(e.getMessage().equals("imposible to get this Id for this OS"))
				throw new Exception("incompatible os");
			else
				throw e;
		}
	}
	
	/*private static void updateTree(List<Modification> modifications)
	{
		LinkedList<Document> documents  = (LinkedList<Document>)ManagerDoc.getDocuments().clone();
		for (Modification modification : modifications) {
			//eliminación
			if (modification.getAction().equals("retiro")) {
				for (Document d : ManagerDoc.getDocuments()) {
					if(d.getId() == modification.getId_document())
						documents.remove(d);
				}
			}
			//adición
			if (modification.getAction().equals("adicion")) {
				Document newDocument = new Document();
				newDocument.setId(modification.getId_document());
				newDocument.setTitle(modification.getTitle_Documet());
				newDocument.setTitle_alias(modification.getTitleAlias());
				//newDocument.setIntrotext(modification.get);
				//newDocument.setSection_id(rs.getInt("sectionid"));
				//newDocument.setChapteID(rs.getInt("catid"));
				//newDocument.setDate(rs.getDate("created"));
				
			}
		}
		ManagerDoc.setDocuments(documents);
	}*/

	/**
	 * Importa los ficheros de actualización compacta hacia la aplicación
	 * 
	 * @param file
	 *            Nombre del fichero de actualizaación
	 * @throws Exception
	 *             Cuando el fichero de actualización viene sin licencia y no
	 *             existe en el sistema una licencia activada válida(Exception
	 *             message: license deactivated).
	 *             Cuando la licencia contenida en el fichero de actualización es incorrecta o expirada.
	 *             (Exception messages: importing incorrect license or importing
	 *             expired license).
	 *             Cuando la licencia actual de la aplicación ha expirado en comparación con una fecha de control enviada
	 *             en el fichero de actualización (Exception message: license expired)
	 * 
	 * @return Un bool que indica si la actualización incluye activación de
	 *         licencia
	 */
	public static Boolean importCompactUpdate(String file, ProgressBarPanel pbar)
			throws Exception {
		Boolean licenseIncluded = false;
		File appDataFolder = new File(appDataPath);
		if (!appDataFolder.exists())
			appDataFolder.mkdir();
		Utils.decompressFiles(file, appDataPath, pbar, 10, 0);

		// getting data from compact update file
		List<Object> importingData = Utils.readObjectsFromZipFile(appDataPath
				+ File.separator + "generalInfo");
		LinkedList<Manual> importingManuals = ((LinkedList<Manual>) importingData
				.get(0));
		LinkedList<Section> importingSections = ((LinkedList<Section>) importingData
				.get(1));
		LinkedList<Chapter> importingChapters = ((LinkedList<Chapter>) importingData
				.get(2));
		LinkedList<Document> importingDocuments = ((LinkedList<Document>) importingData
				.get(3));
		List<Modification> importingModifications = (List<Modification>) importingData
				.get(4);

		LinkedList<Modification> oldModifications = ManagerDoc
				.getModifications();

		// adding old modifications (because deleteOldModifications is in false)
		if (!Controler.applicationSettings.getDeleteOldModifications()) {
			for (Modification modification : oldModifications) {
				if (!importingModifications.contains(modification))// no
					// repeating
					// same
					// mofification
					importingModifications.add(modification);
			}
		}

		Date controlDate = (Date) importingData.get(5);
		License importingLicense = (License) importingData.get(6);

		CompactData newData = new CompactData();
		if (existAppDataFile())
			newData = (CompactData) Utils.ReadObject(appDataFilePath);
		pbar.updateBar(12); // updating progress bar

		// there are not other data
		if (!existAppDataFile() || appDataFileIsEmpty(newData)) {
			newData.setManuals(new LinkedList<Manual>());
			newData.setSections(new LinkedList<Section>());
			newData.setChapters(new LinkedList<Chapter>());
			newData.setDocuments(new LinkedList<Document>());
		}
		// updating modifications and tree
		LinkedList<Modification> newModifications = new LinkedList<Modification>(
				importingModifications);
		newData.setManuals(importingManuals);
		newData.setSections(importingSections);
		newData.setChapters(importingChapters);
		newData.setDocuments(importingDocuments);
		newData.setModifications(newModifications);
		pbar.updateBar(13); // updating progress bar
		
		

		// updateFile includes license, update it in compactData.
		if (importingLicense != null) {
			licenseIncluded = true;
			importLicenseFromUpdateFile(importingLicense, newData);
		} else
			// updateFile without license
			checkLicenseActivation(controlDate, newData);
		pbar.updateBar(15); // updating progress bar
		
		// managing modifications that remove complete documents
		int i = 0;
		for (Modification modification : importingModifications) {
			if (modification.getAction().equals("retiro")) {
				String associatedDocument = documentsPath + File.separator
						+ modification.getTitleAlias();
				new File(associatedDocument).delete();
			}
			pbar.updateBar(15 + ((i++) * 10 / importingModifications.size())); // updating
			// progress
			// bar
		}
		// finished 25%

		// Compact Updtate---------
		Utils.decompressFiles(appDataPath + File.separator+"Documents", documentsPath, pbar,
				70, 25);// decompressing documents (modifications type: addition
		// and modification)
		// finished 95%
		
		Utils.SaveObject(appDataFilePath, newData);// updating data in app.
		pbar.updateBar(97); // updating progress bar
		// updating data in app (ManagerDoc)
		ManagerDoc.setManuals(importingManuals);
		ManagerDoc.setSections(importingSections);
		ManagerDoc.setChapters(importingChapters);
		ManagerDoc.setDocuments(importingDocuments);
		ManagerDoc.setModifications(newModifications);
		pbar.updateBar(88); // updating progress bar

		// -----------------------

		// deleting temporals
		File temp = new File(appDataPath +File.separator+ "generalInfo");
		temp.delete();
		File temp2 = new File(appDataPath + File.separator + "Documents");
		temp2.delete();

		pbar.updateBar(98); // updating progress bar
		return licenseIncluded;

	}

	private static boolean appDataFileIsEmpty(CompactData data) {
		return (data.getManuals() == null && data.getSections() == null
				&& data.getChapters() == null && data.getDocuments() == null);
	}

	/**
	 * Verifica si la licencia actual es válida para la aplicación respecto a
	 * 'controlDate'.
	 * 
	 * @return Un bool que indica la validez de la licencia.
	 */
	public static Boolean isInDateCurrentLicense(Date controlDate) {
		if (Controler.applicationLicense.getValidDate().before(controlDate))
			return false;
		return true;
	}

	/**
	 * Verifica si la licencia dada es más nueva que la licencia actual de la
	 * aplicación.
	 * 
	 * @param license
	 *            Licencia a verificar
	 * @return Un valor booleano, 'true' si 'license' es más nueva que la
	 *         licencia actual o no existe licencia y 'false' en caso contrario.
	 */
	public static Boolean isNewerThanCurrent(License license) {
		License currentLicense = Controler.applicationLicense;
		if (currentLicense == null)
			return true;
		return (license.getValidDate().after(currentLicense.getValidDate()));// importing
		// a
		// license
		// older
		// than
		// the
		// current
		// one.
	}
	
	

	/**
	 * Indica el estado de la licencia que tiene la aplicación.
	 * @param license La licencia a chequear
	 * @return El estado de la licencia.
	 * @throws Exception Si no se puede coger el identificador de la máquina para validar la licencia porque el SO es incompatible.
	 */
	private static LicenseState validate(License license, ProgressBarPanel pbar, int initialProgress, int total) throws Exception {
		// expired license
		if (license.getValidDate().before(Calendar.getInstance().getTime()))
			return LicenseState.expired;

		// invalid license for this instalation
		
		if (!hasValidInstalationID(license, pbar, initialProgress, total))
			return LicenseState.incorrect;
		
		return LicenseState.valid;
	}

	/**
	 * Indica si la licencia es válida en la máquina que se está intentando activar.
	 * @param license Licencia a analizar
	 * @return El estado de la licencia
	* @throws Exception Si no se puede coger el identificador de la máquina para validar la licencia porque el SO es incompatible.
	 */
	private static Boolean hasValidInstalationID(License license, ProgressBarPanel pbar, int initialProgress, int total) throws Exception {
		String importingLicenseId = license.getInstalationID();
		String correctLicenseId = null;
		
		correctLicenseId = Utils.getIdentifierForLicense(pbar, initialProgress, total-5);
		
		
		String[] importingCodes = importingLicenseId.split("-");
		String[] correctCodes = correctLicenseId.split("-");
		
		for (int i=0; i<importingCodes.length; i++)
			if (importingCodes[i].equals(correctCodes[i]))
				return true;
		
		if(pbar!=null)
			pbar.updateBar(total);
		return false;
		
		//if you want to match all identifiers at the same time...
		//return license.getInstalationID().equals(Utils.getIdentifierForLicense());
			}

	/**
	 * Método para realizar una activación de la licencia de la aplicación.
	 * 
	 * @param licenseFileName
	 *            Nombre del fichero de activación de la licencia.
	 * @throws Exception
	 *             En la escritura sobre el fichero de datos de la aplicación.
	 */
	public static void importLicense(String licenseFileName, ProgressBarPanel pbar) throws Exception {
		if (!existAppDataFolder()) {
			File directory = new File(appDataPath);
			directory.mkdir();
		}

		License importingLicense = (License) Utils.ReadObject(licenseFileName);
		// checking license before activation
		try{
		LicenseState state = validate(importingLicense, pbar, 10, 90);
		switch (state) {
		case expired:
			throw new Exception("importing expired license");
		case incorrect:
			throw new Exception("importing incorrect license");

			// importing a valid license...
		case valid: {
			Controler.applicationLicense = importingLicense;
			Controler.applicationLicenseState = LicenseState.valid;
			CompactData data = null;

			if (existAppDataFile())// updating license in the existent
			// appDataFile
			{
				data = (CompactData) Utils.ReadObject(appDataFilePath);
				data.setLicense(importingLicense);
			} else // creating appDataFile only with license info
			{
				data = new CompactData();
				data.setManuals(null);
				data.setSections(null);
				data.setChapters(null);
				data.setDocuments(null);
				data.setModifications(null);
				data.setLicense(importingLicense);
			}

			if(pbar!=null)
				pbar.updateBar(95);
			Utils.SaveObject(appDataFilePath, data);
			if(pbar!=null)
				pbar.updateBar(100);
		}
		}
		}
		catch(Exception e)
		{
			if(e.getMessage().equals("imposible to get this Id for this OS"))
				throw new Exception("incompatible os");
			else
				throw e;
		}
	}

	/**
	 * Calcula la cantidad de días que quedan para vencer la licencia actual que
	 * tiene la aplicación.
	 * 
	 * @return Cantidad de días que quedan hasta la expiración de la licencia
	 *         actual. Si la licencia ya expiró o es nula, este valor es
	 *         negativo.
	 */
	public static int calculateDaysToExpireCurrentLicense() {
		if (Controler.applicationLicense == null)
			return -1;
		return calculateDaysToExpireLicense(Controler.applicationLicense);
	}

	static int calculateDaysToExpireLicense(License currentLicense) {
		Date currentDate = (Date) Calendar.getInstance().getTime();
		return (int) ((currentLicense.getValidDate().getTime() - currentDate
				.getTime()) / (1000 * 60 * 60 * 24));
	}

}
