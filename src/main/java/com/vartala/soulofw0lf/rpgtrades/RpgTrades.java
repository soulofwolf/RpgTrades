package com.vartala.soulofw0lf.rpgtrades;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
public class RpgTrades extends JavaPlugin {
	public static RpgTrades plugin1;
	public static Integer time = 0;
	public static Integer distance = 0;
	public static Boolean onlyLocal = false;
	public static String notInTrade = null;
	public static String hasInvited = null;
	public static String isInvited = null;
	public static String Accepted = null;
	public static String isDenied = null;
	public static String tooFarAway = null;
	public static String errorMsg = null;
	public static String noPerm = null;
	public static String missingPlayer = null;
	public static String sameWorld = null;
	public static String complete = null;
	public static String blockAccept = null;
	public static String blockDeny = null;
	public static String blockWaiting = null;
	public static String blockConfirm = null;
	public static String commandInvite = null;
	public static String commandAccept = null;
	public static String commandDeny = null;
	public static String stubby = null;
	
	@Override
	public void onEnable() {
		plugin1 = this;
		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
		getCommand("trade").setExecutor(new TCCommands(this));
		saveDefaultConfig();
		saveConfig();
		loadConfig();
	
	}
	public void loadConfig(){
		notInTrade = getConfig().getString("Terms.Not In Trade").replaceAll("@", "§");
		time = getConfig().getInt("inviteTimeOut");
		hasInvited = getConfig().getString("Terms.Has Invited").replaceAll("@", "§");
		isInvited = getConfig().getString("Terms.Is Invited").replaceAll("@", "§");
		Accepted = getConfig().getString("Terms.Accepted").replaceAll("@", "§");
		isDenied = getConfig().getString("Terms.Denied").replaceAll("@", "§");
		tooFarAway = getConfig().getString("Terms.Too Far Away").replaceAll("@", "§");
		errorMsg = getConfig().getString("Terms.Error").replaceAll("@", "§");
		distance = getConfig().getInt("TradeDistance");
		onlyLocal = getConfig().getBoolean("onlyLocalTrades");
		noPerm = getConfig().getString("Terms.NoPerm").replaceAll("@", "§");
		missingPlayer = getConfig().getString("Terms.Missing Player").replaceAll("@", "§");
		sameWorld = getConfig().getString("Terms.Same World").replaceAll("@", "§");
		complete = getConfig().getString("Terms.Complete").replaceAll("@", "§");
		blockAccept = getConfig().getString("Terms.Block Accept").replaceAll("@", "§");
		blockDeny = getConfig().getString("Terms.Block Deny").replaceAll("@", "§");
		blockWaiting = getConfig().getString("Terms.Block Waiting").replaceAll("@", "§");
		blockConfirm = getConfig().getString("Terms.Block Confirmed").replaceAll("@", "§");
		commandInvite = getConfig().getString("Terms.Command Invite");
		commandAccept = getConfig().getString("Terms.Command Accept");
		commandDeny = getConfig().getString("Terms.Command Deny");
		stubby = getConfig().getString("Terms.StubMessage");
	}
	 
	@Override
	public void onDisable() {
	}
	public void doInvite(String a, String b){
		if(PlayerManager.isPlayerInTrade(a) || PlayerManager.isPlayerInvited(a))
			return;
		if(PlayerManager.isPlayerInTrade(b) || PlayerManager.isPlayerInvited(b))
			return;
		Player pA = PlayerManager.getExactPlayer(a);
		Player pB = PlayerManager.getExactPlayer(b);
		if((onlyLocal == true) && (pA.getLocation().distance(pB.getLocation()) > distance)){
			PlayerManager.sendMessage(a, b + tooFarAway);
			return;
		}
		InviteManager.addInvite(a, b);
	}
	
	/*
	 * 
	 */
	public static void doAccept(String name){
		Invite invite = null;
		if(!PlayerManager.isPlayerInTrade(name) && !PlayerManager.isPlayerInvited(name))
			return;
		if((invite = InviteManager.getInvite(name)) != null)
			invite.doAccept();
		Trade.trading = true;
	}
	
	/*
	 * 
	 */
	public static void doDeny(String name){
		Invite invite = null;
		if((invite = InviteManager.getInvite(name)) != null)
			invite.doDeny();
	}
	
	public static void doClose(String name){
		Trade trade = TradeManager.getTrade(name);
		if(trade != null)
			trade.doClose(name);
	}
	
	 /*
	 * 
	 */
	
	
	public void doShowTrade(String name){
		if(!PlayerManager.isPlayerInTrade(name)){
			PlayerManager.sendStubMessage(name, notInTrade);
			return;
		}
		TradeManager.getTrade(name).doShowTrade(name);
	}

}
