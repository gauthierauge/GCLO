package fr.gclo.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.gclo.beans.Societe;
import fr.gclo.controller.DataBaseInstance;
import fr.gclo.ui.composants.StringCenter;

public class SocieteDAO {
	
    public boolean create(Societe Societe) 
    {
  	
    	return DataBaseInstance.simpleUpdateQuery("");
    }
	   
    public boolean update(Societe Societe) 
    {
    	
    	return DataBaseInstance.simpleUpdateQuery("");
    }
	   
	public Societe read(String ID) {
		
		Societe Societe = null;
	
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT compteursociete, titresociete, nomsociete, adresse1societe, adresse2societe, codepostalsociete, villesociete, payssociete, telephonesociete, adressedemessageriesociete, sitesociete FROM Societe__tbl WHERE compteursociete = '" + ID + "'");
	     try {
		     if ( rs.next() ) 
		    	 Societe = new Societe(rs.getInt("compteursociete"), rs.getString("titresociete"), rs.getString("nomsociete"), rs.getString("adresse1societe"), rs.getString("adresse2societe"), rs.getString("codepostalsociete"), rs.getString("villesociete"), rs.getString("payssociete"), rs.getString("telephonesociete"), rs.getString("adressedemessageriesociete"), rs.getString("sitesociete"));
	     } 
	     catch (SQLException e) {
				e.printStackTrace();
		 } 
	     finally {
	         if (rs  != null) try { rs.close();  } catch (SQLException ignore) {}
	     }
	
	     return Societe;
	} 
	   
	public String delete(String ID) {
		
		boolean dummy = DataBaseInstance.simpleUpdateQuery("DELETE FROM Societe WHERE IDSociete = " + ID);
			
		return (dummy ? "" : "Erreur lors de la suppression du Societe " + ID);
	} 
   
    public static String getSQL() {
    	
		return "SELECT SaisonSociete AS SAISON, CodeSociete AS NUMERO, ModeleSociete AS MODELE, ColorisSociete AS COULEUR, DesignationSociete AS DESCRIPTION, prixdeventehtSociete AS PRIX_HT FROM Societe__tbl";
	}
    
    @SuppressWarnings("rawtypes")
	public static Class[] getColumnClass() {
    	
		return new Class[] { StringCenter.class,  StringCenter.class, StringCenter.class, StringCenter.class, StringCenter.class, StringCenter.class };
    }

}
