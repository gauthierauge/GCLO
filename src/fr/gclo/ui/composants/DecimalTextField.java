package fr.gclo.ui.composants;

import java.text.DecimalFormat;
import java.awt.event.*;

import javax.swing.text.Document;

/**
* Champ texte pour saisir uniquemnt des nombres d�cimaux
* @author praymond
*/
public class DecimalTextField extends JTagTextField implements KeyListener  
{
    private static final long serialVersionUID = 1L;
    
   // format d'affichage des d�cimaux (nombre de chiffre apr�s la virgule)
   private DecimalFormat df = new DecimalFormat("#.##");

   public DecimalTextField(String name, ActionListener al) 
   {
      super(name, al);
      addKeyListener(this);
   }
   
   /**
   * Dispose de ce composant.
   */
   public void dispose() 
   {
	   df = null;
   		removeFocusListener(this);
   }

   /**
    * M�thode impl�ment�e pour l'interface
    * @see java.awt.Component#isValid()
    */
    public boolean isValid() {
 	      try {
 	         Float.parseFloat(super.getText());
 	         return true;
 	      } catch(NumberFormatException e) {
 	    	  String valeur=super.getText();
 	    	  if (valeur.contains(","))
 	    	  {
 	    		  valeur=valeur.replace(",",".");
 	    		  try {
 	    		         Float.parseFloat(valeur);
 	    		         super.setText(valeur);
 	    		         return true;
 	    		      } catch(NumberFormatException e2) {
 	    		    	  return false;
 	    		      }
 	    	  }
 	    	  return false;
 	      }
 	      catch (Exception ee) {
 	    	  return false;
 	      }
 	   }

    /**
    * R�cup�ration de la valeur contenue dans ce champ
    * @return la valeur contenue dans ce champ
    */
    public float getValue() {
 	   String valeur=super.getText();
 	   try {
 		   if (valeur.contains(","))
 	    	  {
 	    		  valeur=valeur.replace(",",".");
 	    		 
 		         Float.parseFloat(valeur);
 		         super.setText(valeur);
 		         return Float.parseFloat(super.getText());
 	    		      
 	    	  }
          return Float.parseFloat(super.getText());
       } catch(NumberFormatException e) {
     	 
     	 
          return Float.MIN_VALUE;
       }
    }

    public String getText() {
      if(df != null) {
         try {
            float temp = this.getValue();
            if(temp != Float.MIN_VALUE) {
               return df.format(temp);
            } else {
               return super.getText();
            }
         } catch(Exception e) {
            return super.getText();
         }
      }
      return super.getText();
   }

   protected Document createDefaultModel() 
   {
	   return new JTextFieldFilter(JTextFieldFilter.NEG_FLOAT);
   }
   
   public void keyTyped(KeyEvent arg0) {
       //si le caract�re tap� correspond � un raccourci, il ne faut pas effacer
       //le texte s�lectionn�
       if (arg0.getKeyChar()=='+' 
           || (arg0.getKeyChar()>='A') && (arg0.getKeyChar()<='Z')
           || (arg0.getKeyChar()>='a') && (arg0.getKeyChar()<='z')
               )
    	{
    	   setSelectionStart(0);
    	   setSelectionEnd(0);
    	}
   }

   public void keyPressed(KeyEvent arg0) {}

   public void keyReleased(KeyEvent arg0) {}
   
}
