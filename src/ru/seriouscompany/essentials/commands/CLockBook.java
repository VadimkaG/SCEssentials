package ru.seriouscompany.essentials.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import ru.seriouscompany.essentials.Lang;

public class CLockBook implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.lockbook")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (sender instanceof Player) {
			Player player = (Player) sender;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (item.getType() != Material.WRITTEN_BOOK) {
				sender.sendMessage("Вы должны держать в руках подписанную книгу");
				return true;
			}

			ItemMeta meta = item.getItemMeta();
			if (meta instanceof BookMeta) {
				BookMeta bookMeta = (BookMeta)meta;
				ItemStack newItem = new ItemStack(Material.WRITABLE_BOOK);
				newItem.setItemMeta(bookMeta);
				player.getInventory().setItemInMainHand(newItem);
			}
		}
		return false;
	}

}
