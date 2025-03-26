package fr.gclo.modeles;

import java.util.Vector;

public class DeviseDAO {

	public static Vector<String> getVecteurDeviseDAO() 
	{		
		final Vector<String> items = new Vector<String> ();
				
    	items.add("EUR");
    	items.add("USD");
		
		return items;
	}	
}