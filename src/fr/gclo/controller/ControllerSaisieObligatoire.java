package fr.gclo.controller;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JOptionPane;

import fr.gclo.Environnement;
import fr.gclo.ui.UIDialogFiche;
import fr.gclo.ui.composants.JTagTextField;
import fr.gclo.ui.composants.JlblEtiquette;
 
public class ControllerSaisieObligatoire 
{
 	private ControllerSaisieObligatoire() {}
 	 
	private static class SingletonControllerSaisieObligatoire {		
		private final static ControllerSaisieObligatoire instance = new ControllerSaisieObligatoire();
	}
 
	public static ControllerSaisieObligatoire getInstance() {
		return SingletonControllerSaisieObligatoire.instance;
	}

	private Component findComponentByName(Container container, String componentName) 
	 {
		 for (Component component: container.getComponents()) {
			 if (componentName.equalsIgnoreCase(component.getName())) {
				 return component;
			 }
		 }
		 return null;
	}

	public boolean saisieRenseignee(final UIDialogFiche c)
	{
		for ( Component comp :  c.composantSaisieObligatoire ) {
			
	    	  if ( comp instanceof JTagTextField /* && comp.getName() != null*/ ) {
	    		  
	    		  	if ( ((JTagTextField)comp).getText().equalsIgnoreCase("") ) {
		        		Component dummy = findComponentByName(comp.getParent(), "lbl" + comp.getName());
		        		String dummy_2 = ((JlblEtiquette)dummy).getText().substring(0, ((JlblEtiquette)dummy).getText().length()-2);
		                JOptionPane.showMessageDialog(null, "La saisie du champ " + dummy_2 + " est obligatoire !", "Attention", JOptionPane.ERROR_MESSAGE);
		        		comp.requestFocusInWindow();
		        		return false;
	    		  	}
	    	  }
/*				
	    	  if ( comp instanceof JTagComboBox  && comp.getName() != null) 
	    	  {
		        	if ( ((JTagComboBox<?>)comp).getSelectedIndex() == -1 ) 
		        	{
		        		Component toto = findComponentByName(comp.getParent(), "lbl" + comp.getName());
		        		String titi = ((JlblEtiquette)toto).getText().substring(0, ((JlblEtiquette)toto).getText().length()-2);
		                JOptionPane.showMessageDialog(null, "La saisie du champ " + titi + " est obligatoire !", "Attention", JOptionPane.ERROR_MESSAGE);
		        		comp.requestFocusInWindow();
		        		return false;
		        	}
	    	  }
				
	    	  if ( comp instanceof JDateChooser  && comp.getName() != null) 
	    	  {
		        	if ( ((JDateChooser)comp).getDate() == null ) 
		        	{
		        		Component toto = findComponentByName(comp.getParent(), "lbl" + comp.getName());
		        		String titi = ((JlblEtiquette)toto).getText().substring(0, ((JlblEtiquette)toto).getText().length()-2);
		                JOptionPane.showMessageDialog(null, "La saisie du champ " + titi + " est obligatoire !", "Attention", JOptionPane.ERROR_MESSAGE);
		        		comp.requestFocusInWindow();
		        		return false;
		        	}
	    	  }
	    	  */
		}
		
		return true;
	}
	
	public void designChampObligatoire(final UIDialogFiche c) {
		
		for ( Component comp :  c.composantSaisieObligatoire ) {			
    		Component dummy = findComponentByName(comp.getParent(), "lbl" + comp.getName());
    		((JlblEtiquette)dummy).setForeground(Environnement.couleurDeFondRequiredField);
		}
	}
}
