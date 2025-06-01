package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ru.seriouscompany.essentials.Lang;
import ru.seriouscompany.essentials.meta.PlayerEquipmentHolder;

public class COpenInventory implements CommandExecutor {
	
	protected Plugin plugin;
	
	public COpenInventory(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.openinventory")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(Lang.COMMAND_FOR_PLAYERS.toString());
			return true;
		}
		if (args.length < 1) {
			return false;
		}
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Lang.PLAYER_NOT_FOUND.toString().replace("%PLAYER%", args[0]));
			return true;
		}

		Player player = (Player) sender;
		if (args.length < 2) {
			player.openInventory(target.getInventory());
			return true;
		}

		switch (args[1]) {
		case "e":
		case "ender":
			player.openInventory(target.getEnderChest());
			break;
		case "eq":
		case "equipment":
			PlayerEquipmentHolder holder = PlayerEquipmentHolder.from(target,plugin);
			player.openInventory(holder.getInventory());
			break;
		default:
			sender.sendMessage("- ender (e)\n - equipment (eq)");
		}
		return true;
	}
	
}