package com.ur.onigokko;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ExpCountDown {
	public void CountDownPlayerExp(int time){
		Player[] players = Bukkit.getOnlinePlayers();
		for(Player p : players)
		{
			p.setLevel(time);
			p.playSound(p.getLocation(), Sound.NOTE_STICKS, 100, 1);
			if(time == 0)
			{
				p.sendMessage(ChatColor.GREEN.toString() + "ゲーム開始です。");
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 100, 1);
			}
		}
	}
}
