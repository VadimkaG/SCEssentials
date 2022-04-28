package ru.seriouscompany.essentials.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import ru.seriouscompany.essentials.Lang;
import ru.seriouscompany.essentials.SCCore;
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
		if (
				SCCore.combatKickerEnabled()
				&&
				PlayerFlag.getPlayerFlag(player, "IN_COMBAT").asBoolean()
				&& 
				!player.isPermissionSet("scessentials.combat")
			) {
			player.setHealth(0);
		}
		e.getPlayer().setSleepingIgnored(false);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (SCCore.afkTeamEnabled()) Utils.removePlayerFromAfkTeam(player);
		PlayerFlag.setPlayerFlag(player, "PASSIVE_MODE", false);
		PlayerFlag.setPlayerFlag(player, "IN_COMBAT", false);
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
		
		if(SCCore.combatMessagesEnabled() && PlayerFlag.getPlayerFlag(player, "IN_COMBAT").asBoolean())
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Lang.COMBAT_IN_YOU.toString()));
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
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			
			// Пассивный режим
			if (PlayerFlag.getPlayerFlag((Player)e.getEntity(), "PASSIVE_MODE").asBoolean()) {
				e.setCancelled(true);
				return;
			}

			Player player = (Player)e.getEntity();

			// Если игрок заморожен
			if (Utils.isPlayerFreezed(player)) {
				e.setCancelled(true);
				return;
			}

		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onDamageEntity(EntityDamageByEntityEvent e) {

		// Если игрок пробует ударить другого игрока
		if (e.getDamager() instanceof Player) {

			Player damager = (Player)e.getDamager();

			// Если атакуюущий заморожен
			if (Utils.isPlayerFreezed(damager)) {
				e.setCancelled(true);
				return;
			} else
				PlayerFlag.setPlayerFlag(damager, "lastActive", System.currentTimeMillis());

			// Пассивный режим
			if (PlayerFlag.getPlayerFlag(damager, "PASSIVE_MODE").asBoolean()) {
				e.setCancelled(true);
				return;
			}
		}
		
		// Режим боя
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			final Player player = (Player) e.getEntity();
			PlayerFlag.setPlayerFlag(player, "IN_COMBAT", true);
			
			boolean combatMessages = SCCore.combatMessagesEnabled();
			
			if (combatMessages)
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Lang.COMBAT_IN_YOU.toString()));
			
			long combatTime = SCCore.combatTime();
			
			if (combatTime > 0)
				Bukkit.getScheduler().runTaskLater(SCCore.getInstance(), () -> {
					PlayerFlag.setPlayerFlag(player, "IN_COMBAT", false);
					if (combatMessages) player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Lang.COMBAT_OUT_YOU.toString()));
				}, 20L * combatTime);
		}
	}
}
