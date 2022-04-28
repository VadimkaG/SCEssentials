package ru.seriouscompany.essentials.commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.ChunkGenerator;

import ru.seriouscompany.essentials.Lang;

public class CWorldLoad implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isPermissionSet("scessentials.world.load")) {
			sender.sendMessage(Lang.PERMISSION_DENY.toString());
			return true;
		}
		if (args.length >= 1 && args.length <= 2) {
			if (args.length > 1) {
				loadWorld(args[0], args[1]);
			} else {
				loadWorld(args[0], "NORMAL");
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Пустой генератор чанков
	 */
	private static class EmptyChunkGenerator extends ChunkGenerator {
		@Override
		public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
			return createChunkData(world);
		}
	}
	/**
	 * Загрузить мир
	 * @param name Название мира
	 * @param type Тип мира
	 */
	public static void loadWorld(String name, String type) {
		WorldCreator worldc = new WorldCreator(name);
		String worldType = type.toUpperCase();
		switch (worldType) {
		case "EMPTY":
			worldc.environment(Environment.NORMAL);
			worldc.generator(new EmptyChunkGenerator());
			break;
		case "FLAT":
			worldc.environment(Environment.NORMAL);
			worldc.type(WorldType.FLAT);
			break;
		case "THE_END":
		case "NETHER":
			worldc.environment(Environment.valueOf(type));
			break;
		default:
			worldc.environment(Environment.NORMAL);
		}
		Bukkit.broadcastMessage(Lang.WORLD_LOAD.toString().replace("%WORLD%", name));
		worldc.createWorld();
		Bukkit.broadcastMessage(Lang.WORLD_LOAD_COMPLETE.toString().replace("%WORLD%", name));
	}

}
