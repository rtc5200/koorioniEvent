package com.ur.onigokko.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ur.onigokko.InventoryUtil;
import com.ur.onigokko.ItemPotioner;
import com.ur.onigokko.Main;
import com.ur.onigokko.SbManager;
import com.ur.onigokko.Variables;
import com.ur.onigokko.onigokko;

public class ItemUseEvent implements Listener{
	private Main main;
	private onigokko ongk;
	private ItemPotioner ipn;
	private SbManager sm;
	public ItemUseEvent(Main main)
	{
		this.main = main;
		this.ongk = main.getOnigokko();
		this.ipn = ongk.ipn;
		this.sm = main.getSbManager();
	}
	@EventHandler
	public void onItemUse(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		ItemStack hi = e.getPlayer().getItemInHand();
		Material pml = player.getItemInHand().getType();
		if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
		{
			return;
		}
		if(pml == Variables.SPEED_ITEM)
		{
			//PotionEffect pe = new PotionEffect(PotionEffectType.SPEED, 100, 10);
			//PotionEffect(EffectType,Time,Level)
			ItemMeta meta = hi.getItemMeta();
			if(meta.getDisplayName().contains("[LV.2]"))
			{
				player.addPotionEffect(ipn.getSpeedPotionEffect(2));
			}else if(meta.getDisplayName().contains("[LV.3]")){
				player.addPotionEffect(ipn.getSpeedPotionEffect(3));
			}else if(meta.getDisplayName().contains("[LV.4]"))
			{
				player.addPotionEffect(ipn.getSpeedPotionEffect(4));
			}else{
				player.addPotionEffect(ipn.getSpeedPotionEffect(1));
			}
			player.playSound(player.getLocation(), Sound.ARROW_HIT, 100, 1);
			InventoryUtil.UseItem(player, player.getItemInHand());
		}else if(pml == Variables.INVISIBLE_ITEM)
		{
			
			//PotionEffect pe = new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1);
			ItemMeta meta = hi.getItemMeta();
			if(meta.getDisplayName().contains("[LV.2]"))
			{
				player.addPotionEffect(ipn.getInvisiblePotionEffect(2));
			}else if(meta.getDisplayName().contains("[LV.3]")){
				player.addPotionEffect(ipn.getInvisiblePotionEffect(3));
			}else if(meta.getDisplayName().contains("[LV.4]"))
			{
				player.addPotionEffect(ipn.getInvisiblePotionEffect(4));
			}else{
				player.addPotionEffect(ipn.getInvisiblePotionEffect(1));
			}
			player.playSound(player.getLocation(), Sound.DIG_SAND, 100, 1);
			InventoryUtil.UseItem(player, player.getItemInHand());
		}else if(pml == Material.GHAST_TEAR)
		{
			if(sm.getPlayerTeam(player) != sm.getBlackTeam())
			{
				return;
			}
			String cr = ChatColor.RED.toString();
			String cg = ChatColor.GREEN.toString();
			String cm = cr + player.getName() + cg + "が復活!";						
			main.getServer().broadcastMessage(cm);
			sm.addPlayerRedTeam(player);
			//score処理
			String message = "復活アイテムを使ったためスコアは加算されません。";
			player.sendMessage(ChatColor.GREEN.toString() + message);
			player.sendMessage("[スコア]現在のあなたのスコア;" + sm.getScore(player));
			sm.addPlayerRedTeam(player);
			InventoryUtil.UseItem(player, player.getItemInHand());
			}else if(pml == Variables.CHANGE_SNEAK_ITEM)
			{
				player.chat("/sneak");
			}
	}

}
