package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;

public class CWorldUnload implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.isPermissionSet("scessentials.world.unload")) {
				sender.sendMessage(Config.PERMISSION_DENY);
				return true;
			}
		}
		if (args.length == 1) {
			SCCore.getInstance().getServer().broadcastMessage(Config.WORLD_UNLOAD.replace("%WORLD%", args[0]));
			if (SCCore.getInstance().getServer().unloadWorld(args[0], true))
				SCCore.getInstance().getServer().broadcastMessage(Config.WORLD_UNLOAD_COMPLETE.replace("%WORLD%", args[0]));
			else
				SCCore.getInstance().getServer().broadcastMessage(Config.WORLD_UNLOAD_ERROR.replace("%WORLD%", args[0]));
			return true;
		}
		return false;
	}

}
