package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.Lang;
import ru.seriouscompany.essentials.SCCore;

public class CReload implements CommandExecutor {
	
	protected SCCore plugin;
	protected Config config;
	
	public CReload(SCCore plugin, Config config) {
		this.plugin = plugin;
		this.config = config;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lablel, String[] args) {
		if (sender.isPermissionSet("scessentials.reload")) {
			
			config.reload();
			Lang.loadConfiguration(plugin.getDataFolder());
			
			plugin.checkTimedStop(config);
			sender.sendMessage("Done.");
		} else {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
		}
		return true;
	}

}
