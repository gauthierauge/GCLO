package fr.gclo.beans;

import java.util.Date;

public class CommandeModaliteDeReglement {
	
	private int CompteurCommandeModaliteDeReglement = 0;
	private String 	CodeCommande			= "";
	private String 	ModeDeReglement			= "";
	private int NbJoursDeDecalage = 0;
	private double Pourcentage = 0.;
	private double Montant = 0.;
	private Date DateEcheance = null;  
	
	public int getCompteurCommandeModaliteDeReglement() {
		return CompteurCommandeModaliteDeReglement;
	}
	
	public void setCompteurCommandeModaliteDeReglement(int compteurCommandeModaliteDeReglement) {
		CompteurCommandeModaliteDeReglement = compteurCommandeModaliteDeReglement;
	}
	
	public String getCodeCommande() {
		return CodeCommande;
	}
	
	public void setCodeCommande(String codeCommande) {
		CodeCommande = codeCommande;
	}
	
	public String getModeDeReglement() {
		return ModeDeReglement;
	}
	
	public void setModeDeReglement(String modeDeReglement) {
		ModeDeReglement = modeDeReglement;
	}
	
	public int getNbJoursDeDecalage() {
		return NbJoursDeDecalage;
	}
	
	public void setNbJoursDeDecalage(int nbJoursDeDecalage) {
		NbJoursDeDecalage = nbJoursDeDecalage;
	}
	
	public double getPourcentage() {
		return Pourcentage;
	}
	
	public void setPourcentage(double pourcentage) {
		Pourcentage = pourcentage;
	}
	
	public double getMontant() {
		return Montant;
	}
	
	public void setMontant(double montant) {
		Montant = montant;
	}
	
	public Date getDateEcheance() {
		return DateEcheance;
	}
	
	public void setDateEcheance(Date dateEcheance) {
		DateEcheance = dateEcheance;
	}
}
