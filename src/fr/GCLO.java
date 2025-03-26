package fr;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import fr.gclo.Environnement;
import fr.gclo.controller.DataBaseInstance;
import fr.gclo.ui.UIMenu;
import fr.gclo.ui.UIScreenBackGround;

@SuppressWarnings("serial")
public class GCLO extends JFrame {

	public static Properties properties = new Properties();
	
	public static JFrame mySelf = null;

	private static JDesktopPane desktop = new JDesktopPane();

	private JLabel statusLabel = new JLabel();;

	public static void applicationClose() {
		DataBaseInstance.closeConnection();
		System.exit(0);
	}

	public GCLO()  {	
		
		if ( ! DataBaseInstance.openConnection() ) {
			JOptionPane.showMessageDialog(null, "Connexion a la Base de Donnees Impossible. See the log file...", "MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		setJMenuBar(new UIMenu());
		
		Locale.setDefault(new Locale("en", "US"));
	    
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

	    setTitle(Environnement.nomApplication);
	    setSize(800, 600);

	    setExtendedState(getExtendedState() | MAXIMIZED_BOTH);

	    desktop.setLayout(new BorderLayout());

	    desktop.add(new UIScreenBackGround());
	    
	    setContentPane(desktop);
	    
        getContentPane().add(statusLabel, BorderLayout.SOUTH);
        
        try {
			statusLabel.setText(" "  + DataBaseInstance.connection.getMetaData().getURL());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    
	    setVisible(true);
	    
	    mySelf = this;
	    	    	    
	    addWindowListener( new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) { 
	    		applicationClose();
	    	}
	    }); 
	}

	/**
	 * main de l'application 
	 * 
	 * @param argv
	 */
	public static void main(String[] argv) 
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
        	public void run() 
        	{
            	try  {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } 
            	catch (Exception e) {}

            	new GCLO();
            }
		});
	}
}
