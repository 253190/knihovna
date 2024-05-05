package knihy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedMap;
import java.util.TreeMap;

public class SQL {
	static Connection conn;
	public static SortedMap<String,kniha> nacteniKnihZDatabaze(Connection connection){
		SortedMap<String,kniha> kniha = new TreeMap<>();
		String sql = "SELECT id, nazev, autor, rokVydani, dostupnost, typKnihy, zanr, rocnik FROM mojedatabaze";
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				String nazev = rs.getString("nazev");
				String autor = rs.getString("autor");
				int rokVydani = rs.getInt("roKVydani");
				String dostupnost = rs.getString("dostupnost");
				String typKnihy = rs.getString("typKnihy");
				String zanr = rs.getString("zanr");
				int rocnik = rs.getInt("rocnik");
				
				if (typKnihy == "roman") {
					roman roman = new roman(nazev, autor, rokVydani, dostupnost, typKnihy, zanr);
					kniha.put(nazev,roman);
				}
				else {
					ucebnice ucebnice = new ucebnice(nazev, autor, rokVydani, dostupnost, typKnihy, rocnik);
					kniha.put(nazev,ucebnice);
				}
			}
		}
		catch(SQLException e){
			System.out.println("Chyba pri pristupu k databazi: " +e.getMessage());
		}
		catch(NullPointerException e){
			System.out.println("databaze neni pripojena");
		}
		return kniha;
	}
	public static boolean ulozeniKnihDoDatabaze(TreeMap<String,kniha> kniha, Connection mojeDatabaze) {
		String zanr;
		int rocnik;
		String sqlDelete = "DELETE FROM databaze";
		String sqlInsert = "INSERT INTO databaze(nazev, autor, rokVydani, dostupnost, typKnihy, zanr, rocnik) VALUES(?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement pstmtd = conn.prepareStatement(sqlDelete);
			PreparedStatement pstmti = conn.prepareStatement(sqlInsert);
			
			pstmtd.executeUpdate();
			
			for (kniha knizka : kniha.values()) {
				if (knizka.getTypKnihy().equals("roman"))
					zanr = (((roman)knizka)).getZanr();
				else
					rocnik = (((ucebnice)knizka)).getRocnik();
				
				pstmti. setString(1, knizka.getNazev());
				pstmti. setString(2, knizka.getAutor());
				pstmti. setInt(3, knizka.getRokVydani());
				pstmti. setString(4, knizka.getDostupnost());
				pstmti. setString(5, knizka.getTypKnihy());
				pstmti. setString(6, ((roman)knizka).getZanr());
				pstmti. setInt(7, ((ucebnice)knizka).getRocnik());
				pstmti.executeUpdate();
			}
			return true;
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println("chyba pri ukladani do databaze");
			return false;
		}
		catch(NullPointerException e){
			e.printStackTrace();
			System.out.println("databaze neni pripojena");
			return false;
		}
	
	}

}
