package de.doppelbemme.bewerbung.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.doppelbemme.bewerbung.main.Bewerbung;
import de.doppelbemme.bewerbung.main.GameState;
import de.doppelbemme.bewerbung.mysql.MySQLStats;

public class PlayerDamageListener implements Listener{

	@EventHandler
	public void onDamage(EntityDamageEvent event){
		if(event.getEntityType() == EntityType.VILLAGER){
			event.setCancelled(true);
		}
		
		if(Bewerbung.main.state == GameState.LOBBY || Bewerbung.main.state == GameState.RESTART){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent event){
		event.setCancelled(true);
	}
	
	
	@EventHandler
	public void onPlayerKill(PlayerDeathEvent event){
		Player player = (Player)event.getEntity();
		Player killer = player.getKiller();
		
		event.setDeathMessage(null);
		
		if(killer == null){
			
			if(Bewerbung.main.TeamBlue.contains(player)){
								
				if(Bewerbung.main.thiefCakeTeamRed.contains(player)){
					Bewerbung.main.thiefCakeTeamRed.clear();
					Bewerbung.main.fm.getCakeTeam2(Bewerbung.main.Map).getBlock().setType(Material.CAKE_BLOCK);
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Der Kuchen von §4Team Rot §7ist wieder zurück!");
				}

				if(Bewerbung.main.CakeTeamBlue == true){
					Bewerbung.main.utils.clearLevel(player);
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§c" + player.getDisplayName() + " §cist §cgestorben!");
					Bewerbung.main.utils.respawnPlayer(player);
				}else{
					Bewerbung.main.utils.clearLevel(player);
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§c" + player.getDisplayName() + " §cist §cgestorben!");
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§c" + player.getDisplayName() + " §7ist somit ausgeschieden!");
					Bewerbung.main.utils.setPlayerSpectatorMode(player);
					Bewerbung.main.alive.remove(player);
					
					MySQLStats.addDeaths(player.getUniqueId(), 1);
				}
			}else if(Bewerbung.main.TeamRed.contains(player)){
				
				if(Bewerbung.main.thiefCakeTeamBlue.contains(player)){
					Bewerbung.main.thiefCakeTeamBlue.clear();
					Bewerbung.main.fm.getCakeTeam1(Bewerbung.main.Map).getBlock().setType(Material.CAKE_BLOCK);
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Der Kuchen von §9Team Blau §7ist wieder zurück!");
				}
				
				if(Bewerbung.main.CakeTeamRed == true){
					Bewerbung.main.utils.clearLevel(player);
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§c" + player.getDisplayName() + " §cist §cgestorben!");
					Bewerbung.main.utils.respawnPlayer(player);
				}else{
					Bewerbung.main.utils.clearLevel(player);
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§c" + player.getDisplayName() + " §cist §cgestorben!");
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§c" + player.getDisplayName() + " §7ist somit ausgeschieden!");
					Bewerbung.main.utils.setPlayerSpectatorMode(player);
					Bewerbung.main.alive.remove(player);
					
					MySQLStats.addDeaths(player.getUniqueId(), 1);;
				}
		}
			
		}else{
		
			if(Bewerbung.main.TeamBlue.contains(player)){
				
				if(Bewerbung.main.thiefCakeTeamRed.contains(player)){
					Bewerbung.main.thiefCakeTeamRed.clear();
					Bewerbung.main.fm.getCakeTeam2(Bewerbung.main.Map).getBlock().setType(Material.CAKE_BLOCK);
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Der Kuchen von §4Team Rot §7ist wieder zurück!");
				}
				
				if(Bewerbung.main.CakeTeamBlue == true){
					Bewerbung.main.utils.addLevel(killer, player.getLevel()/2);
					Bewerbung.main.utils.clearLevel(player);
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§c" + player.getDisplayName() + " §7wurde von §a" + killer.getDisplayName() + " §cgetötet!");
					Bewerbung.main.utils.respawnPlayer(player);
			}else{
				Bewerbung.main.utils.addLevel(killer, player.getLevel()/2);
				Bewerbung.main.utils.clearLevel(player);
				Bukkit.broadcastMessage(Bewerbung.main.prefix + "§c" + player.getDisplayName() + " §7wurde von §a" + killer.getDisplayName() + " §cgetötet!");
				Bukkit.broadcastMessage(Bewerbung.main.prefix + "§c" + player.getDisplayName() + " §7ist somit ausgeschieden!");
				Bewerbung.main.utils.setPlayerSpectatorMode(player);
				Bewerbung.main.alive.remove(player);
				
				MySQLStats.addDeaths(player.getUniqueId(), 1);
				MySQLStats.addKills(killer.getUniqueId(), 1);
			}
		}else if(Bewerbung.main.TeamRed.contains(player)){
			
			if(Bewerbung.main.thiefCakeTeamBlue.contains(player)){
				Bewerbung.main.thiefCakeTeamBlue.clear();
				Bewerbung.main.fm.getCakeTeam1(Bewerbung.main.Map).getBlock().setType(Material.CAKE_BLOCK);
				Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Der Kuchen von §9Team Blau §7ist wieder zurück!");
			}
			
			if(Bewerbung.main.CakeTeamRed == true){
				Bewerbung.main.utils.addLevel(killer, player.getLevel()/2);
				Bewerbung.main.utils.clearLevel(player);
				Bukkit.broadcastMessage(Bewerbung.main.prefix + "§c" + player.getDisplayName() + " §7wurde von §a" + killer.getDisplayName() + " §cgetötet!");
				Bewerbung.main.utils.respawnPlayer(player);
			}else{
				Bewerbung.main.utils.addLevel(killer, player.getLevel()/2);
				Bewerbung.main.utils.clearLevel(player);
				Bukkit.broadcastMessage(Bewerbung.main.prefix + "§c" + player.getDisplayName() + " §7wurde von §a" + killer.getDisplayName() + " §cgetötet!");
				Bukkit.broadcastMessage(Bewerbung.main.prefix + "§c" + player.getDisplayName() + " §7ist somit ausgeschieden!");
				Bewerbung.main.utils.setPlayerSpectatorMode(player);
				Bewerbung.main.alive.remove(player);
				
				MySQLStats.addDeaths(player.getUniqueId(), 1);
				MySQLStats.addKills(killer.getUniqueId(), 1);
			}
			}
		event.getDrops().clear();
		event.setDroppedExp(0);
		event.setDeathMessage(null);
		}
		}
}