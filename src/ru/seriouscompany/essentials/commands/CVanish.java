package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.listeners.PlayerListener;

public class CVanish implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.vanish"))
			return true;
		if (!(sender instanceof Player))
			return true;
		Player target = (Player)sender;
		Plugin pl = SCCore.getInstance();
		if (PlayerListener.vanishedPlayers.contains(target)) {
			PlayerListener.vanishedPlayers.remove(target);
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.showPlayer(pl,target);
			}
			sender.sendMessage("Вы показались");
		} else {
			PlayerListener.vanishedPlayers.add(target);
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.hidePlayer(pl, target);
			}
			sender.sendMessage("Вы скрылись");
		}
		return true;
	}

}
