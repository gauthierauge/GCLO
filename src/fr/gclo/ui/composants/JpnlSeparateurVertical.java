package fr.gclo.ui.composants;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class JpnlSeparateurVertical extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public JpnlSeparateurVertical() 
	{
		setPreferredSize(new Dimension(1, 58));
		setOpaque(true);
		setBackground(Color.LIGHT_GRAY);
	}
}