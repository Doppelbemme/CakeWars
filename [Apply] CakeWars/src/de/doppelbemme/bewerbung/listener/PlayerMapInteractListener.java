package de.doppelbemme.bewerbung.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.doppelbemme.bewerbung.main.Bewerbung;
import de.doppelbemme.bewerbung.main.GameState;
import de.doppelbemme.bewerbung.mysql.MySQLStats;

public class PlayerMapInteractListener implements Listener{
	
	@EventHandler
	public void onBreak(BlockBreakEvent event){
		
		ItemStack cakeskull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta cakeskullmeta = (SkullMeta) cakeskull.getItemMeta();
		cakeskullmeta.setOwner("MHF_Cake");
		cakeskullmeta.setDisplayName("§dKuchen");
		cakeskull.setItemMeta(cakeskullmeta);
		
		Player player = event.getPlayer();
		
		if(Bewerbung.main.state != GameState.INGAME){
			if(Bewerbung.BuilderList.contains(event.getPlayer())){
				event.setCancelled(false);
			}else{
			event.setCancelled(true);
			}
		}else{
			
			if(event.getBlock().getType() == Material.CAKE_BLOCK){
				
				if(event.getBlock().getLocation().equals(Bewerbung.main.fm.getCakeTeam1(Bewerbung.main.Map)) && !Bewerbung.main.thiefCakeTeamRed.contains(player) && Bewerbung.main.TeamBlue.contains(event.getPlayer())){
					player.sendMessage(Bewerbung.main.prefix + "§cDu kannst deinen eigenen Kuchen nicht stehlen!");
					event.setCancelled(true);
				}else if(event.getBlock().getLocation().equals(Bewerbung.main.fm.getCakeTeam2(Bewerbung.main.Map)) && !Bewerbung.main.thiefCakeTeamBlue.contains(player) && Bewerbung.main.TeamRed.contains(event.getPlayer())){
					player.sendMessage(Bewerbung.main.prefix + "§cDu kannst deinen eigenen Kuchen nicht stehlen!");
					event.setCancelled(true);
				}else if(event.getBlock().getLocation().equals(Bewerbung.main.fm.getCakeTeam1(Bewerbung.main.Map)) && Bewerbung.main.TeamRed.contains(player)){
					Bewerbung.main.thiefCakeTeamBlue.add(player);
					
					player.getInventory().setHelmet(cakeskull);
					
					Bukkit.broadcastMessage(Bewerbung.main.prefix + player.getDisplayName() + " §7hat den Kuchen von §9Team Blau §7gestohlen!");
					Bewerbung.main.fm.getCakeTeam1(Bewerbung.main.Map).getBlock().setType(Material.AIR);
				}else if(event.getBlock().getLocation().equals(Bewerbung.main.fm.getCakeTeam2(Bewerbung.main.Map)) && Bewerbung.main.TeamBlue.contains(player)){
					Bewerbung.main.thiefCakeTeamRed.add(player);
					
					player.getInventory().setHelmet(cakeskull);
					
					Bukkit.broadcastMessage(Bewerbung.main.prefix + player.getDisplayName() + " §7hat den Kuchen von §4Team Rot §7gestohlen!");
					Bewerbung.main.fm.getCakeTeam2(Bewerbung.main.Map).getBlock().setType(Material.AIR);
				}else if(event.getBlock().getLocation().equals(Bewerbung.main.fm.getCakeTeam1(Bewerbung.main.Map)) && Bewerbung.main.thiefCakeTeamRed.contains(player)){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Der Kuchen von §4Team Rot §7wurde aufgegessen!");
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§4Team Rot §ckann nun nicht mehr respawnen!");
					Bewerbung.main.CakeTeamRed = false;
					MySQLStats.addCakes(Bewerbung.main.thiefCakeTeamRed.get(0).getUniqueId(), 1);
					Bewerbung.main.thiefCakeTeamRed.clear();
					event.setCancelled(true);
				}else if(event.getBlock().getLocation().equals(Bewerbung.main.fm.getCakeTeam2(Bewerbung.main.Map)) && Bewerbung.main.thiefCakeTeamBlue.contains(player)){
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Der Kuchen von §9Team Blau §7wurde aufgegessen!");
					Bukkit.broadcastMessage(Bewerbung.main.prefix + "§9Team Blau §ckann nun nicht mehr respawnen!");
					Bewerbung.main.CakeTeamBlue = false;
					MySQLStats.addCakes(Bewerbung.main.thiefCakeTeamBlue.get(0).getUniqueId(), 1);
					Bewerbung.main.thiefCakeTeamBlue.clear();
					event.setCancelled(true);
				}
				
			}else{
			
			if(!Bewerbung.blocks.contains(event.getBlock().getLocation())){
				event.getPlayer().sendMessage(Bewerbung.main.prefix + "§7Du kannst nur §eBlöcke abbauen§7, die von §eSpielern gesetzt §7wurden!");
				event.setCancelled(true);
			}else{
				if(event.getBlock().getType() == Material.SANDSTONE){
					event.getBlock().setType(Material.AIR);
					
					ItemStack SandstoneStack = new ItemStack(Material.SANDSTONE);
					ItemMeta SandstoneMeta = SandstoneStack.getItemMeta();
					SandstoneMeta.setDisplayName("§6Sandstein");
					SandstoneStack.setItemMeta(SandstoneMeta);
					
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), SandstoneStack);
					
				}
				Bewerbung.blocks.remove(event.getBlock().getLocation());
				event.setCancelled(false);
			}
		}
		}
		
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event){
		if(Bewerbung.main.state == GameState.INGAME){
			Bewerbung.blocks.add(event.getBlock().getLocation());
		}else if(Bewerbung.main.state == GameState.LOBBY || Bewerbung.main.state == GameState.RESTART){
			if(Bewerbung.BuilderList.contains(event.getPlayer())){
				event.setCancelled(false);
			}else{
			event.setCancelled(true);
			}
		}
	}
	
}
