package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Lang;

public class CFly implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.fly")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		switch (args.length) {
		case 0:
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.getAllowFlight()) {
					player.setAllowFlight(false);
					player.sendMessage(Lang.FLY_OFF.toString());
				} else {
					player.setAllowFlight(true);
					player.sendMessage(Lang.FLY_ON.toString());
				}
			} else
				sender.sendMessage(Lang.COMMAND_FOR_PLAYERS.toString());
			return true;
		case 1:
			if (!sender.isPermissionSet("scessentials.fly.other")) {
				sender.sendMessage(Lang.PERMISSION_DENY.toString());
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(Lang.PLAYER_NOT_FOUND.toString().replace("%PLAYER%", args[0]));
				return true;
			}
			if (target.getAllowFlight()) {
				target.setAllowFlight(false);
				target.sendMessage(Lang.FLY_OFF_OTHER.toString().replace("%PLAYER%", target.getName()));
			} else {
				target.setAllowFlight(true);
				target.sendMessage(Lang.FLY_ON_OTHER.toString().replace("%PLAYER%", target.getName()));
			}
			return true;
		default: return false;
		}
	}
	
}
