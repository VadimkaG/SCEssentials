package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.api.Utils;

public class CAFK implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (Utils.isPlayerAFKwait(player)) {
				player.sendMessage(Config.AFK_ALREADY_WAIT);
				return true;
			}
			if (Utils.isPlayerAFK(player)) {
				Utils.setPlayerAFK(player, false);
				SCCore.getInstance().getServer().broadcastMessage(Config.AFK_OFF.replace("%PLAYER%", player.getName()));
			} else {
				if (!player.isPermissionSet("scessentials.afk")) {
					player.sendMessage(Config.PERMISSION_DENY);
					return true;
				}
				if (Utils.isPlayerFreezed(player)) {
					player.sendMessage(Config.AFK_DENIED_WHEN_FREEZED);
					return true;
				}
				player.sendMessage(Config.AFK_WAIT.replace("%TIME%", String.valueOf(Config.WAIT_FOR_AFK/15)));
				BukkitRunnable t = new BukkitRunnable() {
					@Override
					public void run() {
						Player p = (Player) sender;
						Utils.setPlayerAFK(player, true);
						Utils.cancelPlayerAFKwait(player);
						Bukkit.broadcastMessage(Config.AFK_ON.replace("%PLAYER%", p.getName()));
					}
				};
				t.runTaskLater(SCCore.getInstance(), Config.WAIT_FOR_AFK);
				Utils.startPlayerAFKwait(player, t);
			}
			return true;
		} else {
			sender.sendMessage(Config.COMMAND_FOR_PLAYERS);
			return true;
		}
	}

}
