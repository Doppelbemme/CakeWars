package de.doppelbemme.bewerbung.utils;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.defaults.SetIdleTimeoutCommand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;

import de.doppelbemme.bewerbung.main.Bewerbung;
import de.doppelbemme.bewerbung.main.GameState;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;


public class Utils {

	Inventory shopInv = Bukkit.createInventory(null, 9*2, "§6Shop");
	Inventory shopInvWeapon = Bukkit.createInventory(null, 9*2, "§6Shop"); 
	Inventory shopInvBow = Bukkit.createInventory(null, 9*2, "§6Shop"); 
	Inventory shopInvArmor = Bukkit.createInventory(null, 9*2, "§6Shop"); 
	Inventory shopInvPickaxe = Bukkit.createInventory(null, 9*2, "§6Shop"); 
	Inventory shopInvBlocks = Bukkit.createInventory(null, 9*2, "§6Shop"); 
	Inventory shopInvFood = Bukkit.createInventory(null, 9*2, "§6Shop"); 
	Inventory shopInvPotions = Bukkit.createInventory(null, 9*2, "§6Shop"); 
	Inventory shopInvSpecial = Bukkit.createInventory(null, 9*2, "§6Shop"); 
	
	public void removePlayerFromTeam(Player player){
		if(Bewerbung.main.TeamBlue.contains(player)){
			Bewerbung.main.TeamBlue.remove(player);
		}else if(Bewerbung.main.TeamRed.contains(player)){
			Bewerbung.main.TeamRed.remove(player);
		}
	}
	
	public void clearInventory(Player player){
		player.getInventory().clear();
		player.getInventory().setHelmet(new ItemStack(Material.AIR));
		player.getInventory().setChestplate(new ItemStack(Material.AIR));
		player.getInventory().setLeggings(new ItemStack(Material.AIR));
		player.getInventory().setBoots(new ItemStack(Material.AIR));
		player.setFoodLevel(20);
		player.setHealth(20);
		player.setFireTicks(0);
		player.setExp(0);
		player.setLevel(60);
		player.setGameMode(GameMode.SURVIVAL);
	}
	
	public void setLobbyItems(Player player){
		clearInventory(player);
		
		ItemStack CakeStack = new ItemStack(Material.CAKE);
		ItemMeta CakeMeta = CakeStack.getItemMeta();
		CakeMeta.setDisplayName("§8» §bTeams");
		CakeStack.setItemMeta(CakeMeta);
		
		ItemStack TorchStack = new ItemStack(Material.REDSTONE_TORCH_ON);
		ItemMeta TorchMeta = TorchStack.getItemMeta();
		TorchMeta.setDisplayName("§8» §aRunde Starten");
		TorchStack.setItemMeta(TorchMeta);		

		ItemStack RedstoneStack = new ItemStack(Material.REDSTONE);
		ItemMeta RedstoneMeta = RedstoneStack.getItemMeta();
		RedstoneMeta.setDisplayName("§8» §cVerlassen");
		RedstoneStack.setItemMeta(RedstoneMeta);
		
		player.getInventory().setItem(0, CakeStack);
		if(player.hasPermission("cakewars.start")){
			player.getInventory().setItem(4, TorchStack);
		}
		player.getInventory().setItem(8, RedstoneStack);
	}
	
	public void openTeamInventory(Player player){
				
		Inventory TeamInv = Bukkit.createInventory(null, 9*1, "§bTeams"); 
		
		ItemStack BlueWoolStack = new ItemStack(Material.WOOL, 1, (short)11);
		ItemMeta BlueWoolMeta = BlueWoolStack.getItemMeta();
		BlueWoolMeta.setDisplayName("§9Blaues Team");
		if(Bewerbung.main.TeamBlue.size() == 0){
			Bewerbung.main.loreTeamBlue.clear();
			Bewerbung.main.loreTeamBlue.add("§8-");
		}else{
			Bewerbung.main.loreTeamBlue.clear();
			Bewerbung.main.loreTeamBlue.add("§8-§9" + Bewerbung.main.TeamBlue.get(0).getName());
		}
		BlueWoolMeta.setLore(Bewerbung.main.loreTeamBlue);
		BlueWoolStack.setItemMeta(BlueWoolMeta);
		
		
		
		ItemStack RedWoolStack = new ItemStack(Material.WOOL, 1, (short)14);
		ItemMeta RedWoolMeta = RedWoolStack.getItemMeta();
		RedWoolMeta.setDisplayName("§4Rotes Team");
		if(Bewerbung.main.TeamRed.size() == 0){
			Bewerbung.main.loreTeamRed.clear();
			Bewerbung.main.loreTeamRed.add("§8-");
		}else{
			Bewerbung.main.loreTeamRed.clear();
			Bewerbung.main.loreTeamRed.add("§8-§4" + Bewerbung.main.TeamRed.get(0).getName());
		}
		RedWoolMeta.setLore(Bewerbung.main.loreTeamRed);
		RedWoolStack.setItemMeta(RedWoolMeta);
	
		
		TeamInv.setItem(2, BlueWoolStack);
		TeamInv.setItem(6, RedWoolStack);
		
		player.openInventory(TeamInv);
	}
	
	public void startRound(Player player){
		Integer OnlinePlayerAmount = Bukkit.getOnlinePlayers().size();
		if(OnlinePlayerAmount != 2){
			player.sendMessage(Bewerbung.main.prefix + "§cEs sind nicht genügend Spieler online.");
		}else{
			if(Bewerbung.main.lobby <= 10){
				player.sendMessage(Bewerbung.main.prefix + "§cDie Runde startet bereits.");
			}else{
				Bewerbung.main.lobby = 10;
				player.sendMessage(Bewerbung.main.prefix + "§aDu hast die Runde erfolgreich gestartet!");
			}
		}
	}
	
	public void pushPlayerInTeam(Player player){
		
		if(!Bewerbung.main.TeamRed.contains(player) && !Bewerbung.main.TeamBlue.contains(player)){
			if(Bewerbung.main.TeamRed.size() == 0){
				Bewerbung.main.TeamRed.add(player);
				player.sendMessage(Bewerbung.main.prefix + "§7Du bist nun in §4Team Rot§7.");
			}else if(Bewerbung.main.TeamBlue.size() == 0){
				Bewerbung.main.TeamBlue.add(player);
				player.sendMessage(Bewerbung.main.prefix + "§7Du bist nun in §9Team Blau§7.");
			}
			Bewerbung.main.utils.setTeamPrefix(player);
		}	
	}
	
	//Prefixe etc
	
	static Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
	
	public static void setScoreboard(){
				
		sb.registerNewTeam("001blue").setPrefix("§9");
		sb.registerNewTeam("002red").setPrefix("§4");
		sb.registerNewTeam("003noteam").setPrefix("§7");
		sb.registerNewTeam("004spec").setPrefix("§7[§c✗§7] ");

	}

	@SuppressWarnings("deprecation")
	public void setTeamPrefix(Player player){
		
		
		
		if(sb.getTeam("001blue").getEntries().contains(player)){
			sb.getTeam("001blue").removePlayer(player);
		}else if(sb.getTeam("002red").getEntries().contains(player)){
			sb.getTeam("002red").removePlayer(player);
		}else if(sb.getTeam("003noteam").getEntries().contains(player)){
			sb.getTeam("003noteam").removePlayer(player);
		}else if(sb.getTeam("004spec").getEntries().contains(player)){
			sb.getTeam("004spec").removePlayer(player);
		}
		
		if(!Bewerbung.main.TeamBlue.contains(player) && !Bewerbung.main.TeamRed.contains(player) && Bewerbung.main.state == GameState.LOBBY){
			sb.getTeam("003noteam").addPlayer(player);
			player.setScoreboard(sb);
			player.setDisplayName("§7" + player.getName());
		}else if(Bewerbung.main.TeamBlue.contains(player)){
			sb.getTeam("001blue").addPlayer(player);
			player.setScoreboard(sb);
			player.setDisplayName("§9" + player.getName());
		}else if(Bewerbung.main.TeamRed.contains(player)){
			sb.getTeam("002red").addPlayer(player);
			player.setScoreboard(sb);
			player.setDisplayName("§4" + player.getName());
		}else if(player.getGameMode() == GameMode.SPECTATOR){
			sb.getTeam("004spec").addPlayer(player);
			player.setScoreboard(sb);
			player.setDisplayName("§7[§c✗§7] " + player.getName());
		}
	}
		
	public void BaseTeleport(Player player){
		if(Bewerbung.main.TeamBlue.contains(player)){
			player.teleport(Bewerbung.main.fm.getSpawnTeam1(Bewerbung.main.Map));
		}else if(Bewerbung.main.TeamRed.contains(player)){
			player.teleport(Bewerbung.main.fm.getSpawnTeam2(Bewerbung.main.Map));
		}
	}
	
	public void chooseRandomMap(){
		
		//Verfügbare Maps:
		//China,
		
		Random randomInt = new Random();
		
		int mapInt = randomInt.nextInt(101);
		
		if(mapInt >= 0 && mapInt <=100){
			Bewerbung.main.Map = "China";
			Bukkit.broadcastMessage(Bewerbung.main.prefix + "§7Es wird die Map §eChina §7gespielt");
		}
	}
	
	public void setPlayerSpectatorMode(Player player){
		
		respawnPlayer(player);
		player.setGameMode(GameMode.SPECTATOR);
		Bewerbung.main.utils.setTeamPrefix(player);
		
		player.setExp(0);
		player.setLevel(0);
	}
	
	
	@SuppressWarnings("deprecation")
	public void startSpawning(){
		
		//Bronze
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Bewerbung.main, new Runnable() {
			@Override
			public void run() {
				String resource = "bronze";
				String mapname = Bewerbung.main.Map;
				int spawners = Bewerbung.main.fm.locationcfg.getInt(mapname + "." +resource+"SpawnerCount");
				
				for(int i = 1; i <= spawners; i++){
					
						World w = Bukkit.getWorld(Bewerbung.main.fm.locationcfg.getString(mapname + ".Spawner."+resource+"." + i + ".World"));
						double x = Bewerbung.main.fm.locationcfg.getDouble(mapname + ".Spawner."+resource+"." + i + ".X");
						double y = Bewerbung.main.fm.locationcfg.getDouble(mapname + ".Spawner."+resource+"." + i + ".Y");
						double z = Bewerbung.main.fm.locationcfg.getDouble(mapname + ".Spawner."+resource+"." + i + ".Z");
						Location spawnerLoc = new Location(w, x, y, z);

					Bukkit.getWorld(spawnerLoc.getWorld().getName()).dropItemNaturally(spawnerLoc, new ItemStack(336));
					
				}
			}
		}, 0, 20*2);
		
		//Eisen
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Bewerbung.main, new Runnable() {
			@Override
			public void run() {
				String resource = "iron";
				String mapname = Bewerbung.main.Map;
				int spawners = Bewerbung.main.fm.locationcfg.getInt(mapname + "." +resource+"SpawnerCount");
				
				for(int i = 1; i <= spawners; i++){
					
						World w = Bukkit.getWorld(Bewerbung.main.fm.locationcfg.getString(mapname + ".Spawner."+resource+"." + i + ".World"));
						double x = Bewerbung.main.fm.locationcfg.getDouble(mapname + ".Spawner."+resource+"." + i + ".X");
						double y = Bewerbung.main.fm.locationcfg.getDouble(mapname + ".Spawner."+resource+"." + i + ".Y");
						double z = Bewerbung.main.fm.locationcfg.getDouble(mapname + ".Spawner."+resource+"." + i + ".Z");
						Location spawnerLoc = new Location(w, x, y, z);

					Bukkit.getWorld(spawnerLoc.getWorld().getName()).dropItemNaturally(spawnerLoc, new ItemStack(Material.IRON_INGOT));
					
				}
			}
		}, 0, 20*20);
		
		//Gold
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Bewerbung.main, new Runnable() {
			@Override
			public void run() {
				String resource = "gold";
				String mapname = Bewerbung.main.Map;
				int spawners = Bewerbung.main.fm.locationcfg.getInt(mapname + "." +resource+"SpawnerCount");
				
				for(int i = 1; i <= spawners; i++){
					
						World w = Bukkit.getWorld(Bewerbung.main.fm.locationcfg.getString(mapname + ".Spawner."+resource+"." + i + ".World"));
						double x = Bewerbung.main.fm.locationcfg.getDouble(mapname + ".Spawner."+resource+"." + i + ".X");
						double y = Bewerbung.main.fm.locationcfg.getDouble(mapname + ".Spawner."+resource+"." + i + ".Y");
						double z = Bewerbung.main.fm.locationcfg.getDouble(mapname + ".Spawner."+resource+"." + i + ".Z");
						Location spawnerLoc = new Location(w, x, y, z);

					Bukkit.getWorld(spawnerLoc.getWorld().getName()).dropItemNaturally(spawnerLoc, new ItemStack(Material.GOLD_INGOT));
					
				}
			}
		}, 0, 20*60);
	}
	
	
	//Methode by PXAV\\
	public void sendActionbarMessage(final Player player, final String message) {
        final IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer
                .a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
        final PacketPlayOutChat packet = new PacketPlayOutChat(iChatBaseComponent, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
	

	public void respawnPlayer(Player player){
		
		Bukkit.getScheduler().runTaskLater(Bewerbung.main, new Runnable() {
			
			@Override
			public void run() {
				if(Bewerbung.main.TeamBlue.contains(player)){
					player.spigot().respawn();
					player.teleport(Bewerbung.main.fm.getSpawnTeam1(Bewerbung.main.Map));
					player.setHealth(20);
					player.setFoodLevel(20);
				}else{
					player.spigot().respawn();
					player.teleport(Bewerbung.main.fm.getSpawnTeam2(Bewerbung.main.Map));
					player.setHealth(20);
					player.setFoodLevel(20);
				}
			}
		}, 20);
	}
	
	///Level API\\\
	
	public void clearLevel(Player player){
		player.setLevel(0);
	}
	
	public void addLevel(Player player, Integer amount){
		Integer currentLevel = player.getLevel();
		
		player.setLevel(currentLevel + amount);
		sendActionbarMessage(player, "§6§l+§a§l" + amount + " §6§lMaterialien");
	}
	
	public boolean checkLevel(Player player, Integer price){
		Integer currentLevel = player.getLevel();
		
		if(currentLevel < price){
			player.sendMessage(Bewerbung.main.prefix + "§cDu hast nicht genug Materialien!");
			return false;
		}else{
			return true;
		}
	}
	
	public void removeLevel(Player player, Integer amount){
		Integer currentLevel = player.getLevel();
		
		player.setLevel(currentLevel - amount);
		player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
	}
	
	//Level API Done\\
		
	public void createShopInventory(){
			
		ItemStack WeaponStack = new ItemStack(Material.GOLD_SWORD);
		ItemMeta WeaponMeta = WeaponStack.getItemMeta();
		WeaponMeta.setDisplayName("§6Waffen");
		WeaponStack.setItemMeta(WeaponMeta);
		
		ItemStack BowStack = new ItemStack(Material.BOW);
		ItemMeta BowMeta = BowStack.getItemMeta();
		BowMeta.setDisplayName("§6Bögen");
		BowStack.setItemMeta(BowMeta);
		
		ItemStack ArmorStack = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta ArmorMeta = ArmorStack.getItemMeta();
		ArmorMeta.setDisplayName("§6Rüstung");
		ArmorStack.setItemMeta(ArmorMeta);
		
		ItemStack PickaxeStack = new ItemStack(Material.WOOD_PICKAXE);
		ItemMeta PickaxeMeta = PickaxeStack.getItemMeta();
		PickaxeMeta.setDisplayName("§6Spitzhacken");
		PickaxeStack.setItemMeta(PickaxeMeta);
		
		ItemStack BlockStack = new ItemStack(Material.SANDSTONE);
		ItemMeta BlockMeta = BlockStack.getItemMeta();
		BlockMeta.setDisplayName("§6Blöcke");
		BlockStack.setItemMeta(BlockMeta);
		
		@SuppressWarnings("deprecation")
		ItemStack FoodStack = new ItemStack(new ItemStack(393));
		ItemMeta FoodMeta = FoodStack.getItemMeta();
		FoodMeta.setDisplayName("§6Nahrung");
		FoodStack.setItemMeta(FoodMeta);
		
		ItemStack PotionStack = new ItemStack(Material.GLASS_BOTTLE);
		ItemMeta PotionMeta = PotionStack.getItemMeta();
		PotionMeta.setDisplayName("§6Tränke");
		PotionStack.setItemMeta(PotionMeta);

		ItemStack SpecialStack = new ItemStack(Material.FIREWORK);
		ItemMeta SpecialMeta = SpecialStack.getItemMeta();
		SpecialMeta.setDisplayName("§6Spezial");
		SpecialStack.setItemMeta(SpecialMeta);
		
		ItemStack SoonStack = new ItemStack(Material.BARRIER);
		ItemMeta SoonMeta = SoonStack.getItemMeta();
		SoonMeta.setDisplayName("§cComming Soon");
		SoonStack.setItemMeta(SoonMeta);
		
		
		//Weapons\\
		
		
		ItemStack KnockbackStack = new ItemStack(Material.STICK);
		ItemMeta KnockbackMeta = KnockbackStack.getItemMeta();
		KnockbackMeta.setDisplayName("§6Knockback Stick");
		KnockbackMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		ArrayList<String> stickLore = new ArrayList<String>();
		String stickPrice = "§6Preis: §a20 §6Materialien";
		stickLore.add(stickPrice);
		KnockbackMeta.setLore(stickLore);
		KnockbackStack.setItemMeta(KnockbackMeta);
		
		ItemStack Sword1Stack = new ItemStack(Material.GOLD_SWORD);
		ItemMeta Sword1Meta = Sword1Stack.getItemMeta();
		Sword1Meta.setDisplayName("§6Schwert Level §cI");
		Sword1Meta.addEnchant(Enchantment.DURABILITY, 10, true);
		ArrayList<String> sword1Lore = new ArrayList<String>();
		String sword1Price = "§6Preis: §a25 §6Materialien";
		sword1Lore.add(sword1Price);
		Sword1Meta.setLore(sword1Lore);
		Sword1Stack.setItemMeta(Sword1Meta);
		
		ItemStack Sword2Stack = new ItemStack(Material.GOLD_SWORD);
		ItemMeta Sword2Meta = Sword2Stack.getItemMeta();
		Sword2Meta.setDisplayName("§6Schwert Level §cII");
		Sword2Meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		Sword2Meta.addEnchant(Enchantment.DURABILITY, 10, true);
		ArrayList<String> sword2Lore = new ArrayList<String>();
		String sword2Price = "§6Preis: §a50 §6Materialien";
		sword2Lore.add(sword2Price);
		Sword2Meta.setLore(sword2Lore);
		Sword2Stack.setItemMeta(Sword2Meta);
		
		ItemStack Sword3Stack = new ItemStack(Material.GOLD_SWORD);
		ItemMeta Sword3Meta = Sword3Stack.getItemMeta();
		Sword3Meta.setDisplayName("§6Schwert Level §cIII");
		Sword3Meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
		Sword3Meta.addEnchant(Enchantment.DURABILITY, 10, true);
		ArrayList<String> sword3Lore = new ArrayList<String>();
		String sword3Price = "§6Preis: §a100 §6Materialien";
		sword3Lore.add(sword3Price);
		Sword3Meta.setLore(sword3Lore);
		Sword3Stack.setItemMeta(Sword3Meta);	
		
		
		//Bow\\
		
		
		ItemStack Bow1Stack = new ItemStack(Material.BOW);
		ItemMeta Bow1Meta = Bow1Stack.getItemMeta();
		Bow1Meta.setDisplayName("§6Bogen Level §cI");
		ArrayList<String> Bow1Lore = new ArrayList<String>();
		String Bow1Price = "§6Preis: §a150 §6Materialien";
		Bow1Lore.add(Bow1Price);
		Bow1Meta.setLore(Bow1Lore);
		Bow1Stack.setItemMeta(Bow1Meta);
		
		ItemStack Bow2Stack = new ItemStack(Material.BOW);
		ItemMeta Bow2Meta = Bow2Stack.getItemMeta();
		Bow2Meta.setDisplayName("§6Bogen Level §cII");
		Bow2Meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		ArrayList<String> Bow2Lore = new ArrayList<String>();
		String Bow2Price = "§6Preis: §a250 §6Materialien";
		Bow2Lore.add(Bow2Price);
		Bow2Meta.setLore(Bow2Lore);
		Bow2Stack.setItemMeta(Bow2Meta);
		
		ItemStack Bow3Stack = new ItemStack(Material.BOW);
		ItemMeta Bow3Meta = Bow3Stack.getItemMeta();
		Bow3Meta.setDisplayName("§6Bogen Level §cIII");
		Bow3Meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		Bow3Meta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
		ArrayList<String> Bow3Lore = new ArrayList<String>();
		String Bow3Price = "§6Preis: §a450 §6Materialien";
		Bow3Lore.add(Bow3Price);
		Bow3Meta.setLore(Bow3Lore);
		Bow3Stack.setItemMeta(Bow3Meta);
		
		ItemStack ArrowStack = new ItemStack(Material.ARROW, 4);
		ItemMeta ArrowMeta = ArrowStack.getItemMeta();
		ArrowMeta.setDisplayName("§6Pfeil");
		ArrayList<String> ArrowLore = new ArrayList<String>();
		String ArrowPrice = "§6Preis: §a25 §6Materialien";
		ArrowLore.add(ArrowPrice);
		ArrowMeta.setLore(ArrowLore);
		ArrowStack.setItemMeta(ArrowMeta);
		
		
		//Armor\\
		
		
		ItemStack HelmetStack = new ItemStack(Material.LEATHER_HELMET);
		ItemMeta HelmetMeta = HelmetStack.getItemMeta();
		HelmetMeta.setDisplayName("§6Leder Helm");
		HelmetMeta.addEnchant(Enchantment.DURABILITY, 3, true);
		ArrayList<String> HelmetLore = new ArrayList<String>();
		String HelmetPrice = "§6Preis: §a5 §6Materialien";
		HelmetLore.add(HelmetPrice);
		HelmetMeta.setLore(HelmetLore);
		HelmetStack.setItemMeta(HelmetMeta);
		
		ItemStack LeggingsStack = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemMeta LeggingsMeta = LeggingsStack.getItemMeta();
		LeggingsMeta.setDisplayName("§6Leder Hose");
		LeggingsMeta.addEnchant(Enchantment.DURABILITY, 3, true);
		ArrayList<String> LeggingsLore = new ArrayList<String>();
		String LeggingsPrice = "§6Preis: §a5 §6Materialien";
		LeggingsLore.add(LeggingsPrice);
		LeggingsMeta.setLore(LeggingsLore);
		LeggingsStack.setItemMeta(LeggingsMeta);
		
		ItemStack BootsStack = new ItemStack(Material.LEATHER_BOOTS);
		ItemMeta BootsMeta = BootsStack.getItemMeta();
		BootsMeta.setDisplayName("§6Leder Schuhe");
		BootsMeta.addEnchant(Enchantment.DURABILITY, 3, true);
		ArrayList<String> BootsLore = new ArrayList<String>();
		String BootsPrice = "§6Preis: §a5 §6Materialien";
		BootsLore.add(BootsPrice);
		BootsMeta.setLore(BootsLore);
		BootsStack.setItemMeta(BootsMeta);
		
		ItemStack Chestplate1Stack = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta Chestplate1Meta = Chestplate1Stack.getItemMeta();
		Chestplate1Meta.setDisplayName("§6Brustplatte Level §cI");
		Chestplate1Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		Chestplate1Meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
		ArrayList<String> Chestplate1Lore = new ArrayList<String>();
		String Chestplate1Price = "§6Preis: §a50 §6Materialien";
		Chestplate1Lore.add(Chestplate1Price);
		Chestplate1Meta.setLore(Chestplate1Lore);
		Chestplate1Stack.setItemMeta(Chestplate1Meta);
		
		ItemStack Chestplate2Stack = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta Chestplate2Meta = Chestplate2Stack.getItemMeta();
		Chestplate2Meta.setDisplayName("§6Brustplatte Level §cII");
		Chestplate2Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		Chestplate2Meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 2, true);
		ArrayList<String> Chestplate2Lore = new ArrayList<String>();
		String Chestplate2Price = "§6Preis: §a150 §6Materialien";
		Chestplate2Lore.add(Chestplate2Price);
		Chestplate2Meta.setLore(Chestplate2Lore);
		Chestplate2Stack.setItemMeta(Chestplate2Meta);
		
		ItemStack Chestplate3Stack = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta Chestplate3Meta = Chestplate3Stack.getItemMeta();
		Chestplate3Meta.setDisplayName("§6Brustplatte Level §cIII");
		Chestplate3Meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
		Chestplate3Meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
		ArrayList<String> Chestplate3Lore = new ArrayList<String>();
		String Chestplate3Price = "§6Preis: §a300 §6Materialien";
		Chestplate3Lore.add(Chestplate3Price);
		Chestplate3Meta.setLore(Chestplate3Lore);
		Chestplate3Stack.setItemMeta(Chestplate3Meta);
		
		
		//Pickaxe\\
		
		
		ItemStack Pickaxe1Stack = new ItemStack(Material.WOOD_PICKAXE);
		ItemMeta Pickaxe1Meta = Pickaxe1Stack.getItemMeta();
		Pickaxe1Meta.setDisplayName("§6Spitzhacke Level §cI");
		Pickaxe1Meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
		Pickaxe1Meta.addEnchant(Enchantment.DURABILITY, 10, true);
		ArrayList<String> Pickaxe1Lore = new ArrayList<String>();
		String Pickaxe1Price = "§6Preis: §a25 §6Materialien";
		Pickaxe1Lore.add(Pickaxe1Price);
		Pickaxe1Meta.setLore(Pickaxe1Lore);
		Pickaxe1Stack.setItemMeta(Pickaxe1Meta);
		
		ItemStack Pickaxe2Stack = new ItemStack(Material.STONE_PICKAXE);
		ItemMeta Pickaxe2Meta = Pickaxe2Stack.getItemMeta();
		Pickaxe2Meta.setDisplayName("§6Spitzhacke Level §cII");
		Pickaxe2Meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
		Pickaxe2Meta.addEnchant(Enchantment.DURABILITY, 10, true);
		ArrayList<String> Pickaxe2Lore = new ArrayList<String>();
		String Pickaxe2Price = "§6Preis: §a50 §6Materialien";
		Pickaxe2Lore.add(Pickaxe2Price);
		Pickaxe2Meta.setLore(Pickaxe2Lore);
		Pickaxe2Stack.setItemMeta(Pickaxe2Meta);
		
		ItemStack Pickaxe3Stack = new ItemStack(Material.GOLD_PICKAXE);
		ItemMeta Pickaxe3Meta = Pickaxe3Stack.getItemMeta();
		Pickaxe3Meta.setDisplayName("§6Spitzhacke Level §cIII");
		Pickaxe3Meta.addEnchant(Enchantment.DIG_SPEED, 2, true);
		Pickaxe3Meta.addEnchant(Enchantment.DURABILITY, 10, true);
		ArrayList<String> Pickaxe3Lore = new ArrayList<String>();
		String Pickaxe3Price = "§6Preis: §a100 §6Materialien";
		Pickaxe3Lore.add(Pickaxe3Price);
		Pickaxe3Meta.setLore(Pickaxe3Lore);
		Pickaxe3Stack.setItemMeta(Pickaxe3Meta);
		
		
		//Blocks\\
		
		
		ItemStack SandstoneStack = new ItemStack(Material.SANDSTONE, 4);
		ItemMeta SandstoneMeta = SandstoneStack.getItemMeta();
		SandstoneMeta.setDisplayName("§6Sandstein");
		ArrayList<String> SandstoneLore = new ArrayList<String>();
		String SandstonePrice = "§6Preis: §a3 §6Materialien";
		SandstoneLore.add(SandstonePrice);
		SandstoneMeta.setLore(SandstoneLore);
		SandstoneStack.setItemMeta(SandstoneMeta);
		
		ItemStack SandstoneStairStack = new ItemStack(Material.SANDSTONE_STAIRS);
		ItemMeta SandstoneStairMeta = SandstoneStairStack.getItemMeta();
		SandstoneStairMeta.setDisplayName("§6Sandstein Treppe");
		ArrayList<String> SandstoneStairLore = new ArrayList<String>();
		String SandstoneStairPrice = "§6Preis: §a3 §6Materialien";
		SandstoneStairLore.add(SandstoneStairPrice);
		SandstoneStairMeta.setLore(SandstoneStairLore);
		SandstoneStairStack.setItemMeta(SandstoneStairMeta);
		
		ItemStack EndstoneStack = new ItemStack(Material.ENDER_STONE, 2);
		ItemMeta EndstoneMeta = EndstoneStack.getItemMeta();
		EndstoneMeta.setDisplayName("§6Endstein");
		ArrayList<String> EndstoneLore = new ArrayList<String>();
		String EndstonePrice = "§6Preis: §a8 §6Materialien";
		EndstoneLore.add(EndstonePrice);
		EndstoneMeta.setLore(EndstoneLore);
		EndstoneStack.setItemMeta(EndstoneMeta);
		
		ItemStack GlasStack = new ItemStack(Material.GLASS);
		ItemMeta GlasMeta = GlasStack.getItemMeta();
		GlasMeta.setDisplayName("§6Glas");
		ArrayList<String> GlasLore = new ArrayList<String>();
		String GlasPrice = "§6Preis: §a3 §6Materialien";
		GlasLore.add(GlasPrice);
		GlasMeta.setLore(GlasLore);
		GlasStack.setItemMeta(GlasMeta);
		
		ItemStack LadderStack = new ItemStack(Material.LADDER);
		ItemMeta LadderMeta = LadderStack.getItemMeta();
		LadderMeta.setDisplayName("§6Leiter");
		ArrayList<String> LadderLore = new ArrayList<String>();
		String LadderPrice = "§6Preis: §a5 §6Materialien";
		LadderLore.add(LadderPrice);
		LadderMeta.setLore(LadderLore);
		LadderStack.setItemMeta(LadderMeta);
		
		ItemStack CobwebStack = new ItemStack(Material.WEB);
		ItemMeta CobwebMeta = CobwebStack.getItemMeta();
		CobwebMeta.setDisplayName("§6Spinnenweben");
		ArrayList<String> CobwebLore = new ArrayList<String>();
		String CobwebPrice = "§6Preis: §a15 §6Materialien";
		CobwebLore.add(CobwebPrice);
		CobwebMeta.setLore(CobwebLore);
		CobwebStack.setItemMeta(CobwebMeta);
		
		
		
		//Food\\
		
		
		ItemStack AppleStack = new ItemStack(Material.APPLE, 3);
		ItemMeta AppleMeta = AppleStack.getItemMeta();
		AppleMeta.setDisplayName("§6Apfel");
		ArrayList<String> AppleLore = new ArrayList<String>();
		String ApplePrice = "§6Preis: §a2 §6Materialien";
		AppleLore.add(ApplePrice);
		AppleMeta.setLore(AppleLore);
		AppleStack.setItemMeta(AppleMeta);
		
		@SuppressWarnings("deprecation")
		ItemStack CarrotStack = new ItemStack(391);
		ItemMeta CarrotMeta = CarrotStack.getItemMeta();
		CarrotMeta.setDisplayName("§6Karotte");
		ArrayList<String> CarrotLore = new ArrayList<String>();
		String CarrotPrice = "§6Preis: §a1 §6Materialien";
		CarrotLore.add(CarrotPrice);
		CarrotMeta.setLore(CarrotLore);
		CarrotStack.setItemMeta(CarrotMeta);
		
		ItemStack MelonStack = new ItemStack(Material.MELON, 3);
		ItemMeta MelonMeta = MelonStack.getItemMeta();
		MelonMeta.setDisplayName("§6Melone");
		ArrayList<String> MelonLore = new ArrayList<String>();
		String MelonPrice = "§6Preis: §a2 §6Materialien";
		MelonLore.add(MelonPrice);
		MelonMeta.setLore(MelonLore);
		MelonStack.setItemMeta(MelonMeta);
		
		ItemStack PotatoStack = new ItemStack(Material.BAKED_POTATO);
		ItemMeta PotatoMeta = PotatoStack.getItemMeta();
		PotatoMeta.setDisplayName("§6Kartoffel");
		ArrayList<String> PotatoLore = new ArrayList<String>();
		String PotatoPrice = "§6Preis: §a2 §6Materialien";
		PotatoLore.add(PotatoPrice);
		PotatoMeta.setLore(PotatoLore);
		PotatoStack.setItemMeta(PotatoMeta);
		
		ItemStack BeefStack = new ItemStack(Material.COOKED_BEEF);
		ItemMeta BeefMeta = BeefStack.getItemMeta();
		BeefMeta.setDisplayName("§6Gebratenes Schweinefleisch");
		ArrayList<String> BeefLore = new ArrayList<String>();
		String BeefPrice = "§6Preis: §a2 §6Materialien";
		BeefLore.add(BeefPrice);
		BeefMeta.setLore(BeefLore);
		BeefStack.setItemMeta(BeefMeta);
		
		ItemStack GoldenAppleStack = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta GoldenAppleMeta = GoldenAppleStack.getItemMeta();
		GoldenAppleMeta.setDisplayName("§6Goldener Apfel");
		ArrayList<String> GoldenAppleLore = new ArrayList<String>();
		String GoldenApplePrice = "§6Preis: §a70 §6Materialien";
		GoldenAppleLore.add(GoldenApplePrice);
		GoldenAppleMeta.setLore(GoldenAppleLore);
		GoldenAppleStack.setItemMeta(GoldenAppleMeta);
		
		//Items in Inventare setzen\\
		
		shopInv.setItem(0, WeaponStack);
		shopInv.setItem(1, BowStack);
		shopInv.setItem(2, ArmorStack);
		shopInv.setItem(3, PickaxeStack);
		shopInv.setItem(4, BlockStack);
		shopInv.setItem(5, FoodStack);
		shopInv.setItem(6, SoonStack);
		shopInv.setItem(7, SoonStack);
		shopInv.setItem(8, SoonStack);
	
		
		
		shopInvWeapon.setItem(0, WeaponStack);
		shopInvWeapon.setItem(1, BowStack);
		shopInvWeapon.setItem(2, ArmorStack);
		shopInvWeapon.setItem(3, PickaxeStack);
		shopInvWeapon.setItem(4, BlockStack);
		shopInvWeapon.setItem(5, FoodStack);
		shopInvWeapon.setItem(6, SoonStack);
		shopInvWeapon.setItem(7, SoonStack);
		shopInvWeapon.setItem(8, SoonStack);
		
		shopInvWeapon.setItem(10, KnockbackStack);
		shopInvWeapon.setItem(12, Sword1Stack);
		shopInvWeapon.setItem(13, Sword2Stack);
		shopInvWeapon.setItem(14, Sword3Stack);
		
		
		
		shopInvBow.setItem(0, WeaponStack);
		shopInvBow.setItem(1, BowStack);
		shopInvBow.setItem(2, ArmorStack);
		shopInvBow.setItem(3, PickaxeStack);
		shopInvBow.setItem(4, BlockStack);
		shopInvBow.setItem(5, FoodStack);
		shopInvBow.setItem(6, SoonStack);
		shopInvBow.setItem(7, SoonStack);
		shopInvBow.setItem(8, SoonStack);
		
		shopInvBow.setItem(9, Bow1Stack);
		shopInvBow.setItem(10, Bow2Stack);
		shopInvBow.setItem(11, Bow3Stack);
		shopInvBow.setItem(17, ArrowStack);
		
		
		
		shopInvArmor.setItem(0, WeaponStack);
		shopInvArmor.setItem(1, BowStack);
		shopInvArmor.setItem(2, ArmorStack);
		shopInvArmor.setItem(3, PickaxeStack);
		shopInvArmor.setItem(4, BlockStack);
		shopInvArmor.setItem(5, FoodStack);
		shopInvArmor.setItem(6, SoonStack);
		shopInvArmor.setItem(7, SoonStack);
		shopInvArmor.setItem(8, SoonStack);
		
		shopInvArmor.setItem(10, HelmetStack);
		shopInvArmor.setItem(11, LeggingsStack);
		shopInvArmor.setItem(12, BootsStack);
		shopInvArmor.setItem(14, Chestplate1Stack);
		shopInvArmor.setItem(15, Chestplate2Stack);
		shopInvArmor.setItem(16, Chestplate3Stack);
		
		
		
		shopInvPickaxe.setItem(0, WeaponStack);
		shopInvPickaxe.setItem(1, BowStack);
		shopInvPickaxe.setItem(2, ArmorStack);
		shopInvPickaxe.setItem(3, PickaxeStack);
		shopInvPickaxe.setItem(4, BlockStack);
		shopInvPickaxe.setItem(5, FoodStack);
		shopInvPickaxe.setItem(6, SoonStack);
		shopInvPickaxe.setItem(7, SoonStack);
		shopInvPickaxe.setItem(8, SoonStack);
		
		shopInvPickaxe.setItem(12, Pickaxe1Stack);
		shopInvPickaxe.setItem(13, Pickaxe2Stack);
		shopInvPickaxe.setItem(14, Pickaxe3Stack);
		
		
		
		shopInvBlocks.setItem(0, WeaponStack);
		shopInvBlocks.setItem(1, BowStack);
		shopInvBlocks.setItem(2, ArmorStack);
		shopInvBlocks.setItem(3, PickaxeStack);
		shopInvBlocks.setItem(4, BlockStack);
		shopInvBlocks.setItem(5, FoodStack);
		shopInvBlocks.setItem(6, SoonStack);
		shopInvBlocks.setItem(7, SoonStack);
		shopInvBlocks.setItem(8, SoonStack);
		
		shopInvBlocks.setItem(9, SandstoneStack);
		shopInvBlocks.setItem(10, SandstoneStairStack);
		shopInvBlocks.setItem(11, EndstoneStack);
		shopInvBlocks.setItem(12, GlasStack);
		shopInvBlocks.setItem(16, LadderStack);
		shopInvBlocks.setItem(17, CobwebStack);
		
		
		
		shopInvFood.setItem(0, WeaponStack);
		shopInvFood.setItem(1, BowStack);
		shopInvFood.setItem(2, ArmorStack);
		shopInvFood.setItem(3, PickaxeStack);
		shopInvFood.setItem(4, BlockStack);
		shopInvFood.setItem(5, FoodStack);
		shopInvFood.setItem(6, SoonStack);
		shopInvFood.setItem(7, SoonStack);
		shopInvFood.setItem(8, SoonStack);
		
		shopInvFood.setItem(10, AppleStack);
		shopInvFood.setItem(11, CarrotStack);
		shopInvFood.setItem(12, MelonStack);
		shopInvFood.setItem(14, PotatoStack);
		shopInvFood.setItem(15, BeefStack);
		shopInvFood.setItem(16, GoldenAppleStack);
		
	}
	
	public void openShopMainPage(Player player){
		
		createShopInventory();
		player.openInventory(shopInv);
		player.updateInventory();
		player.playSound(player.getLocation(), Sound.DIG_STONE, 2.0F, 2.0F);
		
	}
	
	public void openShopWeaponPage(Player player){
		
		createShopInventory();
		player.openInventory(shopInvWeapon);
		player.updateInventory();
		player.playSound(player.getLocation(), Sound.DIG_STONE, 2.0F, 2.0F);
		
	}
	
	public void openShopBowPage(Player player){
		
		createShopInventory();
		player.openInventory(shopInvBow);
		player.updateInventory();
		player.playSound(player.getLocation(), Sound.DIG_STONE, 2.0F, 2.0F);
		
	}
	
	public void openShopArmorPage(Player player){
		
		createShopInventory();
		player.openInventory(shopInvArmor);
		player.updateInventory();
		player.playSound(player.getLocation(), Sound.DIG_STONE, 2.0F, 2.0F);
		
	}
	
	public void openShopPickaxePage(Player player){
		
		createShopInventory();
		player.openInventory(shopInvPickaxe);
		player.updateInventory();
		player.playSound(player.getLocation(), Sound.DIG_STONE, 2.0F, 2.0F);
		
	}
	
	public void openShopBlockPage(Player player){
		
		createShopInventory();
		player.openInventory(shopInvBlocks);
		player.updateInventory();
		player.playSound(player.getLocation(), Sound.DIG_STONE, 2.0F, 2.0F);
		
	}
	
	public void openShopFoodPage(Player player){
		
		createShopInventory();
		player.openInventory(shopInvFood);
		player.updateInventory();
		player.playSound(player.getLocation(), Sound.DIG_STONE, 2.0F, 2.0F);
		
	}
}
