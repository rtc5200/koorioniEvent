package com.ur.onigokko;

import org.bukkit.scheduler.BukkitRunnable;

public class Delay3 extends BukkitRunnable{
	private final Main main;
	public Delay3(Main main)
	{
		this.main = main;
	}
	public void run()
	{
		main.SetSneaking();
	}

}
