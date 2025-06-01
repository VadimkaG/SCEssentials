package ru.seriouscompany.essentials.meta;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class PlayerEquipmentHolder implements InventoryHolder, MetadataValue {

	public static final String METADATA_NAME = "EquipmentInventory";
	
	protected Plugin plugin;
	protected Player owner;
	protected Inventory inventory;
	
	public PlayerEquipmentHolder(Plugin plugin,Player target) {
		this.plugin = plugin;
		owner = target;
		inventory = Bukkit.createInventory(this, 9);
		PlayerInventory inv = target.getInventory();
		inventory.setItem(0, inv.getHelmet());
		inventory.setItem(1, inv.getChestplate());
		inventory.setItem(2, inv.getLeggings());
		inventory.setItem(3, inv.getBoots());
		inventory.setItem(4, inv.getItemInOffHand());
	}
	public void close() {
		if (owner.hasMetadata(METADATA_NAME))
			owner.removeMetadata(METADATA_NAME, plugin);
	}
	/**
	 * Когда меняется инвентарь игрока
	 * @param slot
	 * @param cursorItem
	 */
	public void onInventoryChange(int slot, ItemStack cursorItem) {
		switch (slot) {
		// Голова
		case 39:
			inventory.setItem(0, cursorItem);
			break;
		// Грудь
		case 38:
			inventory.setItem(1, cursorItem);
			break;
		// Шнаты
		case 37:
			inventory.setItem(2, cursorItem);
			break;
		// Ботинки
		case 36:
			inventory.setItem(3, cursorItem);
			break;
		// Дополнительная рука
		case 40:
			inventory.setItem(4, cursorItem);
		}
	}
	/**
	 * Когда чтото меняется в импровизированном инвентаре
	 * @param slot
	 * @param cursorItem
	 */
	public void changeInventory(int slot, ItemStack cursorItem) {
		PlayerInventory inv = owner.getInventory();
		switch (slot) {
		// Голова
		case 0:
			inv.setHelmet(cursorItem);
			break;
		// Грудь
		case 1:
			inv.setChestplate(cursorItem);
			break;
		// Шнаты
		case 2:
			inv.setLeggings(cursorItem);
			break;
		// Ботинки
		case 3:
			inv.setBoots(cursorItem);
			break;
		// Дополнительная рука
		case 4:
			inv.setItem(40, cursorItem);
		}
	}
	public static ItemStack calculateNewItem(ItemStack clicked, ItemStack cursor, InventoryAction action) {
		ItemStack item;
		switch (action) {
			case PICKUP_ALL:
				return null;
		case PICKUP_HALF:
			if (clicked.getAmount() > 1) {
				item = clicked.clone();
				item.setAmount(clicked.getAmount() / 2);
				return item;
			}
			return null;
		case PICKUP_SOME:
			if (clicked.getAmount() > 1) {
				item = clicked.clone();
				item.setAmount(clicked.getAmount() - 1);
				return item;
			}
			return null;
		case PLACE_ALL:
			if (cursor != null && clicked != null && cursor.isSimilar(clicked)) {
				int total = clicked.getAmount() + cursor.getAmount();
				int maxStack = clicked.getMaxStackSize();
				item = clicked.clone();
				if (total > maxStack) {
					item.setAmount(maxStack);
				} else {
					item.setAmount(total);
				}
				return item;
			}
			return cursor;
		case PLACE_SOME:
			item = cursor.clone();
			item.setAmount(clicked == null ? 1  : clicked.getAmount() + 1);
			return item;
		case PLACE_ONE:
			item = cursor.clone();
			item.setAmount(clicked == null ? 1 : clicked.getAmount() + 1);
			return item;
		case SWAP_WITH_CURSOR:
			return cursor;
		default:
			return clicked;
		}
	}
	public static boolean hasMeta(Player player) {
		return player.hasMetadata(METADATA_NAME);
	}
	public static PlayerEquipmentHolder from(Player player, Plugin plugin) {
		List<MetadataValue> metadata = player.getMetadata(METADATA_NAME);
		for (MetadataValue value : metadata) {
			if (value instanceof PlayerEquipmentHolder)
				return (PlayerEquipmentHolder) value;
		}
		PlayerEquipmentHolder meta = new PlayerEquipmentHolder(plugin,player);
		player.setMetadata(METADATA_NAME, meta);
		return meta;
	}
	@Override
	public Inventory getInventory() {return inventory;}
	@Override
	public boolean asBoolean() {return false;}
	@Override
	public byte asByte() {return 0;}
	@Override
	public double asDouble() {return 0;}
	@Override
	public float asFloat() {return 0;}
	@Override
	public int asInt() {return 0;}
	@Override
	public long asLong() {return 0;}
	@Override
	public short asShort() {return 0;}
	@Override
	public String asString() {return null;}
	@Override
	public Plugin getOwningPlugin() {return plugin;}
	@Override
	public void invalidate() {}
	@Override
	public Object value() {return inventory;}

}
