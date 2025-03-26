package fr.gclo.beans;

public class Societe {

	private int compteursociete = 0;
	private String titresociete = "";
	private String nomsociete = "";
	private String adresse1societe = "";
	private String adresse2societe = "";
	private String codepostalsociete = "";
	private String villesociete = "";
	private String payssociete = "";
	private String telephonesociete = "";
	private String adressedemessageriesociete = "";
	private String sitesociete = "";
	
	
	public Societe(int compteursociete_, String titresociete_, String nomsociete_, String adresse1societe_, String adresse2societe_, String codepostalsociete_, String villesociete_, String payssociete_, String telephonesociete_, String adressedemessageriesociete_, String sitesociete_) {
		this.setCompteursociete(compteursociete_);
		this.setTitresociete(titresociete_);
		this.setNomsociete(nomsociete_);
		this.setAdresse1societe(adresse1societe_);
		this.setAdresse2societe(adresse2societe_);
		this.setCodepostalsociete(codepostalsociete_);
		this.setVillesociete(villesociete_);
		this.setTelephonesociete(telephonesociete_);
		this.setAdressedemessageriesociete(adressedemessageriesociete_);
		this.setSitesociete(sitesociete_);
	}

	public int getCompteursociete() {
		return compteursociete;
	}
	
	public void setCompteursociete(int compteursociete) {
		this.compteursociete = compteursociete;
	}
	
	public String getTitresociete() {
		return titresociete;
	}
	
	public void setTitresociete(String titresociete) {
		this.titresociete = titresociete;
	}
	
	public String getNomsociete() {
		return nomsociete;
	}
	
	public void setNomsociete(String nomsociete) {
		this.nomsociete = nomsociete;
	}
	
	public String getAdresse1societe() {
		return adresse1societe;
	}
	
	public void setAdresse1societe(String adresse1societe) {
		this.adresse1societe = adresse1societe;
	}
	
	public String getAdresse2societe() {
		return adresse2societe;
	}
	
	public void setAdresse2societe(String adresse2societe) {
		this.adresse2societe = adresse2societe;
	}
	
	public String getCodepostalsociete() {
		return codepostalsociete;
	}
	
	public void setCodepostalsociete(String codepostalsociete) {
		this.codepostalsociete = codepostalsociete;
	}

	public String getVillesociete() {
		return villesociete;
	}

	public void setVillesociete(String villesociete) {
		this.villesociete = villesociete;
	}

	public String getPayssociete() {
		return payssociete;
	}

	public void setPayssociete(String payssociete) {
		this.payssociete = payssociete;
	}

	public String getTelephonesociete() {
		return telephonesociete;
	}

	public void setTelephonesociete(String telephonesociete) {
		this.telephonesociete = telephonesociete;
	}

	public String getAdressedemessageriesociete() {
		return adressedemessageriesociete;
	}

	public void setAdressedemessageriesociete(String adressedemessageriesociete) {
		this.adressedemessageriesociete = adressedemessageriesociete;
	}

	public String getSitesociete() {
		return sitesociete;
	}

	public void setSitesociete(String sitesociete) {
		this.sitesociete = sitesociete;
	}
}
