package knihy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class databaze {
	
	String novaDostupnost;
	
	databaze()  //implicitni konstruktor(bez parametru)
	{
		//if(SQL.getInstance().connect()) System.out.println("chyba");
		prvkyDatabaze=new HashMap<String,kniha>(/*SQL.getInstance().databasePull()*/);
		//SQL.getInstance().disconnect();
	}
		
	public boolean setKniha(String nazev, String autor, int rokVydani, String dostupnost, String typKnihy)
	{
		if (prvkyDatabaze.put(nazev,new kniha(nazev, autor, rokVydani, dostupnost, typKnihy))==null)
			return true;
		else
			return false;
	}
	public kniha getKniha(String nazev) 
	{
		return prvkyDatabaze.get(nazev);
	}
	public String upravKnihu(String nazev, String autor, int rokVydani, String dostupnost)
	{
		if (prvkyDatabaze.containsKey(nazev))
		{
			kniha upravenaKniha = prvkyDatabaze.get(nazev);
			upravenaKniha.setAutor(autor);
			upravenaKniha.setRokVydani(rokVydani);
			upravenaKniha.setDostupnost(dostupnost);
			return "Kniha " + nazev + " byla uspesne upravena.";
		}
		else
		return "Kniha " + nazev + " nebyla nalezena";
	}
	public String zmenaDostupnosti(String nazev, String dostupnost)
	{
		if (prvkyDatabaze.containsKey(nazev))
		{
			kniha upravenaKniha = prvkyDatabaze.get(nazev);
			upravenaKniha.setDostupnost(dostupnost);
			prvkyDatabaze.put(nazev,upravenaKniha);
		}
		return dostupnost;
	}
	public boolean vymazKnihu(String nazev)
	{
		if (prvkyDatabaze.remove(nazev)!=null)
			return true;
		return false;
	}
	public void vypisKnihAutora(String autor) 
	{
		for (kniha kniha : prvkyDatabaze.values()) 
		{
			if (kniha.getAutor().equals(autor))
			{
				System.out.println(kniha.getNazev());
			}
		} 
	}
	public void vypisDleZanru(String zanr) {
		for (kniha kniha : prvkyDatabaze.values()) {
			if (kniha instanceof roman && ((roman) kniha).getZanr().equalsIgnoreCase(zanr))
			{
	            System.out.println(((roman)kniha).getInfo());
			}
		}
		System.out.println("Vsechny knihy byly vypsany dle zanru");
	}
	public void vypisVypujcenychKnih(String dostupnost)
	{
		for (kniha kniha : prvkyDatabaze.values())
		{
			if (kniha.getDostupnost().equals("vypujcena"))
			{
				System.out.println(kniha.getNazev());
			}
		}
	}
	public void vypisDatabaze()
	{
		Set<String> seznamKnih = new TreeSet<>(prvkyDatabaze.keySet());

		for(String nazev : seznamKnih)
		{
			kniha kniha = prvkyDatabaze.get(nazev);  //trida promenna = mapa.get(klic) //do promenne se ulozi hodnota typu kniha, ktera je asociovana v mape prvkyDatabaze s klicem nazev
			System.out.print(kniha.getNazev()+", "+kniha.getAutor()+", "+kniha.getRokVydani()+", "+kniha.getDostupnost()+", "+kniha.getTypKnihy());
			if (kniha instanceof roman)
			{
				System.out.println(", " + ((roman)kniha).getZanr());
			}
			else if (kniha instanceof ucebnice)
			{
				System.out.println(", " + ((ucebnice)kniha).getRocnik());
			}
			else {
	            System.out.println(", neznamy zanr/rocnik knihy");
	        } 
			System.out.println();
		}
	}
	public boolean nactiDatabazi(String jmenoSouboru) 
	{
		FileReader fr = null;
		BufferedReader in = null;
		try {
			fr = new FileReader(jmenoSouboru);
			in = new BufferedReader(fr);
			String radek = in.readLine();
			String oddelovac = "[ ]+";
			String[] castiTextu = radek.split(oddelovac);
			if (castiTextu.length != 2 || !"pocet".equalsIgnoreCase(castiTextu[0]))
				return false;
			int pocetPrvku=Integer.parseInt(castiTextu[1]);
			prvkyDatabaze=new HashMap<>();
			for (int i=0;i<pocetPrvku;i++)
			{
				radek=in.readLine();
				castiTextu = radek.split(oddelovac);
				if (castiTextu.length==5)
				{
					String nazev = castiTextu[0];
					String autor = castiTextu[1];
					int rokVydani = Integer.parseInt(castiTextu[2]);
					String dostupnost = castiTextu[3];
					String typKnihy = castiTextu[4];
					
					kniha novaKniha = new kniha(nazev, autor, rokVydani, dostupnost, typKnihy);
					prvkyDatabaze.put(nazev, novaKniha);
				}
				else return false;
			}
		}
		catch (IOException e) {
			System.out.println("Soubor  nelze otevrit");
			return false;
		} 
		catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("Chyba integrity dat v souboru");
			return false;
		}
		finally
		{
			try
			{	if (in!=null)
				{
					in.close();
					fr.close();
				}
			}
			catch (IOException e) {
				System.out.println("Soubor  nelze zavrit");
				return false;
			} 
		}
		
		return true;
	}
	private Map<String,kniha> prvkyDatabaze;
	public boolean ulozDatabazi(String jmenoSouboru)
	{
		if (prvkyDatabaze.isEmpty()) {
			System.out.println("Databaze je prazdna");
			return false;
		}
		try {
			FileWriter fw = new FileWriter(jmenoSouboru);
			BufferedWriter out = new BufferedWriter(fw);
			out.write("Pocet " + prvkyDatabaze.size()); //zapis poctu polozek v databazi
			out.newLine();
			for (Map.Entry<String, kniha> entry : prvkyDatabaze.entrySet()){ //zapis kazde polozky v databazi
				kniha currentKniha = entry.getValue();
				out.write(currentKniha.getNazev() +" " + currentKniha.getAutor() +" " + currentKniha.getRokVydani() +" " + currentKniha.getDostupnost() +" " + currentKniha.getTypKnihy());
				out.newLine();
			}
			out.close();
			fw.close();
		}
		catch (IOException e) {
			System.out.println("Soubor nelze vytvorit");
			return false;
		}
		return true;
	}
}