package com.shangest.base;

public class User {

	private static final int DEFAULT = 0;
	private static final int ROOT = 9177;
	
	public static final int ONLINE = 0;
	public static final int OFFLINE = 1;
	
	private String user;
	private String name;
	private String mail;
	private int extId;
	private int type;
	private int connectionMode;
	
	public User() {
		super();
		type = DEFAULT;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public int getExtId() {
		return extId;
	}

	public void setExtId(int extId) {
		this.extId = extId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public void doRoot() {
		this.type = ROOT;
	}
	
	public boolean isRoot() {
		return (type == ROOT);
	}

	public int getConnectionMode() {
		return connectionMode;
	}

	public void setConnectionMode(int connectionMode) {
		this.connectionMode = connectionMode;
	}
	
	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	public boolean isOnline() {
		return (connectionMode == ONLINE);
	}
	
	public void clear() {
		user = "";
		extId = -1;
		type = DEFAULT;
		connectionMode = -1;
	}
}
