package com.planetbn.mysterybox.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.planetbn.mysterybox.MysteryBox;
import com.planetbn.mysterybox.utils.Box;

public class BreakMysteryBlock implements Listener {

	public MysteryBox plugin;
	
	public BreakMysteryBlock(MysteryBox plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBreakHeadBlock(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		Location loc = b.getLocation();
		
		if (b.getType() == null) return;
		
		if (b.getType() == Material.PLAYER_HEAD || b.getType() == Material.PLAYER_WALL_HEAD) {
			BlockState state = b.getState();
			
			if (state == null) return;
			
			if (state instanceof Skull) {
				Skull skull = (Skull) state;
				
				if (skull.getOwningPlayer() == null) return;
								
				if (skull.getOwningPlayer().toString().equalsIgnoreCase("CraftOfflinePlayer[UUID=00000000-7420-7c58-0000-000074207c58]")) {
					b.setType(Material.AIR);
					loc.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, loc.getX(), loc.getY(), loc.getZ(), 50, 0, 0, 0);
					new Box(p).giveRewards(b);
				}				
			}
		}
	}
	
}
