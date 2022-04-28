package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Lang;

public class CSuicide implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if (!sender.isPermissionSet("scessentials.suicide")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (sender instanceof Player) {
			((Player) sender).setHealth(0);
		} else
			sender.sendMessage(Lang.COMMAND_FOR_PLAYERS.toString());
		return true;
	}

}
