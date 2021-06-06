package ru.seriouscompany.essentials.tabcompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TCPlayerArgument implements TabCompleter {
	
	private int ARGS_POS;
	
	public TCPlayerArgument() {
		ARGS_POS = 1;
	}
	
	public TCPlayerArgument(int argument_position) {
		ARGS_POS = argument_position;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> data = new ArrayList<String>();
		if (args.length != ARGS_POS) return data;
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (
					player.getName().equalsIgnoreCase(args[ARGS_POS-1]) || 
					(
						player.getName().length() >= args[ARGS_POS-1].length() &&
						player.getName().substring(0, args[ARGS_POS-1].length()).equalsIgnoreCase(args[ARGS_POS-1])
					)
				)
				data.add(player.getName());
			if (data.size() >= 8) break;
		}
		return data;
	}

}
