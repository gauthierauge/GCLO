package fr.gclo.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import fr.gclo.controller.ControllerSaisieObligatoire;
import fr.gclo.edition.BonDeCommande;
import fr.gclo.edition.ProForma;
import fr.gclo.modeles.BanqueDAO;
import fr.gclo.modeles.CommandeLigneDAO;
import fr.gclo.modeles.CommandeModaliteDeReglementDAO;
import fr.gclo.modeles.DeviseDAO;
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
import fr.gclo.ui.composants.JpnlContainerNoLayout;
import fr.gclo.ui.composants.JtblTable;
import fr.gclo.ui.composants.TableModelSDW;
import fr.gclo.ui.composants.JSDWDateChooser;
import net.java.dev.designgridlayout.DesignGridLayout;
import net.java.dev.designgridlayout.LabelAlignment;

public class UIFicheCommande extends UIDialogFiche implements ActionListener {
	
	private static final long 		serialVersionUID 					= 1L;
	
	private JTagTextField 			txtCodeCommande 					= new JTagTextField("CodeCommande", 	 		this);
	private JTagTextField 			txtLibelleCommande 					= new JTagTextField();
	private JSDWDateChooser  		txtDateCommande 					= new JSDWDateChooser("DateCommande");		
	private JTagTextField 			txtSaisonCommande 					= new JTagTextField("SaisonCommande", 	 		this);
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
	private JTagComboBox<String>	txtDeviseCommande					= new JTagComboBox<String>("txtDeviseCommande", DeviseDAO.getVecteurDeviseDAO());
	private JTagTextField 			txtNbArticleCommande 				= new JTagTextField();
	private JTagTextField 			txtMontantBrutHTCommande 			= new JTagTextField();
    private JTagComboBox<String>	txtRealisation						= new JTagComboBox<String>("txtRealisation", RealisationDAO.getVecteurRealisationDAO());
	private JTagComboBox<String> 	txtLangueDeCorrespondance			= new JTagComboBox<String>("LangueDeCorrespondance", LangueDeCorrespondanceDAO.getVecteurLangueDeCorrespondanceDAO());
	private JCheckBox 				txtExonerationTVA 					= new JCheckBox();
	private JCheckBox 				txtExonerationTPF 					= new JCheckBox();
	
	private JlblEtiquette 			lblFraisDePortHT					= new JlblEtiquette("Frais de Port (H.T.)",	"lblFraisDePortHT");
	private JTagTextField 			txtFraisDePortHT 					= new JTagTextField();
	private JlblEtiquette 			lblTauxTvaFraisDePort				= new JlblEtiquette("Taux de TVA / Frais de Port (%)",	"lblTauxTvaFraisDePort");
	private JTagTextField 			txtTauxTvaFraisDePort 				= new JTagTextField();
	private JlblEtiquette 			lblMontantTvaFraisDePort				= new JlblEtiquette("Montant TVA / Frais de Port",	"lblMontantTvaFraisDePort");
	private JTagTextField 			txtMontantTvaFraisDePort 				= new JTagTextField();
	

	private JlblEtiquette 			lblCodeCommande 					= new JlblEtiquette("Code",				"lblCodeCommande");
	private JlblEtiquette 			lblLibelleCommande 					= new JlblEtiquette("Description",		"lblLibelleCommande");
	private JlblEtiquette 			lblDateCommande 					= new JlblEtiquette("Date",				"lblDateCommande");
	private JlblEtiquette 			lblSaisonCommande 					= new JlblEtiquette("Saison",			"lblSaisonCommande");
	private JlblEtiquette 			lblCodeTiers  						= new JlblEtiquette("Code",			"lblCodeTiers");
	private JlblEtiquette 			lblRaisonSocialeTiers  				= new JlblEtiquette("Client",			"lblRaisonSocialeTiers");
	private JlblEtiquette 			lblAdresse1Tiers  					= new JlblEtiquette("Adresse",			"lblAdresse1Tiers");
	private JlblEtiquette 			lblAdresse2Tiers  					= new JlblEtiquette("",			"lblAdresse2Tiers");
	private JlblEtiquette 			lblCodePostalTiers  				= new JlblEtiquette("",			"lblCodePostalTiers");
	//private JlblEtiquette 			lblVilleTiers  					= new JlblEtiquette("",			"lblVilleTiers");
	private JlblEtiquette 			lblPaysTiers  						= new JlblEtiquette("Pays",			"lblPaysTiers");
	private JlblEtiquette 			lblTelephone1Tiers  				= new JlblEtiquette("Téléphone",			"lblTelephone1Tiers");
	private JlblEtiquette 			lblTelephone2Tiers  				= new JlblEtiquette("Portable",			"lblTelephone2Tiers");
	private JlblEtiquette 			lblTelephone3Tiers  				= new JlblEtiquette("Tél. Prof.",			"lblTelephone2Tiers");
	private JlblEtiquette 			lblAdresseDeMessagerieTiers 		= new JlblEtiquette("e-mail",			"lblAdresseDeMessagerieTiers");
	private JlblEtiquette 			lblDeviseCommande 					= new JlblEtiquette("Devise",			"lblDeviseCommande");
	private JlblEtiquette 			lblNbArticleCommande 				= new JlblEtiquette("Nombre Total d'Articles",			"lblNbArticleCommande");
	private JlblEtiquette 			lblMontantBrutHTCommande 			= new JlblEtiquette("Montant Total Brut H.T.",			"lblMontantBrutHTCommande");
	private JlblEtiquette 			lblRealisation 						= new JlblEtiquette("Réalisé",			"lblRealisation");
	private JlblEtiquette 			lblLangueDeCorrespondance 			= new JlblEtiquette("Langue de Correspondance",	"lblLangueDeCorrespondance");
	private JlblEtiquette 			lblExonerationTVA					= new JlblEtiquette("Exonération de T.V.A.",	"lblExonerationTVA");
	private JlblEtiquette 			lblExonerationTPF					= new JlblEtiquette("Exonération de T.P.F.",	"lblExonerationTPF");
	
	private JlblEtiquette 			lblPeriodeDeLivraison				= new JlblEtiquette("Periode de Livraison",	"lblPeriodeDeLivraison");
	private JTagTextField 			txtPeriodeDeLivraison 				= new JTagTextField();
	
	private JlblEtiquette 			lblDateEcheanceEnvoiCheque			= new JlblEtiquette("Date d'Echéance d'Envoi des Chèques (si pas d'acompte)",	"lblDateEcheanceEnvoiCheque");
	private JSDWDateChooser			txtDateEcheanceEnvoiCheque 			= new JSDWDateChooser("DateEcheanceEnvoiCheque");
	
	private JlblEtiquette 			lblMontantTotalCommandeTTC			= new JlblEtiquette("Montant Total de Commande (T.T.C.)",	"lblMontantTotalCommandeTTC");
	private JTagTextField 			txtMontantTotalCommandeTTC 			= new JTagTextField();
	private JlblEtiquette 			lblDeltaCommandeReglement			= new JlblEtiquette("Delta Montant Commande/ Ligne de Règlement",	"lblDeltaCommandeReglement");
	private JTagTextField 			txtDeltaCommandeReglement 			= new JTagTextField();
	private JlblEtiquette 			lblBanquePlanDeFinancement			= new JlblEtiquette("Banque (si virement)",	"lblBanquePlanDeFinancement");
	private JTagComboBox<String> 	txtBanquePlanDeFinancement			= new JTagComboBox<String>("", BanqueDAO.getVecteurCodeBanqueDAO());
	private JlblEtiquette 			lblMontantEscompteEnvisage			= new JlblEtiquette("Montant Escompte envisagé (%)",	"lblMontantEscompteEnvisage");
	private JTagTextField 			txtMontantEscompteEnvisage 			= new JTagTextField();
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
	

	public JButton 					cmdAjouter 							= new JButton("AJOUTER");
	public JButton 					cmdModifier 						= new JButton("MODIFIER");
	public JButton 					cmdSupprimer 						= new JButton("SUPPRIMER");
	public JButton 					cmdImprimer 						= new JButton("IMPRIMER");
	
	public JButton 					cmdReglementstandardPlanDeFinancement 		= new JButton("REGLEMENT STANDARD");
	public JButton 					cmdAjouterPlanDeFinancement 				= new JButton("AJOUTER");
	public JButton 					cmdModifierPlanDeFinancement 				= new JButton("MODIFIER");
	public JButton 					cmdSupprimerPlanDeFinancement 				= new JButton("SUPPRIMER");
	public JButton 					cmdRecalculerPlanDeFinancement 				= new JButton("RECALCULER");
	
	private JtblTable jtblLigneDeCommande = new JtblTable();

	public void createPopUpEditionFiche() {
		
        JMenuItem mi1 = new JMenuItem("Bon de Commande", new ImageIcon("images/newproject.png"));
        mi1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String nomDuDocumentPDF = "C:/Temp/Commande.pdf";
            	try {
					new BonDeCommande().generateJasper(nomDuDocumentPDF);
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        });
        
        JMenuItem mi2 = new JMenuItem("Pro-Forma", new ImageIcon("images/newproject.png"));
        mi2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String nomDuDocumentPDF = "C:/Temp/ProForma.pdf";
            	try {
					new ProForma().generateJasper(nomDuDocumentPDF);
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        });
        
        popup.add(mi1);
        popup.add(mi2);
	}

	protected void valider() {
		
		if ( ! ControllerSaisieObligatoire.getInstance().saisieRenseignee(this) ) return;
		
		super.valider();
	}
	
	private JPanel getEnteteCommande() {
		
		JSDWPanel pnlDummy = new JSDWPanel();
		pnlDummy.setLayout(null);
		pnlDummy.setPreferredSize(new Dimension(200, 50));
		
		lblCodeCommande.setBounds(20, 10, 100, 25);
		txtCodeCommande.setBounds(70, 10, 50, 25);
		lblLibelleCommande.setBounds(160, 10, 100, 25);
		txtLibelleCommande.setBounds(240, 10, 300, 25);
		lblDateCommande.setBounds(570, 10, 100, 25);
		txtDateCommande.setBounds(620, 10, 150, 25);
		
		txtCodeCommande.setEditable(false);
		txtCodeCommande.setEnabled(false);

		pnlDummy.add(lblCodeCommande);
		pnlDummy.add(txtCodeCommande);
		pnlDummy.add(lblLibelleCommande);
		pnlDummy.add(txtLibelleCommande);
		pnlDummy.add(lblDateCommande);
		pnlDummy.add(txtDateCommande);

//		layout = new DesignGridLayout(pnlDummy);
//		
//		layout.row().grid(lblCodeCommande).add(txtCodeCommande).empty(1).grid(lblLibelleCommande).add(txtLibelleCommande).grid(lblDateCommande).add(txtDateCommande).grid(5);

		return pnlDummy;
	}
	
	private JPanel getCommande() {
		
		JPanel pnlDummy = new JPanel(new BorderLayout());
		
		JpnlContainerNoLayout pnlHaut = new JpnlContainerNoLayout("Caractéristiques");
		//JpnlContainer pnlHaut = new JpnlContainer("Caractéristiques");
		JpnlContainer pnlBas = new JpnlContainer("Client");

		cmdSelectionSaison.addActionListener(this);
		
		pnlDummy.add(pnlHaut, BorderLayout.NORTH);
		lblSaisonCommande.setBounds(20, 10, 100, 25);
		txtSaisonCommande.setBounds(80, 10, 200, 25);
		cmdSelectionSaison.setBounds(300, 10, 30, 25);
		lblRealisation.setBounds(400, 10, 80, 25);
		txtRealisation.setBounds(460, 10, 200, 25);
		lblLangueDeCorrespondance.setBounds(680, 10, 200, 25);
		txtLangueDeCorrespondance.setBounds(860, 10, 200, 25);
		pnlHaut.pnlContenant.add(lblSaisonCommande);
		pnlHaut.pnlContenant.add(txtSaisonCommande);
		pnlHaut.pnlContenant.add(cmdSelectionSaison);
		pnlHaut.pnlContenant.add(lblRealisation);
		pnlHaut.pnlContenant.add(txtRealisation);
		pnlHaut.pnlContenant.add(lblLangueDeCorrespondance);
		pnlHaut.pnlContenant.add(txtLangueDeCorrespondance);
		
		lblExonerationTVA.setBounds(20, 45, 140, 25);
		txtExonerationTVA.setBounds(160, 45, 20, 25);
		lblExonerationTPF.setBounds(200, 45, 140, 25);
		txtExonerationTPF.setBounds(340, 45, 20, 25);
		lblDeviseCommande.setBounds(400, 45, 80, 25);
		txtDeviseCommande.setBounds(460, 45, 140, 25);
		pnlHaut.pnlContenant.add(lblExonerationTVA);
		pnlHaut.pnlContenant.add(txtExonerationTVA);
		pnlHaut.pnlContenant.add(lblExonerationTPF);
		pnlHaut.pnlContenant.add(txtExonerationTPF);
		pnlHaut.pnlContenant.add(lblDeviseCommande);
		pnlHaut.pnlContenant.add(txtDeviseCommande);
		pnlHaut.setPreferredSize(new Dimension(200, 120));
		
	
//		pnlHaut.layoutHaut.row().grid(lblSaisonCommande).add(txtSaisonCommande).add(cmdSelectionSaison).grid(lblRealisation).add(txtRealisation).grid(lblLangueDeCorrespondance).add(txtLangueDeCorrespondance).grid(3);
//		pnlBas.layoutHaut.emptyRow();
//		pnlHaut.layoutHaut.row().grid(lblExonerationTVA).add(txtExonerationTVA).grid(lblExonerationTPF).add(txtExonerationTPF).grid(lblDeviseCommande).add(txtDeviseCommande).grid(2);
		
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
	
	private JPanel getLigneDeCommande() {
		
		JpnlContainer2 pnlLigneDeCommande = new JpnlContainer2("Lignes de Commande");
		
		jtblLigneDeCommande.setModel(new TableModelSDW(CommandeLigneDAO.getColumnClassPourFicheClient()));
		jtblLigneDeCommande.setData(CommandeLigneDAO.getSQLFicheCommande(this.ID), "");
		JSDWScrollPane scrollpane = new JSDWScrollPane(jtblLigneDeCommande);
		
		JPanel pnlMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		pnlMenu.add(cmdAjouter);	
		pnlMenu.add(cmdModifier);
		pnlMenu.add(cmdSupprimer);
		cmdAjouter.addActionListener(this);
		cmdModifier.addActionListener(this);
		cmdSupprimer.addActionListener(this);
		
		pnlLigneDeCommande.pnlContenant.add(scrollpane, BorderLayout.CENTER);		
		pnlLigneDeCommande.pnlContenant.add(pnlMenu, BorderLayout.SOUTH);		

		return pnlLigneDeCommande;
	}
	
	private JPanel getPiedDeCommande() {
		
		JpnlContainer pnlPiedDeCommande = new JpnlContainer("Pied de Commande");
		pnlPiedDeCommande.layoutHaut.row().grid(lblNbArticleCommande).add(txtNbArticleCommande).grid(3);
		pnlPiedDeCommande.layoutHaut.row().grid(lblMontantBrutHTCommande).add(txtMontantBrutHTCommande);
		pnlPiedDeCommande.layoutHaut.row().grid(lblFraisDePortHT).add(txtFraisDePortHT).grid(lblTauxTvaFraisDePort).add(txtTauxTvaFraisDePort).grid(lblMontantTvaFraisDePort).add(txtMontantTvaFraisDePort);
		pnlPiedDeCommande.layoutHaut.row().grid(lblTauxTaxeParafiscale).add(txtTauxTaxeParafiscale).grid(lblMontantTaxeParafiscale).add(txtMontantTaxeParafiscale).grid(lblTauxTvaTaxeParafiscale).add(txtTauxTvaTaxeParafiscale).grid(lblMontantTvaTaxeParafiscale).add(txtMontantTvaTaxeParafiscale);
		pnlPiedDeCommande.layoutHaut.row().grid(lblPourcentageEscompte).add(txtPourcentageEscompte).grid(3);
		return pnlPiedDeCommande;
	}
	
	private JPanel getPlanDeFinancement() {

		JPanel pnlPlanDeFinancementMain = new JPanel(new BorderLayout());

		JpnlContainer2 pnlPlanDeFinancement = new JpnlContainer2("Plan de Financement");
		
		JPanel pnlEntetePlanDeFinancement = new JPanel();
		DesignGridLayout lytEntetePlanDeFinancement = new DesignGridLayout(pnlEntetePlanDeFinancement);
		lytEntetePlanDeFinancement.labelAlignment(LabelAlignment.RIGHT);
		lytEntetePlanDeFinancement.row().grid(lblPeriodeDeLivraison).add(txtPeriodeDeLivraison).grid(4);
		lytEntetePlanDeFinancement.row().grid(lblDateEcheanceEnvoiCheque).add(txtDateEcheanceEnvoiCheque).grid(lblBanquePlanDeFinancement).add(txtBanquePlanDeFinancement).grid(lblMontantEscompteEnvisage).add(txtMontantEscompteEnvisage).grid(2);
		
		JtblTable grid = new JtblTable();	
		grid.setModel(new TableModelSDW(CommandeModaliteDeReglementDAO.getColumnClassPourFicheCommande()));
		grid.setData(CommandeModaliteDeReglementDAO.getSQLFicheCommande(this.ID), "");
		JSDWScrollPane scrollpane = new JSDWScrollPane(grid);
		
		JPanel pnlMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		cmdReglementstandardPlanDeFinancement.addActionListener(this);
		cmdAjouterPlanDeFinancement.addActionListener(this);
		cmdModifierPlanDeFinancement.addActionListener(this);
		cmdSupprimerPlanDeFinancement.addActionListener(this);
		pnlMenu.add(cmdReglementstandardPlanDeFinancement);	
		pnlMenu.add(cmdAjouterPlanDeFinancement);
		pnlMenu.add(cmdModifierPlanDeFinancement);
		pnlMenu.add(cmdSupprimerPlanDeFinancement);
		pnlMenu.add(cmdRecalculerPlanDeFinancement);

		pnlPlanDeFinancement.pnlContenant.add(pnlEntetePlanDeFinancement, BorderLayout.NORTH);		
		pnlPlanDeFinancement.pnlContenant.add(scrollpane, BorderLayout.CENTER);		
		pnlPlanDeFinancement.pnlContenant.add(pnlMenu, BorderLayout.SOUTH);	
		
		JpnlContainer2 pnlPlanDeFinancementControle = new JpnlContainer2("Contrôle");

		JPanel pnlPlanDeFinancementControleDetail = new JPanel();
		DesignGridLayout lytPlanDeFinancementControleDetail = new DesignGridLayout(pnlPlanDeFinancementControleDetail);
		lytPlanDeFinancementControleDetail.labelAlignment(LabelAlignment.RIGHT);
		lytPlanDeFinancementControleDetail.row().grid(lblMontantTotalCommandeTTC).add(txtMontantTotalCommandeTTC).grid(lblDeltaCommandeReglement).add(txtDeltaCommandeReglement).grid(3);
		pnlPlanDeFinancementControle.pnlContenant.add(pnlPlanDeFinancementControleDetail, BorderLayout.CENTER);		

		pnlPlanDeFinancementMain.add(pnlPlanDeFinancement, BorderLayout.CENTER);
		pnlPlanDeFinancementMain.add(pnlPlanDeFinancementControle, BorderLayout.SOUTH);
	
	    return pnlPlanDeFinancementMain;
	}
	
	private JPanel getReglement() {

		JPanel pnlReglementMain = new JPanel(new BorderLayout());
		
		JpnlContainer2 pnlReglement = new JpnlContainer2("Règlements");
		
		JtblTable grid = new JtblTable();	
		grid.setModel(new TableModelSDW(CommandeLigneDAO.getColumnClassPourFicheClient()));
		grid.setData(CommandeLigneDAO.getSQLFicheCommande(this.ID), "");
		JSDWScrollPane scrollpane = new JSDWScrollPane(grid);
		
		JPanel pnlMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		JButton cmdAjouterReglement 	= new JButton("AJOUTER");
		JButton cmdModifierReglement 	= new JButton("MODIFIER");
		JButton cmdSupprimerReglement 	= new JButton("SUPPRIMER");
		cmdAjouterReglement.addActionListener(this);
		cmdModifierReglement.addActionListener(this);
		cmdSupprimerReglement.addActionListener(this);
		pnlMenu.add(cmdAjouterReglement);
		pnlMenu.add(cmdModifierReglement);
		pnlMenu.add(cmdSupprimerReglement);
		
		pnlReglement.pnlContenant.add(scrollpane, BorderLayout.CENTER);		
		pnlReglement.pnlContenant.add(pnlMenu, BorderLayout.SOUTH);		
		
		JpnlContainer2 pnlReglementControle = new JpnlContainer2("Contrôle");

		JPanel pnlReglementControleDetail = new JPanel();
		DesignGridLayout lytReglementControleDetail = new DesignGridLayout(pnlReglementControleDetail);
		lytReglementControleDetail.labelAlignment(LabelAlignment.RIGHT);
		//lytReglementControleDetail.row().grid(lblMontantTotalCommandeTTC).add(txtMontantTotalCommandeTTC).grid(3);
		pnlReglementControle.pnlContenant.add(pnlReglementControleDetail, BorderLayout.CENTER);		
		
		pnlReglementMain.add(pnlReglement, BorderLayout.CENTER);
		pnlReglementMain.add(pnlReglementControle, BorderLayout.SOUTH);
	
	    return pnlReglementMain;
	}
	
	protected void createMenu() {		
		super.createMenu();
		
		cmdImprimer.addActionListener(this);

		pnlMenu.add(separateur_1);
		pnlMenu.add(cmdImprimer);	
	}	

	protected void createGUI() {		
		super.createGUI();

		JTabbedPane onglets = new JTabbedPane();

		onglets.setBounds(40,20,300,300);
	    onglets.add("Entete", this.getCommande());
	    onglets.add("Lignes de Commande", this.getLigneDeCommande());
	    onglets.add("Pied de Commande", this.getPiedDeCommande());
	    onglets.add("Plan de Financement", this.getPlanDeFinancement());
	    onglets.add("Reglements", this.getReglement());
	    
		pnlCorps.add(this.getEnteteCommande(), BorderLayout.NORTH);
		pnlCorps.add(onglets, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		
		if ( e.getSource() == cmdAjouter ) {
			UIFicheCommandeArticle uif = new UIFicheCommandeArticle(null, null, null, txtSaisonCommande.getText());
			uif.setVisible(true);			
		}
		else if ( e.getSource() == cmdModifier ) {
			if ( jtblLigneDeCommande.getSelectedRow() == -1 ) {
	            JOptionPane.showMessageDialog(null, "Aucun item de selectionne...", "Information", JOptionPane.INFORMATION_MESSAGE);	                
	            return;
			}

			UIFicheCommandeArticle uif = new UIFicheCommandeArticle(null, "MODIFICATION", (String)jtblLigneDeCommande.getValueAt(jtblLigneDeCommande.getSelectedRow(), 0), txtSaisonCommande.getText());
			uif.setVisible(true);			
		}
		else if ( e.getSource() == cmdSupprimer ) {
		}
		else if (e.getSource() == cmdSelectionSaison) {
			UISelectionSaison uiselectionsaison = new UISelectionSaison(null);
			uiselectionsaison.setVisible(true);
		}
		else if (e.getSource() == cmdReglementstandardPlanDeFinancement) {
		}
		else if ( e.getSource() == cmdImprimer ) {
			popup.show(cmdImprimer, 0, cmdImprimer.getBounds().height);
		}
	}

	public UIFicheCommande(JDialog parentFrame, String mode, String ID) {
		
		super(parentFrame, mode, ID, "FICHE COMMANDE", "fr.gclo.beans.Commande", "fr.gclo.modeles.CommandeDAO");
		
		this.createGUI();
		
		this.createMenu();
		
		this.createPopUpEditionFiche();

		ControllerSaisieObligatoire.getInstance().designChampObligatoire(this);
		
		this.showItem();
	}
}
