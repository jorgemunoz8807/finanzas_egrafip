package citmatel.cu.class_Pack;

import java.util.List;

public class ExportError {

	List<String> nonexistenDocs;
	List<String> nonInBDDocs;
	
	public List<String> getNonexistenDocs() {
		return nonexistenDocs;
	}
	public List<String> getNonInBDDocs() {
		return nonInBDDocs;
	}	
	public ExportError(List<String> nonexistentDocs, List<String> nonInBDDocs)
	{
		this.nonexistenDocs = nonexistentDocs;
		this.nonInBDDocs = nonInBDDocs;
	}
}
