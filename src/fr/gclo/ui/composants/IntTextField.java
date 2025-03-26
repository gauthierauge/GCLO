package fr.gclo.ui.composants;

import java.text.DecimalFormat;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentListener;

/**
* Champ texte pour saisir uniquement des nombres entiers
* @author praymond
*/
public class IntTextField extends JTextField implements FocusListener, KeyListener  {

    
    /**
     * Version
     */
    private static final long serialVersionUID = 1L;
    private DocumentListener dl = null;
   private static DecimalFormat df = new DecimalFormat("#");

   /**
   * Constructeur
   */
   public IntTextField() {
      super();
      dl = null;
      addFocusListener(this);
      addKeyListener(this);
	   setHorizontalAlignment(SwingConstants.CENTER);
	   setBorder(BorderFactory.createLineBorder(Color.GRAY));
   }

   /**
   * Méthode implémentée pour l'interface
   * @see java.awt.Component#isValid()
   */
   public boolean isValid() {
      try {
         Integer.parseInt(super.getText());
         return true;
      } catch(Exception e) {
         return false;
      }
   }

   /**
   * Récupération de la valeur contenue dans ce champ
   * @return la valeur contenue dans ce champ
   */
   public int getValue() {
      try {
         return Integer.parseInt(super.getText());
      } catch(NumberFormatException e) {
         return Integer.MIN_VALUE;
      }
   }

   /**
   * Méthode implémentée pour l'interface
   * @see javax.swing.text.JTextComponent#getText()
   */
   public String getText() {
      if(df != null) {
         try {
            int temp = this.getValue();
            if(temp != Integer.MIN_VALUE) {
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

   /**
   * Méthode implémentée pour l'interface
   * @see javax.swing.JTextField#createDefaultModel()
   */
//   protected Document createDefaultModel() {
////		return new IntTextDocument();
//      return new kp1.acp.swing.util.JTextFieldFilter(kp1.acp.swing.util.JTextFieldFilter.INTEGER);
//   }

   /**
   * Méthode implémentée pour l'interface
   * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
   */
   public void focusGained(FocusEvent focusEvent) {
      // On sélectionne le contenu :
      selectAll();
   }

   /**
   * Méthode implémentée pour l'interface
   * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
   */
   public void focusLost(FocusEvent focusEvent) {
      // on formate le champ quand on le quitte (pas de 0 devant par exemple)
	   if(dl != null) {
	         this.getDocument().removeDocumentListener(dl);
	      }
      this.setText(this.getText());
      // remettre le documentlistener
      if(dl != null) {
         this.getDocument().addDocumentListener(dl);
      }
   }
   
   /**
    * Initialise le lien vers le DocumentListener.
    */
    public void setDocumentListener(DocumentListener _dl) {
    	dl = _dl;
    	if(dl != null) this.getDocument().addDocumentListener(dl);
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * , KeyListener 
     */
    public void keyTyped(KeyEvent arg0) {
     	//System.out.println(arg0.getKeyChar());
     	if (arg0.getKeyChar()=='+')
     	{
     	   setSelectionStart(0);
     	   setSelectionEnd(0);
     	}
    }

    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent arg0) {
    	// TODO Auto-generated method stub
    	
    }

    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent arg0) {
    	// TODO Auto-generated method stub
    	
    }
   
}