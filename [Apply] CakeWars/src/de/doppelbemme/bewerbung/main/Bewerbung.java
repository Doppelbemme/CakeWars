package de.doppelbemme.bewerbung.main;

import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.mysql.jdbc.PreparedStatement;

import de.doppelbemme.bewerbung.command.Build_Command;
import de.doppelbemme.bewerbung.command.ForceMap_Command;
import de.doppelbemme.bewerbung.command.SetCake_Command;
import de.doppelbemme.bewerbung.command.SetLobby_Command;
import de.doppelbemme.bewerbung.command.SetSpawner_Command;
import de.doppelbemme.bewerbung.command.SetTeamSpawn_Command;
import de.doppelbemme.bewerbung.command.Setup_Command;
import de.doppelbemme.bewerbung.command.Start_Command;
import de.doppelbemme.bewerbung.command.Stats_Command;
import de.doppelbemme.bewerbung.listener.PlayerDamageListener;
import de.doppelbemme.bewerbung.listener.PlayerInteractWithPlayerListener;
import de.doppelbemme.bewerbung.listener.PlayerItemInteractListener;
import de.doppelbemme.bewerbung.listener.PlayerMapInteractListener;
import de.doppelbemme.bewerbung.listener.PlayerOnlineListener;
import de.doppelbemme.bewerbung.listener.WorldWeatherListener;
import de.doppelbemme.bewerbung.mysql.MySQL;
import de.doppelbemme.bewerbung.mysql.MySQLFile;
import de.doppelbemme.bewerbung.utils.Utils;

public class Bewerbung extends JavaPlugin {

	public static Bewerbung main;
	
	public static ArrayList<Player> BuilderList = new ArrayList<Player>();
	
	public ArrayList<Player> TeamBlue = new ArrayList<Player>();
	public ArrayList<Player> TeamRed = new ArrayList<Player>();
	
	public ArrayList<String> loreTeamBlue = new ArrayList<String>();
	public ArrayList<String> loreTeamRed = new ArrayList<String>();
	
	public ArrayList<Player> thiefCakeTeamBlue = new ArrayList<Player>();
	public ArrayList<Player> thiefCakeTeamRed = new ArrayList<Player>();
	
	public static ArrayList<Location> blocks = new ArrayList<Location>();
	
	public ArrayList<Player> alive = new ArrayList<Player>();
	
	public boolean mapChangeAvailable;
	
	public boolean CakeTeamBlue;
	public boolean CakeTeamRed;
	
	public String Map;
	public String prefix = "§7[§dCakeWars§7] ";
	public String NoConsoleAllowed = prefix + "§cNur ein Spieler kann diesen Befehl benutzen!";
	public String NoPermission = prefix + "§cDazu hast du keine Berechtigung!";
	
	public FileManager fm;
	public Utils utils;
	public GameState state;
	public countdown cd;
	
	public int lobby;
	public int ingame;
	public int restart;
	
	@Override
	public void onEnable() {
		
		Utils.setScoreboard();
		
		CakeTeamBlue = true;
		CakeTeamRed = true;
		
		mapChangeAvailable = true;
		
		state = GameState.LOBBY;
		
		fm = new FileManager();
		main = this;
		utils = new Utils();
		cd = new countdown();
		
		lobby = 60;
		ingame = 60*60;
		restart = 15;
		
		cd.informationLobbyCD();
		
		fm.saveLocationCfg();
		
		//MySQL Verbindung aufbauen & einrichten
		
		MySQLFile MySQLfile = new MySQLFile();
		MySQLfile.setStandard();
		MySQLfile.readData();
		MySQL.connect();
		
		if(MySQL.isConnected()){
			Bukkit.getConsoleSender().sendMessage(prefix + "§aVerbindung zur §bDatenbank §aerfolgreich aufgebaut.");
			try {
				PreparedStatement ps =  (PreparedStatement) MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS stats (UUID VARCHAR(100),Kills VARCHAR(100),Deaths VARCHAR(100),Cakes VARCHAR(100),Played VARCHAR(100),Wins VARCHAR(100))");
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			Bukkit.getConsoleSender().sendMessage(prefix + "§cVerbindung zur §bDatenbank §cnicht erfolgreich aufgebaut.");
		}
		
		//Events & Commands registrieren
		
		PluginManager pm = Bukkit.getServer().getPluginManager();
		
		pm.registerEvents(new PlayerOnlineListener(), this);
		pm.registerEvents(new PlayerMapInteractListener(), this);
		pm.registerEvents(new PlayerItemInteractListener(), this);
		pm.registerEvents(new PlayerInteractWithPlayerListener(), this);
		pm.registerEvents(new PlayerDamageListener(), this);
		pm.registerEvents(new WorldWeatherListener(), this);
		
		getCommand("setup").setExecutor(new Setup_Command());
		getCommand("stats").setExecutor(new Stats_Command());
		getCommand("setlobby").setExecutor(new SetLobby_Command());
		getCommand("start").setExecutor(new Start_Command());
		getCommand("setteamspawn").setExecutor(new SetTeamSpawn_Command());
		getCommand("build").setExecutor(new Build_Command());
		getCommand("forcemap").setExecutor(new ForceMap_Command());
		getCommand("setspawner").setExecutor(new SetSpawner_Command());
		getCommand("setcake").setExecutor(new SetCake_Command());

	}
	
	@Override
	public void onDisable() {
		
		Bewerbung.main.fm.getCakeTeam1(Map).getBlock().setType(Material.CAKE_BLOCK);
		Bewerbung.main.fm.getCakeTeam2(Map).getBlock().setType(Material.CAKE_BLOCK);
		
		for(Location blocksToResetLocations : blocks){
			blocksToResetLocations.getBlock().setType(Material.AIR);
		}
		
		for (World world : Bukkit.getWorlds()) {
			for (Entity entity : world.getEntities()) {
				if (entity.getType() == EntityType.DROPPED_ITEM) {
					entity.remove();
				}
			}
		}
		
		MySQL.disconnect();
		
	}

	public static void sendSetupMessage(Player player){
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("§7§l§m------------------[§c§l SETUP §7§l§m]-------------------");
		if(MySQL.isConnected()){
			player.sendMessage("§bMySQL Datenbank §7- §aVerbunden");
		}else{
			player.sendMessage("§bMySQL Datenbank §7- §cGetrennt");
		}
		player.sendMessage("§bMap erstellen §7- §e/setup <Map-Name>");
		player.sendMessage("§bLobby §7- §e/setlobby");
		player.sendMessage("§bSpawner Locations §7- §e/setspawner <Resource> <Map-Name>");
		player.sendMessage("§bTeamspawns §7- §e/setteamspawn §91§e/§42 §e<Map-Name>");
		player.sendMessage("§bKuchen Locations §7- §e/setcake §91§e/§42 §e<Map-Name>");
		player.sendMessage("§7§l§m------------------[§c§l SETUP §7§l§m]-------------------");
		player.sendMessage("");
		player.sendMessage("");
	}
	
}
