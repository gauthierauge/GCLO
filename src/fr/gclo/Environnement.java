package fr.gclo;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Environnement 
{
	public static String nomApplication 						= "GCLO";
	public static String APPVERS								= "1.0a";		// Obligatoirement un format X.X_X (ex. : 1.4_b)
	public static String NEWAPPVERS								= "";
	public static String version 								= "Version " + APPVERS;
	public static String descriptifApplication					= "Gestion Commerciale Les Ours";
	public static String telephone								= "+33 4 99 99 99 9";
	public static String copyright								= "SDW SARL - 2023";
	
	
	public static Color couleurDeFondEntete						= new Color(52, 73, 94);
	public static Color couleurDeFondFocusedField				= new Color(255,215,0);
	public static Color couleurDeFondUnfocusedField				= Color.WHITE;
	public static Color couleurDeFondRequiredField				= new Color(255,140,0);
	public static Color couleurDeFondAlternatedRow				= new Color(241, 249, 254); // Color(206,236,244); // Color(185,198,196);
	public static Color couleurDeTexteSelectedTab				= new Color(10,100,200);
	public static Color couleurScrollBAr						= new Color(255,140,0);
	public static Color couleurLignesFiltres					= new Color(10,100,200);
	public static Color couleurTitreFenetre						= Color.LIGHT_GRAY;	
		
	public static NumberFormat dFormatter 						= new DecimalFormat("#0.00");
	public static NumberFormat dFormatter2 						= new DecimalFormat("#0.00000");
	public static NumberFormat dFormatter3 						= new DecimalFormat("#.##");
	public static NumberFormat entierFormatter 					= new DecimalFormat("#0");
	public static NumberFormat euroFormatter 					= new DecimalFormat("#0.00 �");
	
	public static SimpleDateFormat formatDateFR	 				= new SimpleDateFormat("dd/MM/yyyy", new Locale("FR"));
	public static SimpleDateFormat formatDateUS	 				= new SimpleDateFormat("yyyy-MM-dd", new Locale("FR"));
	public static SimpleDateFormat formatDateTirage 			= new SimpleDateFormat("dd-MM-yyyy", new Locale("FR"));
	public static SimpleDateFormat formatDateSQL				= new SimpleDateFormat("yyyy-MM-dd", new Locale("FR")); // NOUVEAU FORMAT POSTGRESQL 
	public static SimpleDateFormat formatDateSQLFirstDay		= new SimpleDateFormat("yyyy-MM-01", new Locale("FR"));
	public static SimpleDateFormat formatDateSauvegarde 		= new SimpleDateFormat("dd-MM-yyyy-HHmmss", new Locale("FR"));
	public static SimpleDateFormat formatDateExport				= new SimpleDateFormat("yyyyMMdd", new Locale("FR"));
	public static SimpleDateFormat formatDateExportDIF			= new SimpleDateFormat("ddMMyyyy", new Locale("FR"));
	
	public static Font verySmallfont 				= new Font("Lato", Font.TRUETYPE_FONT, 13);
	public static Font smallfont 					= new Font("Lato", Font.TRUETYPE_FONT, 14);

}