package fr.gclo.ui;

import java.awt.Component;

@SuppressWarnings("serial")
public class UIGrilleDesLivraisons extends UIGrille {
	
	public UIGrilleDesLivraisons(Component dummy) {
		
		super(dummy, "GESTION DES LIVRAISONS", "fr.gclo.modeles.LivraisonDAO", "fr.gclo.ui.UIFicheLivraison", "LISTE DES LIVRAISONS");
	}	
}
