package ru.seriouscompany.essentials.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ru.seriouscompany.essentials.api.Utils;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void onExit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if (Utils.isPlayerAFK(player))
			Utils.setPlayerAFK(player, false);
		if (Utils.isPlayerFreezed(player))
			Utils.setPlayerFREEZE(player, false);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (Utils.isPlayerFreezed(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e) {
		if (Utils.isPlayerFreezed(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onMove(PlayerMoveEvent e) {
		if (Utils.isPlayerFreezed(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onDropItem(PlayerDropItemEvent e) {
			if (Utils.isPlayerFreezed(e.getPlayer()))
				e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onItemClicked(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player && Utils.isPlayerFreezed((Player)e.getWhoClicked()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onConsumeItem(PlayerItemConsumeEvent e) {
		if (Utils.isPlayerFreezed((Player) e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onChangeHand(PlayerItemHeldEvent e) {
		if (Utils.isPlayerFreezed((Player) e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent e) {
		if (Utils.isPlayerFreezed(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onInteractEntity(PlayerInteractAtEntityEvent e) {
		if (Utils.isPlayerFreezed(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onDamageEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			if (Utils.isPlayerFreezed(player))
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		e.getPlayer().setSleepingIgnored(false);
	}
}
