package citmatel.cu.class_Pack;

import java.io.Serializable;

public class Manual implements Serializable {

	private static final long serialUID = 1L;
	private int id_manual;
	private String nombre;
	private String directorio;

	public Manual() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return this.getNombre();
	}

	public int getId_manual() {
		return id_manual;
	}

	public void setId_manual(int id_manual) {
		this.id_manual = id_manual;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDirectorio() {
		return directorio;
	}

	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}

}
