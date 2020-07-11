package ru.seriouscompany.essentials.api;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class PlayerFreezeContainer {
	
	private static HashMap<Player,PlayerFreezeContainer> PLAYERS = new HashMap<Player,PlayerFreezeContainer>();
	
	private Player PLAYER;
	
	public PlayerFreezeContainer(Player player) {
		PLAYER = player;
		PLAYERS.put(PLAYER, this);
	}
	/**
	 * Получить пользователя
	 * @return
	 */
	public Player getPlayer() {
		return PLAYER;
	}
	/**
	 * Авторизуется ли пользователь
	 * @param player - Объект пользователя
	 * @return
	 */
	public static boolean contains(Player player) {
		return PLAYERS.containsKey(player);
	}
	/**
	 * Получить список авторизующихся пользователей
	 * @return
	 */
	public static HashMap<Player,PlayerFreezeContainer> getPlayers() {
		return PLAYERS;
	}
	/**
	 * Получить контейнер авторизующихся пользователя
	 * @param player - Объект пользователя
	 * @return
	 */
	public static PlayerFreezeContainer getStorager(Player player) {
		return PLAYERS.get(player);
	}
	/**
	 * Удалить пользователя из базы авторизующихся пользователей
	 * @param player - Объект пользователя
	 */
	public static void removeStorager(Player player) {
		if (PLAYERS.containsKey(player)) {
			PLAYERS.remove(player);
		}
	}
}
