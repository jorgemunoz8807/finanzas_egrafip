package citmatel.cu.class_Pack;

import java.io.Serializable;
import java.sql.Date;

import javax.swing.tree.DefaultMutableTreeNode;

public class Modification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id_modification;
	private int id_document;
	private String title_Documet;
	private String title_Section;
	private String title_Categ;
	private String page;
	private String action;
	private Date date;
	private String titleAlias;

	public Modification() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		String modif = "Fueron modificadas las páginas";
		String add = "Fueron adicionadas las páginas";
		String remove = "Fueron retiradas las páginas";
		String special = "Modificación Especial";

		String var = null;

		if (this.getAction().equals("adicion"))
			var = add;

		if (this.getAction().equals("modificacion"))
			var = modif;

		if (this.getAction().equals("retiro"))
			var = remove;

		if (this.getAction().equals("correccion"))
			var = special;

		return "Modificación No. " + this.getId_modification() + ", "
				+ this.getDate() + ", " + this.getTitle_Documet() + ", " + var
				+ " " + this.getPage();
	}

	public String getId_modification() {
		return id_modification;
	}

	public void setId_modification(String id_modification) {
		this.id_modification = id_modification;
	}

	public int getId_document() {
		return id_document;
	}

	public void setId_document(int id_document) {
		this.id_document = id_document;
	}

	public String getTitle_Documet() {
		return title_Documet;
	}

	public void setTitle_Documet(String title_Documet) {
		this.title_Documet = title_Documet;
	}

	public String getTitle_Section() {
		return title_Section;
	}

	public void setTitle_Section(String title_Section) {
		this.title_Section = title_Section;
	}

	public String getTitle_Categ() {
		return title_Categ;
	}

	public void setTitle_Categ(String title_Categ) {
		this.title_Categ = title_Categ;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitleAlias() {
		return titleAlias;
	}

	public void setTitleAlias(String titleAlias) {
		this.titleAlias = titleAlias;
	}

}
