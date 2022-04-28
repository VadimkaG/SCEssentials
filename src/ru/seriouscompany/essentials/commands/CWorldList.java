package ru.seriouscompany.essentials.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.seriouscompany.essentials.Lang;

public class CWorldList implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] agrs) {
		if (!sender.isPermissionSet("scessentials.world.list")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		List<World> worlds = Bukkit.getServer().getWorlds();
		String str = Lang.WORLD_LIST.toString();
		for (World world: worlds) {
			str += "\n"+world.getName();
		}
		if (Lang.WORLD_LIST_FOOTER.toString().length() > 1)
			str += "\n"+Lang.WORLD_LIST_FOOTER.toString();
		sender.sendMessage(str);
		return true;
	}

}
