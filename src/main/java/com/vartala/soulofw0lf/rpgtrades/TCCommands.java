package com.vartala.soulofw0lf.rpgtrades;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TCCommands implements CommandExecutor {
	RpgTrades Rpgt;


	public TCCommands(RpgTrades rpgt){
		this.Rpgt = rpgt;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player player = (Player)sender;
		if(args.length == 0){
			PlayerManager.sendStubMessage(player.getName(), " " + RpgTrades.errorMsg);
			return true;
		}
		if (args[0].equalsIgnoreCase("Show")){

			player.sendMessage(RpgTrades.onlyLocal + "\n" + RpgTrades.distance + "\n" + RpgTrades.time);
			return true;
		}
		if(!(sender instanceof Player)){
			sender.sendMessage("You Cannot Use The Console For These Commands");
			return true;
		}

		if (!(player.hasPermission("trade.invite"))){
			player.sendMessage(" " + RpgTrades.noPerm);
			return true;
		}

		if(args[0].equalsIgnoreCase(RpgTrades.commandAccept)){
			RpgTrades.doAccept(player.getName());
			
			return true;
		}

		if(args[0].equalsIgnoreCase(RpgTrades.commandDeny)){
			RpgTrades.doDeny(player.getName());
			return true;
		}

		if(args[0].equalsIgnoreCase("inv")){
			this.Rpgt.doShowTrade(player.getName());
			return true;
		}
		if (args[0].equalsIgnoreCase(RpgTrades.commandInvite)){
			if (args.length != 2){
				PlayerManager.sendStubMessage(player.getName(), " " + RpgTrades.errorMsg);
				return true;
			}
			Player b = PlayerManager.getPlayer(args[1]);
			if(b == null || player.getName().equals(b.getName())){
				PlayerManager.sendStubMessage(player.getName(), " " + RpgTrades.missingPlayer);
				return true;
			}
			Location loc = b.getLocation();
			Location Loc = player.getLocation();
			if (RpgTrades.onlyLocal == true){
				if (loc.getWorld() != Loc.getWorld()){
					player.sendMessage(" " + RpgTrades.sameWorld);
					return true;
				}
			}
			this.Rpgt.doInvite(player.getName(), b.getName());
			return true;
		}
		if (args[0].equalsIgnoreCase("reload")){
			if (!(player.isOp())){
				player.sendMessage("Only Ops can reload this plugin!");
			}
			this.Rpgt.reloadConfig();
			this.Rpgt.loadConfig();
			player.sendMessage("Trade config has been reloaded!");
		}
		return false;
	}

}
