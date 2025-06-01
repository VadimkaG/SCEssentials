package ru.seriouscompany.essentials.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.projectiles.ProjectileSource;

import ru.seriouscompany.essentials.meta.FreezeMeta;

public class EntityListener implements Listener {
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onConsumeItem(PlayerItemConsumeEvent e) {
		if (FreezeMeta.isFreezed(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onAttack(EntityDamageByEntityEvent e) {
		if (e.getEntityType() == EntityType.PLAYER && FreezeMeta.isFreezed((Player)e.getEntity())) {
			e.setDamage(0);
			e.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onTarget(EntityTargetEvent e) {
		if (e.getEntityType() == EntityType.PLAYER && FreezeMeta.isFreezed((Player)e.getEntity())) {
			e.setTarget(null);
			e.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		if (e.getEntityType() == EntityType.PLAYER && FreezeMeta.isFreezed((Player)e.getEntity()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onRegainHealth(EntityRegainHealthEvent e) {
		if (e.getEntityType() == EntityType.PLAYER && FreezeMeta.isFreezed((Player)e.getEntity())) {
			e.setAmount(0);
			e.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent e) {
		if (FreezeMeta.isFreezed(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onProjectileLaunch(ProjectileLaunchEvent e) {
		ProjectileSource shooter = e.getEntity().getShooter();
		if (shooter instanceof Player && FreezeMeta.isFreezed((Player) shooter))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onShoot(EntityShootBowEvent e) {
		if (e.getEntityType() == EntityType.PLAYER && FreezeMeta.isFreezed((Player)e.getEntity()))
			e.setCancelled(true);
	}
}
