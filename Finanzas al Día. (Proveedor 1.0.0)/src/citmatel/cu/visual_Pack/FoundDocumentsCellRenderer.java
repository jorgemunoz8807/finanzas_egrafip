package citmatel.cu.visual_Pack;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import citmatel.cu.class_Pack.Document;
import citmatel.cu.class_Pack.Utils;

public class FoundDocumentsCellRenderer extends JLabel implements ListCellRenderer {
   
	List<String> query;
	
	private static final long serialVersionUID = 1L;

	public FoundDocumentsCellRenderer(List<String> query) {
		setOpaque(true);
		//setForeground(new DefaultListCellRenderer().getForeground());
		//setBackground(new DefaultListCellRenderer().getBackground());
        this.query = query;
    }
	
	
	
	

    public Component getListCellRendererComponent(JList list,
                                                  Object value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {

    	
    	if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
    	
    	 //setText(value.toString());
    	String boldName = Utils.getTitleInBoldOf((Document)value, query);
    	String date = ((Document)value).getDate().toString();
    	String result = "<html>" + boldName +"   (" + date + ")" + "</html>";
    	setText(result);
    	//setText("test");
    	list.setBackground(Color.white);

        return this;
    }
}
