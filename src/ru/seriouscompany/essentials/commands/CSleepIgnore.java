package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;

public class CSleepIgnore implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] strs) {
		if (!sender.isPermissionSet("scessentials.sleepignore")) {
			sender.sendMessage(Config.PERMISSION_DENY);
			return true;
		}
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.isSleepingIgnored()) {
				player.setSleepingIgnored(false);
				player.sendMessage(Config.SLEEP_ON);
			} else {
				player.setSleepingIgnored(true);
				player.sendMessage(Config.SLEEP_OFF);
			}
		} else {
			sender.sendMessage(Config.COMMAND_FOR_PLAYERS);
		}
		return true;
	}

}
