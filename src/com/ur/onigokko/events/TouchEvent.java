package com.ur.onigokko.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.ur.onigokko.Main;
import com.ur.onigokko.SbManager;

public class TouchEvent implements Listener{
	private Main main;
	private SbManager manager;
	public TouchEvent(Main main)
	{
		this.main = main;
		this.manager = main.getSbManager();
	}
	@EventHandler
	public void onTouch(EntityDamageByEntityEvent e)
	{
		Entity dgr = e.getDamager();
		Entity rvr = e.getEntity();
		 if(dgr.getType().equals(EntityType.PLAYER))
		{
			 if(rvr.getType().equals(EntityType.PLAYER))
			 {
				 Player damager = (Player)dgr;
				 Player reciever = (Player)rvr;
				 if(manager.getPlayerTeam(damager) != null && manager.getPlayerTeam(reciever) != null)
				 {
					 if(manager.getPlayerTeam(damager).equals(manager.getBlueTeam()))
					 {
						 if(manager.getPlayerTeam(reciever).equals(manager.getRedTeam()))
						 {
							 onCaputured(damager,reciever);
							 e.setDamage(0);
						 }
					 }else if(manager.getPlayerTeam(damager).equals(manager.getRedTeam()))
					 {
						 if(manager.getPlayerTeam(reciever).equals(manager.getBlackTeam()))
						 {
							 onRescued(damager,reciever);
							 e.setDamage(0);
						 }
					 }
				 }
				 e.setCancelled(true);
			 }
			 
		}
	}
	public void onCaputured(Player damager,Player reciever)
	{
		String red = ChatColor.RED.toString();
		String gre = ChatColor.GREEN.toString();
		String blu = ChatColor.BLUE.toString();
		String cm = red  + reciever.getName() + gre + "が" + 
		blu + damager.getName() + gre + "に凍らされてしまった!";
		main.getServer().broadcastMessage(cm);
		manager.addPlayerBlackTeam(reciever);
		manager.setScore(damager, manager.getScore(damager)+1 );
		String message = "[スコア]1ポイント獲得しました。";
		damager.sendMessage(ChatColor.GREEN.toString() + message);
		damager.sendMessage("[スコア]現在のあなたのスコア;" + manager.getScore(damager));
	}
	public void onRescued(Player damager,Player reciever)
	{
		String red = ChatColor.RED.toString();
		String gre = ChatColor.GREEN.toString();
		String bla = ChatColor.BLACK.toString();
		String cm = red + damager.getName() + gre + "が" + red + reciever.getName() + gre + "を助けた!";	
		main.getServer().broadcastMessage(cm);
		manager.setScore(damager, manager.getScore(damager)+2 );
		String message = "[スコア]2ポイント獲得しました。";
		damager.sendMessage(ChatColor.GREEN.toString() + message);
		damager.sendMessage("[スコア]現在のあなたのスコア;" + manager.getScore(damager));
		manager.addPlayerRedTeam(reciever);
	}
	

}
