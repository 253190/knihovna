package knihy;
import java.util.Scanner;

public class test {
	
	public static int pouzeCelaCisla(Scanner sc) 
	{
		int cislo = 0;
		try
		{
			cislo = sc.nextInt();
		}
		catch(Exception e)
		{
			System.out.println("Nastala vyjimka typu "+e.toString());
			System.out.println("Zadejte prosim cele cislo ");
			sc.nextLine();
			cislo = pouzeCelaCisla(sc);
		}
		return cislo;
	}
	
	public static void main(String[] args) {		
		Scanner sc=new Scanner(System.in);
		databaze mojeDatabaze = new databaze();
		int volba;
		boolean run=true;
		String nazev;
		String autor;
		int rokVydani;
		String dostupnost;
		String typKnihy;
		int rocnik;
		String zanr;
		String novyAutor;
		int novyRokVydani;
		String vysledek;
		
		while(run)
		{
			System.out.println("Vyberte pozadovanou cinnost:");
			System.out.println("1 .. pridani nove knihy");
			System.out.println("2 .. uprava knihy");
			System.out.println("3 .. smazani knihy");
			System.out.println("4 .. oznaceni vypujcena/vracena");
			System.out.println("5 .. vypis knih");
			System.out.println("6 .. vyhledani knihy");
			System.out.println("7 .. vypis vsech knih autora");
			System.out.println("8 .. vypis vsech knih zanru");
			System.out.println("9 .. vypujcene knihy");
			System.out.println("10 .. ulozeni info o knize do souboru");
			System.out.println("11 .. nacteni ze souboru");
			System.out.println("12 .. ukonceni aplikace");
			
			volba = pouzeCelaCisla(sc);
			switch(volba)
			{
				case 1:
					System.out.println("Zadejte nazev knihy:");
					nazev = sc.next();
					System.out.println("Zadejte autora:");
					autor = sc.next();
					System.out.println("Zadejte rok vydani knihy:");
					rokVydani = test.pouzeCelaCisla(sc);
					System.out.println("Zadejte dostupnost: dostupna/vypujcena");
					dostupnost = sc.next();
					System.out.println("Zadejte typ knihy: ucebnice/roman");
					typKnihy = sc.next();
					if (typKnihy.equals("roman")) {
						System.out.println("Zadejte zanr:");
						zanr = sc.next();
					}
					else if (typKnihy.equals("ucebnice")) {
						System.out.println("Zadejte rocnik:");
						rocnik = test.pouzeCelaCisla(sc);
					}
					else
						System.out.println("Neplatny typ knihy");
					if (!mojeDatabaze.setKniha(nazev, autor, rokVydani, dostupnost, typKnihy))
						System.out.println("Kniha v databazi jiz existuje");
					break;
				case 2:
					System.out.println("Zadejte nazev knihy k uprave:");
					nazev=sc.next();
					if (mojeDatabaze.getKniha(nazev) != null) {
						System.out.println("Zadejte noveho autora:");
						novyAutor=sc.next();
						System.out.println("Zadejte novy rok vydani:");
						novyRokVydani=pouzeCelaCisla(sc);
						System.out.println("Zadejte novou dostupnost:");
						dostupnost=sc.next();
						vysledek = mojeDatabaze.upravKnihu(nazev, novyAutor, novyRokVydani, dostupnost);
						System.out.println(vysledek);
					}
					break;
				case 3:
					System.out.println("Zadejte nazev knihy k odstraneni:");
					nazev=sc.next();
					if (mojeDatabaze.vymazKnihu(nazev))
						System.out.println(nazev + " odstranena ");
					else
						System.out.println(nazev + " neni v databazi ");
					break;
				case 4:
					System.out.println("Zadejte nazev knihy, u ktere chcete zmenit dostupnost:");
					nazev=sc.next();
					if (mojeDatabaze.getKniha(nazev) != null)
					{
						System.out.println("Zadejte novou dostupnost knihy: dostupna/vypujcena");
						dostupnost=sc.next();
						mojeDatabaze.zmenaDostupnosti(nazev, dostupnost);
						System.out.println("Dostupnost knihy byla zmenena");
					}
					else {
						System.out.println("Kniha s timto nazvem nebyla nalezena");
					}
					break;
				case 5:
					System.out.println("Knihy ulozene v databazi jsou:");
					mojeDatabaze.vypisDatabaze();
					break;
				case 6:
					System.out.println("Zadejte nazev knihy:");
					nazev=sc.next();
					kniha info = null;
					try 
					{	
						info=mojeDatabaze.getKniha(nazev);
						System.out.println("Nazev: " + info.getNazev() + " ,autor: " + info.getAutor() + " ,rok vydani: " + info.getRokVydani() + " ,dostupnost: " + info.getDostupnost() + " ,typ knihy: " + info.getTypKnihy());
					}
					catch (NullPointerException e)
					{
						System.out.println("Vybrana polozka neexistuje");
					}
					break;
				case 7:
					System.out.println("Zadejte autora:");
					autor=sc.next();
					System.out.println("Knihy autora:");
					mojeDatabaze.vypisKnihAutora(autor);
					break;
				case 8:
					System.out.println("Zadejte zanr:");
					zanr=sc.next();
					System.out.println("Knihy daneho zanru:");
					mojeDatabaze.vypisDleZanru(zanr);
					break;
				case 9:
					System.out.println("Vypujcene knihy:");
					mojeDatabaze.vypisVypujcenychKnih("vypujcena");
					break;
				case 10:
					System.out.println("Zadejte jmeno souboru:");
					if (mojeDatabaze.ulozDatabazi(sc.next()))
						System.out.println("Databaze ulozena");
					else
						System.out.println("Databazi nebylo mozno ulozit");
					break;
				case 11:
					System.out.println("Zadejte jmeno souboru:");
					if (mojeDatabaze.nactiDatabazi(sc.next()))
						System.out.println("Databaze nactena");
					else
						System.out.println("Databazi nebylo mozno nacist");
					break;
				case 12:
					mojeDatabaze.ulozDatabazi();
					run = false;
					break;
			}
		}
	}
}
