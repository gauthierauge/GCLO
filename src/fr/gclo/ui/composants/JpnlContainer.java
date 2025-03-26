package fr.gclo.ui.composants;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import net.java.dev.designgridlayout.DesignGridLayout;
import net.java.dev.designgridlayout.LabelAlignment;

@SuppressWarnings("serial")
public class JpnlContainer extends JPanel {
	
	public DesignGridLayout layoutHaut = null;

	public JpnlContainer(String titre) {
		
		JlblEtiquetteTitreContainer lblTitre = new JlblEtiquetteTitreContainer(" " + titre);
		
		setLayout(new BorderLayout());
		
		Border outsideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border insideBorder = BorderFactory.createEtchedBorder();
		Border innerPanelBorder = BorderFactory.createCompoundBorder(outsideBorder , insideBorder );
		  
		setBorder(innerPanelBorder);

		JPanel pnlContenant = new JPanel();
		
		layoutHaut = new DesignGridLayout(pnlContenant);
		layoutHaut.labelAlignment(LabelAlignment.RIGHT);
		
		add(lblTitre, BorderLayout.NORTH);
		add(pnlContenant , BorderLayout.CENTER);
	}
}