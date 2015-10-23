package citmatel.cu.class_Pack;

import java.io.Serializable;

public class Settings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String styleName; // application style
	private int daysToWarning; // when the license is about to expire
	private Boolean deleteOldModifications; // when importing compact
	private Boolean askIfDeleteOldModifications; //when importing compact modifications...if ask for delete old ones
	public String getStyle() {
		return styleName;
	}
	public void setStyleName(String style) {
		this.styleName = style;
	}
	public int getDaysToWarning() {
		return daysToWarning;
	}
	public void setDaysToWarning(int daysToWarning) {
		this.daysToWarning = daysToWarning;
	}
	public Boolean getDeleteOldModifications() {
		return deleteOldModifications;
	}
	public void setDeleteOldModifications(Boolean deleteOldModifications) {
		this.deleteOldModifications = deleteOldModifications;
	}
	public Boolean getAskIfDeleteOldModifications() {
		return askIfDeleteOldModifications;
	}
	public void setAskIfDeleteOldModifications(Boolean askIfDeleteOldModifications) {
		this.askIfDeleteOldModifications = askIfDeleteOldModifications;
	}
	
	
	
}
