package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.tasks.AFKTask;

public class CReload implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lablel, String[] args) {
		if (sender.isPermissionSet("scessentials.reload")) {
			if (SCCore.getInstance().afkTask != null)
				SCCore.getInstance().afkTask.cancel();
			Config.loadConfig();
			Config.loadMessages();
			SCCore.getInstance().checkTimedStop();
			if (Config.AFK_KICK > 0 || Config.AFK_AUTO > 0) {
				SCCore.getInstance().afkTask = new AFKTask();
				SCCore.getInstance().afkTask.runTaskTimer(SCCore.getInstance(), 1000, 1000);
			}
			sender.sendMessage("Презагрузка SCEssentials завершена.");
		} else {
			sender.sendMessage(Config.PERMISSION_DENY);
		}
		return true;
	}

}
