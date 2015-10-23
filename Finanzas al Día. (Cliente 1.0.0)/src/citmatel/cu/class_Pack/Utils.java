package citmatel.cu.class_Pack;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.omg.CORBA.portable.OutputStream;

import citmatel.cu.visual_Pack.ProgressBarPanel;

public class Utils {

	/**
	 * Comprime ficheros en formato 'zip'
	 * 
	 * @param files
	 *            Nombre absoluto de los ficheros a comprimir
	 * @param zipFilePath
	 *            Nombre absoluto del fichero donde comprimir
	 * @throws IOException
	 */
	public static void compressFiles(List<String> files, String zipFilePath,
			ProgressBarPanel pbarPanel, int percent) throws IOException {

		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(
				zipFilePath));
		zipOut.setMethod(ZipOutputStream.DEFLATED);
		zipOut.setLevel(9);

		byte[] buf = new byte[2048];
		int numberOfFiles = files.size();

		// compress a first file which contains the number of compressed files
		ZipEntry firstEntry = new ZipEntry("metadata");
		zipOut.putNextEntry(firstEntry);
		String s = String.valueOf(numberOfFiles);
		zipOut.write(s.getBytes());
		zipOut.closeEntry();
		// finished-----------------------------------------

		// for (String fileName: files) {
		for (int i = 0; i < numberOfFiles; i++) {
			String fileName = files.get(i);
			FileInputStream fis = new FileInputStream(fileName);
			ZipEntry ze = new ZipEntry(new File(fileName).getName());
			zipOut.putNextEntry(ze);
			int len;
			while ((len = fis.read(buf)) > 0) {
				zipOut.write(buf, 0, len);
			}
			zipOut.closeEntry();
			fis.close();
			pbarPanel.updateBar(25 + ((i + 1) * percent / numberOfFiles)); // updating
																			// progress
																			// bar

		}
		zipOut.flush();
		zipOut.close();

	}

	/**
	 * Descomprime un único fichero
	 * 
	 * @param zipIn
	 *            la clase ZipInputStream para descomprimir
	 * @param filePath
	 *            el path del fichero
	 * @throws IOException
	 */
	private static void extractFile(ZipInputStream zipIn, String filePath)
			throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(filePath));
		byte[] bytesIn = new byte[4096];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}

	/**
	 * Descomprime un fichero con formato '.zip'
	 * 
	 * @param zipFilePath
	 *            Nombre absoluto del fichero a descomprimir
	 * @param directoryPath
	 *            Nombre absoluto del directorio donde los ficheros comprimidos
	 *            serán estraídos
	 * @throws IOException
	 */
	public static void decompressFiles(String zipFilePath,
			String directoryPath, ProgressBarPanel pbar, int percent,
			int accumulate) throws IOException {
		System.out.println("start decompress");
		File directory = new File(directoryPath);
		if (!directory.exists())
			directory.mkdir();

		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(
				zipFilePath));

		// getting the first file which contains the number of compressed files
		String first = zipIn.getNextEntry().getName();
		byte[] b = new byte[100];
		int count = zipIn.read(b);
		byte[] b2 = new byte[count];
		for (int i = 0; i < b2.length; i++) {
			b2[i] = b[i];
		}
		String s = new String(b2);
		int numberOfFiles = Integer.parseInt(s);
		// finished----------------------------------

		ZipEntry entry = zipIn.getNextEntry();
		int i = 0;
		while (entry != null) {
			String filePath = directoryPath + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				extractFile(zipIn, filePath);
				System.out.println("Primera entrada");
			} else {
				File dir = new File(filePath);
				dir.mkdir();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
			pbar.updateBar(accumulate + ((i + 1) * percent / numberOfFiles)); // updating
																				// progress
																				// bar
			i++;
		}
		zipIn.close();
		System.out.println("finish decompress");
	}

	/**
	 * Serializa un objeto en un fichero con formato 'zip'
	 * 
	 * @param obj
	 *            Objeto a serializar
	 * @param zipFileName
	 *            Nombre absoluto del fichero donde escribir
	 * @throws IOException
	 */
	public static void writeObjectInZipFormat(Serializable obj,
			String zipFileName) throws IOException {
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(
				zipFileName));
		zipOut.putNextEntry(new ZipEntry(zipFileName));
		ObjectOutputStream out = new ObjectOutputStream(zipOut);
		out.writeObject(obj);
		out.flush();
		zipOut.closeEntry();
		out.close();

	}

	/**
	 * Serializa un conjunto de objetos en un mismo fichero con formato 'zip'
	 * 
	 * @param obj
	 *            Objetos a serializar
	 * @param zipFileName
	 *            Nombre absoluto del fichero donde escribir
	 * @throws IOException
	 */
	public static void writeObjectsInZipFormat(List<Object> objects,
			String zipFileName, ProgressBarPanel pBarPanel, int percent)
			throws IOException {

		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(
				zipFileName));
		zipOut.putNextEntry(new ZipEntry(zipFileName));
		ObjectOutputStream out = new ObjectOutputStream(zipOut);

		int numberOfObjects = objects.size();
		for (int i = 0; i < numberOfObjects; i++) {
			out.writeObject(objects.get(i));
			if (pBarPanel != null)
				pBarPanel.updateBar(0 + ((i + 1) * percent / numberOfObjects)); // updating
																				// progress
																				// bar
		}

		zipOut.closeEntry();
		out.flush();
		out.close();
	}

	/**
	 * Deserializa un objeto guardado en un fichero con formato 'zip'
	 * 
	 * @param zipFileName
	 *            Nombre absoluto del fichero de donde leer
	 * @return El objeto cargado
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object readObjectFromZipFile(String zipFileName)
			throws IOException, ClassNotFoundException {
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(
				zipFileName));
		zipIn.getNextEntry();
		ObjectInputStream in = new ObjectInputStream(zipIn);
		Object obj = in.readObject();
		in.close();
		return obj;

	}

	/**
	 * Deserializa un conjunto de objetos guardados en un fichero con formato
	 * 'zip'
	 * 
	 * @param zipFileName
	 *            Nombre absoluto del fichero de donde leer
	 * @return Los objetos cargados
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<Object> readObjectsFromZipFile(String zipFileName)
			throws IOException, ClassNotFoundException {
		List<Object> objects = new ArrayList<Object>();
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(
				zipFileName));
		zipIn.getNextEntry();
		ObjectInputStream inStream = new ObjectInputStream(zipIn);

		Object obj = null;
		while (true) {
			try {
				obj = inStream.readObject();
				// if(obj != null)
				objects.add(obj);
			} catch (EOFException exc) {
				break;
			}
		}

		inStream.close();
		return objects;
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
	 * @throws Exception
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
	 * Metodo que lee un objeto contenido en un fichero
	 * 
	 * @param AddressFile
	 *            direccion url del fichero donde se quiere leer
	 * @return el objeto leido <br>
	 *         ej: url del fichero-> C:\MARCAJE_RX_1.0\CONFIGURACION.agr,
	 *         entonces, <br>
	 *         lee en el fichero CONFIGURACION.agr un objeto
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * */
	public static Object ReadObject(String AddressFile) throws IOException,
			ClassNotFoundException {
		Object obj = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(AddressFile);
			if (fis.getChannel().size() > 0) {
				ObjectInputStream ois = new ObjectInputStream(fis);
				obj = ois.readObject();
				ois.close();
				return obj;
			} else {
				fis.close();
			}
		} catch (IOException e1) {
			throw e1;
		} catch (ClassNotFoundException e2) {
			throw e2;
		} finally {
			if (fis != null)
				fis.close();
		}

		return obj;
	}

	/**
	 * Copia hacia una carpeta los ficheros especificados
	 * 
	 * @param sourceLocation
	 *            Una carpeta con ficheros o un fichero específico
	 * @param targetLocation
	 *            La carpeta destino donde se copiarán los ficheros
	 *            especificados
	 * @throws Exception
	 */
	public static void copyToAFolder(File sourceLocation, File targetLocation)
			throws IOException {
		if (!targetLocation.exists())
			targetLocation.mkdir();
		else {
			if (!targetLocation.isDirectory())
				throw new IOException("La dirección de destino de la copia ("
						+ targetLocation + ") no es válida");
		}

		if (sourceLocation.isDirectory()) {
			String[] filesInSource = sourceLocation.list();
			for (int i = 0; i < filesInSource.length; i++) {
				copyFiles(new File(sourceLocation, filesInSource[i]), new File(
						targetLocation, filesInSource[i]));
			}
		}

		else // only one file
		{
			copyFiles(sourceLocation, new File(targetLocation, sourceLocation
					.getName()));
		}
	}

	private static void copyFiles(File source, File target) throws IOException {
		FileInputStream in = new FileInputStream(source);
		FileOutputStream out = new FileOutputStream(target);

		// Copy the bits from instream to outstream
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
		System.out.println("copying");
	}

	/**
	 * Determina la extensión de un fichero
	 * 
	 * @param f
	 *            Fichero para saber su extensión
	 * @return La extensión del fichero
	 */
	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	/**
	 * Determina un identificador para una máquina basado en los números de
	 * serie del hardware de la misma: para Windows MAC, MB, CPU, HDD para Linux
	 * MAC, MB.
	 * 
	 * @return El identificador determinado para la máquina.
	 * @throws Exception
	 *             Si el sistema actual no es ni Windows ni Linux.
	 */
	public static String getIdentifierForLicense(ProgressBarPanel pbar,
			int initialProgress, int total) throws Exception {
		int progressBySN = (total - 5) / 4;
		String macSerialNumber = SerialNumberGetter.getMACAddress();
		if (pbar != null)
			pbar.updateBar(initialProgress + progressBySN);
		String mbSerialNumber = SerialNumberGetter.getMotherboardSN();
		if (pbar != null)
			pbar.updateBar(initialProgress + 2 * progressBySN);
		String cpuSerialNumber = SerialNumberGetter.getProcessorSN();
		if (pbar != null)
			pbar.updateBar(initialProgress + 3 * progressBySN);
		String hddSerialNumber = SerialNumberGetter.getHDDSN();
		if (pbar != null)
			pbar.updateBar(initialProgress + 4 * progressBySN);

		String id;

		if (macSerialNumber == null && mbSerialNumber == null
				&& cpuSerialNumber == null && hddSerialNumber == null)
			id = getCodeOf(null) + "-" + getCodeOf(null) + "-"
					+ getCodeOf("none") + getCodeOf(null);
		else
			id = getCodeOf(macSerialNumber) + "-" + getCodeOf(mbSerialNumber)
					+ "-" + getCodeOf(cpuSerialNumber) + "-"
					+ getCodeOf(hddSerialNumber);

		if (pbar != null)
			pbar.updateBar(total);
		return id;

	}

	public static Random rnd = new Random();

	public static String getCodeOf(String serialNumber) {
		if (serialNumber == null)
			return getCodeOf(new Integer(rnd.nextInt()).toString());

		int hashCode = Math.abs(serialNumber.hashCode());
		String code = fillUntil4Digits(hashCode % 10000);
		return code;
	}

	public static String fillUntil4Digits(Integer number) {
		String numberAsString = number.toString();
		int digits = numberAsString.length();
		for (int i = 0; i < 4 - digits; i++) {
			numberAsString = '0' + numberAsString;
		}
		return numberAsString;
	}

	/*
	 * public static String getCodeOf(String serialNumber) {
	 * 
	 * int hashCode = Math.abs(serialNumber.hashCode()); String code =
	 * fillUntil3Digits(hashCode%1000); hashCode = hashCode/1000; for (int i =
	 * 0; i < 2; i++) { code += "-"+ fillUntil3Digits(hashCode%1000); hashCode =
	 * hashCode/1000; } return code; }
	 * 
	 * private static String fillUntil3Digits (Integer number) { String
	 * numberAsString = number.toString(); int digits = numberAsString.length();
	 * for (int i = 0; i < 3-digits; i++) { numberAsString = '0' +
	 * numberAsString; } return numberAsString; }
	 */

	/**
	 * Transforma un string dado a letras minúsculas y le quita los acentos
	 * 
	 * @param s
	 *            Texto a transformar
	 * @return
	 */
	public static String stripAccentsAndToLowerCase(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		// s = s.replaceAll("[^\\p{ASCII}]", "");
		s = s.replaceAll("\\p{M}", ""); // for UNICODE
		s = s.toLowerCase();
		return s;
	}

	/**
	 * Separa un texto teniendo en cuenta como delimitadores espacios en blanco
	 * y caracteres especiales.
	 * 
	 * @param text
	 *            Texto a dividir
	 * @return
	 */
	public static String[] splitWithMultipleDelimeters(String text) {
		// split with multiple delimeters (non-word chars)
		String[] parts = text.split("[\\W]");

		// removing white spaces...
		List<String> listOfParts = new ArrayList<String>(Arrays.asList(parts));
		listOfParts.removeAll(Arrays.asList("", null));

		return listOfParts.toArray(new String[listOfParts.size()]);
	}

	/**
	 * Remplaza en un texto dado vocales con tildes 'html' por vocales simples.
	 * 
	 * @param text
	 *            El testo
	 * @return
	 */
	public static String replaceHtmlAccents(String text) {
		text = text.replaceAll("&aacute;", "á");
		text = text.replaceAll("&eacute;", "é");
		text = text.replaceAll("&iacute;", "í");
		text = text.replaceAll("&oacute;", "ó");
		text = text.replaceAll("&uacute;", "ú");
		text = text.replaceAll("&Aacute;", "Á");
		text = text.replaceAll("&Eacute;", "É");
		text = text.replaceAll("&Iacute;", "Í");
		text = text.replaceAll("&Oacute;", "Ó");
		text = text.replaceAll("&Uacute;", "Ú");
		text = text.replaceAll("&ntilde;", "ñ");
		text = text.replaceAll("&nbsp;", "");
		return text;
	}

	private static List<Integer> getIndicesOf(String text, String subString) {
		text = Utils.stripAccentsAndToLowerCase(text);
		subString = Utils.stripAccentsAndToLowerCase(subString);
		int startIndex = 0;
		int subStringLen = subString.toCharArray().length;
		List<Integer> indices = new ArrayList<Integer>();
		int matchIndex = -1;
		while ((matchIndex = text.indexOf(subString, startIndex)) != -1) {
			indices.add(matchIndex);
			startIndex = matchIndex + subStringLen;
		}
		return indices;
	}

	private static List<Integer> getBoldPositions(String text,
			List<String> query) {
		List<Integer> positions = new ArrayList<Integer>();
		for (String queryWord : query) {
			List<Integer> indicesInTitle = getIndicesOf(text, queryWord); // indices
																			// in
																			// title
																			// that
																			// match
																			// with
																			// queryWord
			for (Integer indexInTitle : indicesInTitle) {
				for (int i = 0; i < queryWord.toCharArray().length; i++)
					positions.add(indexInTitle + i);
			}

		}

		return positions;
	}

	private static String setBoldFontInSpecificPositions(String text,
			List<Integer> positions) {
		// String result = "<html>";
		String result = "";
		for (int i = 0; i < text.length(); i++) {
			char currentChar = text.charAt(i);
			if (positions.contains(i))// this position in title should be in
										// bold
				result += "<b>" + currentChar + "</b>";
			else
				result += currentChar;
		}
		// return result + "</html>";
		return result;
	}

	/**
	 * Devuelve un string que tiene con fuente "bold" en "html" los "substrings"
	 * del título del documento que coinciden con las palabras de la consulta.
	 * 
	 * @param d
	 *            Documento en cuyo título se realiza la búsqueda.
	 * @param query
	 *            Una lista con las palabras a buscar.
	 * @return
	 */
	public static String getTitleInBoldOf(Document d, List<String> query) {
		String title = d.getTitle();
		List<Integer> positions = getBoldPositions(title, query);
		return setBoldFontInSpecificPositions(title, positions);
	}

	/**
	 * Devuelve un string que tiene con fuente "bold" en "html" los "substrings"
	 * del resumen del documento que coinciden con las palabras de la consulta.
	 * 
	 * @param d
	 *            Documento en cuyo resumen se realiza la búsqueda.
	 * @param query
	 *            Una lista con las palabras a buscar.
	 * @return
	 */
	public static String getSummaryInBoldOf(Document d, List<String> query) {
		String summary = d.getIntrotext();
		summary = Utils.replaceHtmlAccents(summary);
		List<Integer> positions = getBoldPositions(summary, query);
		return setBoldFontInSpecificPositions(summary, positions);
	}

	/**
	 * Copia el texto especificado para el portapapeles
	 * 
	 * @param text
	 *            Texto a copiar
	 */
	public static void copyToClipboard(String text) {
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);
	}

	/**
	 * Obtiene el texto que se encuentra en el portapapeles
	 * 
	 * @return Texto
	 * @throws IOException
	 * @throws UnsupportedFlavorException
	 * @throws HeadlessException
	 */
	public static String getFromClipboard() throws HeadlessException,
			UnsupportedFlavorException, IOException {
		String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard()
				.getData(DataFlavor.stringFlavor);
		return data;
	}

}
