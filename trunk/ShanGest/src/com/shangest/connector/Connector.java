package com.shangest.connector;

import java.util.ArrayList;

import com.shangest.base.Consulta;
import com.shangest.base.Note;
import com.shangest.base.Signo;
import com.shangest.base.User;
import com.shangest.connector.disk.LocalConnector;
import com.shangest.connector.mysql.MysqlConnector;

public interface Connector {
	
	public static class App {
		public static final String EXPORT_FOLDER = "./SG_PDF";
		
		public static final User user = new User();
		
		public static final int NOT_CONNECTED = -1;
		public static final int ONLINE = 0;
		public static final int OFFLINE = 1;
		
		public static MysqlConnector onlineConn;
		public static LocalConnector offlineConn;
		
		private static int connMode = NOT_CONNECTED;
		
		public static int getConnectorMode() {
			return connMode;
		}
		
		public static void setConnectorMode(int mode) {
			connMode = mode;
		}
	}

	public boolean login(String user, String password);

	public void changePassword(String user, String newpass);

	public boolean createAccount(User u, String pass);

	public String getCurrentVersion();

	public boolean addSign(Signo s);

	public Signo signSearch(String i);

	public boolean editSign(Signo s);

	public boolean addQuery(Consulta c);

	public boolean editQuery(Consulta c);

	public ArrayList<Signo> getSigns();

	public ArrayList<Consulta> getQueries();

	public void deleteSign(String id);

	public Signo signSearchByName(String i);

	public Consulta querySearch(String n, String d);

	public void deleteQuery(String name, String date);

	public ArrayList<Note> getNotes();

	public boolean addNote(Note n);

	public boolean editNote(Note n);

	public Note noteSearch(String name);

	public void deleteNote(String name, String date);
}
