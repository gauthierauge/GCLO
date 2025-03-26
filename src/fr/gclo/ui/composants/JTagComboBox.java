package fr.gclo.ui.composants;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import fr.gclo.Environnement;

public class JTagComboBox<E> extends JComboBox<E> 
{	
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public JTagComboBox(String name, Vector<E> items) {
		
		super();
		
		Dimension d = getPreferredSize();
		
	    setPreferredSize(new Dimension(150, d.height));
	    
		if ( items != null ) {
			
			setModel(new DefaultComboBoxModel<E>(items));
			
			setRenderer(new BasicComboBoxRenderer() {
					private static final long serialVersionUID = 1L;
	
				    @SuppressWarnings("rawtypes")
				    @Override
					public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	
				        if (value != null) {
				          /*  if (value instanceof CodeTVA ) {
				            	CodeTVA item = (CodeTVA)value;
					            setText(Float.toString(item.getTauxCodeTVA()).toUpperCase());
				            }
				        
				        	if ( value instanceof Journaux ) 
				        	{
				        		Journaux item = (Journaux)value;
					            setText( item.getlibelle().toUpperCase() );
				        	}
				        		if ( value instanceof Produit ) 
				        	{
				        		Produit item = (Produit)value;
					            setText( item.getTITRE().toUpperCase() );
				        	}
				            if (value instanceof Pays )
				            {
				                Pays item = (Pays)value;
				                setText( item.getLIBELLE().toUpperCase() );
				            }
				            if (value instanceof Beneficiaire )
				            {
					        	Beneficiaire item = (Beneficiaire)value;
					            //setText( item.getNOM().toUpperCase() + " " + item.getPRENOM().toUpperCase() );
					        	setText( item.getNOM().toUpperCase());
				            }
				            if (value instanceof Role )
				            {
					            Role item = (Role)value;
					            setText( item.getLIBELLE().toUpperCase() );
				            }
				            if (value instanceof Options )
				            {
				            	Options item = (Options)value;
					            setText( Float.toString(item.getID_OPT()).toUpperCase() );
				            }
				            if (value instanceof Exercice )
				            {
				            	Exercice item = (Exercice)value;
				                setText( item.getDenomination() );
				            }*/
				        	if ( value instanceof String ) {
					            setText( (String)value );
				        	}		
				        	if ( value instanceof Float ) {
					            setText( Float.toString((Float)value ));
				        	}		
				        }
	
				        return this;
				    }
				}
			);
		}
		
		final JTextField editorComponent = (JTextField) this.getEditor().getEditorComponent();
		editorComponent.addFocusListener( new FocusListener()
		{
			 public void focusGained( FocusEvent e )
			 {
			 	editorComponent.selectAll();
		        setBackground(Environnement.couleurDeFondFocusedField);
			 }

			 public void focusLost(FocusEvent e) 
			 {
				 setBackground(Environnement.couleurDeFondUnfocusedField);
			 }
		});
			 
		setName(name);
		//setTag(tag);
		//setEditable(true);
		
		setSelectedIndex(-1);
		
		setMaximumRowCount(20);
	}
}
