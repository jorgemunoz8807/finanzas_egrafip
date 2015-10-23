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
		String remove = "Fue retirado el documento completo.";
		String var = null;
		
		if (this.getAction().equals("adicion")) 
				var = add;
		
		if (this.getAction().equals("modificacion"))
				var = modif;
		
		if (this.getAction().equals("retiro"))
				var = remove;

		return "Modificación No. "
					+ this.getId_modification() + ", "
					+ this.getDate() + ", "
					+ this.getTitle_Documet() + ", " + var + " "
					+
					(this.getAction().equals("retiro")?" ":this.getPage()+".");
		
		
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result
				+ ((id_modification == null) ? 0 : id_modification.hashCode());
		result = prime * result + ((page == null) ? 0 : page.hashCode());
		result = prime * result
				+ ((title_Documet == null) ? 0 : title_Documet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Modification other = (Modification) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (id_modification == null) {
			if (other.id_modification != null)
				return false;
		} else if (!id_modification.equals(other.id_modification))
			return false;
		if (page == null) {
			if (other.page != null)
				return false;
		} else if (!page.equals(other.page))
			return false;
		if (title_Documet == null) {
			if (other.title_Documet != null)
				return false;
		} else if (!title_Documet.equals(other.title_Documet))
			return false;
		return true;
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
