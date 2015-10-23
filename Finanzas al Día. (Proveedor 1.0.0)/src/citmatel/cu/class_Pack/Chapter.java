package citmatel.cu.class_Pack;

import java.io.Serializable;

public class Chapter implements Serializable {

	private static final long serialUID = 1L;
	private int id;
	private String idSection;
	private String name;

	public Chapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return this.getName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdSection() {
		return idSection;
	}

	public void setIdSection(String idSection) {
		this.idSection = idSection;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
