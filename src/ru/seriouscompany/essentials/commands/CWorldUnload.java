package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.seriouscompany.essentials.Config;

public class CWorldUnload implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.world.unload")) {
			sender.sendMessage(Config.PERMISSION_DENY);
			return true;
		}
		if (args.length == 1) {
			Bukkit.getServer().broadcastMessage(Config.WORLD_UNLOAD.replace("%WORLD%", args[0]));
			if (Bukkit.getServer().unloadWorld(args[0], true))
				Bukkit.getServer().broadcastMessage(Config.WORLD_UNLOAD_COMPLETE.replace("%WORLD%", args[0]));
			else
				Bukkit.getServer().broadcastMessage(Config.WORLD_UNLOAD_ERROR.replace("%WORLD%", args[0]));
			return true;
		}
		return false;
	}

}
