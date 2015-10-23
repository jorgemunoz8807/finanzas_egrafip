package citmatel.cu.utils;

import java.beans.Statement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import citmatel.cu.DBConection.DB_Conection;
import citmatel.cu.class_Pack.ManagerSettings;

public class ImportingBackup {

	/**
	 * @param args
	 */
	// public static void main(String[] args) {
	// // TODO Auto-generated method stub
	//
	// try {
	// ImportBackup();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	public static void ImportBackup(String dir, String ip, int port)
			throws IOException, SQLException {
		String s = new String();
		StringBuffer sb = new StringBuffer();
		FileReader fr = new FileReader(new File(dir));
		// be sure to not have line starting with "--" or "/*" or any
		// other non aplhabetical character
		BufferedReader br = new BufferedReader(fr);
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}
		br.close();
		// here is our splitter ! We use ";" as a delimiter for each request
		// then we are sure to have well formed statements
		String[] inst = sb.toString().split("123456789");
		Connection conn = null;
		try {
			conn = GetConnection(ip, port);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Problem getting connection");
			e.printStackTrace();
		}
		java.sql.Statement stm = conn.createStatement();

		for (int i = 0; i < inst.length; i++) {
			// we ensure that there is no spaces before or after the request
			// string in order to not execute empty statements
			if (!inst[i].trim().equals("")) {
				System.out.println("No." + i + ": " + inst[i] + ";");
				stm.executeUpdate(inst[i] + ";");
			}
		}
		conn.close();
	}

	public final static Connection GetConnection(String ip, int port)
			throws Exception {

		Connection connection = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			String servidor = "jdbc:mysql://" + ip + ":" + port;
			connection = DriverManager.getConnection(servidor, ManagerSettings
					.getUsser(), ManagerSettings.getPass());

			System.out.println("Conected");

		} catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null, ex,
					"Error en la Conexión con la BD " + ex.getMessage(),
					JOptionPane.ERROR_MESSAGE);
			connection = null;

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"No se ha podido establecer conexión");

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
}
