package knihy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedMap;
import java.util.TreeMap;

public class SQL {
	
	private static final SQL INSTANCE = new SQL();
	private SQL() {}
	public static SQL getInstance() {
		return INSTANCE;
	}
	private int pocetPrvku=0;
	private static Connection conn;
	
	public boolean connect() {
		conn= null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:databaze.db");
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
		}
	public void disconnect() {
		if (conn != null) {
			try {
				conn.close(); }
			catch (SQLException ex) {
				System.out.println(ex.getMessage()); }
		}
	}
	public boolean vytvorTabulku() {
		 if (conn==null)
			 return false;
		    String sqlKnihy = "CREATE TABLE IF NOT EXISTS knihy ("
		    	+ "id integer PRIMARY KEY," 
		    	+ "nazev varchar NOT NULL,"
		    	+ "autor varchar NOT NULL,"
		    	+ "rokVydani integer NOT NULL," 
		    	+ "dostupnost bit NOT NULL,"
		    	+ "typKnihy integer,"
		    	+ ");";
		    try{
		        Statement stmt = conn.createStatement(); 
		        stmt.executeUpdate(sqlKnihy);
		        stmt.close();
		        return true;
		    } 
		    catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
		    return false;
	}
	public boolean odstranTabulku() {
		if (conn==null)
			return false;
		String sqlKnihy = "DROP TABLE knihy";
		try{
	        Statement stmt = conn.createStatement(); 
	        stmt.execute(sqlKnihy);
	        return true;
	    } 
	    catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    }
	    return false;
	}
	public boolean vlozKnihu(kniha kniha) {
		if (conn==null)
			return false;
	    try {
	    	pocetPrvku++;
	    	String sql = "INSERT INTO knihy(id,nazev,rokVydani,dostupnost,typKnihy) VALUES(?,?,?,?,?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql); 
	        pstmt.setInt(1, pocetPrvku);
	        pstmt.setString(2, kniha.getNazev());
	        pstmt.setString(3, kniha.getAutor());
	        pstmt.setInt(4, kniha.getRokVydani());
	        pstmt.setString(5, kniha.getDostupnost());
	        pstmt.executeUpdate();
	        pstmt.close();
	        return true;
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    return false;
	}
	public static SortedMap<String,kniha> nacteniKnihZDatabaze() {
		SortedMap<String,kniha> kniha = new TreeMap<>();
		if (conn==null) return kniha;
	    try {
	    	String sqlKnihy = "SELECT * FROM knihy";
	    	Statement stmt  = conn.createStatement();
	        ResultSet rs  = stmt.executeQuery(sqlKnihy);
	        while (rs.next())
				{
	        	String nazev = rs.getString("nazev");
	        	String autor = rs.getString("autor");
	        	int rokVydani = rs.getInt("roKVydani");
	        	String dostupnost = rs.getString("dostupnost");
	        	String typKnihy = rs.getString("typKnihy");
								
	        	kniha knizka = new kniha(rs.getString("nazev"),rs.getString("autor"),rs.getInt("rokVydani"),rs.getString("dostupnost"),rs.getString("typKnihy"));
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
}