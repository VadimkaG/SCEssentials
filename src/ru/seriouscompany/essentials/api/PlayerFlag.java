package ru.seriouscompany.essentials.api;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import ru.seriouscompany.essentials.SCCore;

public class PlayerFlag implements MetadataValue {
	
	protected Object value;
	
	public PlayerFlag(Object value) {
		this.value = value;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean asBoolean() {
		return (boolean)value;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte asByte() {
		return (byte)value;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double asDouble() {
		return (double)value;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float asFloat() {
		return (float)value;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int asInt() {
		return (int)value;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long asLong() {
		return (long)value;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public short asShort() {
		return (short)value;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asString() {
		return (String)value;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Plugin getOwningPlugin() {
		return SCCore.getInstance();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidate() {}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object value() {
		return value;
	}
	/**
	 * Установить значение
	 * @param value
	 */
	public void set(Object value) {
		this.value = value;
	}
	/**
	 * Установить параметр в мета данные игрока
	 * @param player - Игрок, которому будут установлен мета параметр
	 * @param flag    - Наименование мета данных
	 * @param value  - Устанавливаемое значение
	 */
	public static void setPlayerFlag(Player player, String flag, Object value) {
		player.setMetadata(flag, new PlayerFlag(value));
	}
	/**
	 * Имеет ли игрок флаг
	 * @param player
	 * @param flag
	 * @return
	 */
	public static boolean hasPlayerFlag(Player player, String flag) {
		return player.hasMetadata(flag);
	}
	/**
	 * Удалить флаг у игрока
	 * @param player
	 * @param flag
	 */
	public static void removePlayerFlag(Player player, String flag) {
		player.removeMetadata(flag, SCCore.getInstance());
	}
	/**
	 * Получить флаг
	 * @param player
	 * @param flag
	 * @return
	 */
	public static MetadataValue getPlayerFlag(Player player, String flag) {
		List<MetadataValue> metaArr = player.getMetadata(flag);
		for (MetadataValue meta:metaArr) {
			if (meta.getOwningPlugin().equals(SCCore.getInstance()))
				return meta;
		}
		return null;
	}
	/**
	 * Установлен ли флаг у игрока
	 * @param player
	 * @param flag
	 * @return
	 */
	public static boolean isSetPlayerFlag(Player player, String flag) {
		if (hasPlayerFlag(player,flag) && getPlayerFlag(player,flag) != null)
			return true;
		else return false;
	}

}
