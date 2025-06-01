package ru.seriouscompany.essentials.meta;

import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import ru.seriouscompany.essentials.listeners.PlayerListener;

public class VanishMeta implements MetadataValue {
	
	public static final String METADATA_NAME = "Vanished";

	protected Plugin plugin;
	protected Player target;
	protected boolean enabled;
	public VanishMeta(Plugin plugin,Player palyer) {
		this.plugin = plugin;
		this.target = palyer;
		enabled = false;
	}
	public void set(boolean value) {
		enabled = value;
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		if (value) {
			PlayerListener.vanishedPlayers.add(target);
			for (Player player : players) {
				if (!player.isOp() && player != target)
					player.hidePlayer(plugin, target);
			}
		} else {
			PlayerListener.vanishedPlayers.remove(target);
			for (Player player : players) {
				player.showPlayer(plugin,target);
			}
		}
	}
	/**
	 * Получить объект из метаданных игрока
	 * @param player - Игрок
	 * @param plugin - Плагин, которому будет принадлежать мета, если ранее не была создана
	 * @return
	 */
	public static VanishMeta from(Player player, Plugin plugin) {
		List<MetadataValue> metadata = player.getMetadata(METADATA_NAME);
		for (MetadataValue value : metadata) {
			if (value instanceof VanishMeta)
				return (VanishMeta)value;
		}
		VanishMeta meta = new VanishMeta(plugin, player);
		player.setMetadata(METADATA_NAME, meta);
		return meta;
	}
	@Override
	public boolean asBoolean() {return enabled;}
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
	public Object value() {return enabled;}
}
