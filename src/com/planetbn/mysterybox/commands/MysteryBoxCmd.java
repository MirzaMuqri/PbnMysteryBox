package com.planetbn.mysterybox.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.planetbn.mysterybox.MysteryBox;
import com.planetbn.mysterybox.files.RewardsFile;
import com.planetbn.mysterybox.utils.Box;

public class MysteryBoxCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			if (cmd.getName().equalsIgnoreCase("mysterybox")) {
				if (sender.hasPermission("mysterybox.cmd")) {
					if (args.length == 0) {
						sender.sendMessage("§7/mysterybox {name} {amount}");
						sender.sendMessage("§7/mysterybox giveall {amount}");
						sender.sendMessage("§7/mysterybox reload");
					}

					else if (args.length == 1) {
						if (args[0].equalsIgnoreCase("reload")) {
							MysteryBox.getInstance().rewardsFile = new RewardsFile();
							sender.sendMessage("§7Reloaded rewards.yml!");
						}
					}

					else if (args.length == 2) {
						if (args[0].equalsIgnoreCase("giveall")) {
							// TODO: Nothing
						} else {
							Player p2 = Bukkit.getPlayer(args[0]);
							int amount = Integer.valueOf(args[1]);

							if (p2 != null) {

								if (amount >= 12) {
									sender.sendMessage("§c§l! >§7 You cannot give more than 10 box!");
									return true;
								}

								new Box(p2).giveSkull(amount);
							} else {
								sender.sendMessage("§c§l! > §7Could not fine the player.");
							}
						}
					}

					else {
						sender.sendMessage("§7/mysterybox {name} {amount}");
						sender.sendMessage("§7/mysterybox reload");
					}
				} else {
					sender.sendMessage("§c§l! > §7You don't have permissions to access that command.");
				}
			}
			return false;
		}

		Player p = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("mysterybox")) {
			if (p.hasPermission("mysterybox.cmd")) {
				if (args.length == 0) {
					p.sendMessage("§7/mysterybox {name} {amount}");
					p.sendMessage("§7/mysterybox giveall {amount}");
					p.sendMessage("§7/mysterybox reload");
				}

				else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("reload")) {
						MysteryBox.getInstance().rewardsFile = new RewardsFile();
						p.sendMessage("§7Reloaded rewards.yml!");
					}
				}

				else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("giveall")) {
						int amount = Integer.valueOf(args[1]);

						if (amount >= 12) {
							p.sendMessage("§c§l! >§7 You cannot give more than 10 box!");
							return true;
						}

						for (Player on : Bukkit.getOnlinePlayers()) {
							new Box(on).giveSkull(amount);
						}

					} else {
						Player p2 = Bukkit.getPlayer(args[0]);
						int amount = Integer.valueOf(args[1]);

						if (p2 != null) {

							if (amount >= 12) {
								p.sendMessage("§c§l! >§7 You cannot give more than 10 box!");
								return true;
							}

							new Box(p2).giveSkull(amount);
						} else {
							p.sendMessage("§c§l! > §7Could not fine the player.");
						}
					}
				}

				else {
					p.sendMessage("§7/mysterybox {name} {amount}");
					p.sendMessage("§7/mysterybox giveall {amount}");
					p.sendMessage("§7/mysterybox reload");
				}
			} else {
				p.sendMessage("§c§l! > §7You don't have permissions to access that command.");
			}
		}

		return false;
	}

}
