package fr.gclo.edition;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.gclo.beans.Commande;
import fr.gclo.beans.Societe;
import fr.gclo.modeles.CommandeDAO;
import fr.gclo.modeles.CommandeLigneDAO;
import fr.gclo.modeles.SocieteDAO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class BonDeCommande {
	
	public BonDeCommande() {};
	
	public boolean generate(String sFileName, String ID) {
		
		DocumentPDF doc = new DocumentPDF(PageSize.A4, 10, 10, 10, 10);
		
		Societe societe = new SocieteDAO().read("1");
		Commande commande = new CommandeDAO().read(ID);
		try {
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(sFileName));
			
			doc.open();
			
			PdfContentByte canvas = writer.getDirectContentUnder();
			
			int placement = 790;
			
			doc.ajoutText(canvas, societe.getNomsociete(), 20, placement, 20);
			doc.ajoutText(canvas, societe.getAdresse1societe(), 20, placement - 20, 10);
			doc.ajoutText(canvas, societe.getCodepostalsociete() + " " + societe.getVillesociete(), 20, placement - 60, 10);
			doc.ajoutText(canvas, "Tél. : " + societe.getTelephonesociete(), 20, placement - 80, 10);
			doc.ajoutText(canvas, "Email : " + societe.getAdressedemessageriesociete(), 20, placement - 100, 10);
			doc.ajoutText(canvas, "Site : " + societe.getSitesociete(), 20, placement - 120, 10);
			
			doc.ajoutText(canvas, commande.getRaisonSocialeTiers(), 400, placement - 40, 10);
			doc.ajoutText(canvas, commande.getAdresse1Tiers(), 400, placement - 60, 10);
			doc.ajoutText(canvas, commande.getAdresse2Tiers(), 400, placement - 80, 10);
			doc.ajoutText(canvas, commande.getCodePostalTiers() + " " + commande.getVilleTiers(), 400, placement - 100, 10);
			doc.ajoutText(canvas, commande.getPaysTiers(), 400, placement - 120, 10);
			
			doc.ajoutText(canvas, "Confirmation de Commande", 20, placement - 160, 10);
			doc.ajoutText(canvas, commande.getSaisonCommande(), 20, placement - 180, 10);
			doc.ajoutText(canvas, "Numero de Commande : " + commande.getCodeCommande(), 20, placement - 200, 10);
			
	        String[] entete 		= {"Saison", "Code Article", "Modele Article"};; 
	        int[] 	 largeurcolonne	= {10, 10, 10};	        
			PdfPTable tableauMvt = doc.ajoutEnteteTableau(largeurcolonne, entete);			
			
			for ( Object o: new CommandeLigneDAO().getCommandeLignes(ID)) {
				if ( placement-tableauMvt.getTotalHeight() < 120 ) {
					// Cas du saut de page
					tableauMvt.writeSelectedRows(0, -1, 20, placement, canvas);
					placement  = doc.sautDePage(doc, canvas, tableauMvt, placement, null, null);
					tableauMvt = doc.ajoutEnteteTableau(largeurcolonne, entete);
				}
				tableauMvt = doc.ajoutLigneTableau(tableauMvt, (String[])o);
			}
			
			tableauMvt.writeSelectedRows(0, -1, 20, placement, canvas);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		doc.close();
		
		return true;		
	}
	
	public boolean generateJasper(String nomDuDocumentPDF) throws Exception  {
		
    	String sourceFileName = "./src/fr/gclo/edition/Commande.jasper";
    	
    	JRDataSource dataSource = new JRBeanCollectionDataSource(CommandeDAO.getVecteurCommandeEditionDAO());
    	
    	JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileName, null, dataSource); 
    	
    	JasperExportManager.exportReportToPdfFile(jasperPrint, nomDuDocumentPDF); 
    	
		// PDF
		try {
			if ( System.getProperty("os.name").startsWith("Windows") ) {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + nomDuDocumentPDF);
			}
			else {
				Desktop.getDesktop().open(new File(nomDuDocumentPDF));		
			}
		} 
		catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog( null, "Probleme a l'ouverture du fichier PDF.", null, JOptionPane.ERROR_MESSAGE);		
		}

		return true;
	}	

}
