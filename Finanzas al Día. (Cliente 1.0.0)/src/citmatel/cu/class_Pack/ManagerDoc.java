package citmatel.cu.class_Pack;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;

public class ManagerDoc implements Serializable {

	private static final long serialUID = 1L;

	private static LinkedList<Manual> manuals;
	private static LinkedList<Section> sections;
	private static LinkedList<Document> documents;
	private static LinkedList<Chapter> chapters;
	private static LinkedList<Modification> modifications;
	
	
	public static LinkedList<Manual> getManuals() {
		return manuals;
	}

	public static void setManuals(LinkedList<Manual> manuals) {
		ManagerDoc.manuals = manuals;
	}

	public static LinkedList<Section> getSections() {
		return sections;
	}

	public static void setSections(LinkedList<Section> sections) {
		ManagerDoc.sections = sections;
	}

	public static LinkedList<Document> getDocuments() {
		return documents;
	}

	public static void setDocuments(LinkedList<Document> documents) {
		ManagerDoc.documents = documents;
	}

	public static LinkedList<Chapter> getChapters() {
		return chapters;
	}

	public static void setChapters(LinkedList<Chapter> chapters) {
		ManagerDoc.chapters = chapters;
	}

	public static LinkedList<Modification> getModifications() {
		return modifications;
	}

	public static void setModifications(LinkedList<Modification> modifications) {
		ManagerDoc.modifications = modifications;
	}

	public static long getSerialUID() {
		return serialUID;
	}
	
	public static Boolean IsInitialized()
	{
		return getManuals()!= null && getSections()!=null && getChapters()!=null && getDocuments()!= null;
	}
	
	public static Boolean HasDocuments()
	{
		if(documents == null)
			return false;
		return documents.size()!=0;
	}

	
	
	
}











