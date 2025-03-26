package fr.gclo.beans;

import java.util.Date;

public class CommandeEdition {
	
	private String 	NomSociete				= "";
	private String 	Adresse1Societe			= "";
	private String 	Adresse2Societe			= "";
	private String 	CodePostalSociete		= "";
	private String 	VilleSociete			= "";
	private String 	PaysSociete				= "";
	private String 	Telephone1Societe		= "";
	private String 	Telephone2Societe		= "";
	private String 	Telephone3Societe		= "";
	private String 	AdresseDeMessagerieSociete		= "";
	private String 	SiteSociete				= "";
	

	private String 	CodeCommande			= "";
	private String 	RaisonSocialeTiers		= "";
	private String 	Adresse1Tiers			= "";
	private String 	Adresse2Tiers			= "";
	private String 	CodePostalTiers			= "";
	private String 	VilleTiers				= "";
	private String 	PaysTiers				= "";
	private String 	SaisonCommande			= "";
	private Date 	DateCommande			= null;

	
	public CommandeEdition(String NomSociete_, String Adresse1Societe_, String Adresse2Societe_, String CodePostalSociete_, String VilleSociete_, 
						   String Telephone1Societe_, String Telephone2Societe_, String AdresseDeMessagerieSociete_, String SiteSociete_, 
						   String RaisonSocialeTiers_, String Adresse1Tiers_, String Adresse2Tiers_, String CodePostalTiers_, String VilleTiers_, String PaysTiers_,
						   String CodeCommande_, String SaisonCommande_, Date DateCommande_) {
		setNomSociete(NomSociete_);
		setAdresse1Societe(Adresse1Societe_);
		setAdresse2Societe(Adresse2Societe_);
		setCodePostalSociete(CodePostalSociete_);
		setVilleSociete(VilleSociete_);
		setTelephone1Societe(Telephone1Societe_);
		setTelephone2Societe(Telephone2Societe_);
		setAdresseDeMessagerieSociete(AdresseDeMessagerieSociete_);
		setSiteSociete(SiteSociete_);
		setRaisonSocialeTiers(RaisonSocialeTiers_);
		setAdresse1Tiers(Adresse1Tiers_);
		setAdresse2Tiers(Adresse2Tiers_);
		setCodePostalTiers(CodePostalTiers_);
		setVilleTiers(VilleTiers_);
		setPaysTiers(PaysTiers_);
		setCodeCommande(CodeCommande_);
		setSaisonCommande(SaisonCommande_);
		setDateCommande(DateCommande_);
	}

	public String getNomSociete() {
		return NomSociete;
	}

	public void setNomSociete(String nomSociete) {
		NomSociete = nomSociete;
	}	
	public String getAdresse1Societe() {
		return Adresse1Societe;
	}
	public void setAdresse1Societe(String adresse1Societe) {
		Adresse1Societe = adresse1Societe;
	}
	public String getAdresse2Societe() {
		return Adresse2Societe;
	}
	public void setAdresse2Societe(String adresse2Societe) {
		Adresse2Societe = adresse2Societe;
	}
	public String getCodePostalSociete() {
		return CodePostalSociete;
	}
	public void setCodePostalSociete(String codePostalSociete) {
		CodePostalSociete = codePostalSociete;
	}
	public String getVilleSociete() {
		return VilleSociete;
	}
	public void setVilleSociete(String villeSociete) {
		VilleSociete = villeSociete;
	}
	public String getPaysSociete() {
		return PaysSociete;
	}
	public void setPaysSociete(String paysSociete) {
		PaysSociete = paysSociete;
	}
	public String getTelephone1Societe() {
		return Telephone1Societe;
	}
	public void setTelephone1Societe(String telephone1Societe) {
		Telephone1Societe = telephone1Societe;
	}
	public String getTelephone2Societe() {
		return Telephone2Societe;
	}
	public void setTelephone2Societe(String telephone2Societe) {
		Telephone2Societe = telephone2Societe;
	}
	public String getTelephone3Societe() {
		return Telephone3Societe;
	}
	public void setTelephone3Societe(String telephone3Societe) {
		Telephone3Societe = telephone3Societe;
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

	public String getAdresseDeMessagerieSociete() {
		return AdresseDeMessagerieSociete;
	}

	public void setAdresseDeMessagerieSociete(String adresseDeMessagerieSociete) {
		AdresseDeMessagerieSociete = adresseDeMessagerieSociete;
	}

	public String getSiteSociete() {
		return SiteSociete;
	}

	public void setSiteSociete(String siteSociete) {
		SiteSociete = siteSociete;
	}

	public Date getDateCommande() {
		return DateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		DateCommande = dateCommande;
	}


}
