package fr.gclo.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import fr.gclo.controller.DataBaseInstance;

public class SaisonDAO {
	   
	public static Vector<String> getVecteurSaisonDAO() 
	{		
		final Vector<String> items = new Vector<String> ();
		
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT saison FROM Saison__tbl");
	     try {
		     while ( rs.next() ) {
		    	 items.add(rs.getString("saison"));
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
	
	public static String getSQL() {
    	
		return "SELECT saison FROM Saison__tbl";
	}
}
