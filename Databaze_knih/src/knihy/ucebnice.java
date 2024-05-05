package knihy;
public class ucebnice extends kniha{
	
	public int rocnik;
	
	//konstruktor
	public ucebnice(String nazev, String autor, int rokVydani, String dostupnost, String typKnihy, int rocnik) {
		super(nazev, autor, rokVydani, dostupnost, typKnihy);
		this.rocnik = rocnik;
		
	}
	 //gettery a settery pro rocnik
	public int getRocnik() {
		return rocnik;
	}
	public void setRocnik(int rocnik) {
		this.rocnik = rocnik;
	}
	@Override
	public String getInfo() {
		return "Nazev: " + getNazev() + "\t" + "Autor: " + getAutor() +
		"\t" + "Rok vydani: " + getRokVydani() + "\t" + "Dostupnost: " + getDostupnost() +
		"\t" + "TypKnihy: " + getTypKnihy()+ "\t"+  "Rocnik: " + rocnik;
	}
}
