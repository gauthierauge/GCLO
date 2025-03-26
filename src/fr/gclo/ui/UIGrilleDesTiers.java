package fr.gclo.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import fr.gclo.edition.ListeDesTiers;

@SuppressWarnings("serial")
public class UIGrilleDesTiers extends UIGrille {
	
	public void createPopUpEditionGrille() {
		
        JMenuItem menuItem = new JMenuItem("Liste des Tiers", new ImageIcon("images/newproject.png"));
        //menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.getAccessibleContext().setAccessibleDescription("New Project");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String nomDuDocumentPDF = "c:\\Temp\\totototo.pdf";
            	if ( new ListeDesTiers().generate(nomDuDocumentPDF) ) { 
        			// PDF
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
        // New File menu item
        menuItem = new JMenuItem("Liste Détaillée des Règlements",  new ImageIcon("images/newfile.png"));
        //menuItem.setMnemonic(KeyEvent.VK_F);
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "New File clicked!");
            }
        });
        popup.add(menuItem);
	}
	
	public UIGrilleDesTiers(Component dummy) {
		
		super(dummy, "GESTION DES TIERS", "fr.gclo.modeles.TiersDAO", "fr.gclo.ui.UIFicheTiers", "LISTE DES TIERS");		
	}	
}