package citmatel.cu.class_Pack;

import java.io.Serializable;

public class Section implements Serializable {

	private static final long serialUID = 1L;
	private int id_section;
	private String title;
	private String name;
	private int id_manual;

	public Section() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString()
	{
		return this.getName();
	}

	public int getId_section() {
		return id_section;
	}

	public void setId_section(int id_section) {
		this.id_section = id_section;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId_manual() {
		return id_manual;
	}

	public void setId_manual(int id_manual) {
		this.id_manual = id_manual;
	}

}
