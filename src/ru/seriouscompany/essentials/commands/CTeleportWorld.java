package ru.seriouscompany.essentials.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;

public class CTeleportWorld implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.isPermissionSet("scessentials.world.teleport")) {
				if (args.length == 1) {
					World world = SCCore.getInstance().getServer().getWorld(args[0]);
					if (world != null) {
						player.teleport(world.getSpawnLocation());
					} else {
						sender.sendMessage(Config.WORLD_NOT_FOUND.replace("%WORLD%", args[0]));
					}
				} else if (args.length == 2) {
					Player target = SCCore.getInstance().getServer().getPlayer(args[1]);
					if (target == null) {
						sender.sendMessage(Config.PLAYER_NOT_FOUND.replace("%PLAYER%", args[1]));
						return true;
					}
					World world = SCCore.getInstance().getServer().getWorld(args[0]);
					if (world != null) {
						target.teleport(world.getSpawnLocation());
					} else {
						sender.sendMessage(Config.WORLD_NOT_FOUND.replace("%WORLD%", args[0]));
					}
				} else
					return false;
			} else
				sender.sendMessage(Config.PERMISSION_DENY);
		} else
			sender.sendMessage(Config.COMMAND_FOR_PLAYERS);
		return true;
	}

}
