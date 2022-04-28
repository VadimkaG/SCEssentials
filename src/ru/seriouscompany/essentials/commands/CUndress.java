package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ru.seriouscompany.essentials.Lang;

public class CUndress implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.undress") ) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (args.length != 1)
			return false;
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Lang.PLAYER_NOT_FOUND.toString().replace("%PLAYER%", args[0]));
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
			sender.sendMessage(Lang.UNDRESSED_FULL.toString());
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
			sender.sendMessage(Lang.UNDRESSED_FULL.toString());
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
			sender.sendMessage(Lang.UNDRESSED_FULL.toString());
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
			sender.sendMessage(Lang.UNDRESSED_FULL.toString());
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
			sender.sendMessage(Lang.UNDRESSED_FULL.toString());
			return true;
		}
		
		sender.sendMessage(Lang.UNDRESSED.toString());
		target.sendMessage(Lang.UNDRESSED_TARGET.toString()
				.replaceAll("%PLAYER%", sender.getName())
			);
		
		return true;
	}
	
}
