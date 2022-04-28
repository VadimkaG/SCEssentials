package ru.seriouscompany.essentials.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Lang;

public class CSpectatorModeToggle implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.spectator")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (player.getGameMode().equals(GameMode.SURVIVAL)) {
				player.setGameMode(GameMode.SPECTATOR);
				player.sendMessage(Lang.SPECTATOR_MODE_ON.toString());
			} else {
				player.setGameMode(GameMode.SURVIVAL);;
				player.sendMessage(Lang.SPECTATOR_MODE_OFF.toString());
			}
		} else
			sender.sendMessage(Lang.COMMAND_FOR_PLAYERS.toString());
		return true;
	}

}
