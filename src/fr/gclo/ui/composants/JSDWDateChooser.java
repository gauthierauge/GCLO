package fr.gclo.ui.composants;

import java.util.Date;

import com.toedter.calendar.JDateChooser;

public class JSDWDateChooser extends JDateChooser {
	
	private static final long serialVersionUID = 1L;

	public JSDWDateChooser(Date date, String name) 
	{
		super(date);
		setName(name);
	}

	public JSDWDateChooser(String name) 
	{
		super();
		setName(name);
	}
}
