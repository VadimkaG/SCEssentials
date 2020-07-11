package ru.seriouscompany.essentials.tabcompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.SCCore;

public class TCWorldTeleport implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> data = new ArrayList<String>();
		if (args.length == 1) {
			for (World world : SCCore.getInstance().getServer().getWorlds()) {
				if (world.getName().substring( 0, args[0].length() ).equals( args[0] ))
					data.add(world.getName());
				if (data.size() >= 8) break;
			}
		} else if (args.length == 2) {
			for (Player player : SCCore.getInstance().getServer().getOnlinePlayers()) {
				if (player.getName().substring(0, args[1].length()).equalsIgnoreCase(args[1]))
					data.add(player.getName());
				if (data.size() >= 8) break;
			}
		}
		return data;
	}

}
