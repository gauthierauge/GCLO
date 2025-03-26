package fr.gclo.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JPanel;

import fr.gclo.controller.ControllerSaisieObligatoire;
import fr.gclo.modeles.ColorisDAO;
import fr.gclo.modeles.ModeleDAO;
import fr.gclo.ui.composants.DecimalTextField;
import fr.gclo.ui.composants.IntTextField;
import fr.gclo.ui.composants.JSDWComboBox;
import fr.gclo.ui.composants.JTagTextField;
import fr.gclo.ui.composants.JlblEtiquette;
import fr.gclo.ui.composants.JlblEtiquetteBordured;

@SuppressWarnings("serial")
public class UIFicheCommandeArticle extends UIDialogFiche implements ActionListener {

	private JTagTextField 			txtCodeArticle 					= new JTagTextField("CodeArticle", 	 			this);
    private JSDWComboBox			txtModeleArticle				= new JSDWComboBox("txtModeleArticle");
    private JSDWComboBox			txtCouleurArticle				= new JSDWComboBox("txtCouleurArticle");
	private DecimalTextField 		txtPrixHTArticle 				= new DecimalTextField("CodeArticle", 	 		this);
	private DecimalTextField 		txtTauxTVA 						= new DecimalTextField("TauxTVA", 	 			this);
	
	private JlblEtiquette 			lblCodeArticle 					= new JlblEtiquette("Code Article", "lblCodeArticle");
	private JlblEtiquette 			lblModeleArticle				= new JlblEtiquette("Modèle", "lblModeleArticle");
	private JlblEtiquette 			lblCouleurArticle				= new JlblEtiquette("Couleur", "lblCouleurArticle");
	private JlblEtiquette 			lblPrixHTArticle				= new JlblEtiquette("Prix", "lblPrixHTArticle");
	private JlblEtiquette 			lblTauxTVA						= new JlblEtiquette("Taux de T.V.A.", "lblTauxTVA");
	
	private JlblEtiquetteBordured	lbltailleUnique					= new JlblEtiquetteBordured("Taille Unique", "lbltailleUnique");
	private JlblEtiquetteBordured	lbltailleS						= new JlblEtiquetteBordured("Taille S", "lbltailleS");
	private JlblEtiquetteBordured	lbltailleM						= new JlblEtiquetteBordured("Taille M", "lbltailleM");
	private JlblEtiquetteBordured	lbltailleL						= new JlblEtiquetteBordured("Taille L", "lbltailleL");
	private JlblEtiquetteBordured	lbltailleXL						= new JlblEtiquetteBordured("Taille XL", "lbltailleXL");
	private JlblEtiquetteBordured	lbltailleXXL					= new JlblEtiquetteBordured("Taille XXL", "lbltailleXXL");
	private JlblEtiquetteBordured	lblNbTotalArticle				= new JlblEtiquetteBordured("Nb Total Articles", "lblNbTotalArticle");
	private JlblEtiquetteBordured	lblMontantTotalArticle			= new JlblEtiquetteBordured("Montant Total Articles", "lblMontantTotalArticle");
	
	private IntTextField			txttailleUnique					= new IntTextField();
	private IntTextField			txttailleS						= new IntTextField();
	private IntTextField			txttailleM						= new IntTextField();
	private IntTextField			txttailleL						= new IntTextField();
	private IntTextField			txttailleXL						= new IntTextField();
	private IntTextField			txttailleXXL					= new IntTextField();
	private DecimalTextField 		txtNbTotalArticle 				= new DecimalTextField("NbTotalArticle", 	 		this);
	private DecimalTextField 		txtMontantTotalArticle 			= new DecimalTextField("MontantTotalArticle", 	 	this);
	
	protected void createGUI() {
		super.createGUI();
		
		JPanel pnlDummy = new JPanel(null);
		pnlDummy.setPreferredSize(new Dimension(200, 120));
		
		lblCodeArticle.setBounds(5, 20, 100, 25);
		txtCodeArticle.setBounds(90, 20, 100, 25);
		
		lblModeleArticle.setBounds(5, 60, 100, 25);
		txtModeleArticle.setBounds(90, 60, 120, 25);
		lblPrixHTArticle.setBounds(220, 60, 100, 25);
		txtPrixHTArticle.setBounds(300, 60, 100, 25);
		
		lblCouleurArticle.setBounds(5, 100, 100, 25);
		txtCouleurArticle.setBounds(90, 100, 120, 25);
		lblTauxTVA.setBounds(220, 100, 100, 25);
		txtTauxTVA.setBounds(300, 100, 100, 25);

		
//		JSDWPanel pnlDummy = new JSDWPanel();
//		layout = new DesignGridLayout(pnlDummy);
//
//		layout.row().grid(lblCodeArticle).add(txtCodeArticle).empty();
//		layout.row().grid(lblModeleArticle).add(txtModeleArticle).add(lblPrixHTArticle).add(txtPrixHTArticle).empty(2);
//		layout.row().grid(lblCouleurArticle).add(txtCouleurArticle).add(lblTauxTVA).add(txtTauxTVA).empty(2);
//
		pnlDummy.add(lblCodeArticle);
		pnlDummy.add(txtCodeArticle);
		pnlDummy.add(lblModeleArticle);
		pnlDummy.add(txtModeleArticle);
		pnlDummy.add(lblPrixHTArticle);
		pnlDummy.add(txtPrixHTArticle);
		pnlDummy.add(lblCouleurArticle);
		pnlDummy.add(txtCouleurArticle);
		pnlDummy.add(lblTauxTVA);
		pnlDummy.add(txtTauxTVA);
		
		
		JPanel pnlDummy2 = new JPanel(null);
		int col = 10;
		int lgLabel = 120;
		int lgTextField= 147;
		int l = 100;
		int l2 = 150;
		lbltailleUnique.setBounds(col, lgLabel, l, 25);
		lbltailleS.setBounds((col + l + 2), lgLabel, l, 25);
		lbltailleM.setBounds((col + l + l + 2 + 2), lgLabel, l, 25);
		lbltailleL.setBounds((col + l + l + l + 2 + 2 + 2), lgLabel, l, 25);
		lbltailleXL.setBounds((col + l + l + l + l + 2 + 2 + 2 + 2), lgLabel, l, 25);
		lbltailleXXL.setBounds((col + l + l + l + l + l + 2 + 2 + 2 + 2 + 2), lgLabel, l, 25);
		lblNbTotalArticle.setBounds((col + l + l + l + l + l + 2 + l + 2 + 2 + 2 + 2 + 2), lgLabel, l2, 25);
		lblMontantTotalArticle.setBounds((col + l + l + l + l + l + 2 + l2 + 2 + l + 2 + 2 + 2 + 2 + 2), lgLabel, l2, 25);
		txttailleUnique.setBounds(col, lgTextField, l, 25);
		txttailleS.setBounds((col + l + 2), lgTextField, l, 25);
		txttailleM.setBounds((col + l + l + 2 + 2), lgTextField, l, 25);
		txttailleL.setBounds((col + l + l + l + 2 + 2 + 2), lgTextField, l, 25);
		txttailleXL.setBounds((col + l + l + l + l + 2 + 2 + 2 + 2), lgTextField, l, 25);
		txttailleXXL.setBounds((col + l + l + l + l + l + 2 + 2 + 2 + 2 + 2), lgTextField, l, 25);
		txtNbTotalArticle.setBounds((col + l + l + l + l + l + 2 + l + 2 + 2 + 2 + 2 + 2), lgTextField, l2, 25);
		txtMontantTotalArticle.setBounds((col + l + l + l + l + l + 2 + l2 + 2 + l + 2 + 2 + 2 + 2 + 2), lgTextField, l2, 25);

		pnlDummy2.add(lbltailleUnique);
		pnlDummy2.add(lbltailleS);
		pnlDummy2.add(lbltailleM);
		pnlDummy2.add(lbltailleL);
		pnlDummy2.add(lbltailleXL);
		pnlDummy2.add(lbltailleXXL);
		pnlDummy2.add(lblNbTotalArticle);
		pnlDummy2.add(lblMontantTotalArticle);
		
		pnlDummy2.add(txttailleUnique);
		pnlDummy2.add(txttailleS);
		pnlDummy2.add(txttailleM);
		pnlDummy2.add(txttailleL);
		pnlDummy2.add(txttailleXL);
		pnlDummy2.add(txttailleXXL);
		pnlDummy2.add(txtNbTotalArticle);
		pnlDummy2.add(txtMontantTotalArticle);

		
		pnlCorps.add(pnlDummy, BorderLayout.NORTH);
		pnlCorps.add(pnlDummy2, BorderLayout.CENTER);

    	setSize(1000, 500);
    	
       	this.screenCenter();
	}

	@SuppressWarnings("unchecked")
	public UIFicheCommandeArticle(JDialog parentFrame, String mode, String ID, String saison) {

		super(parentFrame, mode, ID, "LIGNE DE COMMANDE", "fr.gclo.beans.CommandeLigne", "fr.gclo.modeles.CommandeLigneDAO");
		
		this.createGUI();
		this.createMenu();
		
		this.txtModeleArticle.setModel(new DefaultComboBoxModel<String>(ModeleDAO.getVecteurModeleDAO(saison)));
		this.txtCouleurArticle.setModel(new DefaultComboBoxModel<String>(ColorisDAO.getVecteurColorisDAO(saison)));

		ControllerSaisieObligatoire.getInstance().designChampObligatoire(this);
		
		this.showItem();
	}
}

