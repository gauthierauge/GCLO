package fr.gclo.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import fr.gclo.modeles.CommandeLigneDAO;
import fr.gclo.modeles.SaisonDAO;
import fr.gclo.ui.composants.JSDWScrollPane;
import fr.gclo.ui.composants.JtblTable;
import fr.gclo.ui.composants.TableModelSDW;

@SuppressWarnings("serial")
public class UISelectionSaison extends UIDialog {

 	public JButton cmdValider = new JButton("VALIDER");	

	protected void valider() {		
		retourEcran = true;
		screenClose();
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

	public UISelectionSaison(JFrame parentFrame) {
		super(parentFrame);
		
		this.l_titre = "SELECTION SAISON";		
		
		this.createGUI();
		
		this.createMenu();
		
		JtblTable grid = new JtblTable();
		
		grid.setModel(new TableModelSDW(CommandeLigneDAO.getColumnClassPourFicheClient()));

		grid.setData(SaisonDAO.getSQL(), "");

		JSDWScrollPane scrollpane = new JSDWScrollPane(grid);

		pnlCorps.add(scrollpane, BorderLayout.CENTER);

    	setSize(500, 500);
    	
    	this.screenCenter();
	}
}

