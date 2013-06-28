package com.shangest.connector.disk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.shangest.base.Consulta;
import com.shangest.base.Note;
import com.shangest.base.Signo;
import com.shangest.base.User;
import com.shangest.connector.Connector;
import com.shangest.connector.mysql.MysqlConnector;

public class LocalConnector implements Connector {

	public static final String DATA_FILE = "data.sg";
	public static final String INIT_FILE = "init.sg";
		
	private ArrayList<Note> notes = new ArrayList<Note>();
	private ArrayList<Signo> signs = new ArrayList<Signo>();
	private ArrayList<Consulta> queries = new ArrayList<Consulta>();
	
	private static LocalConnector INSTANCE = null;
	
	public static synchronized LocalConnector getInstance() {
		return (INSTANCE != null) ? INSTANCE : new LocalConnector();
	}
	
	private LocalConnector() {
		
		if (!new File(INIT_FILE).exists())
			createDefaultInitFile("default", "default");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean login(String user, String password) {
		
		ObjectInputStream ois = null;

		try {
			ois = new ObjectInputStream(new FileInputStream(new File(INIT_FILE)));
			ArrayList<BasicNameValuePair> al = (ArrayList<BasicNameValuePair>) ois.readObject();
			
			user = al.get(0).getValue();
			
			if (al.get(1).getValue().equals(password))
				return true;
			else
				return false;
		}
		catch (Exception e) {
			return false;
		}
	}

	@Override
	public void changePassword(String user, String newpass) {}

	@Override
	public boolean createAccount(User u, String pass) {return false;}

	@Override
	public String getCurrentVersion() {return null;}

	@Override
	public boolean addSign(Signo s) {
		signs.add(s);
		return false;
	}

	@Override
	public Signo signSearch(String i) {
		
		Signo s = new Signo();
		s.setRep(i);
		
		return signs.get(signs.indexOf(s));
	}

	@Override
	public boolean editSign(Signo s) {
		
		signs.get(signs.indexOf(s)).setDesc(s.getDesc());
		return true;
	}

	@Override
	public boolean addQuery(Consulta c) {
		queries.add(c);
		return false;
	}

	@Override
	public boolean editQuery(Consulta c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Signo> getSigns() {
		return signs;
	}

	@Override
	public ArrayList<Consulta> getQueries() {
		return queries;
	}
	
	@Override
	public void deleteSign(String id) {
		int i;
		for (i = 0; i < signs.size(); i++)
			if (signs.get(i).getNombre().equals(id))
				break;
		
		signs.remove(i);
	}
	
	@Override
	public Signo signSearchByName(String i) {
		int j;
		for (j = 0; j < signs.size(); j++)
			if (signs.get(j).getNombre().equals(i))
				break;
		
		return signs.get(j);
	}
	
	@Override
	public Consulta querySearch(String n, String d) {
		int j;
		for (j = 0; j < queries.size(); j++)
			if ((queries.get(j).getName().equals(n)) && (MysqlConnector.FORMATTER_DATETIME_CUSTOM.format(queries.get(j).getDate()).equals(d)))
				break;
		
		return queries.get(j);
	}
	
	@Override
	public void deleteQuery(String name, String date) {
		int j;
		for (j = 0; j < queries.size(); j++)
			if ((queries.get(j).getName().equals(name)) && (MysqlConnector.FORMATTER_DATETIME_CUSTOM.format(queries.get(j).getDate()).equals(date)))
				break;
		
		queries.remove(j);
	}
	
	@Override
	public ArrayList<Note> getNotes() {
		return notes;
	}

	@Override
	public boolean addNote(Note n) {
		notes.add(n);
		return true;
	}

	@Override
	public boolean editNote(Note n) {
		return false;
	}

	@Override
	public Note noteSearch(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteNote(String name, String date) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * */
	public boolean createDefaultInitFile(String user, String password) {
		
		ObjectOutputStream oos = null;
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(INIT_FILE)));
			
			ArrayList<BasicNameValuePair> al = new ArrayList<BasicNameValuePair>(); 
			al.add(new BasicNameValuePair("user", user));
			al.add(new BasicNameValuePair("password", password));
			
			//writing initializer file
			oos.writeObject(al);
			
			if (oos != null)
				oos.close();
			
			return true;
		} 
		catch (Exception e) {
			try {
				if (oos != null)
					oos.close();
			} 
			catch (Exception e1) {}
			
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean loadDataFile() {
		ObjectInputStream ois = null;

		try {
			ois = new ObjectInputStream(new FileInputStream(new File(DATA_FILE)));
			
			//reading data file
			notes = (ArrayList<Note>) ois.readObject();
			signs = (ArrayList<Signo>) ois.readObject();
			queries = (ArrayList<Consulta>) ois.readObject();
						
			if (ois != null)
				ois.close();
			
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
			try {
				if (ois != null)
					ois.close();
			} 
			catch (Exception e1) {}
			
			return false;
		}
	}
}
