package fr.gclo.ui.composants;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class JTagTextField extends JTextField implements FocusListener {
	
	private static final long serialVersionUID = 1L;
	
	public JTagTextField() {
		super();
        addFocusListener(this);
        setBorder(new BevelBorder(BevelBorder.LOWERED));
	}

	public JTagTextField(String name, ActionListener al) {
		super();
        addFocusListener(this);
		//addActionListener(al);
		//setName(name);
        setBorder(new BevelBorder(BevelBorder.LOWERED));
	}
	   
	public void focusGained(FocusEvent e) {
        selectAll();
        setBorder(new BevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.LIGHT_GRAY));
	}

	public void focusLost(FocusEvent e) {
        try {
            this.setText(this.getText());
        } 
        catch(Throwable t) {}
        
        setBorder(new BevelBorder(BevelBorder.LOWERED));
	}
}