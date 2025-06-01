package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ru.seriouscompany.essentials.Lang;
import ru.seriouscompany.essentials.meta.PassiveModeMeta;

public class CPassiveModeToggle implements CommandExecutor {
	
	protected Plugin plugin;
	
	public CPassiveModeToggle(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.passivemode")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(Lang.COMMAND_FOR_PLAYERS.toString());
			return true;
		}
		Player player = (Player)sender;
		PassiveModeMeta meta = PassiveModeMeta.from(player,plugin);
		if (meta.asBoolean()) {
			meta.set(false);
			player.sendMessage(Lang.PASSIVE_MODE_OFF.toString());
		} else {
			meta.set(true);
			player.sendMessage(Lang.PASSIVE_MODE_ON.toString());
		}
		
		return true;
	}

}
