package fr.gclo.ui.composants;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class JpnlSeparateurMenu extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public JpnlSeparateurMenu() {
		setPreferredSize(new Dimension(10, 10));
		setOpaque(true);
		setBackground(Color.LIGHT_GRAY);
	}
}
