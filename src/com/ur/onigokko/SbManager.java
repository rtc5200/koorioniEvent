package com.ur.onigokko;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class SbManager {
	Main main;
	Scoreboard sb;
	Objective ob;
	Team red;
	Team blue;
	Team black;
	public SbManager(Main main)
	{
		this.main = main;
		sb = main.getServer().getScoreboardManager().getMainScoreboard();
		if(sb.getObjective("KoorioniTeam") != null)
		{
			ob = sb.getObjective("KoorioniTeam");
		}else{
			ob = sb.registerNewObjective("KoorioniTeam", "dummy");
		}
		if(sb.getObjective("KoorioniScore") != null)
		{
			ob = sb.getObjective("KoorioniScore");
		}else{
			ob = sb.registerNewObjective("KoorioniScore","dummy");
		}
		if(sb.getTeam("red") != null)
		{
			red = sb.getTeam("red");
		}else{
			red = sb.registerNewTeam("red");
		}
		if(sb.getTeam("blue") != null)
		{
			blue = sb.getTeam("blue");
		}else{
			blue = sb.registerNewTeam("blue");
		}
		if(sb.getTeam("black") != null)
		{
			black = sb.getTeam("black");
		}else{
			black = sb.registerNewTeam("black");
		}
		red.setAllowFriendlyFire(false);
		blue.setAllowFriendlyFire(false);
		black.setAllowFriendlyFire(false);
		red.setCanSeeFriendlyInvisibles(false);
		blue.setCanSeeFriendlyInvisibles(false);
		black.setCanSeeFriendlyInvisibles(false);
		
		ob.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		ob.setDisplayName("凍り鬼ステータス");
		
		red.setPrefix(ChatColor.RED.toString());
		blue.setPrefix(ChatColor.BLUE.toString());
		red.setSuffix(ChatColor.RESET.toString());
		blue.setSuffix(ChatColor.RESET.toString());
		black.setPrefix(ChatColor.BLACK.toString());
		black.setSuffix(ChatColor.RESET.toString());
		
	}
	public void refresh()
	{
		red.setPrefix(ChatColor.RED.toString());
		blue.setPrefix(ChatColor.BLUE.toString());
		red.setSuffix(ChatColor.RESET.toString());
		blue.setSuffix(ChatColor.RESET.toString());
	}
		
	public Scoreboard getScoreboard()
	{
		return sb;
	}
	public Objective getObjective()
	{
		return ob;
	}
	public Team getRedTeam()
	{
		return red;
	}
	public Team getBlueTeam()
	{
		return blue;
	}
	public Team getBlackTeam()
	{
		return black;
	}
	public int getScore(Player player)
	{
		return ob.getScore(player).getScore();
	}
	
	public void setScore(Player player,int score)
	{
		ob.getScore(player).setScore(score); 
	}
	public void resetScore()
	{
		for(OfflinePlayer ofp : main.getServer().getOfflinePlayers())
		{
			sb.resetScores(ofp);
		}
		
	}
	public void addPlayerRedTeam(Player player)
	{
		red.addPlayer(player);
		SetPlayerColor(player,ChatColor.RED);
	}
	public void addPlayerBlueTeam(Player player)
	{
		blue.addPlayer(player);
		SetPlayerColor(player,ChatColor.BLUE);
	}
	public void addPlayerBlackTeam(Player player)
	{
		black.addPlayer(player);
		SetPlayerColor(player,ChatColor.BLACK);
	}
	public void removePlayer(Player player)
	{
		if(red.hasPlayer(player))
		{
			red.removePlayer(player);
			SetPlayerColor(player,ChatColor.RESET);
			InventoryUtil.RedTeamUnEquip(player);
		}else if(blue.hasPlayer(player))
		{
			blue.removePlayer(player);
			SetPlayerColor(player,ChatColor.RESET);
			InventoryUtil.BlueTeamEquip(player,main.ongk);
		}else if(black.hasPlayer(player))
		{
			black.removePlayer(player);
			SetPlayerColor(player,ChatColor.RESET);
			InventoryUtil.RedTeamUnEquip(player);
		}
	}
	public void resetcolor()
	{
		for(Player p : Bukkit.getOnlinePlayers())
		{
			SetPlayerColor(p,ChatColor.RESET);
		}
	}
	public Set<OfflinePlayer> getRedTeamMembers()
	{
		return red.getPlayers();
	}
	public Set<OfflinePlayer> getBlueTeamMembers()
	{
		return blue.getPlayers();
	}
	public void refreshScore()
	{
		Player[] players = Bukkit.getOnlinePlayers();
		for(Player p :players)
		{
			p.setScoreboard(sb);
		}
	}
	public Team getPlayerTeam(Player player)
	{
		if(red.hasPlayer(player))
		{
			return red;
		}else if(blue.hasPlayer(player))
		{
			return blue;
		}else if(black.hasPlayer(player)){
			return black;
		}else{
			return null;
		}
	}
	public Set<OfflinePlayer> getRedTeamMember()
	{
		return red.getPlayers();
	}
	public Set<OfflinePlayer> getBlueTeamMember()
	{
		return blue.getPlayers();
	}
	public ArrayList<Player> getRedTeamMemberList()
	{
		ArrayList<Player> result = new ArrayList<Player>();
		Player[] player = Bukkit.getOnlinePlayers();
		for(Player p : player)
		{
			if(red.hasPlayer(p))
			{
				result.add(p);
			}
		}
		return result;
	}
	public ArrayList<Player> getBlueTeamMemberList()
	{
		ArrayList<Player> result = new ArrayList<Player>();
		Player[] player = Bukkit.getOnlinePlayers();
		for(Player p : player)
		{
			if(blue.hasPlayer(p))
			{
				result.add(p);
			}
		}
		return result;
	}
	public void SetPlayerColor(Player player,ChatColor cc)
	{
		if(cc == ChatColor.RESET)
		{
			player.setDisplayName(player.getName());
			return;
		}
		player.setDisplayName(cc.toString() + player.getName() + ChatColor.RESET.toString());
	}
	public void SendMessageToRedTeamPlayer(String sender,String message)
	{
		String ms = sender + message;
		for(Player p : getRedTeamMemberList())
		{
			if(p != null)
			{
				p.sendMessage(ms);
			}
		}
	}
	public void SendMessageToBlueTeamPlayer(String sender,String message)
	{
		String ms = sender + message;
		for(Player p : getBlueTeamMemberList())
		{
			if(p != null)
			{
				p.sendMessage(ms);
			}
		}
	}
	public void BlueTeamMemberRemove()
	{
		for(OfflinePlayer p : blue.getPlayers())
		{
			blue.removePlayer(p);
			InventoryUtil.BlueTeamUnEquip((Player)p,main.ongk);
			Player olp = main.getServer().getPlayerExact(p.getName());
			if(olp != null)
			{
				SetPlayerColor(olp, ChatColor.RESET);
				setScore(olp,0);
				if(main.ongk.PlayerFreezeStats.containsKey(olp))
				{
					main.ongk.PlayerFreezeStats.remove(olp);
				}
			}
		}
	}
	public void RedTeamMemberRemove()
	{
		for(OfflinePlayer p : red.getPlayers())
		{
			removePlayer(p.getPlayer());
		}
	}
	public void BlackTeamMemberRemove()
	{
		for(OfflinePlayer p : black.getPlayers())
		{
			removePlayer(p.getPlayer());
		}
	}
}
