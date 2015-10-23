package citmatel.cu.class_Pack;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Timer;

public class CompactData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LinkedList<Manual> manuals;
	private LinkedList<Section> sections;
	private LinkedList<Chapter> chapters;
	private LinkedList<Document> documents;
	private LinkedList<Modification> modifications;
	private License license;

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	public CompactData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LinkedList<Manual> getManuals() {
		return manuals;
	}

	public void setManuals(LinkedList<Manual> manuals) {
		this.manuals = manuals;
	}

	public LinkedList<Section> getSections() {
		return sections;
	}

	public void setSections(LinkedList<Section> sections) {
		this.sections = sections;
	}

	public LinkedList<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(LinkedList<Chapter> chapters) {
		this.chapters = chapters;
	}

	public LinkedList<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(LinkedList<Document> documents) {
		this.documents = documents;
	}

	public LinkedList<Modification> getModifications() {
		return modifications;
	}

	public void setModifications(LinkedList<Modification> modifications) {
		this.modifications = modifications;
	}



}
