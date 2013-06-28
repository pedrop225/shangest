package com.shangest.connector.mysql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.SecretKey;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.shangest.base.Consulta;
import com.shangest.base.Note;
import com.shangest.base.Signo;
import com.shangest.base.User;
import com.shangest.cipher.RCipher;
import com.shangest.connector.Connector;
import com.shangest.connector.disk.LocalConnector;

public class MysqlConnector implements Connector {

	private static final String EXTERNAL_PATH = "http://shangest.zzl.org/desktopApp";
	
	private RCipher cipher;
	public static SimpleDateFormat FORMATTER_DATE_EN;
	public static SimpleDateFormat FORMATTER_DATE_ES;
	public static SimpleDateFormat FORMATTER_DATETIME_EN;
	public static SimpleDateFormat FORMATTER_DATETIME_CUSTOM;
	
	private static MysqlConnector INSTANCE = null;
	
	private MysqlConnector() {
		
		FORMATTER_DATE_EN = new SimpleDateFormat("yyyy-MM-dd");
		FORMATTER_DATE_ES = new SimpleDateFormat("dd-MM-yyyy");
		FORMATTER_DATETIME_EN = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FORMATTER_DATETIME_CUSTOM = new SimpleDateFormat("dd-MM-yyyy [HH:mm:ss]");
		
		try {
			SecretKey key = RCipher.importKeyFromUrl("http://pedrop225.comuf.com/rhinos/security/security.keys");
			cipher = new RCipher(key);
		}
		catch (Exception e) {}		
	}
	
	public synchronized static MysqlConnector getInstance() {
		return (INSTANCE != null) ? INSTANCE : new MysqlConnector();
	}
	
	public boolean login(String user, String password) {

	    //the mail data to send
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("user", cipher.encode(user)));
	    nameValuePairs.add(new BasicNameValuePair("password", cipher.encode(password)));
	    
	    try {
	        JSONArray jsonArray = getDataFromDB(EXTERNAL_PATH+"/db_login.php", nameValuePairs);

	        if ((jsonArray != null) && (jsonArray.length() > 0)) {
		        JSONObject jsonObj = jsonArray.getJSONObject(0);
		        
	        	App.user.setExtId(jsonObj.getInt("id"));
	        	App.user.setType(jsonObj.getInt("type"));
	        	App.user.setUser(user);
	        	App.user.setName(cipher.decode(jsonObj.getString("name")));
	        	App.user.setMail(cipher.decode(jsonObj.getString("mail")));
	        	return true;
	        }
	    }
	    catch (Exception e) {return false;}
        
        return false;
	}
	
	@Override
	public void changePassword(String user, String newpass) {
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("user", cipher.encode(user)));
	    nameValuePairs.add(new BasicNameValuePair("newpass", cipher.encode(newpass)));
	 
	    getDataFromDB(EXTERNAL_PATH+"/db_change_password.php", nameValuePairs);
	}
	
	@Override
	public boolean createAccount(User u, String password) {
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("user", cipher.encode(u.getUser())));
	    nameValuePairs.add(new BasicNameValuePair("mail", cipher.encode(u.getMail())));
	    
	    JSONArray jsonArray = getDataFromDB(EXTERNAL_PATH+"/db_user_or_mail_exists.php", nameValuePairs);
	    
	    if (jsonArray == null) {
		    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(u.getName())));
		    nameValuePairs.add(new BasicNameValuePair("password", cipher.encode(password)));
	    	
		    getDataFromDB(EXTERNAL_PATH+"/db_create_account.php", nameValuePairs);
		    
	    	return true;
	    }
	    else
	    	return false;
	}
	
	@Override
	public boolean addSign(Signo s) {
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("representation", s.getRep()));
	    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(s.getNombre())));
	    nameValuePairs.add(new BasicNameValuePair("description", cipher.encode(s.getDesc())));
	    
		getDataFromDB(EXTERNAL_PATH+"/db_add_sign.php", nameValuePairs);
		    
	    return true;
	}
	
	@Override
	public boolean editSign(Signo s) {
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("representation", s.getRep()));
	    nameValuePairs.add(new BasicNameValuePair("description", cipher.encode(s.getDesc())));
	    
		getDataFromDB(EXTERNAL_PATH+"/db_edit_sign.php", nameValuePairs);
		    
	    return true;
	}
	
	@Override
	public Signo signSearch(String i) {
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("representation", i));
	    
	    JSONArray jsonArray = getDataFromDB(EXTERNAL_PATH+"/db_sign_search.php", nameValuePairs);
	    
	    if ((jsonArray != null) && (jsonArray.length() > 0)) {
	    	try {
	    		JSONObject jsonObj = jsonArray.getJSONObject(0);
	    	
	    		Signo s = new Signo();
	    		s.setRep(jsonObj.getString("representation"));
	    		s.setNombre(cipher.decode(jsonObj.getString("name")));
	    		s.setDesc(cipher.decode(jsonObj.getString("description")));
	    		
	    		return s;
	    	}
	    	catch (Exception e) {return null;}
	    }
	    else
	    	return null;
	}
	
	@Override
	public Signo signSearchByName(String i) {
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(i)));
	    
	    JSONArray jsonArray = getDataFromDB(EXTERNAL_PATH+"/db_sign_search_by_name.php", nameValuePairs);
	    
	    if ((jsonArray != null) && (jsonArray.length() > 0)) {
	    	try {
	    		JSONObject jsonObj = jsonArray.getJSONObject(0);
	    	
	    		Signo s = new Signo();
	    		s.setRep(jsonObj.getString("representation"));
	    		s.setNombre(cipher.decode(jsonObj.getString("name")));
	    		s.setDesc(cipher.decode(jsonObj.getString("description")));
	    		
	    		return s;
	    	}
	    	catch (Exception e) {return null;}
	    }
	    else
	    	return null;
	}
	
	@Override
	public ArrayList<Signo> getSigns() {
		ArrayList<Signo> r = new ArrayList<Signo>();
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    		
        JSONArray jsonArray = getDataFromDB(EXTERNAL_PATH+ "/db_get_signs.php", nameValuePairs);
		
        if (jsonArray != null) {
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					Signo s = new Signo();
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					
					s.setNombre(cipher.decode(jsonObj.getString("name")));
				
					r.add(s);
				}
				catch (Exception e) {}
	        }
	    }
		
		return r;
	}
	
	@Override
	public void deleteSign(String name) {
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(name)));	
	    
	    getDataFromDB(EXTERNAL_PATH+"/db_delete_sign.php", nameValuePairs);
	}
	
	@Override
	public boolean addQuery(Consulta c) {
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(c.getName())));
	    nameValuePairs.add(new BasicNameValuePair("date", FORMATTER_DATE_EN.format(c.getDate())));
	    nameValuePairs.add(new BasicNameValuePair("rep_sign", c.getSigno().getRep()));
	    nameValuePairs.add(new BasicNameValuePair("rep_witness_1", c.getTest1().getRep()));
	    nameValuePairs.add(new BasicNameValuePair("rep_witness_2", c.getTest2().getRep()));
	    nameValuePairs.add(new BasicNameValuePair("sign", cipher.encode(c.getSigno().getNombre())));
	    nameValuePairs.add(new BasicNameValuePair("witness_1", cipher.encode(c.getTest1().getNombre())));
	    nameValuePairs.add(new BasicNameValuePair("witness_2", cipher.encode(c.getTest2().getNombre())));
	    nameValuePairs.add(new BasicNameValuePair("desc_sign", cipher.encode(c.getSigno().getDesc())));
	    nameValuePairs.add(new BasicNameValuePair("desc_witness_1", cipher.encode(c.getTest1().getDesc())));
	    nameValuePairs.add(new BasicNameValuePair("desc_witness_2", cipher.encode(c.getTest2().getDesc())));

		getDataFromDB(EXTERNAL_PATH+"/db_add_query.php", nameValuePairs);
		    
	    return true;
	}
	
	@Override
	public boolean editQuery(Consulta c) {
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", c.getExtId()+""));
	    nameValuePairs.add(new BasicNameValuePair("rep_sign", c.getSigno().getRep()));
	    nameValuePairs.add(new BasicNameValuePair("rep_witness_1", c.getTest1().getRep()));
	    nameValuePairs.add(new BasicNameValuePair("rep_witness_2", c.getTest2().getRep()));
	    nameValuePairs.add(new BasicNameValuePair("sign", cipher.encode(c.getSigno().getNombre())));
	    nameValuePairs.add(new BasicNameValuePair("witness_1", cipher.encode(c.getTest1().getNombre())));
	    nameValuePairs.add(new BasicNameValuePair("witness_2", cipher.encode(c.getTest2().getNombre())));
	    nameValuePairs.add(new BasicNameValuePair("desc_sign", cipher.encode(c.getSigno().getDesc())));
	    nameValuePairs.add(new BasicNameValuePair("desc_witness_1", cipher.encode(c.getTest1().getDesc())));
	    nameValuePairs.add(new BasicNameValuePair("desc_witness_2", cipher.encode(c.getTest2().getDesc())));
	    
		getDataFromDB(EXTERNAL_PATH+"/db_edit_query.php", nameValuePairs);
		    
	    return true;
	}
	
	@Override
	public Consulta querySearch(String n, String d) {
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(n)));
	    try {
			nameValuePairs.add(new BasicNameValuePair("date", FORMATTER_DATE_EN.format(FORMATTER_DATE_ES.parse(d))));
		} 
	    catch (ParseException e1) {}
	   
	    JSONArray jsonArray = getDataFromDB(EXTERNAL_PATH+"/db_query_search.php", nameValuePairs);
	    
	    if ((jsonArray != null) && (jsonArray.length() > 0)) {
	    	try {
	    		JSONObject jsonObj = jsonArray.getJSONObject(0);
	    	
	    		Consulta c = new Consulta();				
				c.setExtId(jsonObj.getInt("id"));
				c.setName(cipher.decode(jsonObj.getString("name")));
				c.setDate(FORMATTER_DATE_EN.parse(jsonObj.getString("date")));
				
				Signo s = new Signo();
				Signo t = new Signo();
				Signo t2 = new Signo();
				
				s.setRep(jsonObj.getString("rep_sign"));
				s.setNombre(cipher.decode(jsonObj.getString("sign")));
				s.setDesc(cipher.decode(jsonObj.getString("desc_sign")));
				
				t.setRep(jsonObj.getString("rep_witness_1"));
				t.setNombre(cipher.decode(jsonObj.getString("witness_1")));
				t.setDesc(cipher.decode(jsonObj.getString("desc_witness_1")));
				
				t2.setRep(jsonObj.getString("rep_witness_2"));
				t2.setNombre(cipher.decode(jsonObj.getString("witness_2")));
				t2.setDesc(cipher.decode(jsonObj.getString("desc_witness_2")));
				
				c.setSigno(s);
				c.setTest1(t);
				c.setTest2(t2);
	    		
	    		return c;
	    	}
	    	catch (Exception e) {return null;}
	    }
	    else
	    	return null;
	}
	
	@Override
	public void deleteQuery(String name, String date) {
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(name)));	
	    try {
			nameValuePairs.add(new BasicNameValuePair("date", FORMATTER_DATE_EN.format(FORMATTER_DATE_ES.parse(date))));
		} 
	    catch (ParseException e1) {}	
	    
	    getDataFromDB(EXTERNAL_PATH+"/db_delete_query.php", nameValuePairs);
	}

	@Override
	public ArrayList<Consulta> getQueries() {
		ArrayList<Consulta> r = new ArrayList<Consulta>();
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    		
        JSONArray jsonArray = getDataFromDB(EXTERNAL_PATH+"/db_get_queries.php", nameValuePairs);
        
        if (jsonArray != null) {
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					Consulta c = new Consulta();
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					
					c.setExtId(jsonObj.getInt("id"));
					c.setName(cipher.decode(jsonObj.getString("name")));
					c.setDate(FORMATTER_DATE_EN.parse(jsonObj.getString("date")));
					
					r.add(c);
				}
				catch (Exception e) {}
	        }
	    }
		
		return r;
	}
	
	@Override
	public ArrayList<Note> getNotes() {
		ArrayList<Note> r = new ArrayList<Note>();
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    		
        JSONArray jsonArray = getDataFromDB(EXTERNAL_PATH+"/db_get_notes.php", nameValuePairs);
        
        if (jsonArray != null) {
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					Note n = new Note();
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					
					n.setName(cipher.decode(jsonObj.getString("name")));
					n.setDate(FORMATTER_DATETIME_EN.parse(jsonObj.getString("datetime")));
					
					r.add(n);
				}
				catch (Exception e) {}
	        }
	    }
		
		return r;
	}
	
	@Override
	public boolean addNote(Note s) {
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(s.getName())));
	    nameValuePairs.add(new BasicNameValuePair("datetime", FORMATTER_DATETIME_EN.format(s.getDate())));
	    nameValuePairs.add(new BasicNameValuePair("description", cipher.encode(s.getDescription())));
	    
		getDataFromDB(EXTERNAL_PATH+"/db_add_note.php", nameValuePairs);
		    
	    return true;
	}
	
	@Override
	public boolean editNote(Note s) {
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", s.getExtId()+""));
	    nameValuePairs.add(new BasicNameValuePair("datetime", FORMATTER_DATETIME_EN.format(s.getDate())));
	    nameValuePairs.add(new BasicNameValuePair("description", cipher.encode(s.getDescription())));
	    
		getDataFromDB(EXTERNAL_PATH+"/db_edit_note.php", nameValuePairs);
		    
	    return true;
	}
	
	@Override
	public Note noteSearch(String name) {
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(name)));
	    
	    JSONArray jsonArray = getDataFromDB(EXTERNAL_PATH+"/db_note_search.php", nameValuePairs);
	    
	    if ((jsonArray != null) && (jsonArray.length() > 0)) {
	    	try {
	    		JSONObject jsonObj = jsonArray.getJSONObject(0);
	    	
	    		Note n = new Note();
	    		n.setExtId(jsonObj.getInt("id"));
	    		n.setName(cipher.decode(jsonObj.getString("name")));
				n.setDate(FORMATTER_DATETIME_EN.parse(jsonObj.getString("datetime")));
	    		n.setDescription(cipher.decode(jsonObj.getString("description")));
	    		
	    		return n;
	    	}
	    	catch (Exception e) {return null;}
	    }
	    else
	    	return null;
	}
	
	@Override
	public void deleteNote(String name, String date) {
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(name)));
		try {
			Date aux = FORMATTER_DATETIME_CUSTOM.parse(date);
		    nameValuePairs.add(new BasicNameValuePair("datetime", FORMATTER_DATETIME_EN.format(aux)));
		} 
		catch (ParseException e) {}
		
	    getDataFromDB(EXTERNAL_PATH+"/db_delete_note.php", nameValuePairs);
	}
	
	@Override
	public String getCurrentVersion() {
		try {
			JSONArray jsonArray = getDataFromDB(EXTERNAL_PATH+"/db_get_current_version.php", new ArrayList<NameValuePair>()); 
			return jsonArray.getJSONObject(0).getString("c_version");
		}
		catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	private JSONArray getDataFromDB(String url, ArrayList<NameValuePair> nameValuePairs) {
	    
		String result = "";
	    InputStream is = null;
	    
	    try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            sb.append(reader.readLine());
	        
            is.close();
            result = sb.toString();
            return ((result.trim().length() > 0) ? new JSONArray(result) : new JSONArray());
	    }
	    catch (Exception e) {return null;}
	}
	
	public boolean exportDataFile() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(LocalConnector.DATA_FILE)));
			
			//writing file
			oos.writeObject(getNotes());
			oos.writeObject(getSigns());
			oos.writeObject(getQueries());
			
			oos.close();
			return true;
		} 
		catch (Exception e) {
			return false;
		}
	}
}
