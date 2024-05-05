package knihy;
public class roman extends kniha {
	
	public String zanr;

	public roman(String nazev, String autor, int rokVydani, String dostupnost, String typKnihy, String zanr) {
		super(nazev, autor, rokVydani, dostupnost, typKnihy);
		this.zanr = zanr;
	}
	public String getZanr() {
		return zanr;
	}
	public void setZanr(String zanr) {
		this.zanr = zanr;
	}
	@Override
	public String getInfo() {
		return "Nazev: " + getNazev() + "\t" + "Autor: " + getAutor() +
		"\t" + "Rok vydani: " + getRokVydani() + "\t" + "Dostupnost: " + getDostupnost() +
		"\t" + "TypKnihy: " + getTypKnihy()+ "\t" + "Zanr: " + zanr;
	}
}
