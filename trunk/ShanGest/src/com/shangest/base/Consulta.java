package com.shangest.base;

import java.io.Serializable;
import java.util.Date;

public class Consulta implements Serializable {

	private static final long serialVersionUID = 5798166679885752198L;
	
	private String name;
	private Date date;
	private Signo signo;
	private Signo test1;
	private Signo test2;
	private int extId;
	
	public Consulta() {
		// TODO Auto-generated constructor stub
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
	 * @return the signo
	 */
	public Signo getSigno() {
		return signo;
	}

	/**
	 * @return the test1
	 */
	public Signo getTest1() {
		return test1;
	}

	/**
	 * @return the test2
	 */
	public Signo getTest2() {
		return test2;
	}

	/**
	 * @param signo the signo to set
	 */
	public void setSigno(Signo signo) {
		this.signo = signo;
	}

	/**
	 * @param test1 the test1 to set
	 */
	public void setTest1(Signo test1) {
		this.test1 = test1;
	}

	/**
	 * @param test2 the test2 to set
	 */
	public void setTest2(Signo test2) {
		this.test2 = test2;
	}

	public int getExtId() {
		return extId;
	}
	
	public void setExtId(int id) {
		extId = id;
	}
}
