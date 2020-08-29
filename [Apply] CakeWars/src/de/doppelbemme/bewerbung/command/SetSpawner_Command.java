package de.doppelbemme.bewerbung.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.doppelbemme.bewerbung.main.Bewerbung;

public class SetSpawner_Command implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		if(!(sender instanceof Player)){
			sender.sendMessage(Bewerbung.main.NoConsoleAllowed);
		}else{
			Player player = (Player)sender;
			if(!player.hasPermission("cakewars.setup")){
				player.sendMessage(Bewerbung.main.NoPermission);
			}else{
				if(args.length != 2){
					Bewerbung.sendSetupMessage(player);
				}else{
					Location playerLoc = player.getLocation();
					String resource = args[0].toLowerCase();
					String mapname = args[1];
				
					if(!Bewerbung.main.fm.locationcfg.contains(mapname)){
						player.sendMessage(Bewerbung.main.prefix + "§cDie Map §e" + mapname + " §ckonnte nicht gefunden werden!");
					}else{
					if(resource.equalsIgnoreCase("bronze") || resource.equalsIgnoreCase("iron") || resource.equalsIgnoreCase("gold")){
						Bewerbung.main.fm.addSpawner(player, playerLoc, mapname, resource);
					}else{
						player.sendMessage(Bewerbung.main.prefix + "§7Es gibt folgende Resourcen: §ebronze§7, §eiron§7, §egold");
					}
				}
			}
		}	
	}
		return false;
}
}
