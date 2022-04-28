package ru.seriouscompany.essentials.tasks;

import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import ru.seriouscompany.essentials.Lang;
import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.api.Utils;

public class AFKTask extends BukkitRunnable {

	@Override
	public void run() {
		Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
		for (Player player: players) {
			List<MetadataValue> metavalue = player.getMetadata("lastActive");
			if (metavalue.size() > 0) {
				long not_active = ((System.currentTimeMillis() - metavalue.get(0).asLong())/1000)/60;
				long auto_afk_dely = SCCore.getAutoAFKDely();
				if (Utils.isPlayerAFK(player)) {
					long auto_kick = SCCore.getAutoKickTime();
					if (auto_kick > 0 && not_active >= auto_kick)
						player.kickPlayer(Lang.AFK_KICK_REASON.toString().replace("%TIME%", String.valueOf(auto_kick)));
				} else if (auto_afk_dely > 0 && not_active >= auto_afk_dely && !Utils.isPlayerFreezed(player)) {
					Utils.setPlayerAFK(player, true);
					Bukkit.broadcastMessage(Lang.AFK_ON.toString().replace("%PLAYER%", player.getName()));
				}
			}
		}
	}

}
