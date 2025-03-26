package fr.gclo.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.gclo.beans.ReglementClient;
import fr.gclo.beans.TiersAutreAdresse;
import fr.gclo.controller.DataBaseInstance;
import fr.gclo.ui.composants.StringCenter;

public class TiersAutreAdresseDAO {
	
    public boolean create(TiersAutreAdresse tiersautreadresse) 
    {
    	String sql	= "INSERT INTO ReglementClient(TypeAdresseTiersAutreAdresse, RaisonSocialeTiersAutreAdresse, Adresse1TiersAutreAdresse, Adresse2TiersAutreAdresse, CodePostalTiersAutreAdresse, VilleTiersAutreAdresse, PaysTiersAutreAdresse) VALUES ('" + 	
    			tiersautreadresse.getTypeAdresseTiersAutreAdresse().replace("'", "''") 			+ "','" + 
    			tiersautreadresse.getRaisonSocialeTiersAutreAdresse().replace("'", "''") 			+ "','" + 
    			tiersautreadresse.getAdresse1TiersAutreAdresse().replace("'", "''") 		+ "','" + 
    			tiersautreadresse.getAdresse2TiersAutreAdresse().replace("'", "''") 	+ "','" + 
    			tiersautreadresse.getCodePostalTiersAutreAdresse().replace("'", "''") 	+ "','" +
    			tiersautreadresse.getVilleTiersAutreAdresse().replace("'", "''") 	+ "','" +
    			tiersautreadresse.getPaysTiersAutreAdresse().replace("'", "''") 		+ "')";
  	
    	return DataBaseInstance.simpleUpdateQuery(sql);
    }
	   
    public boolean update(TiersAutreAdresse tiersautreadresse) 
    {
    	String sql = "UPDATE TiersAutreAdresse__tbl SET " +
					 "TypeAdresseTiersAutreAdresse 	 = '" + tiersautreadresse.getTypeAdresseTiersAutreAdresse().replace("'", "''") 		+ "', " +
					 "RaisonSocialeTiersAutreAdresse = '" + tiersautreadresse.getRaisonSocialeTiersAutreAdresse().replace("'", "''")	+ "', " +
    				 "Adresse1TiersAutreAdresse 	 = '" + tiersautreadresse.getAdresse1TiersAutreAdresse().replace("'", "''")			+ "', " +
			     	 "Adresse2TiersAutreAdresse 	 = '" + tiersautreadresse.getAdresse2TiersAutreAdresse().replace("'", "''") 		+ "', " +
    				 "CodePostalTiersAutreAdresse 	 = '" + tiersautreadresse.getCodePostalTiersAutreAdresse().replace("'", "''") 		+ "', " +
		 			 "VilleTiersAutreAdresse 	 	 = '" + tiersautreadresse.getVilleTiersAutreAdresse().replace("'", "''") 			+ "'  " +
		 			 "PaysTiersAutreAdresse 	 	 = '" + tiersautreadresse.getPaysTiersAutreAdresse().replace("'", "''") 			+ "'  " +
    			     "WHERE CompteurTiersAutreAdresse =  " + tiersautreadresse.getCompteurTiersAutreAdresse(); 
    	
    	return DataBaseInstance.simpleUpdateQuery(sql);
    }
	   
	public TiersAutreAdresse read(int ID) {
		
		TiersAutreAdresse tiersautreadresse = null;
	
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT * FROM TiersAutreAdresse__tbl WHERE CompteurTiersAutreAdresse = " + ID);
	     try {
		     if ( rs.next() ) 
		    	 tiersautreadresse = new TiersAutreAdresse(rs.getInt("CompteurTiersAutreAdresse"),
						   								   rs.getString("TypeAdresseTiersAutreAdresse"), 
						   								   rs.getString("CodeTiers"), 
														   rs.getString("RaisonSocialeTiersAutreAdresse"), 
														   rs.getString("Adresse1TiersAutreAdresse"), 
														   rs.getString("Adresse2TiersAutreAdresse"), 
														   rs.getString("CodePostalTiersAutreAdresse"), 
														   rs.getString("VilleTiersAutreAdresse"), 
														   rs.getString("PaysTiersAutreAdresse"));
	     } 
	     catch (SQLException e) {
				e.printStackTrace();
		 } 
	     finally {
	         if (rs  != null) try { rs.close();  } catch (SQLException ignore) {}
	     }
	
	     return tiersautreadresse;
	} 
	   
	public String delete(String ID) {
		
		boolean dummy = DataBaseInstance.simpleUpdateQuery("DELETE FROM ReglementClient WHERE IDReglementClient = " + ID);
			
		return (dummy ? "" : "Erreur lors de la suppression du ReglementClient " + ID);
	} 
    
    public static String getSQLFicheTiers(String ID) {
    	
		return "SELECT CompteurTiersAutreAdresse AS COMPTEUR, TypeAdresseTiersAutreAdresse AS TYPE, RaisonSocialeTiersAutreAdresse AS RAISON_SOCIALE,  Adresse1TiersAutreAdresse AS ADRESSE_1, Adresse2TiersAutreAdresse AS ADRESSE_2, CodePostalTiersAutreAdresse AS CODE_POSTAL,  VilleTiersAutreAdresse AS VILLE, PaysTiersAutreAdresse AS PAYS FROM TiersAutreAdresse__tbl WHERE CodeTiers = '" + ID + "'";
	}
    
    public static String getSQL() {
    	
		return "SELECT CompteurTiersAutreAdresse AS COMPTEUR, TypeAdresseTiersAutreAdresse AS TYPE, RaisonSocialeTiersAutreAdresse AS RAISON_SOCIALE,  Adresse1TiersAutreAdresse AS ADRESSE_1, Adresse2TiersAutreAdresse AS ADRESSE_2, CodePostalTiersAutreAdresse AS CODE_POSTAL,  VilleTiersAutreAdresse AS VILLE, PaysTiersAutreAdresse AS PAYS FROM TiersAutreAdresse__tbl";
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
		return new Class[] {StringCenter.class,  String.class, StringCenter.class, StringCenter.class, StringCenter.class, StringCenter.class, String.class, StringCenter.class};
    }


}
