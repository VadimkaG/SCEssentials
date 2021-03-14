package ru.seriouscompany.essentials.commands;

import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;

public class CWorldLoad implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.isPermissionSet("scessentials.world.load")) {
				sender.sendMessage(Config.PERMISSION_DENY);
				return true;
			}
		}
		if (args.length >= 1 && args.length <= 2) {
			WorldCreator worldc = new WorldCreator(args[0]);
			if (args.length > 1) {
				String worldType = args[1].toUpperCase();
				switch (worldType) {
				case "THE_END":
				case "NETHER":
					worldc.environment(Environment.valueOf(args[1]));
					break;
				default:
					worldc.environment(Environment.valueOf("WORLD"));
				}
			}
			SCCore.getInstance().getServer().broadcastMessage(Config.WORLD_LOAD.replace("%WORLD%", args[0]));
			worldc.createWorld();
			SCCore.getInstance().getServer().broadcastMessage(Config.WORLD_LOAD_COMPLETE.replace("%WORLD%", args[0]));
			return true;
		}
		return false;
	}

}
