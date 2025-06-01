package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ru.seriouscompany.essentials.Lang;
import ru.seriouscompany.essentials.meta.RequestMeta;

public class CRequestAccept implements CommandExecutor {
	
	protected Plugin plugin;
	
	public CRequestAccept(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			RequestMeta meta = RequestMeta.from(player,plugin);
			if (meta.has()) {
				meta.run();
			} else {
				sender.sendMessage("Вам нечего принимать");
			}
		} else {
			sender.sendMessage(Lang.COMMAND_FOR_PLAYERS.toString());
			return true;
		}
		return false;
	}

}
