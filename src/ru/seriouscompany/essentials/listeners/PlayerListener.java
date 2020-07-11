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

import ru.seriouscompany.essentials.api.PlayerFreezeContainer;

public class PlayerListener implements Listener {
	
	/*@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onChat(AsyncPlayerChatEvent e) {
		
	}*/

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (PlayerFreezeContainer.contains(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e) {
		if (PlayerFreezeContainer.contains(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onMove(PlayerMoveEvent e) {
		if (PlayerFreezeContainer.contains(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onDropItem(PlayerDropItemEvent e) {
			if (PlayerFreezeContainer.contains(e.getPlayer()))
				e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onItemClicked(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player && PlayerFreezeContainer.contains((Player) e.getWhoClicked()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onConsumeItem(PlayerItemConsumeEvent e) {
		if (PlayerFreezeContainer.contains((Player) e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onChangeHand(PlayerItemHeldEvent e) {
		if (PlayerFreezeContainer.contains((Player) e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent e) {
		if (PlayerFreezeContainer.contains(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onInteractEntity(PlayerInteractAtEntityEvent e) {
		if (PlayerFreezeContainer.contains(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onDamageEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			if (PlayerFreezeContainer.contains(player))
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		e.getPlayer().setSleepingIgnored(false);
	}
}
