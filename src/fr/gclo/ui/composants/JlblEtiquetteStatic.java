package fr.gclo.ui.composants;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Collections;

public class JlblEtiquetteStatic extends JlblEtiquetteSimple {
	 
	private static final long serialVersionUID = 1L;

	public JlblEtiquetteStatic(String text) 
	{
      super(text);
      
      Font font = getFont();

      font = font.deriveFont(Collections.singletonMap(TextAttribute.WEIGHT, TextAttribute.WEIGHT_ULTRABOLD));

      setFont(font);
  }
}
