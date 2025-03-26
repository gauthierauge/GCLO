package fr.gclo.edition;

import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class DocumentPDF extends Document 
{
	public void ajoutText(PdfContentByte support, String texte, int x, int y, int taille) throws DocumentException, IOException
	{
		//	Ajout d'un texte sur le support � l'emplacement x,y avec une taille de police donnee
		support.saveState();                              
		support.beginText();                              
		support.moveText(x, y);                        
		support.setFontAndSize(BaseFont.createFont(), taille); 
		support.showText(texte);                   
		support.endText();                                
		support.restoreState();     
	}
	
	public void ajoutText(PdfContentByte support, String texte, int x, int y, int taille, boolean gras) throws DocumentException, IOException
	{
		//	Ajout d'un texte sur le support � l'emplacement x,y avec une taille de police donnee
		support.saveState();                              
		support.beginText();                              
		support.moveText(x, y);   
		if (gras)
		{
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD,BaseFont.CP1252 ,BaseFont.NOT_EMBEDDED);
			support.setFontAndSize(bf, taille);
		}
		else
		{
			support.setFontAndSize(BaseFont.createFont(), taille); 
		}
		support.showText(texte);                   
		support.endText();                                
		support.restoreState();     
			
	}
	
	public void ajoutTextCourrier(PdfContentByte support, String texte, int x, int y, int taille) throws DocumentException, IOException
	{
		//	Ajout d'un texte sur le support � l'emplacement x,y avec une taille de police donnee
		support.saveState();                              
		support.beginText();                              
		support.moveText(x, y);                        
		support.setFontAndSize(BaseFont.createFont(BaseFont.COURIER,BaseFont.MACROMAN ,BaseFont.EMBEDDED), taille); 
		support.showText(texte);                   
		support.endText();                                
		support.restoreState();     
			
	}
	
	public void ajoutTextArial(PdfContentByte support, String texte, int x, int y, int taille) throws DocumentException, IOException
	{
		//	Ajout d'un texte sur le support � l'emplacement x,y avec une taille de police donnee
		support.saveState();                              
		support.beginText();                              
		support.moveText(x, y);                        
		support.setFontAndSize(BaseFont.createFont(BaseFont.COURIER_OBLIQUE,BaseFont.CP1252 ,BaseFont.EMBEDDED), taille); 
		support.showText(texte);                   
		support.endText();                                
		support.restoreState();     
			
	}
	
	public void dessineRect(PdfContentByte support, int originX, int originY, int longX, int longY, int coulR, int coulG, int coulB, int coulFondR, int coulFondG, int coulFondB)
	{
		//	Dessin d'un rectangle sur le support ayant un point d'origine en x,y une longueur et une largeur donn�, une couleur en RGB pour le tour et une couleur en RGB pour le fond
		support.saveState();  
		PdfGState state = new PdfGState();
		state.setFillOpacity(1);
		support.setGState(state);
		support.setRGBColorFill(coulFondR,coulFondG,coulFondB);
		support.setRGBColorStroke(coulR, coulG, coulB);
		support.setLineWidth(1);
		support.rectangle(originX,originY,longX,longY);
		support.fillStroke();
		support.restoreState();   
	}
	
	public PdfPTable ajoutEnteteTableau(int[] tailleCols, String[] nomEntete) throws DocumentException
	{
		PdfPTable table = new PdfPTable(nomEntete.length);
		PdfPCell cell;
		table.setTotalWidth(550);
		for (int i=0; i<nomEntete.length; i++) {
//			cell = new PdfPCell(new Phrase(1,nomEntete[i], Environnement.verySmallfont));
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//			cell.setPadding(4);
//			cell.setBorderWidthLeft(0.1f);
//			cell.setBorderWidthRight(0.1f);
//			cell.setBorderWidthTop(0.1f);
//			cell.setBorderWidthBottom(0.1f);
//			cell.setBorderColor(BaseColor.GRAY);
//			table.addCell(cell);
		}
		return table;
	}
	
	public PdfPTable ajoutLigneTableau(PdfPTable table, String[] contenuLigne) throws DocumentException
	{
		PdfPCell cell;
		Font f = new Font();
		f.setColor(BaseColor.BLACK);
		f.setSize(8);

		for (int i=0; i<contenuLigne.length ; i++) {
			cell = new PdfPCell(new Phrase(1,contenuLigne[i],f));
			cell.setBackgroundColor(new BaseColor(0xff,0xff,0xff));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPadding(4);
			table.addCell(cell);
		}
		
		return table;		
	}

	public PdfPTable ajoutPiedTableau(PdfPTable table, String[] contenuLigne) throws DocumentException
	{
		PdfPCell cell;
		Font f = new Font();
		f.setColor(BaseColor.BLACK);
		f.setSize(8);
		f.setStyle(Font.BOLD);
		// Cellule Total
		cell = new PdfPCell(new Phrase(1,contenuLigne[0],f));
		cell.setBackgroundColor(new BaseColor(0xc1,0xc1,0xc1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPadding(4);
		cell.setColspan(table.getNumberOfColumns()-1);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase(1,contenuLigne[1],f));
		cell.setBackgroundColor(new BaseColor(0xff,0xff,0xff));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPadding(4);
		table.addCell(cell);
		
		table.completeRow();
		
		return table;
		
	}

	public int sautDePage(Document docEnCours, PdfContentByte support, PdfPTable table, int compteurYSurPage,Image imgPied, Image imgEntete) throws Exception {
		
		// PIED DE PAGE
		if(imgPied != null) {
			insertImg(imgPied, support, 500, 60, 20, 0);
		}
		
		// NOUVELLE PAGE PDF
		docEnCours.newPage();
		
		// ENTETE
		/*if(imgEntete != null) {
			insertImg(imgEntete, support, 500, 60, 20, 730);
		}*/
		
		// PLACEMENT
		//compteurYSurPage = 710;
		compteurYSurPage = 790;
		
		return compteurYSurPage;
	}
	
	public void insertImg(Image img, PdfContentByte support, int largeur, int hauteur, int x, int y) throws Exception
	{
		if(img.getScaledWidth()>img.getScaledHeight()) {
			support.addImage(img, (img.getScaledWidth()*largeur/img.getScaledWidth()) , 0, 0, (img.getScaledHeight()*largeur/img.getScaledWidth()), x, y);
		}
		else {
			support.addImage(img, (img.getScaledWidth()*hauteur/img.getScaledHeight()) , 0, 0, (img.getScaledHeight()*hauteur/img.getScaledHeight()), x, y);
		}
	}	
	
	public DocumentPDF(Rectangle pageSize, float marginLeft, float marginRight, float marginTop, float marginBottom) {
		
		super(pageSize, marginLeft, marginRight, marginTop, marginBottom);
		
		addAuthor("GCLO");
		addTitle("Catalogue");
		addSubject("GCLO est un logiciel fournit par la société SDW Sarl.");
		addKeywords("");				
	}
}
