package com.vartala.soulofw0lf.rpgtrades;

import org.bukkit.scheduler.BukkitTask;



public class Invite {
	RpgTrades Rpgt;
	public Invite(RpgTrades rpgt){
		this.Rpgt = rpgt;
	}

	private String a;
	private String b;
	private BukkitTask task = null;
	private Integer time = RpgTrades.time;
	
	
	public Invite(String a, String b) {
		this.a = a;
		this.b = b;
		PlayerManager.sendStubMessage(a, b + RpgTrades.hasInvited);
		PlayerManager.sendStubMessage(b, a + RpgTrades.isInvited);
		task = new InviteTask(this).runTaskLater(RpgTrades.plugin1, time * 20);
	}
	
	public void doAccept(){
		task.cancel();
		PlayerManager.sendStubMessage(a, b + RpgTrades.Accepted);
		PlayerManager.sendStubMessage(b, RpgTrades.Accepted);
		TradeManager.addTrade(a, b);
		InviteManager.remInvite(this);
	}
	
	public void doDeny(){
		task.cancel();
		PlayerManager.sendStubMessage(a, b + RpgTrades.isDenied);
		PlayerManager.sendStubMessage(b, RpgTrades.isDenied);
		InviteManager.remInvite(this);
	}
	
	public boolean containsPlayer(String name){
		if(a.equals(name) || b.equals(name))
			return true;
		return false;
	}

}
