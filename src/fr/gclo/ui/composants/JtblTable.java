package fr.gclo.ui.composants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import fr.GCLO;
import fr.gclo.Environnement;
import fr.gclo.controller.DataBaseInstance;

public class JtblTable extends JTable {

	private static final long serialVersionUID 			= 1L;
	
	private String						nomTable 		= "";
	public boolean 						isFiltered 		= false;
	private String						nomComposant	= "";
	
	public void setData(final String strSQL, final String _nomComposant) {
		
		ResultSet rs = DataBaseInstance.executeQuery(strSQL);
		
		if ( rs == null ) return;
		
		nomComposant = _nomComposant;
		
		try {
			
			ResultSetMetaData md = rs.getMetaData();
			
			nomTable = md.getTableName(1);
			
			((TableModelSDW)getModel()).setRowCount(0);
			((TableModelSDW)getModel()).setColumnCount(0);
			
			boolean colonneCompteurExiste = false;
			for (int j=1; j <= md.getColumnCount(); j++) {
				((TableModelSDW)getModel()).addColumn(md.getColumnLabel(j).replace('_',' ').toUpperCase());
				
				if ( !colonneCompteurExiste ) {
					colonneCompteurExiste = (md.getColumnLabel(j).replace('_',' ').toUpperCase().equalsIgnoreCase("COMPTEUR")) ? true : false;
				}
			}
			
			if ( colonneCompteurExiste ) {
				this.getColumn("COMPTEUR").setMinWidth(0);
 				this.getColumn("COMPTEUR").setMaxWidth(0);
			};
			
			String serverNamere = GCLO.properties.getProperty(nomComposant + "." + nomTable);
			
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
        
        GCLO.properties.setProperty(nomComposant + "." + nomTable, columnsWidth.trim());
	}
	
	public boolean getScrollableTracksViewportHeight() {
		
		return getParent() instanceof JViewport ? ((JViewport)getParent()).getHeight() > getPreferredSize().height : super.getScrollableTracksViewportHeight();
	}	
	
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		if ( getRowCount() == 0 ) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.GRAY);
			g2d.setFont(new Font("Helvetica", Font.PLAIN, 14)); 
			g2d.drawString("Aucune Donnees a Afficher.",10,20);
		}
	}	
	
	public JtblTable() {
		
		super();
		
		setRowHeight(20);
		
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
		
		getTableHeader().setDefaultRenderer(new HeaderRenderer());
		
		setSelectionBackground(Color.lightGray);
		
		setDefaultRenderer(Date.class,  		new DateCellRenderer());
		setDefaultRenderer(Float.class, 		new FloatCellRenderer());
		setDefaultRenderer(FloatPercent.class, 	new FloatPercentCellRenderer());
		setDefaultRenderer(StringCenter.class, 	new StringCenterCellRenderer());
		setDefaultRenderer(JCheckBox.class, 	new CheckBoxRenderer());
	}	
	
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
    	
		Component c = super.prepareRenderer(renderer, row, column);
		
		//  Alternate row color
		if (!isRowSelected(row)) c.setBackground(row % 2 == 0 ? getBackground() : Environnement.couleurDeFondAlternatedRow);
		
		// change color is filtered
		c.setForeground(isFiltered ? Environnement.couleurLignesFiltres : Color.BLACK);
		
		return c;
    }	
}

class HeaderRenderer extends DefaultTableCellRenderer {
	
	private static final long serialVersionUID = 1L;

	public HeaderRenderer() {
		
	    setHorizontalAlignment(SwingConstants.CENTER);
	    
	    setOpaque(true);

	    setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.black));

	    setBackground(Color.lightGray);
    }
		
	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {

        setFont(new Font("Lato", Font.ROMAN_BASELINE, 12));
	
	    setValue(value);
	    
	    return this;
	}   
}

class DateCellRenderer extends DefaultTableCellRenderer 
{
	private static final long serialVersionUID = -5127020452000139717L;

	public DateCellRenderer() {
		setHorizontalAlignment(CENTER);
	}

	protected void setValue(Object value) {
		if ( value != null ) {
			try {
				value = Environnement.formatDateFR.format(Environnement.formatDateSQL.parse((String) value));
			} 
			catch (ParseException e) { e.printStackTrace(); }
		}
		super.setValue(value);
	}
}

class FloatPercentCellRenderer extends DefaultTableCellRenderer 
{
	private static final long serialVersionUID = 1L;
	
	private NumberFormat dFormatter = new DecimalFormat("#0.00 %");
	
	public FloatPercentCellRenderer() {
		setHorizontalAlignment(RIGHT);
	}

	protected void setValue(Object value) {
		if ( value != null ) {
			value = dFormatter.format(value == null ? BigDecimal.ZERO : Float.parseFloat((String) value));
		}
		super.setValue(value);
	}
}

class FloatCellRenderer extends DefaultTableCellRenderer 
{
	private static final long serialVersionUID = 1L;
	
	private NumberFormat dFormatter = new DecimalFormat("#0.00");
	
	public FloatCellRenderer() 
	{
		setHorizontalAlignment(RIGHT);
	}

	protected void setValue(Object value) 
	{
		if ( value != null )
		{
			value = dFormatter.format(value == null ? BigDecimal.ZERO : Float.parseFloat((String) value));
		}
		super.setValue(value);
	}
}

class StringCenterCellRenderer extends DefaultTableCellRenderer {
	
	private static final long serialVersionUID = 1L;
	
	public StringCenterCellRenderer() {
		setHorizontalAlignment(CENTER);
	}
}

@SuppressWarnings("serial")
class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

    CheckBoxRenderer() {
      setHorizontalAlignment(CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      if (isSelected) {
        setForeground(table.getSelectionForeground());
        //super.setBackground(table.getSelectionBackground());
        setBackground(table.getSelectionBackground());
      } else {
        setForeground(table.getForeground());
        setBackground(table.getBackground());
      }
      //setSelected((value != null && ((Boolean) value).booleanValue()));
      Boolean dummy = (String)value == "t" ? true: false;
      setSelected((value != null) && (!dummy));	// TODO POURQUOI !
      return this;
    }
}