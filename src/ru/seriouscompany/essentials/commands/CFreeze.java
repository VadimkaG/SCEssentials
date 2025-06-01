package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ru.seriouscompany.essentials.Lang;
import ru.seriouscompany.essentials.meta.FreezeMeta;

public class CFreeze implements CommandExecutor {
	
	protected Plugin plugin;
	
	public CFreeze(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if (!sender.isPermissionSet("scessentials.freeze")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (FreezeMeta.isFreezed(player)) {
				sender.sendMessage(Lang.YOU_FREEZED.toString());
				return true;
			}
		}
		if (args.length == 1) {
			Player target = Bukkit.getServer().getPlayer(args[0]);
			FreezeMeta meta = FreezeMeta.from(target,plugin);
			if (target == null) {
				sender.sendMessage(Lang.PLAYER_NOT_FOUND.toString().replace("%PLAYER%", args[0]));
				return true;
			}
			if (target.equals(sender)) {
				sender.sendMessage(Lang.FREEZE_SELF.toString());
				return true;
			}
			if (meta.asBoolean()) {
				meta.set(false);
				if (sender != target)
					sender.sendMessage(Lang.FREEZE_OFF.toString().replace("%PLAYER%", target.getName()));
				target.sendMessage(Lang.FREEZE_OFF_TARGET.toString().replace("%PLAYER%", sender.getName()));
			} else {
				meta.set(true);
				if (sender != target)
					sender.sendMessage(Lang.FREEZE_ON.toString().replace("%PLAYER%", target.getName()));
				target.sendMessage(Lang.FREEZE_ON_TARGET.toString().replace("%PLAYER%", sender.getName()));
			}
			return true;
		}
		return false;
	}

}
