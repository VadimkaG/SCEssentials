package ru.seriouscompany.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.essentials.api.PlayerFreezeContainer;

public class CPlayerInfo implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.isPermissionSet("scessentials.pinfo")) {
			if (args.length != 1) return false;
			Player target = SCCore.getInstance().getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage("Игрока с таким ником не существует");
				return true;
			}
			sender.sendMessage("========== Статистика игрока "+target.getName()+" ==========");
			sender.sendMessage("Мир: "
					+ target.getLocation().getWorld() + "("
					+ target.getLocation().getBlockX() + ","
					+ target.getLocation().getBlockY() + ", "
					+ target.getLocation().getBlockZ() + ")");
			sender.sendMessage("IP: " + target.getAddress().getAddress().getHostAddress());
			sender.sendMessage("Игровой режим: "+target.getGameMode().name());
			sender.sendMessage("Здоровье: "+String.valueOf(target.getHealth()));
			sender.sendMessage("Сытость: "+String.valueOf(target.getFoodLevel()));
			if (target.isOp())
				sender.sendMessage("Оператор: да");
			else
				sender.sendMessage("Оператор: нет");
			if (PlayerFreezeContainer.contains(target))
				sender.sendMessage("Заморожен: да");
			else
				sender.sendMessage("Заморожен: нет");
			
			if (CAFK.isPlayerAfk(target.getName()))
				sender.sendMessage("АФК: да");
			else
				sender.sendMessage("АФК: нет");
			
			if (target.isFlying())
				sender.sendMessage("Летает: да");
			else
				sender.sendMessage("Летает: нет");
			sender.sendMessage("====================");
		} else
			sender.sendMessage("Вам не разрешено использывать данную команду");
		return true;
	}

}
