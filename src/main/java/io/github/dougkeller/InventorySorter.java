package io.github.dougkeller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventorySorter {
	private Inventory inventory;
	
	
	public InventorySorter(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public void sort() {
		ItemStack[] contents = inventory.getContents();
		ItemStack[] combinedContents = combineContents(contents);
		ItemStack[] sortedContents = sortContents(combinedContents);
		inventory.setContents(sortedContents);
	}
	
	private ItemStack[] combineContents(ItemStack[] contents) {
		for(int i = 0; i < contents.length; i++) {
			ItemStack a = contents[i];
			if (a == null) {
				continue;
			}

			for(int j = i + 1; j < contents.length; j++) {
				ItemStack b = contents[j];
				if (b == null || !isSameItem(a, b)) {
					continue;
				}

				int maxStackSize = a.getMaxStackSize();
				int aAmount = a.getAmount();
				if (aAmount >= maxStackSize) {
					continue;
				}

				int bAmount = b.getAmount();
				int amountToCombine = Math.min(maxStackSize - aAmount, bAmount);
				a.setAmount(aAmount + amountToCombine);
				b.setAmount(bAmount - amountToCombine);

				if (b.getAmount() == 0) {
					contents[j] = null;
				}
			}
		}
		
		return contents;
	}
	
	private boolean isSameItem(ItemStack a, ItemStack b) {
		if (a.getType() != b.getType()) {
			return false;
		}
		
		return a.getItemMeta().equals(b.getItemMeta());
	}
	
	private ItemStack[] sortContents(ItemStack[] contents) {
		List<ItemStack> list = Arrays.asList(contents);
		Collections.sort(list, new ItemStackComparator());
		return (ItemStack[]) list.toArray();
	}
}
