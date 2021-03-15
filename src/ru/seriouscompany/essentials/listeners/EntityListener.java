package ru.seriouscompany.essentials.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.api.Utils;

public class EntityListener implements Listener {
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntityType() == EntityType.PLAYER && e.getEntity() instanceof Player) {
			Player player = (Player)e.getEntity();
			if (Utils.isPlayerAFKwait(player)) {
				Utils.cancelPlayerAFKwait(player);
				player.sendMessage("Вы получили урон, афк отменен.");
			}
			if (player != null && Utils.isPlayerFreezed(player)) {
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onAttack(EntityDamageByEntityEvent e) {
		if (e.getEntityType() == EntityType.PLAYER) {
			Player player = SCCore.getInstance().getServer().getPlayer(e.getEntity().getName());
			if (player != null && Utils.isPlayerFreezed(player)) {
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onTarget(EntityTargetEvent e) {
		if (e.getEntityType() == EntityType.PLAYER) {
			Player player = SCCore.getInstance().getServer().getPlayer(e.getEntity().getName());
			if (player != null && Utils.isPlayerFreezed(player)) {
				e.setTarget(null);
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		if (e.getEntityType() == EntityType.PLAYER) {
			Player player = SCCore.getInstance().getServer().getPlayer(e.getEntity().getName());
			if (player != null && Utils.isPlayerFreezed(player))
				e.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onRegainHealth(EntityRegainHealthEvent e) {
		if (e.getEntityType() == EntityType.PLAYER) {
			Player player = SCCore.getInstance().getServer().getPlayer(e.getEntity().getName());
			if (player != null && Utils.isPlayerFreezed(player)) {
				e.setAmount(0);
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onInteract(EntityInteractEvent e) {
		if (e.getEntityType() == EntityType.PLAYER) {
			Player player = SCCore.getInstance().getServer().getPlayer(e.getEntity().getName());
			if (player != null && Utils.isPlayerFreezed(player))
				e.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onProjectileLaunch(ProjectileLaunchEvent e) {
		Projectile projectile = e.getEntity();
		projectile.getShooter();
		if (projectile instanceof Player) {
			Player player = ((Player) projectile);
			if (player != null && Utils.isPlayerFreezed(player)) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onShoot(EntityShootBowEvent e) {
		if (e.getEntityType() == EntityType.PLAYER) {
			Player player = SCCore.getInstance().getServer().getPlayer(e.getEntity().getName());
			if (player != null && Utils.isPlayerFreezed(player))
				e.setCancelled(true);
		}
	}
}
