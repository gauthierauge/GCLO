package fr.gclo.ui;

import java.awt.Component;

@SuppressWarnings("serial")
public class UIGrilleDesCommandes extends UIGrille {
	
	public UIGrilleDesCommandes(Component dummy) {
		
		super(dummy, "GESTION DES COMMANDES", "fr.gclo.modeles.CommandeDAO", "fr.gclo.ui.UIFicheCommande", "LISTE DES COMMANDES");
	}	
}
