package fr.gclo.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.gclo.modeles.ColorisDAO;
import fr.gclo.modeles.ModeleDAO;
import fr.gclo.modeles.SaisonDAO;
import fr.gclo.ui.composants.JTagComboBox;
import fr.gclo.ui.composants.JlblEtiquette;

@SuppressWarnings("serial")
public class UIFicheArticleCreationRapide extends UIDialog {

	JlblEtiquette lblSaison = new JlblEtiquette("Saison", "lblSaison");
    JTagComboBox<String> txtSaison = new JTagComboBox<String>("txtSaison", SaisonDAO.getVecteurSaisonDAO());
	JlblEtiquette lblModele = new JlblEtiquette("Modèle", "lblSaisonCommande");
    JTagComboBox<String> txtModele = new JTagComboBox<String>("", null);

    private DefaultListModel<String> lstModelColorisSource = new DefaultListModel<>();;
    private DefaultListModel<String> lstModelColorisCible = new DefaultListModel<>();;
    private JList<String> lstColorisSource = new JList<>(lstModelColorisSource);
    private JList<String> lstColorisCible = new JList<>(lstModelColorisCible);
    JScrollPane scrollPaneColorisSource = new JScrollPane(lstColorisSource);
    JScrollPane scrollPaneColorisCible = new JScrollPane(lstColorisCible);
    
 	public JButton cmdAjouterUn = new JButton(">");	
 	public JButton cmdAjouterTout = new JButton(">>");	
 	public JButton cmdEnleverUn = new JButton("<");	
 	public JButton cmdEnleverTout = new JButton("<<>");	
 	
 	public JButton cmdValider = new JButton("VALIDER");	

	protected void valider() {		
		retourEcran = true;
		screenClose();
	}	

	protected void createGUI() {		
		super.createGUI();
		
		JPanel dummy = new JPanel(null);
		lblSaison.setBounds(10, 50, 120, 25); // Positionner le bouton en (col = 10, ligne = 50) avec une taille de 120x30
		txtSaison.setBounds(60, 50, 200, 25); // Positionner le bouton en (50, 50) avec une taille de 120x30
		lblModele.setBounds(10, 80, 120, 25); // Positionner le bouton en (col = 10, ligne = 50) avec une taille de 120x30
		txtModele.setBounds(60, 80, 120, 25); // Positionner le bouton en (50, 50) avec une taille de 120x30
		
		scrollPaneColorisSource.setBounds(80, 120, 160, 200);
		
		cmdAjouterUn.setBounds(250, 120, 50, 25);
		cmdAjouterTout.setBounds(250, 170, 50, 25);
		cmdEnleverUn.setBounds(250, 220, 50, 25);
		cmdEnleverTout.setBounds(250, 270, 50, 25);
		
		scrollPaneColorisCible.setBounds(350, 120, 160, 200);

		txtSaison.addActionListener(this);
		cmdAjouterTout.addActionListener(this);
		cmdAjouterUn.addActionListener(this);
		cmdEnleverTout.addActionListener(this);
		cmdEnleverUn.addActionListener(this);
        
		dummy.add(lblSaison);
		dummy.add(txtSaison);
		dummy.add(lblModele);
		dummy.add(txtModele);
		dummy.add(scrollPaneColorisSource);
		dummy.add(cmdAjouterUn);
		dummy.add(cmdAjouterTout);
		dummy.add(cmdEnleverUn);
		dummy.add(cmdEnleverTout);
		dummy.add(scrollPaneColorisCible);
		
		pnlCorps.add(dummy, BorderLayout.CENTER);
	}	

	protected void createMenu() {		
		super.createMenu();
		
		cmdValider.addActionListener(this);

		pnlMenu.add(cmdValider);	
	}	

	public UIFicheArticleCreationRapide(JFrame parentFrame) {
		super(parentFrame);
		
		this.l_titre = "CREATION RAPIDE D'ARTICLES";		
		
		this.createGUI();
		this.createMenu();
		
    	this.setSize(700, 600);
    	
    	this.screenCenter();
	}

	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if ( e.getSource() == cmdValider ) {
			valider();
		}
		else if ( e.getSource() == txtSaison ) {
			
			txtModele.removeAllItems();
			for (String s: ModeleDAO.getVecteurModeleDAO(txtSaison.getSelectedItem().toString()) ) {
				txtModele.addItem(s);
			}
	    	
			lstModelColorisSource.clear();
			lstModelColorisCible.clear();
			for (String s: ColorisDAO.getVecteurColorisDAO(txtSaison.getSelectedItem().toString()) ) {
				lstModelColorisSource.addElement(s);
			}    	
		}
		else if ( e.getSource() == cmdAjouterUn ) {
			System.out.print("cmdAjouterUn");
		}
		else if ( e.getSource() == cmdAjouterTout ) {
			System.out.print("cmdAjouterTout");
		}
		else if ( e.getSource() == cmdEnleverUn ) {
			System.out.print("cmdEnleverUn");
		}
		else if ( e.getSource() == cmdEnleverTout ) {
			System.out.print("cmdEnleverTout");
		}
	}	

}
