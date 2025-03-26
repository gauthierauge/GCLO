package fr.gclo.beans;

public class Tiers {
	
	private String 	TypeTiers			= "";
	private String 	CodeTiers			= "";
	private String 	TitreTiers			= "";
	private String 	RaisonSocialeTiers		= "";
	private String 	Adresse1Tiers		= "";
	private String 	Adresse2Tiers		= "";
	private String 	CodePostalTiers		= "";
	private String 	VilleTiers			= "";
	private String 	PaysTiers			= "";
	private String 	Telephone1Tiers		= "";
	private String 	Telephone2Tiers		= "";
	private String 	Telephone3Tiers		= "";
	private String 	InterlocuteurTiers	= "";
	
	public Tiers(String TypeTiers_, String CodeTiers_, String TitreTiers_,  String RaisonSocialeTiers_, String Adresse1Tiers_,  
				 String Adresse2Tiers_,  String CodePostalTiers_, String VilleTiers_, String PaysTiers_, String Telephone1Tiers_, 
				 String Telephone2Tiers_, String Telephone3Tiers_, String InterlocuteurTiers_ ) {
		this.setTypeTiers(TypeTiers_);
		this.setCodeTiers(CodeTiers_);
		this.setTitreTiers(TitreTiers_);
		this.setRaisonSocialeTiers(RaisonSocialeTiers_);
		this.setAdresse1Tiers(Adresse1Tiers_);
		this.setAdresse2Tiers(Adresse2Tiers_);
		this.setCodePostalTiers(CodePostalTiers_);
		this.setVilleTiers(VilleTiers_);
		this.setPaysTiers(PaysTiers_);
		this.setTelephone1Tiers(Telephone1Tiers_);
		this.setTelephone2Tiers(Telephone2Tiers_);
		this.setTelephone3Tiers(Telephone3Tiers_);
		this.setInterlocuteurTiers(InterlocuteurTiers_);
	}

	public String getTypeTiers() {
		return TypeTiers;
	}

	public void setTypeTiers(String typeTiers) {
		TypeTiers = typeTiers;
	}

	public String getCodeTiers() {
		return CodeTiers;
	}

	public void setCodeTiers(String codeTiers) {
		CodeTiers = codeTiers;
	}

	public String getTitreTiers() {
		return TitreTiers;
	}

	public void setTitreTiers(String titreTiers) {
		TitreTiers = titreTiers;
	}

	public String getRaisonSocialeTiers() {
		return RaisonSocialeTiers;
	}

	public void setRaisonSocialeTiers(String raisonSocialeTiers) {
		RaisonSocialeTiers = raisonSocialeTiers;
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

	public String getTelephone1Tiers() {
		return Telephone1Tiers;
	}

	public void setTelephone1Tiers(String telephone1Tiers) {
		Telephone1Tiers = telephone1Tiers;
	}

	public String getTelephone2Tiers() {
		return Telephone2Tiers;
	}

	public void setTelephone2Tiers(String telephone2Tiers) {
		Telephone2Tiers = telephone2Tiers;
	}

	public String getTelephone3Tiers() {
		return Telephone3Tiers;
	}

	public void setTelephone3Tiers(String telephone3Tiers) {
		Telephone3Tiers = telephone3Tiers;
	}

	public String getInterlocuteurTiers() {
		return InterlocuteurTiers;
	}

	public void setInterlocuteurTiers(String interlocuteurTiers) {
		InterlocuteurTiers = interlocuteurTiers;
	}
}
