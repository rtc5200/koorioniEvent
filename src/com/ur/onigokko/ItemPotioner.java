package com.ur.onigokko;


import java.util.HashMap;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemPotioner {
	HashMap<Integer,PotionEffect> sp = new HashMap<Integer,PotionEffect>();
	HashMap<Integer,PotionEffect> ip = new HashMap<Integer,PotionEffect>();
	public ItemPotioner()
	{
		for(int i = 1;i<=5;i++)
		{
			PotionEffect pe = new PotionEffect(PotionEffectType.SPEED,100,i*5);
			sp.put(i, pe);
		}
		for(int i = 1;i<=5;i++)
		{
			int time1 = i * 20 * 5;
			int time2 = time1 + 100;
			PotionEffect pe = new PotionEffect(PotionEffectType.INVISIBILITY,time2,1);
			ip.put(i, pe);
		}
		
	}
	//PotionEffect pe1 = new PotionEffect(PotionEffectType.SPEED, 100, 10);
	public PotionEffect getSpeedPotionEffect(int level)
	{
		if(sp.containsKey(level))
		{
			return sp.get(level);
		}else{
			return null;
		}
	}
	public PotionEffect getInvisiblePotionEffect(int level)
	{
		if(ip.containsKey(level))
		{
			return ip.get(level);
		}else{
			return null;
		}
	}

}
