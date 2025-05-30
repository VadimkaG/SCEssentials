package ru.seriouscompany.essentials.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
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
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.api.PlayerFlag;
import ru.seriouscompany.essentials.api.Utils;
import ru.seriouscompany.essentials.commands.CVanish;

public class PlayerListener implements Listener {
	
	// Скрытиые игроки
	public static List<Player> vanishedPlayers = new ArrayList<>();

	/**
	 * Получить конфиг сохранения данных игрока, если есть
	 * @param player
	 * @return
	 */
	public static YamlConfiguration getPlayerStateConfig(Player player) {
		if (player.hasMetadata("PlayerStateConfig")) {
			List<MetadataValue> metadata = player.getMetadata("PlayerStateConfig");
			for (MetadataValue value : metadata) {
				if (value.value() instanceof YamlConfiguration)
					return (YamlConfiguration)value.value();
			}
		}
		return null;
	}
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
		
		if (PlayerFlag.isSetPlayerFlag(player, "OLD_FLY_SPEED")) {
			MetadataValue flag = PlayerFlag.getPlayerFlag(player, "OLD_FLY_SPEED");
			if (flag != null)
				player.setFlySpeed(flag.asFloat());
		}
		
		if (PlayerFlag.isSetPlayerFlag(player, "OLD_WALK_SPEED")) {
			MetadataValue flag = PlayerFlag.getPlayerFlag(player, "OLD_WALK_SPEED");
			if (flag != null)
				player.setWalkSpeed(flag.asFloat());
		}
		
		// Удалить скрытого игрока при выходе
		if (vanishedPlayers.contains(player))
			vanishedPlayers.remove(player);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (SCCore.afkTeamEnabled()) Utils.removePlayerFromAfkTeam(player);
		PlayerFlag.setPlayerFlag(player, "PASSIVE_MODE", false);
		PlayerFlag.setPlayerFlag(player, "IN_COMBAT", false);
		Plugin pl = SCCore.getInstance();
		
		// Скрыть игрока
		if (!player.isOp())
		for (Player target : vanishedPlayers) {
			if (player != target)
				player.hidePlayer(pl, target);
		}
		
		YamlConfiguration config = getPlayerStateConfig(player);
		if (config != null && config.getBoolean(SCCore.METADATA_VANISHED,false)) {
			if (player.isPermissionSet("scessentials.vanish")) {
				CVanish.enableVanish(player);
			} else
				config.set(SCCore.METADATA_VANISHED, null);
		}
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
		
//		if(SCCore.combatMessagesEnabled() && PlayerFlag.getPlayerFlag(player, "IN_COMBAT").asBoolean())
//			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Lang.COMBAT_IN_YOU.toString()));
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
			
//			final boolean combatMessages = SCCore.combatMessagesEnabled();
			
//			if (combatMessages)
//				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Lang.COMBAT_IN_YOU.toString()));
			
			long combatTime = SCCore.combatTime();
			
			if (combatTime > 0)
				Bukkit.getScheduler().runTaskLater(SCCore.getInstance(), () -> {
					PlayerFlag.setPlayerFlag(player, "IN_COMBAT", false);
//					if (combatMessages) player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Lang.COMBAT_OUT_YOU.toString()));
				}, 20L * combatTime);
		}
	}
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		
		// Нельзя телепортироваться в блок!
		if (e.getCause().equals(TeleportCause.ENDER_PEARL) && SCCore.disallowPearlTeleportIntoBlock()) {
			Block block = e.getTo().getBlock();
			if (block != null) {
				
				// Если это не прозрачный блок, то просто отменяем телепорт
				if (!block.getType().equals(Material.AIR) && block.getType().isSolid())
					e.setCancelled(true);
				
				// Если это прозрачный блок, то вычисляем препятствия вокруг и корректируем телепорт
				else {
					
					// Проверяем по координате x
					Location loc = e.getTo().clone();
					double x = e.getTo().getX()-e.getTo().getBlockX();
					if (x > 0.7) {
						loc = loc.add(1, 1, 0);
					} else if (x < 0.3) {
						loc = loc.add(-1, 1, 0);
					}
					block = loc.getBlock();
					if (!block.getType().equals(Material.AIR) && block.getType().isSolid()) {
						e.setTo(new Location(e.getTo().getWorld(), e.getTo().getBlockX()+0.5, e.getTo().getBlockY(), e.getTo().getBlockZ()+0.5,e.getTo().getYaw(), e.getTo().getPitch()));
						return;
					}

					// Проверяем по координате z
					loc = e.getTo().clone();
					x = e.getTo().getZ()-e.getTo().getBlockZ();
					if (x > 0.7) {
						loc = loc.add(0, 1, 1);
					} else if (x < 0.3) {
						loc = loc.add(0, 1, -1);
					}
					block = loc.getBlock();
					if (!block.getType().equals(Material.AIR) && block.getType().isSolid()) {
						e.setTo(new Location(e.getTo().getWorld(), e.getTo().getBlockX()+0.5, e.getTo().getBlockY(), e.getTo().getBlockZ()+0.5,e.getTo().getYaw(), e.getTo().getPitch()));
						return;
					}
				}
			}
		}
	}
}
