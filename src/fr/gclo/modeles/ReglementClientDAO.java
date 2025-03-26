package fr.gclo.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JCheckBox;

import fr.gclo.beans.ReglementClient;
import fr.gclo.controller.DataBaseInstance;
import fr.gclo.ui.composants.StringCenter;

public class ReglementClientDAO {
	
    public boolean create(ReglementClient ReglementClient) 
    {
//    	String sql	= "INSERT INTO ReglementClient(TypeReglementClient, NomReglementClient, PrenomReglementClient, Adresse1ReglementClient, Adresse2ReglementClient, CodePostalReglementClient, VilleReglementClient) VALUES ('" + 	
//    			ReglementClient.getTypeReglementClient().replace("'", "''") 			+ "','" + 
//    			ReglementClient.getNomReglementClient().replace("'", "''") 			+ "','" + 
//    			ReglementClient.getPrenomReglementClient().replace("'", "''") 		+ "','" + 
//    			ReglementClient.getAdresse1ReglementClient().replace("'", "''") 	+ "','" + 
//    			ReglementClient.getAdresse2ReglementClient().replace("'", "''") 	+ "','" +
//    			ReglementClient.getCodePostalReglementClient().replace("'", "''") 	+ "','" +
//    			ReglementClient.getVilleReglementClient().replace("'", "''") 		+ "')";
  	
    	return DataBaseInstance.simpleUpdateQuery("");
    }
	   
    public boolean update(ReglementClient ReglementClient) 
    {
//    	String sql = "UPDATE ReglementClient SET " +
//					 "TypeReglementClient 		 = '" + ReglementClient.getTypeReglementClient().replace("'", "''") 		    + "', " +
//					 "NomReglementClient 			 = '" + ReglementClient.getNomReglementClient().replace("'", "''") 		    + "', " +
//    				 "PrenomReglementClient 		 = '" + ReglementClient.getPrenomReglementClient().replace("'", "''")		+ "', " +
//			     	 "Adresse1ReglementClient 	 = '" + ReglementClient.getAdresse1ReglementClient().replace("'", "''") 	+ "', " +
//    				 "Adresse2ReglementClient 	 = '" + ReglementClient.getAdresse2ReglementClient().replace("'", "''") 	+ "', " +
//    				 "CodePostalReglementClient 	 = '" + ReglementClient.getCodePostalReglementClient().replace("'", "''") 	+ "', " +
//    				 "VilleReglementClient 	 	 = '" + ReglementClient.getVilleReglementClient().replace("'", "''") 		+ "'  " +
//    			     "WHERE IDReglementClient      =  " + ReglementClient.getIDReglementClient(); 
    	
    	return DataBaseInstance.simpleUpdateQuery("");
    }
	   
	public ReglementClient read(String ID) {
		
		ReglementClient ReglementClient = null;
	
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT compteurreglementclient, datereglementclient, libellereglementclient, saison FROM ReglementClient__tbl WHERE CompteurReglementClient = '" + ID + "'");
	     try {
		     if ( rs.next() ) 
		    	 ReglementClient = new ReglementClient(rs.getInt("CompteurReglementClient"), rs.getDate("datereglementclient"), rs.getString("libellereglementclient"), rs.getString("saison"));//, rs.getString("CodeReglementClient"), rs.getString("TitreReglementClient"), rs.getString("RaisonSocialeReglementClient"), rs.getString("Adresse1ReglementClient"), rs.getString("Adresse2ReglementClient"), rs.getString("CodePostalReglementClient"), rs.getString("VilleReglementClient"));
	     } 
	     catch (SQLException e) {
				e.printStackTrace();
		 } 
	     finally {
	         if (rs  != null) try { rs.close();  } catch (SQLException ignore) {}
	     }
	
	     return ReglementClient;
	} 
	   
	public String delete(String ID) {
		
		boolean dummy = DataBaseInstance.simpleUpdateQuery("DELETE FROM ReglementClient WHERE IDReglementClient = " + ID);
			
		return (dummy ? "" : "Erreur lors de la suppression du ReglementClient " + ID);
	} 
    
    public static String getSQLFicheTiers(String ID) {
    	
		return "SELECT CompteurReglementClient AS NUMERO, typedereglementclient AS TYPE, ModeDeReglementClient AS MODE_DE_REGLEMENT, DateReglementClient AS DATE_DE_RECEPTION, CommentaireReglementClient AS COMMENTAIRES, MontantReglementClient AS MONTANT, DateEcheancereglement AS DATE_ECHEANCE, CASE WHEN EncaisseReglement THEN True ELSE False END AS ENCAISSE FROM ReglementClient__tbl WHERE CodeTiers = '" + ID + "'";
	}
    
    public static String getSQL() {
    	
		return "SELECT CodeReglementClient AS NUMERO, LibelleReglementClient AS LIBELLE, DateReglementClient AS DATE, Raisonsocialetiers AS CLIENT, LibelleEtat AS STATUT, VilleTiers AS VILLE FROM ReglementClientetatobjet";
	}


    public static String getSQLEdition() {
    	
		return "SELECT IDReglementClient AS \"COMPTEUR-10\", NomReglementClient AS \"NOM-100\", PrenomReglementClient AS \"PRENOM-100\", Adresse1ReglementClient AS \"ADRESSE-150\", Adresse2ReglementClient AS \"COMP_ADRESSE-150\", CodePostalReglementClient AS \"CODE_POSTAL-30\", VilleReglementClient AS \"VILLE-50\" FROM ReglementClient";
	}
    
    @SuppressWarnings("rawtypes")
	public static Class[] getColumnClass() {
    	
		return new Class[] { StringCenter.class,  String.class, StringCenter.class, StringCenter.class, StringCenter.class, StringCenter.class };
    }
       
    @SuppressWarnings("rawtypes")
	public static Class[] getColumnClassPourFicheClient()
    {
		return new Class[] {StringCenter.class,  StringCenter.class, StringCenter.class, Date.class, String.class, Float.class, Date.class, JCheckBox.class};
    }


}
