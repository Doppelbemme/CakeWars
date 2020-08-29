package de.doppelbemme.bewerbung.command;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.doppelbemme.bewerbung.main.Bewerbung;

public class SetLobby_Command implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		if(!(sender instanceof Player)){
			sender.sendMessage(Bewerbung.main.NoConsoleAllowed);
		}else{
			Player player = (Player)sender;
			if(!player.hasPermission("cakewars.setup")){
				player.sendMessage(Bewerbung.main.NoPermission);
			}else{
				Location PlayerLoc = player.getLocation(); 
				Bewerbung.main.fm.setSpawn(PlayerLoc);
				player.sendMessage(Bewerbung.main.prefix + "§aDer §eLobby Spawn §awurde erfolgreich gesetzt. §7(§e" + PlayerLoc.getBlockX() + "§7/§e" + PlayerLoc.getBlockY() + "§7/§e" + PlayerLoc.getBlockZ() + "§7)");
				player.playSound(PlayerLoc, Sound.ORB_PICKUP, 1.0F, 1.0F);
			}
		}
		
		return false;
	}
}
