package fr.gclo.ui.composants;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicBorders;

import fr.gclo.Environnement;

public class JSearchTextField extends JTextField implements FocusListener {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Icon icon = null;

    /**
     *
     */
    public JSearchTextField() {
        super();
        
        setPreferredSize(new Dimension(200, 25));
        setIcon(new ImageIcon(getClass().getClassLoader().getResource("fr/gclo/ressources/search.jpg")));
        
        addFocusListener(this);
    }


    /**
     * @return Returns the current Icon.
     */
    public Icon getIcon() {
        return icon;
    }

    /**
     * @param aIcon set the Icon to display.
     */
    public void setIcon(Icon aIcon) {
        Insets i = getMargin();
        int wplus = 0;
        int wminus = 0;

        if (aIcon != null) {
            wplus = aIcon.getIconWidth() + 2;
        }
        if (icon != null) {
            wminus = icon.getIconWidth() + 2;
        }
        setMargin(new Insets(i.top, i.left + wplus - wminus, i.bottom, i.right));
        this.icon = aIcon;
        validate();
        repaint();
    }

    /**
     * paintComponent
     *
     * @param g
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getIcon() != null) {
            int hei = getHeight();
            getIcon().paintIcon(this, g, 2, Math.max(0, (hei - getIcon().getIconHeight()) / 2));
        }
    }

    /**
     * getPreferredSize
     *
     * @return the preferred size + the image width
     * @see javax.swing.JTextField#getPreferredSize()
     */
    public Dimension getPreferredSize() {
        if (getIcon() == null) {
            return super.getPreferredSize();
        }
        Dimension d = new Dimension(super.getPreferredSize().width + getIcon().getIconWidth() + 4, super.getPreferredSize().height);
        return d;
    }


	@Override
	public void focusGained(FocusEvent e) {
        // On s�lectionne le contenu :
        selectAll();

        Border border = BorderFactory.createLineBorder(Environnement.couleurDeFondAlternatedRow);

        setBorder(border);        
	}


	@Override
	public void focusLost(FocusEvent e) {
        try {
            this.setText(this.getText());
        } 
        catch(Throwable t) {}
        
//        setBorder();        
	}

    /**
     * setBorder
     *
     * @param border
     * @see javax.swing.JComponent#setBorder(javax.swing.border.Border)
     */
    public void setBorder(Border border) {
        Border marginBorder = new BasicBorders.MarginBorder();
        if (border == null) {
            super.setBorder(marginBorder);
        } else if (border instanceof BasicBorders.MarginBorder
                || border instanceof BasicBorders.FieldBorder
                || border.getClass().getName().equals("com.sun.java.swing.plaf.windows.XPStyle$XPFillBorder")) {
            super.setBorder(border);
        } else {
            super.setBorder(new CompoundBorder(border, marginBorder));
        }
    }
}
