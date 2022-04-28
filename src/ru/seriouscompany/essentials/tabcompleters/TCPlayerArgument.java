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
		List<Player> players = Bukkit.getServer().matchPlayer(args[ARGS_POS-1]);
		for (Player player: players) {
			data.add(player.getName());
		}
		return data;
	}

}
