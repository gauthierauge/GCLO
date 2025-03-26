package fr.gclo.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import fr.gclo.beans.Commande;
import fr.gclo.beans.CommandeLigne;
import fr.gclo.controller.DataBaseInstance;
import fr.gclo.ui.composants.StringCenter;

public class CommandeLigneDAO {
	
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
	   
	public CommandeLigne read(String ID) {
		
		CommandeLigne commandeLigne = null;
	
		String strSQL = "SELECT compteurcommandeligne, codecommande, codearticle, colorisarticle, modelearticle, designationarticle, prixdeventehtarticle, "
				     + "tauxtvaarticle, prixdeventettcarticle, nbarticletailles, nbarticletaillem, nbarticletaillel, nbarticle, montanttotalht, montanttotaltva, "
				     + "montanttotalttc, datecreationcommandeligne, datedernieremodificationcommandeligne, nbarticletailleslivre, nbarticletaillemlivre, "
				     + "nbarticletaillellivre, nbarticlelivre, nbarticletaillexl, nbarticletaillexllivre, nbarticletailleunique, nbarticletailleuniquelivre, "
				     + "nbarticletaillexxl, nbarticletaillexxllivre "
				     + "FROM CommandeLigne__tbl "
				     + "WHERE compteurcommandeligne = " + ID;

	     ResultSet rs =  DataBaseInstance.executeQuery(strSQL);
	     try {
		     if ( rs.next() ) 
		    	 commandeLigne = new CommandeLigne(rs.getInt("compteurcommandeligne"), 
		    			 						   rs.getString("CodeCommande"), 
		    			 						   rs.getString("codearticle"),
		    			 						   rs.getString("colorisarticle"),
		    			 						   rs.getString("modelearticle"),
		    			 						   rs.getString("designationarticle"),
							 					   rs.getInt("nbarticletailleunique"),
							 					   rs.getInt("nbarticletailles"),
							 					   rs.getInt("nbarticletaillem"),
							 					   rs.getInt("nbarticletaillel"),
							 					   rs.getInt("nbarticletaillexl"),
							 					   rs.getInt("nbarticletaillexxl"),
							 					   rs.getFloat("prixdeventehtarticle"),
							 					   rs.getFloat("montanttotalht")
							 					   
		    			 						   );
	     } 
	     catch (SQLException e) {
				e.printStackTrace();
		 } 
	     finally {
	         if (rs  != null) try { rs.close();  } catch (SQLException ignore) {}
	     }
	
	     return commandeLigne;
	} 
	   
	public String delete(String ID) {
		
		boolean dummy = DataBaseInstance.simpleUpdateQuery("DELETE FROM Commande WHERE IDCommande = " + ID);
			
		return (dummy ? "" : "Erreur lors de la suppression du Commande " + ID);
	} 
    
    public static String getSQLFicheCommande(String ID) {
    	
		return "SELECT CompteurCommandeLigne AS COMPTEUR, CodeArticle AS CODE_REF, ModeleArticle AS ARTICLE, ColorisArticle AS COLORIS FROM CommandeLigne__tbl WHERE CodeCommande = '" + ID + "'";
	}
    
    public static String getSQL() {
    	
		return "SELECT CompteurCommandeLigne AS COMPTEUR, CodeCommande AS NUMERO, FROM CommandeLigne__tbl";
	}


    public static String getSQLEdition() {
    	
		return "SELECT IDCommande AS \"COMPTEUR-10\", NomCommande AS \"NOM-100\", PrenomCommande AS \"PRENOM-100\", Adresse1Commande AS \"ADRESSE-150\", Adresse2Commande AS \"COMP_ADRESSE-150\", CodePostalCommande AS \"CODE_POSTAL-30\", VilleCommande AS \"VILLE-50\" FROM Commande";
	}
    
    
	public Vector<String[]> getCommandeLignes(String ID) {
		
		String sql = "SELECT CompteurCommandeLigne, CodeCommande, CodeArticle FROM CommandeLigne__tbl WHERE codecommande = '" + ID + "'";

		Vector<String[]> toto = new Vector<String[]>();
		
		ResultSet rs = DataBaseInstance.executeQuery(sql);
		try {
		    while ( rs.next() ) {
		    	String dummy[] = {Integer.toString(rs.getInt("CompteurCommandeLigne")), rs.getString("CodeCommande"), rs.getString("CodeArticle")};
		    	toto.add(dummy);
		    }
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		finally {
	        if (rs  != null) try { rs.close();  } catch (SQLException ignore) {}
	   	}
		
		return toto;
	}

	public static Vector<CommandeLigne> getVecteurCommandeLigneEditionDAO(String ID) 
	{		
		final Vector<CommandeLigne> items = new Vector<CommandeLigne> ();
		
		String sql = "SELECT CompteurCommandeLigne, Commande__tbl.CodeCommande, CodeArticle, CommandeLigne__tbl.ColorisArticle, ModeleArticle, DesignationArticle, "
				+ "nbarticletailleunique, nbarticletailles, nbarticletaillem, nbarticletaillel, nbarticletaillexl, nbarticletaillexxl, "
				+ "prixdeventehtarticle, montanttotalht, composition "
				+ "FROM Commande__tbl, CommandeLigne__tbl, Coloris__tbl "
				+ "WHERE Commande__tbl.codecommande = CommandeLigne__tbl.codecommande "
				+ "AND CommandeLigne__tbl.ColorisArticle = Coloris__tbl.coloris "
				+ "AND Commande__tbl.saisoncommande = Coloris__tbl.saison "
				+ "AND Commande__tbl.codecommande = '" + ID + "'";
		ResultSet rs =  DataBaseInstance.executeQuery(sql);
	     try {
		     while ( rs.next() ) {
		    	 items.add(new CommandeLigne(rs.getInt("CompteurCommandeLigne"), 
					 						rs.getString("CodeCommande"), 
					 						rs.getString("CodeArticle"), 
					 						rs.getString("ColorisArticle"), 
					 						rs.getString("ModeleArticle"), 
					 						rs.getString("DesignationArticle"),
					 						rs.getInt("nbarticletailleunique"),
						 					   rs.getInt("nbarticletailles"),
						 					   rs.getInt("nbarticletaillem"),
						 					   rs.getInt("nbarticletaillel"),
						 					   rs.getInt("nbarticletaillexl"),
						 					   rs.getInt("nbarticletaillexxl"),
						 					   rs.getFloat("prixdeventehtarticle"),
						 					   rs.getFloat("montanttotalht"),
						 					   rs.getString("composition")
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
