package ru.seriouscompany.essentials.tabcompleters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import ru.seriouscompany.essentials.commands.CWorld;

public class TCWorld implements TabCompleter {
	
	private TCWorldOnFirst completerWorld;
	private TCWorldLoad completerWorldLoad;
	
	public TCWorld() {
		completerWorld = new TCWorldOnFirst();
		completerWorldLoad = new TCWorldLoad();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> data = new ArrayList<String>();
		if (args.length == 1) {
			tryadd(args, data, CWorld.PREFIX_COMMAND_LIST);
			tryadd(args, data, CWorld.PREFIX_COMMAND_LOAD);
			tryadd(args, data, CWorld.PREFIX_COMMAND_UNLOAD);
		} else if (args.length > 1) {
			String[] newArgs;
			if (args.length > 1) newArgs = Arrays.copyOfRange(args, 1, args.length);
			else newArgs = new String[0];
			switch (args[0].trim()) {
			case CWorld.PREFIX_COMMAND_UNLOAD:
				return completerWorld.onTabComplete(sender, cmd, label+" "+CWorld.PREFIX_COMMAND_UNLOAD, newArgs);
			case CWorld.PREFIX_COMMAND_LOAD:
				return completerWorldLoad.onTabComplete(sender, cmd, label+" "+CWorld.PREFIX_COMMAND_LOAD, newArgs);
			}
			
		}
		return data;
	}
	
	private void tryadd(String[] args, List<String> data,String text) {
		int argLen = args[0].length();
		if (
				args[0].equalsIgnoreCase("") || 
				args[0].equalsIgnoreCase(text) || 
				(
					text.length() >= argLen &&
					text.substring(0,argLen).equalsIgnoreCase(args[0])
				)
			)
			data.add(text);
	}

}
