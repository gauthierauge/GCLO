package fr.gclo.ui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;

import fr.gclo.controller.ControllerSaisieObligatoire;
import fr.gclo.modeles.PaysDAO;
import fr.gclo.modeles.TypeAutreAdresseDAO;
import fr.gclo.ui.composants.JSDWPanel;
import fr.gclo.ui.composants.JTagComboBox;
import fr.gclo.ui.composants.JTagTextField;
import fr.gclo.ui.composants.JlblEtiquette;
import net.java.dev.designgridlayout.DesignGridLayout;

@SuppressWarnings("serial")
public class UIFicheTiersAutreAdresse extends UIDialogFiche {
	
	private JTagComboBox<String> 	txtTypeAdresseTiersAutreAdresseTiers = new JTagComboBox<String>("TitreTiers", TypeAutreAdresseDAO.getVecteurTypeAutreAdresseDAO());
	private JlblEtiquette 			lblTypeAdresseTiersAutreAdresseTiers = new JlblEtiquette("Type", "lblTypeAdresseTiersAutreAdresseTiers");

	private JlblEtiquette 			lblRaisonSocialeTiers 				= new JlblEtiquette("Nom",				"lblRaisonSocialeTiers");
	private JlblEtiquette 			lblAdresse1Tiers 					= new JlblEtiquette("Adresse",			"lblAdresse1Tiers");
	private JlblEtiquette 			lblAdresse2Tiers 					= new JlblEtiquette("Adresse compl.",	"lblAdresse2Tiers");
	private JlblEtiquette 			lblCodePostalTiers 					= new JlblEtiquette("Code Postal",		"lblCodePostalTiers");
	private JlblEtiquette 			lblVilleTiers 						= new JlblEtiquette("Ville",			"lblVilleTiers");
	private JlblEtiquette 			lblPaysTiers 						= new JlblEtiquette("Pays",				"lblPaysTiers");
	
	private JTagTextField 			txtRaisonSocialeTiers 				= new JTagTextField("RaisonSocialeTiers", 	 	this);
	private JTagTextField 			txtAdresse1Tiers 					= new JTagTextField("Adresse1Tiers", 	this);
	private JTagTextField 			txtAdresse2Tiers 					= new JTagTextField("Adresse2Tiers", 	this);
	private JTagTextField 			txtCodePostalTiers 					= new JTagTextField("CodePostalTiers", 	this);
	private JTagTextField 			txtVilleTiers 						= new JTagTextField("VilleTiers", 	 	this);
	private JTagComboBox<String> 	txtPaysTiers			 			= new JTagComboBox<String>("PaysTiers", PaysDAO.getVecteurPaysDAO());

	protected void valider() {		
		if ( ! ControllerSaisieObligatoire.getInstance().saisieRenseignee(this) ) return;		
		super.valider();
	}
	
	private JPanel getEnteteTiersAutreAdresse() {
		
		JSDWPanel pnlDummy = new JSDWPanel();
		
		layout = new DesignGridLayout(pnlDummy);
		
		layout.row().grid(lblTypeAdresseTiersAutreAdresseTiers).add(txtTypeAdresseTiersAutreAdresseTiers).grid(2);
		
		layout.row().grid(lblRaisonSocialeTiers).add(txtRaisonSocialeTiers).grid(1);
		layout.emptyRow();
		layout.row().grid(lblAdresse1Tiers).add(txtAdresse1Tiers).grid(1);
		layout.emptyRow();
		layout.row().grid(lblAdresse2Tiers).add(txtAdresse2Tiers).grid(1);
		layout.emptyRow();
		layout.row().grid(lblCodePostalTiers).add(txtCodePostalTiers).grid(lblVilleTiers).add(txtVilleTiers).grid(1);
		layout.emptyRow();
		layout.row().grid(lblPaysTiers).add(txtPaysTiers).grid(1);

		return pnlDummy;	
	}

	protected void createGUI() {		
		super.createGUI();
	    
		pnlCorps.add(this.getEnteteTiersAutreAdresse(), BorderLayout.CENTER);
	}

	public UIFicheTiersAutreAdresse(JDialog parentFrame, String mode, String ID) {		
		super(parentFrame, mode, ID, "FICHE AUTRE ADRESSE", "fr.gclo.beans.TiersAutreAdresseClient", "fr.gclo.modeles.TiersAutreAdresseDAO");
		
		this.createGUI();
		
		ControllerSaisieObligatoire.getInstance().designChampObligatoire(this);
		
		this.showItem();
	}
}