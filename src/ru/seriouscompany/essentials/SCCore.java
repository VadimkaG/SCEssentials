package ru.seriouscompany.essentials;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.bukkit.plugin.java.JavaPlugin;

import ru.seriouscompany.essentials.commands.CAFK;
import ru.seriouscompany.essentials.commands.CFeed;
import ru.seriouscompany.essentials.commands.CFly;
import ru.seriouscompany.essentials.commands.CFreeze;
import ru.seriouscompany.essentials.commands.CHeal;
import ru.seriouscompany.essentials.commands.CLockBook;
import ru.seriouscompany.essentials.commands.COpenEnder;
import ru.seriouscompany.essentials.commands.COpenInventory;
import ru.seriouscompany.essentials.commands.CPlayerInfo;
import ru.seriouscompany.essentials.commands.CReload;
import ru.seriouscompany.essentials.commands.CRequestAccept;
import ru.seriouscompany.essentials.commands.CSleepIgnore;
import ru.seriouscompany.essentials.commands.CSuicide;
import ru.seriouscompany.essentials.commands.CTeleportWorld;
import ru.seriouscompany.essentials.commands.CWorld;
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
		Config.init();
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
		
		checkTimedStop();
		
		if (Config.AFK_KICK > 0 || Config.AFK_AUTO > 0) {
			afkTask = new AFKTask();
			afkTask.runTaskTimer(this, 1000, 1000);
		}
		
		getLogger().info("Плагин запущен");
	}
	
	@Override
	public void onDisable() {
		if (afkTask != null)
			afkTask.cancel();
		for (ScheduledFuture<?> task: stopTask) {
			task.cancel(true);
		}
		getLogger().info("Плагин остановлен");
	}
	
	public void checkTimedStop() {
		if (!stopTask.isEmpty()) {
			for (ScheduledFuture<?> task : stopTask) {
				task.cancel(false);
			}
			stopTask.clear();
		}
		if (Config.TIMEDSTOP_ENABLE) {
			ScheduledExecutorService timerServerShutdownPool = Executors.newScheduledThreadPool(1);
			long delay = 0;
			if (Config.TIMEDSTOP_FIXED_TIME) {
				LocalTime time = LocalTime.parse(Config.TIMEDSTOP_TIME, DateTimeFormatter.ofPattern("HH:mm:ss:SSS"));
				LocalDateTime dateTime;
				if (time.isAfter(LocalTime.now()))
					dateTime = LocalDateTime.of(LocalDate.now(), time);
				else
					dateTime = LocalDateTime.of(LocalDate.now().plusDays(1), time);
				delay = LocalDateTime.now().until(dateTime, ChronoUnit.MILLIS);
			} else if (Config.TIMEDSTOP_DELY > 0)
				delay = Config.TIMEDSTOP_DELY * 3600000;
			if (delay > 0) {
				long sec = (delay/1000) % 60;
				long min = ((delay/1000) / 60) % 60;
				long hour = ((delay/1000) / 60) / 60;
				getLogger().info("Автовыключение через "+String.valueOf(hour)+":"+String.valueOf(min)+":"+String.valueOf(sec));
				for (Integer warnSecond : Config.TIMEDSTOP_WARNS) {
					stopTask.add(timerServerShutdownPool.schedule(new Runnable() {
						@Override
						public void run() {
							int sec = warnSecond % 60;
							int min = (warnSecond / 60) % 60;
							int hour = (warnSecond / 60) / 60;
							String secondMessage = String.valueOf(warnSecond) + " ms";
							if (hour > 0)
								secondMessage = String.valueOf(hour) + ":" + String.valueOf(min) + ":" + String.valueOf(sec) + " " + Config.SERVER_OFF_HOURS;
							else if (min > 0)
								secondMessage = String.valueOf(min) + ":" + String.valueOf(sec) + " " + Config.SERVER_OFF_MINUTES;
							else if (sec > 0)
								secondMessage = String.valueOf(sec) + " " + Config.SERVER_OFF_SECONDS;
							getServer().broadcastMessage(Config.SERVER_OFF_WHITH.replace("%%SECONDS%%", secondMessage));
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
