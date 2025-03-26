package fr.gclo.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import net.java.dev.designgridlayout.DesignGridLayout;
import fr.gclo.controller.ControllerSaisieObligatoire;
import fr.gclo.modeles.CommandeDAO;
import fr.gclo.modeles.DeviseDAO;
import fr.gclo.modeles.FactureDAO;
import fr.gclo.modeles.LangueDeCorrespondanceDAO;
import fr.gclo.modeles.LivraisonDAO;
import fr.gclo.modeles.PaysDAO;
import fr.gclo.modeles.ReglementClientDAO;
import fr.gclo.modeles.TiersAutreAdresseDAO;
import fr.gclo.modeles.TitreTiersDAO;
//import fr.gclo.modeles.TypeTiersDAO;
import fr.gclo.ui.composants.JSDWPanel;
import fr.gclo.ui.composants.JSDWScrollPane;
import fr.gclo.ui.composants.JTagComboBox;
//import fr.gclo.ui.composants.JTagComboBox;
import fr.gclo.ui.composants.JTagTextField;
import fr.gclo.ui.composants.JlblEtiquette;
import fr.gclo.ui.composants.JpnlContainer;
import fr.gclo.ui.composants.JpnlContainer2;
import fr.gclo.ui.composants.JtblTable;
import fr.gclo.ui.composants.TableModelSDW;

public class UIFicheTiers extends UIDialogFiche {
	
	private static final long 		serialVersionUID 					= 1L;
	
	private JTagTextField 			txtCodeTiers 						= new JTagTextField("CodeTiers", 	 		this);
	private JTagTextField 			txtTypeTiers 						= new JTagTextField("TypeTiers", 	 		this);
	private JTagComboBox<String> 	txtTitreTiers			 			= new JTagComboBox<String>("TitreTiers", TitreTiersDAO.getVecteurTitreTiersDAO());
	private JTagTextField 			txtRaisonSocialeTiers 				= new JTagTextField("RaisonSocialeTiers", 	 	this);
	private JTagTextField 			txtAdresse1Tiers 					= new JTagTextField("Adresse1Tiers", 	this);
	private JTagTextField 			txtAdresse2Tiers 					= new JTagTextField("Adresse2Tiers", 	this);
	private JTagTextField 			txtCodePostalTiers 					= new JTagTextField("CodePostalTiers", 	this);
	private JTagTextField 			txtVilleTiers 						= new JTagTextField("VilleTiers", 	 	this);
	private JTagComboBox<String> 	txtPaysTiers			 			= new JTagComboBox<String>("PaysTiers", PaysDAO.getVecteurPaysDAO());
	private JTagTextField 			txtTelephone1Tiers 					= new JTagTextField("Telephone1Tiers", 	this);
	private JTagTextField 			txtTelephone2Tiers 					= new JTagTextField("Telephone2Tiers", 	this);
	private JTagTextField 			txtTelephone3Tiers 					= new JTagTextField("Telephone3Tiers", 	this);
	private JTagTextField 			txtAdresseDeMessagerieTiers 		= new JTagTextField("AdresseDeMessagerieTiers", 	this);
	private JTagComboBox<String> 	txtLangueDeCorrespondance			= new JTagComboBox<String>("LangueDeCorrespondance", LangueDeCorrespondanceDAO.getVecteurLangueDeCorrespondanceDAO());
	private JTagComboBox<String> 	txtDevise							= new JTagComboBox<String>("Devise", DeviseDAO.getVecteurDeviseDAO());
	private JTagTextField 			txtNumeroSiretTiers 				= new JTagTextField("NumeroSiretTiers", 	this);
	private JTagTextField 			txtNumeroTvaIntracommunautaireTiers = new JTagTextField();
	private JTagTextField 			txtCompteTiers 						= new JTagTextField();
	private JTextArea 				txtObservationTiers 				= new JTextArea();
	private JTagTextField 			txtInterlocuteurTiers 				= new JTagTextField();
	private JCheckBox 				txtExonerationTVA 					= new JCheckBox();
	private JCheckBox 				txtExonerationTPF 					= new JCheckBox();
	private JCheckBox 				txtClientExport 					= new JCheckBox();

	private JlblEtiquette 			lblCodeTiers 						= new JlblEtiquette("Code",				"lblCodeTiers");
	private JlblEtiquette 			lblTitreTiers 						= new JlblEtiquette("Civilité",			"lblTitreTiers");
	private JlblEtiquette 			lblRaisonSocialeTiers 				= new JlblEtiquette("Nom",				"lblRaisonSocialeTiers");
	private JlblEtiquette 			lblAdresse1Tiers 					= new JlblEtiquette("Adresse",			"lblAdresse1Tiers");
	private JlblEtiquette 			lblAdresse2Tiers 					= new JlblEtiquette("Adresse compl.",	"lblAdresse2Tiers");
	private JlblEtiquette 			lblCodePostalTiers 					= new JlblEtiquette("Code Postal",		"lblCodePostalTiers");
	private JlblEtiquette 			lblVilleTiers 						= new JlblEtiquette("Ville",			"lblVilleTiers");
	private JlblEtiquette 			lblPaysTiers 						= new JlblEtiquette("Pays",			"lblPaysTiers");
	private JlblEtiquette 			lblTelephone1Tiers 					= new JlblEtiquette("N° de Téléphone",	"lblTelephone1Tiers");
	private JlblEtiquette 			lblTelephone2Tiers 					= new JlblEtiquette("N° de Portable",	"lblTelephone21Tiers");
	private JlblEtiquette 			lblTelephone3Tiers 					= new JlblEtiquette("N° de Téléphone Pro.",	"lblTelephone3Tiers");
	private JlblEtiquette 			lblAdresseDeMessagerieTiers 		= new JlblEtiquette("Adresse de Messagerie",	"lblAdresseDeMessagerieTiers");
	private JlblEtiquette 			lblLangueDeCorrespondance 			= new JlblEtiquette("Langue de Correspondance",	"lblLangueDeCorrespondance");
	private JlblEtiquette 			lblDevise 							= new JlblEtiquette("Devise",	"lblDevise");
	private JlblEtiquette 			lblNumeroSiretTiers 				= new JlblEtiquette("Siret",	"lblNumeroSiretTiers");
	private JlblEtiquette 			lblNumeroTvaIntracommunautaireTiers	= new JlblEtiquette("N° de Tva Intra-Communautaire",	"lblNumeroTvaIntracommunautaireTiers");
	private JlblEtiquette 			lblCompteTiers 						= new JlblEtiquette("N° Comptable",	"lblCompteTiers");
	private JlblEtiquette 			lblObservationTiers 				= new JlblEtiquette("Commentaires",	"lblObservationTiers");
	private JlblEtiquette 			lblInterlocuteurTiers 				= new JlblEtiquette("Interlocuteur",	"lblInterlocuteurTiers");
	private JlblEtiquette 			lblExonerationTVA					= new JlblEtiquette("Exonération de T.V.A.",	"lblExonerationTVA");
	private JlblEtiquette 			lblExonerationTPF					= new JlblEtiquette("Exonération de T.P.F.",	"lblExonerationTPF");
	private JlblEtiquette 			lblClientExport						= new JlblEtiquette("Client export",	"lblClientExport");
	
	private JtblTable gridReglement = new JtblTable();
	private JtblTable gridAutreAdresse = new JtblTable();
	
	
	private JButton cmdAjouterAutreAdresse = new JButton("AJOUTER");
	private JButton cmdModifierAutreAdresse = new JButton("MODIFIER");
	private JButton cmdSupprimerAutreAdresse = new JButton("SUPPRIMER");
	
	private JButton cmdAjouterReglement = new JButton("AJOUTER");
	private JButton cmdModifierReglement = new JButton("MODIFIER");
	private JButton cmdSupprimerReglement = new JButton("SUPPRIMER");
	
	private JTabbedPane 			onglets 							= new JTabbedPane();
	
	protected void valider() {
		
		if ( ! ControllerSaisieObligatoire.getInstance().saisieRenseignee(this) ) return;
		
		super.valider();
	}
	
	private JPanel getEnteteTiers() {
		
		JSDWPanel pnlDummy = new JSDWPanel();
		
		txtCodeTiers.setEditable(false);
		txtCodeTiers.setEnabled(false);
		
		layout = new DesignGridLayout(pnlDummy);
		
		layout.row().grid(lblCodeTiers).add(txtCodeTiers).grid(lblRaisonSocialeTiers).add(txtRaisonSocialeTiers).grid(lblTitreTiers).add(txtTitreTiers).grid(1);

		return pnlDummy;	
	}
	
	private JPanel getRenseignementsGeneraux() {
		
		JpnlContainer pnlHaut = new JpnlContainer("Renseignements Generaux");
		
		txtObservationTiers.setRows(5);
		
		pnlHaut.layoutHaut.row().grid(lblTelephone1Tiers).add(txtTelephone1Tiers).grid(2);
		pnlHaut.layoutHaut.row().grid(lblTelephone2Tiers).add(txtTelephone2Tiers, 2).grid(lblTelephone3Tiers).add(txtTelephone3Tiers).grid(1);
		pnlHaut.layoutHaut.row().grid(lblAdresseDeMessagerieTiers).add(txtAdresseDeMessagerieTiers).grid(1);
		pnlHaut.layoutHaut.emptyRow();
		pnlHaut.layoutHaut.row().grid(lblLangueDeCorrespondance).add(txtLangueDeCorrespondance).grid(lblDevise).add(txtDevise).grid(1);
		pnlHaut.layoutHaut.emptyRow();
		pnlHaut.layoutHaut.row().grid(lblNumeroSiretTiers).add(txtNumeroSiretTiers).grid(lblNumeroTvaIntracommunautaireTiers).add(txtNumeroTvaIntracommunautaireTiers).grid(1);
		pnlHaut.layoutHaut.emptyRow();
		pnlHaut.layoutHaut.row().grid(lblCompteTiers).add(txtCompteTiers).grid(lblClientExport).add(txtClientExport).grid(lblExonerationTVA).add(txtExonerationTVA).grid(lblExonerationTPF).add(txtExonerationTPF).grid(1);
		pnlHaut.layoutHaut.emptyRow();
		pnlHaut.layoutHaut.row().grid(lblObservationTiers).add(txtObservationTiers);
	
	    return pnlHaut;
	}
	
	private JPanel getAdresses() {
		
		JPanel pnlAdresses = new JPanel(new BorderLayout());
		
		JpnlContainer pnlHaut = new JpnlContainer("Adresses");
		pnlHaut.layoutHaut.row().grid(lblAdresse1Tiers).add(txtAdresse1Tiers).grid(1);
		pnlHaut.layoutHaut.emptyRow();
		pnlHaut.layoutHaut.row().grid(lblAdresse2Tiers).add(txtAdresse2Tiers).grid(1);
		pnlHaut.layoutHaut.emptyRow();
		pnlHaut.layoutHaut.row().grid(lblCodePostalTiers).add(txtCodePostalTiers).grid(lblVilleTiers).add(txtVilleTiers).grid(1);
		pnlHaut.layoutHaut.emptyRow();
		pnlHaut.layoutHaut.row().grid(lblPaysTiers).add(txtPaysTiers).grid(1);
		pnlHaut.layoutHaut.emptyRow();
		pnlHaut.layoutHaut.row().grid(lblInterlocuteurTiers).add(txtInterlocuteurTiers).grid(1);
		
		JpnlContainer2 pnlBas = new JpnlContainer2("Autres Adresses");
				
		gridAutreAdresse.setModel(new TableModelSDW(TiersAutreAdresseDAO.getColumnClassPourFicheClient()));

		gridAutreAdresse.setData(TiersAutreAdresseDAO.getSQLFicheTiers(this.ID), "");

		JSDWScrollPane scrollpane = new JSDWScrollPane(gridAutreAdresse);				
		
		
		cmdAjouterAutreAdresse.addActionListener(this);
		cmdModifierAutreAdresse.addActionListener(this);
		cmdSupprimerAutreAdresse.addActionListener(this);
		
		JPanel pnlMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

		pnlMenu.add(cmdAjouterAutreAdresse);
		pnlMenu.add(cmdModifierAutreAdresse);
		pnlMenu.add(cmdSupprimerAutreAdresse);
		
		pnlBas.pnlContenant.add(scrollpane, BorderLayout.CENTER);
		pnlBas.pnlContenant.add(pnlMenu, BorderLayout.SOUTH);


		pnlAdresses.add(pnlHaut, BorderLayout.NORTH);
		pnlAdresses.add(pnlBas, BorderLayout.CENTER);
	
	    return pnlAdresses;
	}
	
	private JPanel getCommandes() {
		
		JpnlContainer2 pnlHaut = new JpnlContainer2("Commandes");
		
		JtblTable grid = new JtblTable();
		
		grid.setModel(new TableModelSDW(CommandeDAO.getColumnClassPourFicheClient()));

		grid.setData(CommandeDAO.getSQLFicheTiers(this.ID), "");

		JSDWScrollPane scrollpane = new JSDWScrollPane(grid);
		
		pnlHaut.pnlContenant.add(scrollpane, BorderLayout.CENTER);		

		return pnlHaut;
	}
	
	private JPanel getLivraisons() {
		
		JpnlContainer2 pnlHaut = new JpnlContainer2("Livraison");
		
		JtblTable grid = new JtblTable();
		
		grid.setModel(new TableModelSDW(LivraisonDAO.getColumnClassPourFicheClient()));

		grid.setData(LivraisonDAO.getSQLFicheTiers(this.ID), "");

		JSDWScrollPane scrollpane = new JSDWScrollPane(grid);
		
		pnlHaut.pnlContenant.add(scrollpane, BorderLayout.CENTER);		

		return pnlHaut;
	}
	
	private JPanel getFactures() {
		
		JpnlContainer2 pnlHaut = new JpnlContainer2("Factures");
		
		JtblTable grid = new JtblTable();
		
		grid.setModel(new TableModelSDW(FactureDAO.getColumnClassPourFicheClient()));

		grid.setData(FactureDAO.getSQLFicheTiers(this.ID), "");

		JSDWScrollPane scrollpane = new JSDWScrollPane(grid);
		
		pnlHaut.pnlContenant.add(scrollpane, BorderLayout.CENTER);		

		return pnlHaut;
	}
	
	private JPanel getReglements() {
		
		JpnlContainer2 pnlHaut = new JpnlContainer2("Règlements");
		
		gridReglement.setModel(new TableModelSDW(ReglementClientDAO.getColumnClassPourFicheClient()));

		gridReglement.setData(ReglementClientDAO.getSQLFicheTiers(this.ID), "");

		JSDWScrollPane scrollpane = new JSDWScrollPane(gridReglement);
		
		
		cmdAjouterReglement.addActionListener(this);
		cmdModifierReglement.addActionListener(this);
		cmdSupprimerReglement.addActionListener(this);
		
		JPanel pnlMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

		pnlMenu.add(cmdAjouterReglement);
		pnlMenu.add(cmdModifierReglement);
		pnlMenu.add(cmdSupprimerReglement);
		
		pnlHaut.pnlContenant.add(scrollpane, BorderLayout.CENTER);		
		pnlHaut.pnlContenant.add(pnlMenu, BorderLayout.SOUTH);		

		return pnlHaut;
	}

	protected void createGUI() {		
		super.createGUI();
				
		onglets.setBounds(40,20,300,300);
	    onglets.add("Client", this.getRenseignementsGeneraux());
	    onglets.add("Adresses", this.getAdresses());
	    onglets.add("Liste des Commandes", this.getCommandes());
	    onglets.add("Liste des Bons de Livraison", this.getLivraisons());
	    onglets.add("Liste des Factures", this.getFactures());
	    onglets.add("Liste des Règlements", this.getReglements());
	    
		pnlCorps.add(this.getEnteteTiers(), BorderLayout.NORTH);
		pnlCorps.add(this.onglets, BorderLayout.CENTER);
	}

	public UIFicheTiers(JDialog parentFrame, String mode, String ID) {
		
		super(parentFrame, mode, ID, "FICHE TIERS", "fr.gclo.beans.Tiers", "fr.gclo.modeles.TiersDAO");
		
		this.createGUI();
		
		this.createMenu();
		
		ControllerSaisieObligatoire.getInstance().designChampObligatoire(this);
		
		this.showItem();
	}
	

	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if ( e.getSource() == cmdAjouterReglement ) {
//			UIFicheTiersReglement uif = new UIFicheTiersReglement(null, null, null);
//			uif.setVisible(true);			
		}
		else if ( e.getSource() == cmdModifierReglement ) {
			String dummy = (String)gridReglement.getValueAt(gridReglement.getSelectedRow(), 0);
			UIFicheTiersReglement uif = new UIFicheTiersReglement(this, "MODIFICATION", dummy);
			uif.setVisible(true);			
		}
		else if ( e.getSource() == cmdSupprimerReglement ) {
//			UIFicheCommandeArticle uif = new UIFicheCommandeArticle(null, null, null);
//			uif.setVisible(true);			
		}
		else if ( e.getSource() == cmdModifierAutreAdresse ) {
			String dummy = (String)gridAutreAdresse.getValueAt(gridAutreAdresse.getSelectedRow(), 0);
			UIFicheTiersAutreAdresse uif = new UIFicheTiersAutreAdresse(this, "MODIFICATION", dummy);
			uif.setVisible(true);			
		}
	}	

}
