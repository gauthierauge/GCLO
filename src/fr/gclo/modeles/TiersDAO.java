package fr.gclo.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import fr.gclo.beans.Tiers;
import fr.gclo.controller.DataBaseInstance;
import fr.gclo.ui.composants.StringCenter;

public class TiersDAO {
	
    public boolean create(Tiers tiers) 
    {
//    	String sql	= "INSERT INTO Tiers(TypeTiers, NomTiers, PrenomTiers, Adresse1Tiers, Adresse2Tiers, CodePostalTiers, VilleTiers) VALUES ('" + 	
//    			tiers.getTypeTiers().replace("'", "''") 			+ "','" + 
//    			tiers.getNomTiers().replace("'", "''") 			+ "','" + 
//    			tiers.getPrenomTiers().replace("'", "''") 		+ "','" + 
//    			tiers.getAdresse1Tiers().replace("'", "''") 	+ "','" + 
//    			tiers.getAdresse2Tiers().replace("'", "''") 	+ "','" +
//    			tiers.getCodePostalTiers().replace("'", "''") 	+ "','" +
//    			tiers.getVilleTiers().replace("'", "''") 		+ "')";
  	
    	return DataBaseInstance.simpleUpdateQuery("");
    }
	   
    public boolean update(Tiers tiers) 
    {
//    	String sql = "UPDATE Tiers SET " +
//					 "TypeTiers 		 = '" + tiers.getTypeTiers().replace("'", "''") 		    + "', " +
//					 "NomTiers 			 = '" + tiers.getNomTiers().replace("'", "''") 		    + "', " +
//    				 "PrenomTiers 		 = '" + tiers.getPrenomTiers().replace("'", "''")		+ "', " +
//			     	 "Adresse1Tiers 	 = '" + tiers.getAdresse1Tiers().replace("'", "''") 	+ "', " +
//    				 "Adresse2Tiers 	 = '" + tiers.getAdresse2Tiers().replace("'", "''") 	+ "', " +
//    				 "CodePostalTiers 	 = '" + tiers.getCodePostalTiers().replace("'", "''") 	+ "', " +
//    				 "VilleTiers 	 	 = '" + tiers.getVilleTiers().replace("'", "''") 		+ "'  " +
//    			     "WHERE IDTiers      =  " + tiers.getIDTiers(); 
    	
    	return DataBaseInstance.simpleUpdateQuery("");
    }
	   
	public Tiers read(String ID) {
		
		Tiers tiers = null;
	
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT * FROM Tiers__tbl WHERE CodeTiers = '" + ID + "'");
	     try {
		     if ( rs.next() ) 
		    	 tiers = new Tiers(rs.getString("TypeTiers"), rs.getString("CodeTiers"), rs.getString("TitreTiers"), rs.getString("RaisonSocialeTiers"), 
		    			 			rs.getString("Adresse1Tiers"), 
		    			 			rs.getString("Adresse2Tiers"), rs.getString("CodePostalTiers"), rs.getString("VilleTiers"), rs.getString("PaysTiers"), 
		    			 			rs.getString("Telephone1Tiers"), rs.getString("Telephone2Tiers"), rs.getString("Telephone3Tiers"), rs.getString("InterlocuteurTiers"));
	     } 
	     catch (SQLException e) {
				e.printStackTrace();
		 } 
	     finally {
	         if (rs  != null) try { rs.close();  } catch (SQLException ignore) {}
	     }
	
	     return tiers;
	} 
	   
	public String delete(String ID) {
		
		// TODO 
		// TESTER SI LA SUPPRESSION DE L'ARTICLE EST POSSIBLE
		
		boolean dummy = DataBaseInstance.simpleUpdateQuery("DELETE FROM Tiers WHERE IDTiers = " + ID);
			
		return (dummy ? "" : "Erreur lors de la suppression du tiers " + ID);
	} 
    
    public static String getSQL() {
    	
		return "SELECT CodeTiers AS CODE, RaisonSocialeTiers AS NOM, Adresse1Tiers AS ADRESSE, Adresse2Tiers AS ADRESSE_2, codepostaltiers AS CP, villetiers AS VILLE, paystiers AS PAYS, comptetiers AS CODE_COMPTABLE FROM Tiers__tbl ORDER BY raisonsocialetiers";
	}
	   
	public int getIDTiersDivers() {
		
		int dummy = 0;
	
	     ResultSet rs = DataBaseInstance.executeQuery("SELECT IDTiers FROM Tiers WHERE NomTiers = 'DIVERS'");
	     try {
		     if ( rs.next() ) 
		    	 dummy = rs.getInt("IDTiers");
	     } 
	     catch (SQLException e) {
				e.printStackTrace();
		 } 
	     finally {
	         if (rs  != null) try { rs.close();  } catch (SQLException ignore) {}
	     }
	
	     return dummy;
	} 
	
	public static Vector<String> getVecteurCodeTiersDAO() 
	{		
		final Vector<String> items = new Vector<String> ();
		
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT RaisonSocialeTiers FROM Tiers__tbl ORDER BY RaisonSocialeTiers");
	     try {
		     while ( rs.next() ) {
		    	 items.add(rs.getString("RaisonSocialeTiers"));
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
	
	
	

    public static String getSQLEdition() {
    	
		return "SELECT IDTiers AS \"COMPTEUR-10\", NomTiers AS \"NOM-100\", PrenomTiers AS \"PRENOM-100\", Adresse1Tiers AS \"ADRESSE-150\", Adresse2Tiers AS \"COMP_ADRESSE-150\", CodePostalTiers AS \"CODE_POSTAL-30\", VilleTiers AS \"VILLE-50\" FROM Tiers";
	}
    
    @SuppressWarnings("rawtypes")
	public static Class[] getColumnClass() {
    	
		return new Class[] { StringCenter.class,  StringCenter.class, StringCenter.class, StringCenter.class, StringCenter.class, StringCenter.class, StringCenter.class, StringCenter.class };
    }
}
