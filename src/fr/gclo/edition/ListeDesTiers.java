package fr.gclo.edition;

import java.io.FileOutputStream;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ListeDesTiers {
	
	public ListeDesTiers() {};
	
	public boolean generate(String sFileName) {
		
		DocumentPDF doc = new DocumentPDF(PageSize.A4, 10, 10, 10, 10);
		
		try {
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(sFileName));
			
			doc.open();
			
			PdfContentByte canvas = writer.getDirectContentUnder();
			
			int placement = 750;
			
			doc.ajoutText(canvas, "LISTE DES TIERS", 20, placement + 40, 20);
						
	        String[] entete 		= {"Date", "Marque", "Article", "Qt�", "P.U. T.T.C.", "REMISE", "TOTAL T.T.C."};; 
	        int[] 	 largeurcolonne	= {10, 10, 10, 10, 10, 10, 10};	        
			PdfPTable tableauMvt = doc.ajoutEnteteTableau(largeurcolonne, entete);			
			
//			for ( Object o: new TiersDAO().getSQLEdition())) {
//				if ( placement-tableauMvt.getTotalHeight() < 120 ) {
//					// Cas du saut de page
//					tableauMvt.writeSelectedRows(0, -1, 20, placement, canvas);
//					placement  = doc.sautDePage(doc, canvas, tableauMvt, placement, null, null);
//					tableauMvt = doc.ajoutEnteteTableau(largeurcolonne, entete);
//				}
//				tableauMvt = doc.ajoutLigneTableau(tableauMvt, (String[])o);
//			}
			
			tableauMvt.writeSelectedRows(0, -1, 20, placement, canvas);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		doc.close();
		
		return true;		
	}
}
