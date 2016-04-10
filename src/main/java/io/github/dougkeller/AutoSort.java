package io.github.dougkeller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

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
		ItemStack[] contents = event.getInventory().getContents();
		combineContents(contents);
		List<ItemStack> list = Arrays.asList(contents);
		Collections.sort(list, new ItemStackComparator());
		contents = (ItemStack[]) list.toArray();
		event.getInventory().setContents(contents);
	}
	
	private void combineContents(ItemStack[] contents) {
		for(int i = 0; i < contents.length; i++) {
			ItemStack a = contents[i];
			if(a != null) {
				for(int j = i + 1; j < contents.length; j++) {
					ItemStack b = contents[j];
					if(b != null && sameItem(a, b)) {
						int maxStackSize = a.getMaxStackSize();
						int aAmount = a.getAmount(), bAmount = b.getAmount();
						if(aAmount < maxStackSize) {
							int amountToCombine = Math.min(maxStackSize - aAmount, bAmount);
							a.setAmount(aAmount + amountToCombine);
							b.setAmount(bAmount - amountToCombine);
							if(b.getAmount() == 0) {
								contents[j] = null;
							}
						}
					}
				}
			}
		}
	}
	
	private boolean sameItem(ItemStack a, ItemStack b) {
		return a.getType() == b.getType()
			   && a.getDurability() == b.getDurability();
	}
}
