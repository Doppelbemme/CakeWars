package de.doppelbemme.bewerbung.command;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.doppelbemme.bewerbung.main.Bewerbung;

public class Build_Command implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		String BuildOn = Bewerbung.main.prefix + "§aDu kannst nun bauen!";
		String BuildOf = Bewerbung.main.prefix + "§cDu kannst nicht mehr bauen!";
		
		if(!(sender instanceof Player)){
			sender.sendMessage(Bewerbung.main.NoConsoleAllowed);
		}else{
			Player player = (Player)sender;
			
			if(!player.hasPermission("cakewars.build")){
				player.sendMessage(Bewerbung.main.NoPermission);
			}else{
				if(args.length == 0){
					if(!Bewerbung.BuilderList.contains(player)){
						Bewerbung.BuilderList.add(player);
						player.setGameMode(GameMode.CREATIVE);
						player.sendMessage(BuildOn);
					}else{
						Bewerbung.BuilderList.remove(player);
						player.setGameMode(GameMode.SURVIVAL);
						player.sendMessage(BuildOf);
					}
				}else if(args.length == 1){
					Player target = Bukkit.getPlayer(args[0]);
					if(target == null){
						player.sendMessage(Bewerbung.main.prefix + "§cDieser Spieler ist nicht online!");
					}else{
						if(!Bewerbung.BuilderList.contains(target)){
							Bewerbung.BuilderList.add(target);
							target.setGameMode(GameMode.CREATIVE);
							target.sendMessage(BuildOn);
							player.sendMessage(Bewerbung.main.prefix + "§aDu hast das bauen für den Spieler §e" + target.getName() + " §aaktiviert!");
						}else{
							Bewerbung.BuilderList.remove(target);
							player.setGameMode(GameMode.SURVIVAL);
							target.sendMessage(BuildOf);
							player.sendMessage(Bewerbung.main.prefix + "§cDu hast das bauen für den Spieler §e" + target.getName() + " §cdeaktiviert!");
						}
					}
				}else{
					player.sendMessage(Bewerbung.main.prefix + "§cNutze: §7/§ebuild §7(§eName§7)");
				}
			}
		}
		
		return false;
	}
}
