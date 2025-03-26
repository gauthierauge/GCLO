package fr.gclo.ui.composants;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import fr.gclo.Environnement;

public class JSDWTabbedPanne extends JTabbedPane {

	private static final long serialVersionUID = 1L;

	public JSDWTabbedPanne()
	{
//		UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0)); 
//		UIManager.put("TabbedPane.contentAreaColor ",ColorUIResource.WHITE);
//		UIManager.put("TabbedPane.selected",ColorUIResource.WHITE);
//		UIManager.put("TabbedPane.background",ColorUIResource.WHITE);
//		UIManager.put("TabbedPane.shadow",ColorUIResource.WHITE);
//       
//		UIManager.put("TabbedPane.borderColor", Color.WHITE);
	}
	
	public JSDWTabbedPanne(String titre, JPanel contenu)
	{
		this.addTab(titre, contenu);
	}
	
	public Color getForegroundAt(int index)
	{
//          if(getSelectedIndex() == index) return Environnement.couleurDeTexteSelectedTab;
//          return Color.BLACK;
          
          return (getSelectedIndex() == index) ? Environnement.couleurDeTexteSelectedTab : Color.BLACK;
	}
}
