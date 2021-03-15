package ru.seriouscompany.essentials.api;

import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public abstract class Utils {
	/**
	 * Рескрасить текст
	 * Заменить & на \00A7
	 * @param msg - Сообщение в котором нужно заменить символ
	 * @return
	 */
	public static String replaceColorCodes(String msg) {
		if (msg == null) return "";
		return msg.replaceAll("&([a-z0-9])", "\u00A7$1");
	}
	/**
	 * Проверить находится ли игрок в АФК
	 * @param player - игрок
	 * @return boolean
	 */
	public static boolean isPlayerAFK(Player player) {
		MetadataValue afkFlag;
		return PlayerFlag.hasPlayerFlag(player, "AFK") && (afkFlag = PlayerFlag.getPlayerFlag(player, "AFK")) != null && afkFlag.asBoolean();
	}
	/**
	 * Установить АФК состояние игрока
	 * @param player - Игрок
	 * @param value  - Состояние АФК
	 */
	public static void setPlayerAFK(Player player, boolean value) {
		MetadataValue afkFlag;
		if ((afkFlag = PlayerFlag.getPlayerFlag(player, "AFK")) != null && afkFlag instanceof PlayerFlag) {
			((PlayerFlag)afkFlag).set(value);
		} else
			PlayerFlag.setPlayerFlag(player, "AFK", value);
		Utils.setPlayerFREEZE(player, value);
	}
	/**
	 * Проверить ожидает ли игрок, чтобы стать АФК
	 * @param player - игрок
	 * @return boolean
	 */
	public static boolean isPlayerAFKwait(Player player) {
		MetadataValue afkFlag;
		return PlayerFlag.hasPlayerFlag(player, "AFK_WAIT") && (afkFlag = PlayerFlag.getPlayerFlag(player, "AFK_WAIT")) != null && afkFlag.asBoolean();
	}
	/**
	 * Установить состояние ожидания АФК
	 * @param player - Игрок
	 * @param value  - Состояние АФК
	 */
	public static void setPlayerAFKwait(Player player, boolean value) {
		MetadataValue afkFlag;
		if ((afkFlag = PlayerFlag.getPlayerFlag(player, "AFK_WAIT")) != null && afkFlag instanceof PlayerFlag) {
			((PlayerFlag)afkFlag).set(value);
		} else
			PlayerFlag.setPlayerFlag(player, "AFK_WAIT", value);
	}
	/**
	 * Заморожен ли игрок
	 * @param player - игрок
	 * @return
	 */
	public static boolean isPlayerFreezed(Player player) {
		MetadataValue afkFlag;
		return PlayerFlag.hasPlayerFlag(player, "FREEZED") && (afkFlag = PlayerFlag.getPlayerFlag(player, "FREEZED")) != null && afkFlag.asBoolean();
	}
	/**
	 * Заморозить или разморозить игрока
	 * @param player - игрок
	 * @param value  - состояние заморозки
	 */
	public static void setPlayerFREEZE(Player player, boolean value) {
		MetadataValue afkFlag;
		if ((afkFlag = PlayerFlag.getPlayerFlag(player, "FREEZED")) != null && afkFlag instanceof PlayerFlag) {
			((PlayerFlag)afkFlag).set(value);
		} else
			PlayerFlag.setPlayerFlag(player, "FREEZED", value);
	}
}
