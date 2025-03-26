package fr.gclo.beans;

public class TiersAutreAdresse {

	private int    CompteurTiersAutreAdresse			= 0;
	private String TypeAdresseTiersAutreAdresse = "";
	private String CodeTiers = "";
	private String RaisonSocialeTiersAutreAdresse = "";
	private String Adresse1TiersAutreAdresse = "";
	private String Adresse2TiersAutreAdresse = "";
	private String CodePostalTiersAutreAdresse = "";
	private String VilleTiersAutreAdresse = "";
	private String PaysTiersAutreAdresse = "";

	public TiersAutreAdresse(int CompteurTiersAutreAdresse_, String TypeAdresseTiersAutreAdresse_, String CodeTiers_, String RaisonSocialeTiersAutreAdresse_, 
							 String Adresse1TiersAutreAdresse_, String Adresse2TiersAutreAdresse_, String CodePostalTiersAutreAdresse_,  String VilleTiersAutreAdresse_,
							 String PaysTiersAutreAdresse_) {
		this.setCompteurTiersAutreAdresse(CompteurTiersAutreAdresse_);
		this.setTypeAdresseTiersAutreAdresse(TypeAdresseTiersAutreAdresse_);
		this.setCodeTiers(CodeTiers_);
		this.setRaisonSocialeTiersAutreAdresse(RaisonSocialeTiersAutreAdresse_);
		this.setAdresse1TiersAutreAdresse(Adresse1TiersAutreAdresse_);
		this.setAdresse2TiersAutreAdresse(Adresse2TiersAutreAdresse_);
		this.setCodePostalTiersAutreAdresse(CodePostalTiersAutreAdresse_);
		this.setVilleTiersAutreAdresse(VilleTiersAutreAdresse_);
		this.setPaysTiersAutreAdresse(PaysTiersAutreAdresse_);
	}

	public int getCompteurTiersAutreAdresse() {
		return CompteurTiersAutreAdresse;
	}

	public void setCompteurTiersAutreAdresse(int compteurTiersAutreAdresse) {
		CompteurTiersAutreAdresse = compteurTiersAutreAdresse;
	}

	public String getTypeAdresseTiersAutreAdresse() {
		return TypeAdresseTiersAutreAdresse;
	}

	public void setTypeAdresseTiersAutreAdresse(String typeAdresseTiersAutreAdresse) {
		TypeAdresseTiersAutreAdresse = typeAdresseTiersAutreAdresse;
	}

	public String getCodeTiers() {
		return CodeTiers;
	}

	public void setCodeTiers(String codeTiers) {
		CodeTiers = codeTiers;
	}

	public String getRaisonSocialeTiersAutreAdresse() {
		return RaisonSocialeTiersAutreAdresse;
	}

	public void setRaisonSocialeTiersAutreAdresse(String raisonSocialeTiersAutreAdresse) {
		RaisonSocialeTiersAutreAdresse = raisonSocialeTiersAutreAdresse;
	}

	public String getAdresse1TiersAutreAdresse() {
		return Adresse1TiersAutreAdresse;
	}

	public void setAdresse1TiersAutreAdresse(String adresse1TiersAutreAdresse) {
		Adresse1TiersAutreAdresse = adresse1TiersAutreAdresse;
	}

	public String getAdresse2TiersAutreAdresse() {
		return Adresse2TiersAutreAdresse;
	}

	public void setAdresse2TiersAutreAdresse(String adresse2TiersAutreAdresse) {
		Adresse2TiersAutreAdresse = adresse2TiersAutreAdresse;
	}

	public String getCodePostalTiersAutreAdresse() {
		return CodePostalTiersAutreAdresse;
	}

	public void setCodePostalTiersAutreAdresse(String codePostalTiersAutreAdresse) {
		CodePostalTiersAutreAdresse = codePostalTiersAutreAdresse;
	}

	public String getVilleTiersAutreAdresse() {
		return VilleTiersAutreAdresse;
	}

	public void setVilleTiersAutreAdresse(String villeTiersAutreAdresse) {
		VilleTiersAutreAdresse = villeTiersAutreAdresse;
	}

	public String getPaysTiersAutreAdresse() {
		return PaysTiersAutreAdresse;
	}

	public void setPaysTiersAutreAdresse(String paysTiersAutreAdresse) {
		PaysTiersAutreAdresse = paysTiersAutreAdresse;
	}
}
