package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;

public class CHeal implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if (!sender.isPermissionSet("scessentials.heal")) {
			sender.sendMessage(Config.PERMISSION_DENY);
			return true;
		}
		Integer scale = 20;
		switch (args.length) {

		// Установить определенное количество
		case 1:
			try {
				scale = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				sender.sendMessage(Config.HEAL_ERR_INTEGER);
				return true;
			}
			if (scale < 0 || scale > 20) {
				sender.sendMessage(Config.HEAL_ERR_INTEGER);
				return true;
			}

		// Установить полные хп
		case 0:
			if (!(sender instanceof Player))
				return false;
			((Player) sender).setHealth(20);
			sender.sendMessage(Config.HEAL_SUCCESS);
			return true;

		// Установить определенное количество определенному игроку
		case 2:
			if (!sender.isPermissionSet("scessentials.heal.other")) {
				sender.sendMessage(Config.PERMISSION_DENY);
				return true;
			}
			try {
				scale = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				sender.sendMessage(Config.HEAL_ERR_INTEGER);
				return true;
			}
			if (scale < 0 || scale > 20) {
				sender.sendMessage(Config.HEAL_ERR_INTEGER);
				return true;
			}
			
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(Config.PLAYER_NOT_FOUND.replace("%PLAYER%", args[0]));
				return true;
			}
			target.setHealth(scale);
			sender.sendMessage(Config.HEAL_SUCCESS_OTHER.replace("%PLAYER%", args[0]));
			return true;
		default: return false;
		}
	}

}
