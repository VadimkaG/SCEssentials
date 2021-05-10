package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.api.PlayerFlag;
import ru.seriouscompany.essentials.api.Utils;

public class CPlayerInfo implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.pinfo")) {
			sender.sendMessage(Config.PERMISSION_DENY);
			return true;
		}
		
		if (args.length != 1) return false;
		
		Player target = SCCore.getInstance().getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Config.PLAYER_NOT_FOUND.replace("%PLAYER%", args[0]));
			return true;
		}
		
		String message = Config.PLAYER_STAT.replace("%PLAYER%", target.getName());
		
		message += "\n"+Config.WORLD+": "
				+ target.getLocation().getWorld() + "("
				+ target.getLocation().getBlockX() + ","
				+ target.getLocation().getBlockY() + ", "
				+ target.getLocation().getBlockZ() + ")";
		
		message += "\nUUID: " + target.getUniqueId().toString();
		message += "\nIP: " + target.getAddress().getAddress().getHostAddress();
		message += "\n"+Config.GAME_MODE+": "+target.getGameMode().name();
		message += "\n"+Config.HEALTH+": "+String.valueOf(target.getHealth());
		message += "\n"+Config.FOOD_LEVEL+": "+String.valueOf(target.getFoodLevel());
		message += "\n"+Config.EXP+": "+String.valueOf(target.getLevel())+" lvl "+String.valueOf(target.getTotalExperience())+" points";
		
		message += "\n"+Config.OPERATOR+": ";
		if (target.isOp())
			message += Config.YES;
		else
			message += Config.NO;
		
		message += "\n"+Config.FREEZED+": ";
		if (Utils.isPlayerFreezed(target))
			message += Config.YES;
		else
			message += Config.NO;
		
		message += "\nAFK: ";
		if (Utils.isPlayerAFK(target))
			message += Config.YES;
		else
			message += Config.NO;
		
		message += "\n"+Config.FLYING+": ";
		if (target.isFlying())
			message += Config.YES;
		else
			message += Config.NO;
		
		message += "\n"+Config.COMBAT_IN+": ";
		if (PlayerFlag.getPlayerFlag(target, "IN_COMBAT").asBoolean())
			message += Config.YES;
		else
			message += Config.NO;
		
		message += "\n"+Config.PASSIVEMODE_STAT+": ";
		if (PlayerFlag.getPlayerFlag(target, "PASSIVE_MODE").asBoolean())
			message += Config.YES;
		else
			message += Config.NO;
		
		if (Config.PLAYER_STAT_FOOTER.length() > 1)
			message += "\n"+Config.PLAYER_STAT_FOOTER;
		
		sender.sendMessage(message);
		return true;
	}

}
