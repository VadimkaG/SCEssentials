package ru.seriouscompany.essentials.commands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CWorld implements CommandExecutor {
	
	public static final String PREFIX_COMMAND_LIST = "list";
	public static final String PREFIX_COMMAND_LOAD = "load";
	public static final String PREFIX_COMMAND_UNLOAD = "unload";
	
	private CWorldList commandList;
	private CWorldLoad commandLoad;
	private CWorldUnload commandUnload;
	
	public CWorld() {
		commandList = new CWorldList();
		commandLoad = new CWorldLoad();
		commandUnload = new CWorldUnload();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length >= 1) {
			String[] newArgs;
			if (args.length > 1) newArgs = Arrays.copyOfRange(args, 1, args.length);
			else newArgs = new String[0];
			switch (args[0].trim()) {
			case PREFIX_COMMAND_LIST:
				commandList.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_LIST, newArgs);
				break;
			case PREFIX_COMMAND_LOAD:
				commandLoad.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_LOAD, newArgs);
				break;
			case PREFIX_COMMAND_UNLOAD:
				commandUnload.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_UNLOAD, newArgs);
				break;
			default:
				printHelp(sender,label);
			}
		} else {
			printHelp(sender,label);
		}
		return true;
	}

	private void printHelp(CommandSender sender, String label) {
		sender.sendMessage("/"+label+" "+PREFIX_COMMAND_LIST+" - Список миров");
		sender.sendMessage("/"+label+" "+PREFIX_COMMAND_LOAD+" <Мир> - Загрузить мир");
		sender.sendMessage("/"+label+" "+PREFIX_COMMAND_UNLOAD+" <Мир> <Генератор> - Список миров");
	}

}
