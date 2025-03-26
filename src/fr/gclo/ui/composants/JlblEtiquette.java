package fr.gclo.ui.composants;


public class JlblEtiquette extends JlblEtiquetteSimple {
	 
	private static final long serialVersionUID = 1L;

	public JlblEtiquette(String text, String nom) {
		
       super(text + " :");
       setName(nom);
   }
}
