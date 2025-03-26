package fr.gclo.beans;

import java.util.Date;

public class ReglementClient {

	private int CompteurReglementClient = 0;
	private String CodeTiers = "";
	private String CodeCommande = "";
	private String TypeDeReglementClient = "";
	private Date DateReglementClient;
	private String LibelleReglementClient = "";
	private String Saison = "";

	public ReglementClient(int CompteurReglementClient_, Date DateReglementClient_, String LibelleReglementClient_, String Saison_ ) {
		this.setCompteurReglementClient(CompteurReglementClient_);
		this.setDateReglementClient(DateReglementClient_);
		this.setLibelleReglementClient(LibelleReglementClient_);
		this.setSaison(Saison_);
	}

	public int getCompteurReglementClient() {
		return CompteurReglementClient;
	}

	public void setCompteurReglementClient(int compteurReglementClient) {
		CompteurReglementClient = compteurReglementClient;
	}

	public String getCodeTiers() {
		return CodeTiers;
	}

	public void setCodeTiers(String codeTiers) {
		CodeTiers = codeTiers;
	}

	public String getCodeCommande() {
		return CodeCommande;
	}

	public void setCodeCommande(String codeCommande) {
		CodeCommande = codeCommande;
	}

	public String getTypeDeReglementClient() {
		return TypeDeReglementClient;
	}

	public void setTypeDeReglementClient(String typeDeReglementClient) {
		TypeDeReglementClient = typeDeReglementClient;
	}

	public Date getDateReglementClient() {
		return DateReglementClient;
	}

	public void setDateReglementClient(Date dateReglementClient) {
		DateReglementClient = dateReglementClient;
	}

	public String getLibelleReglementClient() {
		return LibelleReglementClient;
	}

	public void setLibelleReglementClient(String libelleReglementClient) {
		LibelleReglementClient = libelleReglementClient;
	}

	public String getSaison() {
		return Saison;
	}

	public void setSaison(String saison) {
		Saison = saison;
	}
}
