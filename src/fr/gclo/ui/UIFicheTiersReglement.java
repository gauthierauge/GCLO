package fr.gclo.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import fr.gclo.controller.ControllerSaisieObligatoire;
import fr.gclo.modeles.CommandeDAO;
import fr.gclo.modeles.PaysDAO;
import fr.gclo.modeles.TiersDAO;
import fr.gclo.ui.composants.JSDWDateChooser;
import fr.gclo.ui.composants.JSDWPanel;
import fr.gclo.ui.composants.JTagComboBox;
import fr.gclo.ui.composants.JTagTextField;
import fr.gclo.ui.composants.JlblEtiquette;
import fr.gclo.ui.composants.JpnlContainer;
import net.java.dev.designgridlayout.DesignGridLayout;

@SuppressWarnings("serial")
public class UIFicheTiersReglement extends UIDialogFiche {

	private JlblEtiquette 			lblDateReglementClient		= new JlblEtiquette("Date", "lblDateReglementClient");
	private JSDWDateChooser  		txtDateReglementClient		= new JSDWDateChooser("DateReglementClient");		
	
	private JlblEtiquette 			lblLibelleReglementClient 	= new JlblEtiquette("Libelle", "lblLibelleReglementClient");
	private JTagTextField 			txtLibelleReglementClient	= new JTagTextField();
	
	private JlblEtiquette 			lblSaison 					= new JlblEtiquette("Saison", "lblSaison");
	private JTagTextField 			txtSaison 					= new JTagTextField("Saison", this);
	private JButton 				cmdSelectionSaison 			= new JButton("...");

	private JlblEtiquette 			lblCodeTiers  				= new JlblEtiquette("Code",			"lblCodeTiers");
    private JTagComboBox<String>	txtCodeTiers				= new JTagComboBox<String>("txtCodeTiers", TiersDAO.getVecteurCodeTiersDAO());

	private JlblEtiquette 			lblRaisonSocialeTiers  		= new JlblEtiquette("Client",			"lblRaisonSocialeTiers");
	private JTagTextField 			txtRaisonSocialeTiers 		= new JTagTextField();
	
	private JlblEtiquette 			lblAdresse1Tiers  			= new JlblEtiquette("Adresse",			"lblAdresse1Tiers");
	private JTagTextField 			txtAdresse1Tiers 			= new JTagTextField();
	
	private JTagTextField 			txtAdresse2Tiers 			= new JTagTextField();
	
	private JTagTextField 			txtCodePostalTiers 			= new JTagTextField();
	
	private JTagTextField 			txtVilleTiers 				= new JTagTextField();
	
 	private JlblEtiquette 			lblPaysTiers  				= new JlblEtiquette("Pays",			"lblPaysTiers");
    private JTagComboBox<String>	txtPaysTiers				= new JTagComboBox<String>("txtPaysTiers", PaysDAO.getVecteurPaysDAO());
	
	private JlblEtiquette 			lblCodeCommande 			= new JlblEtiquette("Code Commande", "lblCodeCommande");
    private JTagComboBox<String>	txtCodeCommande				= new JTagComboBox<String>("txtCodeCommande", CommandeDAO.getVecteurCodeCommandeDAO());
	
	private JlblEtiquette 			lblMontantReglementClient 	= new JlblEtiquette("Montant", "lblMontantReglementClient");
    private JTagTextField			txtMontantReglementClient	= new JTagTextField();
	
	private JlblEtiquette 			lblCommentaireReglementClient 	= new JlblEtiquette("Commentaires", "lblCommentaireReglementClient");
    private JTagTextField			txtCommentaireReglementClient	= new JTagTextField();
	
	private JlblEtiquette 			lblTypeReglementClient 	= new JlblEtiquette("Type", "lblTypeReglementClient");
    private JTagTextField			txtTypeReglementClient	= new JTagTextField();

	private JlblEtiquette 			lblDateEcheanceReglementClient 	= new JlblEtiquette("Date d'Echéance", "lblDateEcheanceReglementClient");
	private JSDWDateChooser  		txtDateEcheanceReglementClient  = new JSDWDateChooser("DateEcheanceReglementClient");		

	private JlblEtiquette 			lblEncaisseReglementClient 	= new JlblEtiquette("Encaissé", "lblEncaisseReglementClient");
	private JCheckBox 				txtEncaisseReglementClient 	    = new JCheckBox();


	private JTabbedPane 			onglets 					= new JTabbedPane();
	
	protected void valider() {		
		if ( ! ControllerSaisieObligatoire.getInstance().saisieRenseignee(this) ) return;		
		super.valider();
	}
	
	private JPanel getEnteteTiersReglement() {
		
		JSDWPanel pnlDummy = new JSDWPanel();
		
		layout = new DesignGridLayout(pnlDummy);
		
		layout.row().grid(lblDateReglementClient).add(txtDateReglementClient).grid(lblLibelleReglementClient).add(txtLibelleReglementClient).grid(2);//.grid(lblTitreTiers).add(txtTitreTiers).grid(1);
		layout.row().grid(lblSaison).add(txtSaison).add(cmdSelectionSaison).grid(1);


		return pnlDummy;	
	}
	
	private JPanel getCaracteristiques() {
	
		JPanel pnlAdresses = new JPanel(new BorderLayout());
		
		JpnlContainer pnlHaut = new JpnlContainer("Client");
		pnlHaut.layoutHaut.row().grid(lblCodeTiers).add(txtCodeTiers).grid(lblRaisonSocialeTiers).add(txtRaisonSocialeTiers).grid(3);
		pnlHaut.layoutHaut.emptyRow();
		pnlHaut.layoutHaut.row().grid(lblAdresse1Tiers).add(txtAdresse1Tiers).empty();
		pnlHaut.layoutHaut.row().grid(1).add(txtAdresse2Tiers).empty();
		pnlHaut.layoutHaut.row().grid(1).add(txtCodePostalTiers).add(txtVilleTiers).grid(4);
		pnlHaut.layoutHaut.row().grid(lblPaysTiers).add(txtPaysTiers).grid(2);

		
		JpnlContainer pnlBas = new JpnlContainer("Détail du Règlement");
		pnlBas.layoutHaut.row().grid(lblCodeCommande).add(txtCodeCommande).grid(3);
		pnlBas.layoutHaut.row().grid(lblMontantReglementClient).add(txtMontantReglementClient).grid(3);
		pnlBas.layoutHaut.row().grid(lblCommentaireReglementClient).add(txtCommentaireReglementClient).grid(3);
		pnlBas.layoutHaut.row().grid(lblTypeReglementClient).add(txtTypeReglementClient).grid(lblDateEcheanceReglementClient).add(txtDateEcheanceReglementClient).grid(lblEncaisseReglementClient).add(txtEncaisseReglementClient).grid(3);
		
		pnlAdresses.add(pnlHaut, BorderLayout.NORTH);
		pnlAdresses.add(pnlBas, BorderLayout.CENTER);
		
		return pnlAdresses;
	}

	protected void createGUI() {		
		super.createGUI();
				
		onglets.setBounds(40,20,300,300);
	    onglets.add("Caractéristiques", this.getCaracteristiques());
	    
		pnlCorps.add(this.getEnteteTiersReglement(), BorderLayout.NORTH);
		pnlCorps.add(this.onglets, BorderLayout.CENTER);
	}

	public UIFicheTiersReglement(JDialog parentFrame, String mode, String ID) {		
		super(parentFrame, mode, ID, "FICHE REGLEMENT CLIENT", "fr.gclo.beans.ReglementClient", "fr.gclo.modeles.ReglementClientDAO");
		
		this.createGUI();
		
		ControllerSaisieObligatoire.getInstance().designChampObligatoire(this);
		
		this.showItem();
	}
}