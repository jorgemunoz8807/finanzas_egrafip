package citmatel.cu.class_Pack;

import java.io.Serializable;

public class ManagerSettings implements Serializable {
	private static final long serialUID = 1L;
	private static String dir;
	private static String name;
	private static String usser;
	private static String pass;
	private static int port;
	private static String pathDocument;

	public ManagerSettings() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String getDir() {
		return dir;
	}

	public static void setDir(String dir) {
		ManagerSettings.dir = dir;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		ManagerSettings.name = name;
	}

	public static String getUsser() {
		return usser;
	}

	public static void setUsser(String usser) {
		ManagerSettings.usser = usser;
	}

	public static String getPass() {
		return pass;
	}

	public static void setPass(String pass) {
		ManagerSettings.pass = pass;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		ManagerSettings.port = port;
	}

	public static String getPathDocument() {
		return pathDocument;
	}

	public static void setPathDocument(String pathDocument) {
		ManagerSettings.pathDocument = pathDocument;
	}

	public static long getSerialUID() {
		return serialUID;
	}

}
