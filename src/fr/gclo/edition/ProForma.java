package fr.gclo.edition;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import fr.gclo.modeles.CommandeDAO;
import fr.gclo.modeles.CommandeLigneDAO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ProForma {
	
	public ProForma() {};
	
	public boolean generateJasper(String nomDuDocumentPDF) throws Exception  {
		
    	String sourceFileName = "./src/fr/gclo/edition/ProForma.jasper";
    	
    	Map<String, Object> parameters = new HashMap<>();
    	
    	JRDataSource dataSource = new JRBeanCollectionDataSource(CommandeDAO.getVecteurCommandeEditionDAO());
    	
    	JRDataSource subreportDataSource = new JRBeanCollectionDataSource(CommandeLigneDAO.getVecteurCommandeLigneEditionDAO("001967"));
    	parameters.put("SUBREPORT_DATASOURCE", subreportDataSource);
    	
    	JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileName, parameters, dataSource); 
    	
    	JasperExportManager.exportReportToPdfFile(jasperPrint, nomDuDocumentPDF); 
    	
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + nomDuDocumentPDF);
		} 
		catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog( null, "Probleme a l'ouverture du fichier PDF.", null, JOptionPane.ERROR_MESSAGE);		
		}

		return true;
	}	
}
