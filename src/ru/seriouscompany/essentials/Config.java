package ru.seriouscompany.essentials;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import ru.seriouscompany.essentials.api.Utils;

public abstract class Config {
	public static long WAIT_FOR_AFK = 75;

	public static boolean TIMEDSTOP_ENABLE = false;
	public static boolean TIMEDSTOP_FIXED_TIME = true;
	public static String TIMEDSTOP_TIME = "04:00:00:000";
	public static long TIMEDSTOP_DELY = 1;

	public static long AFK_AUTO = 0;
	public static long AFK_KICK = 0;

	public static List<Integer> TIMEDSTOP_WARNS = new ArrayList<Integer>();

	public static String PERMISSION_DENY = "Вам не разрешено использывать данное действие";
	public static String COMMAND_FOR_PLAYERS = "Эта команда предназначена для игроков";

	public static String AFK_ALREADY_WAIT = "Вы уже ожидаете афк.";
	public static String AFK_ON = "* %PLAYER% теперь афк";
	public static String AFK_OFF = "* %PLAYER% больше не афк";
	public static String AFK_WAIT = "Подождите %TIME% секунд";
	public static String AFK_DENIED_WHEN_FREEZED = "Вы заморожены, вам нельзя использовать AFK";
	public static String AFK_KICK_REASON = "Вы находилесь в афк дольше %TIME% минут(ы)";
	
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
	
	public static String FREEZE_ON = "Вы заморозили игрока %PLAYER%";
	public static String FREEZE_ON_TARGET = "Вас заморозил %PLAYER%";
	public static String FREEZE_OFF = "Вы разморозили игрока %PLAYER%";
	public static String FREEZE_OFF_TARGET = "Вас разморозил %PLAYER%";
	
	public static String SLEEP_ON = "Теперь вам нужно спать";
	public static String SLEEP_OFF = "Теперь вам не нужно спать";

	public static String WORLD_LIST = "========== Загруженные миры ==========";
	public static String WORLD_LIST_FOOTER = "==============================";
	
	public static String WORLD_LOAD = "Начата подгрузка мира '%WORLD%', пожалуйста подождите...";
	public static String WORLD_LOAD_COMPLETE = "Подгрузка мира '%WORLD%', завершена.";

	public static String WORLD_UNLOAD = "Начата отгрузка мира '%WORLD%', пожалуйста подождите...";
	public static String WORLD_UNLOAD_COMPLETE = "Отгрузка мира '%WORLD%' завершена.";
	public static String WORLD_UNLOAD_ERROR = "Отгрузка мира '%WORLD%' не удалась.";

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

	public static void init() {
		loadConfig();
		loadMessages();
	}

	@SuppressWarnings("unchecked")
	public static void loadConfig() {
		File f = new File("plugins/SCEssentials/config.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		setDefaultConfig(y);
		if (!f.exists()) {
			try {y.save(f);} catch (IOException e) {
				SCCore.getInstance().getLogger().info("Ошибка сохранения config.yml.");
			}
		} else {
			WAIT_FOR_AFK = y.getLong("WaitForAFK");
			
			AFK_AUTO = y.getLong("Afk.Auto");
			AFK_KICK = y.getLong("Afk.AutoKick");
			
			TIMEDSTOP_ENABLE = y.getBoolean("timedstop.enable");
			TIMEDSTOP_FIXED_TIME = y.getBoolean("timedstop.fixed_time");
			TIMEDSTOP_TIME = y.getString("timedstop.time");
			TIMEDSTOP_DELY = y.getLong("timedstop.delyhour");
			TIMEDSTOP_WARNS = (List<Integer>) y.getList("timedstop.warnings");
		}
	}
	
	public static void setDefaultConfig(YamlConfiguration y) {
		y.options().copyDefaults(true);
		
		y.addDefault("WaitForAFK", WAIT_FOR_AFK);
		
		y.addDefault("Afk.Auto", AFK_AUTO);
		y.addDefault("Afk.AutoKick", AFK_KICK);
		
		y.addDefault("timedstop.enable", TIMEDSTOP_ENABLE);
		y.addDefault("timedstop.fixed_time", TIMEDSTOP_FIXED_TIME);
		y.addDefault("timedstop.time", TIMEDSTOP_TIME);
		y.addDefault("timedstop.delyhour", TIMEDSTOP_DELY);
		y.addDefault("timedstop.warnings", TIMEDSTOP_WARNS);
	}
	
	public static void loadMessages() {
		File f = new File("plugins/SCEssentials/messages.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		setDeafultMessages(y);
		if (!f.exists())  {
			try {y.save(f);} catch (IOException e) {
				SCCore.getInstance().getLogger().info("Ошибка сохранения messages.yml.");
			}
		} else {
			PERMISSION_DENY = Utils.replaceColorCodes(y.getString("PermissionDeny"));
			COMMAND_FOR_PLAYERS = y.getString("CommandOnlyForPlayers");

			SERVER_OFF_WHITH = Utils.replaceColorCodes(y.getString("ServerOff.With"));
			SERVER_OFF_SECONDS = Utils.replaceColorCodes(y.getString("ServerOff.Seconds"));
			SERVER_OFF_MINUTES = Utils.replaceColorCodes(y.getString("ServerOff.Minutes"));
			SERVER_OFF_HOURS = Utils.replaceColorCodes(y.getString("ServerOff.Hours"));

			AFK_ALREADY_WAIT = Utils.replaceColorCodes(y.getString("Afk.AlreadyWait"));
			AFK_ON = Utils.replaceColorCodes(y.getString("Afk.On"));
			AFK_OFF = Utils.replaceColorCodes(y.getString("Afk.Off"));
			AFK_WAIT = Utils.replaceColorCodes(y.getString("Afk.Wait"));
			AFK_DENIED_WHEN_FREEZED = Utils.replaceColorCodes(y.getString("Afk.DeniedWhenFreezed"));
			AFK_KICK_REASON = Utils.replaceColorCodes(y.getString("Afk.KickReason"));
			
			FLY_DENYED = Utils.replaceColorCodes(y.getString("Fly.Deny"));
			FLY_ON = Utils.replaceColorCodes(y.getString("Fly.OnSelf"));
			FLY_OFF = Utils.replaceColorCodes(y.getString("Fly.OffSelf"));
			FLY_ON_OTHER = Utils.replaceColorCodes(y.getString("Fly.OnOther"));
			FLY_OFF_OTHER = Utils.replaceColorCodes(y.getString("Fly.OffOther"));
			FLY_DENY_OTHER = Utils.replaceColorCodes(y.getString("Fly.DenyOther"));
			
			HEAL_DENYED = Utils.replaceColorCodes(y.getString("Heal.Denyed"));
			HEAL_SUCCESS = Utils.replaceColorCodes(y.getString("Heal.Success"));
			HEAL_SUCCESS_OTHER = Utils.replaceColorCodes(y.getString("Heal.SuccessOther"));
			HEAL_ERR_INTEGER = Utils.replaceColorCodes(y.getString("Heal.ErrInteger"));
			
			WORLD_LIST = Utils.replaceColorCodes(y.getString("World.List"));
			WORLD_LIST_FOOTER = Utils.replaceColorCodes(y.getString("World.ListFooter"));
			WORLD_LOAD = Utils.replaceColorCodes(y.getString("World.Load"));
			WORLD_LOAD_COMPLETE = Utils.replaceColorCodes(y.getString("World.LoadComplete"));
			WORLD_UNLOAD = Utils.replaceColorCodes(y.getString("World.Unload"));
			WORLD_UNLOAD_COMPLETE = Utils.replaceColorCodes(y.getString("World.UnloadComplete"));
			WORLD_UNLOAD_ERROR = Utils.replaceColorCodes(y.getString("World.UnloadError"));
			
			PLAYER_NOT_FOUND = Utils.replaceColorCodes(y.getString("Player.NotFound"));
			
			WORLD_NOT_FOUND = Utils.replaceColorCodes(y.getString("World.NotFound"));
			
			SLEEP_ON = Utils.replaceColorCodes(y.getString("Sleep.on"));
			SLEEP_OFF = Utils.replaceColorCodes(y.getString("Sleep.off"));
			
			YES = Utils.replaceColorCodes(y.getString("yes"));
			NO = Utils.replaceColorCodes(y.getString("no"));
			
			PLAYER_STAT = Utils.replaceColorCodes(y.getString("stats.header"));
			PLAYER_STAT_FOOTER = Utils.replaceColorCodes(y.getString("stats.footer"));
			
			WORLD = Utils.replaceColorCodes(y.getString("stats.world"));
			GAME_MODE = Utils.replaceColorCodes(y.getString("stats.gamemode"));
			HEALTH = Utils.replaceColorCodes(y.getString("stats.health"));
			FOOD_LEVEL = Utils.replaceColorCodes(y.getString("stats.food_level"));
			EXP = Utils.replaceColorCodes(y.getString("stats.exp"));
			OPERATOR = Utils.replaceColorCodes(y.getString("stats.operator"));
			FREEZED = Utils.replaceColorCodes(y.getString("stats.freezed"));
			FLYING = Utils.replaceColorCodes(y.getString("stats.flying"));
		}
	}
	
	public static void setDeafultMessages(YamlConfiguration y) {
		y.options().copyDefaults(true);
		
		y.addDefault("PermissionDeny", PERMISSION_DENY);
		y.addDefault("CommandOnlyForPlayers", COMMAND_FOR_PLAYERS);
		y.addDefault("ServerOff.With", SERVER_OFF_WHITH);
		y.addDefault("ServerOff.Seconds", SERVER_OFF_SECONDS);
		y.addDefault("ServerOff.Minutes", SERVER_OFF_MINUTES);
		y.addDefault("ServerOff.Hours", SERVER_OFF_HOURS);

		y.addDefault("Afk.AlreadyWait", AFK_ALREADY_WAIT);
		y.addDefault("Afk.On", AFK_ON);
		y.addDefault("Afk.Off", AFK_OFF);
		y.addDefault("Afk.Wait", AFK_WAIT);
		y.addDefault("Afk.DeniedWhenFreezed", AFK_DENIED_WHEN_FREEZED);
		y.addDefault("Afk.KickReason", AFK_KICK_REASON);
		
		y.addDefault("Player.NotFound", PLAYER_NOT_FOUND);
		
		y.addDefault("World.NotFound", WORLD_NOT_FOUND);
		
		y.addDefault("Fly.Deny", FLY_DENYED);
		y.addDefault("Fly.OnSelf", FLY_ON);
		y.addDefault("Fly.OffSelf", FLY_OFF);
		y.addDefault("Fly.OnOther", FLY_ON_OTHER);
		y.addDefault("Fly.OffOther", FLY_OFF_OTHER);
		y.addDefault("Fly.DenyOther", FLY_DENY_OTHER);
		
		y.addDefault("Heal.Denyed", HEAL_DENYED);
		y.addDefault("Heal.Success", HEAL_SUCCESS);
		y.addDefault("Heal.SuccessOther", HEAL_SUCCESS_OTHER);
		y.addDefault("Heal.ErrInteger", HEAL_ERR_INTEGER);
		
		y.addDefault("World.List", WORLD_LIST);
		y.addDefault("World.ListFooter", WORLD_LIST_FOOTER);
		y.addDefault("World.Load", WORLD_LOAD);
		y.addDefault("World.LoadComplete", WORLD_LOAD_COMPLETE);
		y.addDefault("World.Unload", WORLD_UNLOAD);
		y.addDefault("World.UnloadComplete", WORLD_UNLOAD_COMPLETE);
		y.addDefault("World.UnloadError", WORLD_UNLOAD_ERROR);
		
		y.addDefault("Sleep.on", SLEEP_ON);
		y.addDefault("Sleep.off", SLEEP_OFF);
		
		y.addDefault("yes", YES);
		y.addDefault("no", NO);
		
		y.addDefault("stats.header", PLAYER_STAT);
		y.addDefault("stats.footer", PLAYER_STAT_FOOTER);
		
		y.addDefault("stats.world", WORLD);
		y.addDefault("stats.gamemode", GAME_MODE);
		y.addDefault("stats.health", HEALTH);
		y.addDefault("stats.food_level", FOOD_LEVEL);
		y.addDefault("stats.exp", EXP);
		y.addDefault("stats.operator", OPERATOR);
		y.addDefault("stats.freezed", FREEZED);
		y.addDefault("stats.flying", FLYING);
	}
}
