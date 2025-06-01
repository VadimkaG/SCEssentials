package ru.seriouscompany.essentials.meta;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class CombatStateMeta implements MetadataValue {
	
	public static final String METADATA_NAME = "IN_COMBAT";

	protected Plugin plugin;
	protected boolean inCombat;
	
	public CombatStateMeta(Plugin plugin) {
		this.plugin = plugin;
		inCombat = false;
	}
	public void set(boolean value) {
		inCombat = value;
	}
	public static boolean inCombat(Player player) {
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
	public static CombatStateMeta from(Player player, Plugin plugin) {
		List<MetadataValue> metadata = player.getMetadata(METADATA_NAME);
		for (MetadataValue value : metadata) {
			if (value instanceof CombatStateMeta)
				return (CombatStateMeta)value;
		}
		CombatStateMeta meta = new CombatStateMeta(plugin);
		player.setMetadata(METADATA_NAME, meta);
		return meta;
	}
	
	@Override
	public boolean asBoolean() {return inCombat;}
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
	public Object value() {return inCombat;}

}
