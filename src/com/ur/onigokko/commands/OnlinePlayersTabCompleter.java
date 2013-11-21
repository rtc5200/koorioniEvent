package com.ur.onigokko.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class OnlinePlayersTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String label, String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		Player[] players = Bukkit.getOnlinePlayers();
		for(Player p : players)
		{
			list.add(p.getName());
		}
		return list;
	}

}
