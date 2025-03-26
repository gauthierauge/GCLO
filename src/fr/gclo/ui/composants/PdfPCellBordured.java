package fr.gclo.ui.composants;

import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

public class PdfPCellBordured extends PdfPCell {

	public PdfPCellBordured(Phrase phrase) {
		
		super(phrase);
		setBorder(0);
		setBorderWidthLeft(0.5f);
		setHorizontalAlignment(Element.ALIGN_CENTER);
		setVerticalAlignment(Element.ALIGN_MIDDLE);
	}
}
