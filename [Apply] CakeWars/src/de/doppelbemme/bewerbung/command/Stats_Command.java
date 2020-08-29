package de.doppelbemme.bewerbung.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.doppelbemme.bewerbung.main.Bewerbung;
import de.doppelbemme.bewerbung.mysql.MySQLStats;

public class Stats_Command implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		if(!(sender instanceof Player)){
			sender.sendMessage(Bewerbung.main.NoConsoleAllowed);
		}else{
			Player player = (Player)sender;
			
			if(args.length == 0){
				sendOwnStatsMessage(player);
			}else if(args.length == 1){
				if(player.hasPermission("cakewars.stats.others")){
					@SuppressWarnings("deprecation")
					OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
					if(!MySQLStats.isUserExisting(target.getUniqueId())){
						player.sendMessage("§cDer Spieler §c" + args[0] + " §chat noch nie auf dem Server gespielt.");
					}else{
						sendTargetStatsMessage(player, target);
					}
				}else{
					sendOwnStatsMessage(player);
				}
			}else{
				sendOwnStatsMessage(player);
			}
		}	
		return false;
	}
	
	public void sendOwnStatsMessage(Player player){
		
		Integer kills = MySQLStats.getKills(player.getUniqueId());
		Integer deaths = MySQLStats.getDeaths(player.getUniqueId());
		Double KD = ((double) kills) / ((double) deaths);
		Double KDR = (double) Math.round(KD*10) / 10;
		Integer cakes = MySQLStats.getCakes(player.getUniqueId());
		Integer games = MySQLStats.getGames(player.getUniqueId());
		Integer wins = MySQLStats.getWins(player.getUniqueId());
		
		player.sendMessage("");
		player.sendMessage("§7-= §b" + player.getName() + "§b´s §eStats §7=-");
		player.sendMessage(" §7Kills§8: §a" + kills);
		player.sendMessage(" §7Tode§8: §a" + deaths);
		if(deaths == 0){
			player.sendMessage(" §7KD§8: §a" + kills);
		}else{
			player.sendMessage(" §7KD§8: §a" + KDR);
		}
		player.sendMessage(" §7Zerstörte Kuchen§8: §a" + cakes);
		player.sendMessage(" §7Gespielte Spiele§8: §a" + games);
		player.sendMessage(" §7Siege§8: §a" + wins);
		player.sendMessage("§7§l§m---------------");
		player.sendMessage("");
	}
	
	public void sendTargetStatsMessage(Player player, OfflinePlayer target){
		
		Integer kills = MySQLStats.getKills(target.getUniqueId());
		Integer deaths = MySQLStats.getDeaths(target.getUniqueId());
		Double KD = ((double) kills) / ((double) deaths);
		Double KDR = (double) Math.round(KD*10) / 10;
		Integer cakes = MySQLStats.getCakes(target.getUniqueId());
		Integer games = MySQLStats.getGames(target.getUniqueId());
		Integer wins = MySQLStats.getWins(player.getUniqueId());
		
		player.sendMessage("");
		player.sendMessage("§7-= §b" + target.getName() + "§b´s §eStats §7=-");
		player.sendMessage(" §7Kills§8: §a" + kills);
		player.sendMessage(" §7Tode§8: §a" + deaths);
		if(deaths == 0){
			player.sendMessage(" §7KD§8: §a" + kills);
		}else{
			player.sendMessage(" §7KD§8: §a" + KDR);
		}
		player.sendMessage(" §7Zerstörte Kuchen§8: §a" + cakes);
		player.sendMessage(" §7Gespielte Spiele§8: §a" + games);
		player.sendMessage(" §7Siege§8: §a" + wins);
		player.sendMessage("§7§l§m---------------");
		player.sendMessage("");
	}
}
