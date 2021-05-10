package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;

public class CTeleportToPlayer implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Config.COMMAND_FOR_PLAYERS);
			return true;
		}
		
		Player player = (Player)sender;
		if (!player.isPermissionSet("scessentials.teleport.toplayers")) {
			sender.sendMessage(Config.PERMISSION_DENY);
			return true;
		}
		
		if (args.length != 1)
			return false;
		
		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Config.PLAYER_NOT_FOUND
					.replaceAll("%PLAYER%", args[0])
				);
			return true;
		}
		
		player.teleport(target);
		return true;
	}

}
