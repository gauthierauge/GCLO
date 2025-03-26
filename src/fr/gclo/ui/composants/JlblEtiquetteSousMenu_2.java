package fr.gclo.ui.composants;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import fr.gclo.Environnement;

public class JlblEtiquetteSousMenu_2 extends JButton implements MouseListener, FocusListener  {
	
	private static final long serialVersionUID = 1L;

	public JlblEtiquetteSousMenu_2(String text) 
	{
        super(text);
        
	    setFont(new Font("Helvetica", Font.PLAIN, 12));
	    setForeground(Color.WHITE);
	    //setBackground(Color.WHITE);
	    setHorizontalAlignment(SwingConstants.CENTER);
	    setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	    setBorderPainted(false);
	    setContentAreaFilled(false);
	    setFocusPainted(false);
    
	    addMouseListener(this); 
	    addFocusListener(this); 
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
	    setForeground(Color.BLACK);
	    setBackground(Environnement.couleurDeFondAlternatedRow);
	    setContentAreaFilled(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
	    setForeground(Color.WHITE);
	   // setBackground(Color.WHITE);
	    setContentAreaFilled(false);    
	}

	@Override
	public void focusGained(FocusEvent e) {
	    setForeground(Color.BLACK);
	    setBackground(Environnement.couleurDeFondAlternatedRow);
	    setContentAreaFilled(true);
	}

	@Override
	public void focusLost(FocusEvent e) {
	    setForeground(Color.WHITE);
	 //   setBackground(Color.WHITE);
	    setContentAreaFilled(false); 
	}
}
