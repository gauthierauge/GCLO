package fr.gclo.beans;

public class Facture {

	private String 	CodeFacture			= "";

	public Facture(String CodeFacture_ ) {
		this.setCodeFacture(CodeFacture_);
	}

	public String getCodeFacture() {
		return CodeFacture;
	}

	public void setCodeFacture(String codeFacture) {
		CodeFacture = codeFacture;
	}

}
