package de.doppelbemme.bewerbung.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.mysql.jdbc.PreparedStatement;

public class MySQLStats {

	//CREATE TABLE IF NOT EXISTS stats (UUID VARCHAR(100),Kills VARCHAR(100),Deaths VARCHAR(100),Played VARCHAR(100),Wins VARCHAR(100))
	
	public static boolean isUserExisting(UUID uuid) {	
		try {
			PreparedStatement ps = (PreparedStatement) MySQL.getConnection().prepareStatement("SELECT UUID FROM stats WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//
	
	public static void register(UUID uuid,int Kills,int Deaths,int Cakes,int Played,int Wins) {
		 
		try {
			PreparedStatement ps = (PreparedStatement) MySQL.getConnection().prepareStatement("INSERT INTO stats (UUID,Kills,Deaths,Cakes,Played,Wins) VALUES(?,?,?,?,?,?)"); 
			ps.setString(1, uuid.toString());
			ps.setInt(2, Kills);
			ps.setInt(3, Deaths);
			ps.setInt(4, Cakes);
			ps.setInt(5, Played);
			ps.setInt(6, Wins);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//
	
	public static void addKills(UUID uuid, int Kills) {
		try {
			PreparedStatement ps = (PreparedStatement) MySQL.getConnection().prepareStatement("UPDATE stats SET Kills = ? WHERE UUID = ?");
			ps.setInt(1, getKills(uuid) + Kills);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//

	public static void addDeaths(UUID uuid, int Deaths) {
		try {
			PreparedStatement ps = (PreparedStatement) MySQL.getConnection().prepareStatement("UPDATE stats SET Deaths = ? WHERE UUID = ?");
			ps.setInt(1, getDeaths(uuid) + Deaths);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	///
	
	public static void addCakes(UUID uuid, int Cakes) {
		try {
			PreparedStatement ps = (PreparedStatement) MySQL.getConnection().prepareStatement("UPDATE stats SET Cakes = ? WHERE UUID = ?");
			ps.setInt(1, getCakes(uuid) + Cakes);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	///
	
	public static void addGames(UUID uuid, int Games) {
		try {
			PreparedStatement ps = (PreparedStatement) MySQL.getConnection().prepareStatement("UPDATE stats SET Played = ? WHERE UUID = ?");
			ps.setInt(1, getGames(uuid) + Games);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	///
	
	public static void addWins(UUID uuid, int Wins) {
		try {
			PreparedStatement ps = (PreparedStatement) MySQL.getConnection().prepareStatement("UPDATE stats SET Wins = ? WHERE UUID = ?");
			ps.setInt(1, getKills(uuid) + Wins);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	///
	
	public static int getKills(UUID uuid) {
	try {
	PreparedStatement ps = (PreparedStatement) MySQL.getConnection().prepareStatement("SELECT Kills FROM stats WHERE UUID = ?");
	ps.setString(1, uuid.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return rs.getInt("Kills");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return -1;
	}
	
	///
	
	public static int getDeaths(UUID uuid) {
		try {
			PreparedStatement ps = (PreparedStatement) MySQL.getConnection().prepareStatement("SELECT Deaths FROM stats WHERE UUID = ?");
			ps.setString(1, uuid.toString());
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					return rs.getInt("Deaths");
				}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	///
	
	public static int getCakes(UUID uuid) {
		try {
			PreparedStatement ps = (PreparedStatement) MySQL.getConnection().prepareStatement("SELECT Cakes FROM stats WHERE UUID = ?");
			ps.setString(1, uuid.toString());
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					return rs.getInt("Cakes");
				}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	///
	
	public static int getGames(UUID uuid) {
		try {
		PreparedStatement ps = (PreparedStatement) MySQL.getConnection().prepareStatement("SELECT Played FROM stats WHERE UUID = ?");
		ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt("Played");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
		}
	
	///
	
	public static int getWins(UUID uuid) {
		try {
			PreparedStatement ps = (PreparedStatement) MySQL.getConnection().prepareStatement("SELECT Wins FROM stats WHERE UUID = ?");
			ps.setString(1, uuid.toString());
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					return rs.getInt("Wins");
				}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}