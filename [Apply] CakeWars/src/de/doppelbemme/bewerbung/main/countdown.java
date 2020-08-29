package de.doppelbemme.bewerbung.main;


import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.doppelbemme.bewerbung.mysql.MySQLStats;

public class countdown {

	public boolean lobbystarted = false;
	public boolean restartstarted = false; 
	
	public static int lobbycd;
	public int informcd;
	int ingamecd;
	int restartcd;
	
	@SuppressWarnings("deprecation")
	public void informationLobbyCD(){
		informcd = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Bewerbung.main, new Runnable() {
					
			@Override
			public void run() {
				if(Bukkit.getOnlinePlayers().size() < 2 && Bukkit.getOnlinePlayers().size() > 0){
					
					Integer playerStillNeeded = 2-Bukkit.getOnlinePlayers().size();
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§cWarte auf §e" + playerStillNeeded + " §cweitere Spieler...");
				
				}
			}
		}, 0, 20*60L);
	}
	
	public void startLobbyCD(Player player) {
	if(lobbystarted == false) {
		lobbystarted = true;
		lobbycd = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) Bewerbung.main, new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				
				if(Bewerbung.main.lobby == 60){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde beginnt in §e60 §7Sekunden");
					for(Player current : Bukkit.getOnlinePlayers()){
						current.playSound(current.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
						current.setLevel(Bewerbung.main.lobby);
					}
				}else if(Bewerbung.main.lobby == 30){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde beginnt in §e30 §7Sekunden");
					for(Player current : Bukkit.getOnlinePlayers()){
						current.playSound(current.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
					}
				}else if(Bewerbung.main.lobby == 15){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde beginnt in §e15 §7Sekunden");
					for(Player current : Bukkit.getOnlinePlayers()){
						current.playSound(current.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
					}
				}else if(Bewerbung.main.lobby == 10){
					Bewerbung.main.utils.chooseRandomMap();
				}else if(Bewerbung.main.lobby <= 5 && Bewerbung.main.lobby >= 2){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde beginnt in §e" + Bewerbung.main.lobby + " §7Sekunden");
					for(Player current : Bukkit.getOnlinePlayers()){
						current.sendTitle("§7§l» §c" + Bewerbung.main.lobby + " §7§l«", "");
						current.playSound(current.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
					}
				}else if(Bewerbung.main.lobby == 1){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde beginnt in §e" + Bewerbung.main.lobby + " §7Sekunde");
					for(Player current : Bukkit.getOnlinePlayers()){
						current.sendTitle("§7§l» §c" + Bewerbung.main.lobby + " §7§l«", "");
						current.playSound(current.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
					}
				}else if(Bewerbung.main.lobby == 0){
				
					for(Player current : Bukkit.getOnlinePlayers()){
						current.sendTitle("§7§l» §eGO §7§l«", "");
					}
					
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§aDie Runde beginnt jetzt");
					for(Player current : Bukkit.getOnlinePlayers()){
						current.playSound(current.getLocation(), Sound.LEVEL_UP, 2.0F, 1.0F);
						Bewerbung.main.utils.pushPlayerInTeam(current);
						Bewerbung.main.utils.BaseTeleport(current);
					}
					
					Bewerbung.main.utils.startSpawning();
					
					Bewerbung.main.state = GameState.INGAME;
					Bukkit.getScheduler().cancelTask(lobbycd);
					startIngameCD();
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§aDu wurdest auf deine Insel teleportiert!");
					for(Player current : Bukkit.getOnlinePlayers()){
						Bewerbung.main.utils.clearInventory(current);
						Bewerbung.main.alive.add(current);
						MySQLStats.addGames(current.getUniqueId(), 1);
					}
				}
				
				for(Player current : Bukkit.getOnlinePlayers()){
					current.setLevel(Bewerbung.main.lobby);
				}
				
				Bewerbung.main.lobby --;
				}
			
			}, 0, 20L);
		
		}
	}
	
	public void startIngameCD() {
		ingamecd = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) Bewerbung.main, new Runnable() {

			@Override
			public void run() {

				if(Bewerbung.main.ingame == 60*60){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e60 §7Minuten");
				}else if(Bewerbung.main.ingame == 60*30){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e30 §7Minuten");
				}else if(Bewerbung.main.ingame == 60*15){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e15 §7Minuten");
				}else if(Bewerbung.main.ingame == 60*10){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e10 §7Minuten");
				}else if(Bewerbung.main.ingame == 60*5){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e5 §7Minuten");
				}else if(Bewerbung.main.ingame == 60*3){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e3 §7Minuten");
				}else if(Bewerbung.main.ingame == 60*2){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e2 §7Minuten");
				}else if(Bewerbung.main.ingame == 60){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e60 §7Sekunden");
				}else if(Bewerbung.main.ingame <= 15 && Bewerbung.main.ingame >= 2){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e" + Bewerbung.main.ingame + "§7Sekunden");
				}else if(Bewerbung.main.ingame == 1){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde endet in §e1 §7Sekunde");
				}else if(Bewerbung.main.ingame == 0){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde ist jetzt vorbei");
					Bukkit.getScheduler().cancelTask(ingamecd);
					startRestartCD();
				}
				
				if(Bewerbung.main.alive.size() == 1){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde ist jetzt vorbei");
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§a" + Bewerbung.main.alive.get(0).getDisplayName() + " §ahat das Spiel gewonnen!");
					MySQLStats.addWins(Bewerbung.main.alive.get(0).getUniqueId(), 1);
					Bukkit.getScheduler().cancelTask(ingamecd);
					startRestartCD();
					for(Player current : Bukkit.getOnlinePlayers()){
						current.playSound(current.getLocation(), Sound.LEVEL_UP, 2.0F, 2.0F);
						}
					}else if(Bewerbung.main.alive.size() == 0){
						Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Die Runde ist jetzt vorbei");
						Bukkit.getScheduler().cancelTask(ingamecd);
						startRestartCD();
					}
				Bewerbung.main.ingame --;
				}
		}, 0, 20L);
		
	}
	
	public void startRestartCD() {
		if(restartstarted == false) {
			restartstarted = true;
			restartcd = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) Bewerbung.main, new Runnable() {

				@Override
				public void run() {
					
					if(Bewerbung.main.restart <=15 && Bewerbung.main.restart >= 2){
						Bukkit.broadcastMessage(Bewerbung.main.prefix + "§4Der Server startet in §e" + Bewerbung.main.restart + " §4Sekunden neu!");
					}else if(Bewerbung.main.restart == 1){
						Bukkit.broadcastMessage(Bewerbung.main.prefix + "§4Der Server startet in §e" + Bewerbung.main.restart + " §4Sekunde neu!");
					}else if(Bewerbung.main.restart == 0){
						Bukkit.broadcastMessage(Bewerbung.main.prefix + "§4Der Server startet jetzt neu!");
						Bukkit.getServer().shutdown();
					}
				
					Bewerbung.main.restart --;
				}
			}, 0, 20L);
		}	
		}
	
}
