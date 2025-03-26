package fr.gclo.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import fr.gclo.controller.DataBaseInstance;

public class ModeleDAO {
	   
	public static Vector<String> getVecteurModeleDAO(String saison) 
	{		
		final Vector<String> items = new Vector<String> ();
		
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT modele FROM modele__tbl WHERE saison ='" + saison + "' ORDER BY modele");
	     try {
		     while ( rs.next() ) {
		    	 items.add(rs.getString("modele"));
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
