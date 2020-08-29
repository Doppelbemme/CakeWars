package de.doppelbemme.bewerbung.command;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.doppelbemme.bewerbung.main.Bewerbung;

public class SetCake_Command implements CommandExecutor {
	
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
					Location PlayerLoc = player.getLocation();
					String teamNumber = args[0];
					String mapname = args[1];
				
					if(!Bewerbung.main.fm.locationcfg.contains(mapname)){
						player.sendMessage(Bewerbung.main.prefix + "§cDie Map §e" + mapname + " §ckonnte nicht gefunden werden!");
					}else{
				if(teamNumber.equalsIgnoreCase("1")){
					Bewerbung.main.fm.setCakeTeam1(player.getLocation(), mapname);
					player.sendMessage(Bewerbung.main.prefix + "§aDer §eKuchen §afür §eTeam 1 §aauf der Map §e" + mapname + " §awurde erfolgreich gesetzt.");
					player.playSound(PlayerLoc, Sound.ORB_PICKUP, 1.0F, 1.0F);
				}else if(teamNumber.equalsIgnoreCase("2")){
					Bewerbung.main.fm.setCakeTeam2(player.getLocation(), mapname);
					player.sendMessage(Bewerbung.main.prefix + "§aDer §eKuchen §afür §eTeam 2 §aauf der Map §e" + mapname + " §awurde erfolgreich gesetzt.");
					player.playSound(PlayerLoc, Sound.ORB_PICKUP, 1.0F, 1.0F);
				}else{
					Bewerbung.sendSetupMessage(player);
					}
				}
				}
			}
		}
		
		return false;
	}
}
