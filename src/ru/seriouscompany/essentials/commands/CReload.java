package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;

public class CReload implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lablel, String[] args) {
		if (sender.isPermissionSet("scessentials.reload")) {
			Config.loadConfig();
			Config.loadMessages();
			SCCore.getInstance().checkTimedStop();
			sender.sendMessage("Презагрузка SCEssentials завершена.");
		} else {
			sender.sendMessage(Config.PERMISSION_DENY);
		}
		return true;
	}

}
