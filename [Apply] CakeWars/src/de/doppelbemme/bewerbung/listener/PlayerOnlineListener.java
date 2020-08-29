package de.doppelbemme.bewerbung.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.doppelbemme.bewerbung.main.Bewerbung;
import de.doppelbemme.bewerbung.main.GameState;
import de.doppelbemme.bewerbung.main.countdown;
import de.doppelbemme.bewerbung.mysql.MySQL;
import de.doppelbemme.bewerbung.mysql.MySQLStats;

public class PlayerOnlineListener implements Listener{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		
		Integer OnlinePlayerAmount = Bukkit.getOnlinePlayers().size();
		
		event.setJoinMessage(null);
		
		if(Bewerbung.main.state == GameState.LOBBY){
		Bukkit.broadcastMessage("§a» §7" + event.getPlayer().getName() + " §7hat die Runde betreten");
		if(player.hasPermission("cakewars.setup")){
			if(!MySQL.isConnected()){
				Bewerbung.sendSetupMessage(player);
			}
		}
		
		Bukkit.getScheduler().runTaskLater(Bewerbung.main, new Runnable() {
			
			@Override
			public void run() {
				
				player.teleport(Bewerbung.main.fm.getSpawn());
				Bewerbung.main.utils.setLobbyItems(player);
				Bewerbung.main.utils.setTeamPrefix(player);
				
			}
		}, 2);
		
		if(!MySQLStats.isUserExisting(player.getUniqueId())){
			MySQLStats.register(player.getUniqueId(), 0, 0, 0, 0, 0);
			Bukkit.getConsoleSender().sendMessage(Bewerbung.main.prefix + "§bDatensatz §afür den Spieler §e" + player.getName() +  "§7(§e" + player.getUniqueId() + "§7)" + " §aerfolgreich erstellt!");
		}
		
		
		if(OnlinePlayerAmount == 2){
			Bewerbung.main.cd.startLobbyCD(player);
			Bukkit.getScheduler().cancelTask(Bewerbung.main.cd.informcd);
		}
	}else if(Bewerbung.main.state == GameState.INGAME || Bewerbung.main.state == GameState.RESTART){
			
		Bukkit.getScheduler().runTaskLater(Bewerbung.main, new Runnable() {
			
			@Override
			public void run() {
				Bewerbung.main.utils.clearInventory(player);
				Bewerbung.main.utils.setPlayerSpectatorMode(player);
				}
			}, 5);
		}
	}
	
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		
		event.setQuitMessage(null);
		Bukkit.broadcastMessage("§c« §7" + event.getPlayer().getName() + " §7hat die Runde verlassen");
		Bewerbung.main.utils.removePlayerFromTeam(event.getPlayer());
		Bewerbung.main.utils.setTeamPrefix(event.getPlayer());
		
		Bukkit.getScheduler().runTaskLater(Bewerbung.main, new Runnable() {
			
			@Override
			public void run() {
				
				if(Bewerbung.main.state == GameState.LOBBY){
				Integer OnlinePlayerAmount = Bukkit.getOnlinePlayers().size();
				
				if(OnlinePlayerAmount < 2){
					Bukkit.getScheduler().cancelTask(countdown.lobbycd);
					Bewerbung.main.cd.informationLobbyCD();
					Bewerbung.main.cd.lobbystarted = false;
					Bewerbung.main.lobby = 60;
					for(Player current : Bukkit.getOnlinePlayers()){
						current.setLevel(60);
						}
					}
				}else if(Bewerbung.main.state == GameState.INGAME){
					Bewerbung.main.alive.remove(event.getPlayer());
				}
			}
		}, 5);
		
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event){
		
		Player player = event.getPlayer();
		
		Integer onlinePlayerAmount = Bukkit.getOnlinePlayers().size();
		if(Bewerbung.main.state == GameState.LOBBY){
			if(onlinePlayerAmount == 2){
				String roundFull = "§cDiese Runde ist bereits voll!";
				event.disallow(null, roundFull);
			}
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		
		Player player = event.getPlayer();
		
		String message = event.getMessage();
		String newmessage = message.replace("%", "Prozent");
		
		
		event.setCancelled(true);
		
		if(player.getGameMode() == GameMode.SPECTATOR){
			player.sendMessage(Bewerbung.main.prefix + "§cRuhe auf den billigen Plätzen!");
		}else{
		Bukkit.broadcastMessage(player.getDisplayName() + "§8: §7" + newmessage);
		}
	}
	
}
