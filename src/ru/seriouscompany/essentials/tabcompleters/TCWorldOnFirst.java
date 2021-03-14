package ru.seriouscompany.essentials.tabcompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import ru.seriouscompany.essentials.SCCore;

public class TCWorldOnFirst implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> data = new ArrayList<String>();
		if (args.length == 1) {
			List<World> worlds = SCCore.getInstance().getServer().getWorlds();
			for (World world : worlds) {
				
				if (world.getName().length() >= args[0].length() && world.getName().substring( 0, args[0].length() ).equals( args[0] ))
					data.add(world.getName());
				if (data.size() >= 8) break;
			}
		}
		return data;
	}

}
