package ru.seriouscompany.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.SCCore;

public class CFixSpeed implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isOp() || args.length < 1) return false;
		
		Player player = Bukkit.getPlayer(args[0]);
		
		if (player == null) {
			sender.sendMessage("Игрок не найден");
			return true;
		}

		player.setFlySpeed((float) SCCore.speedDefaultFly());
		player.setWalkSpeed((float) SCCore.speedDefaultWalk());
		sender.sendMessage("Игроку установлена нормальная скорость");
		
		return true;
	}

}
