package com.ur.onigokko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utils {
	public static Map<Player, Boolean> getAllPlayerMap()
	{
		Player[] players = Bukkit.getOnlinePlayers();
		HashMap<Player,Boolean> pmap = new HashMap<Player,Boolean>();
		for (Player p : players)
		{
			pmap.put(p, true);
		}
		return pmap;
	}
	public static ArrayList<Player> getAllPlayers() {
        
        Player[] players = Bukkit.getOnlinePlayers();
        ArrayList<Player> rst = new ArrayList<Player>();
        for ( Player p : players ) 
        {
            rst.add(p);
        }
        return rst;
    }

}
