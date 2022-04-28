package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Lang;
import ru.seriouscompany.essentials.api.PlayerFlag;

public class CPassiveModeToggle implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.passivemode")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(Lang.COMMAND_FOR_PLAYERS.toString());
			return true;
		}
		Player player = (Player)sender;
		if (PlayerFlag.getPlayerFlag(player, "PASSIVE_MODE").asBoolean()) {
			PlayerFlag.setPlayerFlag(player, "PASSIVE_MODE", false);
			player.sendMessage(Lang.PASSIVE_MODE_OFF.toString());
		} else {
			PlayerFlag.setPlayerFlag(player, "PASSIVE_MODE", true);
			player.sendMessage(Lang.PASSIVE_MODE_ON.toString());
		}
		
		return true;
	}

}
