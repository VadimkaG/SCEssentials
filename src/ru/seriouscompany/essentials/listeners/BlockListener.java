package ru.seriouscompany.essentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import ru.seriouscompany.essentials.api.Utils;

public class BlockListener implements Listener {
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (Utils.isPlayerFreezed(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent e) {
		if (Utils.isPlayerFreezed(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
}
