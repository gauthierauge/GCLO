package fr.gclo.ui.composants;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class JlblEtiquetteAction extends JlblEtiquetteSousMenu {
	 
	private static final long serialVersionUID = 1L;

	public JlblEtiquetteAction(String text) 
	{
	     super(text);
	     
	     Font font = getFont();
	     
	     Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>(font.getAttributes());
	     attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	     attributes.put(TextAttribute.SIZE, 14);
	
	    // font = font.deriveFont(Collections.singletonMap(TextAttribute.SIZE, 12));
	     font = font.deriveFont(attributes);
	
	     setFont(font);
	 }
}
