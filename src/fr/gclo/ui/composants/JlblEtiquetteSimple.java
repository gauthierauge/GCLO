package fr.gclo.ui.composants;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class JlblEtiquetteSimple extends JLabel {
	 
	private static final long serialVersionUID = 1L;

	public JlblEtiquetteSimple(String text) 
	{
       super(text);
	   setFont(new Font("Lato", Font.TRUETYPE_FONT, 13));
	   setForeground(Color.BLACK);
	   setHorizontalAlignment(SwingConstants.LEFT);
   }
}
