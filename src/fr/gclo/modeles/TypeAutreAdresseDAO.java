package fr.gclo.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import fr.gclo.controller.DataBaseInstance;

public class TypeAutreAdresseDAO {
	   
	public static Vector<String> getVecteurTypeAutreAdresseDAO() 
	{		
		final Vector<String> items = new Vector<String> ();
		
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT libellecodefils FROM CodeFils__tbl WHERE codepere = 'TYPEAUTREADRESSE'");
	     try {
		     while ( rs.next() ) {
		    	 items.add(rs.getString("libellecodefils"));
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
