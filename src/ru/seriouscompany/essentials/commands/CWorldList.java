package ru.seriouscompany.essentials.commands;

import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;

public class CWorldList implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] agrs) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.isPermissionSet("scessentials.world.list")) {
				sender.sendMessage(Config.PERMISSION_DENY);
				return true;
			}
		}
		List<World> worlds = SCCore.getInstance().getServer().getWorlds();
		sender.sendMessage(Config.WORLD_LIST);
		for (World world: worlds) {
			sender.sendMessage(world.getName());
		}
		if (Config.WORLD_LIST_FOOTER.length() > 1)
			sender.sendMessage(Config.WORLD_LIST_FOOTER);
		return true;
	}

}
