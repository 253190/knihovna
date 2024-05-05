package knihy;

public class kniha {
	
	public String nazev;
	public String autor;
	public int rokVydani;
	public String dostupnost;
	public String typKnihy;
	
	//konstruktor
	public kniha(String nazev, String autor, int rokVydani, String dostupnost, String typKnihy) {
		this.nazev = nazev;
		this.autor = autor;
		this.rokVydani = rokVydani;
		this.dostupnost = dostupnost;
		this.typKnihy = typKnihy;
					
	}
	
	//gettery a settery
	public String getNazev() {
		return nazev;
	}
	public void setNazev(String nazev) {
		this.nazev = nazev;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public int getRokVydani() {
		return rokVydani;
	}
	public void setRokVydani(int rokVydani) {
		this.rokVydani = rokVydani;
	}
	public String getDostupnost() {
		return dostupnost;
	}
	public void setDostupnost(String dostupnost) {
		this.dostupnost = dostupnost;
	}
	public String getTypKnihy() {
		return typKnihy;
	}
	public void setTypKnihy(String typKnihy) {
		this.typKnihy = typKnihy;
	}
	
	
	public String getInfo() {
		return "Nazev: " + getNazev() + "\t" + "Autor: " + getAutor() +
		"\t" + "Rok vydani: " + getRokVydani() + "\t" + "Dostupnost: " + getDostupnost() +
		"\t" + "TypKnihy: " + getTypKnihy()+ "\t";
	}
	
	
}

