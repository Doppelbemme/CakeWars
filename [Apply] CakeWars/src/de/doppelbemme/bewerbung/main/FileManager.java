package de.doppelbemme.bewerbung.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class FileManager {
	
	File locationfile = new File("plugins/CakeWars", "locations.yml");
	public FileConfiguration locationcfg = YamlConfiguration.loadConfiguration(locationfile);

	public void saveLocationCfg() {
		try {
			locationcfg.save(locationfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Lobby \/
	
	public void setSpawn(Location loc) {
		String name = "Lobby";
		locationcfg.set(name+".world", loc.getWorld().getName());
		locationcfg.set(name+".x", loc.getX());
		locationcfg.set(name+".y", loc.getY());
		locationcfg.set(name+".z", loc.getZ());
		
		locationcfg.set(name+".yaw", loc.getYaw());
		locationcfg.set(name+".pitch", loc.getPitch());
		saveLocationCfg();
	}
	
	public Location getSpawn() {
		String name = "Lobby";
		World w = Bukkit.getWorld(locationcfg.getString(name+".world"));
		double x = locationcfg.getDouble(name+".x");
		double y = locationcfg.getDouble(name+".y");
		double z = locationcfg.getDouble(name+".z");
		Location loc = new Location(w, x, y, z);
		loc.setYaw(locationcfg.getInt(name+".yaw"));
		loc.setPitch(locationcfg.getInt(name+".pitch"));
		return loc;
	}
	
	//Lobby /\
	//Kuchen Team 1 \/
	
	public void setCakeTeam1(Location loc, String mapname) {
		locationcfg.set(mapname+".cake1.world", loc.getWorld().getName());
		locationcfg.set(mapname+".cake1.x", loc.getX());
		locationcfg.set(mapname+".cake1.y", loc.getY());
		locationcfg.set(mapname+".cake1.z", loc.getZ());

		saveLocationCfg();
	}
	
	
	public Location getCakeTeam1(String mapname) {
		World w = Bukkit.getWorld(locationcfg.getString(mapname+".cake1.world"));
		double x = locationcfg.getDouble(mapname+".cake1.x");
		double y = locationcfg.getDouble(mapname+".cake1.y");
		double z = locationcfg.getDouble(mapname+".cake1.z");
		Location loc = new Location(w, x, y, z);
		return loc;
	}
	
	//Kuchen Team 1 /\
	//Kuchen Team 2 \/
	public void setCakeTeam2(Location loc, String mapname) {
		locationcfg.set(mapname+".cake2.world", loc.getWorld().getName());
		locationcfg.set(mapname+".cake2.x", loc.getX());
		locationcfg.set(mapname+".cake2.y", loc.getY());
		locationcfg.set(mapname+".cake2.z", loc.getZ());

		saveLocationCfg();
	}
	
	public Location getCakeTeam2(String mapname) {
		World w = Bukkit.getWorld(locationcfg.getString(mapname+".cake1.world"));
		double x = locationcfg.getDouble(mapname+".cake2.x");
		double y = locationcfg.getDouble(mapname+".cake2.y");
		double z = locationcfg.getDouble(mapname+".cake2.z");
		Location loc = new Location(w, x, y, z);
		return loc;
	}
	
	//Kuchen Team 2 /\
	
	//Team Spawn 1 \/
	
	public void setSpawnTeam1(Location loc, String mapname) {
		locationcfg.set(mapname+".spawn1.world", loc.getWorld().getName());
		locationcfg.set(mapname+".spawn1.x", loc.getX());
		locationcfg.set(mapname+".spawn1.y", loc.getY());
		locationcfg.set(mapname+".spawn1.z", loc.getZ());
		
		locationcfg.set(mapname+".spawn1.yaw", loc.getYaw());
		locationcfg.set(mapname+".spawn1.pitch", loc.getPitch());
		saveLocationCfg();
	}
	
	public Location getSpawnTeam1(String mapname) {
		World w = Bukkit.getWorld(locationcfg.getString(mapname+".spawn1.world"));
		double x = locationcfg.getDouble(mapname+".spawn1.x");
		double y = locationcfg.getDouble(mapname+".spawn1.y");
		double z = locationcfg.getDouble(mapname+".spawn1.z");
		Location loc = new Location(w, x, y, z);
		loc.setYaw(locationcfg.getInt(mapname+".spawn1.yaw"));
		loc.setPitch(locationcfg.getInt(mapname+".spawn1.pitch"));
		return loc;
	}
	
	//Team Spawn 1 /\
	//Team Spawn 2 \/
	
	public void setSpawnTeam2(Location loc, String mapname) {
		locationcfg.set(mapname+".spawn2.world", loc.getWorld().getName());
		locationcfg.set(mapname+".spawn2.x", loc.getX());
		locationcfg.set(mapname+".spawn2.y", loc.getY());
		locationcfg.set(mapname+".spawn2.z", loc.getZ());
		
		locationcfg.set(mapname+".spawn2.yaw", loc.getYaw());
		locationcfg.set(mapname+".spawn2.pitch", loc.getPitch());
		saveLocationCfg();
	}
	
	public Location getSpawnTeam2(String mapname) {
		World w = Bukkit.getWorld(locationcfg.getString(mapname+".spawn2.world"));
		double x = locationcfg.getDouble(mapname+".spawn2.x");
		double y = locationcfg.getDouble(mapname+".spawn2.y");
		double z = locationcfg.getDouble(mapname+".spawn2.z");
		Location loc = new Location(w, x, y, z);
		loc.setYaw(locationcfg.getInt(mapname+".spawn2.yaw"));
		loc.setPitch(locationcfg.getInt(mapname+".spawn2.pitch"));
		return loc;
	}
	
	//Team Spawn 2 /\
	
	public void register() {
		locationcfg.options().copyDefaults(true);
		saveLocationCfg();
	}
	
	public void addSpawner(Player player, Location playerLocation, String mapname, String resource){
		int spawnerCount;
		try{
		spawnerCount = Bewerbung.main.fm.locationcfg.getInt(mapname + "." +resource+"SpawnerCount");
		}catch(Exception exception){
			locationcfg.set(mapname + "." +resource+"SpawnerCount", 0);
			spawnerCount = 0;
			
			
			locationcfg.set(mapname + ".Spawner."+resource+"." + spawnerCount + ".World", playerLocation.getWorld().getName());
			locationcfg.set(mapname + ".Spawner."+resource+"." + spawnerCount + ".X", playerLocation.getX());
			locationcfg.set(mapname + ".Spawner."+resource+"." + spawnerCount + ".Y", playerLocation.getY());
			locationcfg.set(mapname + ".Spawner."+resource+"." + spawnerCount + ".Z", playerLocation.getZ());
			
			player.sendMessage(Bewerbung.main.prefix + "§7Du hast einen §e" + resource + " Spawner §7mit der §eID " + spawnerCount + " §7auf der Map §e" + mapname + " §7erstellt!");
			player.playSound(playerLocation, Sound.ORB_PICKUP, 1.0F, 1.0F);
			saveLocationCfg();
		}
		int newSpawnerCount = spawnerCount + 1;
		locationcfg.set(mapname + "." +resource+"SpawnerCount", newSpawnerCount);

		locationcfg.set(mapname + ".Spawner."+resource+"." + newSpawnerCount + ".World", playerLocation.getWorld().getName());
		locationcfg.set(mapname + ".Spawner."+resource+"." + newSpawnerCount + ".X", playerLocation.getX());
		locationcfg.set(mapname + ".Spawner."+resource+"." + newSpawnerCount + ".Y", playerLocation.getY());
		locationcfg.set(mapname + ".Spawner."+resource+"." + newSpawnerCount + ".Z", playerLocation.getZ());
		
		player.sendMessage(Bewerbung.main.prefix + "§7Du hast einen §e" + resource + " Spawner §7mit der §eID " + newSpawnerCount + " §7auf der Map §e" + mapname + " §7erstellt!");
		player.playSound(playerLocation, Sound.ORB_PICKUP, 1.0F, 1.0F);
		saveLocationCfg();
	}
	
}