package fr.gclo.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class UIGrilleDesFactures extends UIGrille {

	public void createPopUpEditionGrille() {
		
        JMenuItem menuItem = new JMenuItem("Liste des Factures", new ImageIcon("images/newproject.png"));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Liste des Factures cliked!");
            }
        });
        popup.add(menuItem);
        menuItem = new JMenuItem("Plusieurs Factures",  new ImageIcon("images/newfile.png"));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Plusieurs Factures cliked!");
            }
        });
        popup.add(menuItem);
	}
	
	public UIGrilleDesFactures(Component dummy) {
		
		super(dummy, "GESTION DES FACTURES", "fr.gclo.modeles.FactureDAO", "fr.gclo.ui.UIFicheFacture", "LISTE DES FACTURES");
	}	
}
