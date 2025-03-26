package fr.gclo.modeles;

import java.util.Vector;


public class LangueDeCorrespondanceDAO {

	public static Vector<String> getVecteurLangueDeCorrespondanceDAO() 
	{		
		final Vector<String> items = new Vector<String> ();

    	items.addElement("FRANCAIS");
    	items.addElement("ANGLAIS");

		return items;
	}
}
