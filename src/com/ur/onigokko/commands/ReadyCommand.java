package com.ur.onigokko.commands;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.ur.onigokko.Main;
import com.ur.onigokko.SbManager;
import com.ur.onigokko.Variables;
import com.ur.onigokko.onigokko;

public class ReadyCommand implements CommandExecutor {
	private Main plugin;
	private onigokko ongk;
	private SbManager sm;
	public ReadyCommand(Main plugin)
	{
		this.plugin = plugin;
		ongk = plugin.getOnigokko();
		sm = plugin.getSbManager();
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if( sender instanceof BlockCommandSender || sender instanceof ConsoleCommandSender)
		{
			perform();
			return true;
			
		}else if (sender instanceof Player)
		{
			Player player = (Player)sender;
			if(player.isOp() || player.hasPermission("onigokko.gm"))
			{
				perform();
				return true;
			} else {
				player.sendMessage(Variables.MESSAGE_NotHavePermission);
			}
		} 
		return false;
	}
	public void perform(){
		ongk.createTeam();
		ongk.ready();
		plugin.setStartState(true);
		sm.refresh();
	}

}
