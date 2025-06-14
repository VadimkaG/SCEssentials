package ru.seriouscompany.essentials.commands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;

public class CWorld implements CommandExecutor {
	
	public static final String PREFIX_COMMAND_LIST = "list";
	public static final String PREFIX_COMMAND_LOAD = "load";
	public static final String PREFIX_COMMAND_UNLOAD = "unload";
	public static final String PREFIX_COMMAND_AUTOLOAD = "autoload";
	public static final String PREFIX_COMMAND_TELEPORT = "tp";
	
	private CWorldList     commandList;
	private CWorldLoad     commandLoad;
	private CWorldUnload   commandUnload;
	private CWorldLoadAuto commandLoadAuto;
	private CommandExecutor commandTeleport;
	
	public CWorld(SCCore plugin, Config config) {
		commandList = new CWorldList(); 
		commandLoad = new CWorldLoad();
		commandUnload = new CWorldUnload();
		commandLoadAuto = new CWorldLoadAuto(plugin,config);
		commandTeleport = new CTeleportWorld();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length >= 1) {
			String[] newArgs;
			if (args.length > 1) newArgs = Arrays.copyOfRange(args, 1, args.length);
			else newArgs = new String[0];
			boolean commandSuccess = false;
			switch (args[0].trim()) {
			case PREFIX_COMMAND_LIST:
				commandSuccess = commandList.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_LIST, newArgs);
				break;
			case PREFIX_COMMAND_LOAD:
				commandSuccess = commandLoad.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_LOAD, newArgs);
				break;
			case PREFIX_COMMAND_UNLOAD:
				commandSuccess = commandUnload.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_UNLOAD, newArgs);
				break;
			case PREFIX_COMMAND_AUTOLOAD:
				commandSuccess = commandLoadAuto.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_AUTOLOAD, newArgs);
				break;
			case PREFIX_COMMAND_TELEPORT:
				commandSuccess = commandTeleport.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_TELEPORT, newArgs);
				break;
			}
			if (!commandSuccess)
				printHelp(sender,label);
		} else {
			printHelp(sender,label);
		}
		return true;
	}

	private void printHelp(CommandSender sender, String label) {
		sender.sendMessage(
				"/"+label+" "+PREFIX_COMMAND_LIST+" - Список миров"
				+"\n"+
				"/"+label+" "+PREFIX_COMMAND_TELEPORT+" <Мир> [Игрок] - Телепортироваться в мир"
				+"\n"+
				"/"+label+" "+PREFIX_COMMAND_LOAD+" <Мир> <Генератор> - Загрузить мир"
				+"\n"+
				"/"+label+" "+PREFIX_COMMAND_UNLOAD+" <Мир> - Отгрузить мир"
				+"\n"+
				"/"+label+" "+PREFIX_COMMAND_AUTOLOAD+" <Мир> <Генератор> - Автоматическая загрузка мира"
			);
	}

}
