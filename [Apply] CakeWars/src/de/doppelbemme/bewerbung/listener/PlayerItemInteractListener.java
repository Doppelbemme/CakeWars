package de.doppelbemme.bewerbung.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.doppelbemme.bewerbung.main.Bewerbung;
import de.doppelbemme.bewerbung.main.GameState;
import de.doppelbemme.bewerbung.mysql.MySQLStats;

public class PlayerItemInteractListener implements Listener{

	@EventHandler
	public void onItemInteract(PlayerInteractEvent event){
		
		Player player = (Player)event.getPlayer();
		ItemStack clickedItem = event.getItem();
		
		if(clickedItem != null){
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			
		if(clickedItem.getType() == Material.REDSTONE && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cVerlassen")){
			player.kickPlayer(null);
		}else if(clickedItem.getType() == Material.CAKE && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§8» §bTeams")){
			Bewerbung.main.utils.openTeamInventory(player);
		}else if(clickedItem.getType() == Material.REDSTONE_TORCH_ON && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§8» §aRunde Starten")){
			Bewerbung.main.utils.startRound(player);
		}	
			}
		}
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event){
		if(Bewerbung.main.state == GameState.LOBBY || Bewerbung.main.state == GameState.RESTART){
			event.setCancelled(true);
		}
	}
	
	
	
	@EventHandler
	public void omItemPickup(PlayerPickupItemEvent event){
		Player player = event.getPlayer();
		
		if(Bewerbung.main.state == GameState.INGAME){
			if(event.getItem().getItemStack().getType().equals(Material.CLAY_BRICK)){
				Integer pickedUpAmount = event.getItem().getItemStack().getAmount();
				Bewerbung.main.utils.addLevel(player, 3*pickedUpAmount);
				Bukkit.getScheduler().runTaskLater(Bewerbung.main, new Runnable() {
					
					@Override
					public void run() {
						player.getInventory().remove(Material.CLAY_BRICK);
						player.updateInventory();	
					}
				}, 2);
			}else if(event.getItem().getItemStack().getType().equals(Material.IRON_INGOT)){
				Integer pickedUpAmount = event.getItem().getItemStack().getAmount();
				Bewerbung.main.utils.addLevel(player, 10*pickedUpAmount);
				Bukkit.getScheduler().runTaskLater(Bewerbung.main, new Runnable() {
					
					@Override
					public void run() {
						player.getInventory().remove(Material.IRON_INGOT);
						player.updateInventory();	
					}
				}, 2);
			}else if(event.getItem().getItemStack().getType().equals(Material.GOLD_INGOT)){
				Integer pickedUpAmount = event.getItem().getItemStack().getAmount();
				Bewerbung.main.utils.addLevel(player, 25*pickedUpAmount);
				Bukkit.getScheduler().runTaskLater(Bewerbung.main, new Runnable() {
					
					@Override
					public void run() {
						player.getInventory().remove(Material.GOLD_INGOT);
						player.updateInventory();	
					}
				}, 2);
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		
		ItemStack KnockbackStack = new ItemStack(Material.STICK);
		ItemMeta KnockbackMeta = KnockbackStack.getItemMeta();
		KnockbackMeta.setDisplayName("§6Knockback Stick");
		KnockbackMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		KnockbackStack.setItemMeta(KnockbackMeta);
		
		ItemStack Sword1Stack = new ItemStack(Material.GOLD_SWORD);
		ItemMeta Sword1Meta = Sword1Stack.getItemMeta();
		Sword1Meta.setDisplayName("§6Schwert Level §cI");
		Sword1Meta.addEnchant(Enchantment.DURABILITY, 10, true);
		Sword1Stack.setItemMeta(Sword1Meta);
		
		ItemStack Sword2Stack = new ItemStack(Material.GOLD_SWORD);
		ItemMeta Sword2Meta = Sword2Stack.getItemMeta();
		Sword2Meta.setDisplayName("§6Schwert Level §cII");
		Sword2Meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		Sword2Meta.addEnchant(Enchantment.DURABILITY, 10, true);
		Sword2Stack.setItemMeta(Sword2Meta);
		
		ItemStack Sword3Stack = new ItemStack(Material.GOLD_SWORD);
		ItemMeta Sword3Meta = Sword3Stack.getItemMeta();
		Sword3Meta.setDisplayName("§6Schwert Level §cIII");
		Sword3Meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
		Sword3Meta.addEnchant(Enchantment.DURABILITY, 10, true);
		Sword3Stack.setItemMeta(Sword3Meta);	
		
		
		//Bow\\
		
		
		ItemStack Bow1Stack = new ItemStack(Material.BOW);
		ItemMeta Bow1Meta = Bow1Stack.getItemMeta();
		Bow1Meta.setDisplayName("§6Bogen Level §cI");
		Bow1Stack.setItemMeta(Bow1Meta);
		
		ItemStack Bow2Stack = new ItemStack(Material.BOW);
		ItemMeta Bow2Meta = Bow2Stack.getItemMeta();
		Bow2Meta.setDisplayName("§6Bogen Level §cII");
		Bow2Meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		Bow2Stack.setItemMeta(Bow2Meta);
		
		ItemStack Bow3Stack = new ItemStack(Material.BOW);
		ItemMeta Bow3Meta = Bow3Stack.getItemMeta();
		Bow3Meta.setDisplayName("§6Bogen Level §cIII");
		Bow3Meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		Bow3Meta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
		Bow3Stack.setItemMeta(Bow3Meta);
		
		ItemStack ArrowStack = new ItemStack(Material.ARROW, 4);
		ItemMeta ArrowMeta = ArrowStack.getItemMeta();
		ArrowMeta.setDisplayName("§6Pfeil");
		ArrowStack.setItemMeta(ArrowMeta);
		
		
		//Armor\\
		
		
		ItemStack HelmetStack = new ItemStack(Material.LEATHER_HELMET);
		ItemMeta HelmetMeta = HelmetStack.getItemMeta();
		HelmetMeta.setDisplayName("§6Leder Helm");
		HelmetMeta.addEnchant(Enchantment.DURABILITY, 3, true);
		HelmetStack.setItemMeta(HelmetMeta);
		
		ItemStack LeggingsStack = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemMeta LeggingsMeta = LeggingsStack.getItemMeta();
		LeggingsMeta.setDisplayName("§6Leder Hose");
		LeggingsMeta.addEnchant(Enchantment.DURABILITY, 3, true);
		LeggingsStack.setItemMeta(LeggingsMeta);
		
		ItemStack BootsStack = new ItemStack(Material.LEATHER_BOOTS);
		ItemMeta BootsMeta = BootsStack.getItemMeta();
		BootsMeta.setDisplayName("§6Leder Schuhe");
		BootsMeta.addEnchant(Enchantment.DURABILITY, 3, true);
		BootsStack.setItemMeta(BootsMeta);
		
		ItemStack Chestplate1Stack = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta Chestplate1Meta = Chestplate1Stack.getItemMeta();
		Chestplate1Meta.setDisplayName("§6Brustplatte Level §cI");
		Chestplate1Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		Chestplate1Meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
		Chestplate1Stack.setItemMeta(Chestplate1Meta);
		
		ItemStack Chestplate2Stack = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta Chestplate2Meta = Chestplate2Stack.getItemMeta();
		Chestplate2Meta.setDisplayName("§6Brustplatte Level §cII");
		Chestplate2Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		Chestplate2Meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 2, true);
		Chestplate2Stack.setItemMeta(Chestplate2Meta);
		
		ItemStack Chestplate3Stack = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta Chestplate3Meta = Chestplate3Stack.getItemMeta();
		Chestplate3Meta.setDisplayName("§6Brustplatte Level §cIII");
		Chestplate3Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
		Chestplate3Meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
		Chestplate3Stack.setItemMeta(Chestplate3Meta);
		
		
		//Pickaxe\\
		
		
		ItemStack Pickaxe1Stack = new ItemStack(Material.WOOD_PICKAXE);
		ItemMeta Pickaxe1Meta = Pickaxe1Stack.getItemMeta();
		Pickaxe1Meta.setDisplayName("§6Spitzhacke Level §cI");
		Pickaxe1Meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
		Pickaxe1Meta.addEnchant(Enchantment.DURABILITY, 10, true);
		Pickaxe1Stack.setItemMeta(Pickaxe1Meta);
		
		ItemStack Pickaxe2Stack = new ItemStack(Material.STONE_PICKAXE);
		ItemMeta Pickaxe2Meta = Pickaxe2Stack.getItemMeta();
		Pickaxe2Meta.setDisplayName("§6Spitzhacke Level §cII");
		Pickaxe2Meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
		Pickaxe2Meta.addEnchant(Enchantment.DURABILITY, 10, true);
		Pickaxe2Stack.setItemMeta(Pickaxe2Meta);
		
		ItemStack Pickaxe3Stack = new ItemStack(Material.GOLD_PICKAXE);
		ItemMeta Pickaxe3Meta = Pickaxe3Stack.getItemMeta();
		Pickaxe3Meta.setDisplayName("§6Spitzhacke Level §cIII");
		Pickaxe3Meta.addEnchant(Enchantment.DIG_SPEED, 2, true);
		Pickaxe3Meta.addEnchant(Enchantment.DURABILITY, 10, true);
		Pickaxe3Stack.setItemMeta(Pickaxe3Meta);
		
		
		//Blocks\\
		
		
		ItemStack SandstoneStack = new ItemStack(Material.SANDSTONE, 4);
		ItemMeta SandstoneMeta = SandstoneStack.getItemMeta();
		SandstoneMeta.setDisplayName("§6Sandstein");
		SandstoneStack.setItemMeta(SandstoneMeta);
		
		ItemStack SandstoneStackStack = new ItemStack(Material.SANDSTONE, 64);
		ItemMeta SandstoneStackMeta = SandstoneStackStack.getItemMeta();
		SandstoneStackMeta.setDisplayName("§6Sandstein");
		SandstoneStackStack.setItemMeta(SandstoneStackMeta);
		
		ItemStack SandstoneStairStack = new ItemStack(Material.SANDSTONE_STAIRS);
		ItemMeta SandstoneStairMeta = SandstoneStairStack.getItemMeta();
		SandstoneStairMeta.setDisplayName("§6Sandstein Treppe");
		SandstoneStairStack.setItemMeta(SandstoneStairMeta);
		
		ItemStack EndstoneStack = new ItemStack(Material.ENDER_STONE, 2);
		ItemMeta EndstoneMeta = EndstoneStack.getItemMeta();
		EndstoneMeta.setDisplayName("§6Endstein");
		EndstoneStack.setItemMeta(EndstoneMeta);
		
		ItemStack GlasStack = new ItemStack(Material.GLASS);
		ItemMeta GlasMeta = GlasStack.getItemMeta();
		GlasMeta.setDisplayName("§6Glas");
		GlasStack.setItemMeta(GlasMeta);
		
		ItemStack LadderStack = new ItemStack(Material.LADDER);
		ItemMeta LadderMeta = LadderStack.getItemMeta();
		LadderMeta.setDisplayName("§6Leiter");
		LadderStack.setItemMeta(LadderMeta);
		
		ItemStack CobwebStack = new ItemStack(Material.WEB);
		ItemMeta CobwebMeta = CobwebStack.getItemMeta();
		CobwebMeta.setDisplayName("§6Spinnenweben");
		CobwebStack.setItemMeta(CobwebMeta);
		
		
		
		//Food\\
		
		
		ItemStack AppleStack = new ItemStack(Material.APPLE, 3);
		ItemMeta AppleMeta = AppleStack.getItemMeta();
		AppleMeta.setDisplayName("§6Apfel");
		AppleStack.setItemMeta(AppleMeta);
		
		@SuppressWarnings("deprecation")
		ItemStack CarrotStack = new ItemStack(391);
		ItemMeta CarrotMeta = CarrotStack.getItemMeta();
		CarrotMeta.setDisplayName("§6Karotte");
		CarrotStack.setItemMeta(CarrotMeta);
		
		ItemStack MelonStack = new ItemStack(Material.MELON, 3);
		ItemMeta MelonMeta = MelonStack.getItemMeta();
		MelonMeta.setDisplayName("§6Melone");
		MelonStack.setItemMeta(MelonMeta);
		
		ItemStack PotatoStack = new ItemStack(Material.BAKED_POTATO);
		ItemMeta PotatoMeta = PotatoStack.getItemMeta();
		PotatoMeta.setDisplayName("§6Kartoffel");
		PotatoStack.setItemMeta(PotatoMeta);
		
		ItemStack BeefStack = new ItemStack(Material.COOKED_BEEF);
		ItemMeta BeefMeta = BeefStack.getItemMeta();
		BeefMeta.setDisplayName("§6Gebratenes Schweinefleisch");
		BeefStack.setItemMeta(BeefMeta);
		
		ItemStack GoldenAppleStack = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta GoldenAppleMeta = GoldenAppleStack.getItemMeta();
		GoldenAppleMeta.setDisplayName("§6Goldener Apfel");
		GoldenAppleStack.setItemMeta(GoldenAppleMeta);
		
		Player player = (Player)event.getWhoClicked();
		ItemStack clickedItem = event.getCurrentItem();
		Inventory clickedInventory = event.getClickedInventory();
	
		if(clickedItem != null){
		
			if(Bewerbung.main.state == GameState.LOBBY || Bewerbung.main.state == GameState.RESTART){
				event.setCancelled(true);
			}
			if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§dKuchen")){
				event.setCancelled(true);
			}
			if(clickedInventory.getName().equalsIgnoreCase("§bTeams")){
			if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§9Blaues Team")){
				if(Bewerbung.main.TeamBlue.size() >= 1){
					player.sendMessage(Bewerbung.main.prefix + "§cDieses Team ist bereits voll!");
				}else{
					Bewerbung.main.utils.removePlayerFromTeam(player);
					Bewerbung.main.TeamBlue.add(player);
					Bewerbung.main.utils.setTeamPrefix(player);
					player.sendMessage(Bewerbung.main.prefix + "§7Du bist nun in §9Team Blau§7.");
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
				}
				player.closeInventory();
				event.setCancelled(true);
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§4Rotes Team")){
				if(Bewerbung.main.TeamRed.size() >= 1){
					player.sendMessage(Bewerbung.main.prefix + "§cDieses Team ist bereits voll!");
				}else{
					Bewerbung.main.utils.removePlayerFromTeam(player);
					Bewerbung.main.TeamRed.add(player);
					Bewerbung.main.utils.setTeamPrefix(player);
					player.sendMessage(Bewerbung.main.prefix + "§7Du bist nun in §4Team Rot§7.");
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
				}
				player.closeInventory();
				event.setCancelled(true);
			}
		}if(clickedInventory.getName().equals("§6Shop")){
			if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Waffen")){
				Bewerbung.main.utils.openShopWeaponPage(player);
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Bögen")){
				Bewerbung.main.utils.openShopBowPage(player);
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Rüstung")){
				Bewerbung.main.utils.openShopArmorPage(player);
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Spitzhacken")){
				Bewerbung.main.utils.openShopPickaxePage(player);
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Blöcke")){
				Bewerbung.main.utils.openShopBlockPage(player);
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Nahrung")){
				Bewerbung.main.utils.openShopFoodPage(player);
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Knockback Stick")){
				if(Bewerbung.main.utils.checkLevel(player, 20)){
					Bewerbung.main.utils.removeLevel(player, 20);
					player.getInventory().addItem(KnockbackStack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Schwert Level §cI")){
				if(Bewerbung.main.utils.checkLevel(player, 25)){
					Bewerbung.main.utils.removeLevel(player, 25);
					player.getInventory().addItem(Sword1Stack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Schwert Level §cII")){
				if(Bewerbung.main.utils.checkLevel(player, 50)){
					Bewerbung.main.utils.removeLevel(player, 50);
					player.getInventory().addItem(Sword2Stack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Schwert Level §cIII")){
				if(Bewerbung.main.utils.checkLevel(player, 100)){
					Bewerbung.main.utils.removeLevel(player, 100);
					player.getInventory().addItem(Sword3Stack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Bogen Level §cI")){
				if(Bewerbung.main.utils.checkLevel(player, 150)){
					Bewerbung.main.utils.removeLevel(player, 150);
					player.getInventory().addItem(Bow1Stack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Bogen Level §cII")){
				if(Bewerbung.main.utils.checkLevel(player, 250)){
					Bewerbung.main.utils.removeLevel(player, 250);
					player.getInventory().addItem(Bow2Stack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Bogen Level §cIII")){
				if(Bewerbung.main.utils.checkLevel(player, 450)){
					Bewerbung.main.utils.removeLevel(player, 450);
					player.getInventory().addItem(Bow3Stack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Pfeil")){
				if(Bewerbung.main.utils.checkLevel(player, 25)){
					Bewerbung.main.utils.removeLevel(player, 25);
					player.getInventory().addItem(ArrowStack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Leder Helm")){
				if(Bewerbung.main.utils.checkLevel(player, 5)){
					Bewerbung.main.utils.removeLevel(player, 5);
					player.getInventory().addItem(HelmetStack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Leder Hose")){
				if(Bewerbung.main.utils.checkLevel(player, 5)){
					Bewerbung.main.utils.removeLevel(player, 5);
					player.getInventory().addItem(LeggingsStack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6leder Schuhe")){
				if(Bewerbung.main.utils.checkLevel(player, 5)){
					Bewerbung.main.utils.removeLevel(player, 5);
					player.getInventory().addItem(BootsStack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Brustplatte Level §cI")){
				if(Bewerbung.main.utils.checkLevel(player, 50)){
					Bewerbung.main.utils.removeLevel(player, 50);
					player.getInventory().addItem(Chestplate1Stack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Brustplatte Level §cII")){
				if(Bewerbung.main.utils.checkLevel(player, 150)){
					Bewerbung.main.utils.removeLevel(player, 150);
					player.getInventory().addItem(Chestplate2Stack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Brustplatte Level §cIII")){
				if(Bewerbung.main.utils.checkLevel(player, 300)){
					Bewerbung.main.utils.removeLevel(player, 300);
					player.getInventory().addItem(Chestplate3Stack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Spitzhacke Level §cI")){
				if(Bewerbung.main.utils.checkLevel(player, 25)){
					Bewerbung.main.utils.removeLevel(player, 25);
					player.getInventory().addItem(Pickaxe1Stack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Spitzhacke Level §cII")){
				if(Bewerbung.main.utils.checkLevel(player, 50)){
					Bewerbung.main.utils.removeLevel(player, 50);
					player.getInventory().addItem(Pickaxe2Stack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Spitzhacke Level §cIII")){
				if(Bewerbung.main.utils.checkLevel(player, 100)){
					Bewerbung.main.utils.removeLevel(player, 100);
					player.getInventory().addItem(Pickaxe3Stack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Sandstein")){
				if(event.isShiftClick()){
					if(Bewerbung.main.utils.checkLevel(player, 3*16)){
						Bewerbung.main.utils.removeLevel(player, 3*16);
						player.getInventory().addItem(SandstoneStackStack);
					}	
				}else{
				if(Bewerbung.main.utils.checkLevel(player, 3)){
					Bewerbung.main.utils.removeLevel(player, 3);
					player.getInventory().addItem(SandstoneStack);
				}
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Sandstein Treppe")){
				if(Bewerbung.main.utils.checkLevel(player, 3)){
					Bewerbung.main.utils.removeLevel(player, 3);
					player.getInventory().addItem(SandstoneStairStack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Endstein")){
				if(Bewerbung.main.utils.checkLevel(player, 8)){
					Bewerbung.main.utils.removeLevel(player, 8);
					player.getInventory().addItem(EndstoneStack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Glas")){
				if(Bewerbung.main.utils.checkLevel(player, 3)){
					Bewerbung.main.utils.removeLevel(player, 3);
					player.getInventory().addItem(GlasStack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Leiter")){
				if(Bewerbung.main.utils.checkLevel(player, 5)){
					Bewerbung.main.utils.removeLevel(player, 5);
					player.getInventory().addItem(LadderStack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Spinnenweben")){
				if(Bewerbung.main.utils.checkLevel(player, 15)){
					Bewerbung.main.utils.removeLevel(player, 15);
					player.getInventory().addItem(CobwebStack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Apfel")){
				if(Bewerbung.main.utils.checkLevel(player, 2)){
					Bewerbung.main.utils.removeLevel(player, 2);
					player.getInventory().addItem(AppleStack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Karotte")){
				if(Bewerbung.main.utils.checkLevel(player, 1)){
					Bewerbung.main.utils.removeLevel(player, 1);
					player.getInventory().addItem(CarrotStack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Melone")){
				if(Bewerbung.main.utils.checkLevel(player, 2)){
					Bewerbung.main.utils.removeLevel(player, 2);
					player.getInventory().addItem(MelonStack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Kartoffel")){
				if(Bewerbung.main.utils.checkLevel(player, 2)){
					Bewerbung.main.utils.removeLevel(player, 2);
					player.getInventory().addItem(PotatoStack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Gebratenes Schweinefleisch")){
				if(Bewerbung.main.utils.checkLevel(player, 2)){
					Bewerbung.main.utils.removeLevel(player, 2);
					player.getInventory().addItem(BeefStack);
				}
			}else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§6Goldener Apfel")){
				if(Bewerbung.main.utils.checkLevel(player, 70)){
					Bewerbung.main.utils.removeLevel(player, 70);
					player.getInventory().addItem(GoldenAppleStack);
				}
			}else{
				event.setCancelled(true);
			}
			event.setCancelled(true);
		}
		}
	}
	
	
}
