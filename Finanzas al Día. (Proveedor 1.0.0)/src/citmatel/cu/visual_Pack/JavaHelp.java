/**
 * Ejemplo de uso de JavaHelp. 6 Oct 2007
 * 
 * Una aplicación tonta con dos ventanas y un menú. En ella se puede ver la 
 * ayuda de JavaHelp pulsando el item de menu adecuado o la tecla F1.
 * Sirve como basico ejemplo de uso de JavaHelp.
 * 
 *  @author Chuidiang
 */
package citmatel.cu.visual_Pack;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Ejemplo sencillo de uso de JavaHelp. Crea dos ventanas con un menú y les pone
 * la ayuda.
 * 
 * @author JAMCH
 * 
 */
public class JavaHelp {
	/** Ventana secundaria */
	private JDialog secundaria;

	/** Ventana principal */
	private JFrame principal;

	/** Item de menú para la ayuda */
	private JMenuItem itemAyuda;

	/**
	 * Crea una instanacia de esta clase.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new JavaHelp();
	}

	/**
	 * Crea las ventanas, pone la ayuda y visualiza la ventana principal.
	 */
	public JavaHelp() {
		creaVentanaPrincipal();

		ponLaAyuda();
		visualizaVentanaPrincipal();
	}

	/**
	 * Con este nombre digo yo que visualizará la ventana principal.
	 */
	private void visualizaVentanaPrincipal() {
		principal.pack();
		principal.setSize(400, 100);
		principal.setVisible(true);
		principal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	/**
	 * Hace que el item del menu y la pulsacion de F1 visualicen la ayuda.
	 */
	private void ponLaAyuda() {
		try {
			// Carga el fichero de ayuda
			File fichero = new File("E:/!!WorkSpace/Help/help/help_set.hs");
			URL hsURL = fichero.toURI().toURL();

			// Crea el HelpSet y el HelpBroker
			HelpSet helpset = new HelpSet(getClass().getClassLoader(), hsURL);
			HelpBroker hb = helpset.createHelpBroker();
			

			// Pone ayuda a item de menu al pulsarlo y a F1 en ventana
			// principal y secundaria.
			hb.enableHelpOnButton(itemAyuda, "aplicacion", helpset);
			// hb.enableHelpKey(principal.getContentPane(), "ventana_principal",
			// helpset);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ¿Quizás crea la ventana principal?
	 */
	private void creaVentanaPrincipal() {
		principal = new JFrame("Ventana principal");
		JMenuBar menuBar = new JMenuBar();
		itemAyuda = new JMenuItem("Ayuda");
		menuBar.add(itemAyuda);
		principal.setJMenuBar(menuBar);
		principal.getContentPane().setLayout(new FlowLayout());
	}

}
