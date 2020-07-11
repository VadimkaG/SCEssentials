package ru.seriouscompany.essentials.api;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class DataBase {
	protected Connection connection = null;
	
	private String PATH = "";
	protected Logger LOGGER = null;
	
	protected int TYPE;
	
	public static final int TYPE_SQLITE = 0;
	public static final int TYPE_MYSQL = 1;

	protected String AUTH_LINK = null;
	protected String AUTH_BASENAME = null;
	protected String AUTH_LOGIN = null;
	protected String AUTH_PASSWORD = null;
	
	/**
	 * Создать базу данных
	 * @param path - путь к базе
	 */
	public DataBase(String path) {
		this(path,LogManager.getLogManager().getLogger(""));
	}
	/**
	 * Создать базу данных
	 * @param path - путь к базе
	 * @param logger - Логгер
	 */
	public DataBase(String path, Logger logger) {
		PATH = path;
		LOGGER = logger;
		TYPE = TYPE_SQLITE;
	}
	/**
	 * Переключить тип базы на mysql
	 * @param baselink - Ссылка на базу данных
	 * @param basename - Имя базы данных
	 * @param login - Логин
	 * @param password - Пароль
	 */
	public void setConnectMySQL(String baselink, String basename, String login, String password) {
		AUTH_LINK = baselink;
		AUTH_BASENAME = basename;
		AUTH_LOGIN = login;
		AUTH_PASSWORD = password;
		TYPE = TYPE_MYSQL;
	}
	/**
	 * Переключить тип базы на sqlite
	 * @param path
	 */
	public void setConnectSQLite(String path) {
		PATH = path;
		TYPE = TYPE_SQLITE;
	}
	/**
	 * Подключиться к БД
	 */
	public void connect() {
		try {
			if (connection != null && !connection.isClosed()) return;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			switch (TYPE) {
			case TYPE_MYSQL:
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://"+AUTH_LINK+"/"+AUTH_BASENAME,AUTH_LOGIN,AUTH_PASSWORD);
				break;
			case TYPE_SQLITE:
			default:
				File file = new File(PATH).getParentFile();
				if (!file.exists()) file.mkdirs();
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:"+PATH);
				break;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Отключиться от БД
	 */
	public void disconnect() {
		try {
			if (connection != null && !connection.isClosed())
				connection.close();
			connection = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Запрос в БД
	 * @param query - запрос
	 * @return ResultSet
	 */
	public ResultSet query(String query) {
		return query(query, false);
	}
	/**
	 * Запрос в БД
	 * @param query - запрос
	 * @param update false = возвращать ResultSet
	 * @return ResultSet при update = false и null при update = true
	 */
	public ResultSet query(String query, boolean update) {
		if (connection == null) {
			LOGGER.info("Ошибка, при попытке исполнения запроса: База данных не подключена");
			return null;
		}
		try {
			Statement statement = connection.createStatement();
			if (update) {
				int affectedRows = statement.executeUpdate(query);
				//connection.commit();
				if (affectedRows == 0)
					return null;
				else
					return statement.getGeneratedKeys();
			} else
				return statement.executeQuery(query);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"Ошибка SQL: "+e.getMessage(),e);
			return null;
		}
	}
	/**
	 * Подключена ли база данных
	 */
	public boolean isConnected() {
		try {
			if (connection != null && !connection.isClosed()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Получить тип базы данных
	 */
	public int getType() {
		return TYPE;
	}
}
