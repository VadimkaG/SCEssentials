package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;

public class CFly implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.fly")) {
			sender.sendMessage(Config.PERMISSION_DENY);
			return true;
		}
		switch (args.length) {
		case 0:
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.getAllowFlight()) {
					player.setAllowFlight(false);
					player.sendMessage(Config.FLY_OFF);
				} else {
					player.setAllowFlight(true);
					player.sendMessage(Config.FLY_ON);
				}
			} else
				sender.sendMessage(Config.COMMAND_FOR_PLAYERS);
			return true;
		case 1:
			if (!sender.isPermissionSet("scessentials.fly.other")) {
				sender.sendMessage(Config.PERMISSION_DENY);
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
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
			return true;
		default: return false;
		}
	}
	
}
