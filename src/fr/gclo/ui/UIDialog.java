package fr.gclo.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import fr.gclo.Environnement;
import fr.gclo.ui.composants.JlblEtiquetteSousTitre;
import fr.gclo.ui.composants.JpnlSeparateurMenu;
import net.java.dev.designgridlayout.DesignGridLayout;

@SuppressWarnings("serial")
public class UIDialog extends JDialog implements ActionListener {

	protected String 				l_titre   		= "";
	
	protected JPanel 				pnlEntete 		= null;
	protected JPanel 				pnlCorps  		= null;
	protected JPanel 				pnlMenu   		= null;
	protected DesignGridLayout 		layout 			= null;
	
	public boolean 					retourEcran 	= false;
	
	public JButton 					cmdQuitter 		= new JButton("QUITTER [ESC]");
	
	public JpnlSeparateurMenu		separateur_1 	= new JpnlSeparateurMenu();
	
	protected Dimension 			screenSize 		= Toolkit.getDefaultToolkit().getScreenSize();
	
	final protected Component			mySelf		= this;
		
	protected void createGUI() {
		
		getContentPane().setLayout(new BorderLayout());
		
        Box verticalBox = Box.createVerticalBox();
		
		pnlEntete = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		pnlEntete.setBorder(BorderFactory.createEmptyBorder(2, 5, 0, 0));
		pnlEntete.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
		pnlEntete.add(new JlblEtiquetteSousTitre(" " + l_titre));	
		verticalBox.add(pnlEntete);
		
		pnlMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		verticalBox.add(pnlMenu);

		add(verticalBox, BorderLayout.NORTH);
		
		pnlCorps = new JPanel(new BorderLayout());
		pnlCorps.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
			
		add(pnlCorps, BorderLayout.CENTER);
	}

	protected void quitter() {
		retourEcran = false;
		screenClose();
	}
	
	protected void screenClose() {
		dispose();
	}
	
	public void screenCenter() 
	{
		Dimension frameSize = this.getSize();
      	if(frameSize.height > screenSize.height)  {
	         frameSize.height = screenSize.height;
      	}
      	
      	if(frameSize.width > screenSize.width) {
	         frameSize.width = screenSize.width;
      	}
      	setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
	protected void createMenu() {
		
		cmdQuitter.addActionListener(this);
		
		pnlMenu.add(cmdQuitter);	
		pnlMenu.add(separateur_1);
	}
	
	public void actionPerformed(ActionEvent e) {
		
	    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	    manager.focusNextComponent();
	    
	    if ( e.getSource() == cmdQuitter ) {
        	quitter();    	
	    }
	}
	
	public UIDialog(Component parentFrame) {
		
		if ( parentFrame != null ) {
			setLocation(parentFrame.getLocation().x, parentFrame.getLocation().y );
			setSize(new Dimension(parentFrame.getSize().width, parentFrame.getSize().height));
		}
		
		setModal(true);
		
		setTitle(Environnement.nomApplication);
		
	    Action escapeAction = new AbstractAction() {	    	
			private static final long serialVersionUID = 1L;
	        public void actionPerformed(ActionEvent e) { quitter(); }
	    }; 
	    
	    KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
	    getRootPane().getActionMap().put("ESCAPE", escapeAction);  
	}
}