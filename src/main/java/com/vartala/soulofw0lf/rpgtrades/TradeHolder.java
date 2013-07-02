package com.vartala.soulofw0lf.rpgtrades;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class TradeHolder implements InventoryHolder {
	RpgTrades Rpgt;
	public TradeHolder(RpgTrades rpgt){
		this.Rpgt = rpgt;
	}
	
	public static enum Slot{
		SLOTA,
		SLOTB,
		SLOTACCEPT,
		SLOTDENY,
		SLOTCONFIRM
	}
	
	private final Inventory inventory = Bukkit.createInventory(this, 36, "Trade:");
	private final int slotA[] = {0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30};
	private final int slotB[] = {5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34, 35};
	private final int slotAccept[] = {13};
	private final int slotDeny[] = {22};
	private final int slotAConfirm[] = {4};
	private final int slotBConfirm[] = {31};
	
	private ItemStack itmAccept = new ItemStack(Material.WOOL);
	private ItemStack itmDeny = new ItemStack(Material.WOOL);
	private ItemStack itmConfirm = new ItemStack(Material.WOOL);
	private ItemStack itmNConfirm = new ItemStack(Material.WOOL);
	
	public TradeHolder(){
		ItemMeta imAccept = itmAccept.getItemMeta();
		imAccept.setDisplayName(" " + RpgTrades.blockAccept);
		itmAccept.setItemMeta(imAccept);
		imAccept.setDisplayName(" " + RpgTrades.blockDeny);
		itmDeny.setItemMeta(imAccept);
		imAccept.setDisplayName(" " + RpgTrades.blockWaiting);
		itmNConfirm.setItemMeta(imAccept);
		imAccept.setDisplayName(" " + RpgTrades.blockConfirm);
		itmConfirm.setItemMeta(imAccept);
		itmAccept.setDurability((short)5);
		itmDeny.setDurability((short)14);
		itmConfirm.setDurability((short)11);
		for(int x : slotAccept)
			inventory.setItem(x, itmAccept);
		for(int x : slotDeny)
			inventory.setItem(x, itmDeny);
		for(int x : slotAConfirm)
			inventory.setItem(x, itmNConfirm);
		for(int x : slotBConfirm)
			inventory.setItem(x, itmNConfirm);
	}

	public Inventory getInventory() {
		return inventory;
	}
	
	public Slot getSlot(int x){
		if(arrayContains(slotA, x))
			return Slot.SLOTA;
		if(arrayContains(slotB, x))
			return Slot.SLOTB;
		if(arrayContains(slotAccept, x))
			return Slot.SLOTACCEPT;
		if(arrayContains(slotDeny, x))
			return Slot.SLOTDENY;
		if(arrayContains(slotAConfirm, x) || arrayContains(slotBConfirm, x))
			return Slot.SLOTCONFIRM;
		return null;
	}
	
	public boolean arrayContains(int[] x, int y){
		for(int z : x)
			if(z == y)
				return true;
		return false;
	}
	
	public void PlayerAConfirm(){
		for(int x : slotAConfirm)
			inventory.setItem(x, itmConfirm);
	}
	
	public void PlayerBConfirm(){
		for(int x : slotBConfirm)
			inventory.setItem(x, itmConfirm);
	}
	
	public void PlayerAWaiting(){
		for(int x : slotAConfirm){
			inventory.remove(x);
			inventory.setItem(x, itmNConfirm);
		}
	}
	
	public void PlayerBWaiting(){
		for(int x : slotBConfirm){
			inventory.remove(x);
			inventory.setItem(x, itmNConfirm);
		}
	}
	
	@SuppressWarnings({ "incomplete-switch" })
	public void givePlayerSlot(Player player, Slot slot){
		switch(slot){
		case SLOTA:
			for(int x : slotA)
				if(inventory.getItem(x) != null){
                    HashMap<Integer, ItemStack> noFit = player.getInventory().addItem(inventory.getItem(x));
                    for(ItemStack stack : noFit.values()) {
                        Bukkit.getWorld(player.getWorld().getName()).dropItem(player.getLocation(), stack);
                    }
                }
			break;
		case SLOTB:
			for(int x : slotB)
				if(inventory.getItem(x) != null){
                    HashMap<Integer, ItemStack> noFit = player.getInventory().addItem(inventory.getItem(x));
                    for(ItemStack stack : noFit.values()) {
                        Bukkit.getWorld(player.getWorld().getName()).dropItem(player.getLocation(), stack);
                    }
                }
			break;
		}
	}
	
	public void clearInv(){
		inventory.clear();
	}

}
