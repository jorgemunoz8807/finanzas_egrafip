package citmatel.cu.DBConection;

public class DB_Query {

	public final static String GET_MANUAL = "SELECT * FROM manual ORDER BY id_manual";

	public final static String GET_SECTION = "SELECT * FROM egf_sections ORDER BY id";

	public final static String GET_CHAPTER = "SELECT * FROM egf_categories ORDER BY id";

	public final static String GET_DOCUMENT = "SELECT * FROM egf_content ORDER BY id";

	public final static String GET_MODIFICATION = "SELECT * FROM egf_modificaciones ORDER BY id";
	
	public final static String GET_DOCUMENT_FILENAME = "SELECT * FROM egf_content WHERE id = ?";
	
	public final static String GET_MODIFIEDDOC_FILENAMES = "SELECT title_alias FROM egf_modificaciones INNER JOIN egf_content ON egf_modificaciones.id_doc = egf_content.id";
	
	public final static String GET_NUMBER_OF_DOCUMENTS = "SELECT COUNT(*) as RECORDCOUNT FROM egf_content";
	public final static String GET_NUMBER_OF_SECTIONS = "SELECT COUNT(*) as RECORDCOUNT FROM egf_sections";
	public final static String GET_NUMBER_OF_CHAPTERS = "SELECT COUNT(*) as RECORDCOUNT FROM egf_categories";
	public final static String GET_NUMBER_OF_MANUALS = "SELECT COUNT(*) as RECORDCOUNT FROM manual";
	public final static String GET_NUMBER_OF_MODIFICATIONS = "SELECT COUNT(*) as RECORDCOUNT FROM egf_modificaciones";

	//arreglar comparar fechas
	public final static String Get_MODIFIEDDOC_FILENAMES_FILTERBYDATE_GREATERTHAN = "";
	
	public final static String Get_MODIFIEDDOC_FILENAMES_FILTERBYDATE_LESSTHAN = "";
	
	public final static String Get_MODIFIEDDOC_FILENAMES_FILTERBYDATE_BETWEEN = "";
	//
	
	
	//for test
	public final static String Get_MODIFIEDDOC_FILENAMES_AND_TITLEALIAS = "SELECT title_alias, title_doc  FROM egf_modificaciones INNER JOIN egf_content ON egf_modificaciones.id_doc = egf_content.id";
	public final static String Get_DOCFILENAMES = "SELECT title_alias, id FROM egf_content ORDER BY id";
	//
	
	public final static String INSERT_DOCUMENT = "INSERT INTO egf_content (title,title_alias,introtext, section_id) VALUES (?, ?, ?, ?)";

	public final static String INSERT_SECTION = "INSERT INTO egf_sections (id,title,name,id_manual) VALUES (?, ?, ?, ?)";

	public final static String INSERT_MANUAL = "INSERT INTO manual (id,nombre,directorio) VALUES (?, ?, ?)";

	public final static String DELETE_DOCUMENT = "DELETE FROM egf_content WHERE id=? ";

	public final static String DELETE_SECTION = "DELETE FROM egf_sections WHERE id=? ";

	public final static String DELETE_MANUAL = "DELETE FROM egf_manual WHERE id=? ";

}
