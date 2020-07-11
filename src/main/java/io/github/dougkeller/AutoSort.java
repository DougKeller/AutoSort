package io.github.dougkeller;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class AutoSort extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this,  this);
		getLogger().info("AutoSort loaded!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("AutoSort disabled!");
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		sortIfChestInventory(event.getInventory());
	}
	
	public void sortIfChestInventory(Inventory inventory) {
		if (inventory.getType() != InventoryType.CHEST) {
			return;
		}
		
		InventorySorter sorter = new InventorySorter(inventory);
		sorter.sort();
	}
}
