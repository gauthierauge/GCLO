package fr.gclo.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class UIScreenBackGround extends JPanel {

//	private boolean nomApplicationVisible = true;
	
	private Font font = new Font("Lato", Font.PLAIN, 52);

	private Font font2 = new Font("Lato", Font.ROMAN_BASELINE, 20);
	
	@Override
    public void paint(Graphics g) 
	{
		super.paint(g);
		
        g.setColor(new Color(49, 69, 89));
        g.fillRect( 0,  0, getSize().width, getSize().height  / 2);        
        g.drawRect( 0,  0, getSize().width, getSize().height  / 2);   
        
        g.setColor(new Color(52, 73, 94));
        g.fillRect( 0,  getSize().height  / 2, getSize().width, (getSize().height  / 2) /*- 30*/);        
        g.drawRect( 0,  getSize().height  / 2, getSize().width, (getSize().height  / 2) /*- 30*/);   
		
//		if ( nomApplicationVisible ) 
//		{
	        Graphics2D g2 = (Graphics2D)g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setFont(font);
	        g2.setColor(Color.WHITE);
	        //g2.drawString(Environnement.nomApplication, 40, 120);
	        
	        g2.setFont(font2);
	        g2.setColor(new Color(116, 129, 142));
	       // g2.drawString(Environnement.descriptifApplication + " - " + Environnement.APPVERS, 40, 150);  
//		}	
	}
}
