package com.planetbn.mysterybox;

import org.bukkit.plugin.java.JavaPlugin;

import com.planetbn.mysterybox.commands.MysteryBoxCmd;
import com.planetbn.mysterybox.files.RewardsFile;
import com.planetbn.mysterybox.listeners.BreakMysteryBlock;

public class MysteryBox extends JavaPlugin {

	public static MysteryBox instance;
	
	public RewardsFile rewardsFile;

	public void onEnable() {
		instance = this;
		
		registerCommands();
		registerListeners();
		
		rewardsFile = new RewardsFile();
		
		getLogger().info("Mysterybox is running....");
	}

	public void onDisable() {

	}
	
	public void registerCommands() {
		getCommand("mysterybox").setExecutor(new MysteryBoxCmd());
	}
	
	public void registerListeners() {
		getServer().getPluginManager().registerEvents(new BreakMysteryBlock(this), this);
	}

	public static MysteryBox getInstance() {
		return instance;
	}

}
