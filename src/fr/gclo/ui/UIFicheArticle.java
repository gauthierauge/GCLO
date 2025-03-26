package fr.gclo.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.gclo.controller.ControllerSaisieObligatoire;
import fr.gclo.edition.BonDeCommande;
import fr.gclo.modeles.ColorisArticleDAO;
import fr.gclo.modeles.SaisonDAO;
import fr.gclo.ui.composants.JSDWPanel;
import fr.gclo.ui.composants.JTagComboBox;
import fr.gclo.ui.composants.JTagTextField;
import fr.gclo.ui.composants.JlblEtiquette;
import fr.gclo.ui.composants.JpnlContainer;
import net.java.dev.designgridlayout.DesignGridLayout;

@SuppressWarnings("serial")
public class UIFicheArticle extends UIDialogFiche implements ActionListener {
	
	private JlblEtiquette lblCodeArticle = new JlblEtiquette("Code", "lblCodeArticle");
	private JTagTextField txtCodeArticle = new JTagTextField();
	private JlblEtiquette lblModeleArticle = new JlblEtiquette("Modèle", "lblModeleArticle");
	private JTagTextField txtModeleArticle = new JTagTextField();
	private JlblEtiquette lblDesignationArticle = new JlblEtiquette("Designation", "lblDesignationArticle");
	private JTagTextField txtDesignationArticle = new JTagTextField();
	private JlblEtiquette lblSaisonArticle = new JlblEtiquette("Saison", "lblSaisonArticle");
	private JTagComboBox<String> txtSaisonArticle = new JTagComboBox<String>("SaisonArticle", SaisonDAO.getVecteurSaisonDAO());
	private JlblEtiquette lblColorisArticle = new JlblEtiquette("Coloris", "lblColorisArticle");
	private JTagComboBox<String> txtColorisArticle = new JTagComboBox<String>("ColorisArticle", ColorisArticleDAO.getVecteurColorisArticleeDAO());
	private JlblEtiquette lblPrixDeVenteHTArticle = new JlblEtiquette("Prix de Vente H.T.", "lblPrixDeVenteHTArticle");
	private JTagTextField txtPrixDeVenteHTArticle = new JTagTextField();
	private JlblEtiquette lblPrixDeVenteTTCArticle = new JlblEtiquette("Prix de Vente T.T.C.", "lblPrixDeVenteTTCArticle");
	private JTagTextField txtPrixDeVenteTTCArticle = new JTagTextField();
	private JlblEtiquette lblTauxTVAArticle = new JlblEtiquette("Taux de T.V.A.", "lblTauxTVAArticle");
	private JTagTextField txtTauxTVAArticle = new JTagTextField();
	private JlblEtiquette lblPoidsArticle = new JlblEtiquette("Poids", "lblPoidsArticle");
	private JTagTextField txtPoidsArticle = new JTagTextField();
	private JlblEtiquette lblNumeroATAArticle = new JlblEtiquette("Numéro ATA", "lblNumeroATAArticle");
	private JTagTextField txtNumeroATAArticle = new JTagTextField();
	private JlblEtiquette lblNumeroModele = new JlblEtiquette("Numéro Modele", "lblNumeroModele");
	private JTagTextField txtNumeroModele = new JTagTextField();

	public JButton 		  cmdImprimer = new JButton("IMPRIMER");
	
	
	public void createPopUpEditionFiche() {
		
        JMenuItem menuItem = new JMenuItem("Bon de Commande", new ImageIcon("images/newproject.png"));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String nomDuDocumentPDF = "c:\\Temp\\totototo.pdf";
            	if ( new BonDeCommande().generate(nomDuDocumentPDF, ID) ) { 
        			try {
        				if ( System.getProperty("os.name").startsWith("Windows") ) {
        					Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + nomDuDocumentPDF);
        				}
        				else {
        				//	Desktop.getDesktop().open(new File(nomDuDocumentPDF));		
        				}
        			} 
        			catch (IOException ex) {
        				ex.printStackTrace();
        				JOptionPane.showMessageDialog( null, "Probl�me � l'ouverture du fichier PDF.", null, JOptionPane.ERROR_MESSAGE);		
        			}
            	}
            }
        });
        popup.add(menuItem);
	}
		
	private JPanel getEnteteArticle() {
		
		JSDWPanel pnlDummy = new JSDWPanel();
		
		txtCodeArticle.setEditable(false);
		txtCodeArticle.setEnabled(false);
		
		layout = new DesignGridLayout(pnlDummy);
		
		layout.row().grid(lblCodeArticle).add(txtCodeArticle).grid(lblModeleArticle).add(txtModeleArticle).grid(lblDesignationArticle).add(txtDesignationArticle).grid(5);

		return pnlDummy;
	}
	
	private JPanel getDesignationArticle() {
		
		JpnlContainer pnlDesignation = new JpnlContainer("Designation");

		pnlDesignation.layoutHaut.row().grid(lblSaisonArticle).add(txtSaisonArticle).grid(2);
		pnlDesignation.layoutHaut.row().grid(lblColorisArticle).add(txtColorisArticle).grid(2);
		pnlDesignation.layoutHaut.row().grid(lblPrixDeVenteHTArticle).add(txtPrixDeVenteHTArticle).grid(2);
		pnlDesignation.layoutHaut.row().grid(lblTauxTVAArticle).add(txtTauxTVAArticle).grid(2);
		pnlDesignation.layoutHaut.row().grid(lblPrixDeVenteTTCArticle).add(txtPrixDeVenteTTCArticle).grid(2);
		pnlDesignation.layoutHaut.row().grid(lblPoidsArticle).add(txtPoidsArticle).grid(2);
		pnlDesignation.layoutHaut.row().grid(lblNumeroATAArticle).add(txtNumeroATAArticle).grid(2);
		pnlDesignation.layoutHaut.row().grid(lblNumeroModele).add(txtNumeroModele).grid(2);
	
	    return pnlDesignation;
	}
	
	protected void createMenu() {		
		super.createMenu();
		
		cmdImprimer.addActionListener(this);

		pnlMenu.add(separateur_1);
		pnlMenu.add(cmdImprimer);	
	}	
	
	protected void createGUI() {		
		super.createGUI();
				
		pnlCorps.add(this.getEnteteArticle(), BorderLayout.NORTH);
		pnlCorps.add(this.getDesignationArticle(), BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		
		if ( e.getSource() == cmdImprimer ) {
			popup.show(cmdImprimer, 0, cmdImprimer.getBounds().height);
		}
	}

	public UIFicheArticle(JDialog parentFrame, String mode, String ID) {
		super(parentFrame, mode, ID, "FICHE ARTICLE", "fr.gclo.beans.Article", "fr.gclo.modeles.ArticleDAO");
		
		this.createGUI();
		
		this.createMenu();
		
		this.createPopUpEditionFiche();
		
		ControllerSaisieObligatoire.getInstance().designChampObligatoire(this);
		
		this.showItem();
	}
}
