package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ru.seriouscompany.essentials.Config;

public class CUndress implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.undress") ) {
			sender.sendMessage(Config.PERMISSION_DENY);
			return true;
		}
		if (args.length != 1)
			return false;
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Config.PLAYER_NOT_FOUND.replace("%PLAYER%", args[0]));
			return true;
		}
		ItemStack item;
		
		// Снимаем шлем
		if (target.getInventory().firstEmpty() >= 0) {
			item = target.getInventory().getHelmet();
			if (item != null) {
				target.getInventory().setHelmet(null);
				target.getInventory().addItem(item);
			}
		} else {
			sender.sendMessage(Config.UNDRESSED_FULL);
			return true;
		}
		
		// Сн имаем нагрудник
		if (target.getInventory().firstEmpty() >= 0) {
			item = target.getInventory().getChestplate();
			if (item != null) {
				target.getInventory().setChestplate(null);
				target.getInventory().addItem(item);
			}
		} else {
			sender.sendMessage(Config.UNDRESSED_FULL);
			return true;
		}
		
		// Снимаем штаны
		if (target.getInventory().firstEmpty() >= 0) {
			item = target.getInventory().getLeggings();
			if (item != null) {
				target.getInventory().setLeggings(null);
				target.getInventory().addItem(item);
			}
		} else {
			sender.sendMessage(Config.UNDRESSED_FULL);
			return true;
		}
		
		// Снимаем ботинки
		if (target.getInventory().firstEmpty() >= 0) {
			item = target.getInventory().getBoots();
			if (item != null) {
				target.getInventory().setBoots(null);
				target.getInventory().addItem(item);
			}
		} else {
			sender.sendMessage(Config.UNDRESSED_FULL);
			return true;
		}
		
		// Снимаем предмет со второстепенной руки
		if (target.getInventory().firstEmpty() >= 0) {
			item = target.getInventory().getItemInOffHand();
			if (item != null) {
				target.getInventory().setItemInOffHand(null);
				target.getInventory().addItem(item);
			}
		} else {
			sender.sendMessage(Config.UNDRESSED_FULL);
			return true;
		}
		
		sender.sendMessage(Config.UNDRESSED);
		target.sendMessage(Config.UNDRESSED_TARGET
				.replaceAll("%PLAYER%", sender.getName())
			);
		
		return true;
	}
	
}
