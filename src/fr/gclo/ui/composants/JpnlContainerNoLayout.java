package fr.gclo.ui.composants;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class JpnlContainerNoLayout extends JPanel {
	
	public JPanel pnlContenant = new JPanel(null);
	
	public JpnlContainerNoLayout(String titre) {
		
		JlblEtiquetteTitreContainer lblTitre = new JlblEtiquetteTitreContainer(" " + titre);
		
		setLayout(new BorderLayout());
		
		Border outsideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border insideBorder = BorderFactory.createEtchedBorder();
		Border innerPanelBorder = BorderFactory.createCompoundBorder(outsideBorder , insideBorder );
		  
		setBorder(innerPanelBorder);
		
		add(lblTitre, BorderLayout.NORTH);
		add(pnlContenant , BorderLayout.CENTER);
	}
}