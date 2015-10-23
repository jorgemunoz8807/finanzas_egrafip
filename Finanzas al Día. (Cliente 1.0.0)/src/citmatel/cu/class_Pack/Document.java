package citmatel.cu.class_Pack;

import java.io.Serializable;
import java.util.Date;

import javax.swing.tree.TreeSelectionModel;

public class Document implements Serializable {

	private static final long serialUID = 1L;
	private int id;
	private String title;
	private String title_alias;
	private String introtext;
	private int section_id;
	private int chapteID;
	private Date date;
	private int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Document() {
		super();

		// TODO Auto-generated constructor stub
	}

	public Document(TreeSelectionModel selectionModel) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return this.getTitle() + "   (" + this.getDate() + ")";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle_alias() {
		return title_alias;
	}

	public void setTitle_alias(String title_alias) {
		this.title_alias = title_alias;
	}

	public String getIntrotext() {
		return introtext;
	}

	public void setIntrotext(String introtext) {
		this.introtext = introtext;
	}

	public int getSection_id() {
		return section_id;
	}

	public void setSection_id(int section_id) {
		this.section_id = section_id;
	}

	public int getChapteID() {
		return chapteID;
	}

	public void setChapteID(int chapteID) {
		this.chapteID = chapteID;
	}

	public static long getSerialUID() {
		return serialUID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
