package fr.gclo.beans;

public class Article {
	private String 	CodeArticle				= "";
	private String 	ModeleArticle			= "";
	private String 	DesignationArticle		= "";
	private String 	SaisonArticle			= "";
	private String 	ColorisArticle			= "";
	private float 	PrixDeVenteHTArticle 	= 0.f;
	private float 	TauxTVAArticle 			= 0.f;
	private float 	PrixDeVenteTTCArticle 	= 0.f;
	private float 	PoidsArticle 			= 0.f;
	private int 	NumeroATAArticle 		= 0;
	private int 	NumeroModele 			= 0;

	public Article(String CodeArticle_, String 	ModeleArticle_, String 	DesignationArticle, String SaisonArticle, String ColorisArticle, float PrixDeVenteHTArticle_, 
				   float TauxTVAArticle_, float PrixDeVenteTTCArticle_, float PoidsArticle_, int NumeroATAArticle_, int NumeroModele_) 
	{
		this.setCodeArticle(CodeArticle_);
		this.setModeleArticle(ModeleArticle_);
		this.setDesignationArticle(DesignationArticle);
		this.setSaisonArticle(SaisonArticle);
		this.setColorisArticle(ColorisArticle);
		this.setPrixDeVenteHTArticle(PrixDeVenteHTArticle_);
		this.setTauxTVAArticle(TauxTVAArticle_);
		this.setPrixDeVenteTTCArticle(PrixDeVenteTTCArticle_);
		this.setPoidsArticle(PoidsArticle_);
		this.setNumeroATAArticle(NumeroATAArticle_);
		this.setNumeroModele(NumeroModele_);
	}

	public String getCodeArticle() {
		return CodeArticle;
	}

	public void setCodeArticle(String codeArticle) {
		CodeArticle = codeArticle;
	}

	public String getModeleArticle() {
		return ModeleArticle;
	}

	public void setModeleArticle(String modeleArticle) {
		ModeleArticle = modeleArticle;
	}

	public String getDesignationArticle() {
		return DesignationArticle;
	}

	public void setDesignationArticle(String designationArticle) {
		DesignationArticle = designationArticle;
	}

	public String getSaisonArticle() {
		return SaisonArticle;
	}

	public void setSaisonArticle(String saisonArticle) {
		SaisonArticle = saisonArticle;
	}

	public String getColorisArticle() {
		return ColorisArticle;
	}

	public void setColorisArticle(String colorisArticle) {
		ColorisArticle = colorisArticle;
	}

	public float getPrixDeVenteHTArticle() {
		return PrixDeVenteHTArticle;
	}

	public void setPrixDeVenteHTArticle(float prixDeVenteHTArticle) {
		PrixDeVenteHTArticle = prixDeVenteHTArticle;
	}

	public float getTauxTVAArticle() {
		return TauxTVAArticle;
	}

	public void setTauxTVAArticle(float tauxTVAArticle) {
		TauxTVAArticle = tauxTVAArticle;
	}

	public float getPrixDeVenteTTCArticle() {
		return PrixDeVenteTTCArticle;
	}

	public void setPrixDeVenteTTCArticle(float prixDeVenteTTCArticle) {
		PrixDeVenteTTCArticle = prixDeVenteTTCArticle;
	}

	public float getPoidsArticle() {
		return PoidsArticle;
	}

	public void setPoidsArticle(float poidsArticle) {
		PoidsArticle = poidsArticle;
	}

	public int getNumeroATAArticle() {
		return NumeroATAArticle;
	}

	public void setNumeroATAArticle(int numeroATAArticle) {
		NumeroATAArticle = numeroATAArticle;
	}

	public int getNumeroModele() {
		return NumeroModele;
	}

	public void setNumeroModele(int numeroModele) {
		NumeroModele = numeroModele;
	}
}
