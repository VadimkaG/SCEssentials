package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Lang;

public class CTeleportWorld implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.world.teleport")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(Lang.COMMAND_FOR_PLAYERS.toString());
			return true;
		}
		Player player = (Player) sender;
		World world;
		switch (args.length) {
		
		// Телепортировать другого игрока в мир
		case 2:
			if (!sender.isPermissionSet("scessentials.world.teleport.other")) {
				sender.sendMessage(Lang.PERMISSION_DENY.toString());
				return true;
			}
			player = Bukkit.getServer().getPlayer(args[1]);
			if (player == null) {
				sender.sendMessage(Lang.PLAYER_NOT_FOUND.toString().replace("%PLAYER%", args[1]));
				return true;
			}
		
		// Телепортировать себя в мир
		case 1:
			world = Bukkit.getServer().getWorld(args[0]);
			if (world != null) {
				player.teleport(world.getSpawnLocation());
			} else
				sender.sendMessage(Lang.WORLD_NOT_FOUND.toString().replace("%WORLD%", args[0]));
			return true;
		default: return false;
		}
	}

}
