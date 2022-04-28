package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import ru.seriouscompany.essentials.Lang;
import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.api.Utils;

public class CAFK implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (Utils.isPlayerAFKwait(player)) {
				player.sendMessage(Lang.AFK_ALREADY_WAIT.toString());
				return true;
			}
			if (Utils.isPlayerAFK(player)) {
				Utils.setPlayerAFK(player, false);
				Bukkit.getServer().broadcastMessage(Lang.AFK_OFF.toString().replace("%PLAYER%", player.getName()));
				Utils.removePlayerFromAfkTeam(player);
			} else {
				if (!player.isPermissionSet("scessentials.afk")) {
					player.sendMessage(Lang.PERMISSION_DENY.toString());
					return true;
				}
				if (Utils.isPlayerFreezed(player)) {
					player.sendMessage(Lang.AFK_DENIED_WHEN_FREEZED.toString());
					return true;
				}
				long cooldown = SCCore.getAFKCooldown();
				player.sendMessage(Lang.AFK_WAIT.toString().replace("%TIME%", String.valueOf(cooldown/15)));
				BukkitRunnable t = new BukkitRunnable() {
					@Override
					public void run() {
						Player p = (Player) sender;
						Utils.setPlayerAFK(player, true);
						Utils.cancelPlayerAFKwait(player);
						Bukkit.broadcastMessage(Lang.AFK_ON.toString().replace("%PLAYER%", p.getName()));
						Utils.addPlayerToAfkTeam(player);
					}
				};
				t.runTaskLater(SCCore.getInstance(), cooldown);
				Utils.startPlayerAFKwait(player, t);
			}
			return true;
		} else {
			sender.sendMessage(Lang.COMMAND_FOR_PLAYERS.toString());
			return true;
		}
	}

}
