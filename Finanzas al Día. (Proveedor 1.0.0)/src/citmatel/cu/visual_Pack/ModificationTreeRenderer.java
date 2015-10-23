package citmatel.cu.visual_Pack;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class ModificationTreeRenderer extends DefaultTreeCellRenderer {
	
	
	private static final long serialVersionUID = 1L;
	Icon rootIcon;
	Icon icon;
	
	
	public ModificationTreeRenderer(Icon rootIcon, Icon icon)
	{
		this.rootIcon = rootIcon;
		this.icon = icon;
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
		else
			 setIcon(icon);
		 
		return this;
	}	

}
