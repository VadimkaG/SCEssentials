package ru.seriouscompany.essentials;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import ru.seriouscompany.essentials.api.PlayerFlag;
import ru.seriouscompany.essentials.commands.CAFK;
import ru.seriouscompany.essentials.commands.CBlockUnderMe;
import ru.seriouscompany.essentials.commands.CFeed;
import ru.seriouscompany.essentials.commands.CFly;
import ru.seriouscompany.essentials.commands.CFreeze;
import ru.seriouscompany.essentials.commands.CHeal;
import ru.seriouscompany.essentials.commands.CLockBook;
import ru.seriouscompany.essentials.commands.COpenEnder;
import ru.seriouscompany.essentials.commands.COpenInventory;
import ru.seriouscompany.essentials.commands.CPassiveModeToggle;
import ru.seriouscompany.essentials.commands.CPlayerInfo;
import ru.seriouscompany.essentials.commands.CReload;
import ru.seriouscompany.essentials.commands.CRequestAccept;
import ru.seriouscompany.essentials.commands.CSleepIgnore;
import ru.seriouscompany.essentials.commands.CSpectatorModeToggle;
import ru.seriouscompany.essentials.commands.CSuicide;
import ru.seriouscompany.essentials.commands.CTeleportToPlayer;
import ru.seriouscompany.essentials.commands.CTeleportToPlayerBed;
import ru.seriouscompany.essentials.commands.CTeleportWorld;
import ru.seriouscompany.essentials.commands.CUndress;
import ru.seriouscompany.essentials.commands.CWorld;
import ru.seriouscompany.essentials.commands.CWorldLoad;
import ru.seriouscompany.essentials.listeners.BlockListener;
import ru.seriouscompany.essentials.listeners.EntityListener;
import ru.seriouscompany.essentials.listeners.PlayerListener;
import ru.seriouscompany.essentials.tabcompleters.TCPlayerArgument;
import ru.seriouscompany.essentials.tabcompleters.TCWorld;
import ru.seriouscompany.essentials.tabcompleters.TCWorldTeleport;
import ru.seriouscompany.essentials.tasks.AFKTask;

public class SCCore extends JavaPlugin {
	
	private static SCCore INSTANCE = null;
	public AFKTask afkTask;
	
	private List<ScheduledFuture<?>> stopTask = new ArrayList<ScheduledFuture<?>>();
	
	public static SCCore getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void onEnable() {
		INSTANCE = this;
		
		getConfig().addDefault("WaitForAFK", 75);
		
		getConfig().addDefault("speedDefault.walk", 0.2d);
		getConfig().addDefault("speedDefault.fly", 0.2d);
		
		getConfig().addDefault("Timedstop.Enable", false);
		getConfig().addDefault("Timedstop.FixedTime", true);
		getConfig().addDefault("Timedstop.Time", "04:00:00:000");
		getConfig().addDefault("Timedstop.Delyhour", 1);
		
		getConfig().addDefault("Afk.Auto", 0l);
		getConfig().addDefault("Afk.AutoKick", 0l);
		getConfig().addDefault("Afk.Team", false);
		
		getConfig().addDefault("Combat.KillCombatLeavers", false);
		getConfig().addDefault("Combat.CombatMessages", false);
		getConfig().addDefault("Combat.CombatTime", 5);
		
		//getConfig().addDefault("Worlds.AutoLoad", false);
		getConfig().addDefault("teleport.allowImmunity", false);
		
		getConfig().addDefault("Timedstop.Warnings", new ArrayList<Integer>());
		
		saveDefaultConfig();
		Lang.loadConfiguration();
		
		Map<String, String> worlds = getWorldList();
		for (Entry<String, String> world : worlds.entrySet()) {
			//getLogger().info("Мир: "+world.getKey()+" - "+world.getValue());
			CWorldLoad.loadWorld(world.getKey(), world.getValue());
		}
		
		getServer().getPluginManager().registerEvents(new BlockListener(), this);
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getCommand("scereload").setExecutor(new CReload());
		getCommand("afk").setExecutor(new CAFK());
		getCommand("pinfo").setExecutor(new CPlayerInfo());
		getCommand("pinfo").setTabCompleter(new TCPlayerArgument());
		getCommand("fly").setExecutor(new CFly());
		getCommand("fly").setTabCompleter(new TCPlayerArgument());
		getCommand("heal").setExecutor(new CHeal());
		getCommand("feed").setExecutor(new CFeed());
		getCommand("feed").setTabCompleter(new TCPlayerArgument());
		getCommand("oi").setExecutor(new COpenInventory());
		getCommand("oi").setTabCompleter(new TCPlayerArgument());
		getCommand("oe").setExecutor(new COpenEnder());
		getCommand("oe").setTabCompleter(new TCPlayerArgument());
		getCommand("sleepignore").setExecutor(new CSleepIgnore());
		getCommand("tpw").setExecutor(new CTeleportWorld());
		getCommand("tpw").setTabCompleter(new TCWorldTeleport());
		getCommand("suicide").setExecutor(new CSuicide());
		getCommand("world").setExecutor(new CWorld());
		getCommand("world").setTabCompleter(new TCWorld());
		getCommand("lockbook").setExecutor(new CLockBook());
		getCommand("freeze").setExecutor(new CFreeze());
		getCommand("accept").setExecutor(new CRequestAccept());
		getCommand("specmode").setExecutor(new CSpectatorModeToggle());
		getCommand("tpp").setExecutor(new CTeleportToPlayer());
		getCommand("tpp").setTabCompleter(new TCPlayerArgument());
		getCommand("tpb").setExecutor(new CTeleportToPlayerBed());
		getCommand("tpb").setTabCompleter(new TCPlayerArgument());
		getCommand("undress").setExecutor(new CUndress());
		getCommand("undress").setTabCompleter(new TCPlayerArgument());
		getCommand("passive").setExecutor(new CPassiveModeToggle());
		getCommand("bunder").setExecutor(new CBlockUnderMe());
		
		checkTimedStop();
		
		long auto_kick = getAutoKickTime();
		
		if (auto_kick > 0 || auto_kick > 0) {
			afkTask = new AFKTask();
			afkTask.runTaskTimer(this, 1000, 1000);
		}
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerFlag.setPlayerFlag(player, "PASSIVE_MODE", false);
			PlayerFlag.setPlayerFlag(player, "IN_COMBAT", false);
		}
	}
	
	@Override
	public void onDisable() {
		if (afkTask != null)
			afkTask.cancel();
		for (ScheduledFuture<?> task: stopTask) {
			task.cancel(true);
		}
		
		// Вернуть скорость замороженным игрокам
		for (Player player : Bukkit.getOnlinePlayers()) {
			
			if (PlayerFlag.isSetPlayerFlag(player, "OLD_FLY_SPEED")) {
				MetadataValue flag = PlayerFlag.getPlayerFlag(player, "OLD_FLY_SPEED");
				if (flag != null)
					player.setFlySpeed(flag.asFloat());
			}
			
			if (PlayerFlag.isSetPlayerFlag(player, "OLD_WALK_SPEED")) {
				MetadataValue flag = PlayerFlag.getPlayerFlag(player, "OLD_WALK_SPEED");
				if (flag != null)
					player.setWalkSpeed(flag.asFloat());
			}
		}
	}
	/**
	 * Время ожидания до ввода следущего ввода команды afk, после ее использования
	 * @return
	 */
	public static long getAFKCooldown() {
		return INSTANCE.getConfig().getLong("WaitForAFK");
	}
	/**
	 * Время неактивности игрока, через которое ему будет назначен статус AFK
	 * @return
	 */
	public static long getAutoAFKDely() {
		return INSTANCE.getConfig().getLong("Afk.Auto");
	}
	/**
	 * Получить время простоя в афк, после которого сервер выкинет игрока
	 * @return
	 */
	public static long getAutoKickTime() {
		return INSTANCE.getConfig().getLong("Afk.AutoKick");
	}
	/**
	 * Включино ли автоматическое отключение сервера
	 * @return
	 */
	public static boolean isTimedStopEnabled() {
		return INSTANCE.getConfig().getBoolean("Timedstop.Enable");
	}
	/**
	 * Абсолютно ли время выключение сервера или относительное
	 * @return
	 */
	public static boolean isTimedStopFixedTime() {
		return INSTANCE.getConfig().getBoolean("Timedstop.FixedTime");
	}
	/**
	 * Получить фиксированное время выключение сервера
	 * @return
	 */
	public static String getFixedTime() {
		return INSTANCE.getConfig().getString("Timedstop.Time");
	}
	/**
	 * Получить относительное время выключения сервера
	 * @return
	 */
	public static int getTimeDely() {
		return INSTANCE.getConfig().getInt("Timedstop.Delyhour");
	}
	/**
	 * Помещать ли в специальную команду игроков, которые на ходятся в афк
	 * @return
	 */
	public static boolean afkTeamEnabled() {
		return INSTANCE.getConfig().getBoolean("Afk.Team");
	}
	/**
	 * Стандартная скорость пешком
	 * @return
	 */
	public static double speedDefaultWalk() {
		return INSTANCE.getConfig().getDouble("speedDefault.walk");
	}
	/**
	 * Стандартная скорость полет
	 * @return
	 */
	public static double speedDefaultFly() {
		return INSTANCE.getConfig().getDouble("speedDefault.fly");
	}
	/**
	 * Включен ли режим убийства игроков, которые выходят во время боя
	 * @return
	 */
	public static boolean combatKickerEnabled() {
		return INSTANCE.getConfig().getBoolean("Combat.KillCombatLeavers");
	}
	/**
	 * Включены ли сообщения у режима защиты пвп
	 * @return
	 */
	public static boolean combatMessagesEnabled() {
		return INSTANCE.getConfig().getBoolean("Combat.CombatMessages");
	}
	/**
	 * Длительность боя, после которой режим защиты пвп выключается
	 * @return
	 */
	public static long combatTime() {
		return INSTANCE.getConfig().getLong("Combat.CombatTime");
	}
	/**
	 * Включен ли имунитет к телепортам
	 * @return
	 */
	public static boolean teleportImmunityAllow() {
		return INSTANCE.getConfig().getBoolean("teleport.allowImmunity");
	}
	@SuppressWarnings("unchecked")
	public static List<Integer> getTimeStopedWarns() {
		return (List<Integer>) INSTANCE.getConfig().getList("Timedstop.Warnings");
	}
	/**
	 * Сохранить миры, которые будут автоматически загружены при запуске плагина
	 * @param worlds - Список миров
	 */
	public static void setWorldList(Map<String, String> worlds) {
		File f = new File(INSTANCE.getDataFolder().getPath()+"/worlds.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		y.set("worlds", null);
		for (Entry<String, String> world: worlds.entrySet()) {
			y.set("worlds."+world.getKey(), world.getValue());
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
		File f = new File(INSTANCE.getDataFolder().getPath()+"/worlds.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		if (f.exists()) {
			ConfigurationSection section = y.getConfigurationSection("worlds");
			HashMap<String, String> map = new HashMap<>();
			if (section != null)
			for (String key : section.getKeys(false)) {
				map.put(key, section.getString(key));
			}
			return map;
		}
		return new HashMap<>();
	}
	/**
	 * Запустить задачу по автоматическому выключению сервера
	 */
	public void checkTimedStop() {
		if (!stopTask.isEmpty()) {
			for (ScheduledFuture<?> task : stopTask) {
				task.cancel(false);
			}
			stopTask.clear();
		}
		if (isTimedStopEnabled()) {
			ScheduledExecutorService timerServerShutdownPool = Executors.newScheduledThreadPool(1);
			long delay = 0;
			int timedStopDely = getTimeDely();
			if (isTimedStopFixedTime()) {
				LocalTime time = LocalTime.parse(getFixedTime(), DateTimeFormatter.ofPattern("HH:mm:ss:SSS"));
				LocalDateTime dateTime;
				if (time.isAfter(LocalTime.now()))
					dateTime = LocalDateTime.of(LocalDate.now(), time);
				else
					dateTime = LocalDateTime.of(LocalDate.now().plusDays(1), time);
				delay = LocalDateTime.now().until(dateTime, ChronoUnit.MILLIS);
			} else if (timedStopDely > 0)
				delay = timedStopDely * 3600000;
			if (delay > 0) {
				long sec = (delay/1000) % 60;
				long min = ((delay/1000) / 60) % 60;
				long hour = ((delay/1000) / 60) / 60;
				getLogger().info("Автовыключение через "+String.valueOf(hour)+":"+String.valueOf(min)+":"+String.valueOf(sec));
				List<Integer> warns = getTimeStopedWarns();
				for (Integer warnSecond : warns) {
					stopTask.add(timerServerShutdownPool.schedule(new Runnable() {
						@Override
						public void run() {
							int sec = warnSecond % 60;
							int min = (warnSecond / 60) % 60;
							int hour = (warnSecond / 60) / 60;
							String secondMessage = String.valueOf(warnSecond) + " ms";
							if (hour > 0)
								secondMessage = String.valueOf(hour) + ":" + String.valueOf(min) + ":" + String.valueOf(sec) + " " + Lang.SERVER_OFF_HOURS;
							else if (min > 0)
								secondMessage = String.valueOf(min) + ":" + String.valueOf(sec) + " " + Lang.SERVER_OFF_MINUTES;
							else if (sec > 0)
								secondMessage = String.valueOf(sec) + " " + Lang.SERVER_OFF_SECONDS;
							getServer().broadcastMessage(Lang.SERVER_OFF_WHITH.toString().replace("%%SECONDS%%", secondMessage));
						}
					}, delay - (warnSecond * 1000), TimeUnit.MILLISECONDS));
				}
				stopTask.add(timerServerShutdownPool.schedule(new Runnable() {
					@Override
					public void run() {
						getServer().shutdown();
					}
				}, delay, TimeUnit.MILLISECONDS));
			}
		}
	}
}
