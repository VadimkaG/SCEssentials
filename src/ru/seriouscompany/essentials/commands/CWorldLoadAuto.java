package ru.seriouscompany.essentials.commands;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.seriouscompany.essentials.Config;

public class CWorldLoadAuto implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.world.autoload")) {
			sender.sendMessage(Config.PERMISSION_DENY);
			return true;
		}
		if (args.length < 1 && args.length > 2)
			return false;
		
		Map<String, String> worlds = Config.getWorldList();
		
		if (worlds.containsKey(args[0])) {
			worlds.remove(args[0]);
			Config.setWorldList(worlds);
			sender.sendMessage(Config.WORLD_LOAD_AUTO_UNSET.replaceAll("%WORLD%", args[0]));
		} else {
			if (args.length != 2)
				return false;
			worlds.put(args[0], args[1]);
			Config.setWorldList(worlds);
			sender.sendMessage(Config.WORLD_LOAD_AUTO_SET.replaceAll("%WORLD%", args[0]));
		}
		return true;
	}

}
