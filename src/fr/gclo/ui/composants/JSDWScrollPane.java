package fr.gclo.ui.composants;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import fr.gclo.Environnement;


public class JSDWScrollPane extends JScrollPane 
{
	private static final long serialVersionUID = -5203213046224812121L;
	
	public JSDWScrollPane(JTable table) 
	//public JSDWScrollPane(JViewport table) 
	{		
        super(table, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);

		setBorder( BorderFactory.createLineBorder(Color.GRAY, 1, true) );
        
		JPanel rightCorner = new JPanel();
		rightCorner.setBackground(Environnement.couleurDeFondEntete);	
		setCorner(JScrollPane.UPPER_RIGHT_CORNER, rightCorner);
	}
}