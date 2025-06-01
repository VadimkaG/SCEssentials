package ru.seriouscompany.essentials.meta;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class RequestMeta implements MetadataValue {

	public static final String METADATA_NAME = "PLAYER_REQUEST";
	
	protected Plugin plugin;
	protected Runnable action;
	protected BukkitRunnable waitingScheduler;
	
	public RequestMeta(Plugin plugin) {
		this.plugin = plugin;
		action = null;
	}
	public boolean has() {
		return action != null;
	}
	public void run() {
		action.run();
		if (waitingScheduler != null && !waitingScheduler.isCancelled()) {
			waitingScheduler.cancel();
			waitingScheduler = null;
		}
		action = null;
	}
	/**
	 * Активировать запрос
	 * @param act
	 * @param delay
	 */
	public void set(Runnable act, long delay) {
		action = act;
		waitingScheduler = new BukkitRunnable() {
			@Override
			public void run() {
				action = null;
				waitingScheduler = null;
			}
		};
		waitingScheduler.runTaskLater(plugin, delay);
	}
	/**
	 * Отменить запрос
	 */
	public void cancel() {
		if (action != null) {
			waitingScheduler.cancel();
			action = null;
			waitingScheduler = null;
		}
	}
	/**
	 * Присутствует ли какой-либо запрос
	 * @param player
	 * @return
	 */
	public static boolean hasRequest(Player player) {
		List<MetadataValue> metadata = player.getMetadata(METADATA_NAME);
		if (metadata.isEmpty())
			return false;
		return metadata.get(0).asBoolean();
	}
	/**
	 * Получить объект из метаданных игрока
	 * @param player - Игрок
	 * @param plugin - Плагин, которому будет принадлежать мета, если ранее не была создана
	 * @return
	 */
	public static RequestMeta from(Player player, Plugin plugin) {
		List<MetadataValue> metadata = player.getMetadata(METADATA_NAME);
		for (MetadataValue value : metadata) {
			if (value instanceof RequestMeta)
				return (RequestMeta)value;
		}
		RequestMeta meta = new RequestMeta(plugin);
		player.setMetadata(METADATA_NAME, meta);
		return meta;
	}
	
	@Override
	public boolean asBoolean() {return action != null;}
	@Override
	public byte asByte() {return 0;}
	@Override
	public double asDouble() {return 0;}
	@Override
	public float asFloat() {return 0;}
	@Override
	public int asInt() {return 0;}
	@Override
	public long asLong() {return 0;}
	@Override
	public short asShort() {return 0;}
	@Override
	public String asString() {return null;}
	@Override
	public Plugin getOwningPlugin() {return plugin;}
	@Override
	public void invalidate() {}
	@Override
	public Object value() {return action;}

}
