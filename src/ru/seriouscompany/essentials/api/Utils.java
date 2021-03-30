package ru.seriouscompany.essentials.api;

import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import ru.seriouscompany.essentials.SCCore;

public abstract class Utils {
	/**
	 * Раскрасить текст
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
		if (!isPlayerAFK(player) && Utils.isPlayerFreezed(player))
			return;
		MetadataValue afkFlag;
		if ((afkFlag = PlayerFlag.getPlayerFlag(player, "AFK")) != null && afkFlag instanceof PlayerFlag) {
			((PlayerFlag)afkFlag).set(value);
		} else
			PlayerFlag.setPlayerFlag(player, "AFK", value);
		player.setSleepingIgnored(value);
		Utils.setPlayerFREEZE(player, value);
		PlayerFlag.setPlayerFlag(player, "lastActive", System.currentTimeMillis());
	}
	/**
	 * Проверить ожидает ли игрок, чтобы стать АФК
	 * @param player - игрок
	 * @return boolean
	 */
	public static boolean isPlayerAFKwait(Player player) {
		MetadataValue afkFlag;
		return PlayerFlag.hasPlayerFlag(player, "AFK_WAIT") && (afkFlag = PlayerFlag.getPlayerFlag(player, "AFK_WAIT")) != null && afkFlag.value() instanceof BukkitRunnable;
	}
	/**
	 * Начать ожидание АФК для игрока
	 * @param player - Игрок
	 * @param value  - Объект задачи на установку АФК
	 */
	public static void startPlayerAFKwait(Player player, BukkitRunnable value) {
		PlayerFlag.setPlayerFlag(player, "AFK_WAIT", value);
	}
	/**
	 * Отменить ожидание АФК у игрока
	 * @param player - ИГрок
	 */
	public static void cancelPlayerAFKwait(Player player) {
		MetadataValue afkFlag;
		if (PlayerFlag.hasPlayerFlag(player, "AFK_WAIT") && (afkFlag = PlayerFlag.getPlayerFlag(player, "AFK_WAIT")).value() instanceof BukkitRunnable) {
			((BukkitRunnable)afkFlag.value()).cancel();
			PlayerFlag.setPlayerFlag(player, "AFK_WAIT", false);
		}
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
	/**
	 * Запросить разрешение на действие у игрока
	 * @param player - Игрок, у которого будет запрошено разрешение на действие
	 * @param action - Действие
	 */
	public static void requset(Player player, Runnable action) {
		requset(player, action, 3000);
	}
	/**
	 * Запросить разрешение на действие у игрока
	 * @param player  - Игрок, у которого будет запрошено разрешение на действие
	 * @param action  - Действие
	 * @param delay   - Время, после которого запрос будет отменен
	 */
	public static void requset(Player player, Runnable action, long delay) {
		PlayerFlag.setPlayerFlag(player, "PLAYER_REQUEST", action);
		BukkitRunnable task = new BukkitRunnable() {
			@Override
			public void run() {
				PlayerFlag.removePlayerFlag(player, "PLAYER_REQUEST");
			}
		};
		task.runTaskLater(SCCore.getInstance(), delay);
	}
}
