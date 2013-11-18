package com.ur.onigokko;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
//import org.kitteh.tag.TagAPI;


public class Main extends JavaPlugin implements Listener
{
	onigokko ongk;
	boolean alreadystart;
	Logger log = Logger.getLogger("minecraft");
	Location tploc;
	Scoreboard sb;
	int imd = 0;
	int imc = 0;
	ItemStack gi;
	ArrayList<Player> sneakp = new ArrayList<Player>();
	Team redTeam;
	Team blueTeam;
	Objective ob;
	SbManager sbm;
	public void onEnable()
	{
		ongk = new onigokko(this);
		ongk.createTeam();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(ongk, this);
		BukkitScheduler bs = getServer().getScheduler();
		bs.scheduleSyncRepeatingTask(this, new Delay3(this), 0, 10);
		sbm = ongk.sm;
		sb = sbm.getScoreboard();
		redTeam =sbm.getRedTeam();
		blueTeam = sbm.getBlueTeam();
		ob = sbm.getObjective();
	}
	public void onDisable()
	{
		
	}
	public boolean onCommand(CommandSender sender,Command cmd,String commandlabel,String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("debug"))
		{
			if(args[0] == null)
			{
				return false;
			}
			if(args[0].equalsIgnoreCase("additem"))
			{
				InventoryUtil.PlayeraddItem((Player)sender, new ItemStack(Material.DIAMOND));//一個追加される
				return true;
			}
			if(args[0].equalsIgnoreCase("removeitem"))
			{
				ItemStack is = new ItemStack(Material.DIAMOND);
				is.setAmount(2);
				InventoryUtil.PlayerremoveItem((Player)sender, is);//2個消える
				return true;
			}
			if(args[0].equalsIgnoreCase("removematerial"))
			{
				InventoryUtil.removePlayerMaterial((Player)sender, Material.DIAMOND);//全部消える
				return true;
			}
			if(args[0].equalsIgnoreCase("show"))
			{
				ItemStack is = InventoryUtil.getPlayerItem((Player)sender, Material.DIAMOND);
				sender.sendMessage("amount = " + is.getAmount());
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("sneak"))
		{
			if(!(sender instanceof Player))
			{
				return true;
			}else{
				Player p = (Player) sender;
				String SMessage1 = ChatColor.RESET.toString() + "オートスニーク" + ChatColor.GOLD.toString() + "オン";
				String SMessage2 = ChatColor.RESET.toString() + "オートスニーク" + ChatColor.GOLD.toString() + "オフ";
				if(sneakp.contains(p))
				{
					sneakp.remove(p);
					p.setSneaking(false);
					p.sendMessage(SMessage2);
				}else{
					sneakp.add(p);
					p.sendMessage(SMessage1);
				}
				return true;
			}
			
			
		}
		if (cmd.getName().equalsIgnoreCase("onigokko-ready"))
		{
			if (SenderCheck(sender) >= 1)
			{
				if(alreadystart == true)
				{
					if (SenderCheck(sender) == 1)
					{
						sender.sendMessage("すでに準備しています。");
					}
					return false;
				}
				ongk.createTeam();
				ongk.ready();
				alreadystart = true;
				sbm.refresh();
				return true;
					
			}
		}
		if (cmd.getName().equalsIgnoreCase("onigokko-exit"))
		{
			if (SenderCheck(sender) >= 1)
			{
				if (alreadystart == true)
				{
					ongk.exit();
					alreadystart = false;
					return true;
				}
				if (SenderCheck(sender) == 1)
				{
					sender.sendMessage("ゲームが開始されていません。");
					return true;
				}
				return false;
					
			}
		}
		if (cmd.getName().equalsIgnoreCase("onigokko-b"))
		{
			if (alreadystart == true)
			{
				if (SenderCheck(sender) >= 1)
				{
					Player name = Bukkit.getPlayer(args[0]);
					if (name.isOnline() == false)
					{
						if(SenderCheck(sender) == 1)
						{
						sender.sendMessage(name.toString() + "はオンラインではありません!");
						}
						return true;
					}
					ongk.PlayerPickUpBlueTeam(name);
					return true;
				}
				
			}
			
		}
		if (cmd.getName().equalsIgnoreCase("onigokko-r"))
		{
			if (alreadystart == true)
			{
				if (SenderCheck(sender) >= 1)
				{
					Player name = Bukkit.getPlayer(args[0]);
					if (name.isOnline() == false)
					{
						if(SenderCheck(sender) == 1)
						{
						sender.sendMessage(name.toString() + "はオンラインではありません!");
						}
						return true;
					}
					ongk.PlayerAddRedteam(name);
					return true;
				}
				
			}
			
		}
		if (cmd.getName().equalsIgnoreCase("onigokko-tp-r"))
		{
			if (SenderCheck(sender) >= 1)
			{
				if (alreadystart == false)
				{
					if(SenderCheck(sender) == 1)
					{
						sender.sendMessage("ゲームが始まっていません。");
						return true;
					}
				}
				if(args[0] == null || args[1] == null || args[2] == null)
				{
					if(SenderCheck(sender) == 1)
					{
						sender.sendMessage("値が不足または不正です。");
						return true;
					}
				}
					Double x = Double.parseDouble(args[0]);
					Double y = Double.parseDouble(args[1]);
					Double z = Double.parseDouble(args[2]);
				ongk.redTeamTP(x, y, z);
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("onigokko-tp-b"))
		{
			if (SenderCheck(sender) >= 1)
			{
				if (alreadystart == false)
				{
					if(SenderCheck(sender) == 1)
					{
						sender.sendMessage("ゲームが始まっていません。");
						return true;
					}
				}
				if(args[0] == null || args[1] == null || args[2] == null)
				{
					if(SenderCheck(sender) == 1)
					{
						sender.sendMessage("値が不足または不正です。");
						return true;
					}
				}
					Double x = Double.parseDouble(args[0]);
					Double y = Double.parseDouble(args[1]);
					Double z = Double.parseDouble(args[2]);
				ongk.BlueTeamMemberTP(x, y, z);
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("onigokko-remove"))
		{
			if(SenderCheck(sender) >= 1)
			{
				if(alreadystart == false)
				{
					if(sender instanceof Player)
					{
						sender.sendMessage("ゲームが始まっていません");
						return true;
					}
					if(sender instanceof Player)
					{
						sender.sendMessage("プレイヤーがいません！");
						return true;
					}
					return false;
				}
				if(Bukkit.getPlayer(args[0]).isOnline())
				{
					ongk.PlayerQuitTeam(Bukkit.getPlayer(args[0]));
					return true;
				}
			}
		}
		if(cmd.getName().equalsIgnoreCase("onigokko-freeze"))
		{
			if(SenderCheck(sender) >= 1)
			{
				if(alreadystart == false || !Bukkit.getPlayer(args[0]).isOnline())
				{
					try{
						Bukkit.getPlayer(args[0]);
					}catch(Exception e)
					{
						if(sender instanceof Player)
						{
							sender.sendMessage("プレイヤーが存在しません。");
							return true;
						}
					}
					if(sender instanceof Player)
					{
						sender.sendMessage("ゲームが始まっていません。");
					}
					return true;
				}
				ongk.setPlayerFreezed(Bukkit.getPlayer(args[0]));
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("onigokko-unfreeze"))
		{
			if(SenderCheck(sender) >= 1)
			{
				if(alreadystart == false || !Bukkit.getPlayer(args[0]).isOnline())
				{
					if(sender instanceof Player)
					{
						sender.sendMessage("ゲームが始まっていないかプレイヤーが存在しません。");
					}
					return true;
				}
				ongk.setPlayerUnFreezed(Bukkit.getPlayer(args[0]));
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("onigokko-hs"))
		{
			if(SenderCheck(sender) >= 1)
			{
				if(alreadystart == false)
				{
					if(sender instanceof Player)
					{
						sender.sendMessage("ゲームが始まっていません。");
					}
					return true;
				}
				int setlevel = Integer.parseInt(args[0]);
				ongk.setHunterSpeed(setlevel);
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("onigokko-freeze-all"))
		{
			if(SenderCheck(sender) >= 1)
			{
				if(alreadystart == false)
				{
					if(sender instanceof Player)
					{
						sender.sendMessage("ゲームが始まっていません。");
					}
					return true;
				}
				ongk.SetAllPlayerFreeze();
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("onigokko-unfreeze-all"))
		{
			if(SenderCheck(sender) >= 1)
			{
				if(alreadystart == false)
				{
					if(sender instanceof Player)
					{
						sender.sendMessage("ゲームが始まっていません。");
					}
					return true;
				}
				ongk.SetAllPlayerUnFreeze();
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("onigokko-give-r"))
		{
			if(SenderCheck(sender) >= 1)
			{
				if(alreadystart == false)
				{
					if(sender instanceof Player)
					{
						sender.sendMessage("ゲームが始まっていません。");
					}
					return true;
				}
				Material material = Material.matchMaterial(args[0]);
				if(material != null)
				{
					int amount = 1;
					short data = 0;
					try{
						amount = Integer.parseInt(args[1]);
					}catch(NumberFormatException e)
					{
						return false;
					}
					ItemStack item = new ItemStack(material, amount, data);
					ongk.redTeamGiveItem(item);
				}
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("onigokko-give-b"))
		{
			if(SenderCheck(sender) >= 1)
			{
				if(alreadystart == false)
				{
					if(sender instanceof Player)
					{
						sender.sendMessage("ゲームが始まっていません。");
					}
					return true;
				}
				Material material = Material.matchMaterial(args[0]);
				if(material != null)
				{
					int amount = 1;
					short data = 0;
					try{
						amount = Integer.parseInt(args[1]);
					}catch(NumberFormatException e)
					{
						return false;
					}
					ItemStack item = new ItemStack(material, amount, data);
					ongk.blueTeamGiveItem(item);
				}
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("onigokko-b-random"))	
		{
			if(SenderCheck(sender) >= 1)
			{
				if(alreadystart == false)
				{
					if(sender instanceof Player)
					{
						sender.sendMessage("ゲームが始まっていません。");
					}
					return true;
				}
				try{
					int amount = Integer.parseInt(args[0]);
					ongk.PlayerPickUpBlueTeamRandom(amount);
					return true;
				}catch(NumberFormatException e)
				{
					if(sender instanceof Player)
					{
						sender.sendMessage("不正な値です。");
					}
					return false;
				}
				
				
			}
		}
		if(cmd.getName().equalsIgnoreCase("diamond-enderchest"))
		{
			if(sender instanceof Player)
			{
				
			}else{
				return true;
			}
			if(args[0] == null)
			{
				sender.sendMessage("引数が不十分です。");
				return false;
			}
			if(Bukkit.getPlayerExact(args[0]) != null)
			{
				
			}else{
				sender.sendMessage("プレイヤーが存在しません。");
				return true;
			}
			Player player = Bukkit.getPlayerExact(args[0]);
			Inventory iv = player.getEnderChest();
			int amount = 0;
			if(iv.contains(Material.DIAMOND))
			{
				ItemStack[] is = iv.getContents();
				for(ItemStack s : is)
				{
					if(s != null)
					{
					if(s.getType() == Material.DIAMOND)
					{
						amount = amount + s.getAmount();
					}
					}
				}
				sender.sendMessage(args[0] + "さんのエンダーチェストのダイアモンド数は" + amount + "個です。");
			}else{
				sender.sendMessage(args[0] + "さんのエンダーチェストにダイアモンドは入っていません。");
			}
			return true;
			
		}
		if(cmd.getName().equalsIgnoreCase("diamond-inventory"))
		{
			if(sender instanceof Player)
			{
				
			}else{
				return true;
			}
			if(args[0] == null)
			{
				sender.sendMessage("引数が不十分です。");
				return false;
			}
			if(Bukkit.getPlayerExact(args[0]) != null)
			{
				
			}else{
				sender.sendMessage("プレイヤーが存在しません。");
				return true;
			}
			Player player = Bukkit.getPlayerExact(args[0]);
			Inventory iv = player.getInventory();
			int amount = 0;
			if(iv.contains(Material.DIAMOND))
			{
				ItemStack[] is = iv.getContents();
				for(ItemStack s : is)
				{
					if(s != null)
					{
					if(s.getType() == Material.DIAMOND)
					{
						amount = amount + s.getAmount();
					}
					}
				}
				sender.sendMessage(args[0] + "さんのインベントリのダイアモンド数は" + amount + "個です。");
			}else{
				sender.sendMessage(args[0] + "さんはダイアモンドをインベントリに持っていません。");
			}
			return true;
			
		}
		
		return false;
	}
	public boolean sendercheck(CommandSender sender)
	{
		if (sender instanceof Player)
		{
			if (sender.isOp())
			{
				return true;
			}
			if (sender.hasPermission("onigokko.gm"))
			{
				return true;
			}
			return false;
		}else
		{
			return true;
		}
	}
	/*SenderCheckメゾット
	 * プレイヤー条件が合っていれば1,そのほかなら2を返し、プレイヤーで条件が
	 * 合わない場合は0を返す
	 */
	public int SenderCheck(CommandSender sender)
	{
		if (sender instanceof Player)
		{
			if(sender.isOp() || sender.hasPermission("onigokko.gm"))
			{
				return 1;
			}
			return 0;
		}else{
			return 2;
		}
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void Move(PlayerMoveEvent event)
	{
		if (alreadystart == false)
		{
			return;
		}
		Player tmp = event.getPlayer();
		if(sbm.getBlackTeam() == sbm.getPlayerTeam(tmp))
		{
			Location locf = event.getFrom();
			Location loct = event.getTo();
			if(locf.getX() == loct.getX() && locf.getY() == loct.getY() && locf.getZ() == loct.getZ())
			{
				return;
			}
			if(locf.getX() == loct.getX() && locf.getY() != loct.getY() && locf.getZ() == loct.getZ())
			{
				return ;
			}
			PlayerSetLocation(tmp, locf);
			tmp.playSound(locf, Sound.EXPLODE, 100, 1);
		}
		
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		if(alreadystart == false)
		{
			return;
		}
		try{
			sbm.getPlayerTeam(player);
		}catch(Exception e)
		{
			return;
		}
		sbm.removePlayer(player);
	}
	public void PlayerSetLocation(Player player, Location loc)
	{
		player.teleport(loc);
	}
	public void SetSneaking()
	{
		for(Player p : sneakp)
		{
			p.setSneaking(true);
		}
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent event)
	{
		//Player player = event.getPlayer();
		/*if(alreadystart == true)
		{
			ongk.PlayerQuitTeam(player);
		}
		if(player.isOp())
		{
			return;
		}
		Location loc = player.getWorld().getSpawnLocation();
		player.teleport(loc);*/
		
	}
	
}