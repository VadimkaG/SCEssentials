package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.api.Utils;

public class CPlayerInfo implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.isPermissionSet("scessentials.pinfo")) {
			if (args.length != 1) return false;
			Player target = SCCore.getInstance().getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(Config.PLAYER_NOT_FOUND.replace("%PLAYER%", args[0]));
				return true;
			}
			sender.sendMessage(Config.PLAYER_STAT.replace("%PLAYER%", target.getName()));
			sender.sendMessage(Config.WORLD+": "
					+ target.getLocation().getWorld() + "("
					+ target.getLocation().getBlockX() + ","
					+ target.getLocation().getBlockY() + ", "
					+ target.getLocation().getBlockZ() + ")");
			sender.sendMessage("IP: " + target.getAddress().getAddress().getHostAddress());
			sender.sendMessage(Config.GAME_MODE+": "+target.getGameMode().name());
			sender.sendMessage(Config.HEALTH+": "+String.valueOf(target.getHealth()));
			sender.sendMessage(Config.FOOD_LEVEL+": "+String.valueOf(target.getFoodLevel()));
			sender.sendMessage(Config.EXP+": "+String.valueOf(target.getLevel())+" lvl "+String.valueOf(target.getTotalExperience())+" points");
			if (target.isOp())
				sender.sendMessage(Config.OPERATOR+": "+Config.YES);
			else
				sender.sendMessage(Config.OPERATOR+": "+Config.NO);
			if (Utils.isPlayerFreezed(target))
				sender.sendMessage(Config.FREEZED+": "+Config.YES);
			else
				sender.sendMessage(Config.FREEZED+": "+Config.NO);
			
			if (Utils.isPlayerAFK(target))
				sender.sendMessage("AFK: "+Config.YES);
			else
				sender.sendMessage("AFK: "+Config.NO);
			
			if (target.isFlying())
				sender.sendMessage(Config.FLYING+": "+Config.YES);
			else
				sender.sendMessage(Config.FLYING+": "+Config.NO);
			if (Config.PLAYER_STAT_FOOTER.length() > 1)
				sender.sendMessage(Config.PLAYER_STAT_FOOTER);
		} else
			sender.sendMessage(Config.PERMISSION_DENY);
		return true;
	}

}
