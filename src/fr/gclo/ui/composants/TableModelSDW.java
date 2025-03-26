package fr.gclo.ui.composants;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class TableModelSDW extends DefaultTableModel 
{
	@SuppressWarnings("rawtypes")
	private Class[] type = null;
	
	@SuppressWarnings("rawtypes")
	public TableModelSDW(Class[] type) 
	{		
		this.type = type;
	}
		
	public boolean isCellEditable(int row, int column)
	{
		return false;		
	} 
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return ( type[columnIndex] == null ) ? Object.class : type[columnIndex];
	}
}
