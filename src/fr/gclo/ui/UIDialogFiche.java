package fr.gclo.ui;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import fr.gclo.ui.composants.DecimalTextField;
import fr.gclo.ui.composants.JSDWComboBox;
import fr.gclo.ui.composants.JSDWDateChooser;
import fr.gclo.ui.composants.JTagComboBox;
import fr.gclo.ui.composants.JTagTextField;


@SuppressWarnings("serial")
public class UIDialogFiche extends UIDialog {

	protected String 				ID							= "";
	protected String 				IDParent					= "";
	protected String 				mode						= "";	
	protected String				beanName					= "";	
	protected Component 			parentFrame					= null;
	public String 					classObjetDAO				= "";		
	public JButton				 	cmdValider 					= new JButton("VALIDER");	
	public ArrayList <JComponent> 	composantSaisieObligatoire 	= new ArrayList <JComponent>();
	
    public final JPopupMenu 		popup 						= new JPopupMenu();

	protected void valider() {
		
		try {
			Class<?>  classeBean = Class.forName(this.beanName);
			
			List<Object> items 	 = new ArrayList<>();

		    for ( Field field : classeBean.newInstance().getClass().getDeclaredFields() ) {
	   	        field.setAccessible(true); // if you want to modify private fields
				try {
					Field field1 = this.getClass().getDeclaredField("txt" + field.getName());
					field1.setAccessible(true); 
					
					if (field1.get(this) instanceof JTagTextField && ! (field1.get(this) instanceof DecimalTextField) ) {
						JTagTextField prop 	= (JTagTextField)field1.get(this);
						items.add(prop.getText());
					}
					else if (field1.get(this) instanceof JTagComboBox<?>) {
						JTagComboBox<?> prop 	= (JTagComboBox<?>)field1.get(this);
						items.add(prop.getSelectedItem());
					}
					else if (field1.get(this) instanceof DecimalTextField) {
						DecimalTextField prop 	= (DecimalTextField)field1.get(this);
						items.add(prop.getValue());
					}
					else if (field1.get(this) instanceof JSDWDateChooser) {
						JSDWDateChooser prop 	= (JSDWDateChooser)field1.get(this);
						items.add(prop.getDate());
					}									
					else if (field1.get(this) instanceof JTextField) {
						JTextField prop 	= (JTextField)field1.get(this);
						items.add(prop.getText());
					}									
				} catch (NoSuchFieldException | SecurityException e1) {
					e1.printStackTrace();
				}  			 
		    }
		    
		    Class<?> 		classe	 	 = Class.forName(classObjetDAO);
		    Object   		o 			 = classe.newInstance();
			Constructor<?> 	constructeur = classeBean.getConstructor(new Class[] { List.class });
			Object 			instanceBean = (Object) constructeur.newInstance(new Object[] { items });			
			if ( this.mode.equals("MODIFICATION") ? (boolean)classe.getDeclaredMethod("update", classeBean).invoke(o, instanceBean) : (boolean)classe.getDeclaredMethod("create", classeBean).invoke(o, instanceBean) ) {				
				retourEcran = true;
				screenClose();
			}
			else {
				JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la mise � jour de vos donn�es !", "Attention", JOptionPane.ERROR_MESSAGE);
			}
			
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	protected void showItem() {
		
		if ( this.mode.equalsIgnoreCase("MODIFICATION") ) {
			
			try {				
				//Class<?> 		classe 				= parentFrame instanceof UIInternalFrameGrid ? Class.forName(((UIInternalFrameGrid)parentFrame).classObjetDAO) :  Class.forName(((UIDialogFiche)parentFrame).classObjetDAO);
			    Class<?> classe	 	= Class.forName(classObjetDAO);
			    Object   o 			= classe.newInstance();
				Object 	 objectBean	= classe.getDeclaredMethod("read", String.class).invoke(o, this.ID);
			        					
	    	    for ( Field field : objectBean.getClass().getDeclaredFields() ) {
	    	        field.setAccessible(true); // if you want to modify private fields
					try {
						//System.out.println("txt" + field.getName());
						Field field1 = this.getClass().getDeclaredField("txt" + field.getName());
						field1.setAccessible(true); 
						
						if (field1.get(this) instanceof JTagTextField && ! (field1.get(this) instanceof DecimalTextField) ) {
							JTagTextField prop 	= (JTagTextField)field1.get(this);
							if ( field.getType().equals(Integer.TYPE) ) {
								prop.setText(Integer.toString((int) field.get(objectBean)));
							}
							else if ( field.getType().equals(Float.TYPE) ) {
								prop.setText(Float.toString((Float) field.get(objectBean)));								
							}
							else {
								prop.setText((String)field.get(objectBean));
							}
						}
						else if (field1.get(this) instanceof JTagComboBox<?>) {
							JTagComboBox<?> prop 	= (JTagComboBox<?>)field1.get(this);
							prop.setSelectedItem((String)field.get(objectBean));
						}
						else if (field1.get(this) instanceof JSDWComboBox) {
							JSDWComboBox prop 	= (JSDWComboBox)field1.get(this);
							prop.setSelectedItem((String)field.get(objectBean));
						}
						else if (field1.get(this) instanceof DecimalTextField) {
							JTagTextField prop 	= (JTagTextField)field1.get(this);
							prop.setText(Float.toString((Float)field.get(objectBean)));
						}
						else if (field1.get(this) instanceof JSDWDateChooser) {
							JSDWDateChooser prop 	= (JSDWDateChooser)field1.get(this);
							prop.setDate((Date)field.get(objectBean));
						}
						else if (field1.get(this) instanceof JTextField) {
							JTextField prop 	= (JTextField)field1.get(this);
							if ( field.getType().equals(Integer.TYPE) ) {
								prop.setText(Integer.toString((int) field.get(objectBean)));
							}
							else {
								prop.setText((String)field.get(objectBean));
							}
						}
					} catch (NoSuchFieldException | SecurityException e1) {
						e1.printStackTrace();
					}  			 
	    	    }
			} catch (IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}  			 
		}
	}

	protected void createMenu() {
		
		super.createMenu();
		
		cmdValider.addActionListener(this);

		pnlMenu.add(cmdValider);	
	}

	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if ( e.getSource() == cmdValider ) {
			valider();
		}
	}
	
	public UIDialogFiche(Component parentFrame, String mode, String ID, String titre, String bean, String classe) {
		
		super(parentFrame);
		
		this.parentFrame = parentFrame;		
		this.ID			 = ID;
		this.mode 		 = mode;
		this.l_titre 	 = titre;	
		
		this.beanName		= bean;
		this.classObjetDAO	= classe;
	}

}