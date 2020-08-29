package de.doppelbemme.bewerbung.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.doppelbemme.bewerbung.main.Bewerbung;
import de.doppelbemme.bewerbung.mysql.MySQL;

public class Setup_Command implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		if(!(sender instanceof Player)){
			sender.sendMessage(Bewerbung.main.NoConsoleAllowed);
		}else{
			Player player = (Player)sender;

			if(!player.hasPermission("cakewars.setup")){
				player.sendMessage(Bewerbung.main.NoPermission);
			}else{
				if(args.length == 0){
					Bewerbung.sendSetupMessage(player);
				}else if(args.length == 1){
					String mapname = args[0];
					if(Bewerbung.main.fm.locationcfg.contains(mapname)){
						player.sendMessage(Bewerbung.main.prefix + "§cEine Karte mit dem Namen §e" + mapname + " §cexistiert bereits!");
					}else{
						Location playerLoc = player.getLocation();
							Bewerbung.main.fm.locationcfg.set(mapname + ".world", playerLoc.getWorld().getName());
							if(!Bewerbung.main.fm.locationcfg.contains("Maps")){
								Bewerbung.main.fm.locationcfg.set("Maps", mapname+", ");
								Bewerbung.main.fm.saveLocationCfg();
							}else{
								String oldMaps = Bewerbung.main.fm.locationcfg.getString("Maps");
								Bewerbung.main.fm.locationcfg.set("Maps", oldMaps + mapname);
								Bewerbung.main.fm.saveLocationCfg();
							}
							Bewerbung.main.fm.saveLocationCfg();
							player.sendMessage(Bewerbung.main.prefix + "§aDie Map §e" + mapname + " §awurde erfolgreich erstellt!");
						}
					}
				}
			}
		return false;
	}
	
}
