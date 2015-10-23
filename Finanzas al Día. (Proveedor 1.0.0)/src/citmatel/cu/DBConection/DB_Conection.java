package citmatel.cu.DBConection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import citmatel.cu.class_Pack.ManagerSettings;

public class DB_Conection {
	private String nameDB;
	private String dirIP;
	private String usser;
	private String pass;
	private int port;

	public final static Connection GetConnection() throws Exception {

		String url = ManagerSettings.getDir();
		int port = ManagerSettings.getPort();
		String name = ManagerSettings.getName();

		String usser = ManagerSettings.getUsser();
		String pass = ManagerSettings.getPass();

		Connection connection = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			String servidor = "jdbc:mysql://" + url + ":" + port + "/" + name;

			connection = DriverManager.getConnection(servidor, usser, pass);

			System.out.println("Conected");

		} catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null, ex,
					"Error en la Conexión con la BD " + ex.getMessage(),
					JOptionPane.ERROR_MESSAGE);
			connection = null;

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Imposible conectar con:"
					+ "jdbc:mysql://" + url + ":" + port + "/" + name);

			connection = null;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex,
					"Error3 en la Conexión con la BD " + ex.getMessage(),
					JOptionPane.ERROR_MESSAGE);
			connection = null;
		} finally {
			return connection;
		}
	}

	// -----------------------------------
	public static void close(ResultSet rs) {
		if (rs == null) {
			return;
		}
		try {
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement stmt) {
		if (stmt == null) {
			return;
		}
		try {
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(PreparedStatement pstmt) {
		if (pstmt == null) {
			return;
		}
		try {
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(Connection conn) {
		if (conn == null) {
			return;
		}
		try {
			if (!conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void rollbackTransaction(Connection conn) {
		if (conn == null) {
			return;
		}
		try {
			if (!conn.isClosed()) {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getNameDB() {
		return nameDB;
	}

	public void setNameDB(String nameDB) {
		this.nameDB = nameDB;
	}

	public String getDirIP() {
		return dirIP;
	}

	public void setDirIP(String dirIP) {
		this.dirIP = dirIP;
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

}