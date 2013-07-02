package com.vartala.soulofw0lf.rpgtrades;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.vartala.soulofw0lf.rpgtrades.TradeHolder.Slot;


public class Trade {
	RpgTrades Rpgt;

	public Trade(RpgTrades rpgt){
		this.Rpgt = rpgt;
	}

	private String a;
	private String b;
	private boolean bA = false;
	private boolean bB = false;
	private TradeHolder tradeInv = new TradeHolder();
	static boolean trading = false;

	public Trade(String a, String b) {
		this.a = a;
		this.b = b;
		PlayerManager.getExactPlayer(a).openInventory(tradeInv.getInventory());
		PlayerManager.getExactPlayer(b).openInventory(tradeInv.getInventory());
	}

	public void doAccept(String name){
		if(name.equals(a)){
			tradeInv.PlayerAConfirm();
			bA = true;
		}
		if(name.equals(b)){
			tradeInv.PlayerBConfirm();
			bB = true;
		}
		checkTrade();
	}

	public void doDeny(String name){
		if(name.equals(a)){
			tradeInv.PlayerAWaiting();
			bA = false;
		}
		if(name.equals(b)){
			tradeInv.PlayerBWaiting();
			bA = false;
		}
	}

	public void setBothDeny(){
		tradeInv.PlayerAWaiting();
		bA = false;
		tradeInv.PlayerBWaiting();
		bB = false;
	}

	public void checkTrade(){
		if(bA && bB){
			tradeInv.givePlayerSlot(PlayerManager.getExactPlayer(a), Slot.SLOTB);
			tradeInv.givePlayerSlot(PlayerManager.getExactPlayer(b), Slot.SLOTA);
			tradeInv.clearInv();
			trading = false;
			PlayerManager.getExactPlayer(a).closeInventory();
			PlayerManager.getExactPlayer(b).closeInventory();
			PlayerManager.sendStubMessage(a, " " + RpgTrades.complete);
			PlayerManager.sendStubMessage(b, " " + RpgTrades.complete);
			TradeManager.delTrade(this);
		}
	}

	@SuppressWarnings("deprecation")
	public void doClose(String name){
		Player player = Bukkit.getPlayer(a);
		Player player1 = Bukkit.getPlayer(b);
		if(name.equals(a)){
			PlayerManager.sendStubMessage(b, a + " " + RpgTrades.isDenied);
			PlayerManager.sendStubMessage(a, " " + RpgTrades.isDenied);
			trading = false;
			PlayerManager.getExactPlayer(b).closeInventory();
			tradeInv.givePlayerSlot(PlayerManager.getExactPlayer(a), Slot.SLOTA);
			tradeInv.givePlayerSlot(PlayerManager.getExactPlayer(b), Slot.SLOTB);
			TradeManager.delTrade(this);
			final Player p1 = player;
			final Player p2 = player1;
			new BukkitRunnable(){
				
				@Override
				public void run(){
					p1.updateInventory();
					p2.updateInventory();
				}
			}.runTaskLater(RpgTrades.plugin1, 4);
			
		}else{
			PlayerManager.sendStubMessage(a, b + " " + RpgTrades.isDenied);
			PlayerManager.sendStubMessage(b, " " + RpgTrades.isDenied);
			trading = false;
			PlayerManager.getExactPlayer(a).closeInventory();
			tradeInv.givePlayerSlot(PlayerManager.getExactPlayer(a), Slot.SLOTA);
			tradeInv.givePlayerSlot(PlayerManager.getExactPlayer(b), Slot.SLOTB);
			TradeManager.delTrade(this);
			final Player p1 = player;
			final Player p2 = player1;
			new BukkitRunnable(){
				
				@Override
				public void run(){
					p1.updateInventory();
					p2.updateInventory();
				}
			}.runTaskLater(RpgTrades.plugin1, 4);
		}
		return;

	}

	public void doShowTrade(String name){
		PlayerManager.getExactPlayer(name).openInventory(tradeInv.getInventory());
	}

	public boolean containsPlayer(String name){
		if(a.equals(name) || b.equals(name))
			return true;
		return false;
	}

	public TradeHolder getHolder(){
		return tradeInv;
	}

	public Slot getPlayerSlot(String name){
		if(a.equals(name))
			return Slot.SLOTA;
		if(b.equals(name))
			return Slot.SLOTB;
		return null;
	}

}
