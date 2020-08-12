package com.planetbn.mysterybox.files;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.planetbn.mysterybox.MysteryBox;

public class RewardsFile {

	File file;
	FileConfiguration config;

	public RewardsFile() {
		file = new File(MysteryBox.getInstance().getDataFolder(), "rewards.yml");

		if (!isExists()) {
			MysteryBox.getInstance().saveResource("rewards.yml", false);
		}

		config = YamlConfiguration.loadConfiguration(file);
	}

	public boolean isExists() {
		return file.exists();
	}

	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getCommonRewards(Player p, Location loc) {
		List<String> list = config.getStringList("common");

		Random random = new Random();
		int index = random.nextInt(list.size());

		String rewards = list.get(index).toString();
		String[] split = rewards.split(":");

		if (split[0].equalsIgnoreCase("cmd")) {
			String cmd = split[1].replace("{name}", p.getName());

			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);

			p.sendMessage("§a§lMYSTERY > §7You received §a" + split[2] + "§7!");
		}

		else if (split[0].equalsIgnoreCase("summon")) {
			// "summon:CREEPER:&cThis will be the name:10"
			for (int i = 0; i < Integer.valueOf(split[3]); i++) {
				Entity ent = loc.getWorld().spawnEntity(loc, EntityType.valueOf(split[1]));

				ent.setCustomName(split[2].replace("&", "§"));
			}

			p.sendMessage("§a§lMYSTERY > §7You received §a" + split[3] + " " + split[2].replace("&", "§") + "§7!");
		}

		else {
			ItemStack item = new ItemStack(Material.valueOf(split[1]), Integer.valueOf(split[2]));

			loc.getWorld().dropItem(loc, item);

			p.sendMessage("§a§lMYSTERY > §7You received §a" + split[2] + " " + split[1].toLowerCase().replace('_', ' ')
					+ "§7!");
		}

		p.playSound(loc, Sound.ENTITY_EXPERIENCE_BOTTLE_THROW, 1, 0);
	}

	public void getRareRewards(Player p, Location loc) {
		List<String> list = config.getStringList("rare");

		Random random = new Random();
		int index = random.nextInt(list.size());

		String rewards = list.get(index).toString();
		String[] split = rewards.split(":");

		if (split[0].equalsIgnoreCase("cmd")) {
			String cmd = split[1].replace("{name}", p.getName());

			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);

			p.sendMessage("§a§lMYSTERY > §7You received a rare §a" + split[2] + "§7!");
		} else {
			ItemStack item = new ItemStack(Material.valueOf(split[1]), Integer.valueOf(split[2]));

			loc.getWorld().dropItem(loc, item);

			p.sendMessage("§a§lMYSTERY > §7You received a rare §a" + split[2] + " "
					+ split[1].toLowerCase().replace('_', ' ') + "§7!");
		}

		p.playSound(loc, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 0);
	}

	public void getLegendaryRewards(Player p, Location loc) {
		List<String> list = config.getStringList("legendary");

		Random random = new Random();
		int index = random.nextInt(list.size());

		String rewards = list.get(index).toString();
		String[] split = rewards.split(":");

		if (split[0].equalsIgnoreCase("cmd")) {
			String cmd = split[1].replace("{name}", p.getName());

			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);

			p.sendMessage("§a§lMYSTERY > §7You received a legendary §a" + split[2] + "§7!");
		} else {
			ItemStack item = new ItemStack(Material.valueOf(split[1]), Integer.valueOf(split[2]));

			loc.getWorld().dropItem(loc, item);

			p.sendMessage("§a§lMYSTERY > §7You received a legendary §a" + split[2] + " "
					+ split[1].toLowerCase().replace('_', ' ') + "§7!");
		}

		Bukkit.getWorld(p.getWorld().getName()).playSound(loc, Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 0);
	}
	
	public void addMysteryBoxOpen(String type) {
		int amt = config.getInt("mysterybox-opened." + type);
		
		config.set("mysterybox-opened." + type, amt + 1);
		
		save();
	}
	
	public void addMysteryBoxGiven(int i) {
		int amt = config.getInt("mysterybox-given");
		
		config.set("mysterybox-given", amt + i);
		
		save();
	}
}
