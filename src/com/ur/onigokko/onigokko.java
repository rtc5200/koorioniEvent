package com.ur.onigokko;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
//import org.kitteh.tag.TagAPI;

/*PlayerFreezeStatsのboolean:true = フリーズしない; false = フリーズする
 * 
 */
@SuppressWarnings("unused")
public class onigokko extends JavaPlugin implements Listener 
{
	Random ria;
	Main main;
	Team redTeam;
	Team blueTeam;
	Logger log = Logger.getLogger("umasyu");
	Logger log1;
	int splevel = 3;
	Firework fw1;
	Scoreboard sb;
	Objective ob;
	BukkitTask task;
	Boolean ticancel = false;
	SbManager sm;
	ItemUtility iu;
	public ItemPotioner ipn;
	public onigokko(Main main){
		this.main = main;
		sm = new SbManager(main);
		sb = sm.getScoreboard();
		redTeam = sm.getRedTeam();
		blueTeam = sm.getBlueTeam();
		ob = sm.getObjective();
		ipn = new ItemPotioner();
	}
	public void  createTeam()
	{
		sm.resetScore();
	}
	public void ready()
	{
		PlayersAddRedteam();
		RedTeamItems();
	}
	public void exit()
	{
		ArrayList<Player> allpls = Utils.getAllPlayers();
		ArrayList<Integer> scs = new ArrayList<Integer>();
		for(Player p : allpls)
		{
			scs.add(sm.getScore(p));
		}
		Collections.sort(scs);
		Collections.reverse(scs);
		int maxsc = Collections.max(scs);
		String mps = "";
		Random ra = new Random();
		int r = ra.nextInt(255);
		int g = ra.nextInt(255);
		int b = ra.nextInt(255);
		
		for(Player pssss : allpls)
		{
			if(sm.getPlayerTeam(pssss) != null)
			{
				InventoryUtil.RedTeamUnEquip(pssss);
				InventoryUtil.removePlayerMaterial(pssss, Variables.CHANGE_SNEAK_ITEM);
				InventoryUtil.PlayeraddItem(pssss,ItemUtility.getMONEYItem(2));
				if(maxsc == sm.getScore(pssss))
				{
					mps = mps + pssss.getDisplayName() + "(スコア):" + sm.getScore(pssss) +  ",";
					InventoryUtil.PlayeraddItem(pssss,ItemUtility.getMONEYItem(3));
					InventoryUtil.PlayeraddItem(pssss,ItemUtility.makeFireworkRandom(Variables.WIN_FIREWORK_NAME, r, g, b));
				}
			}
			
		}
		String Message0 = ChatColor.AQUA.toString() + "--------氷鬼結果レポート--------";
		String Message1 = ChatColor.GREEN.toString() + "最高スコア獲得者は以下の方々です。";
		String Message2 = ChatColor.AQUA.toString() + "お疲れ様でした。";
		mps.substring(mps.length());
		main.getServer().broadcastMessage(Message0);
		main.getServer().broadcastMessage(Message1);
		main.getServer().broadcastMessage(mps);
		main.getServer().broadcastMessage(Message2);
		sm.RedTeamMemberRemove();
		sm.BlueTeamMemberRemove();
		sm.BlackTeamMemberRemove();
	}
	public void PlayerAddRedteam(Player player)
	{
		if(sm.getPlayerTeam(player) == sm.getBlueTeam())
		{
			sm.removePlayer(player);
			InventoryUtil.BlueTeamUnEquip(player, this);
		}
		sm.addPlayerRedTeam(player);
		player.sendMessage(ChatColor.RED + "逃走者" + ChatColor.RESET + "チームになりました。");
		InventoryUtil.RedTeamEquip(player);
		return;
	}
	public void PlayersAddRedteam()
	{
		ArrayList<Player> playerslist = Utils.getAllPlayers();
		for (Player ap : playerslist)
		{
			sm.addPlayerRedTeam(ap);
			ap.sendMessage(ChatColor.RED +"逃走者" + ChatColor.RESET + "チームに追加されました。");
			ap.setSneaking(true);
		}
	}
	public void PlayerPickUpBlueTeam(Player player)
	{
		if(player.isOnline())
		{if(sm.getPlayerTeam(player) == sm.getRedTeam() || sm.getPlayerTeam(player) == sm.getBlackTeam())
		{
			InventoryUtil.RedTeamUnEquip(player);
		}
			sm.addPlayerBlueTeam(player);
			player.sendMessage(ChatColor.BLUE + "鬼" + ChatColor.RESET + "チームになりました。");
			InventoryUtil.BlueTeamEquip(player,this);
			return;
		}
	}
	public void PlayerPickUpBlueTeamRandom(int i)
	{
		ArrayList<Player> rpsl = sm.getRedTeamMemberList();
		Collections.shuffle(rpsl);
		Player[] rps = rpsl.toArray(new Player[rpsl.size()]);
		if(i == 0)
		{
			return;
		}
		if(rps.length >= i)
		{
			for(int t = 0; t< i; t++)
			{
				if(rps.length <= t)
				{
					break;
				}else{
					PlayerPickUpBlueTeam(rps[t]);
				}
			}
		}else{
			Bukkit.getServer().broadcastMessage("鬼選択に失敗しました。");
		}
	}
	public void BlueTeamMemberTP(double x, double y, double z)
	{
		Set<OfflinePlayer> blueplayers = blueTeam.getPlayers();
		for(OfflinePlayer ofp : blueplayers)
		{
			if(ofp.isOnline())
			{
				Entity et = (Entity) ofp;
				Location pnl = et.getLocation();
				pnl.setX(x);
				pnl.setY(y);
				pnl.setZ(z);
				et.teleport(pnl);
			}
		}
	}
	public void redTeamTP(double x, double y, double z)
	{
		Set<OfflinePlayer> redplayers = redTeam.getPlayers();
		for(OfflinePlayer ofp : redplayers)
		{
			if(ofp.isOnline())
			{
				Entity et = (Entity) ofp;
				Location pnl = et.getLocation();
				pnl.setX(x);
				pnl.setY(y);
				pnl.setZ(z);
				et.teleport(pnl);
			}
		}
	}
	public void removePlayerblueTeam(Player player)
	{
		if (blueTeam.hasPlayer(player))
		{
			blueTeam.removePlayer(player);
			InventoryUtil.BlueTeamUnEquip(player,this);
		}
		
	}
	
	public void PlayerQuitTeam(Player player)
	{
		if(sm.getPlayerTeam(player) != null)
		{
			sm.SetPlayerColor(player, ChatColor.RESET);
			sm.getPlayerTeam(player).removePlayer(player);
			if(sm.getPlayerTeam(player) == blueTeam)
			{
				InventoryUtil.BlueTeamUnEquip(player,this);
			}else if(sm.getPlayerTeam(player) == sm.getRedTeam() ||sm.getPlayerTeam(player) == sm.getBlackTeam())
			{
				InventoryUtil.RedTeamUnEquip(player);
			}
			player.sendMessage("チームから抜けました。");
		}
	}
	/*public void SetAllPlayerFreeze()
	{
		Player[] players = Bukkit.getOnlinePlayers();
		for(Player p : players)
		{
			PlayerFreezeStats.put(p, false);
		}
	}
	public void SetAllPlayerUnFreeze()
	{
		Player[] players = Bukkit.getOnlinePlayers();
		for(Player p : players)
		{
			PlayerFreezeStats.put(p, true);
		}
	}*/
	
	public void giveItemTeamMember(Team team,ItemStack item)
	{
		Set<OfflinePlayer> teamplayers = redTeam.getPlayers();
		for(OfflinePlayer ofp : teamplayers)
		{
			Inventory pi = ((Player) ofp).getInventory();
			pi.addItem(item);
			return;
		}
		
	}
	
	public void RedTeamItems()
	{
		for(Player p : sm.getRedTeamMemberList())
		{
			InventoryUtil.RedTeamEquip(p);
		}
		
	}
	public void setHunterSpeed(int i)
	{
		splevel = i -1;
		return;
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void edbee(EntityDamageByEntityEvent event)
	{
		if(event.getDamager().getType() == EntityType.SNOWBALL)
		{
			Entity rr = event.getEntity();
			if(rr instanceof Player)
			{
				Player pp = ((Player)rr);
				if(sm.getPlayerTeam(pp) == sm.getBlueTeam())
				{
					PotionEffect pe = new PotionEffect(PotionEffectType.BLINDNESS, 100, 10);
					pp.addPotionEffect(pe);
					
				}
			}
		}
		if (event.getDamager().getType() == EntityType.PLAYER)
		{
			if (event.getEntityType() == EntityType.PLAYER)
			{
				Player recieveplayer = (Player) event.getEntity();
				Player attackplayer = (Player) event.getDamager();
				
					if (sm.getPlayerTeam(attackplayer) == blueTeam)
					{
						if(sm.getPlayerTeam(recieveplayer) == sm.getRedTeam())
						{
							String cr = ChatColor.RED.toString();
							String cg = ChatColor.GREEN.toString();
							String cb = ChatColor.BLUE.toString();
							String cm = cr + recieveplayer.getName() + cg + "が" + cb + attackplayer.getName() +cg + "に凍らされてしまった!" ;
							main.getServer().broadcastMessage(cm);
							sm.SetPlayerColor(recieveplayer, ChatColor.BLACK);
							int alsc = sm.getScore(attackplayer);
							sm.setScore(attackplayer, alsc + 1);
							//score処理
							String message = "[スコア]1ポイント獲得しました。";
							attackplayer.sendMessage(ChatColor.GREEN.toString() + message);
							attackplayer.sendMessage("[スコア]現在のあなたのスコア;" + sm.getScore(attackplayer));
						}
						sm.addPlayerBlackTeam(recieveplayer);
					}
					if (sm.getPlayerTeam(attackplayer) == sm.getRedTeam())
					{
						if(sm.getPlayerTeam(recieveplayer) == sm.getBlackTeam())
						{
							if(sm.getPlayerTeam(attackplayer) == sm.getBlackTeam())
							{
								return;
							}
							String cr = ChatColor.RED.toString();
							String cg = ChatColor.GREEN.toString();
							String cm = cr + attackplayer.getName() + cg + "が" + cr + recieveplayer.getName() + cg + "を助けた!";						
							main.getServer().broadcastMessage(cm);
							sm.SetPlayerColor(recieveplayer, ChatColor.RED);
							int alsc = sm.getScore(attackplayer) + 2;
							sm.setScore(attackplayer, alsc);
							//score処理
							String message = "[スコア]2ポイント獲得しました。";
							attackplayer.sendMessage(ChatColor.GREEN.toString() + message);
							attackplayer.sendMessage("[スコア]現在のあなたのスコア;" + sm.getScore(attackplayer));
							sm.addPlayerRedTeam(recieveplayer);
						}
						
						
					}
					event.setDamage(0);
					event.setCancelled(true);
				}
			}
		}
	/*@EventHandler
	public void onhit(ProjectileHitEvent event)
	{
		Projectile pjl = event.getEntity();//CraftSnowBall
		Entity shooter = pjl.getShooter();//CrafterPlayer[name=_RailgunS_]
		EntityType hittertp = event.getEntityType();//SNOWBALL
		Location loc = event.getEntity().getLocation();//地面に当たった地点
		
		if(hittertp == EntityType.SNOWBALL)
		{
			double maxx = loc.getX() + 5;
			double minx = loc.getX() - 5;
			double maxy = loc.getY() + 5;
			double miny = loc.getY() - 5;
			double maxz = loc.getZ() + 5;
			double minz = loc.getZ() - 5;
			snowballeffect(maxx,minx,maxy,miny,maxz,minz,loc);
		}
		if(hittertp == EntityType.EGG)
		{
			double maxx = loc.getX() + 2;
			double minx = loc.getX() - 2;
			double maxy = loc.getY() + 2;
			double miny = loc.getY() - 2;
			double maxz = loc.getZ() + 2;
			double minz = loc.getZ() - 2;
			if(shooter instanceof Player)
			{
				Player ply = (Player) shooter;
				eggact(maxx,minx,maxy,miny,maxz,minz,loc,ply);
			
				
			}
			
		}
	}
	public void snowballeffect(double maxx, double minx, double maxy, double miny, double maxz, double minz, Location loc)
	{
		Player[] player = Bukkit.getOnlinePlayers();
		World world = loc.getWorld();
		world.playSound(loc, Sound.EXPLODE, 80, 1);
		for(Player p : player)
		{
			if(sm.getPlayerTeam(p) == redTeam)
			{
				Location ploc = p.getLocation();
				double px = ploc.getX();
				double py = ploc.getY();
				double pz = ploc.getZ();
				if(minx < px && maxx > px && miny < py && maxy > py && minz < pz && maxz > pz  )
				{
					p.removePotionEffect(PotionEffectType.SPEED);
					p.removePotionEffect(PotionEffectType.INVISIBILITY);
					PotionEffect pe = new PotionEffect(PotionEffectType.BLINDNESS, 200, 100);
					p.addPotionEffect(pe);
				}
			}
		}
	}
	public void eggact(double maxx, double minx, double maxy, double miny, double maxz, double minz, Location loc,Player shooter)
	{
		Player[] player = Bukkit.getOnlinePlayers();
		World world = loc.getWorld();
		world.playSound(loc, Sound.EXPLODE, 80, 1);
		for(Player p : player)
		{
			if(sm.getPlayerTeam(p) == redTeam)
			{
				Location ploc = p.getLocation();
				double px = ploc.getX();
				double py = ploc.getY();
				double pz = ploc.getZ();
				if(minx < px && maxx > px && miny < py && maxy > py && minz < pz && maxz > pz  )
				{
					if(sm.getPlayerTeam(p) == sm.getBlackTeam())
					{
						if(sm.getPlayerTeam(shooter) != sm.getRedTeam())
						{
							return;
						}
						String cr = ChatColor.RED.toString();
						String cg = ChatColor.GREEN.toString();
						String cm = cr + shooter.getName() + cg + "が" + cr + p.getName() + cg + "を助けた!";						
						main.getServer().broadcastMessage(cm);
						sm.SetPlayerColor(p, ChatColor.RED);
						int alsc = sm.getScore(shooter) + 1;
						sm.setScore(shooter, alsc);
						//score処理
						String message = "[スコア]1ポイント獲得しました。";
						shooter.sendMessage(ChatColor.GREEN.toString() + message);
						shooter.sendMessage("[スコア]現在のあなたのスコア;" + sm.getScore(shooter));
						sm.addPlayerRedTeam(p);
					}
					
				}
			}
		}
	}
	*/
	@EventHandler(priority = EventPriority.HIGH)
	public void chat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		String chatmessage = event.getMessage();
		String chatformat = event.getFormat();
			double x = player.getLocation().getX();
			double y = player.getLocation().getY();
			double z = player.getLocation().getZ();
			BigDecimal bdx = new BigDecimal(String.valueOf(x));
			BigDecimal bdy = new BigDecimal(String.valueOf(y));
			BigDecimal bdz = new BigDecimal(String.valueOf(z));
			x = bdx.setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
			y = bdy.setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
			z = bdz.setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
			String lm = "x:" + x +  " y;" + y + " z:" + z;
			String ud = "どいてどいてー。今うましゅーが死にましゅよー";
			String uf = "シュークリームひとつ貰うね";
			String uc = "(⊆〇Д〇⊇)来ったー!来た!来た!来た!来た!うーまーしゅー!!";
			chatmessage = chatmessage.replaceAll("<loc>", lm);
			chatmessage = chatmessage.replaceAll("<umasyu_d>", ud);
			chatmessage = chatmessage.replaceAll("<umasyu_f>", uf);
			chatmessage = chatmessage.replaceAll("<umasyu_c>", uc);
			event.setMessage(chatmessage);
	}
	/*@EventHandler(priority = EventPriority.HIGH)
	public void itemuse(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		ItemStack hi = event.getPlayer().getItemInHand();
		Material pml = player.getItemInHand().getType();
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
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
			sm.SetPlayerColor(player, ChatColor.RED);
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
	}*/
	public void CountDownPlayerExp(int time){
		Player[] players = Bukkit.getOnlinePlayers();
		for(Player p : players)
		{
			p.setLevel(time);
			if(time <= 100)
			{
				p.playSound(p.getLocation(), Sound.NOTE_STICKS, 100, 1);
			}
			if(time == 0)
			{
				main.getServer().broadcastMessage("ゲーム終了です。");
				exit();
			}
			
		}
	}
	public void redTeamGiveItem(ItemStack item)
	{
		for(OfflinePlayer p : sm.getRedTeamMember())
		{
			if(p.isOnline())
			{
				((Player)p).getInventory().addItem(item);
			}
		}
	}
	public void blueTeamGiveItem(ItemStack item)
	{
		for(OfflinePlayer p : sm.getBlueTeamMember())
		{
			if(p != null)
			{
				if(p.isOnline())
				{
					((Player)p).getInventory().addItem(item);
				}
			}
			
		}
	}
	
}
