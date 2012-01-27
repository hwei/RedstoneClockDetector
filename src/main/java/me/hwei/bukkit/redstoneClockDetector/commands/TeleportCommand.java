package me.hwei.bukkit.redstoneClockDetector.commands;

import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.hwei.bukkit.redstoneClockDetector.RCDPlugin;
import me.hwei.bukkit.redstoneClockDetector.util.AbstractCommand;
import me.hwei.bukkit.redstoneClockDetector.util.IOutput;
import me.hwei.bukkit.redstoneClockDetector.util.OutputManager;
import me.hwei.bukkit.redstoneClockDetector.util.UsageException;

public class TeleportCommand extends AbstractCommand {

	public TeleportCommand(String usage, String perm, AbstractCommand[] children, RCDPlugin plugin)
			throws Exception {
		super(usage, perm, children);
		this.plugin = plugin;
	}
	protected RCDPlugin plugin;
	
	@Override
	protected boolean execute(CommandSender sender, MatchResult[] data)
			throws UsageException {
		Player player = sender instanceof Player ? (Player)sender : null;
		int tpNum = 0;
		OutputManager outputManager = OutputManager.GetInstance();
		IOutput toSender = outputManager.toSender(sender);
		if(data.length == 0) {
			if(player == null) {
				throw new UsageException(this.coloredUsage, "Must specify which player to teleport.");
			}
		} else if(data.length == 1) {
			if(player == null) {
				String playerName = data[0].getString();
				player = this.plugin.getServer().getPlayer(playerName);
				if(player == null) {
					toSender.output(String.format(
							"Can not find player " +
									ChatColor.GREEN + "%d" + ChatColor.WHITE +
									".", playerName));
					return true;
				}
			} else {
				Integer numData = data[0].getInteger();
				if(numData == null || numData <= 0) {
					throw new UsageException(this.coloredUsage, "Location num must be a positive integer.");
				}
				tpNum = numData - 1;
			}
		} else if(data.length == 2) {
			String playerName = data[0].getString();
			player = this.plugin.getServer().getPlayer(playerName);
			if(player == null) {
				toSender.output(String.format(
						"Can not find player " +
								ChatColor.GREEN + "%d" + ChatColor.WHITE +
								".", playerName));
				return true;
			}
			Integer numData = data[1].getInteger();
			if(numData == null || numData <= 0) {
				throw new UsageException(this.coloredUsage, "Location num must be a positive integer.");
			}
			tpNum = numData - 1;
		}
		List<Entry<Location, Integer>> actList = this.plugin.getRedstoneActivityList();
		if(tpNum >= actList.size()) {
			toSender.output(String.format(
					"Location num " +
					ChatColor.YELLOW + "%d " + ChatColor.WHITE +
					"dose not exist.", tpNum + 1));
		} else {
			player.teleport(actList.get(tpNum).getKey());
			IOutput toPlayer = outputManager.prefix(outputManager.toSender(player));
			if(player == sender)
				toPlayer.output("Teleporting...");
			else
				toPlayer.output(String.format(
						ChatColor.GREEN.toString() + "%s " + ChatColor.WHITE +
						"is teleporting you...", sender.getName()));
		}
		return true;
	}

}
