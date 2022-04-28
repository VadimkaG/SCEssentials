package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Lang;

public class COpenEnder implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.openender")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (args.length == 1) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if (target == null) {
					sender.sendMessage(Lang.PLAYER_NOT_FOUND.toString().replace("%PLAYER%", args[0]));
					return true;
				}
				player.openInventory(target.getEnderChest());
			} else
				return false;
		} else {
			sender.sendMessage(Lang.COMMAND_FOR_PLAYERS.toString());
		}
		return false;
	}

}
