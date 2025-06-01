package ru.seriouscompany.essentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import ru.seriouscompany.essentials.meta.FreezeMeta;

public class BlockListener implements Listener {
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (FreezeMeta.isFreezed(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent e) {
		if (FreezeMeta.isFreezed(e.getPlayer()))
			e.setCancelled(true);
	}
}
