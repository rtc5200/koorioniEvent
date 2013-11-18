package com.ur.onigokko;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InventoryUtil {
	public static void BlueTeamEquip(Player player,onigokko ongk)
	{
		MaterialGoToEnderChest(player, Variables.SPEED_ITEM);
		MaterialGoToEnderChest(player, Variables.INVISIBLE_ITEM);
		PlayerInventory pi = player.getInventory();
		ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
		ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemStack boot = new ItemStack(Material.DIAMOND_BOOTS);
		ItemStack leg = new ItemStack(Material.DIAMOND_LEGGINGS);
		pi.setHelmet(helmet);
		pi.setChestplate(chestplate);
		pi.setLeggings(leg);
		pi.setBoots(boot);
		pi.addItem(ItemUtility.getSNOWBALLItem(20));
		PotionEffect pe = new PotionEffect(PotionEffectType.SPEED, 24000000, ongk.splevel);
		player.addPotionEffect(pe);
	}
	public static void BlueTeamUnEquip(Player player,onigokko ongk)
	{
		PlayerInventory pi = player.getInventory();
		pi.setHelmet(new ItemStack(Material.AIR));
		pi.setChestplate(new ItemStack(Material.AIR));
		pi.setLeggings(new ItemStack(Material.AIR));
		pi.setBoots(new ItemStack(Material.AIR));
		removePlayerMaterial(player,Material.SNOW_BALL);
		removePlayerMaterial(player,Variables.CHANGE_SNEAK_ITEM);
		player.removePotionEffect(PotionEffectType.SPEED);
		
	}
	public static void RedTeamEquip(Player player)
	{
		PlayerInventory pi = player.getInventory();
		pi.addItem(ItemUtility.getSPEEDItem(10));
		pi.addItem(ItemUtility.getINVISIBLEItem(10));
		pi.addItem(ItemUtility.createFireWork(Type.BURST, Color.RED, 10, Variables.HELP_FIREWORK_NAME));
		pi.addItem(ItemUtility.getSNEAKItem(1));
	}
	public static void RedTeamUnEquip(Player player)
	{
		ItemStack speed = new ItemStack(Variables.SPEED_ITEM);
		ItemMeta meta = speed.getItemMeta();
		meta.setDisplayName(Variables.SPEED_ITEM_NAME);
		speed.setItemMeta(meta);
		speed.setAmount(64);
		ItemStack invi = new ItemStack(Variables.INVISIBLE_ITEM);
		ItemMeta im = invi.getItemMeta();
		im.setDisplayName(Variables.INVISIBLE_ITEM_NAME);
		invi.setItemMeta(im);
		invi.setAmount(64);
		ItemStack fw = new ItemStack(ItemUtility.createFireWork(Type.BURST, Color.RED, 10, Variables.HELP_FIREWORK_NAME));
		PlayerRemoveNamedItem(player,fw);
		PlayerRemoveNamedItem(player,speed);
		PlayerRemoveNamedItem(player,invi);
	}
	public static void PlayeraddItem(Player player,ItemStack item)
	{
		PlayerInventory pi = player.getInventory();
		pi.addItem(item);
	}
	public static void PlayerremoveItem(Player player,ItemStack item)
	{
		PlayerInventory pi = player.getInventory();
		pi.removeItem(item);
	}
	public static void PlayerRemoveNamedItem(Player player,ItemStack item)
	{
		PlayerInventory pi = player.getInventory();
		ItemStack[] contents = pi.getContents();
		
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		for(ItemStack i : contents)
		{
			if(i != null)
			{
				if(i.getItemMeta().hasDisplayName())
				{
					if(i.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName()) && i.getItemMeta().getDisplayName().length() == item.getItemMeta().getDisplayName().length())
					{
						pi.remove(i);
						
					}
				}
			}
		}
	}
	public static ItemStack getPlayerItem(Player player,Material material)
	{
		PlayerInventory pi = player.getInventory();
		int amount = 0;
		for(ItemStack is : pi.getContents())
		{
			if(is != null)
			{
				if(is.getType() == material)
				{
					amount = amount + is.getAmount();
				}
			}
			
		}
		ItemStack result = new ItemStack(material);
		result.setAmount(amount);
		return result;
	}
	public static void removePlayerMaterial(Player player ,Material material)
	{
		player.getInventory().remove(material);
	}
	public static void UseItem(Player player,ItemStack item)
	{
		PlayerInventory inv = player.getInventory();
		ItemStack ia = new ItemStack(item.getType());
		ia.setItemMeta(item.getItemMeta());
		ia.setAmount(1);
		PlayerremoveItem(player,ia);
	}
	public static void MaterialGoToEnderChest(Player player,Material material)
	{
		Inventory ec = player.getEnderChest();
		PlayerInventory iv = player.getInventory();
		for(ItemStack item : iv.getContents())
		{
			if(item != null)
			{
				if(item.getType().equals(material))
				{
					iv.remove(item);
					ec.addItem(item);
				}
			}
			
		}
	}
}
