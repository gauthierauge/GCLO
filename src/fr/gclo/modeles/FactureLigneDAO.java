package fr.gclo.modeles;

import java.sql.ResultSet;

import fr.gclo.beans.Facture;
import fr.gclo.controller.DataBaseInstance;
import fr.gclo.ui.composants.StringCenter;

public class FactureLigneDAO {
	
    public boolean create(Facture Facture) 
    {
//    	String sql	= "INSERT INTO Facture(TypeFacture, NomFacture, PrenomFacture, Adresse1Facture, Adresse2Facture, CodePostalFacture, VilleFacture) VALUES ('" + 	
//    			Facture.getTypeFacture().replace("'", "''") 			+ "','" + 
//    			Facture.getNomFacture().replace("'", "''") 			+ "','" + 
//    			Facture.getPrenomFacture().replace("'", "''") 		+ "','" + 
//    			Facture.getAdresse1Facture().replace("'", "''") 	+ "','" + 
//    			Facture.getAdresse2Facture().replace("'", "''") 	+ "','" +
//    			Facture.getCodePostalFacture().replace("'", "''") 	+ "','" +
//    			Facture.getVilleFacture().replace("'", "''") 		+ "')";
  	
    	return DataBaseInstance.simpleUpdateQuery("");
    }
	   
    public boolean update(Facture Facture) 
    {
//    	String sql = "UPDATE Facture SET " +
//					 "TypeFacture 		 = '" + Facture.getTypeFacture().replace("'", "''") 		    + "', " +
//					 "NomFacture 			 = '" + Facture.getNomFacture().replace("'", "''") 		    + "', " +
//    				 "PrenomFacture 		 = '" + Facture.getPrenomFacture().replace("'", "''")		+ "', " +
//			     	 "Adresse1Facture 	 = '" + Facture.getAdresse1Facture().replace("'", "''") 	+ "', " +
//    				 "Adresse2Facture 	 = '" + Facture.getAdresse2Facture().replace("'", "''") 	+ "', " +
//    				 "CodePostalFacture 	 = '" + Facture.getCodePostalFacture().replace("'", "''") 	+ "', " +
//    				 "VilleFacture 	 	 = '" + Facture.getVilleFacture().replace("'", "''") 		+ "'  " +
//    			     "WHERE IDFacture      =  " + Facture.getIDFacture(); 
    	
    	return DataBaseInstance.simpleUpdateQuery("");
    }
	   
	public Facture read(String ID) {
		
		Facture Facture = null;
	
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT codeFacture FROM Facture__tbl WHERE CodeFacture = '" + ID + "'");
//	     try {
//		     //if ( rs.next() ) 
//		    //	 Facture = new Facture(rs.getString("CodeFacture"));//, rs.getString("CodeFacture"), rs.getString("TitreFacture"), rs.getString("RaisonSocialeFacture"), rs.getString("Adresse1Facture"), rs.getString("Adresse2Facture"), rs.getString("CodePostalFacture"), rs.getString("VilleFacture"));
//	     } 
//	     catch (SQLException e) {
//				e.printStackTrace();
//		 } 
//	     finally {
//	         if (rs  != null) try { rs.close();  } catch (SQLException ignore) {}
//	     }
	
	     return Facture;
	} 
	   
	public String delete(String ID) {
		
		boolean dummy = DataBaseInstance.simpleUpdateQuery("DELETE FROM Facture WHERE IDFacture = " + ID);
			
		return (dummy ? "" : "Erreur lors de la suppression du Facture " + ID);
	} 
    
    public static String getSQLFicheFacture(String ID) {
    	
		return "SELECT CompteurFactureLigne AS COMPTEUR, CodeFacture AS NUMERO, CodeArticle AS CODE_REF, CodeArticle AS CODE_REF, DesignationArticle AS ARTICLE FROM FactureLigne__tbl WHERE CodeFacture = '" + ID + "'";
	}
    
    public static String getSQL() {
    	
		return "SELECT CompteurFactureLigne AS COMPTEUR, CodeFacture AS NUMERO, FROM FactureLigne__tbl";
	}


    public static String getSQLEdition() {
    	
		return "SELECT IDFacture AS \"COMPTEUR-10\", NomFacture AS \"NOM-100\", PrenomFacture AS \"PRENOM-100\", Adresse1Facture AS \"ADRESSE-150\", Adresse2Facture AS \"COMP_ADRESSE-150\", CodePostalFacture AS \"CODE_POSTAL-30\", VilleFacture AS \"VILLE-50\" FROM Facture";
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
