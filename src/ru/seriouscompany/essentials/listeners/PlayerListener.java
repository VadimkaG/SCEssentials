package ru.seriouscompany.essentials.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.MetadataValue;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.meta.CombatStateMeta;
import ru.seriouscompany.essentials.meta.FreezeMeta;
import ru.seriouscompany.essentials.meta.PassiveModeMeta;
import ru.seriouscompany.essentials.meta.PlayerEquipmentHolder;
import ru.seriouscompany.essentials.meta.VanishMeta;

public class PlayerListener implements Listener {
	
	protected SCCore plugin;
	protected Config config;
	
	// Скрытиые игроки
	public static List<Player> vanishedPlayers = new ArrayList<>();
	
	public PlayerListener(SCCore plugin,Config config) {
		this.plugin = plugin;
		this.config = config;
	}

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
		if (
				config.isCombatKickEnabled()
				&&
				CombatStateMeta.inCombat(player)
				&& 
				!player.isPermissionSet("scessentials.combat")
			) {
			player.setHealth(0);
		}
		e.getPlayer().setSleepingIgnored(false);
		
		if (FreezeMeta.isFreezed(player))
			FreezeMeta.from(player, plugin).set(false);
		
		// Удалить скрытого игрока при выходе
		if (vanishedPlayers.contains(player))
			vanishedPlayers.remove(player);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
//		if (plugin.afkTeamEnabled()) Utils.removePlayerFromAfkTeam(player);
//		PlayerFlag.setPlayerFlag(player, "PASSIVE_MODE", false);
//		PlayerFlag.setPlayerFlag(player, "IN_COMBAT", false);
		
		// Скрыть игрока
		if (!player.isOp())
		for (Player target : vanishedPlayers) {
			if (player != target)
				player.hidePlayer(plugin, target);
		}
		
		YamlConfiguration config = getPlayerStateConfig(player);
		if (config != null && config.getBoolean(VanishMeta.METADATA_NAME,false)) {
			if (player.isPermissionSet("scessentials.vanish")) {
				VanishMeta.from(player,plugin).set(true);
			} else
				config.set(VanishMeta.METADATA_NAME, null);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (FreezeMeta.isFreezed(e.getPlayer()))
			e.setCancelled(true);
//		else
//			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e) {
		if (FreezeMeta.isFreezed(e.getPlayer()))
			e.setCancelled(true);
//		else
//			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onMove(PlayerMoveEvent e) {
//		Player player = e.getPlayer();
		if (FreezeMeta.isFreezed(e.getPlayer()))
			e.setCancelled(true);
//		else
//			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
		
//		if(SCCore.combatMessagesEnabled() && PlayerFlag.getPlayerFlag(player, "IN_COMBAT").asBoolean())
//			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Lang.COMBAT_IN_YOU.toString()));
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onDropItem(PlayerDropItemEvent e) {
		if (FreezeMeta.isFreezed(e.getPlayer()))
			e.setCancelled(true);
//		else
//			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onItemClicked(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player))
			return;
		if (FreezeMeta.isFreezed((Player)e.getWhoClicked())) {
			e.setCancelled(true);
			return;
		}
//		else if (e.getWhoClicked() instanceof Player)
//			PlayerFlag.setPlayerFlag((Player)e.getWhoClicked(), "lastActive", System.currentTimeMillis());

		// Редактирование обмундирования игрока
		Player player = (Player)e.getWhoClicked();
		if (e.getSlotType() == SlotType.ARMOR && player.hasMetadata(PlayerEquipmentHolder.METADATA_NAME)) {
			if (e.isRightClick()) {
				ItemStack item = e.getCursor().clone();
				item.setAmount(1);
				PlayerEquipmentHolder.from(player, plugin).onInventoryChange(e.getSlot(), item);
			} else
				PlayerEquipmentHolder.from(player, plugin).onInventoryChange(e.getSlot(), e.getCursor());
		} else if (e.getClickedInventory() != null) {
			if (e.getInventory().getHolder() instanceof PlayerEquipmentHolder) {
				if (e.isShiftClick() || e.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
					e.setCancelled(true);
					return;
				}
				if (!(e.getClickedInventory().getHolder() instanceof PlayerEquipmentHolder))
					return;
				((PlayerEquipmentHolder)e.getClickedInventory().getHolder()).changeInventory(
						e.getSlot(),
						PlayerEquipmentHolder.calculateNewItem(e.getCurrentItem(), e.getCursor(), e.getAction())
					);
			} else if (e.getClickedInventory() instanceof PlayerInventory && e.getSlot() == 40) {
				PlayerEquipmentHolder.from(player, plugin).onInventoryChange(
					40, 
					PlayerEquipmentHolder.calculateNewItem(e.getCurrentItem(), e.getCursor(), e.getAction())
				);
			}
		}
	}
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getInventory().getHolder() instanceof PlayerEquipmentHolder)
			((PlayerEquipmentHolder)e.getInventory().getHolder()).close();
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onConsumeItem(PlayerItemConsumeEvent e) {
		if (FreezeMeta.isFreezed(e.getPlayer()))
			e.setCancelled(true);
//		else
//			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onChangeHand(PlayerItemHeldEvent e) {
		if (FreezeMeta.isFreezed(e.getPlayer()))
			e.setCancelled(true);
//		else
//			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent e) {
		if (FreezeMeta.isFreezed(e.getPlayer()))
			e.setCancelled(true);
//		else
//			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onInteractEntity(PlayerInteractAtEntityEvent e) {
		if (FreezeMeta.isFreezed(e.getPlayer()))
			e.setCancelled(true);
//		else
//			PlayerFlag.setPlayerFlag(e.getPlayer(), "lastActive", System.currentTimeMillis());
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player player = (Player)e.getEntity();
			
			// Пассивный режим
			Entity damager = e.getDamageSource().getCausingEntity();
			if (damager != null && damager instanceof Player && PassiveModeMeta.isModeEnabled(player)) {
				e.setCancelled(true);
				return;
			}

			// Если игрок заморожен
			if (FreezeMeta.isFreezed(player)) {
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
			if (FreezeMeta.isFreezed(damager)) {
				e.setCancelled(true);
				return;
			} /*
				 * else PlayerFlag.setPlayerFlag(damager, "lastActive",
				 * System.currentTimeMillis());
				 */

			// Пассивный режим
			if (PassiveModeMeta.isModeEnabled(damager)) {
				e.setCancelled(true);
				return;
			}
			
		}
		// Режим боя
		long combatTime = config.getCombatDuration();
		if(e.getEntity() instanceof Player && combatTime > 0) {
			final Player player = (Player) e.getEntity();
			CombatStateMeta.from(player,plugin).set(true);
			
//			if (plugin.combatMessagesEnabled())
//				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Lang.COMBAT_IN_YOU.toString()));
			
				Bukkit.getScheduler().runTaskLater(plugin, () -> {
					CombatStateMeta.from(player,plugin).set(false);
//					if (combatMessages) player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Lang.COMBAT_OUT_YOU.toString()));
				}, 20L * combatTime);
		}
	}
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		
		// Нельзя телепортироваться в блок!
		if (e.getCause().equals(TeleportCause.ENDER_PEARL) && config.isEnderPearlPassthrowFixed()) {
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
