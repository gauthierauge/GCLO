package fr.gclo.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import fr.GCLO;
import fr.gclo.edition.MatricePAP;

@SuppressWarnings("serial")
public class UIMenu extends JMenuBar implements ActionListener {
	
	private JMenu	  mnuFichier   								= new JMenu("Fichier");
	private JMenuItem mnuFichierQuitter 						= new JMenuItem("Quitter");	
	
	private JMenu	  mnuParametrage   							= new JMenu("Parametrage");
	private JMenuItem mnuParametrageFicheSociete				= new JMenuItem("Fiche Soci�t�");
	
	private JMenu 	  mnuDonnees 								= new JMenu("Données");	
	private JMenuItem mnuDonneesClients 						= new JMenuItem("Clients");
	private JMenuItem mnuDonneesArticles 						= new JMenuItem("Articles");
	
	private JMenu 	  mnuVentes 								= new JMenu("Ventes");	
	private JMenuItem mnuVentesCommandes 					    = new JMenuItem("Commandes");
	private JMenuItem mnuVentesLivraisons 					    = new JMenuItem("Gestion des Livraisons");
	private JMenuItem mnuVentesFactures 					    = new JMenuItem("Gestion des Factures");
	
	private JMenu 	  mnuProduction 							= new JMenu("Production");	
	private JMenuItem mnuProductionMatrice 					    = new JMenuItem("Matrice PAP");
	

	public UIMenu()  {

		mnuFichierQuitter.addActionListener(this);
		mnuFichier.add(mnuFichierQuitter);
		
		mnuParametrage.add(mnuParametrageFicheSociete);
		
		mnuDonneesClients.addActionListener(this);
		mnuDonneesArticles.addActionListener(this);	
		mnuDonnees.add(mnuDonneesArticles);
		mnuDonnees.add(mnuDonneesClients);
		
		mnuVentesCommandes.addActionListener(this);
		mnuVentesLivraisons.addActionListener(this);	
		mnuVentesFactures.addActionListener(this);	
		mnuVentes.add(mnuVentesCommandes);
		mnuVentes.add(mnuVentesLivraisons);
		mnuVentes.add(mnuVentesFactures);
		
		mnuProductionMatrice.addActionListener(this);
		mnuProduction.add(mnuProductionMatrice);
		
			
	    this.add(mnuFichier);
	    this.add(mnuParametrage);
	    this.add(mnuDonnees);
	    this.add(mnuVentes);
	    this.add(mnuProduction);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if ( e.getSource() == mnuDonneesClients ) {
			new UIGrilleDesTiers(GCLO.mySelf);
		}	
		else if ( e.getSource() == mnuDonneesArticles ) {
			new UIGrilleDesArticles(GCLO.mySelf);
		}	
		else if ( e.getSource() == mnuVentesCommandes ) {
			new UIGrilleDesCommandes(GCLO.mySelf);
		}	
		else if ( e.getSource() == mnuVentesLivraisons ) {
			new UIGrilleDesLivraisons(GCLO.mySelf);
		}	
		else if ( e.getSource() == mnuVentesFactures ) {
			new UIGrilleDesFactures(GCLO.mySelf);
		}	
		else if ( e.getSource() == mnuProductionMatrice ) {
			try {
				new MatricePAP().generate();
			} catch (Exception e1) {
				System.out.println(e1);
			}
		}	
		else if ( e.getSource() == mnuFichierQuitter) {
	    	GCLO.applicationClose();
		}		
	}
}