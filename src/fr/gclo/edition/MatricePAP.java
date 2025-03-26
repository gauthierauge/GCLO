package fr.gclo.edition;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import fr.gclo.modeles.MatriceDAO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class MatricePAP {
	
	public MatricePAP() {};
	
	public boolean generate() throws Exception  {
		
    	String sourceFileName = "./src/fr/gclo/edition/MatricePAP.jasper";
    	String nomDuDocumentPDF = "C:/Temp/MAtricePAP.pdf";
    	
    	JRDataSource dataSource = new JRBeanCollectionDataSource(MatriceDAO.getVecteurMatriceDAO());
    	
    	// Fill the report with data (empty data source for simplicity) 
    	JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileName, null, dataSource); 
    	
    	// Export the report to a PDF file 
    	JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Temp/MatricePAP.pdf"); 
    	
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
