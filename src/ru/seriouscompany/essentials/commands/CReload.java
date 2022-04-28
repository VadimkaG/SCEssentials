package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.seriouscompany.essentials.Lang;
import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.tasks.AFKTask;

public class CReload implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lablel, String[] args) {
		if (sender.isPermissionSet("scessentials.reload")) {
			if (SCCore.getInstance().afkTask != null)
				SCCore.getInstance().afkTask.cancel();
			
			SCCore.getInstance().reloadConfig();
			Lang.loadConfiguration();
			
			SCCore.getInstance().checkTimedStop();
			if (SCCore.getAutoKickTime() > 0 || SCCore.getAutoAFKDely() > 0) {
				SCCore.getInstance().afkTask = new AFKTask();
				SCCore.getInstance().afkTask.runTaskTimer(SCCore.getInstance(), 1000, 1000);
			}
			sender.sendMessage("Презагрузка SCEssentials завершена.");
		} else {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
		}
		return true;
	}

}
