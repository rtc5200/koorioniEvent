package com.ur.onigokko.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ur.onigokko.Main;

public class ProjectileEvent implements Listener {
	private Main main;
	public ProjectileEvent(Main main)
	{
		this.main = main;
	}
	@EventHandler
	public void onHit(ProjectileHitEvent e)
	{
		Projectile pj = e.getEntity();
		Entity shooter = pj.getShooter();
		EntityType pjt = pj.getType();
		Location loc = pj.getLocation();
		if(pjt.equals(EntityType.SNOWBALL))
		{
			SnowballAct(loc);
		}else if(pjt.equals(EntityType.EGG))
		{
			EggAct(loc,shooter);
		}
	}
	public void SnowballAct(Location loc)
	{
		Player[] players = Bukkit.getOnlinePlayers();
		Double x = loc.getX();Double y = loc.getY();Double z = loc.getZ();
		for(Player p : players)
		{
			Double maxx = p.getLocation().getX() + 5;Double minx = p.getLocation().getX() -5;
			Double maxy = p.getLocation().getY() + 5;Double miny = p.getLocation().getY() -5;
			Double maxz = p.getLocation().getZ() + 5;Double minz = p.getLocation().getZ() -5;
			if(main.getSbManager().getRedTeam().equals(main.getSbManager().getPlayerTeam(p)))
			{
				if(minx < x && maxx > x && miny < y&& maxy > y && minz < z && maxz > z)
				{
					p.removePotionEffect(PotionEffectType.SPEED);
					p.removePotionEffect(PotionEffectType.INVISIBILITY);
					PotionEffect pe = new PotionEffect(PotionEffectType.BLINDNESS, 200, 100);
					p.addPotionEffect(pe);
				}
				
			}
		}
		
	}
	public void EggAct(Location loc,Entity shoot)
	{
		Player[] players = Bukkit.getOnlinePlayers();
		Player shooter =(Player)shoot;
		World world = loc.getWorld();
		world.playSound(loc,Sound.EXPLODE,80,1);
		Double x = loc.getX();Double y = loc.getY();Double z = loc.getZ();
		for(Player p : players)
		{
			if(main.getSbManager().getRedTeam().equals(main.getSbManager().getPlayerTeam(p)))
			{
				if(main.getSbManager().getRedTeam().equals(main.getSbManager().getPlayerTeam(shooter)))
				{
					Location pl = p.getLocation();
					Double minx = pl.getX() - 2;Double maxx = pl.getX() + 2;
					Double miny = pl.getY() - 2;Double maxy = pl.getY() + 2;
					Double minz = pl.getZ() - 2;Double maxz = pl.getZ() + 2;
					if(minx < x && maxx > x && miny < y && maxy > y && minz < z && maxz > z  )
					{
						String cr = ChatColor.RED.toString();
						String cg = ChatColor.GREEN.toString();
						String cm = cr + shooter.getName() + cg + "が" + cr + p.getName() + cg + "を助けた!";						
						main.getServer().broadcastMessage(cm);
						main.getSbManager().addPlayerRedTeam(p);
						int alsc = main.getSbManager().getScore(shooter) + 1;
						main.getSbManager().setScore(shooter, alsc);
						String message = "[スコア]1ポイント獲得しました。";
						shooter.sendMessage(ChatColor.GREEN.toString() + message);
						shooter.sendMessage("[スコア]現在のあなたのスコア;" + main.getSbManager().getScore(shooter));
						main.getSbManager().addPlayerRedTeam(p);
					}
					
				}
			}
		}
	}

}
