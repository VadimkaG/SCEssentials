package ru.seriouscompany.essentials;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import ru.seriouscompany.essentials.commands.CBlockUnderMe;
import ru.seriouscompany.essentials.commands.CFeed;
import ru.seriouscompany.essentials.commands.CFly;
import ru.seriouscompany.essentials.commands.CFreeze;
import ru.seriouscompany.essentials.commands.CHeal;
import ru.seriouscompany.essentials.commands.CLockBook;
import ru.seriouscompany.essentials.commands.COpenInventory;
import ru.seriouscompany.essentials.commands.CPassiveModeToggle;
import ru.seriouscompany.essentials.commands.CPlayerInfo;
import ru.seriouscompany.essentials.commands.CReload;
import ru.seriouscompany.essentials.commands.CRequestAccept;
import ru.seriouscompany.essentials.commands.CSleepIgnore;
import ru.seriouscompany.essentials.commands.CSpectatorModeToggle;
import ru.seriouscompany.essentials.commands.CSpeed;
import ru.seriouscompany.essentials.commands.CSuicide;
import ru.seriouscompany.essentials.commands.CTeleportToPlayer;
import ru.seriouscompany.essentials.commands.CTeleportToPlayerBed;
import ru.seriouscompany.essentials.commands.CVanish;
import ru.seriouscompany.essentials.commands.CWorld;
import ru.seriouscompany.essentials.commands.CWorldLoad;
import ru.seriouscompany.essentials.listeners.AntiDOSListener;
import ru.seriouscompany.essentials.listeners.BlockListener;
import ru.seriouscompany.essentials.listeners.EntityListener;
import ru.seriouscompany.essentials.listeners.PlayerListener;
import ru.seriouscompany.essentials.tabcompleters.TCPlayerArgument;
import ru.seriouscompany.essentials.tabcompleters.TCWorld;

public class SCCore extends JavaPlugin {
	
	private List<ScheduledFuture<?>> stopTask = new ArrayList<ScheduledFuture<?>>();

	@Override
	public void onEnable() {
		Lang.loadConfiguration(getDataFolder());
		saveDefaultConfig();
		Config config = new Config(this);
		config.read();
		
		Map<String, String> worlds = config.readWorldList();
		for (Entry<String, String> world : worlds.entrySet()) {
			CWorldLoad.loadWorld(world.getKey(), world.getValue());
		}
		
		Server server = getServer();
		if (getConfig().getBoolean("antidos.enabled", true))
			server.getPluginManager().registerEvents(new AntiDOSListener(config), this);
		
		server.getPluginManager().registerEvents(new BlockListener(), this);
		server.getPluginManager().registerEvents(new EntityListener(), this);
		server.getPluginManager().registerEvents(new PlayerListener(this,config), this);
		
		getCommand("scereload").setExecutor(new CReload(this,config));
		getCommand("pinfo").setExecutor(new CPlayerInfo());
		getCommand("pinfo").setTabCompleter(new TCPlayerArgument());
		getCommand("fly").setExecutor(new CFly());
		getCommand("fly").setTabCompleter(new TCPlayerArgument());
		getCommand("heal").setExecutor(new CHeal());
		getCommand("feed").setExecutor(new CFeed());
		getCommand("feed").setTabCompleter(new TCPlayerArgument());
		getCommand("oi").setExecutor(new COpenInventory(this));
		getCommand("oi").setTabCompleter(new TCPlayerArgument());
		getCommand("sleepignore").setExecutor(new CSleepIgnore());
		getCommand("suicide").setExecutor(new CSuicide());
		getCommand("world").setExecutor(new CWorld(this,config));
		getCommand("world").setTabCompleter(new TCWorld());
		getCommand("lockbook").setExecutor(new CLockBook());
		getCommand("freeze").setExecutor(new CFreeze(this));
		getCommand("accept").setExecutor(new CRequestAccept(this));
		getCommand("specmode").setExecutor(new CSpectatorModeToggle());
		getCommand("tpp").setExecutor(new CTeleportToPlayer(config));
		getCommand("tpp").setTabCompleter(new TCPlayerArgument());
		getCommand("tpb").setExecutor(new CTeleportToPlayerBed(config));
		getCommand("tpb").setTabCompleter(new TCPlayerArgument());
		getCommand("passive").setExecutor(new CPassiveModeToggle(this));
		getCommand("bunder").setExecutor(new CBlockUnderMe());
		getCommand("speed").setExecutor(new CSpeed(config));
		getCommand("vanish").setExecutor(new CVanish(this));
		
		checkTimedStop(config);
	}
	@Override
	public void onDisable() {
		for (ScheduledFuture<?> task: stopTask) {
			task.cancel(true);
		}
	}
	/**
	 * Запустить задачу по автоматическому выключению сервера
	 */
	public void checkTimedStop(Config config) {
		if (!stopTask.isEmpty()) {
			for (ScheduledFuture<?> task : stopTask) {
				task.cancel(false);
			}
			stopTask.clear();
		}
		if (config.isTimedStopEnabled()) {
			ScheduledExecutorService timerServerShutdownPool = Executors.newScheduledThreadPool(1);
			long delay = 0;
			int timedStopDely = config.getTimeDelay();
			if (config.isTimedStopEnabled()) {
				LocalTime time = LocalTime.parse(config.getFixedTime(), DateTimeFormatter.ofPattern("HH:mm:ss:SSS"));
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
				List<Integer> warns = config.getTimeStopedWarns();
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
