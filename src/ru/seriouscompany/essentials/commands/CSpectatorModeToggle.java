package ru.seriouscompany.essentials.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;

public class CSpectatorModeToggle implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.spectator")) {
			sender.sendMessage(Config.PERMISSION_DENY);
			return true;
		}
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (player.getGameMode().equals(GameMode.SURVIVAL)) {
				player.setGameMode(GameMode.SPECTATOR);
				player.sendMessage(Config.SPECTATOR_MODE_ON);
			} else {
				player.setGameMode(GameMode.SURVIVAL);;
				player.sendMessage(Config.SPECTATOR_MODE_OFF);
			}
		} else
			sender.sendMessage(Config.COMMAND_FOR_PLAYERS);
		return true;
	}

}
