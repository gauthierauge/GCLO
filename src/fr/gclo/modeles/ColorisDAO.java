package fr.gclo.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import fr.gclo.controller.DataBaseInstance;

public class ColorisDAO {
	   
	public static Vector<String> getVecteurColorisDAO(String saison) 
	{		
		final Vector<String> items = new Vector<String> ();
		
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT coloris FROM coloris__tbl WHERE saison ='" + saison + "' ORDER BY coloris");
	     try {
		     while ( rs.next() ) {
		    	 items.add(rs.getString("coloris"));
		     }
	     } 
	     catch (SQLException e) {
				e.printStackTrace();
		 } 
	     finally {
	         if (rs  != null) try { rs.close();  } catch (SQLException ignore) {}
	     }

		return items;
	}	
}