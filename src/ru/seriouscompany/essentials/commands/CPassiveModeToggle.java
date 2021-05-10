package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.api.PlayerFlag;

public class CPassiveModeToggle implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Config.COMMAND_FOR_PLAYERS);
			return true;
		}
		Player player = (Player)sender;
		if (!player.isPermissionSet("scessentials.passivemode")) {
			sender.sendMessage(Config.PERMISSION_DENY);
			return true;
		}
		
		if (PlayerFlag.getPlayerFlag(player, "PASSIVE_MODE").asBoolean()) {
			PlayerFlag.setPlayerFlag(player, "PASSIVE_MODE", false);
			player.sendMessage(Config.PASSIVE_MODE_OFF);
		} else {
			PlayerFlag.setPlayerFlag(player, "PASSIVE_MODE", true);
			player.sendMessage(Config.PASSIVE_MODE_ON);
		}
		
		return true;
	}

}
