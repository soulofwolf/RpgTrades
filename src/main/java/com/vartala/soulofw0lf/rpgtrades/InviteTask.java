package com.vartala.soulofw0lf.rpgtrades;

import org.bukkit.scheduler.BukkitRunnable;



public class InviteTask extends BukkitRunnable {
	
	private static Invite invite = null;
	
	public InviteTask(Invite inv) {
		invite = inv;
	}

	@Override
	public void run() {
		invite.doDeny();
	}

}
