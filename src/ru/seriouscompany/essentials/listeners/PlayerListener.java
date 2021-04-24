package ru.seriouscompany.essentials.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.api.PlayerFlag;
import ru.seriouscompany.essentials.api.Utils;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void onExit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if (Utils.isPlayerAFK(player)) {
			Utils.setPlayerAFK(player, false);
		}
		if (Utils.isPlayerFreezed(player))
			Utils.setPlayerFREEZE(player, false);
		if (
				Config.PLAYERS_IN_COMBAT.containsKey(player) 
				&& 
				!player.isPermissionSet("scessentials.combat")
				&&
				Config.KILL_COMBAT_LEAVER
			) {
			player.setHealth(0);
		}
		e.getPlayer().setSleepingIgnored(false);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (Config.AFK_TEAM) Utils.removePlayerFromAfkTeam(player);
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
		if(Config.PLAYERS_IN_COMBAT.containsKey(player) && Config.COMBAT_MESSAGES)
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Config.IN_COMBAT));
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
		if(e.getEntityType() == EntityType.PLAYER && e.getDamager().getType() == EntityType.PLAYER)
        {
            Player player = (Player) e.getEntity();
			if (Config.COMBAT_MESSAGES) player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Config.IN_COMBAT));
            if(Config.PLAYERS_IN_COMBAT.containsKey(player))
            {
            	Config.PLAYERS_IN_COMBAT.get(player).cancel();
            	Config.PLAYERS_IN_COMBAT.remove(player);
            }
            BukkitTask bukkitTask = Bukkit.getScheduler().runTaskLater(SCCore.getInstance(), () ->
            {
            	if (Config.COMBAT_MESSAGES) player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Config.OUT_COMBAT));
				Config.PLAYERS_IN_COMBAT.get(player).cancel();
				Config.PLAYERS_IN_COMBAT.remove(player);
            }, 20L * Config.COMBAT_TIME);
            Config.PLAYERS_IN_COMBAT.put(player, bukkitTask);
        }
	}
}
