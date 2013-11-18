package com.ur.onigokko;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtility {
	public static ItemStack getSPEEDItem(int amount)
	{
		ItemStack spar = new ItemStack(Variables.SPEED_ITEM);
		spar.setAmount(amount);
		ItemMeta meta = spar.getItemMeta();
		meta.setDisplayName(Variables.SPEED_ITEM_NAME);
		spar.setItemMeta(meta);
		return spar;
	}
	public static ItemStack getINVISIBLEItem(int amount)
	{
		ItemStack is = new ItemStack(Variables.INVISIBLE_ITEM);
		is.setAmount(amount);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(Variables.INVISIBLE_ITEM_NAME);
		is.setItemMeta(meta);
		return is;
	}
	public static ItemStack getEGGItem(int amount)
	{
		ItemStack is = new ItemStack(Variables.RESTORE_EGG);
		is.setAmount(amount);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(Variables.RESTORE_EGG_NAME);
		is.setItemMeta(meta);
		return is;
	}
	public static ItemStack createFireWork(Type type,Color color,int amount,String name)
	{
		ItemStack fwi = new ItemStack(Material.FIREWORK);
		FireworkMeta fwm = (FireworkMeta) fwi.getItemMeta();
		
		Builder fwb = FireworkEffect.builder();
		fwb.flicker(true);
		fwb.trail(true);
		fwb.with(type);
		fwb.withColor(color);
		fwb.withFlicker();
		fwb.withTrail();
		FireworkEffect fwe = fwb.build();
		fwm.addEffect(fwe);
		fwm.setPower(1);
		fwm.setDisplayName(name);
		
		fwi.setItemMeta(fwm);
		fwi.setAmount(amount);
		return fwi;
	}
	public static ItemStack getSNEAKItem(int amount)
	{
		ItemStack is = new ItemStack(Variables.CHANGE_SNEAK_ITEM);
		is.setAmount(amount);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(Variables.CHANGE_SNEAK_ITEM_NAME);
		is.setItemMeta(meta);
		return is;
	}
	public static ItemStack makeFireworkRandom(String name,int r, int g, int b)
	{
		ItemStack fwi = new ItemStack(Material.FIREWORK);
		FireworkMeta fwm = (FireworkMeta) fwi.getItemMeta();
		Color color = Color.fromBGR(b,g,r);
		Builder fwb = FireworkEffect.builder();
		fwb.flicker(true);
		fwb.trail(true);
		fwb.with(Type.CREEPER);
		fwb.withColor(color);
		fwb.withFlicker();
		fwb.withTrail();
		FireworkEffect fwe = fwb.build();
		fwm.addEffect(fwe);
		fwm.setPower(1);
		fwm.setDisplayName(name);
		fwi.setItemMeta(fwm);
		fwi.setAmount(1);
		return fwi;
	}
	public static ItemStack getMONEYItem(int amount)
	{
		ItemStack is = new ItemStack(Variables.MONEY_ITEM);
		is.setAmount(amount);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(Variables.MONEY_ITEM_NAME);
		is.setItemMeta(meta);
		return is;
	}
	public static ItemStack getSNOWBALLItem(int amount)
	{
		ItemStack is = new ItemStack(Material.SNOW_BALL);
		is.setAmount(amount);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(Variables.SNOWBALL_NAME);
		is.setItemMeta(meta);
		return is;
	}

}
