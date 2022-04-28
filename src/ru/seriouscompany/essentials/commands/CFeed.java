package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Lang;

public class CFeed implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.feed")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		switch (args.length) {
		case 0:
			if (sender instanceof Player) {
				((Player) sender).setFoodLevel(20);
				return true;
			} else
				return false;
		case 1:
			if (!sender.isPermissionSet("scessentials.feed.other")) {
				sender.sendMessage(Lang.PERMISSION_DENY.toString());
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(Lang.PLAYER_NOT_FOUND.toString().replace("%PLAYER%", args[0]));
				return true;
			}
			target.setFoodLevel(20);
			return true;
		default:return false;
		}
	}

}
