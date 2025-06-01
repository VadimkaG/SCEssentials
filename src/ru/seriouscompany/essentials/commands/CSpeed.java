package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.essentials.Lang;

public class CSpeed implements CommandExecutor {
	
	protected Config config;
	public CSpeed(Config config) {
		this.config = config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.speed"))
			return true;
		
		if (args.length < 1)
			return false;
		
		// Определяем значение
		Float speed;
		switch (args[0]) {
		case "default":
		case "def":
		case "d":
			speed = null;
			break;
		default:
			try {
				speed = Float.parseFloat(args[0]);
			} catch(NumberFormatException e) {
				sender.sendMessage(e.getLocalizedMessage());
				return true;
			}
		}
		
		// Ищем цель
		Player target;
		if (args.length > 2) {
			target = Bukkit.getPlayer(args[2]);
			if (target == null) {
				sender.sendMessage(Lang.PLAYER_NOT_FOUND.toString().replaceAll("%PLAYER%", args[1]));
				return true;
			}
		} else {
			if (!(sender instanceof Player))
				return false;
			target = (Player)sender;
		}

		// Устанавливаем скорость
		try {
			boolean isFly = false;
			if (args.length > 1)
			switch (args[1].toLowerCase()) {
			case "fly":
			case "f":
				isFly = true;
			}
			if (isFly) {
				if (speed == null)
					speed = config.getSpeedDefaultFly();
				target.setFlySpeed(speed);
			} else {
				if (speed == null)
					speed = config.getSpeedDefaultWalk();
				target.setWalkSpeed(speed);
			}
		} catch (Exception e) {
			sender.sendMessage(e.getLocalizedMessage());
		}
		
		return true;
	}

}
