package fr.gclo.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import fr.gclo.controller.DataBaseInstance;

public class PaysDAO {
	   
	public static Vector<String> getVecteurPaysDAO() 
	{		
		final Vector<String> items = new Vector<String> ();
		
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT pays FROM Pays__tbl");
	     try {
		     while ( rs.next() ) {
		    	 items.add(rs.getString("pays"));
		     }
	     } 
	     catch (SQLException e) {
				e.printStackTrace();
		 } 
	     finally {
	         if (rs  != null) try { rs.close();  } catch (SQLException ignore) {}
	     }

		
//    	items.addElement(Environnement.TYPE_REGLEMENT_A_RECEPTION);
//    	items.addElement(Environnement.TYPE_REGLEMENT_AU);
//    	items.addElement(Environnement.TYPE_REGLEMENT_CHEQUE);
//    	items.addElement(Environnement.TYPE_REGLEMENT_DROITS);
//    	items.addElement(Environnement.TYPE_REGLEMENT_ESPECE);
//    	items.addElement(Environnement.TYPE_REGLEMENT_PAYPAL);
//    	items.addElement(Environnement.TYPE_REGLEMENT_VIREMENT);
		
		return items;
	}	
}