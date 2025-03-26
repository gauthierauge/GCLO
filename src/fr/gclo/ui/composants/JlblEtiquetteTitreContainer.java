package fr.gclo.ui.composants;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.gclo.Environnement;

@SuppressWarnings("serial")
public class JlblEtiquetteTitreContainer extends JLabel {
	 
	public JlblEtiquetteTitreContainer(String text) {
		super(text);
		setFont(Environnement.smallfont);
		setForeground(Color.WHITE);//new Color(49, 69, 89));//Color.WHITE);
		//setBackground(Environnement.couleurDeFondAlternatedRow);
		setBackground(new Color(121, 147, 170));
	  	setOpaque(true);
	  	setHorizontalAlignment(SwingConstants.LEFT);
	}
}