package ru.seriouscompany.essentials.tasks;

import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.api.Utils;

public class AFKTask extends BukkitRunnable {

	@Override
	public void run() {
		Collection<? extends Player> players = SCCore.getInstance().getServer().getOnlinePlayers();
		for (Player player: players) {
			List<MetadataValue> metavalue = player.getMetadata("lastActive");
			if (metavalue.size() > 0) {
				long not_active = ((System.currentTimeMillis() - metavalue.get(0).asLong())/1000)/60;
				if (Utils.isPlayerAFK(player)) {
					if (Config.AFK_KICK > 0 && not_active >= Config.AFK_KICK)
						player.kickPlayer(Config.AFK_KICK_REASON.replace("%TIME%", String.valueOf(Config.AFK_KICK)));
				} else if (Config.AFK_AUTO > 0 && not_active >= Config.AFK_AUTO && !Utils.isPlayerFreezed(player)) {
					Utils.setPlayerAFK(player, true);
					Bukkit.broadcastMessage(Config.AFK_ON.replace("%PLAYER%", player.getName()));
				}
			}
		}
	}

}
