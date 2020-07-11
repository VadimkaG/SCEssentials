package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;

public class CFly implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.isPermissionSet("scessentials.fly")) {
				if (args.length == 1) {
					if (player.isPermissionSet("scessentials.fly.other")) {
						Player target = SCCore.getInstance().getServer().getPlayer(args[0]);
						if (target == null) {
							sender.sendMessage(Config.PLAYER_NOT_FOUND.replace("%PLAYER%", args[0]));
							return true;
						}
						
						if (target.getAllowFlight()) {
							target.setAllowFlight(false);
							target.sendMessage(Config.FLY_OFF_OTHER.replace("%PLAYER%", target.getName()));
						} else {
							target.setAllowFlight(true);
							target.sendMessage(Config.FLY_ON_OTHER.replace("%PLAYER%", target.getName()));
						}
					} else {
						sender.sendMessage(Config.FLY_DENY_OTHER);
					}
				} else {
					if (player.getAllowFlight()) {
						player.setAllowFlight(false);
						player.sendMessage(Config.FLY_OFF);
					} else {
						player.setAllowFlight(true);
						player.sendMessage(Config.FLY_ON);
					}
				}
			} else {
				sender.sendMessage(Config.PERMISSION_DENY);
			}
		} else {
			sender.sendMessage(Config.COMMAND_FOR_PLAYERS);
		}
		return true;
	}
	
}
