package ru.seriouscompany.essentials;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import ru.seriouscompany.essentials.api.DataBaseController;
import ru.seriouscompany.essentials.api.Utils;

public abstract class Config {
	private static String CONFIG_DIR;
	
	public static long WAIT_FOR_AFK = 75;
	
	public static boolean TIMEDSTOP_ENABLE = false;
	public static boolean TIMEDSTOP_FIXED_TIME = true;
	public static String TIMEDSTOP_TIME = "04:00:00:000";
	public static long TIMEDSTOP_DELY = 1;
	
	public static List<Integer> TIMEDSTOP_WARNS = new ArrayList<Integer>();
	
	public static String PERMISSION_DENY = "Вам не разрешено использывать данное действие";
	public static String COMMAND_FOR_PLAYERS = "Эта команда предназначена для игроков";
	
	public static String AFK_ALREADY_WAIT = "Вы уже ожидаете афк.";
	public static String AFK_ON = "* %PLAYER% теперь афк";
	public static String AFK_OFF = "* %PLAYER% больше не афк";
	public static String AFK_WAIT = "Подождите %TIME% секунд";
	
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
	
	public static String SLEEP_ON = "Теперь вам нужно спать";
	public static String SLEEP_OFF = "Теперь вам не нужно спать";
	
	public static String PLAYER_NOT_FOUND = "Игрок с ником %PLAYER% не найден";
	
	public static String WORLD_NOT_FOUND = "Мир %WORLD% не существует";
	
	public static String SERVER_OFF_WHITH = "Сервер будет выключен через %%SECONDS%%.";
	public static String SERVER_OFF_SECONDS = "секунд";
	public static String SERVER_OFF_MINUTES = "минут";
	public static String SERVER_OFF_HOURS = "часов";
	
	public static void init() {
		CONFIG_DIR = DataBaseController.getConfigPathMain();
		loadConfig();
		loadMessages();
	}
	
	@SuppressWarnings("unchecked")
	public static void loadConfig() {
		File f = new File(CONFIG_DIR+"config.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		setDefaultConfig(y);
		if (!f.exists()) {
			try {y.save(f);} catch (IOException e) {
				SCCore.getInstance().getLogger().info("Ошибка сохранения config.yml.");
			}
		} else {
			WAIT_FOR_AFK = y.getLong("WaitForAFK");
			
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
		
		y.addDefault("timedstop.enable", TIMEDSTOP_ENABLE);
		y.addDefault("timedstop.fixed_time", TIMEDSTOP_FIXED_TIME);
		y.addDefault("timedstop.time", TIMEDSTOP_TIME);
		y.addDefault("timedstop.delyhour", TIMEDSTOP_DELY);
		y.addDefault("timedstop.warnings", TIMEDSTOP_WARNS);
	}
	
	public static void loadMessages() {
		File f = new File(CONFIG_DIR+"messages.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		setDeafultMessages(y);
		if (!f.exists())  {
			try {y.save(f);} catch (IOException e) {
				SCCore.getInstance().getLogger().info("Ошибка сохранения messages.yml.");
			}
		} else {
			PERMISSION_DENY = Utils.replaceColorCodes(y.getString("PermissionDeny"));

			SERVER_OFF_WHITH = Utils.replaceColorCodes(y.getString("ServerOff.With"));
			SERVER_OFF_SECONDS = Utils.replaceColorCodes(y.getString("ServerOff.Seconds"));
			SERVER_OFF_MINUTES = Utils.replaceColorCodes(y.getString("ServerOff.Minutes"));
			SERVER_OFF_HOURS = Utils.replaceColorCodes(y.getString("ServerOff.Hours"));
			
			AFK_ON = Utils.replaceColorCodes(y.getString("Afk.On"));
			AFK_OFF = Utils.replaceColorCodes(y.getString("Afk.Off"));
			AFK_WAIT = Utils.replaceColorCodes(y.getString("Afk.Wait"));
			
			FLY_DENYED = Utils.replaceColorCodes(y.getString("Fly.Deny"));
			FLY_ON = Utils.replaceColorCodes(y.getString("Fly.OnSelf"));
			FLY_OFF = Utils.replaceColorCodes(y.getString("Fly.OffSelf"));
			FLY_ON_OTHER = Utils.replaceColorCodes(y.getString("Fly.OnOther"));
			FLY_OFF_OTHER = Utils.replaceColorCodes(y.getString("Fly.OffOther"));
			FLY_DENY_OTHER = Utils.replaceColorCodes(y.getString("Fly.DenyOther"));
			
			PLAYER_NOT_FOUND = Utils.replaceColorCodes(y.getString("Player.NotFound"));
			
			WORLD_NOT_FOUND = Utils.replaceColorCodes(y.getString("World.NotFound"));
			
			SLEEP_ON = Utils.replaceColorCodes(y.getString("Sleep.on"));
			SLEEP_OFF = Utils.replaceColorCodes(y.getString("Sleep.off"));
		}
	}
	
	public static void setDeafultMessages(YamlConfiguration y) {
		y.options().copyDefaults(true);
		
		y.addDefault("PermissionDeny", PERMISSION_DENY);
		y.addDefault("ServerOff.With", SERVER_OFF_WHITH);
		y.addDefault("ServerOff.Seconds", SERVER_OFF_SECONDS);
		y.addDefault("ServerOff.Minutes", SERVER_OFF_MINUTES);
		y.addDefault("ServerOff.Hours", SERVER_OFF_HOURS);
		
		y.addDefault("Afk.On", AFK_ON);
		y.addDefault("Afk.Off", AFK_OFF);
		y.addDefault("Afk.Wait", AFK_WAIT);
		
		y.addDefault("Player.NotFound", PLAYER_NOT_FOUND);
		
		y.addDefault("World.NotFound", WORLD_NOT_FOUND);
		
		y.addDefault("Fly.Deny", FLY_DENYED);
		y.addDefault("Fly.OnSelf", FLY_ON);
		y.addDefault("Fly.OffSelf", FLY_OFF);
		y.addDefault("Fly.OnOther", FLY_ON_OTHER);
		y.addDefault("Fly.OffOther", FLY_OFF_OTHER);
		y.addDefault("Fly.DenyOther", FLY_DENY_OTHER);
		
		y.addDefault("Sleep.on", SLEEP_ON);
		y.addDefault("Sleep.off", SLEEP_OFF);
	}
}
