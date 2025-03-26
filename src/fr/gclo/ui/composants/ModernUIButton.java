package fr.gclo.ui.composants;

import java.awt.Color;
import java.awt.Image;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ModernUIButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	public ModernUIButton(String image) {
		
		setMargin(new Insets(0, 0, 0, 0));
		//setBackground(new Color(116, 129, 142));
		setBackground(Color.white);
		setBorder(null);
		setFocusPainted(false);
		
		try {
		    Image img = ImageIO.read(getClass().getClassLoader().getResource("sdw/progest/ressources/" + image));
		    setIcon(new ImageIcon(img));
		} 
		catch (Exception ex) {
		    System.out.println(ex);
		}				
	}
}
