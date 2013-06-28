package com.shangest.base;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable, Comparable<Note>{

	private static final long serialVersionUID = 428595729575525429L;
	
	private int extId = -1;
	private String name;
	private Date date;
	private String description;
	
	
	/**
	 * @return the extId
	 */
	public int getExtId() {
		return extId;
	}
	/**
	 * @param extId the extId to set
	 */
	public void setExtId(int extId) {
		this.extId = extId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public int compareTo(Note o) {
		return new Integer(extId).compareTo(o.getExtId());
	}

}
