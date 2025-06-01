package ru.seriouscompany.essentials.commands;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.Lang;

public class CWorldLoadAuto implements CommandExecutor {
	
	protected Plugin plugin;
	protected Config config;
	
	public CWorldLoadAuto(Plugin plugin,Config config) {
		this.plugin = plugin;
		this.config = config;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.world.manage")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (args.length < 1 && args.length > 2)
			return false;
		
		plugin.getServer().getScheduler().runTaskAsynchronously(
			plugin,
			()->{
				Map<String, String> worlds = config.readWorldList();
				if (worlds.containsKey(args[0])) {
					worlds.remove(args[0]);
					config.saveWorldList(worlds);
					sender.sendMessage(Lang.WORLD_LOAD_AUTO_UNSET.toString().replaceAll("%WORLD%", args[0]));
				} else {
					if (args.length == 2) {
						worlds.put(args[0], args[1]);
						config.saveWorldList(worlds);
						sender.sendMessage(Lang.WORLD_LOAD_AUTO_SET.toString().replaceAll("%WORLD%", args[0]));
					} else
						sender.sendMessage("Недостаточно аргументов");
				}
			}
		);
		return true;
	}

}
