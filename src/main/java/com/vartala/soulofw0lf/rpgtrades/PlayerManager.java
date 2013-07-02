package com.vartala.soulofw0lf.rpgtrades;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;




public class PlayerManager {
	
	private static String stub = ChatColor.GOLD + RpgTrades.stubby + ChatColor.RESET;

	public static boolean isPlayerInTrade(String name){
		for(Trade trade : TradeManager.getTradeList())
			if(trade.containsPlayer(name))
				return true;
		return false;
	}
	
	public static boolean isPlayerInvited(String name){
		for(Invite invite : InviteManager.getInviteList())
			if(invite.containsPlayer(name))
				return true;
		return false;
	}
	
	public static void sendStubMessage(String p, String m){
		sendMessage(p, stub + m);
	}
	
	public static void sendMessage(String name, String m){
		for(Player player : Bukkit.getOnlinePlayers())
			if(player.getName().equals(name)){
				player.sendMessage(m);
				return;
			}
	}
	
	public static Player getPlayer(String name){
		for(Player player : Bukkit.getOnlinePlayers())
			if(player.getName().contains(name))
				return player;
		return null;
	}
	
	public static Player getExactPlayer(String name){
		for(Player player : Bukkit.getOnlinePlayers())
			if(player.getName().equals(name))
				return player;
		return null;
	}
	
}
