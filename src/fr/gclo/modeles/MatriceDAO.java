package fr.gclo.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import fr.gclo.controller.DataBaseInstance;

import fr.gclo.beans.Matrice;

public class MatriceDAO {
  
	public static Vector<Matrice> getVecteurMatriceDAO() 
	{		
		final Vector<Matrice> items = new Vector<Matrice> ();
		
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT numero, modelearticle, colorisarticle, nbarticletotal, nbarticletailleunique, nbarticletailles, nbarticletaillem, nbarticletaillel, nbarticletaillexl, nbarticletaillexxl FROM Matrice_3('AUTOMNE - HIVER 2025', 'CMAW2025_1')");
	     try {
		     while ( rs.next() ) {
		    	 items.add(new Matrice(rs.getString("numero"), 
		    			 				rs.getString("modelearticle"), 
		    			 				rs.getString("colorisarticle"), 
		    			 				rs.getInt("nbarticletotal"), 
		    			 				rs.getInt("nbarticletailleunique"), 
		    			 				rs.getInt("nbarticletailles"), 
		    			 				rs.getInt("nbarticletaillem"), 
		    			 				rs.getInt("nbarticletaillel"), 
		    			 				rs.getInt("nbarticletaillexl"), 
		    			 				rs.getInt("nbarticletaillexxl")));
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