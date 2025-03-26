package fr.gclo.modeles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import fr.gclo.beans.Article;
import fr.gclo.controller.DataBaseInstance;
import fr.gclo.ui.composants.StringCenter;

public class ArticleDAO {
	
    public boolean create(Article Article) 
    {
//    	String sql	= "INSERT INTO Article(TypeArticle, NomArticle, PrenomArticle, Adresse1Article, Adresse2Article, CodePostalArticle, VilleArticle) VALUES ('" + 	
//    			Article.getTypeArticle().replace("'", "''") 			+ "','" + 
//    			Article.getNomArticle().replace("'", "''") 			+ "','" + 
//    			Article.getPrenomArticle().replace("'", "''") 		+ "','" + 
//    			Article.getAdresse1Article().replace("'", "''") 	+ "','" + 
//    			Article.getAdresse2Article().replace("'", "''") 	+ "','" +
//    			Article.getCodePostalArticle().replace("'", "''") 	+ "','" +
//    			Article.getVilleArticle().replace("'", "''") 		+ "')";
  	
    	return DataBaseInstance.simpleUpdateQuery("");
    }
	   
    public boolean update(Article Article) 
    {
//    	String sql = "UPDATE Article SET " +
//					 "TypeArticle 		 = '" + Article.getTypeArticle().replace("'", "''") 		    + "', " +
//					 "NomArticle 			 = '" + Article.getNomArticle().replace("'", "''") 		    + "', " +
//    				 "PrenomArticle 		 = '" + Article.getPrenomArticle().replace("'", "''")		+ "', " +
//			     	 "Adresse1Article 	 = '" + Article.getAdresse1Article().replace("'", "''") 	+ "', " +
//    				 "Adresse2Article 	 = '" + Article.getAdresse2Article().replace("'", "''") 	+ "', " +
//    				 "CodePostalArticle 	 = '" + Article.getCodePostalArticle().replace("'", "''") 	+ "', " +
//    				 "VilleArticle 	 	 = '" + Article.getVilleArticle().replace("'", "''") 		+ "'  " +
//    			     "WHERE IDArticle      =  " + Article.getIDArticle(); 
    	
    	return DataBaseInstance.simpleUpdateQuery("");
    }
	   
	public Article read(String ID) {
		
		Article Article = null;
	
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT codeArticle, modelearticle, designationarticle, saisonarticle, colorisarticle, prixdeventehtarticle, tauxtvaarticle, prixdeventettcarticle, poidsarticle, numeroataarticle, numeromodele FROM Article__tbl WHERE CodeArticle = '" + ID + "'");
	     try {
		     if ( rs.next() ) 
		    	 Article = new Article(rs.getString("CodeArticle"),
		    			 			   rs.getString("modelearticle"),
		    			 			   rs.getString("designationarticle"),
		    			 			   rs.getString("saisonarticle"),
		    			 			   rs.getString("colorisarticle"),
		    			 			   rs.getFloat("prixdeventehtarticle"),
		    			 			   rs.getFloat("tauxtvaarticle"),
		    			 			   rs.getFloat("prixdeventettcarticle"),
		    			 			   rs.getFloat("poidsarticle"),
		    			 			   rs.getInt("numeroataarticle"),
		    			 			   rs.getInt("numeromodele")
		    			 			
		    			 );
	     } 
	     catch (SQLException e) {
				e.printStackTrace();
		 } 
	     finally {
	         if (rs  != null) try { rs.close();  } catch (SQLException ignore) {}
	     }
	
	     return Article;
	} 
	   
	public String delete(String ID) {
		
		boolean dummy = DataBaseInstance.simpleUpdateQuery("DELETE FROM Article WHERE IDArticle = " + ID);
			
		return (dummy ? "" : "Erreur lors de la suppression du Article " + ID);
	} 
	   
	public static Vector<String> getVecteurCodeArticleDAO() 
	{		
		final Vector<String> items = new Vector<String> ();
		
	     ResultSet rs =  DataBaseInstance.executeQuery("SELECT codearticle FROM Article__tbl");
	     try {
		     while ( rs.next() ) {
		    	 items.add(rs.getString("codearticle"));
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
   
    public static String getSQL() {
    	
		return "SELECT CodeArticle AS COMPTEUR, SaisonArticle AS SAISON, CodeArticle AS NUMERO, ModeleArticle AS MODELE, ColorisArticle AS COULEUR, DesignationArticle AS DESCRIPTION, prixdeventehtarticle AS PRIX_HT FROM Article__tbl";
	}
    
	public Vector<String[]> getArticles() {
		
		String sql = "SELECT SaisonArticle, CodeArticle, ModeleArticle, ColorisArticle, DesignationArticle, prixdeventehtarticle FROM Article__tbl";

		Vector<String[]> toto = new Vector<String[]>();
		
		ResultSet rs = DataBaseInstance.executeQuery(sql);
		try {
		    while ( rs.next() ) {
		    	String dummy[] = {rs.getString("SaisonArticle"), rs.getString("CodeArticle"), rs.getString("ModeleArticle"), rs.getString("ColorisArticle"), rs.getString("DesignationArticle"), Float.toString(rs.getFloat("prixdeventehtarticle"))};
		    	toto.add(dummy);
		    }
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		finally {
	        if (rs  != null) try { rs.close();  } catch (SQLException ignore) {}
	   	}
		
		return toto;
	}

    public static String getSQLEdition() {
    	
		return "SELECT IDArticle AS \"COMPTEUR-10\", NomArticle AS \"NOM-100\", PrenomArticle AS \"PRENOM-100\", Adresse1Article AS \"ADRESSE-150\", Adresse2Article AS \"COMP_ADRESSE-150\", CodePostalArticle AS \"CODE_POSTAL-30\", VilleArticle AS \"VILLE-50\" FROM Article";
	}
    
    @SuppressWarnings("rawtypes")
	public static Class[] getColumnClass() {
    	
		return new Class[] { StringCenter.class, StringCenter.class,  StringCenter.class, StringCenter.class, StringCenter.class, StringCenter.class, StringCenter.class };
    }
}
