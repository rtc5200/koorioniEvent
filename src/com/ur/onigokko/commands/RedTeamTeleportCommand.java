package com.ur.onigokko.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ur.onigokko.Main;
import com.ur.onigokko.Variables;
import com.ur.onigokko.onigokko;

public class RedTeamTeleportCommand implements CommandExecutor {
	private Main plugin;
	private onigokko ongk;
	CommandSender sender;
	String[] args;
	public RedTeamTeleportCommand(Main plugin)
	{
		this.plugin = plugin;
		ongk = plugin.getOnigokko();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		this.sender = sender;
		this.args = args;
		if(plugin.getStartState())
		{
			if(args != null)
			{
				if(args.length < 3)
				{
					sender.sendMessage(Variables.MESSAGE_ValidArgument);
					return false;
				}
				if(sender instanceof Player)
				{
					Player player = (Player)sender;
					if(player.isOp() || player.hasPermission("onigokko.gm"))
					{
						perform();
						return true;
					}else{
						sender.sendMessage(Variables.MESSAGE_NotHavePermission);
						return true;
					}
				}else{
					perform();
					return true;
				}
			}else{
				sender.sendMessage(Variables.MESSAGE_ValidArgument);
				return false;
			}
		} else{
			sender.sendMessage(Variables.MESSAGE_NotStartedGame);
			return true;
		}
	}
	public void perform()
	{
		Double x = Double.parseDouble(args[0]);
		Double y = Double.parseDouble(args[1]);
		Double z = Double.parseDouble(args[2]);
		ongk.redTeamTP(x, y, z);
		sender.sendMessage("逃走者チームをテレポートしました");
	}

}
