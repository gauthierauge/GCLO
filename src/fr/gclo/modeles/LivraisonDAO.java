package fr.gclo.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.gclo.beans.Livraison;
import fr.gclo.controller.DataBaseInstance;
import fr.gclo.ui.composants.StringCenter;

public class LivraisonDAO {
	
    public boolean create(Livraison Livraison) 
    {
//    	String sql	= "INSERT INTO Livraison(TypeLivraison, NomLivraison, PrenomLivraison, Adresse1Livraison, Adresse2Livraison, CodePostalLivraison, VilleLivraison) VALUES ('" + 	
//    			Livraison.getTypeLivraison().replace("'", "''") 			+ "','" + 
//    			Livraison.getNomLivraison().replace("'", "''") 			+ "','" + 
//    			Livraison.getPrenomLivraison().replace("'", "''") 		+ "','" + 
//    			Livraison.getAdresse1Livraison().replace("'", "''") 	+ "','" + 
//    			Livraison.getAdresse2Livraison().replace("'", "''") 	+ "','" +
//    			Livraison.getCodePostalLivraison().replace("'", "''") 	+ "','" +
//    			Livraison.getVilleLivraison().replace("'", "''") 		+ "')";
  	
    	return DataBaseInstance.simpleUpdateQuery("");
    }
	   
    public boolean update(Livraison Livraison) 
    {
//    	String sql = "UPDATE Livraison SET " +
//					 "TypeLivraison 		 = '" + Livraison.getTypeLivraison().replace("'", "''") 		    + "', " +
//					 "NomLivraison 			 = '" + Livraison.getNomLivraison().replace("'", "''") 		    + "', " +
//    				 "PrenomLivraison 		 = '" + Livraison.getPrenomLivraison().replace("'", "''")		+ "', " +
//			     	 "Adresse1Livraison 	 = '" + Livraison.getAdresse1Livraison().replace("'", "''") 	+ "', " +
//    				 "Adresse2Livraison 	 = '" + Livraison.getAdresse2Livraison().replace("'", "''") 	+ "', " +
//    				 "CodePostalLivraison 	 = '" + Livraison.getCodePostalLivraison().replace("'", "''") 	+ "', " +
//    				 "VilleLivraison 	 	 = '" + Livraison.getVilleLivraison().replace("'", "''") 		+ "'  " +
//    			     "WHERE IDLivraison      =  " + Livraison.getIDLivraison(); 
    	
    	return DataBaseInstance.simpleUpdateQuery("");
    }
	   
	public Livraison read(String ID) {
		
		Livraison Livraison = null;
	
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT * FROM Livraison__tbl WHERE CodeLivraison = '" + ID + "'");
	     try {
		     if ( rs.next() ) 
		    	 Livraison = new Livraison(rs.getString("CodeLivraison"));//, rs.getString("CodeLivraison"), rs.getString("TitreLivraison"), rs.getString("RaisonSocialeLivraison"), rs.getString("Adresse1Livraison"), rs.getString("Adresse2Livraison"), rs.getString("CodePostalLivraison"), rs.getString("VilleLivraison"));
	     } 
	     catch (SQLException e) {
				e.printStackTrace();
		 } 
	     finally {
	         if (rs  != null) try { rs.close();  } catch (SQLException ignore) {}
	     }
	
	     return Livraison;
	} 
	   
	public String delete(String ID) {
		
		boolean dummy = DataBaseInstance.simpleUpdateQuery("DELETE FROM Livraison WHERE IDLivraison = " + ID);
			
		return (dummy ? "" : "Erreur lors de la suppression du Livraison " + ID);
	} 
    
    public static String getSQLFicheTiers(String ID) {
    	
		return "SELECT CodeLivraison AS NUMERO, LibelleLivraison AS LIBELLE, DateLivraison AS DATE, Raisonsocialetiers AS CLIENT, LibelleEtat AS STATUT, VilleTiers AS VILLE FROM Livraisonetatobjet WHERE CodeTiers = '" + ID + "'";
	}
    
    public static String getSQL() {
    	
		return "SELECT CodeLivraison AS NUMERO, LibelleLivraison AS LIBELLE, DateLivraison AS DATE, Raisonsocialetiers AS CLIENT, LibelleEtat AS STATUT, VilleTiers AS VILLE FROM Livraisonetatobjet";
	}


    public static String getSQLEdition() {
    	
		return "SELECT IDLivraison AS \"COMPTEUR-10\", NomLivraison AS \"NOM-100\", PrenomLivraison AS \"PRENOM-100\", Adresse1Livraison AS \"ADRESSE-150\", Adresse2Livraison AS \"COMP_ADRESSE-150\", CodePostalLivraison AS \"CODE_POSTAL-30\", VilleLivraison AS \"VILLE-50\" FROM Livraison";
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


}
