package fr.gclo.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import fr.gclo.edition.ListeDesArticles;
import fr.gclo.modeles.SaisonDAO;
import fr.gclo.ui.composants.JTagComboBox;
import fr.gclo.ui.composants.JlblEtiquette;
import fr.gclo.ui.composants.JpnlSeparateurMenu;

@SuppressWarnings("serial")
public class UIGrilleDesArticles extends UIGrille {


	public void createPopUpEditionGrille() {
		
        JMenuItem menuItem = new JMenuItem("Liste des Articles", new ImageIcon("images/newproject.png"));
        menuItem.getAccessibleContext().setAccessibleDescription("New Project");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String nomDuDocumentPDF = "c:\\Temp\\totototo.pdf";
            	if ( new ListeDesArticles().generate(nomDuDocumentPDF) ) { 
        			try {
        				if ( System.getProperty("os.name").startsWith("Windows") ) {
        					Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + nomDuDocumentPDF);
        				}
//        				else {
//        				//	Desktop.getDesktop().open(new File(nomDuDocumentPDF));		
//        				}
        			} 
        			catch (IOException ex) {
        				ex.printStackTrace();
        				JOptionPane.showMessageDialog( null, "Probl�me � l'ouverture du fichier PDF.", null, JOptionPane.ERROR_MESSAGE);		
        			}
            	}
            }
        });
        popup.add(menuItem);
        menuItem = new JMenuItem("Liste Détaillée des Règlements",  new ImageIcon("images/newfile.png"));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "New File clicked!");
            }
        });
        popup.add(menuItem);
	}
	
	protected void createMenu() {
		super.createMenu();

		JButton cmdCreationRapideArtice = new JButton("CREATION RAPIDE");
		
		JlblEtiquette lblSaisonCommande = new JlblEtiquette("Saison", "lblSaisonCommande");
	    JTagComboBox<String> txtSaison = new JTagComboBox<String>("txtSaison", SaisonDAO.getVecteurSaisonDAO());
	    txtSaison.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "txtSaison clicked!");
            }
        });
	    
		cmdCreationRapideArtice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	UIFicheArticleCreationRapide dummy = new UIFicheArticleCreationRapide(null);
            	dummy.setVisible(true);
            }
        });
		
		pnlMenu.add(new JpnlSeparateurMenu());
		pnlMenu.add(cmdCreationRapideArtice);	

		pnlMenu.add(new JpnlSeparateurMenu());
		pnlMenu.add(lblSaisonCommande);	
		pnlMenu.add(txtSaison);			
	}
	
	public UIGrilleDesArticles(Component dummy) {
		
		super(dummy, "GESTION DES ARTICLES", "fr.gclo.modeles.ArticleDAO", "fr.gclo.ui.UIFicheArticle", "LISTE DES ARTICLES");
	}	
}
