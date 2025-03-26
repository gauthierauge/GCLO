package fr.gclo.beans;

public class Commande {

	private String 	CodeCommande			= "";
	private String 	RaisonSocialeTiers		= "";
	private String 	Adresse1Tiers			= "";
	private String 	Adresse2Tiers			= "";
	private String 	CodePostalTiers			= "";
	private String 	VilleTiers				= "";
	private String 	PaysTiers				= "";
	private String 	SaisonCommande				= "";
	private int 	NbArticleCommande			= 0;
	private float 	MontantBrutHTCommande = 0.0f;

	public Commande(String CodeCommande_,String RaisonSocialeTiers_, String Adresse1Tiers_, String Adresse2Tiers_, String CodePostalTiers_, String VilleTiers_, String PaysTiers_, String SaisonCommande_, int NbArticleCommande_, float MontantBrutHTCommande_ ) {
		setCodeCommande(CodeCommande_);
		setRaisonSocialeTiers(RaisonSocialeTiers_);
		setAdresse1Tiers(Adresse1Tiers_);
		setAdresse2Tiers(Adresse2Tiers_);
		setCodePostalTiers(CodePostalTiers_);
		setVilleTiers(VilleTiers_);
		setPaysTiers(PaysTiers_);
		setSaisonCommande(SaisonCommande_);
		
		setNbArticleCommande(NbArticleCommande_);
		setMontantbruthtcommande(MontantBrutHTCommande_);
	}

	public String getCodeCommande() {
		return CodeCommande;
	}

	public void setCodeCommande(String codeCommande) {
		CodeCommande = codeCommande;
	}

	public String getRaisonSocialeTiers() {
		return RaisonSocialeTiers;
	}

	public void setRaisonSocialeTiers(String raisonSocialeTiers) {
		RaisonSocialeTiers = raisonSocialeTiers;
	}

	public int getNbArticleCommande() {
		return NbArticleCommande;
	}

	public void setNbArticleCommande(int nbArticleCommande) {
		NbArticleCommande = nbArticleCommande;
	}

	public float getMontantbruthtcommande() {
		return MontantBrutHTCommande;
	}

	public void setMontantbruthtcommande(float MontantBrutHTCommande) {
		this.MontantBrutHTCommande = MontantBrutHTCommande;
	}

	public String getAdresse1Tiers() {
		return Adresse1Tiers;
	}

	public void setAdresse1Tiers(String adresse1Tiers) {
		Adresse1Tiers = adresse1Tiers;
	}

	public String getAdresse2Tiers() {
		return Adresse2Tiers;
	}

	public void setAdresse2Tiers(String adresse2Tiers) {
		Adresse2Tiers = adresse2Tiers;
	}

	public String getCodePostalTiers() {
		return CodePostalTiers;
	}

	public void setCodePostalTiers(String codePostalTiers) {
		CodePostalTiers = codePostalTiers;
	}

	public String getVilleTiers() {
		return VilleTiers;
	}

	public void setVilleTiers(String villeTiers) {
		VilleTiers = villeTiers;
	}

	public String getPaysTiers() {
		return PaysTiers;
	}

	public void setPaysTiers(String paysTiers) {
		PaysTiers = paysTiers;
	}

	public String getSaisonCommande() {
		return SaisonCommande;
	}

	public void setSaisonCommande(String saisonCommande) {
		SaisonCommande = saisonCommande;
	}

}