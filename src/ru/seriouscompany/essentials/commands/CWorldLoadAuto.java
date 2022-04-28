package ru.seriouscompany.essentials.commands;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.seriouscompany.essentials.Lang;
import ru.seriouscompany.essentials.SCCore;

public class CWorldLoadAuto implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.world.autoload")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (args.length < 1 && args.length > 2)
			return false;
		
		Map<String, String> worlds = SCCore.getWorldList();
		
		if (worlds.containsKey(args[0])) {
			worlds.remove(args[0]);
			SCCore.setWorldList(worlds);
			sender.sendMessage(Lang.WORLD_LOAD_AUTO_UNSET.toString().replaceAll("%WORLD%", args[0]));
		} else {
			if (args.length != 2)
				return false;
			worlds.put(args[0], args[1]);
			SCCore.setWorldList(worlds);
			sender.sendMessage(Lang.WORLD_LOAD_AUTO_SET.toString().replaceAll("%WORLD%", args[0]));
		}
		return true;
	}

}
