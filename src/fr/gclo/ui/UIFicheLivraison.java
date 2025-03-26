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
import fr.gclo.modeles.LivraisonLigneDAO;
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

public class UIFicheLivraison extends UIDialogFiche implements ActionListener {
	
	private static final long 		serialVersionUID 					= 1L;
	
	private JTagTextField 			txtCodeLivraison 						= new JTagTextField("CodeLivraison", 	 		this);
	private JTagTextField 			txtLibelleLivraison 					= new JTagTextField();
	private JSDWDateChooser  		txtDateLivraison 						= new JSDWDateChooser("DateLivraison");		
	private JTagTextField 			txtSaisonLivraison 					= new JTagTextField("SaisonLivraison", 	 		this);
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
	private JTagComboBox<String>	txtDeviseLivraison					= new JTagComboBox<String>("txtDeviseLivraison", DeviseDAO.getVecteurDeviseDAO());
	private JTagTextField 			txtNbArticleLivraison 				= new JTagTextField();
	private JTagTextField 			txtMontantBrutHTLivraison 			= new JTagTextField();
    private JTagComboBox<String>	txtRealisation						= new JTagComboBox<String>("txtRealisation", RealisationDAO.getVecteurRealisationDAO());
	private JTagComboBox<String> 	txtLangueDeCorrespondance			= new JTagComboBox<String>("LangueDeCorrespondance", LangueDeCorrespondanceDAO.getVecteurLangueDeCorrespondanceDAO());
	private JCheckBox 				txtExonerationTVA 					= new JCheckBox();
	private JCheckBox 				txtExonerationTPF 					= new JCheckBox();
	

	private JlblEtiquette 			lblCodeLivraison 						= new JlblEtiquette("Code",				"lblCodeLivraison");
	private JlblEtiquette 			lblLibelleLivraison 					= new JlblEtiquette("Description",		"lblLibelleLivraison");
	private JlblEtiquette 			lblDateLivraison 						= new JlblEtiquette("Date",				"lblDateLivraison");
	private JlblEtiquette 			lblSaisonLivraison 					= new JlblEtiquette("Saison",			"lblSaisonLivraison");
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
	private JlblEtiquette 			lblDeviseLivraison 					= new JlblEtiquette("Devise",			"lblDeviseLivraison");
	private JlblEtiquette 			lblNbArticleLivraison 				= new JlblEtiquette("Nombre Total d'Articles",			"lblNbArticleLivraison");
	private JlblEtiquette 			lblMontantBrutHTLivraison 			= new JlblEtiquette("Montant Total Brut H.T.",			"lblMontantBrutHTLivraison");
	private JlblEtiquette 			lblRealisation 						= new JlblEtiquette("Réalisé",			"lblRealisation");
	private JlblEtiquette 			lblLangueDeCorrespondance 			= new JlblEtiquette("Langue de Correspondance",	"lblLangueDeCorrespondance");
	private JlblEtiquette 			lblExonerationTVA					= new JlblEtiquette("Exonération de T.V.A.",	"lblExonerationTVA");
	private JlblEtiquette 			lblExonerationTPF					= new JlblEtiquette("Exonération de T.P.F.",	"lblExonerationTPF");
	
	public JButton 					cmdAjouter 		= new JButton("AJOUTER");
	public JButton 					cmdModifier 	= new JButton("MODIFIER");
	public JButton 					cmdSupprimer 	= new JButton("SUPPRIMER");
	
	
	private JTabbedPane 			onglets 							= new JTabbedPane();
	
	protected void valider() {
		
		if ( ! ControllerSaisieObligatoire.getInstance().saisieRenseignee(this) ) return;
		
		super.valider();
	}
	
	private JPanel getEnteteLivraison() {
		
		JSDWPanel pnlDummy = new JSDWPanel();
		
		txtCodeLivraison.setEditable(false);
		txtCodeLivraison.setEnabled(false);
		
		layout = new DesignGridLayout(pnlDummy);
		
		layout.row().grid(lblCodeLivraison).add(txtCodeLivraison).empty(1).grid(lblLibelleLivraison).add(txtLibelleLivraison).grid(lblDateLivraison).add(txtDateLivraison).grid(5);

		return pnlDummy;
	}
	
	private JPanel getLivraison() {
		
		JPanel pnlDummy = new JPanel(new BorderLayout());
		
		JpnlContainer pnlHaut = new JpnlContainer("Caractéristiques");
		JpnlContainer pnlBas = new JpnlContainer("Client");

		cmdSelectionSaison.addActionListener(this);
		
		pnlDummy.add(pnlHaut, BorderLayout.NORTH);
		pnlHaut.layoutHaut.row().grid(lblSaisonLivraison).add(txtSaisonLivraison).add(cmdSelectionSaison).grid(lblRealisation).add(txtRealisation).grid(lblLangueDeCorrespondance).add(txtLangueDeCorrespondance).grid(3);
		pnlBas.layoutHaut.emptyRow();
		pnlHaut.layoutHaut.row().grid(lblExonerationTVA).add(txtExonerationTVA).grid(lblExonerationTPF).add(txtExonerationTPF).grid(lblDeviseLivraison).add(txtDeviseLivraison).grid(2);
		
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
	
	private JPanel getLigneDeLivraison() {
		
		JpnlContainer2 pnlLigneDeLivraison = new JpnlContainer2("Lignes de Livraison");
		
		JtblTable grid = new JtblTable();
		grid.setModel(new TableModelSDW(LivraisonLigneDAO.getColumnClassPourFicheClient()));
		grid.setData(LivraisonLigneDAO.getSQLFicheLivraison(this.ID), "");
		JSDWScrollPane scrollpane = new JSDWScrollPane(grid);
		
		JPanel pnlMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		pnlMenu.add(cmdAjouter);	
		pnlMenu.add(cmdModifier);
		pnlMenu.add(cmdSupprimer);
		cmdAjouter.addActionListener(this);
		cmdModifier.addActionListener(this);
		cmdSupprimer.addActionListener(this);
		
		pnlLigneDeLivraison.pnlContenant.add(scrollpane, BorderLayout.CENTER);		
		pnlLigneDeLivraison.pnlContenant.add(pnlMenu, BorderLayout.SOUTH);		

		return pnlLigneDeLivraison;
	}
	
	private JPanel getPiedDeLivraison() {
		
		JpnlContainer pnlPiedDeLivraison = new JpnlContainer("Pied de Livraison");
		pnlPiedDeLivraison.layoutHaut.row().grid(lblNbArticleLivraison).add(txtNbArticleLivraison).grid(3);
		pnlPiedDeLivraison.layoutHaut.row().grid(lblMontantBrutHTLivraison).add(txtMontantBrutHTLivraison);
		
		return pnlPiedDeLivraison;
	}
	
	protected void createGUI() {		
		super.createGUI();
				
		onglets.setBounds(40,20,300,300);
	    onglets.add("Entete", this.getLivraison());
	    onglets.add("Lignes de Livraison", this.getLigneDeLivraison());
	    onglets.add("Pied de Livraison", this.getPiedDeLivraison());
	    
		pnlCorps.add(this.getEnteteLivraison(), BorderLayout.NORTH);
		pnlCorps.add(this.onglets, BorderLayout.CENTER);
	}

	public UIFicheLivraison(JDialog parentFrame, String mode, String ID) {
		
		super(parentFrame, mode, ID, "FICHE Livraison", "fr.gclo.beans.Livraison", "fr.gclo.modeles.LivraisonDAO");
		
		this.createGUI();
		
		ControllerSaisieObligatoire.getInstance().designChampObligatoire(this);
		
		this.showItem();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if ( e.getSource() == cmdAjouter ) {
			//UIFicheLivraisonArticle uif = new UIFicheLivraisonArticle(null, null, null);
			//uif.setVisible(true);			
		}
		else if ( e.getSource() == cmdModifier ) {
		}
		else if ( e.getSource() == cmdSupprimer ) {
		}
		else if (e.getSource() == cmdSelectionSaison) {
			UISelectionSaison uiselectionsaison = new UISelectionSaison(null);
			uiselectionsaison.setVisible(true);
		}
	}
}
