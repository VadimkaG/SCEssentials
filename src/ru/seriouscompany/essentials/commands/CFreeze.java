package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.api.Utils;

public class CFreeze implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.isPermissionSet("scessentials.freeze")) {
				player.sendMessage(Config.PERMISSION_DENY);
				return true;
			}
			if (Utils.isPlayerFreezed(player)) {
				sender.sendMessage(Config.YOU_FREEZED);
				return true;
			}
		}
		if (args.length == 1) {
			Player target = SCCore.getInstance().getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(Config.PLAYER_NOT_FOUND.replace("%PLAYER%", args[0]));
				return true;
			}
			if (target.equals(sender)) {
				sender.sendMessage(Config.FREEZE_SELF);
				return true;
			}
			if (Utils.isPlayerFreezed(target)) {
				Utils.setPlayerFREEZE(target, false);
				if (sender != target)
					sender.sendMessage(Config.FREEZE_OFF.replace("%PLAYER%", target.getName()));
				target.sendMessage(Config.FREEZE_OFF_TARGET.replace("%PLAYER%", sender.getName()));
			} else {
				Utils.setPlayerFREEZE(target, true);
				if (sender != target)
					sender.sendMessage(Config.FREEZE_ON.replace("%PLAYER%", target.getName()));
				target.sendMessage(Config.FREEZE_ON_TARGET.replace("%PLAYER%", sender.getName()));
			}
			return true;
		}
		return false;
	}

}
