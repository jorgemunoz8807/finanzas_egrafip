package citmatel.cu.visual_Pack;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import citmatel.cu.class_Pack.Utils;

public class LicenseFilesFilter extends FileFilter{

	public static String validExtension = "lic";
	@Override
	//Accept all directories and all .lic files.
	public boolean accept(File f) {
		if (f.isDirectory()) {
            return true;
        }
        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(validExtension)) 
                    return true;
            return false;
            }        

        return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Ficheros de licencia";
	}

}
