package com.ur.onigokko.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.ur.onigokko.Main;

public class ReadyCommand implements CommandExecutor {
	private Main plugin;
	public ReadyCommand(Main plugin)
	{
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		
		return false;
	}

}
