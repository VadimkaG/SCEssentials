package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.seriouscompany.essentials.Lang;

public class CWorldUnload implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.world.manage")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (args.length == 1) {
			Bukkit.getServer().broadcastMessage(Lang.WORLD_UNLOAD.toString().replace("%WORLD%", args[0]));
			if (Bukkit.getServer().unloadWorld(args[0], true))
				Bukkit.getServer().broadcastMessage(Lang.WORLD_UNLOAD_COMPLETE.toString().replace("%WORLD%", args[0]));
			else
				Bukkit.getServer().broadcastMessage(Lang.WORLD_UNLOAD_ERROR.toString().replace("%WORLD%", args[0]));
			return true;
		}
		return false;
	}

}
