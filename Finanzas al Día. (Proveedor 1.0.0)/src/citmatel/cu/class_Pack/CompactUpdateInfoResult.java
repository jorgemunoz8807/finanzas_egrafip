package citmatel.cu.class_Pack;

import java.util.List;

public class CompactUpdateInfoResult {

	List<String> nonexistenDocs;
	List<String> nonInBDDocs;
	int filterModifications;
	
	public int getFilterModifications() {
		return filterModifications;
	}
	public void setFilterModifications(int filterModifications) {
		this.filterModifications = filterModifications;
	}
	public List<String> getNonexistenDocs() {
		return nonexistenDocs;
	}
	public List<String> getNonInBDDocs() {
		return nonInBDDocs;
	}	
	public CompactUpdateInfoResult(int numberOfModifications, List<String> nonexistentDocs, List<String> nonInBDDocs)
	{
		this.filterModifications = numberOfModifications;
		this.nonexistenDocs = nonexistentDocs;
		this.nonInBDDocs = nonInBDDocs;
	}
}
