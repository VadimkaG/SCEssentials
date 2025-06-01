package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ru.seriouscompany.essentials.listeners.PlayerListener;
import ru.seriouscompany.essentials.meta.VanishMeta;

public class CVanish implements CommandExecutor {
	
	protected Plugin plugin;
	
	public CVanish(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.vanish"))
			return true;
		if (!(sender instanceof Player))
			return true;
		Player target = (Player)sender;

		YamlConfiguration config = PlayerListener.getPlayerStateConfig(target);
		VanishMeta meta = VanishMeta.from(target,plugin);
		if (meta.asBoolean()) {
			meta.set(false);
			if (config != null)
				config.set(VanishMeta.METADATA_NAME, false);
			sender.sendMessage("Вы показались");
		} else {
			meta.set(true);
			if (config != null)
				config.set(VanishMeta.METADATA_NAME, true);
			sender.sendMessage("Вы скрылись");
		}
		return true;
	}

}
