package fr.gclo.ui.composants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ToolTipManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import fr.GCLO;
import fr.gclo.controller.DataBaseInstance;


public class JtblTableInput extends JTable {

	private static final long serialVersionUID 		= 1L;
	
	private String						nomTable 	= "";
	public boolean 						isFiltered 	= false;
	
	public void setData(final String strSQL) {
		
		ResultSet rs = DataBaseInstance.executeQuery(strSQL);
		
		if ( rs == null ) return;
		
		try {
			ResultSetMetaData md = rs.getMetaData();
			
			nomTable = md.getTableName(1);
			
			((TableModelSDW)getModel()).setRowCount(0);
			((TableModelSDW)getModel()).setColumnCount(0);
			
			for (int j=1; j <= md.getColumnCount(); j++) ((TableModelSDW)getModel()).addColumn(md.getColumnLabel(j).replace('_',' ').toUpperCase());
			
			this.getColumn("COMPTEUR").setMinWidth(0);
			this.getColumn("COMPTEUR").setMaxWidth(0);
			
			String serverNamere = GCLO.properties.getProperty(nomTable);
			
			if ( serverNamere != null ) {
				String[] strArray = serverNamere.split(" ");
				
				if ( strArray.length == md.getColumnCount() )	// Lazy test... si on rajoute une colonne dans la requete, pour �viter de modifier le fichier properties aussi...		
					for ( TableColumn c:  Collections.list( getColumnModel().getColumns()) ) c.setPreferredWidth(Integer.parseInt(strArray[c.getModelIndex()])); 
			}
			
			String[] rsRow = new String[md.getColumnCount()];
			while (rs.next()) {
				for ( int j=0; j < md.getColumnCount(); j++ ) rsRow[j] = rs.getString(j + 1); 
				((TableModelSDW)getModel()).addRow(rsRow);
			}			
		} 
		catch (SQLException e) {} 
		finally { 
			try { rs.close(); rs = null; } catch (SQLException ignore) {}
		}
	}

	public void saveWidth() {
		
		String columnsWidth = "";
		
        for ( TableColumn c:  Collections.list( getColumnModel().getColumns()) )  columnsWidth += c.getWidth() + " ";
        
        GCLO.properties.setProperty(this.nomTable, columnsWidth.trim());
	}
	
	public boolean getScrollableTracksViewportHeight() {
	    if(getParent() instanceof JViewport)
	    return(((JViewport)getParent()).getHeight() > getPreferredSize().height);

	    return super.getScrollableTracksViewportHeight();
	}	
	
	  protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(getRowCount() == 0) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.BLACK);
			g2d.drawString("Appuyer sur la touche INS pour ins�rer une ligne.", 10, 20);
		}
	}	
	
	public JtblTableInput() {	
		
		super();
		
		setRowHeight(20);
		
		setAutoResizeMode(JtblTable.AUTO_RESIZE_OFF);
		
		getTableHeader().setDefaultRenderer(new HeaderRenderer());
		
		ToolTipManager.sharedInstance().unregisterComponent(this);
		ToolTipManager.sharedInstance().unregisterComponent(this.getTableHeader());
		
		setDefaultRenderer(Date.class,  		new DateCellRenderer());
		setDefaultRenderer(Float.class, 		new FloatCellRenderer());
		setDefaultRenderer(FloatPercent.class, 	new FloatPercentCellRenderer());
		setDefaultRenderer(StringCenter.class, 	new StringCenterCellRenderer());
	}	
	
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) 
    {
		Component c = super.prepareRenderer(renderer, row, column);
		
		//  Alternate row color
		//if (!isRowSelected(row)) c.setBackground(row % 2 == 0 ? getBackground() : Environnement.couleurDeFondAlternatedRow);
		
		// change color is filtered
		//c.setForeground(isFiltered ? Environnement.couleurLignesFiltres : Color.BLACK);
		
		return c;
    }	
 }