package citmatel.cu.DBConection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import citmatel.cu.class_Pack.Chapter;
import citmatel.cu.class_Pack.Document;
import citmatel.cu.class_Pack.Manual;
import citmatel.cu.class_Pack.Modification;
import citmatel.cu.class_Pack.Section;
import citmatel.cu.visual_Pack.ProgressBarPanel;

import com.mysql.jdbc.ResultSet;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

public class DB_Service {

	public DB_Service() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Metodo que obtiene los listados de manuales en BD
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static LinkedList<Manual> List_Manual() throws SQLException {

		LinkedList<Manual> list_M = new LinkedList<Manual>();

		// Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// try {
		// conn = DB_Conection.GetConnection();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			pstmt = DB_Conection.GetConnection().prepareStatement(
					DB_Query.GET_MANUAL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = (ResultSet) pstmt.executeQuery();

		Manual manual = null;
		while (rs.next()) {

			manual = new Manual();
			manual.setId_manual(rs.getInt("id_manual"));
			manual.setDirectorio(rs.getString("directorio"));
			manual.setNombre(rs.getString("nombre"));

			list_M.add(manual);
		}
		rs.close();

		return list_M;

	}

	/**
	 * Metodo que obtiene los listados de manuales en BD
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static LinkedList<Manual> List_Manual(ProgressBarPanel pbar,
			int percent, int previousValue, int numberOfManuals)
			throws SQLException {

		LinkedList<Manual> list_M = new LinkedList<Manual>();

		// Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// try {
		// conn = DB_Conection.GetConnection();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			pstmt = DB_Conection.GetConnection().prepareStatement(
					DB_Query.GET_MANUAL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = (ResultSet) pstmt.executeQuery();

		Manual manual = null;
		int index = 0;
		while (rs.next()) {

			manual = new Manual();
			manual.setId_manual(rs.getInt("id_manual"));
			manual.setDirectorio(rs.getString("directorio"));
			manual.setNombre(rs.getString("nombre"));

			list_M.add(manual);

			// updating progress bar
			index = index + 1;
			pbar.updateBar(previousValue + percent * index / numberOfManuals);

		}
		System.out.println("Cantidad de manuales:" + numberOfManuals);
		// pbar.updateBar(100);
		rs.close();
		// conn.close();
		return list_M;

	}

	/**
	 * Metodo que obtiene los listados de secciones en BD
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static LinkedList<Section> List_Sections() throws SQLException {

		LinkedList<Section> list_S = new LinkedList<Section>();

		// Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// try {
		// conn = DB_Conection.GetConnection();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			pstmt = DB_Conection.GetConnection().prepareStatement(
					DB_Query.GET_SECTION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = (ResultSet) pstmt.executeQuery();

		Section sections = null;
		while (rs.next()) {

			sections = new Section();
			sections.setId_section(rs.getInt("id"));
			sections.setId_manual(rs.getInt("id_manual"));
			sections.setName(rs.getString("name"));
			sections.setTitle(rs.getString("title"));

			list_S.add(sections);
		}
		rs.close();
		return list_S;
	}

	/**
	 * Metodo que obtiene los listados de secciones en BD
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static LinkedList<Section> List_Sections(ProgressBarPanel pbar,
			int percent, int previousValue, int numberOfSections)
			throws SQLException {

		LinkedList<Section> list_S = new LinkedList<Section>();

		// Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// try {
		// conn = DB_Conection.GetConnection();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			pstmt = DB_Conection.GetConnection().prepareStatement(
					DB_Query.GET_SECTION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = (ResultSet) pstmt.executeQuery();

		Section sections = null;
		int index = 0;
		while (rs.next()) {

			sections = new Section();
			sections.setId_section(rs.getInt("id"));
			sections.setId_manual(rs.getInt("id_manual"));
			sections.setName(rs.getString("name"));
			sections.setTitle(rs.getString("title"));

			list_S.add(sections);

			index += 1;
			pbar.updateBar(previousValue + percent * index / numberOfSections);
		}
		rs.close();
		return list_S;
	}

	/**
	 * Metodo que obtiene los listados de capítulos en BD
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static LinkedList<Chapter> List_Chapter() throws SQLException {

		LinkedList<Chapter> list_C = new LinkedList<Chapter>();

		// Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// try {
		// conn = DB_Conection.GetConnection();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			pstmt = DB_Conection.GetConnection().prepareStatement(
					DB_Query.GET_CHAPTER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = (ResultSet) pstmt.executeQuery();

		Chapter chapter = null;
		while (rs.next()) {

			chapter = new Chapter();
			chapter.setId(rs.getInt("id"));
			chapter.setName(rs.getString("name"));
			chapter.setIdSection(rs.getString("section"));

			// System.out.println("ID CHAPTER" + chapter.getId());
			// System.out.println("ID SECTION" + chapter.getIdSection());

			list_C.add(chapter);
		}
		rs.close();
		return list_C;
	}

	/**
	 * Metodo que obtiene los listados de capítulos en BD
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static LinkedList<Chapter> List_Chapter(ProgressBarPanel pbar,
			int percent, int previousValue, int numberOfChapters)
			throws SQLException {

		LinkedList<Chapter> list_C = new LinkedList<Chapter>();

		// Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// try {
		// conn = DB_Conection.GetConnection();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			pstmt = DB_Conection.GetConnection().prepareStatement(
					DB_Query.GET_CHAPTER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = (ResultSet) pstmt.executeQuery();

		int index = 0;
		Chapter chapter = null;
		while (rs.next()) {

			chapter = new Chapter();
			chapter.setId(rs.getInt("id"));
			chapter.setName(rs.getString("name"));
			chapter.setIdSection(rs.getString("section"));

			list_C.add(chapter);
			index += 1;
			pbar.updateBar(previousValue + index * percent / numberOfChapters);
		}
		rs.close();
		return list_C;
	}

	/**
	 * Metodo que obtiene los listados de documentos en BD
	 * 
	 * @return
	 * @throws SQLException
	 */
	// public static LinkedList<Document> List_Document() throws SQLException {
	//
	// LinkedList<Document> list_D = new LinkedList<Document>();
	//
	// // Connection conn = null;
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	//
	// try {
	// pstmt = DB_Conection.GetConnection().prepareStatement(
	// DB_Query.GET_DOCUMENT);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// rs = (ResultSet) pstmt.executeQuery();
	//
	// Document document = null;
	// while (rs.next()) {
	//
	// document = new Document();
	// document.setId(rs.getInt("id"));
	// document.setTitle(rs.getString("title"));
	// document.setTitle_alias(rs.getString("title_alias"));
	// document.setIntrotext(rs.getString("introtext"));
	// document.setSection_id(rs.getInt("sectionid"));
	// document.setChapteID(rs.getInt("catid"));
	// document.setDate(rs.getDate("created"));
	//
	// if (document.getId() == 1 || document.getId() == 21
	// || document.getId() == 51 || document.getId() == 43
	// || document.getId() == 184 || document.getId() == 44) {
	//
	// System.out.println(document.getId());
	//
	// } else {
	// list_D.add(document);
	// }
	// }
	// rs.close();
	// return list_D;
	// }
	/**
	 * Metodo que obtiene los listados de documentos en BD
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static LinkedList<Document> List_Document(ProgressBarPanel pbar,
			int percent, int previousValue, int numberOfDocuments)
			throws SQLException {

		LinkedList<Document> list_D = new LinkedList<Document>();

		// Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// try {
		// conn = DB_Conection.GetConnection();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			pstmt = DB_Conection.GetConnection().prepareStatement(
					DB_Query.GET_DOCUMENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = (ResultSet) pstmt.executeQuery();

		Document document = null;
		int index = 0;
		while (rs.next()) {

			document = new Document();
			document.setId(rs.getInt("id"));
			document.setTitle(rs.getString("title"));
			document.setTitle_alias(rs.getString("title_alias"));
			document.setIntrotext(rs.getString("introtext"));
			document.setSection_id(rs.getInt("sectionid"));
			document.setChapteID(rs.getInt("catid"));
			document.setDate(rs.getDate("publish_up"));
			document.setState(rs.getInt("state"));

			// System.out.println(document.getTitle_alias());

			if (document.getId() == 1 || document.getId() == 21
					|| document.getId() == 51 || document.getId() == 43
					|| document.getId() == 184 || document.getId() == 44) {

				System.out.println(document.getId());

			} else {
				if(rs.getInt("state")==1){
				list_D.add(document);
				index += 1;
				pbar.updateBar(previousValue + index * percent
						/ numberOfDocuments);
				}
			}

		}
		rs.close();
		return list_D;
	}

	/**
	 * Metodo que obtiene los listados de modificaciones en BD
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static LinkedList<Modification> List_Modifications()
			throws SQLException {

		LinkedList<Modification> list_Modfi = new LinkedList<Modification>();

		// Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// try {
		// conn = DB_Conection.GetConnection();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			pstmt = DB_Conection.GetConnection().prepareStatement(
					DB_Query.GET_MODIFICATION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rs = (ResultSet) pstmt.executeQuery();

		Modification modification = null;
		while (rs.next()) {

			modification = new Modification();
			modification.setAction(rs.getString("accion"));
			modification.setDate(rs.getDate("fecha"));
			int id = rs.getInt("id_doc");
			modification.setId_document(id);
			modification.setId_modification(rs.getString("id_modif"));
			modification.setPage(rs.getString("paginas"));
			modification.setTitle_Categ(rs.getString("title_categ"));
			modification.setTitle_Documet(rs.getString("title_doc"));
			modification.setTitleAlias(getFileNameOfDocument(id));

			list_Modfi.add(modification);
		}
		rs.close();
		return list_Modfi;

	}

	/**
	 * Metodo que obtiene los listados de modificaciones en BD
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static LinkedList<Modification> List_Modifications(
			ProgressBarPanel pbar, int percent, int previousValue,
			int numberOfModifications) throws SQLException {

		LinkedList<Modification> list_Modfi = new LinkedList<Modification>();

		// Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// try {
		// conn = DB_Conection.GetConnection();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			pstmt = DB_Conection.GetConnection().prepareStatement(
					DB_Query.GET_MODIFICATION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = (ResultSet) pstmt.executeQuery();

		Modification modification = null;
		int index = 0;
		while (rs.next()) {

			modification = new Modification();
			modification.setAction(rs.getString("accion"));
			modification.setDate(rs.getDate("fecha"));
			int id = rs.getInt("id_doc");
			modification.setId_document(id);
			modification.setId_modification(rs.getString("id_modif"));
			modification.setPage(rs.getString("paginas"));
			modification.setTitle_Categ(rs.getString("title_categ"));
			modification.setTitle_Documet(rs.getString("title_doc"));
			modification.setTitleAlias(getFileNameOfDocument(id));

			list_Modfi.add(modification);
			index += 1;
			pbar.updateBar(previousValue + index * percent
					/ numberOfModifications);

		}
		rs.close();
		return list_Modfi;

	}

	/**
	 * Devuelve la cantidad de manuales existentes en la tabla de Manuales
	 * 
	 * @return Devuelve -1 si ocurre algún error y si no la cantidad de manuales
	 *         existentes.
	 * @throws SQLException
	 */
	public static int getNumberOfManuals() throws SQLException {
		// Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// try {
		// conn = DB_Conection.GetConnection();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		try {
			pstmt = DB_Conection.GetConnection().prepareStatement(
					DB_Query.GET_NUMBER_OF_MANUALS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = (ResultSet) pstmt.executeQuery();

		int count = -1;
		if (rs.next())
			count = rs.getInt(1);

		return count;
	}

	/**
	 * Devuelve la cantidad de secciones existentes en la tabla de Secciones
	 * 
	 * @return Devuelve -1 si ocurre algún error y si no la cantidad de
	 *         secciones existentes.
	 * @throws SQLException
	 */
	public static int getNumberOfSections() throws SQLException {
		// Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// try {
		// conn = DB_Conection.GetConnection();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			pstmt = DB_Conection.GetConnection().prepareStatement(
					DB_Query.GET_NUMBER_OF_SECTIONS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = (ResultSet) pstmt.executeQuery();

		int count = -1;
		if (rs.next())
			count = rs.getInt(1);

		return count;
	}

	/**
	 * Devuelve la cantidad de capítulos existentes en la tabla de Capítulos
	 * 
	 * @return Devuelve -1 si ocurre algún error y si no la cantidad de
	 *         capítulos existentes.
	 * @throws SQLException
	 */
	public static int getNumberOfChapters() throws SQLException {
		// Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// try {
		// conn = DB_Conection.GetConnection();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			pstmt = DB_Conection.GetConnection().prepareStatement(
					DB_Query.GET_NUMBER_OF_CHAPTERS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = (ResultSet) pstmt.executeQuery();

		int count = -1;
		if (rs.next())
			count = rs.getInt(1);

		// conn.close();
		return count;
	}

	/**
	 * Devuelve la cantidad de documentos existentes en la tabla de Documentos
	 * 
	 * @return Devuelve -1 si ocurre algún error y si no la cantidad de
	 *         documentos existentes.
	 * @throws SQLException
	 */
	public static int getNumberOfDocuments() throws SQLException {
		// Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// try {
		// conn = DB_Conection.GetConnection();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			pstmt = DB_Conection.GetConnection().prepareStatement(
					DB_Query.GET_NUMBER_OF_DOCUMENTS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = (ResultSet) pstmt.executeQuery();

		int count = -1;
		if (rs.next())
			count = rs.getInt("RECORDCOUNT");

		return count;
	}

	/**
	 * Devuelve la cantidad de modificaciones existentes en la tabla de
	 * Modificaciones
	 * 
	 * @return Devuelve -1 si ocurre algún error y si no la cantidad de
	 *         modificaciones existentes.
	 * @throws SQLException
	 */
	public static int getNumberOfModifications() throws SQLException {
		// Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// try {
		// conn = DB_Conection.GetConnection();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			pstmt = DB_Conection.GetConnection().prepareStatement(
					DB_Query.GET_NUMBER_OF_MODIFICATIONS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = (ResultSet) pstmt.executeQuery();

		int count = -1;
		if (rs.next())
			count = rs.getInt(1);

		// conn.close();
		return count;
	}

	/**
	 * Metodo que obtiene los fileNames de los documentos modificados
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static List<String> getFileNamesOfModifiedDocs() throws SQLException {

		List<String> modifiedDocs = new ArrayList<String>();

		// Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// try {
		// conn = DB_Conection.GetConnection();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			pstmt = DB_Conection.GetConnection().prepareStatement(
					DB_Query.GET_MODIFIEDDOC_FILENAMES);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = (ResultSet) pstmt.executeQuery();

		while (rs.next()) {
			modifiedDocs.add(rs.getString("title_alias"));
		}
		rs.close();

		return modifiedDocs;

	}

	// fechas
	/**
	 * Metodo que obtiene los fileNames de los documentos modificados filtrados
	 * por fecha
	 * 
	 * @param after
	 *            Fecha menor- null si no se quiere tener en cuenta
	 * @param before
	 *            Fecha mayor- null si no se quiere tener en cuenta
	 * @return La lista con los nombres de los ficheros
	 * @throws SQLException
	 * @throws Exception
	 */
	public static List<String> getFileNamesOfModifiedDocs(Date after,
			Date before) throws SQLException {

		if (after == null && before == null)
			return getFileNamesOfModifiedDocs();

		List<String> modifiedDocs = new ArrayList<String>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DB_Conection.GetConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (before == null)
			pstmt = conn
					.prepareStatement(DB_Query.Get_MODIFIEDDOC_FILENAMES_FILTERBYDATE_GREATERTHAN);

		if (after == null)
			pstmt = conn
					.prepareStatement(DB_Query.Get_MODIFIEDDOC_FILENAMES_FILTERBYDATE_LESSTHAN);

		if (after != null && before != null)
			pstmt = conn
					.prepareStatement(DB_Query.Get_MODIFIEDDOC_FILENAMES_FILTERBYDATE_BETWEEN);

		rs = (ResultSet) pstmt.executeQuery();

		while (rs.next()) {
			modifiedDocs.add(rs.getString("title_alias"));
		}
		rs.close();

		conn.close();
		return modifiedDocs;

	}

	/**
	 * Metodo que obtiene los fileNames de los documentos modificados y ademas
	 * los titulos de las modificaciones
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static Hashtable getFileNamesOfModifiedDocsAndModifTitle()
			throws SQLException {

		Hashtable modifiedDocs = new Hashtable();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DB_Conection.GetConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pstmt = conn
				.prepareStatement(DB_Query.Get_MODIFIEDDOC_FILENAMES_AND_TITLEALIAS);
		rs = (ResultSet) pstmt.executeQuery();

		while (rs.next()) {
			// modifiedDocs.put(rs.getString("title_doc"),
			// rs.getString("title_alias"));//[modifName, docFileName]
			modifiedDocs.put(rs.getString("title_alias"), rs
					.getString("title_doc"));// [docFileName, modifName]
		}
		rs.close();

		conn.close();
		return modifiedDocs;

	}

	/**
	 * Metodo que obtiene los nombres de los ficheros de los docs y su id en la
	 * tabla de docs.
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static Hashtable getDocFileNames() throws SQLException {

		Hashtable modifiedDocs = new Hashtable();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DB_Conection.GetConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pstmt = conn.prepareStatement(DB_Query.Get_DOCFILENAMES);
		rs = (ResultSet) pstmt.executeQuery();

		while (rs.next()) {
			modifiedDocs.put(rs.getString("title_alias"), rs.getString("id"));// [docFileName,
			// id]
		}
		rs.close();

		conn.close();
		return modifiedDocs;

	}

	/**
	 * Metodo que obtiene el fileName de un documento especifico
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static String getFileNameOfDocument(int idDoc) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String fileName = null;

		try {
			conn = DB_Conection.GetConnection();
			pstmt = conn.prepareStatement(DB_Query.GET_DOCUMENT_FILENAME);
			pstmt.setInt(1, idDoc);

			rs = (ResultSet) pstmt.executeQuery();

			while (rs.next())
				fileName = rs.getString("title_alias");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			rs.close();
		}

		conn.close();
		return fileName;
	}

	// // delete
	// /**
	// * Inserta un documento en la BD
	// *
	// * @return
	// * @throws Exception
	// */
	// public static void insertDocument(Document doc) throws Exception {
	// Connection conn = null;
	// PreparedStatement pstmt = null;
	// try {
	// conn = DB_Conection.GetConnection();
	// pstmt = conn.prepareStatement(DB_Query.INSERT_DOCUMENT);
	//
	// pstmt.setString(1, doc.getTitle());
	// pstmt.setString(2, doc.getTitle_alias());
	// pstmt.setString(3, doc.getIntrotext());
	// pstmt.setInt(4, doc.getSection_id());
	//
	// pstmt.executeUpdate();
	// } finally {
	// DB_Conection.close(pstmt);
	// }
	// }
	//
	// /**
	// * Inserta una sección en la BD
	// *
	// * @return
	// * @throws Exception
	// */
	// public static void insertSection(Section s) throws Exception {
	// Connection conn = null;
	// PreparedStatement pstmt = null;
	// try {
	// conn = DB_Conection.GetConnection();
	// pstmt = conn.prepareStatement(DB_Query.INSERT_SECTION);
	//
	// pstmt.setInt(1, s.getId_section());
	// pstmt.setString(2, s.getTitle());
	// pstmt.setString(3, s.getName());
	// pstmt.setInt(4, s.getId_manual());
	//
	// pstmt.executeUpdate();
	// } finally {
	// DB_Conection.close(pstmt);
	// }
	// }
	//
	// /**
	// * Inserta un manual en la BD
	// *
	// * @return
	// * @throws Exception
	// */
	// public static void insertManual(Manual m) throws Exception {
	// Connection conn = null;
	// PreparedStatement pstmt = null;
	// try {
	// conn = DB_Conection.GetConnection();
	// pstmt = conn.prepareStatement(DB_Query.INSERT_MANUAL);
	//
	// pstmt.setInt(1, m.getId_manual());
	// pstmt.setString(2, m.getNombre());
	// pstmt.setString(3, m.getDirectorio());
	//
	// pstmt.executeUpdate();
	// } finally {
	// DB_Conection.close(pstmt);
	// }
	// }
	//
	// /**
	// * Elimina un documento de la BD
	// *
	// * @return
	// * @throws Exception
	// */
	// public static void deleteDocument(Document doc) throws Exception {
	// Connection conn = null;
	// PreparedStatement pstmt = null;
	// try {
	// conn = DB_Conection.GetConnection();
	// pstmt = conn.prepareStatement(DB_Query.DELETE_DOCUMENT);
	// pstmt.setInt(1, doc.getId());
	// pstmt.executeUpdate();
	// } finally {
	// DB_Conection.close(pstmt);
	// }
	// }
	//
	// /**
	// * Elimina una seccion de la BD
	// *
	// * @return
	// * @throws Exception
	// */
	// public static void deleteSection(Section sec) throws Exception {
	// Connection conn = null;
	// PreparedStatement pstmt = null;
	// try {
	// conn = DB_Conection.GetConnection();
	// pstmt = conn.prepareStatement(DB_Query.DELETE_SECTION);
	// pstmt.setInt(1, sec.getId_section());
	// pstmt.executeUpdate();
	// } finally {
	// DB_Conection.close(pstmt);
	// }
	// }
	//
	// /**
	// * Elimina un manual de la BD
	// *
	// * @return
	// * @throws Exception
	// */
	// public static void deleteManual(Manual m) throws Exception {
	// Connection conn = null;
	// PreparedStatement pstmt = null;
	// try {
	// conn = DB_Conection.GetConnection();
	// pstmt = conn.prepareStatement(DB_Query.DELETE_MANUAL);
	// pstmt.setInt(1, m.getId_manual());
	// pstmt.executeUpdate();
	// } finally {
	// DB_Conection.close(pstmt);
	// }
	// }

}
