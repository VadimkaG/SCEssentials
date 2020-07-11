package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;

public class CHeal implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.isPermissionSet("scessentials.heal")) {
				if (args.length == 2) {
					if (player.isPermissionSet("scessentials.heal.other")) { // 999999999
						Integer scale = 20;
						try {
							scale = Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							sender.sendMessage(Config.HEAL_ERR_INTEGER);
							return true;
						}
						if (scale < 1 || scale > 20) {
							sender.sendMessage(Config.HEAL_ERR_INTEGER);
							return true;
						}
						
						Player target = SCCore.getInstance().getServer().getPlayer(args[0]);
						if (target == null) {
							sender.sendMessage(Config.PLAYER_NOT_FOUND.replace("%PLAYER%", args[0]));
							return true;
						}
						target.setHealth(scale);
						player.sendMessage(Config.HEAL_SUCCESS_OTHER.replace("%PLAYER%", args[0]));
					} else {
						player.sendMessage(Config.HEAL_DENYED);
					}
				} else if (args.length == 1) {
					Integer scale = 20;
					try {
						scale = Integer.parseInt(args[0]);
					} catch (NumberFormatException e) {
						sender.sendMessage(Config.HEAL_ERR_INTEGER);
						return true;
					}
					if (scale < 1 || scale > 20) {
						sender.sendMessage(Config.HEAL_ERR_INTEGER);
						return true;
					}
					player.setHealth(scale);
					player.sendMessage(Config.HEAL_SUCCESS);
				} else {
					player.setHealth(20);
					player.sendMessage(Config.HEAL_SUCCESS);
				}
			} else {
				player.sendMessage(Config.PERMISSION_DENY);
			}
		} else {
			sender.sendMessage(Config.COMMAND_FOR_PLAYERS);
		}
 		return true;
	}

}
