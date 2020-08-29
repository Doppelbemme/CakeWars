package de.doppelbemme.bewerbung.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import de.doppelbemme.bewerbung.main.Bewerbung;

public class MySQL {

	static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	
	public static String host;
	public static String port;
	public static String database;
	public static String username;
	public static String password;
	public static Connection con;
	
	
	public static void connect() {
		if(!isConnected()) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" +  database + "?autoReconnect=true", username, password);
				console.sendMessage(Bewerbung.main.prefix + "§aDie §bMySQL-Verbindung §awurde erfolgreich aufgebaut!");
			} catch (SQLException e) {
				console.sendMessage(Bewerbung.main.prefix + "§cDie §bMySQL-Verbindung §ckonnte nicht aufgebaut werden!");
				e.printStackTrace();
			}
		}
	}
	
	public static void disconnect() {
		if(isConnected()) {
			try {
				con.close();
				console.sendMessage(Bewerbung.main.prefix + "§aDie §bMySQL-Verbindung §awurde erfolgreich getrennt!");
			} catch (SQLException e) {
				console.sendMessage(Bewerbung.main.prefix + "§cDie §bMySQL-Verbindung §ckonnte nicht getrennt werden!");
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isConnected() {
		return (con == null ? false : true);
	}
	
	public static Connection getConnection() {
		return con;
	}

}