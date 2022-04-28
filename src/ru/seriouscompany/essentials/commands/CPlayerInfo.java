package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Lang;
import ru.seriouscompany.essentials.api.PlayerFlag;
import ru.seriouscompany.essentials.api.Utils;

public class CPlayerInfo implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.pinfo")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		
		if (args.length != 1) return false;
		
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Lang.PLAYER_NOT_FOUND.toString().replace("%PLAYER%", args[0]));
			return true;
		}
		
		String message = Lang.PLAYER_STAT.toString().replace("%PLAYER%", target.getName());
		
		message += "\n"+Lang.WORLD+": "
				+ target.getLocation().getWorld() + "("
				+ target.getLocation().getBlockX() + ","
				+ target.getLocation().getBlockY() + ", "
				+ target.getLocation().getBlockZ() + ")";
		
		message += "\nUUID: " + target.getUniqueId().toString();
		message += "\nIP: " + target.getAddress().getAddress().getHostAddress();
		message += "\n"+Lang.GAME_MODE+": "+target.getGameMode().name();
		message += "\n"+Lang.HEALTH+": "+String.valueOf(target.getHealth());
		message += "\n"+Lang.FOOD_LEVEL+": "+String.valueOf(target.getFoodLevel());
		message += "\n"+Lang.EXP+": "+String.valueOf(target.getLevel())+" lvl "+String.valueOf(target.getTotalExperience())+" points";
		
		message += "\n"+Lang.OPERATOR+": ";
		if (target.isOp())
			message += Lang.YES;
		else
			message += Lang.NO;
		
		message += "\n"+Lang.FREEZED+": ";
		if (Utils.isPlayerFreezed(target))
			message += Lang.YES;
		else
			message += Lang.NO;
		
		message += "\nAFK: ";
		if (Utils.isPlayerAFK(target))
			message += Lang.YES;
		else
			message += Lang.NO;
		
		message += "\n"+Lang.FLYING+": ";
		if (target.isFlying())
			message += Lang.YES;
		else
			message += Lang.NO;
		
		message += "\n"+Lang.COMBAT_IN+": ";
		if (PlayerFlag.getPlayerFlag(target, "IN_COMBAT").asBoolean())
			message += Lang.YES;
		else
			message += Lang.NO;
		
		message += "\n"+Lang.PASSIVEMODE_STAT+": ";
		if (PlayerFlag.getPlayerFlag(target, "PASSIVE_MODE").asBoolean())
			message += Lang.YES;
		else
			message += Lang.NO;
		
		if (Lang.PLAYER_STAT_FOOTER.toString().length() > 1)
			message += "\n"+Lang.PLAYER_STAT_FOOTER;
		
		sender.sendMessage(message);
		return true;
	}

}
