package com.ur.onigokko;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
@SuppressWarnings("unused")
public class Delay extends BukkitRunnable {
	private final JavaPlugin plugin;
	int time = 0;
	public Delay(JavaPlugin plugin, int time)
	{
		this.plugin = plugin;
		this.time = time;
	}
	public void run()
	{
		ExpCountDown ecd = new ExpCountDown();
		 ecd.CountDownPlayerExp(time);
	}
	

}
