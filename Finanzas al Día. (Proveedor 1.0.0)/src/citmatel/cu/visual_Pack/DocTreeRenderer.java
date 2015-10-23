package citmatel.cu.visual_Pack;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import citmatel.cu.class_Pack.Chapter;
import citmatel.cu.class_Pack.Document;
import citmatel.cu.class_Pack.Manual;
import citmatel.cu.class_Pack.Section;

public class DocTreeRenderer extends DefaultTreeCellRenderer {
	
	
	private static final long serialVersionUID = 1L;
	Icon rootIcon;
	Icon manualIcon;
	Icon sectionIcon;
	Icon chapterIcon;
	Icon documentIcon;
	
	public DocTreeRenderer(Icon rootIcon, Icon manual, Icon section, Icon chapter, Icon document)
	{
		this.manualIcon = manual;
		this.sectionIcon = section;
		this.chapterIcon = chapter;
		this.documentIcon = document;
		this.rootIcon = rootIcon;
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object value,
		      boolean selected, boolean expanded, boolean leaf, int row,
		      boolean hasFocus)
	{
		 super.getTreeCellRendererComponent(
                 tree, value, selected,
                 expanded, leaf, row,
                 hasFocus);
		 
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)value; 
		if(node.isRoot())
		 	setIcon(rootIcon);		 
		 Object userNode = node.getUserObject();
		 		 
		 if(userNode instanceof Manual)
			setIcon(manualIcon); 		
		 
		 if(userNode instanceof Section)
			 setIcon(sectionIcon); 	
		 
		 if(userNode instanceof Chapter)
			 setIcon(chapterIcon); 	
		 
		 if(userNode instanceof Document)
			 setIcon(documentIcon);
		 
		return this;
	}	

}
