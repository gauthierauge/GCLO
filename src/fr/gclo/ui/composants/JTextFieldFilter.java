package fr.gclo.ui.composants;

import javax.swing.text.*;

/**
 * D�finition d'un filtre pour les zones de saisie de texte.
 */
public class JTextFieldFilter extends PlainDocument {

	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Liste des caract�res en minuscules.
	 */
	public static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";

	/**
	 * Liste des caract�res en majuscules.
	 */
	public static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * Liste de tous les caract�res.
	 */
	public static final String ALPHA = LOWERCASE + UPPERCASE;

	/**
	 * Liste de caractere avec l'espace.
	 */
	public static final String ESP_ALPHA = ALPHA + " ";

	/**
	 * Liste des caract�res pour des entiers positifs.
	 */
	public static final String INTEGER = "0123456789";

	/**
	 * Liste des caract�res pour des entiers n�gatifs.
	 */
	public static final String NEG_INTEGER = INTEGER + "-";

	/**
	 * Liste des caract�res pour des nombres flottants positifs.
	 */
	public static final String FLOAT = INTEGER + "."  + ",";

	/**
	 * Liste des caract�res pour des nombres flottants n�gatifs.
	 */
	public static final String NEG_FLOAT = FLOAT + "-";

	/**
	 * Liste de caract�res alpha num�rique.
	 */
	public static final String ALPHA_NUMERIC = ALPHA + INTEGER;

	/**
	 * Liste de caract�res alpha num�rique.
	 */
	public static final String ESP_ALPHA_NUMERIC = ALPHA_NUMERIC + " ";

	/**
	 * Liste de caract�res alpha num�rique.
	 */
	public static final String ESP_ALPHA_TIRET_NUMERIC = ESP_ALPHA_NUMERIC + "-"+"_";

	/**
	 * Liste de caract�res alpha num�rique, tirets, points, accents
	 */
	public static final char caracteresAccentues[] = {'�','�','�','�','�','�','�','�','�','�','�','�','�'};
	public static final String ESP_ALPHA_ACCENTS_TIRET_NUMERIC = ESP_ALPHA_TIRET_NUMERIC 
	+ " " + "-" + "_" + "." + "+"+ ":" 
	+ new String(caracteresAccentues);
	/**
	 * Liste de caract�res alpha num�rique.
	 */
	public static final String ALPHA_TIRET_NUMERIC = ALPHA_NUMERIC + "-";

	/**
	 * Liste des caract�res accept�s.
	 */
	private String charAutorises = null;

	/**
	 * Signale si le signe n�gatif est accept�.
	 */
	//private boolean negativeAccepted = false;

	/**
	 * On conscerve dans un coin le texte pr�c�dent, cela peut servir.
	 */
	private String prevValue = null;

	/**
	 * On m�morise le nombre de caract�res maximal autoris�.
	 */
	private int nbMax = -1;

	/**
	 * Construction par d�faut.
	 */
	public JTextFieldFilter() {
		this(ALPHA_NUMERIC);
	}

	/**
	 * Construction � partir d'une liste de caract�res autoris�s.
	 */
	public JTextFieldFilter(String char_autorises) {
		charAutorises = char_autorises;
	}

	/**
	 * Construction � partir d'une liste de caract�res autoris�s.
	 */
	public JTextFieldFilter(String char_autorises, int NbMax) {
		charAutorises = char_autorises;
		if(NbMax > 0) nbMax = NbMax;
	}


	/**
	 * Invoqu� quand on ajouter une cha�ne de caract�res.
	 */
	public void insertString(int offset, String  Str, AttributeSet attr) throws BadLocationException {
		String  str = Str;
		if(str == null) return;
		if(charAutorises.equals(UPPERCASE)) str = str.toUpperCase();
		else {
			if(charAutorises.equals(LOWERCASE)) str = str.toLowerCase();
		}
		String oldString = getText(0, getLength());
		// On v�rifie que l'on va pas nous polluer :
		for(int i=0; i < str.length(); i++) {
			if(charAutorises.indexOf(String.valueOf(str.charAt(i))) == -1)  {
				// Pollueur !
				if(prevValue != null) {
					if(oldString.length() == 0) {
						// On remet la pr�c�dente valeur :
						//  super.insertString(0, prevValue, attr);
					}
				}
				return;
			}
		}
		if(charAutorises.equals(NEG_INTEGER) || charAutorises.equals(NEG_FLOAT)) {
			if(str.indexOf("-") != -1) {
				if(str.indexOf("-") != 0 || offset != 0 ) {
					// Pollueur !
					if(prevValue != null) {
						if(oldString.length() == 0) {
							// On remet la pr�c�dente valeur :
							//    super.insertString(0, prevValue, attr);
						}
					}
				}
			}
		}
		// On regarde si on n'a pas une overdose :
		if(nbMax > 0) {
			oldString = getText(0, getLength());
			int nbActuel = (oldString != null) ? oldString.length() : 0;
			int NbAjoute = str.length();
			if((nbActuel + NbAjoute) > nbMax) {
				// Overdose !
				if(prevValue != null) {
					//if(oldString.length() == 0) {
						// On remet la pr�c�dente valeur :
						//		super.insertString(0, prevValue, attr);
					//}
				}
				return;
			}
		}

		// On laisse la chaine passer :
		super.insertString(offset, str, attr);
	}

	/**
	 *
	 */
	protected void insertUpdate(AbstractDocument.DefaultDocumentEvent chng, AttributeSet attr) {
		try {
			prevValue = getText(0,getLength());
		}
		catch(Exception e) {}
	}

	/**
	 *
	 */
	protected void removeUpdate(AbstractDocument.DefaultDocumentEvent chng) {
		try {
			prevValue = getText(0,getLength());
		}
		catch(Exception e) {}
	}


	/**
	 * On ne garde que les caract�res autoris�s dans la chaine de caract�res 
	 * @param chaineIn
	 * @param caracteresAutorises
	 * @return
	 */
	public static String purgeString(String chaineIn, String caracteresAutorises) {
		String chaineOut = "";

		int i = 0;
		while (i < chaineIn.length()) {
			if(caracteresAutorises.indexOf(chaineIn.charAt(i)) >= 0)  {
				//On ajoute le caract�re i
				chaineOut += chaineIn.charAt(i);
			}
			i++;
		}

		return chaineOut;
	}

	/**
	 * Test
	 * @param args
	 */
	public static void main(String[] args) {
		String autorises = ESP_ALPHA_TIRET_NUMERIC;
		String in = "C'est la f�te !";
		String out = purgeString(in, autorises);
		System.out.println("Out : "  + out);
	}

}
