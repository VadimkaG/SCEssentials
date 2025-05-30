package ru.seriouscompany.essentials;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;

import ru.seriouscompany.essentials.api.Utils;

public enum Lang {
	PERMISSION_DENY("PermissionDeny","Вам не разрешено использывать данное действие"),
	COMMAND_FOR_PLAYERS("CommandOnlyForPlayers","Эта команда предназначена для игроков"),
	
	AFK_ALREADY_WAIT("Afk.AlreadyWait","Вы уже ожидаете афк"),
	AFK_ON("Afk.On","* %PLAYER% теперь афк"),
	AFK_OFF("Afk.Off","* %PLAYER% больше не афк"),
	AFK_WAIT("Afk.Wait","Подождите %TIME% секунд"),
	AFK_DENIED_WHEN_FREEZED("Afk.DeniedWhenFreezed","Вы заморожены, вам нельзя использовать AFK"),
	AFK_KICK_REASON("Afk.KickReason","Вы находились в афк дольше %TIME% минут(ы)"),
	AFK_PREFIX("Afk.Prefix","[AFK] "),
	
	FLY_DENYED("Fly.Deny","Полет выключен сервером"),
	FLY_ON("Fly.OnSelf","Теперь вы можете летать"),
	FLY_OFF("Fly.OffSelf","Теперь вы не можете летать"),
	FLY_ON_OTHER("Fly.OnOther","Теперь %PLAYER% может летать"),
	FLY_OFF_OTHER("Fly.OffOther","Теперь %PLAYER% не может летать"),
	
	HEAL_SUCCESS("Heal.Success","Вы вылечены"),
	HEAL_SUCCESS_OTHER("Heal.SuccessOther","Вы вылечили игрока %PLAYER%"),
	HEAL_ERR_INTEGER("Heal.ErrInteger","Вы должны указать число"),
	
	SPECTATOR_MODE_ON("SpectatorMode.on","Вы включили режим зрителя"),
	SPECTATOR_MODE_OFF("SpectatorMode.off","Вы выключили режим зрителя"),
	
	PASSIVE_MODE_ON("PassiveMod.on","Вы включили пассивный режим"),
	PASSIVE_MODE_OFF("PassiveMod.off","Вы выключили пассивный режим"),
	
	SLEEP_ON("Sleep.on","Теперь вам нужно спать"),
	SLEEP_OFF("Sleep.off","Теперь вам не нужно спать"),
	
	UNDRESSED("undressed.success","Игрок успешно раздет"),
	UNDRESSED_FULL("undressed.full","Инвентарь игрока забит. Некоторые вещи не сняты."),
	UNDRESSED_TARGET("undressed.target","Вас раздел %PLAYER%"),
	
	FREEZE_ON("freeze.on","Вы заморозили игрока %PLAYER%"),
	FREEZE_ON_TARGET("freeze.target.on","Вас заморозил %PLAYER%"),
	FREEZE_OFF("freeze.off","Вы разморозили игрока %PLAYER%"),
	FREEZE_OFF_TARGET("freeze.target.off","Вас разморозил %PLAYER%"),
	FREEZE_SELF("freeze.self","Нельзя морозить себя"),
	YOU_FREEZED("freeze.denyYorFreezed","Вы заморожены и не можете пользоваться командой заморозки"),
	
	COMBAT_IN_YOU("Combat.inCombat","Вы в бою!"),
	COMBAT_OUT_YOU("Combat.outCombat","Вы больше не в бою!"),
	
	WORLD_LIST("World.List","========== Загруженные миры =========="),
	WORLD_LIST_FOOTER("World.ListFooter","=============================="),
	
	WORLD_LOAD("World.Load","Начата подгрузка мира '%WORLD%', пожалуйста подождите..."),
	WORLD_LOAD_COMPLETE("World.LoadComplete","Подгрузка мира '%WORLD%', завершена."),
	
	WORLD_LOAD_AUTO_SET("World.LoadAutoAdded","Мир %WORLD% добавлен в автозагрузку"),
	WORLD_LOAD_AUTO_UNSET("World.LoadAutoRemoved","Мир %WORLD% удален из автозагрузки"),
	
	WORLD_UNLOAD("World.Unload","Начата отгрузка мира '%WORLD%', пожалуйста подождите..."),
	WORLD_UNLOAD_COMPLETE("World.UnloadComplete","Отгрузка мира '%WORLD%' завершена."),
	WORLD_UNLOAD_ERROR("World.UnloadError","Отгрузка мира '%WORLD%' не удалась."),
	
	PLAYER_BED_NOT_FOUND("Player.BedNotFound","Кровать игрока %PLAYER% не найдена"),
	PLAYER_NOT_FOUND("Player.NotFound","Игрок с ником %PLAYER% не найден"),
	WORLD_NOT_FOUND("World.NotFound","Мир %WORLD% не существует"),
	
	SERVER_OFF_WHITH("ServerOff.With","Сервер будет выключен через %%SECONDS%%."),
	SERVER_OFF_SECONDS("ServerOff.Seconds","секунд"),
	SERVER_OFF_MINUTES("ServerOff.Minutes","минут"),
	SERVER_OFF_HOURS("ServerOff.Hours","часов"),
	
	PLAYER_STAT("stats.header","========== Статистика игрока %PLAYER% =========="),
	PLAYER_STAT_FOOTER("stats.footer","=============================="),
	
	YES("yes","Да"),
	NO("no","Нет"),
	
	WORLD("stats.world","Мир"),
	GAME_MODE("stats.gamemode","Игровой режим"),
	HEALTH("stats.health","Здоровье"),
	FOOD_LEVEL("stats.food_level","Сытость"),
	EXP("stats.exp","Опыт"),
	OPERATOR("stats.operator","Оператор"),
	FREEZED("stats.freezed","Заморожен"),
	FLYING("stats.flying","Летает"),
	COMBAT_IN("stats.combat","В бою"),
	PASSIVEMODE_STAT("stats.passivemode","Пассивный"),
	
	ANTIDOS_FULL("antidos.fulled","Очередь на сервер переполнена. Вход временно ограничен. Приходите позже."),
	ANTIDOS_SLOWMODE("antidos.slowmode","Активен медленный режим. Пропускает одного игрока в секунду. Пожалуйста проявите терпение и не пытайтесь заходить слишком часто."),
	ANTIDOS_TEMPBAN("antidos.tempban","Слишком быстрый перезаход в игру. Подождите %SECONDS% секунд.");
	
	String configAlias;
	String message;
	
	Lang(String configAlias, String defaultMessage) {
		this.configAlias = configAlias;
		this.message = defaultMessage;
	}
	
	@Override
	public String toString() {
		return message;
	}
	/**
	 * Загрузить конфигурацию
	 */
	public static void loadConfiguration() {
		
		YamlConfiguration config;
		
		File f = new File(SCCore.getInstance().getDataFolder().getPath()+"/messages.yml");
		if (f.exists() && f.canRead()) {
			config = YamlConfiguration.loadConfiguration(f);
			for (Lang value: Lang.values()) {
				if (config.contains(value.configAlias))
					value.message = Utils.replaceColorCodes(config.getString(value.configAlias));
			}
		} else if (!f.exists()) {
			if (!f.getParentFile().exists() && f.getParentFile().getParentFile().canWrite())
				f.getParentFile().mkdir();
			config = new YamlConfiguration();
			for (Lang value: Lang.values()) {
				config.set(value.configAlias, value.message);
			}
			try {
				config.save(f);
			} catch (IOException e) {
				SCCore.getInstance().getLogger().log(Level.WARNING,"Не удалось сохранить файл переводов",e);
			}
		}
	}
}
