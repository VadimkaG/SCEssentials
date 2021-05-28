package ru.seriouscompany.essentials;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import ru.seriouscompany.essentials.api.Utils;

public abstract class Config {
	public static long WAIT_FOR_AFK = 75;
	public static long COMBAT_TIME = 5;
	public static float SPEED_DEFAULT = 0.2f;
	public static float SPEED_DEFAULT_FLY = 0.2f;

	public static boolean TIMEDSTOP_ENABLE = false;
	public static boolean TIMEDSTOP_FIXED_TIME = true;
	public static String TIMEDSTOP_TIME = "04:00:00:000";
	public static long TIMEDSTOP_DELY = 1;


	public static long AFK_AUTO = 0;
	public static long AFK_KICK = 0;
	public static boolean AFK_TEAM = false;

	public static boolean KILL_COMBAT_LEAVER = false;
	public static boolean COMBAT_MESSAGES = false;

	public static boolean WORLD_AUTO_LOAD = false;
	
	public static boolean allowTeleportImmunity = false;

	public static List<Integer> TIMEDSTOP_WARNS = new ArrayList<Integer>();

	public static String PERMISSION_DENY = "Вам не разрешено использывать данное действие";
	public static String COMMAND_FOR_PLAYERS = "Эта команда предназначена для игроков";

	public static String AFK_ALREADY_WAIT = "Вы уже ожидаете афк.";
	public static String AFK_ON = "* %PLAYER% теперь афк";
	public static String AFK_OFF = "* %PLAYER% больше не афк";
	public static String AFK_WAIT = "Подождите %TIME% секунд";
	public static String AFK_DENIED_WHEN_FREEZED = "Вы заморожены, вам нельзя использовать AFK";
	public static String AFK_KICK_REASON = "Вы находилесь в афк дольше %TIME% минут(ы)";
	public static String AFK_PREFIX = "[AFK] ";
	
	public static String FLY_DENYED = "Полет выключен сервером";
	public static String FLY_ON = "Теперь вы можете летать";
	public static String FLY_OFF = "Теперь вы не можете летать";
	public static String FLY_ON_OTHER = "Теперь %PLAYER% может летать";
	public static String FLY_OFF_OTHER = "Теперь %PLAYER% не может летать";
	public static String FLY_DENY_OTHER = "Вы не можете переключать полет другим игрокам";

	public static String HEAL_DENYED = "Вы не можете лечить других игроков";
	public static String HEAL_SUCCESS = "Вы вылечены";
	public static String HEAL_SUCCESS_OTHER = "Вы вылечили игрока %PLAYER%";
	public static String HEAL_ERR_INTEGER = "Вы должны указать число";
	
	public static String SPECTATOR_MODE_ON = "Вы включили режим зрителя";
	public static String SPECTATOR_MODE_OFF = "Вы выключили режим зрителя";
	
	public static String UNDRESSED = "Игрок успешно раздет.";
	public static String UNDRESSED_FULL = "Инвентарь игрока забит. Некоторые вещи не сняты.";
	public static String UNDRESSED_TARGET = "Вас раздел %PLAYER%";

	public static String PASSIVE_MODE_ON = "Вы включили пассивный режим.";
	public static String PASSIVE_MODE_OFF = "Вы выключили пассивный режим.";
	
	public static String FREEZE_ON = "Вы заморозили игрока %PLAYER%";
	public static String FREEZE_ON_TARGET = "Вас заморозил %PLAYER%";
	public static String FREEZE_OFF = "Вы разморозили игрока %PLAYER%";
	public static String FREEZE_OFF_TARGET = "Вас разморозил %PLAYER%";
	public static String FREEZE_SELF = "Нельзя морозить себя";
	public static String YOU_FREEZED = "Вы заморожены и не можете пользоваться командой заморозки";
	
	public static String SLEEP_ON = "Теперь вам нужно спать";
	public static String SLEEP_OFF = "Теперь вам не нужно спать";
	
	public static String COMBAT_IN_YOU = "Вы в бою!";
	public static String COMBAT_OUT_YOU = "Вы больше не в бою!";

	public static String WORLD_LIST = "========== Загруженные миры ==========";
	public static String WORLD_LIST_FOOTER = "==============================";
	
	public static String WORLD_LOAD = "Начата подгрузка мира '%WORLD%', пожалуйста подождите...";
	public static String WORLD_LOAD_COMPLETE = "Подгрузка мира '%WORLD%', завершена.";

	public static String WORLD_UNLOAD = "Начата отгрузка мира '%WORLD%', пожалуйста подождите...";
	public static String WORLD_UNLOAD_COMPLETE = "Отгрузка мира '%WORLD%' завершена.";
	public static String WORLD_UNLOAD_ERROR = "Отгрузка мира '%WORLD%' не удалась.";

	public static String PLAYER_BED_NOT_FOUND = "Кровать игрока %PLAYER% не найдена";
	public static String PLAYER_NOT_FOUND = "Игрок с ником %PLAYER% не найден";

	public static String WORLD_NOT_FOUND = "Мир %WORLD% не существует";

	public static String SERVER_OFF_WHITH = "Сервер будет выключен через %%SECONDS%%.";
	public static String SERVER_OFF_SECONDS = "секунд";
	public static String SERVER_OFF_MINUTES = "минут";
	public static String SERVER_OFF_HOURS = "часов";

	public static String PLAYER_STAT = "========== Статистика игрока %PLAYER% ==========";
	public static String PLAYER_STAT_FOOTER = "==============================";

	public static String YES = "Да";
	public static String NO = "Нет";

	public static String WORLD = "Мир";
	public static String GAME_MODE = "Игровой режим";
	public static String HEALTH = "Здоровье";
	public static String FOOD_LEVEL = "Сытость";
	public static String EXP = "Опыт";
	public static String OPERATOR = "Оператор";
	public static String FREEZED = "Заморожен";
	public static String FLYING = "Летает";
	public static String COMBAT_IN = "В бою";
	public static String PASSIVEMODE_STAT = "Пассивный";

	public static void loadConfig() {
		File f = new File("plugins/SCEssentials/config.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		boolean fileNotExists = !f.exists();
		if (fileNotExists)
			y.options().copyDefaults(true);

		WAIT_FOR_AFK = procLong(y, fileNotExists, "WaitForAFK", WAIT_FOR_AFK);
		allowTeleportImmunity = procBoolean(y, fileNotExists, "teleport.allowImmunity", allowTeleportImmunity);
		SPEED_DEFAULT = procFloat(y, fileNotExists, "speedDefault.walk", SPEED_DEFAULT);
		SPEED_DEFAULT_FLY = procFloat(y, fileNotExists, "speedDefault.fly", SPEED_DEFAULT_FLY);
		
		AFK_AUTO = procLong(y, fileNotExists, "Afk.Auto", AFK_AUTO);
		AFK_KICK = procLong(y, fileNotExists, "Afk.AutoKick", AFK_KICK);
		AFK_TEAM = procBoolean(y, fileNotExists, "Afk.Team", AFK_TEAM);
		
		KILL_COMBAT_LEAVER = procBoolean(y, fileNotExists, "Combat.KillCombatLeavers", KILL_COMBAT_LEAVER);
		COMBAT_MESSAGES = procBoolean(y, fileNotExists, "Combat.CombatMessages", COMBAT_MESSAGES);
		COMBAT_TIME = procLong(y, fileNotExists, "Combat.CombatTime", COMBAT_TIME);
		
		TIMEDSTOP_ENABLE = procBoolean(y, fileNotExists, "Timedstop.Enable", TIMEDSTOP_ENABLE);
		TIMEDSTOP_FIXED_TIME = procBoolean(y, fileNotExists, "Timedstop.FixedTime", TIMEDSTOP_FIXED_TIME);
		TIMEDSTOP_TIME = procString(y, fileNotExists, "Timedstop.Time", TIMEDSTOP_TIME,false);
		TIMEDSTOP_DELY = procLong(y, fileNotExists, "Timedstop.Delyhour", TIMEDSTOP_DELY);
		TIMEDSTOP_WARNS = procListInteger(y, fileNotExists, "Timedstop.Warnings", TIMEDSTOP_WARNS);
		
		WORLD_AUTO_LOAD = procBoolean(y, fileNotExists, "Worlds.AutoLoad", WORLD_AUTO_LOAD);
		
		if (!f.exists()) {
			try {y.save(f);} catch (IOException e) {
				Bukkit.getLogger().info("Ошибка сохранения config.yml.");
			}
		}
	}
	
	public static void loadMessages() {
		File f = new File("plugins/SCEssentials/messages.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		boolean fileNotExists = !f.exists();
		if (fileNotExists)
			y.options().copyDefaults(true);
		
		PERMISSION_DENY = procString(y, fileNotExists, "PermissionDeny", PERMISSION_DENY);
		COMMAND_FOR_PLAYERS = procString(y, fileNotExists, "CommandOnlyForPlayers", COMMAND_FOR_PLAYERS);
		
		SERVER_OFF_WHITH = procString(y, fileNotExists, "ServerOff.With", SERVER_OFF_WHITH);
		SERVER_OFF_SECONDS = procString(y, fileNotExists, "ServerOff.Seconds", SERVER_OFF_SECONDS);
		SERVER_OFF_MINUTES = procString(y, fileNotExists, "ServerOff.Minutes", SERVER_OFF_MINUTES);
		SERVER_OFF_HOURS = procString(y, fileNotExists, "ServerOff.Hours", SERVER_OFF_HOURS);
		
		AFK_ALREADY_WAIT = procString(y, fileNotExists, "Afk.AlreadyWait", AFK_ALREADY_WAIT);
		AFK_ON = procString(y, fileNotExists, "Afk.On", AFK_ON);
		AFK_OFF = procString(y, fileNotExists, "Afk.Off", AFK_OFF);
		AFK_WAIT = procString(y, fileNotExists, "Afk.Wait", AFK_WAIT);
		AFK_DENIED_WHEN_FREEZED = procString(y, fileNotExists, "Afk.DeniedWhenFreezed", AFK_DENIED_WHEN_FREEZED);
		AFK_KICK_REASON = procString(y, fileNotExists, "Afk.KickReason", AFK_KICK_REASON);
		AFK_PREFIX = procString(y, fileNotExists, "Afk.Prefix", AFK_PREFIX);
		
		FLY_DENYED = procString(y, fileNotExists, "Fly.Deny", FLY_DENYED);
		FLY_ON = procString(y, fileNotExists, "Fly.OnSelf", FLY_ON);
		FLY_OFF = procString(y, fileNotExists, "Fly.OffSelf", FLY_OFF);
		FLY_ON_OTHER = procString(y, fileNotExists, "Fly.OnOther", FLY_ON_OTHER);
		FLY_OFF_OTHER = procString(y, fileNotExists, "Fly.OffOther", FLY_OFF_OTHER);
		FLY_DENY_OTHER = procString(y, fileNotExists, "Fly.DenyOther", FLY_DENY_OTHER);
		
		HEAL_DENYED = procString(y, fileNotExists, "Heal.Denyed", HEAL_DENYED);
		HEAL_SUCCESS = procString(y, fileNotExists, "Heal.Success", HEAL_SUCCESS);
		HEAL_SUCCESS_OTHER = procString(y, fileNotExists, "Heal.SuccessOther", HEAL_SUCCESS_OTHER);
		HEAL_ERR_INTEGER = procString(y, fileNotExists, "Heal.ErrInteger", HEAL_ERR_INTEGER);
		
		SPECTATOR_MODE_ON = procString(y, fileNotExists, "SpectatorMode.on", SPECTATOR_MODE_ON);
		SPECTATOR_MODE_OFF = procString(y, fileNotExists, "SpectatorMode.off", SPECTATOR_MODE_OFF);
		
		PASSIVE_MODE_ON = procString(y, fileNotExists, "PassiveMod.on", PASSIVE_MODE_ON);
		PASSIVE_MODE_OFF = procString(y, fileNotExists, "PassiveMod.off", PASSIVE_MODE_OFF);
		
		FREEZE_ON = procString(y, fileNotExists, "freeze.on", FREEZE_ON);
		FREEZE_OFF = procString(y, fileNotExists, "freeze.off", FREEZE_OFF);
		FREEZE_ON_TARGET = procString(y, fileNotExists, "freeze.target.on", FREEZE_ON_TARGET);
		FREEZE_OFF_TARGET = procString(y, fileNotExists, "freeze.target.off", FREEZE_OFF_TARGET);
		FREEZE_SELF = procString(y, fileNotExists, "freeze.self", FREEZE_SELF);
		YOU_FREEZED = procString(y, fileNotExists, "freeze.denyYorFreezed", YOU_FREEZED);
		
		UNDRESSED = procString(y, fileNotExists, "undressed.success", UNDRESSED);
		UNDRESSED_FULL = procString(y, fileNotExists, "undressed.full", UNDRESSED_FULL);
		UNDRESSED_TARGET = procString(y, fileNotExists, "undressed.target", UNDRESSED_TARGET);
		
		WORLD_LIST = procString(y, fileNotExists, "World.List", WORLD_LIST);
		WORLD_LIST_FOOTER = procString(y, fileNotExists, "World.ListFooter", WORLD_LIST_FOOTER);
		WORLD_LOAD = procString(y, fileNotExists, "World.Load", WORLD_LOAD);
		WORLD_LOAD_COMPLETE = procString(y, fileNotExists, "World.LoadComplete", WORLD_LOAD_COMPLETE);
		WORLD_UNLOAD = procString(y, fileNotExists, "World.Unload", WORLD_UNLOAD);
		WORLD_UNLOAD_COMPLETE = procString(y, fileNotExists, "World.UnloadComplete", WORLD_UNLOAD_COMPLETE);
		WORLD_UNLOAD_ERROR = procString(y, fileNotExists, "World.UnloadError", WORLD_UNLOAD_ERROR);
		
		WORLD_NOT_FOUND = procString(y, fileNotExists, "World.NotFound", WORLD_NOT_FOUND);
		PLAYER_NOT_FOUND = procString(y, fileNotExists, "Player.NotFound", PLAYER_NOT_FOUND);
		PLAYER_BED_NOT_FOUND = procString(y, fileNotExists, "Player.BedNotFound", PLAYER_BED_NOT_FOUND);
		
		SLEEP_ON = procString(y, fileNotExists, "Sleep.on", SLEEP_ON);
		SLEEP_OFF = procString(y, fileNotExists, "Sleep.off", SLEEP_OFF);
		
		COMBAT_IN_YOU = procString(y, fileNotExists, "Combat.inCombat", COMBAT_IN_YOU);
		COMBAT_OUT_YOU = procString(y, fileNotExists, "Combat.outCombat", COMBAT_OUT_YOU);
		
		YES = procString(y, fileNotExists, "yes", YES);
		NO = procString(y, fileNotExists, "no", NO);
		
		PLAYER_STAT = procString(y, fileNotExists, "stats.header", PLAYER_STAT);
		PLAYER_STAT_FOOTER = procString(y, fileNotExists, "stats.footer", PLAYER_STAT_FOOTER);
		
		WORLD = procString(y, fileNotExists, "stats.world", WORLD);
		GAME_MODE = procString(y, fileNotExists, "stats.gamemode", GAME_MODE);
		HEALTH = procString(y, fileNotExists, "stats.health", HEALTH);
		FOOD_LEVEL = procString(y, fileNotExists, "stats.food_level", FOOD_LEVEL);
		EXP = procString(y, fileNotExists, "stats.exp", EXP);
		OPERATOR = procString(y, fileNotExists, "stats.operator", OPERATOR);
		FREEZED = procString(y, fileNotExists, "stats.freezed", FREEZED);
		FLYING = procString(y, fileNotExists, "stats.flying", FLYING);
		COMBAT_IN = procString(y, fileNotExists, "stats.combat", COMBAT_IN);
		PASSIVEMODE_STAT = procString(y, fileNotExists, "stats.passivemode", PASSIVEMODE_STAT);
		
		if (!f.exists()) {
			try {y.save(f);} catch (IOException e) {
				Bukkit.getLogger().info("Ошибка сохранения messages.yml.");
			}
		}
	}
	/**
	 * Сохранить миры, которые будут автоматически загружены при запуске плагина
	 * @param worlds - Список миров
	 */
	public static void setWorldList(Map<String, String> worlds) {
		File f = new File("plugins/SCEssentials/worlds.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		y.set("worlds", null);
		for (Entry<String, String> world: worlds.entrySet()) {
			y.set(world.getKey(), world.getValue());
		}
		try {y.save(f);} catch (IOException e) {
			Bukkit.getLogger().info("Ошибка сохранения worlds.yml.");
		}
	}
	/**
	 * Получить список миров
	 * @return
	 */
	public static Map<String, String> getWorldList() {
		File f = new File("plugins/SCEssentials/worlds.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		if (f.exists()) {
			ConfigurationSection section = y.getConfigurationSection("worlds");
			HashMap<String, String> map = new HashMap<>();
			for (String key : section.getKeys(false)) {
				map.put(key, section.getString(key));
			}
			return map;
		}
		return new HashMap<>();
	}
	/**
	 * Попытаться загрузить строку из конфига
	 * @param y - Конфиг
	 * @param fileNotExists - Если файл конфигурации не существует
	 * @param alias - Идентификатор конфигурации
	 * @param value - Значение по умолчанию
	 * @return
	 */
	private static String procString(YamlConfiguration y, boolean fileNotExists, String alias, String value) {
		return procString(y, fileNotExists, alias, value, true);
	}
	/**
	 * Попытаться загрузить строку из конфига
	 * @param y - Конфиг
	 * @param fileNotExists - Если файл конфигурации не существует
	 * @param alias - Идентификатор конфигурации
	 * @param value - Значение по умолчанию
	 * @param replaceColors - Заменять & на символ цветов чата
	 * @return
	 */
	private static String procString(YamlConfiguration y, boolean fileNotExists, String alias, String value, boolean replaceColors) {
		y.addDefault(alias, value);
		if (fileNotExists) {
			return value;
		} else if (replaceColors)
			return Utils.replaceColorCodes(y.getString(alias));
		else
			return y.getString(alias);
	}
	/**
	 * Попытаться загрузить длинное целочисленное значение из конфига
	 * @param y - Конфиг
	 * @param fileNotExists - Если файл конфигурации не существует
	 * @param alias - Идентификатор конфигурации
	 * @param value - Значение по умолчанию
	 * @return
	 */
	private static long procLong(YamlConfiguration y, boolean fileNotExists, String alias, long value) {
		y.addDefault(alias, value);
		if (fileNotExists) {
			return value;
		} else
			return y.getLong(alias);
	}
	/**
	 * Попытаться загрузить длинное целочисленное значение из конфига
	 * @param y - Конфиг
	 * @param fileNotExists - Если файл конфигурации не существует
	 * @param alias - Идентификатор конфигурации
	 * @param value - Значение по умолчанию
	 * @return
	 */
	private static float procFloat(YamlConfiguration y, boolean fileNotExists, String alias, float value) {
		y.addDefault(alias, value);
		if (fileNotExists) {
			return value;
		} else
			return (float)y.getDouble(alias);
	}
	/**
	 * Попытаться загрузить логическое значение из конфига
	 * @param y - Конфиг
	 * @param fileNotExists - Если файл конфигурации не существует
	 * @param alias - Идентификатор конфигурации
	 * @param value - Значение по умолчанию
	 * @return
	 */
	private static boolean procBoolean(YamlConfiguration y, boolean fileNotExists, String alias, boolean value) {
		y.addDefault(alias, value);
		if (fileNotExists) {
			return value;
		} else
			return y.getBoolean(alias);
	}
	/**
	 * Попытаться загрузить логическое значение из конфига
	 * @param y - Конфиг
	 * @param fileNotExists - Если файл конфигурации не существует
	 * @param alias - Идентификатор конфигурации
	 * @param value - Значение по умолчанию
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<Integer> procListInteger(YamlConfiguration y, boolean fileNotExists, String alias, List<Integer> value) {
		y.addDefault(alias, value);
		if (fileNotExists) {
			return value;
		} else {
			List<?> list = y.getList(alias);
			if (!list.isEmpty() && list.get(0) instanceof Integer) {
				return (List<Integer>)list;
			} else
				return new ArrayList<>();
		}
	}
}
