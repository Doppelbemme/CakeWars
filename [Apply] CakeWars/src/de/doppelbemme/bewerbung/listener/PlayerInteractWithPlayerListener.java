package de.doppelbemme.bewerbung.listener;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import de.doppelbemme.bewerbung.main.Bewerbung;
import de.doppelbemme.bewerbung.main.GameState;

public class PlayerInteractWithPlayerListener implements Listener {

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent event){
		
		if(Bewerbung.main.state == GameState.LOBBY || Bewerbung.main.state == GameState.RESTART){
			event.setCancelled(true);
			}
		if(event.getEntity().getType() == EntityType.VILLAGER){
			event.setCancelled(true);
		}
		}
	
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event){
		if(event.getRightClicked().getType() == EntityType.VILLAGER){
					
			if(Bewerbung.main.state == GameState.INGAME){
				
			Bewerbung.main.utils.openShopMainPage(event.getPlayer());
			event.setCancelled(true);
			
		}else{
			
			event.setCancelled(true);
		}
	}
	}
	
	@EventHandler
    public void onCakeEat(PlayerItemConsumeEvent e) {
        if (e.getItem().equals(Material.CAKE_BLOCK)) {
            e.setCancelled(true);
        }
    }
	
	}