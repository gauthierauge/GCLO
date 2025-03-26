package fr.gclo.ui.composants;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

public class JlblEtiquetteBordured extends JlblEtiquetteSimple {
		 
	private static final long serialVersionUID = 1L;

	public JlblEtiquetteBordured(String text, String nom) {
		
       super(text);
       setName(nom);
	   setBorder(BorderFactory.createLineBorder(Color.GRAY));
	   setHorizontalAlignment(SwingConstants.CENTER);
   }
}
