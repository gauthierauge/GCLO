package fr.gclo.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import fr.gclo.controller.ControllerSaisieObligatoire;
import fr.gclo.modeles.DeviseDAO;
import fr.gclo.modeles.FactureLigneDAO;
import fr.gclo.modeles.LangueDeCorrespondanceDAO;
import fr.gclo.modeles.PaysDAO;
import fr.gclo.modeles.RealisationDAO;
import fr.gclo.modeles.TiersDAO;
import fr.gclo.ui.composants.JSDWPanel;
import fr.gclo.ui.composants.JSDWScrollPane;
import fr.gclo.ui.composants.JTagComboBox;
import fr.gclo.ui.composants.JTagTextField;
import fr.gclo.ui.composants.JlblEtiquette;
import fr.gclo.ui.composants.JpnlContainer;
import fr.gclo.ui.composants.JpnlContainer2;
import fr.gclo.ui.composants.JtblTable;
import fr.gclo.ui.composants.TableModelSDW;
import fr.gclo.ui.composants.JSDWDateChooser;
import net.java.dev.designgridlayout.DesignGridLayout;

public class UIFicheFacture extends UIDialogFiche implements ActionListener {
	
	private static final long 		serialVersionUID 					= 1L;
	
	private JTagTextField 			txtCodeFacture 						= new JTagTextField("CodeFacture", 	 		this);
	private JTagTextField 			txtLibelleFacture 					= new JTagTextField();
	private JSDWDateChooser  		txtDateFacture 						= new JSDWDateChooser("DateFacture");		
	private JTagTextField 			txtSaisonFacture 					= new JTagTextField("SaisonFacture", 	 		this);
	private JButton 				cmdSelectionSaison 					= new JButton("...");
    private JTagComboBox<String>	txtCodeTiers						= new JTagComboBox<String>("txtCodeTiers", TiersDAO.getVecteurCodeTiersDAO());

	private JTagTextField 			txtRaisonSocialeTiers 				= new JTagTextField();
	private JTagTextField 			txtAdresse1Tiers 					= new JTagTextField();
	private JTagTextField 			txtAdresse2Tiers 					= new JTagTextField();
	private JTagTextField 			txtCodePostalTiers 					= new JTagTextField();
	private JTagTextField 			txtVilleTiers 						= new JTagTextField();
    private JTagComboBox<String>	txtPaysTiers						= new JTagComboBox<String>("txtPaysTiers", PaysDAO.getVecteurPaysDAO());
	private JTagTextField 			txtTelephone1Tiers 					= new JTagTextField();
	private JTagTextField 			txtTelephone2Tiers 					= new JTagTextField();
	private JTagTextField 			txtTelephone3Tiers 					= new JTagTextField();
	private JTagTextField 			txtAdresseDeMessagerieTiers 		= new JTagTextField();
	private JTagComboBox<String>	txtDeviseFacture					= new JTagComboBox<String>("txtDeviseFacture", DeviseDAO.getVecteurDeviseDAO());
    private JTagComboBox<String>	txtRealisation						= new JTagComboBox<String>("txtRealisation", RealisationDAO.getVecteurRealisationDAO());
	private JTagComboBox<String> 	txtLangueDeCorrespondance			= new JTagComboBox<String>("LangueDeCorrespondance", LangueDeCorrespondanceDAO.getVecteurLangueDeCorrespondanceDAO());
	private JCheckBox 				txtExonerationTVA 					= new JCheckBox();
	private JCheckBox 				txtExonerationTPF 					= new JCheckBox();
	

	private JlblEtiquette 			lblCodeFacture 						= new JlblEtiquette("Code",				"lblCodeFacture");
	private JlblEtiquette 			lblLibelleFacture 					= new JlblEtiquette("Description",		"lblLibelleFacture");
	private JlblEtiquette 			lblDateFacture 						= new JlblEtiquette("Date",				"lblDateFacture");
	private JlblEtiquette 			lblSaisonFacture 					= new JlblEtiquette("Saison",			"lblSaisonFacture");
	private JlblEtiquette 			lblCodeTiers  						= new JlblEtiquette("Code",			"lblCodeTiers");
	private JlblEtiquette 			lblRaisonSocialeTiers  				= new JlblEtiquette("Client",			"lblRaisonSocialeTiers");
	private JlblEtiquette 			lblAdresse1Tiers  					= new JlblEtiquette("Adresse",			"lblAdresse1Tiers");
	private JlblEtiquette 			lblAdresse2Tiers  					= new JlblEtiquette("",			"lblAdresse2Tiers");
	private JlblEtiquette 			lblCodePostalTiers  				= new JlblEtiquette("",			"lblCodePostalTiers");
	private JlblEtiquette 			lblPaysTiers  						= new JlblEtiquette("Pays",			"lblPaysTiers");
	private JlblEtiquette 			lblTelephone1Tiers  				= new JlblEtiquette("Téléphone",			"lblTelephone1Tiers");
	private JlblEtiquette 			lblTelephone2Tiers  				= new JlblEtiquette("Portable",			"lblTelephone2Tiers");
	private JlblEtiquette 			lblTelephone3Tiers  				= new JlblEtiquette("Tél. Prof.",			"lblTelephone2Tiers");
	private JlblEtiquette 			lblAdresseDeMessagerieTiers 		= new JlblEtiquette("e-mail",			"lblAdresseDeMessagerieTiers");
	private JlblEtiquette 			lblDeviseFacture 					= new JlblEtiquette("Devise",			"lblDeviseFacture");
	private JlblEtiquette 			lblRealisation 						= new JlblEtiquette("Réalisé",			"lblRealisation");
	private JlblEtiquette 			lblLangueDeCorrespondance 			= new JlblEtiquette("Langue de Correspondance",	"lblLangueDeCorrespondance");
	private JlblEtiquette 			lblExonerationTVA					= new JlblEtiquette("Exonération de T.V.A.",	"lblExonerationTVA");
	private JlblEtiquette 			lblExonerationTPF					= new JlblEtiquette("Exonération de T.P.F.",	"lblExonerationTPF");
	
	// Onglet Pied de Facture
	private JlblEtiquette 			lblNbArticleFacture 				= new JlblEtiquette("Nombre Total d'Articles",			"lblNbArticleFacture");
	private JTagTextField 			txtNbArticleFacture 				= new JTagTextField();
	private JlblEtiquette 			lblMontantBrutHTFacture 			= new JlblEtiquette("Montant Total Brut H.T.",			"lblMontantBrutHTFacture");
	private JTagTextField 			txtMontantBrutHTFacture 			= new JTagTextField();
	private JlblEtiquette 			lblFraisDePortHT					= new JlblEtiquette("Frais de Port (H.T.)",	"lblFraisDePortHT");
	private JTagTextField 			txtFraisDePortHT 					= new JTagTextField();
	private JlblEtiquette 			lblTauxTvaFraisDePort				= new JlblEtiquette("Taux de TVA / Frais de Port (%)",	"lblTauxTvaFraisDePort");
	private JTagTextField 			txtTauxTvaFraisDePort 				= new JTagTextField();
	private JlblEtiquette 			lblMontantTvaFraisDePort				= new JlblEtiquette("Montant TVA / Frais de Port",	"lblMontantTvaFraisDePort");
	private JTagTextField 			txtMontantTvaFraisDePort 				= new JTagTextField();
	private JlblEtiquette 			lblTauxTaxeParafiscale				= new JlblEtiquette("Taux de Taxe Parafiscale (%)",	"lblTauxTaxeParafiscale");
	private JTagTextField 			txtTauxTaxeParafiscale 				= new JTagTextField();
	private JlblEtiquette 			lblMontantTaxeParafiscale			= new JlblEtiquette("Montant Taxe Parafiscale",	"lblMontantTaxeParafiscale");
	private JTagTextField 			txtMontantTaxeParafiscale 			= new JTagTextField();
	private JlblEtiquette 			lblTauxTvaTaxeParafiscale			= new JlblEtiquette("Taux TVA Taxe Parafiscale (%)",	"lblTauxTvaTaxeParafiscale");
	private JTagTextField 			txtTauxTvaTaxeParafiscale 			= new JTagTextField();
	private JlblEtiquette 			lblMontantTvaTaxeParafiscale		= new JlblEtiquette("Montant TVA Taxe Parafiscale",	"lblMontantTvaTaxeParafiscale");
	private JTagTextField 			txtMontantTvaTaxeParafiscale 		= new JTagTextField();
	private JlblEtiquette 			lblPourcentageEscompte				= new JlblEtiquette("Escompte (%)",	"lblPourcentageEscompte");
	private JTagTextField 			txtPourcentageEscompte 				= new JTagTextField();

	
	public JButton 					cmdAjouter 		= new JButton("AJOUTER");
	public JButton 					cmdModifier 	= new JButton("MODIFIER");
	public JButton 					cmdSupprimer 	= new JButton("SUPPRIMER");
	
	
	private JTabbedPane 			onglets 							= new JTabbedPane();
	
	protected void valider() {
		
		if ( ! ControllerSaisieObligatoire.getInstance().saisieRenseignee(this) ) return;
		
		super.valider();
	}
	
	private JPanel getEnteteFacture() {
		
		JSDWPanel pnlDummy = new JSDWPanel();
		
		txtCodeFacture.setEditable(false);
		txtCodeFacture.setEnabled(false);
		
		layout = new DesignGridLayout(pnlDummy);
		
		layout.row().grid(lblCodeFacture).add(txtCodeFacture).empty(1).grid(lblLibelleFacture).add(txtLibelleFacture).grid(lblDateFacture).add(txtDateFacture).grid(5);

		return pnlDummy;
	}
	
	private JPanel getFacture() {
		
		JPanel pnlDummy = new JPanel(new BorderLayout());
		
		JpnlContainer pnlHaut = new JpnlContainer("Caractéristiques");
		JpnlContainer pnlBas = new JpnlContainer("Client");

		cmdSelectionSaison.addActionListener(this);
		
		pnlDummy.add(pnlHaut, BorderLayout.NORTH);
		pnlHaut.layoutHaut.row().grid(lblSaisonFacture).add(txtSaisonFacture).add(cmdSelectionSaison).grid(lblRealisation).add(txtRealisation).grid(lblLangueDeCorrespondance).add(txtLangueDeCorrespondance).grid(3);
		pnlBas.layoutHaut.emptyRow();
		pnlHaut.layoutHaut.row().grid(lblExonerationTVA).add(txtExonerationTVA).grid(lblExonerationTPF).add(txtExonerationTPF).grid(lblDeviseFacture).add(txtDeviseFacture).grid(2);
		
		pnlDummy.add(pnlBas, BorderLayout.CENTER);
		pnlBas.layoutHaut.row().grid(lblCodeTiers).add(txtCodeTiers).grid(lblRaisonSocialeTiers).add(txtRaisonSocialeTiers).grid(3);
		pnlBas.layoutHaut.emptyRow();
		pnlBas.layoutHaut.row().grid(lblAdresse1Tiers).add(txtAdresse1Tiers).empty();
		pnlBas.layoutHaut.row().grid(lblAdresse2Tiers).add(txtAdresse2Tiers).empty();
		pnlBas.layoutHaut.row().grid(lblCodePostalTiers).add(txtCodePostalTiers).add(txtVilleTiers).grid(4);
		pnlBas.layoutHaut.row().grid(lblPaysTiers).add(txtPaysTiers).grid(2);
		pnlBas.layoutHaut.row().grid(lblTelephone1Tiers).add(txtTelephone1Tiers).add(lblTelephone2Tiers).add(txtTelephone2Tiers).add(lblTelephone3Tiers).add(txtTelephone3Tiers).empty();
		pnlBas.layoutHaut.row().grid(lblAdresseDeMessagerieTiers).add(txtAdresseDeMessagerieTiers).grid(2);

		return pnlDummy;
	}
	
	private JPanel getLigneDeFacture() {
		
		JpnlContainer2 pnlLigneDeFacture = new JpnlContainer2("Factures");
		
		JtblTable grid = new JtblTable();
		grid.setModel(new TableModelSDW(FactureLigneDAO.getColumnClassPourFicheClient()));
		grid.setData(FactureLigneDAO.getSQLFicheFacture(this.ID), "");
		JSDWScrollPane scrollpane = new JSDWScrollPane(grid);
		
		JPanel pnlMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		pnlMenu.add(cmdAjouter);	
		pnlMenu.add(cmdModifier);
		pnlMenu.add(cmdSupprimer);
		cmdAjouter.addActionListener(this);
		cmdModifier.addActionListener(this);
		cmdSupprimer.addActionListener(this);
		
		pnlLigneDeFacture.pnlContenant.add(scrollpane, BorderLayout.CENTER);		
		pnlLigneDeFacture.pnlContenant.add(pnlMenu, BorderLayout.SOUTH);		

		return pnlLigneDeFacture;
	}
	
	private JPanel getPiedDeFacture() {
		
		JpnlContainer pnlPiedDeFacture = new JpnlContainer("Pied de Facture");
		pnlPiedDeFacture.layoutHaut.row().grid(lblNbArticleFacture).add(txtNbArticleFacture).grid(3);
		pnlPiedDeFacture.layoutHaut.row().grid(lblMontantBrutHTFacture).add(txtMontantBrutHTFacture);
		pnlPiedDeFacture.layoutHaut.row().grid(lblFraisDePortHT).add(txtFraisDePortHT).grid(lblTauxTvaFraisDePort).add(txtTauxTvaFraisDePort).grid(lblMontantTvaFraisDePort).add(txtMontantTvaFraisDePort);
		pnlPiedDeFacture.layoutHaut.row().grid(lblTauxTaxeParafiscale).add(txtTauxTaxeParafiscale).grid(lblMontantTaxeParafiscale).add(txtMontantTaxeParafiscale).grid(lblTauxTvaTaxeParafiscale).add(txtTauxTvaTaxeParafiscale).grid(lblMontantTvaTaxeParafiscale).add(txtMontantTvaTaxeParafiscale);
		pnlPiedDeFacture.layoutHaut.row().grid(lblPourcentageEscompte).add(txtPourcentageEscompte).grid(3);
		
		return pnlPiedDeFacture;
	}
	
	protected void createGUI() {		
		super.createGUI();
				
		onglets.setBounds(40,20,300,300);
	    onglets.add("Entete", this.getFacture());
	    onglets.add("Lignes de Facture", this.getLigneDeFacture());
	    onglets.add("Pied de Facture", this.getPiedDeFacture());
	    
		pnlCorps.add(this.getEnteteFacture(), BorderLayout.NORTH);
		pnlCorps.add(this.onglets, BorderLayout.CENTER);
	}

	public UIFicheFacture(JDialog parentFrame, String mode, String ID) {
		
		super(parentFrame, mode, ID, "FICHE Facture", "fr.gclo.beans.Facture", "fr.gclo.modeles.FactureDAO");
		
		this.createGUI();
		
		ControllerSaisieObligatoire.getInstance().designChampObligatoire(this);
		
		this.showItem();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if ( e.getSource() == cmdAjouter ) {
			System.out.println("AJOUTER");
			//UIFicheFactureArticle uif = new UIFicheFactureArticle(null, null, null);
			//uif.setVisible(true);			
		}
		else if ( e.getSource() == cmdModifier ) {
			System.out.println("modifier");
		}
		else if ( e.getSource() == cmdSupprimer ) {
			System.out.println("SUPPRIMER");
		}
		else if (e.getSource() == cmdSelectionSaison) {
			UISelectionSaison uiselectionsaison = new UISelectionSaison(null);
			uiselectionsaison.setVisible(true);
		}
	}
}
