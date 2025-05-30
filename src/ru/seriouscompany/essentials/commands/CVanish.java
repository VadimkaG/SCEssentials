package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValueAdapter;
import org.bukkit.plugin.Plugin;

import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.listeners.PlayerListener;

public class CVanish implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.vanish"))
			return true;
		if (!(sender instanceof Player))
			return true;
		Player target = (Player)sender;
		Plugin pl = SCCore.getInstance();

		YamlConfiguration config = PlayerListener.getPlayerStateConfig(target);
		if (target.hasMetadata(SCCore.METADATA_VANISHED)) {
			if (target.hasMetadata(SCCore.METADATA_VANISHED))
				target.removeMetadata(SCCore.METADATA_VANISHED, pl);
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.showPlayer(pl,target);
			}
			if (config != null)
				config.set(SCCore.METADATA_VANISHED, false);
			sender.sendMessage("Вы показались");
		} else {
			enableVanish(target);
			if (config != null)
				config.set(SCCore.METADATA_VANISHED, true);
			sender.sendMessage("Вы скрылись");
		}
		return true;
	}
	/**
	 * Включить ваниш для игрока
	 * @param target
	 */
	public static void enableVanish(Player target) {
		Plugin pl = SCCore.getInstance();
		PlayerListener.vanishedPlayers.add(target);
		target.setMetadata(SCCore.METADATA_VANISHED, new MetadataValueAdapter(pl) {
			@Override
			public boolean asBoolean() {
				return true;
			}
			@Override
			public Object value() {
				return true;
			}
			@Override
			public void invalidate() {}
		});
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!player.isOp() && player != target)
				player.hidePlayer(pl, target);
		}
	}

}
