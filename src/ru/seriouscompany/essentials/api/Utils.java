package ru.seriouscompany.essentials.api;

public abstract class Utils {
	/**
	 * Рескрасить текст
	 * Заменить & на \00A7
	 * @param msg - Сообщение в котором нужно заменить символ
	 * @return
	 */
	public static String replaceColorCodes(String msg) {
		if (msg == null) return "";
		return msg.replaceAll("&([a-z0-9])", "\u00A7$1");
	}
}
