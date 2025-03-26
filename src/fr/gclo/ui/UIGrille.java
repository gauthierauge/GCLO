package fr.gclo.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import fr.GCLO;
import fr.gclo.Environnement;
import fr.gclo.ui.composants.JSDWScrollPane;
import fr.gclo.ui.composants.JSearchTextField;
import fr.gclo.ui.composants.JlblEtiquetteSousTitre;
import fr.gclo.ui.composants.JpnlSeparateurMenu;
import fr.gclo.ui.composants.JtblTable;
import fr.gclo.ui.composants.StringCenter;
import fr.gclo.ui.composants.TableModelSDW;

@SuppressWarnings("serial")
public class UIGrille extends JDialog implements ActionListener {
	
	protected JPanel 						pnlMenu				= null;
	
	protected String 						l_titre 			= "";
	
	protected JPanel 						pnlEntete 			= null;
	
	protected JPanel 						pnlCorps 			= null;

	public JtblTable 		 				grid 				= new JtblTable();
	protected TableRowSorter<TableModel> 	sorter 				= null;
	private JSDWScrollPane 					scrollpane 			= new JSDWScrollPane(grid);

	protected JSearchTextField 				searchTextField 	= new JSearchTextField();
	
	private String 							classObjetDAO		= "";
	private String 							classFicheSaisie	= "";
	
	private String							strSQL				= "";
	
	@SuppressWarnings("rawtypes")
	private Class[]							columnClass			= null;
	
	private final JButton 					cmdQuitter 			= new JButton("QUITTER [ESC]");
	private final JButton 					cmdAjouter 	 		= new JButton("AJOUTER");
	private final JButton 					cmdModifier	 		= new JButton("MODIFIER");
	private final JButton 					cmdSupprimer 		= new JButton("SUPPRIMER");
	private final JButton 					cmdSelectionner		= new JButton("SELECTIONNER");
	private final JButton 					cmdEditer	 		= new JButton("IMPRIMER");
	
	private final JLabel					lblDatabaseState	= new JLabel();
	
    protected final JPopupMenu 				popup 				= new JPopupMenu();
		
	public boolean 							retourEcran 		= false;
	
	public void createPopUpEditionGrille() {}
	
	public void screenClose() {	
		
		grid.saveWidth();		
		dispose();
	}		
	
	private void showLineCount() {
		
		lblDatabaseState.setText("<html><br>" + grid.getRowSorter().getViewRowCount() + " retrieved line(s).</htlm>");
	}

	private void newFilter()  {
		
		ArrayList<RowFilter<TableModel, Object>> andFilter = new ArrayList<RowFilter<TableModel, Object>>(); //split.length);
		ArrayList<RowFilter<TableModel, Object>> subFilterOR;
		ArrayList<RowFilter<TableModel, Object>> subFilterAND;
		RowFilter<TableModel, Object> rf;
		RowFilter<TableModel, Object> rfAND;
		RowFilter<TableModel, Object> rf0;

		sorter.setRowFilter(null);

		try {
			subFilterOR 	= new ArrayList<RowFilter<TableModel, Object>>();
			subFilterAND 	= new ArrayList<RowFilter<TableModel, Object>>();
			
			for (Integer i = 0; i < grid.getModel().getColumnCount(); i++)
			{
				if ( i > 0 ) {
					if ( grid.getModel().getColumnClass(i).equals(Integer.class) || grid.getModel().getColumnClass(i).equals(String.class) || grid.getModel().getColumnClass(i).equals(StringCenter.class)) {
						rf0 = RowFilter.regexFilter("(?i)" + Pattern.quote(searchTextField.getText()), i);
						subFilterOR.add(rf0);
					}
				}
			}
				
			rf 		= RowFilter.orFilter(subFilterOR);
			rfAND 	= RowFilter.andFilter(subFilterAND);
			andFilter.add(rf);
			andFilter.add(rfAND);		
		}
		catch (PatternSyntaxException pse) {}

		RowFilter<TableModel, Object> rowf = RowFilter.andFilter(andFilter);
		
		sorter.setRowFilter(rowf);
		
		grid.isFiltered = grid.getRowSorter().getModelRowCount() != grid.getRowSorter().getViewRowCount();

		showLineCount();
	}	
		
	protected void createMenu() {
		
		pnlMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		pnlMenu.setOpaque(false);
		
		cmdQuitter.addActionListener(this);
		cmdAjouter.addActionListener(this);
		cmdModifier.addActionListener(this);
		cmdSupprimer.addActionListener(this);
		cmdSelectionner.addActionListener(this);
		cmdEditer.addActionListener(this);
			
		cmdQuitter.setFocusable(false);
		cmdAjouter.setFocusable(false);
		cmdModifier.setFocusable(false);
		cmdSupprimer.setFocusable(false);
		cmdSelectionner.setFocusable(false);
		cmdEditer.setFocusable(false);
		
		searchTextField.getDocument().addDocumentListener(new documentFiltrer());
		
		pnlMenu.add(cmdQuitter);	
		pnlMenu.add(new JpnlSeparateurMenu());
		pnlMenu.add(cmdAjouter);	
		pnlMenu.add(cmdModifier);		
		pnlMenu.add(cmdSupprimer);		
		pnlMenu.add(new JpnlSeparateurMenu());
		pnlMenu.add(cmdSelectionner);		
		pnlMenu.add(new JpnlSeparateurMenu());
		pnlMenu.add(cmdEditer);			
		pnlMenu.add(new JpnlSeparateurMenu());
		pnlMenu.add(searchTextField);	
	}
	
	private void ajouter() {
    	
		try {
			Class<?> 		c 		= Class.forName(classFicheSaisie);
			Constructor<?> 	constr 	= c.getConstructor(JDialog.class, String.class, int.class);
			Object 			o 		= constr.newInstance(this, "CREATION", 0);
			((UIDialogFiche)o).setVisible(true);
			if ( ((UIDialogFiche)o).retourEcran ) { 
				grid.setData(strSQL, this.getClass().getCanonicalName());
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
	}
	
	private void modifier() {
		  	
		if ( grid.getSelectedRow() == -1 ) {
            JOptionPane.showMessageDialog(null, "Aucun item de s�lectionn�...", "Information", JOptionPane.INFORMATION_MESSAGE);	                
            return;
		}

		try {
			Class<?> 		c 		= Class.forName(classFicheSaisie);
			Constructor<?> 	constr 	= c.getConstructor(JDialog.class, String.class, String.class);
			Object 			o 		= constr.newInstance(this, "MODIFICATION", (String)grid.getValueAt(grid.getSelectedRow(), 0));
			((UIDialogFiche)o).setVisible(true);
			if ( ((UIDialogFiche)o).retourEcran ) {
				grid.setData(strSQL, this.getClass().getCanonicalName());
			}
			
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
	}
	
	private void supprimer() {
		  	
		if ( grid.getSelectedRow() == -1 ) {
            JOptionPane.showMessageDialog(null, "Aucun item de s�lectionn�...", "Information", JOptionPane.INFORMATION_MESSAGE);	                
            return;
		}  
	
		if ( JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer cet item ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION ) return; 

		String dummy = (String)grid.getValueAt(grid.getSelectedRow(), 0);
		try {
			Class<?> c	= Class.forName(this.classObjetDAO);
			Object   o	= c.newInstance() ;
			String   s	= (String) c.getDeclaredMethod("delete", String.class).invoke(o, dummy);
			
			if ( s.equalsIgnoreCase("")) {
				grid.setData(strSQL, this.getClass().getCanonicalName());
			}
	        
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	private void selectionner() {
		  	
		if ( grid.getSelectedRow() == -1 ) {
            JOptionPane.showMessageDialog(null, "Aucun item de s�lectionn�...", "Information", JOptionPane.INFORMATION_MESSAGE);	                
            return;
		}  	
		
		retourEcran = true;
		
		screenClose();
	}
	
	protected void createGUI() {
		
		setLayout(new BorderLayout());
		
        Box verticalBox = Box.createVerticalBox();
		
		pnlEntete = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		pnlEntete.setBorder(BorderFactory.createEmptyBorder(2, 5, 0, 0));
		pnlEntete.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
		pnlEntete.setOpaque(false);
		pnlEntete.add(new JlblEtiquetteSousTitre(" " + l_titre));
		
//		searchTextField.addActionListener(this);
		
		verticalBox.add(pnlEntete);
		verticalBox.add(pnlMenu);

		add(verticalBox, BorderLayout.NORTH);
		
		pnlCorps = new JPanel(new BorderLayout());
		pnlCorps.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		
		add(pnlCorps, BorderLayout.CENTER);
		
		try {
			Class<?> c    		= Class.forName(this.classObjetDAO);
			Object   o 			= c.newInstance() ;

	        this.strSQL 		= (String) 	c.getDeclaredMethod("getSQL").invoke(o, new Object[] {});
	        this.columnClass 	= (Class[]) c.getDeclaredMethod("getColumnClass").invoke(0, new Object[] {});
	        
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		grid.setModel(new TableModelSDW(this.columnClass));
		
		grid.setData(this.strSQL, this.getClass().getCanonicalName());
	
		sorter = new TableRowSorter<TableModel>(grid.getModel()) {};
		
		grid.setRowSorter(sorter);
		
		grid.addMouseListener(new grid_mouseListener());
				
		pnlCorps.add(scrollpane);		
		
		pnlCorps.add(lblDatabaseState, BorderLayout.SOUTH);
		
	    setTitle(Environnement.nomApplication);

		showLineCount();
	}
	
	public UIGrille(Component parentFrame, String titre, String classe, String fiche, String titreEdition ) {
		
		if ( parentFrame != null ) {
			Point point = parentFrame.getLocation();
			int decalageVertical = parentFrame instanceof UIDialogFiche ? 10 : 53;
			setLocation(point.x + 2, point.y + decalageVertical);
			
			Dimension dummy = null;
			Dimension dimension = parentFrame.getSize();
			if ( ! (parentFrame instanceof GCLO) ) {
				dummy = new Dimension(1590, dimension.height - 75);
			}
			else {
				dummy = new Dimension(dimension.width - 3, dimension.height - 70);
			}
			setSize(dummy);
		}
		
	    Action escapeAction = new AbstractAction() {	    	
	        public void actionPerformed(ActionEvent e) { screenClose(); }
	    }; 
	    
	    KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
	    getRootPane().getActionMap().put("ESCAPE", escapeAction);
	    
		this.l_titre 			= titre;
		this.classObjetDAO 		= classe;
		this.classFicheSaisie	= fiche;
		
		this.createMenu();
		this.createGUI();
		this.createPopUpEditionGrille();

		setModal(true);

		setVisible(true);
	}
	
	private class grid_mouseListener implements MouseListener {
		
		public void mouseClicked(MouseEvent e) {			
			if (e.getClickCount() == 2) {
				modifier();
			}
		}
	
		public void mousePressed(MouseEvent e) 	{}
	
		public void mouseReleased(MouseEvent e) {}
	
		public void mouseEntered(MouseEvent e) 	{}
	
		public void mouseExited(MouseEvent e) 	{}
	}
	
	private class documentFiltrer implements DocumentListener {
		  public void changedUpdate(DocumentEvent e) {
			  newFilter();
		  }
		  public void removeUpdate(DocumentEvent e) {
			  newFilter();
		  }
		  public void insertUpdate(DocumentEvent e) {
			  newFilter();
		  }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if ( e.getSource() == cmdAjouter ) {
			ajouter();
		}
		else if ( e.getSource() == cmdModifier ) {
			modifier();
		}
		else if ( e.getSource() == cmdSupprimer ) {
			supprimer();
		}
		else if ( e.getSource() == cmdSelectionner ) {
			selectionner();
		}
		else if ( e.getSource() == cmdEditer ) {
			popup.show(cmdEditer, 0, cmdEditer.getBounds().height);
		}
		else if ( e.getSource() == cmdQuitter ) {
        	screenClose();
		}
//		else if ( e.getSource() == searchTextField ) {
//        	System.out.println("searchTextField");
//		}
		
	}
}
