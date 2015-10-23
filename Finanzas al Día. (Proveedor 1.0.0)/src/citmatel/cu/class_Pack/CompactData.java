package citmatel.cu.class_Pack;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Timer;

public class CompactData implements Serializable {
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsser() {
		return usser;
	}

	public void setUsser(String usser) {
		this.usser = usser;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPathDocument() {
		return pathDocument;
	}

	public void setPathDocument(String pathDocument) {
		this.pathDocument = pathDocument;
	}

	private static final long serialUID = 1L;

	private LinkedList<Manual> listM;
	private LinkedList<Section> listS;
	private LinkedList<Chapter> listC;
	private LinkedList<Document> listD;
	private LinkedList<Modification> listModif;

	private String dir;
	private String name;
	private String usser;
	private String pass;
	private int port;
	private String pathDocument;

	public CompactData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LinkedList<Manual> getListM() {
		return listM;
	}

	public void setListM(LinkedList<Manual> listM) {
		this.listM = listM;
	}

	public LinkedList<Section> getListS() {
		return listS;
	}

	public void setListS(LinkedList<Section> listS) {
		this.listS = listS;
	}

	public LinkedList<Chapter> getListC() {
		return listC;
	}

	public void setListC(LinkedList<Chapter> listC) {
		this.listC = listC;
	}

	public LinkedList<Document> getListD() {
		return listD;
	}

	public void setListD(LinkedList<Document> listD) {
		this.listD = listD;
	}

	public static long getSerialUID() {
		return serialUID;
	}

	public LinkedList<Modification> getListModif() {
		return listModif;
	}

	public void setListModif(LinkedList<Modification> listModif) {
		this.listModif = listModif;
	}

	/**
	 * Método que llena las lista y configuración de BD que sera almacenada en
	 * ficheros
	 * 
	 * @throws SQLException
	 */
	/*public void SetCompactData() throws SQLException {
		
		listM = new ManagerDoc().getManuals();		
		listS = new ManagerDoc().getSections();		
		listC = new ManagerDoc().getChapters();	
		listD = new ManagerDoc().getDocuments();		
		listModif = new ManagerDoc().getModifications();
	}*/
	
	public void SetCompactData() throws SQLException {
		
		listM = ManagerDoc.getManuals();		
		listS = ManagerDoc.getSections();		
		listC = ManagerDoc.getChapters();	
		listD = ManagerDoc.getDocuments();		
		listModif = ManagerDoc.getModifications();
	}

}
