package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.Lang;

public class CTeleportToPlayerBed implements CommandExecutor {
	
	protected Config config;
	
	public CTeleportToPlayerBed(Config config) {
		this.config = config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.teleport.tobed")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(Lang.COMMAND_FOR_PLAYERS.toString());
			return true;
		}
		
		Player player = (Player)sender;
		Location loc;
		
		switch (args.length) {
		case 1:
			if (!sender.isPermissionSet("scessentials.teleport.tobed.other")) {
				sender.sendMessage(Lang.PERMISSION_DENY.toString());
				return true;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(Lang.PLAYER_NOT_FOUND.toString()
						.replaceAll("%PLAYER%", args[0])
					);
				return true;
			}
			
			if (config.isTeleportImmunityEnabled() && target.isPermissionSet("scessentials.teleport.immunity")) {
				sender.sendMessage(Lang.PERMISSION_DENY.toString());
				return true;
			}
			
			loc = target.getRespawnLocation();
			if (loc == null) {
				sender.sendMessage(Lang.PLAYER_BED_NOT_FOUND.toString()
						.replaceAll("%PLAYER%", target.getName())
					);
				return true;
			}
			player.teleport(loc);
			return true;
		case 0:
			loc = player.getRespawnLocation();
			if (loc == null) {
				sender.sendMessage(Lang.PLAYER_BED_NOT_FOUND.toString()
						.replaceAll("%PLAYER%", player.getName())
					);
				return true;
			}
			player.teleport(loc);
			return true;
		default: return false;
		}
	}

}
