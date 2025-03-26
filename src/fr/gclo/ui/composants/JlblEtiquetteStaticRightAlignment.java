package fr.gclo.ui.composants;

import javax.swing.SwingConstants;

public class JlblEtiquetteStaticRightAlignment extends JlblEtiquetteStatic {
	 
	private static final long serialVersionUID = 1L;

	public JlblEtiquetteStaticRightAlignment(String text) 
	{
     super(text);
     
     setHorizontalAlignment(SwingConstants.RIGHT);
 }
}
