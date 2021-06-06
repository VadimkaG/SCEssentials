package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;

public class COpenInventory implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.openinventory")) {
			sender.sendMessage(Config.PERMISSION_DENY);
			return true;
		}
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 1) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if (target == null) {
					sender.sendMessage(Config.PLAYER_NOT_FOUND.replace("%PLAYER%", args[0]));
					return true;
				}
				player.openInventory(target.getInventory());
			} else
				return false;
		} else {
			sender.sendMessage(Config.COMMAND_FOR_PLAYERS);
		}
		return true;
	}
	
}