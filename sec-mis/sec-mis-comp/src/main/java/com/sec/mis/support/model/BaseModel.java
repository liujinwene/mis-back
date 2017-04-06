package com.sec.mis.support.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class is the super class of all the model classes. According to good design for database, every table should have a meaningless
 * primary key and a version column for performance consideration.And specially, the id and the version should generate by the database
 * system automatically.
 * 
 * @author James Lu<jameslus77@gmail.com>
 *
 */
public abstract class BaseModel implements Serializable {

	private static final long serialVersionUID = -3077559073886161301L;
	private int version = 1;
	private Date createdDate = new Date();
	private Date lastModifiedDate = new Date();

	protected String[] cannotNullOrEmptyProperties;

	public abstract Long getId();

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCode() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddss");
		String timeSign = simpleDateFormat.format(this.getCreatedDate());
		return timeSign + String.valueOf(this.getId());
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		if (this.lastModifiedDate == null) this.setLastModifiedDate(this.createdDate);
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public void validate() {

	}
}
