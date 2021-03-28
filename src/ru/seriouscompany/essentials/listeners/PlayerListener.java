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

import ru.seriouscompany.essentials.api.PlayerFlag;
import ru.seriouscompany.essentials.api.Utils;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void onExit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if (Utils.isPlayerAFK(player))
			Utils.setPlayerAFK(player, false);
		if (Utils.isPlayerFreezed(player))
			Utils.setPlayerFREEZE(player, false);
		e.getPlayer().setSleepingIgnored(false);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (Utils.isPlayerFreezed(e.getPlayer()))
			e.setCancelled(true);
		else
			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e) {
		if (Utils.isPlayerFreezed(e.getPlayer()))
			e.setCancelled(true);
		else
			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if (Utils.isPlayerFreezed(player))
			e.setCancelled(true);
		else
			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onDropItem(PlayerDropItemEvent e) {
		if (Utils.isPlayerFreezed(e.getPlayer()))
			e.setCancelled(true);
		else
			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onItemClicked(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player && Utils.isPlayerFreezed((Player)e.getWhoClicked()))
			e.setCancelled(true);
		else if (e.getWhoClicked() instanceof Player)
			PlayerFlag.setPlayerFlag((Player)e.getWhoClicked(), "lastActive", System.currentTimeMillis());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onConsumeItem(PlayerItemConsumeEvent e) {
		if (Utils.isPlayerFreezed((Player) e.getPlayer()))
			e.setCancelled(true);
		else
			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onChangeHand(PlayerItemHeldEvent e) {
		if (Utils.isPlayerFreezed((Player) e.getPlayer()))
			e.setCancelled(true);
		else
			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent e) {
		if (Utils.isPlayerFreezed(e.getPlayer()))
			e.setCancelled(true);
		else
			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onInteractEntity(PlayerInteractAtEntityEvent e) {
		if (Utils.isPlayerFreezed(e.getPlayer()))
			e.setCancelled(true);
		else
			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onDamageEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			if (Utils.isPlayerFreezed((Player) e.getDamager()))
				e.setCancelled(true);
			else
				PlayerFlag.setPlayerFlag((Player) e.getDamager(), "lastActive", System.currentTimeMillis());
		}
	}
}
