package ru.seriouscompany.essentials.meta;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class FreezeMeta implements MetadataValue {
	
	public static final String METADATA_NAME = "FREEZED";

	protected Plugin plugin;
	protected boolean isFreezed;
	
	public FreezeMeta(Plugin plugin) {
		this.plugin = plugin;
		isFreezed = false;
	}
	public void set(boolean value) {
		isFreezed = value;
	}
	public static boolean isFreezed(Player player) {
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
	public static FreezeMeta from(Player player, Plugin plugin) {
		List<MetadataValue> metadata = player.getMetadata(METADATA_NAME);
		for (MetadataValue value : metadata) {
			if (value instanceof FreezeMeta)
				return (FreezeMeta)value;
		}
		FreezeMeta meta = new FreezeMeta(plugin);
		player.setMetadata(METADATA_NAME, meta);
		return meta;
	}
	@Override
	public boolean asBoolean() {return isFreezed;}
	@Override
	public byte asByte() {return 0;}
	@Override
	public double asDouble() {	return 0;}
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
	public Object value() {return isFreezed;}
}
