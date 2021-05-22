package ru.seriouscompany.essentials.tabcompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TCWorldLoad implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> data = new ArrayList<String>();
		if (args.length == 2) {
			if (args[1].length() <= 6 && "normal".substring( 0, args[1].length()).equalsIgnoreCase(args[1]))
				data.add("NORMAL");
			if (args[1].length() <= 6 && "nether".substring( 0, args[1].length()).equalsIgnoreCase(args[1]))
				data.add("NETHER");
			if (args[1].length() <= 7 && "the_end".substring( 0, args[1].length()).equalsIgnoreCase(args[1]))
				data.add("THE_END");
			if (args[1].length() <= 4 && "flat".substring( 0, args[1].length()).equalsIgnoreCase(args[1]))
				data.add("FLAT");
			if (args[1].length() <= 5 && "empty".substring( 0, args[1].length()).equalsIgnoreCase(args[1]))
				data.add("EMPTY");
		}
		return data;
	}

}
