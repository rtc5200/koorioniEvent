package com.ur.onigokko.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.ur.onigokko.Main;
import com.ur.onigokko.Variables;
import com.ur.onigokko.onigokko;

public class BlueTeamAddCommand implements CommandExecutor {
	private Main plugin;
	private onigokko ongk;
	private String[] args;
	private CommandSender sender;
	public BlueTeamAddCommand(Main plugin)
	{
		this.plugin = plugin;
		ongk = plugin.getOnigokko();
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		this.args = args;
		this.sender = sender;
		
		if(sender instanceof BlockCommandSender || sender instanceof ConsoleCommandSender)
		{
			if(args != null)
			{
				if(args.length >= 1)
				{
					perform();
					return true;
				}
			}else{
				sender.sendMessage(Variables.MESSAGE_ValidArgument);
				return false;
			}
		} else if(sender instanceof Player)
		{
			Player player = (Player)sender;
			if(player.isOp() || player.hasPermission("onigokko.gm"))
			{
				if(args != null)
				{
					if(args.length >= 1)
					{
						perform();
						return true;
					}
				}else{
					sender.sendMessage(Variables.MESSAGE_ValidArgument);
					return false;
				}
			}
		}
		return false;
	}
	public void perform()
	{
		Player player = Bukkit.getPlayerExact(args[0]);
		if(player != null)
		{
		if(player.isOnline())
		{
			if(plugin.getStartState())
			{
				ongk.PlayerPickUpBlueTeam(player);
				return;
			}else{
				sender.sendMessage(Variables.MESSAGE_NotStartedGame);
				return;
			}
		}else{
			sender.sendMessage(Variables.MESSAGE_PlayerIsNotOnline);
			return;
		}
		}
	}
}
