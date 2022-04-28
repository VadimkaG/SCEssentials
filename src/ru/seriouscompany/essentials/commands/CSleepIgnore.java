package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Lang;

public class CSleepIgnore implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] strs) {
		if (!sender.isPermissionSet("scessentials.sleepignore")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.isSleepingIgnored()) {
				player.setSleepingIgnored(false);
				player.sendMessage(Lang.SLEEP_ON.toString());
			} else {
				player.setSleepingIgnored(true);
				player.sendMessage(Lang.SLEEP_OFF.toString());
			}
		} else {
			sender.sendMessage(Lang.COMMAND_FOR_PLAYERS.toString());
		}
		return true;
	}

}
