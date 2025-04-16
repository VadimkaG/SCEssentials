package ru.seriouscompany.essentials.listeners;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import ru.seriouscompany.essentials.Lang;

public class AntiDOSListener implements Listener {
	
	public static final long BAN_STANDART = 15000;
	public static final long BAN_SLOW = 180000;

	// Банлист
	HashMap<String, Long> banTimeouts;
	// Счетчик количества игроков
	byte playerCounter;
	// Время захода на сервер
	long joinTime;
	// Время до остановки медленного режима
	long emergencyTimeout;
	// Время до следующей чистки банлиста
	long clearTime;
	// Время, на которое игрок будет забанен при заходе
	long banTime;
	// Последний этап обороны. Полная блокировка входа на сервер
	boolean disableJoin;
	
	public AntiDOSListener() {
		banTimeouts = new HashMap<>();
		playerCounter = 0;
		emergencyTimeout = 0;
		joinTime = System.currentTimeMillis();
		clearTime = joinTime+30000;
		banTime = BAN_STANDART;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(AsyncPlayerPreLoginEvent e) {
		long time = System.currentTimeMillis();
		
		// Последний этап обороны. Блокируем вход полностью
		if (disableJoin) {
			if (time > clearTime) {
				clearTime = time + BAN_SLOW;
				if (time > joinTime || playerCounter < 20) {
					banTimeouts.clear();
					playerCounter = 0;
					disableJoin = false;
					onJoin(e);
					return;
				}
			}
			if (playerCounter < 125)
				playerCounter++;
			joinTime = time+BAN_STANDART;
			
			e.disallow(Result.KICK_OTHER, Lang.ANTIDOS_FULL.toString());
			return;
		}
		
		String addres = e.getAddress().getHostAddress();
		
		// Кикаем забаненых
		if (banTimeouts.containsKey(addres)) {
			long timeout = banTimeouts.get(addres);
			if (time < timeout) {
				e.disallow(Result.KICK_BANNED, Lang.ANTIDOS_TEMPBAN.toString().replace("%SECONDS%", String.valueOf(((timeout-time)/1000))));
				return;
			} else
				banTimeouts.remove(addres);
		}

		// Стандартный режим
		if (emergencyTimeout == 0) {

			// Если с последнего захода прошла секунда
			if (time > joinTime+1000) {
				playerCounter = 0;
			
			// Если с последнего захода прошло менее секунды
			} else {
				playerCounter++;
				
				// Если много человек заходят быстрее, чем за секунду, то врубаем медленный режим
				if (playerCounter > 40) {
					emergencyTimeout = time + 60000;
					banTime = BAN_SLOW;
				}
			}
			
			// Время последнего захода в игру
			joinTime = time;

		// Медленный режим
		} else {
			if (time < joinTime) {
				e.disallow(Result.KICK_OTHER, Lang.ANTIDOS_SLOWMODE.toString());
				return;
			
			// Выключение медленного режима
			} else if (time > emergencyTimeout && time > joinTime+5000) {
				emergencyTimeout = 0;
				banTime = BAN_STANDART;
				joinTime = time;
			} else
				joinTime = time + 1000;
		}
		
		// Чистка
		if (time > clearTime) {
			clearTime = time + BAN_SLOW;
			if (banTimeouts.size() > 0) {
				clearTimeouts(time);
				if (banTimeouts.size() > 1000) {
					disableJoin = true;
					playerCounter = 0;
					emergencyTimeout = 0;
					banTimeouts.clear();
					return;
				}
			}
		}
		
		// Кикаем пиратки с рандомными никами
		String playerName = e.getName();
		if (playerName.length() >= 6 && playerName.substring(0,6).equalsIgnoreCase("player")) {
			e.disallow(Result.KICK_OTHER, "У вас не подходящий ник. Если вы с пиратской версии QuestCraft, то вам необходимо использовать лицензию.");
			return;
		}
		
		// Добавляем в банлист
		if (banTimeouts.containsKey(addres)) {
			banTimeouts.replace(addres, time + banTime);
		} else
			banTimeouts.put(addres, time + banTime);
	}
	/**
	 * Очистить баны
	 * @param time
	 */
	void clearTimeouts(long time) {
		final Object[] keys = banTimeouts.keySet().toArray();
		for (int i = keys.length-1; i >= 0; i--) {
			if (banTimeouts.containsKey(keys[i]) && banTimeouts.get(keys[i]) < time) {
				banTimeouts.remove(keys[i]);
				i--;
			}
		}
	}
}
