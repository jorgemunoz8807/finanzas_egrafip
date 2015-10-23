package citmatel.cu.visual_Pack;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.awt.Dimension;

public class ProgressBarPanel extends JProgressBar {

	static final int MY_MINIMUM = 0;
	static final int MY_MAXIMUM = 100;

	
	public ProgressBarPanel() {
		super();
		
	}
	  public void updateBar(int percent) {
		  
		    this.setValue(percent);
		    refresh();
		  }
	  
	  public void refresh()
	  {
		  this.paintImmediately(new Rectangle(this.getWidth(), this.getHeight()));
	  }
	  

}  //  @jve:decl-index=0:visual-constraint="10,-11"
