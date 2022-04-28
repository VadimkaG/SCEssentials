package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Lang;
import ru.seriouscompany.essentials.SCCore;

public class CTeleportToPlayer implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.teleport.toplayers")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(Lang.COMMAND_FOR_PLAYERS.toString());
			return true;
		}
		
		if (args.length != 1)
			return false;
		
		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Lang.PLAYER_NOT_FOUND.toString()
					.replaceAll("%PLAYER%", args[0])
				);
			return true;
		}
		
		if (SCCore.teleportImmunityAllow() && target.isPermissionSet("scessentials.teleport.immunity")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		
		((Player)sender).teleport(target);
		return true;
	}

}
