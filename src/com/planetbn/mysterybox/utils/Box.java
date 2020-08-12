package com.planetbn.mysterybox.utils;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import com.planetbn.mysterybox.MysteryBox;

public class Box {

	Player p;

	public Box(Player p) {
		this.p = p;
	}

	public void giveSkull(int amount) {
		p.sendMessage("§a§l! > §7+" + amount + " MysteryBox.");
		ItemStack item = SkullCreator.itemFromBase64("eyJ0aW1lc3RhbXAiOjE1MTkwMDM1MzQwMzksInByb2ZpbGVJZCI6IjMwMTlmYTFhMTZiODQ0MWNiNjVkY2NiYzAzYzdjNTFlIiwicHJvZmlsZU5hbWUiOiJBaXJCcnVudCIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM5YzhlMjkzYTczOThmNzVhYzhhZThlYWJmNDdmM2U1YzBjMWI0ZTRmOGI3Yjg5NzgzYjVmNTc3MWMzZSJ9fX0=");
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		
		NamespacedKey key = new NamespacedKey(MysteryBox.getInstance(), "mysteryboxbooo");

		item.setAmount(amount);
		meta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, Math.PI);
		meta.setDisplayName("§eMystery Box");
		meta.setLore(Arrays.asList("§7Place this blocks and break it.", "", "§7mc.planetbn.com"));
		item.setItemMeta(meta);

		p.getInventory().addItem(item);
		
		MysteryBox.getInstance().rewardsFile.addMysteryBoxGiven(amount);
	}

	public void dropItem(Location loc, Material mat, int amount) {
		Item i = loc.getWorld().dropItem(loc, new ItemStack(mat, amount));
		i.setVelocity(i.getVelocity().zero());
	}

	public void giveRewards(Block b) {
		Random r = new Random();
		int max = 100;
		int res = r.nextInt(max);

		if (res <= 2) {
			MysteryBox.getInstance().rewardsFile.getLegendaryRewards(p, b.getLocation());
			MysteryBox.getInstance().rewardsFile.addMysteryBoxOpen("legendary");
		} else if (res <= 7) {
			MysteryBox.getInstance().rewardsFile.getRareRewards(p, b.getLocation());
			MysteryBox.getInstance().rewardsFile.addMysteryBoxOpen("rare");
		} else {
			MysteryBox.getInstance().rewardsFile.getCommonRewards(p, b.getLocation());
			MysteryBox.getInstance().rewardsFile.addMysteryBoxOpen("common");
		}
	}

}
