package ru.seriouscompany.essentials.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.seriouscompany.essentials.Config;

public class CWorldList implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] agrs) {
		if (!sender.isPermissionSet("scessentials.world.list")) {
			sender.sendMessage(Config.PERMISSION_DENY);
			return true;
		}
		List<World> worlds = Bukkit.getServer().getWorlds();
		String str = Config.WORLD_LIST;
		for (World world: worlds) {
			str += "\n"+world.getName();
		}
		if (Config.WORLD_LIST_FOOTER.length() > 1)
			str += "\n"+Config.WORLD_LIST_FOOTER;
		sender.sendMessage(str);
		return true;
	}

}
