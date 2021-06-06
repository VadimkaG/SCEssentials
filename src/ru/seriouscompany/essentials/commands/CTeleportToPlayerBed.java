package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;

public class CTeleportToPlayerBed implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.teleport.tobed")) {
			sender.sendMessage(Config.PERMISSION_DENY);
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(Config.COMMAND_FOR_PLAYERS);
			return true;
		}
		
		Player player = (Player)sender;
		Location loc;
		
		switch (args.length) {
		case 1:
			if (!sender.isPermissionSet("scessentials.teleport.tobed.other")) {
				sender.sendMessage(Config.PERMISSION_DENY);
				return true;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(Config.PLAYER_NOT_FOUND
						.replaceAll("%PLAYER%", args[0])
					);
				return true;
			}
			
			if (Config.allowTeleportImmunity && target.isPermissionSet("scessentials.teleport.immunity")) {
				sender.sendMessage(Config.PERMISSION_DENY);
				return true;
			}
			
			loc = target.getBedSpawnLocation();
			if (loc == null) {
				sender.sendMessage(Config.PLAYER_BED_NOT_FOUND
						.replaceAll("%PLAYER%", target.getName())
					);
				return true;
			}
			player.teleport(loc);
			return true;
		case 0:
			loc = player.getBedSpawnLocation();
			if (loc == null) {
				sender.sendMessage(Config.PLAYER_BED_NOT_FOUND
						.replaceAll("%PLAYER%", player.getName())
					);
				return true;
			}
			player.teleport(loc);
			return true;
		default: return false;
		}
	}

}
