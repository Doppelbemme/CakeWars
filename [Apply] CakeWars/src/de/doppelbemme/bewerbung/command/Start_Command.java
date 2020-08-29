package de.doppelbemme.bewerbung.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.doppelbemme.bewerbung.main.Bewerbung;

public class Start_Command implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		if(!(sender instanceof Player)){
			sender.sendMessage(Bewerbung.main.NoConsoleAllowed);
		}else{
			Player player = (Player)sender;
			if(!player.hasPermission("cakewars.start")){
				player.sendMessage(Bewerbung.main.NoPermission);
			}else{
				Bewerbung.main.utils.startRound(player);
			}
		}
		return false;
	}
}
