package fr.gclo.beans;

public class Matrice {
	
	private String 	numero = "";
	private String 	modelearticle = "";
	private String 	colorisarticle = "";
	private int nbarticletotal = 0;
	private int nbarticletailleunique = 0;
	private int nbarticletailles = 0;
	private int nbarticletaillem = 0;
	private int nbarticletaillel = 0;
	private int nbarticletaillexl = 0;
	private int nbarticletaillexxl = 0;
		
	public Matrice(String numero_, String modelearticle_, String colorisarticle_, int nbarticletotal_, int nbarticletailleunique_, int nbarticletailles_,
					int nbarticletaillem_, int nbarticletaillel_, int nbarticletaillexl_, int nbarticletaillexxl_) {
        this.numero = numero_;
        this.modelearticle = modelearticle_;
        this.colorisarticle = colorisarticle_;
        this.nbarticletotal = nbarticletotal_;
        this.nbarticletailleunique = nbarticletailleunique_;
        this.nbarticletailles = nbarticletailles_;
        this.setNbarticletaillem(nbarticletaillem_);
        this.setNbarticletaillel(nbarticletaillel_);
        this.setNbarticletaillexl(nbarticletaillexxl_);
        this.setNbarticletaillexxl(nbarticletaillexxl_);
    }

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getModelearticle() {
		return modelearticle;
	}

	public void setModelearticle(String modelearticle) {
		this.modelearticle = modelearticle;
	}

	public String getColorisarticle() {
		return colorisarticle;
	}

	public void setColorisarticle(String colorisarticle) {
		this.colorisarticle = colorisarticle;
	}

	public int getNbarticletotal() {
		return nbarticletotal;
	}

	public void setNbarticletotal(int nbarticletotal) {
		this.nbarticletotal = nbarticletotal;
	}

	public int getNbarticletailleunique() {
		return nbarticletailleunique;
	}

	public void setNbarticletailleunique(int nbarticletailleunique) {
		this.nbarticletailleunique = nbarticletailleunique;
	}

	public int getNbarticletailles() {
		return nbarticletailles;
	}

	public void setNbarticletailles(int nbarticletailles) {
		this.nbarticletailles = nbarticletailles;
	}

	public int getNbarticletaillem() {
		return nbarticletaillem;
	}

	public void setNbarticletaillem(int nbarticletaillem) {
		this.nbarticletaillem = nbarticletaillem;
	}

	public int getNbarticletaillel() {
		return nbarticletaillel;
	}

	public void setNbarticletaillel(int nbarticletaillel) {
		this.nbarticletaillel = nbarticletaillel;
	}

	public int getNbarticletaillexl() {
		return nbarticletaillexl;
	}

	public void setNbarticletaillexl(int nbarticletaillexl) {
		this.nbarticletaillexl = nbarticletaillexl;
	}

	public int getNbarticletaillexxl() {
		return nbarticletaillexxl;
	}

	public void setNbarticletaillexxl(int nbarticletaillexxl) {
		this.nbarticletaillexxl = nbarticletaillexxl;
	}

}
