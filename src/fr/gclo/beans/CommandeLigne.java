package fr.gclo.beans;

public class CommandeLigne {

	private int    CompteurCommandeLigne	= 0;
	private String CodeCommande				= "";
	private String CodeArticle				= "";
	private String ColorisArticle			= "";
	private String ModeleArticle			= "";
	private String DesignationArticle		= "";
	private int    NbArticleTailleUnique	= 0;
	private int    NbArticleTailleS			= 0;
	private int    NbArticleTailleM			= 0;
	private int    NbArticleTailleL			= 0;
	private int    NbArticleTailleXL		= 0;
	private int    NbArticleTailleXXL		= 0;
	private float  PrixDeVentehHTArticle	= 0.f;
	private float  montantTotalHT			= 0.f;
	private String Composition				= "";

	public CommandeLigne(int CompteurCommandeLigne_, String CodeCommande_, String CodeArticle_, String ColorisArticle_, String ModeleArticle_, 
						 String DesignationArticle_, int nbarticletailleunique_, int nbarticletailles_, int nbarticletaillem_, 
						 int nbarticletaillel_, int nbarticletaillexl_,int nbarticletaillexxl_, float prixdeventehtarticle_, float montanttotalht_) 
	{
		setCompteurCommandeLigne(CompteurCommandeLigne_);
		setCodeCommande(CodeCommande_);
		setCodeArticle(CodeArticle_);
		setColorisArticle(ColorisArticle_);
		setModeleArticle(ModeleArticle_);
		setDesignationArticle(DesignationArticle_);
		setNbArticleTailleUnique(nbarticletailleunique_);
		setNbArticleTailleS(nbarticletailles_);
		setNbArticleTailleM(nbarticletaillem_);
		setNbArticleTailleL(nbarticletaillel_);
		setNbArticleTailleXL(nbarticletaillexl_);
		setNbArticleTailleXXL(nbarticletaillexxl_);
		setPrixDeVentehHTArticle(montanttotalht_);
		setMontantTotalHT(montanttotalht_);
	}

	public CommandeLigne(int CompteurCommandeLigne_, String CodeCommande_, String CodeArticle_, String ColorisArticle_, String ModeleArticle_, 
						 String DesignationArticle_, int nbarticletailleunique_, int nbarticletailles_, int nbarticletaillem_, 
						 int nbarticletaillel_, int nbarticletaillexl_,int nbarticletaillexxl_, float prixdeventehtarticle_, float montanttotalht_,
						 String composition_) 
	{
		setCompteurCommandeLigne(CompteurCommandeLigne_);
		setCodeCommande(CodeCommande_);
		setCodeArticle(CodeArticle_);
		setColorisArticle(ColorisArticle_);
		setModeleArticle(ModeleArticle_);
		setDesignationArticle(DesignationArticle_);
		setNbArticleTailleUnique(nbarticletailleunique_);
		setNbArticleTailleS(nbarticletailles_);
		setNbArticleTailleM(nbarticletaillem_);
		setNbArticleTailleL(nbarticletaillel_);
		setNbArticleTailleXL(nbarticletaillexl_);
		setNbArticleTailleXXL(nbarticletaillexxl_);
		setPrixDeVentehHTArticle(montanttotalht_);
		setMontantTotalHT(montanttotalht_);
		setComposition(composition_);
	}

	public int getCompteurCommandeLigne() {
		return CompteurCommandeLigne;
	}

	public void setCompteurCommandeLigne(int compteurCommandeLigne) {
		CompteurCommandeLigne = compteurCommandeLigne;
	}

	public String getCodeCommande() {
		return CodeCommande;
	}

	public void setCodeCommande(String codeCommande) {
		CodeCommande = codeCommande;
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

	public String getColorisArticle() {
		return ColorisArticle;
	}

	public void setColorisArticle(String colorisArticle) {
		ColorisArticle = colorisArticle;
	}

	public int getNbArticleTailleS() {
		return NbArticleTailleS;
	}

	public void setNbArticleTailleS(int nbArticleTailleS) {
		NbArticleTailleS = nbArticleTailleS;
	}

	public int getNbArticleTailleUnique() {
		return NbArticleTailleUnique;
	}

	public void setNbArticleTailleUnique(int nbArticleTailleUnique) {
		NbArticleTailleUnique = nbArticleTailleUnique;
	}

	public int getNbArticleTailleM() {
		return NbArticleTailleM;
	}

	public void setNbArticleTailleM(int nbArticleTailleM) {
		NbArticleTailleM = nbArticleTailleM;
	}

	public int getNbArticleTailleL() {
		return NbArticleTailleL;
	}

	public void setNbArticleTailleL(int nbArticleTailleL) {
		NbArticleTailleL = nbArticleTailleL;
	}

	public int getNbArticleTailleXL() {
		return NbArticleTailleXL;
	}

	public void setNbArticleTailleXL(int nbArticleTailleXL) {
		NbArticleTailleXL = nbArticleTailleXL;
	}

	public int getNbArticleTailleXXL() {
		return NbArticleTailleXXL;
	}

	public void setNbArticleTailleXXL(int nbArticleTailleXXL) {
		NbArticleTailleXXL = nbArticleTailleXXL;
	}

	public float getPrixDeVentehHTArticle() {
		return PrixDeVentehHTArticle;
	}

	public void setPrixDeVentehHTArticle(float prixDeVentehHTArticle) {
		PrixDeVentehHTArticle = prixDeVentehHTArticle;
	}

	public float getMontantTotalHT() {
		return montantTotalHT;
	}

	public void setMontantTotalHT(float montantTotalHT) {
		this.montantTotalHT = montantTotalHT;
	}

	public String getComposition() {
		return Composition;
	}

	public void setComposition(String composition) {
		Composition = composition;
	}

}
