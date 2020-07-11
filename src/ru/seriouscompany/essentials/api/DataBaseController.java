package ru.seriouscompany.essentials.api;

import java.util.HashMap;

import ru.seriouscompany.essentials.SCCore;

public abstract class DataBaseController {
	/**
	 * Получить путь к главной конфигурации плагина
	 * @return
	 */
	public static String getConfigPathMain() {
		return "plugins/"+SCCore.getInstance().getDescription().getName()+"/";
	}
	/**
	 * Получить путь к конфигурации определенного плагина
	 * @param jp
	 * @return
	 */
	public static String getConfigPathPlugin(String pluginName) {
		return "plugins/"+pluginName+"/";
	}
	/**
	 * Объект главной базы данных
	 */
	private static DataBase mainDB = null;
	/**
	 * Получить главную базу данных
	 * @return
	 */
	public static DataBase getBaseMain() {
		if (mainDB == null) {
			mainDB = new DataBase("plugins/"+SCCore.getInstance().getDescription().getName()+"/base.db",SCCore.getInstance().getLogger());
			//mainDB.setConnectMySQL("localhost:3306", "test", login, password);
		}
		return mainDB;
	}
	/**
	 * Хранилище баз данных плагинов
	 */
	private static HashMap<String,DataBase> DB = new HashMap<String,DataBase>();
	/**
	 * Получить базу данных плагина
	 * @param jp - Плагин, для которого зарегистрирована база
	 * @return
	 */
	public static DataBase getBaseForPlugin(String pluginName) {
		if (!dataBaseExist(pluginName))
			registerDataBaseForPlugin(pluginName, new DataBase("plugins/"+pluginName+"/base.db"));
		return DB.get(pluginName);
	}
	/**
	 * Зарегистрировать базу для определенного плагина
	 * @param pluginName - имя плагина
	 * @param db - база данных
	 */
	public static void registerDataBaseForPlugin(String pluginName, DataBase db) {
		if (!DB.containsKey(pluginName)) {
			DB.put(pluginName, db);
		}
	}
	/**
	 * Существует ли база данных
	 * @param pluginName - имя плагина
	 */
	public static boolean dataBaseExist(String pluginName) {
		return DB.containsKey(pluginName);
	}
}
