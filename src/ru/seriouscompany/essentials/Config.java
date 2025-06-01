package ru.seriouscompany.essentials;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {
	protected Plugin plugin;
	
	protected boolean timedStopEnabled;
	protected boolean timedStopIsTimeFixed;
	protected String  timedStopFixedTime;
	protected int     timedStopDelay;
	
	protected float speedDefaultWalk;
	protected float speedDefaultFly;
	
	protected boolean combatKickEnabled;
	protected boolean combatMessagesEnabled;
	protected long    combadDuration;
	
	protected boolean teleportImmunityEnabled;
	
	protected boolean enderPearlPassthrowFixed;
	
	protected String disallowedNamePrefix;
	
	public Config(Plugin plugin) {
		this.plugin = plugin;
	}
	/**
	 * Прочитать информацию из конфига
	 */
	public void read() {
		FileConfiguration config = plugin.getConfig();
		
		timedStopEnabled     = config.getBoolean("Timedstop.Enable",false);
		timedStopIsTimeFixed = config.getBoolean("Timedstop.FixedTime",true);
		timedStopFixedTime   = config.getString("Timedstop.Time", "04:00:00:000");
		timedStopDelay       = config.getInt("Timedstop.Delyhour",1);
		
		speedDefaultWalk = (float)config.getDouble("speedDefault.walk", 0.2d);
		speedDefaultFly  = (float)config.getDouble("speedDefault.fly", 0.2d);
		
		combatKickEnabled     = config.getBoolean("Combat.KillCombatLeavers", false);
		combatMessagesEnabled = config.getBoolean("Combat.CombatMessages", false);
		combadDuration        = config.getLong("Combat.CombatTime", 5);
		
		teleportImmunityEnabled = config.getBoolean("teleport.allowImmunity", false);
		enderPearlPassthrowFixed = config.getBoolean("teleport.disallowPearlTeleportIntoBlock", true);
		
		disallowedNamePrefix = config.getString("antidos.disallowedNamePrefix", null);
	}
	/**
	 * Перезагрузить конфигурацию
	 */
	public void reload() {
		plugin.reloadConfig();
		read();
	}
	/**
	 * Включино ли автоматическое отключение сервера
	 * @return
	 */
	public boolean isTimedStopEnabled() {
		return timedStopEnabled;
	}
	/**
	 * Абсолютно ли время выключение сервера или относительное
	 * @return
	 */
	public boolean isTimedStopFixedTime() {
		return timedStopIsTimeFixed;
	}
	/**
	 * Получить фиксированное время выключение сервера
	 * @return
	 */
	public String getFixedTime() {
		return timedStopFixedTime;
	}
	/**
	 * Получить относительное время выключения сервера
	 * @return
	 */
	public int getTimeDelay() {
		return timedStopDelay;
	}
	/**
	 * Стандартная скорость пешком
	 * @return
	 */
	public float getSpeedDefaultWalk() {
		return speedDefaultWalk;
	}
	/**
	 * Стандартная скорость полет
	 * @return
	 */
	public float getSpeedDefaultFly() {
		return speedDefaultFly;
	}
	/**
	 * Включен ли режим убийства игроков, которые выходят во время боя
	 * @return
	 */
	public boolean isCombatKickEnabled() {
		return combatKickEnabled;
	}
	/**
	 * Включены ли сообщения у режима защиты пвп
	 * @return
	 */
	public boolean isCombatMessagesEnabled() {
		return combatMessagesEnabled;
	}
	/**
	 * Длительность боя, после которой режим защиты пвп выключается
	 * @return
	 */
	public long getCombatDuration() {
		return combadDuration;
	}
	/**
	 * Включен ли имунитет к телепортам
	 * @return
	 */
	public boolean isTeleportImmunityEnabled() {
		return teleportImmunityEnabled;
	}
	/**
	 * Запрет телепорта внутрь блоков с помощью эндер-жемчугов
	 * @return
	 */
	public boolean isEnderPearlPassthrowFixed() {
		return enderPearlPassthrowFixed;
	}
	/**
	 * Получить запрещенный префикс в нике
	 * @return
	 */
	public String getDisallowedNamePrefix() {
		return disallowedNamePrefix;
	}
	@SuppressWarnings("unchecked")
	public List<Integer> getTimeStopedWarns() {
		return (List<Integer>) plugin.getConfig().getList("Timedstop.Warnings");
	}
	/**
	 * Получить список миров
	 * ВНИМАНИЕ! Читает список в реальном времени
	 * @return
	 */
	public Map<String, String> readWorldList() {
		File f = new File(plugin.getDataFolder().getPath()+"/worlds.yml");
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
	 * Сохранить миры, которые будут автоматически загружены при запуске плагина
	 * ВНИМАНИЕ! Сохраняет в реальном времени
	 * @param worlds - Список миров
	 */
	public void saveWorldList(Map<String, String> worlds) {
		File f = new File(plugin.getDataFolder().getPath()+"/worlds.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		y.set("worlds", null);
		for (Entry<String, String> world: worlds.entrySet()) {
			y.set("worlds."+world.getKey(), world.getValue());
		}
		try {y.save(f);} catch (IOException e) {
			Bukkit.getLogger().info("Ошибка сохранения worlds.yml.");
		}
	}
}
