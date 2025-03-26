package fr.gclo.ui.composants;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class JlblEtiquetteSousTitre extends JLabel {
	 
	private static final long serialVersionUID = 1L;
	
	public JlblEtiquetteSousTitre(String text) {
       super(text);
	   setFont(new Font("Lato", Font.PLAIN, 40));
	   //setForeground(Environnement.couleurDeFondAlternatedRow);
	   setForeground(Color.black);
	   setHorizontalAlignment(SwingConstants.LEFT);
   }
}