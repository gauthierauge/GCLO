package fr.gclo.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import fr.gclo.beans.Commande;
import fr.gclo.controller.DataBaseInstance;
import fr.gclo.ui.composants.StringCenter;
import fr.gclo.beans.CommandeEdition;

public class CommandeDAO {
	
    public boolean create(Commande Commande) 
    {
//    	String sql	= "INSERT INTO Commande(TypeCommande, NomCommande, PrenomCommande, Adresse1Commande, Adresse2Commande, CodePostalCommande, VilleCommande) VALUES ('" + 	
//    			Commande.getTypeCommande().replace("'", "''") 			+ "','" + 
//    			Commande.getNomCommande().replace("'", "''") 			+ "','" + 
//    			Commande.getPrenomCommande().replace("'", "''") 		+ "','" + 
//    			Commande.getAdresse1Commande().replace("'", "''") 	+ "','" + 
//    			Commande.getAdresse2Commande().replace("'", "''") 	+ "','" +
//    			Commande.getCodePostalCommande().replace("'", "''") 	+ "','" +
//    			Commande.getVilleCommande().replace("'", "''") 		+ "')";
  	
    	return DataBaseInstance.simpleUpdateQuery("");
    }
	   
    public boolean update(Commande Commande) 
    {
//    	String sql = "UPDATE Commande SET " +
//					 "TypeCommande 		 = '" + Commande.getTypeCommande().replace("'", "''") 		    + "', " +
//					 "NomCommande 			 = '" + Commande.getNomCommande().replace("'", "''") 		    + "', " +
//    				 "PrenomCommande 		 = '" + Commande.getPrenomCommande().replace("'", "''")		+ "', " +
//			     	 "Adresse1Commande 	 = '" + Commande.getAdresse1Commande().replace("'", "''") 	+ "', " +
//    				 "Adresse2Commande 	 = '" + Commande.getAdresse2Commande().replace("'", "''") 	+ "', " +
//    				 "CodePostalCommande 	 = '" + Commande.getCodePostalCommande().replace("'", "''") 	+ "', " +
//    				 "VilleCommande 	 	 = '" + Commande.getVilleCommande().replace("'", "''") 		+ "'  " +
//    			     "WHERE IDCommande      =  " + Commande.getIDCommande(); 
    	
    	return DataBaseInstance.simpleUpdateQuery("");
    }
	   
	public Commande read(String ID) {
		
		Commande Commande = null;
	
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT codecommande, raisonsocialetiers, adresse1tiers, adresse2tiers, codepostaltiers, villetiers, paystiers, saisoncommande, nbarticlecommande, montantbruthtcommande FROM Commande__tbl WHERE CodeCommande = '" + ID + "'");
	     try {
		     if ( rs.next() ) 
		    	 Commande = new Commande(rs.getString("CodeCommande"), 
		    			 				 rs.getString("raisonsocialetiers"), 
		    			 				 rs.getString("adresse1tiers"), 
		    			 				 rs.getString("adresse2tiers"), 
		    			 				 rs.getString("CodePostalTiers"), 
		    			 				 rs.getString("VilleTiers"), 
		    			 				 rs.getString("PaysTiers"), 
		    			 				 rs.getString("SaisonCommande"), 
		    			 				 rs.getInt("nbarticlecommande"), 
		    			 				 rs.getFloat("montantbruthtcommande"));
	     } 
	     catch (SQLException e) {
				e.printStackTrace();
		 } 
	     finally {
	         if (rs  != null) try { rs.close();  } catch (SQLException ignore) {}
	     }
	
	     return Commande;
	} 
	   
	public String delete(String ID) {
		
		boolean dummy = DataBaseInstance.simpleUpdateQuery("DELETE FROM Commande WHERE IDCommande = " + ID);
			
		return (dummy ? "" : "Erreur lors de la suppression du Commande " + ID);
	} 
		   
	public static Vector<String> getVecteurCodeCommandeDAO() 
	{		
		final Vector<String> items = new Vector<String> ();
		
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT codecommande FROM Commande__tbl ORDER BY codecommande");
	     try {
		     while ( rs.next() ) {
		    	 items.add(rs.getString("codecommande"));
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

    
    public static String getSQLFicheTiers(String ID) {
    	
		return "SELECT CodeCommande AS NUMERO, LibelleCommande AS LIBELLE, DateCommande AS DATE, Raisonsocialetiers AS CLIENT, LibelleEtat AS STATUT, VilleTiers AS VILLE FROM commandeetatobjet WHERE CodeTiers = '" + ID + "'";
	}
    
    public static String getSQL() {
    	
		return "SELECT CodeCommande AS NUMERO, LibelleCommande AS LIBELLE, DateCommande AS DATE, Raisonsocialetiers AS CLIENT, LibelleEtat AS STATUT, VilleTiers AS VILLE FROM commandeetatobjet ORDER BY Codecommande DESC";
	}


    public static String getSQLEdition() {
    	
		return "SELECT IDCommande AS \"COMPTEUR-10\", NomCommande AS \"NOM-100\", PrenomCommande AS \"PRENOM-100\", Adresse1Commande AS \"ADRESSE-150\", Adresse2Commande AS \"COMP_ADRESSE-150\", CodePostalCommande AS \"CODE_POSTAL-30\", VilleCommande AS \"VILLE-50\" FROM Commande";
	}
    
    @SuppressWarnings("rawtypes")
	public static Class[] getColumnClass() {
    	
		return new Class[] { StringCenter.class,  String.class, StringCenter.class, StringCenter.class, StringCenter.class, StringCenter.class };
    }
       
    @SuppressWarnings("rawtypes")
	public static Class[] getColumnClassPourFicheClient()
    {
		return new Class[] {StringCenter.class,  String.class, StringCenter.class, StringCenter.class, StringCenter.class, StringCenter.class};
    }

	public static Vector<CommandeEdition> getVecteurCommandeEditionDAO() 
	{		
		final Vector<CommandeEdition> items = new Vector<CommandeEdition> ();
		
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT nomsociete, adresse1societe, adresse2societe, codepostalsociete, villesociete, telephonesociete, "
	     											 + "adressedemessageriesociete, sitesociete, raisonsocialetiers, adresse1tiers, "
	     											 + "adresse2tiers, codepostaltiers, villetiers, paystiers, "
	     											 + "codecommande, saisoncommande, datecommande FROM public.commande__tbl, public.societe__tbl WHERE codecommande = '000152'");
	     try {
		     while ( rs.next() ) {
		    	 items.add(new CommandeEdition(	rs.getString("nomsociete"), 
						 						rs.getString("adresse1societe"), 
						 						rs.getString("adresse2societe"), 
						 						rs.getString("codepostalsociete"), 
						 						rs.getString("villesociete"), 
						 						rs.getString("telephonesociete"), 
						 						rs.getString("telephonesociete"), 
						 						rs.getString("adressedemessageriesociete"), 
						 						rs.getString("sitesociete"), 
	    			 							rs.getString("raisonsocialetiers"),
	    			 							rs.getString("adresse1tiers"),
	    			 							rs.getString("adresse2tiers"),
	    			 							rs.getString("codepostaltiers"),
	    			 							rs.getString("villetiers"),
	    			 							rs.getString("paystiers"),
	    			 							rs.getString("codecommande"),
	    			 							rs.getString("saisoncommande"),
	    			 							rs.getDate("datecommande")
	    			 							));
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
