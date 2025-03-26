package fr.gclo.ui.composants;

import java.awt.Color;
import java.awt.Font;

import javax.swing.SwingConstants;

public class JlblEtiquetteMenu extends JlblEtiquette {
	
	private static final long serialVersionUID = 1L;

	public JlblEtiquetteMenu(String text, String nom) 
	{
        super(text, nom);
	    setFont(new Font("Helvetica", Font.PLAIN, 12));
	    setForeground(Color.GRAY);
	    setHorizontalAlignment(SwingConstants.RIGHT);
	}
}
