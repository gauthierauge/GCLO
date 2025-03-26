package fr.gclo.beans;

public class Livraison {

	private String 	CodeLivraison			= "";

	public Livraison(String CodeLivraison_ ) {
		this.setCodeLivraison(CodeLivraison_);
	}

	public String getCodeLivraison() {
		return CodeLivraison;
	}

	public void setCodeLivraison(String codeLivraison) {
		CodeLivraison = codeLivraison;
	}
}
