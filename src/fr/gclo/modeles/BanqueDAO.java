package fr.gclo.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import fr.gclo.controller.DataBaseInstance;

public class BanqueDAO {
	   
	public static Vector<String> getVecteurCodeBanqueDAO() 
	{		
		final Vector<String> items = new Vector<String> ();
		
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT codebanque FROM Banque__tbl ORDER BY codebanque");
	     try {
		     while ( rs.next() ) {
		    	 items.add(rs.getString("codebanque"));
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
