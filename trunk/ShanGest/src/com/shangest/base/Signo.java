package com.shangest.base;

import java.io.Serializable;

public class Signo implements Serializable, Comparable<Signo> {

	private static final long serialVersionUID = 1214543223771071304L;
	
	private String rep;
	private String nombre;
	private String desc;
	
	public Signo() {
	}

	/**
	 * @return the rep
	 */
	public String getRep() {
		return rep;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param rep the rep to set
	 */
	public void setRep(String rep) {
		this.rep = rep;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public int compareTo(Signo s) {
		return rep.compareTo(s.getRep());
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
