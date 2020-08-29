package de.doppelbemme.bewerbung.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.doppelbemme.bewerbung.main.Bewerbung;

public class ForceMap_Command implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		if(!(sender instanceof Player)){
			sender.sendMessage(Bewerbung.main.NoConsoleAllowed);
		}else{
			Player player = (Player)sender;
			if(!player.hasPermission("cakewars.forcemap")){
				player.sendMessage(Bewerbung.main.NoPermission);
			}else{
				if(args.length != 1){
					player.sendMessage(Bewerbung.main.prefix + "§cNutze: §7/§eforcemap §7<§eMap-Name§7>");
					player.sendMessage(Bewerbung.main.prefix + "§7Es existieren folgende Maps: §e" + Bewerbung.main.fm.locationcfg.getString("Maps"));
				}else{
					if(Bewerbung.main.lobby <= 10 || Bewerbung.main.mapChangeAvailable == false){
						player.sendMessage(Bewerbung.main.prefix + "§cDie Map kann diese Runde nicht mehr geändert werden!");
					}else{
				String mapname = args[0];
				if(!Bewerbung.main.fm.locationcfg.contains(mapname)){
					player.sendMessage(Bewerbung.main.prefix + "§cDiese Map existiert nicht!");
					player.sendMessage(Bewerbung.main.prefix + "§7Es existieren folgende Maps: §e" + Bewerbung.main.fm.locationcfg.getString("Maps"));
				}else{
					Bewerbung.main.Map = mapname;
					player.sendMessage(Bewerbung.main.prefix + "§aDie Map wurde auf §e" + mapname + " §ageändert!");
					player.playSound(player.getLocation(), Sound.LEVEL_UP, 2.0F, 2.0F);
					Bewerbung.main.mapChangeAvailable = false;
					}
					}
				}
			}
		}
		
		return false;
	}
}
