package io.github.dougkeller;

import java.util.Comparator;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemStackComparator implements Comparator<ItemStack> {

	@Override
	public int compare(ItemStack a, ItemStack b) {
		if(a == null && b == null)
			return 0;
		if(a == null)
			return 1;
		if(b == null)
			return -1;
		
		Material aType = a.getType(), bType = b.getType();
		
		if(aType == bType) {
			short aDamage = a.getDurability(), bDamage = b.getDurability();
			
			if(aDamage == bDamage) {
				int aAmount = a.getAmount(), bAmount = b.getAmount();
				return bAmount - aAmount;
			} else {
				return aDamage - bDamage;
			}
			
		} else {
			return aType.compareTo(bType);
		}
	}

}
