package ru.seriouscompany.essentials.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.api.PlayerFreezeContainer;

public class CAFK implements CommandExecutor {
	private static List<String> PLAYERS = new ArrayList<String>();
	
	private static HashMap<String,BukkitTask> WaitForAfk = new HashMap<String,BukkitTask>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.isPermissionSet("scessentials.afk")) {
				if (WaitForAfk.containsKey(player.getName())) {
					player.sendMessage(Config.AFK_ALREADY_WAIT);
					return true;
				}
				if (PlayerFreezeContainer.contains(player)) {
					PlayerFreezeContainer.removeStorager(player);
					PLAYERS.remove(player.getName());
					player.setSleepingIgnored(false);
					SCCore.getInstance().getServer().broadcastMessage(Config.AFK_OFF.replace("%PLAYER%", player.getName()));
				} else {
					player.sendMessage(Config.AFK_WAIT.replace("%TIME%", String.valueOf(Config.WAIT_FOR_AFK/15)));
					BukkitTask t = new BukkitRunnable() {
						@Override
						public void run() {
							Player p = (Player) sender;
							new PlayerFreezeContainer(p);
							PLAYERS.add(p.getName());
							WaitForAfk.remove(p.getName());
							p.setSleepingIgnored(true);
							Bukkit.broadcastMessage(Config.AFK_ON.replace("%PLAYER%", p.getName()));
						}
					}.runTaskLater(SCCore.getInstance(), Config.WAIT_FOR_AFK);
					
					WaitForAfk.put(player.getName(),t);
				}
			} else
				player.sendMessage(Config.PERMISSION_DENY);
			return true;
		} else {
			sender.sendMessage(Config.COMMAND_FOR_PLAYERS);
			return true;
		}
		//return false;
	}
	
	public static boolean isPlayerAfk(String player_name) {
		return PLAYERS.contains(player_name);
	}
	
	public static boolean isPlayerwaitingForAfk(String player_name) {
		return WaitForAfk.containsKey(player_name);
	}
	
	public static void cancelWaiting(String player_name) {
		if (WaitForAfk.containsKey(player_name)) {
			WaitForAfk.get(player_name).cancel();
			WaitForAfk.remove(player_name);
		}
	}

}
