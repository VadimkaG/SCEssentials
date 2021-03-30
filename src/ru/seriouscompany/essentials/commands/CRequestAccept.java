package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.api.PlayerFlag;

public class CRequestAccept implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (PlayerFlag.hasPlayerFlag(player, "PLAYER_REQUEST")) {
				MetadataValue obj = PlayerFlag.getPlayerFlag(player, "PLAYER_REQUEST");
				if (obj.value() instanceof Runnable)
					((Runnable)obj.value()).run();
				PlayerFlag.removePlayerFlag(player, "PLAYER_REQUEST");
			} else {
				sender.sendMessage("Вам нечего принимать");
			}
		} else {
			sender.sendMessage(Config.COMMAND_FOR_PLAYERS);
			return true;
		}
		return false;
	}

}
