package com.vartala.soulofw0lf.rpgtrades;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import com.vartala.soulofw0lf.rpgtrades.TradeHolder.Slot;



public class InventoryListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInv(InventoryClickEvent event){
		Player player = (Player)event.getWhoClicked();
		//if(!PlayerManager.isPlayerInTrade(player.getName()))
		//	return;
	
		if(event.getInventory().getTitle() != "Trade:")
			return;
		if (event.isShiftClick()){
			event.setResult(Result.DENY);
			return;
		}
		if(event.getRawSlot() < 0)
			return;
		if(event.getCurrentItem().getTypeId() == 0 && event.getCursor().getTypeId() == 0){
			return;
		}
	//	if (player.isSneaking()){
	//		cancelClick(event);
	//	}
		Trade trade = TradeManager.getTrade(player.getName());
		Slot loc = trade.getHolder().getSlot(event.getRawSlot());
		Slot playerLoc = trade.getPlayerSlot(player.getName());
		if(playerLoc.equals(loc)){
			setItem(event);
			trade.setBothDeny();
			player.updateInventory();
			return;
		}
		if(loc == null)
			return;
		if(loc.equals(Slot.SLOTACCEPT))
			trade.doAccept(player.getName());
		if(loc.equals(Slot.SLOTDENY)){
			trade.doClose(player.getName());
			player.closeInventory();}
		cancelClick(event);
	}
    @SuppressWarnings("deprecation")
	private void setItem(InventoryClickEvent event){
		ItemStack tmp = event.getCurrentItem();
		event.setCurrentItem(event.getCursor());
		event.setCursor(tmp);
		event.setCancelled(true);
		event.setResult(Result.ALLOW);
	}
	
	@SuppressWarnings("deprecation")
	private void cancelClick(InventoryClickEvent event){
		event.setCursor(event.getCursor());
		event.setCurrentItem(event.getCurrentItem());
		event.setCancelled(true);
		event.setResult(Result.DENY);
		((Player)event.getWhoClicked()).updateInventory();
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent event){
		if (Trade.trading == true){
		Player player = (Player)event.getPlayer();
		if(!event.getInventory().getTitle().equals("Trade:"))
			return;
		if(!PlayerManager.isPlayerInTrade(player.getName()))
			return;
		RpgTrades.doClose(player.getName());
		return;
		}
	}

}
