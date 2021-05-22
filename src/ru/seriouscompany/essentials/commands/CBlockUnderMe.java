package ru.seriouscompany.essentials.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;

public class CBlockUnderMe implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.blockunderme")) {
			sender.sendMessage(Config.PERMISSION_DENY);
			return true;
		}
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(Config.COMMAND_FOR_PLAYERS);
			return true;
		}
		
		Player player = (Player)sender;
		
		player.getLocation().subtract(0, 1, 0).getBlock().setType(Material.GLASS);
		
		return true;
	}

}
