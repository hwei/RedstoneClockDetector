package me.hwei.bukkit.redstoneClockDetector.commands;

import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import me.hwei.bukkit.redstoneClockDetector.RCDPlugin;
import me.hwei.bukkit.redstoneClockDetector.util.AbstractCommand;
import me.hwei.bukkit.redstoneClockDetector.util.IOutput;
import me.hwei.bukkit.redstoneClockDetector.util.OutputManager;
import me.hwei.bukkit.redstoneClockDetector.util.UsageException;

public class ListCommand extends AbstractCommand {

	public ListCommand(String usage, String perm, AbstractCommand[] children, RCDPlugin plugin)
			throws Exception {
		super(usage, perm, children);
		this.plugin = plugin;
	}
	protected RCDPlugin plugin;
	protected final int pageSize = 10;
	
	@Override
	protected boolean execute(CommandSender sender, MatchResult[] data)
			throws UsageException {
		int pageNum = 1;
		if(data.length > 0) {
			Integer pageData = data[0].getInteger();
			if(pageData == null || pageData <= 0) {
				throw new UsageException(this.coloredUsage, "page number should be a positive integer.");
			}
			pageNum = pageData;
		}
		IOutput toSender = OutputManager.GetInstance().toSender(sender);
		int startIndex = (pageNum - 1) * this.pageSize;
		List<Entry<Location, Integer>> actList = this.plugin.getRedstoneActivityList();
		int totalPage = actList.size() == 0 ? 0 : (actList.size() - 1) / this.pageSize + 1;
		toSender.output(String.format("Page: " +
				ChatColor.YELLOW + "%d" + ChatColor.WHITE +
				"/" +
				ChatColor.GOLD + "%d",
				pageNum,
				totalPage));
		if(startIndex >= actList.size()) {
			toSender.output(ChatColor.GRAY.toString() + "No data.");
		} else {
			for(int i = startIndex, e = Math.min(startIndex + this.pageSize, actList.size()); i < e; ++i) {
				Entry<Location, Integer> entry = actList.get(i);
				Location l = entry.getKey();
				toSender.output(String.format(
								ChatColor.YELLOW.toString() + "%d" + ChatColor.WHITE
								+ ". " +
								ChatColor.GREEN + "(%d, %d, %d) %s " +
								ChatColor.DARK_GREEN + "%d",
								i + 1,
								l.getBlockX(), l.getBlockY(), l.getBlockZ(),
								l.getWorld().getName(),
								entry.getValue()));
			}
		}
		
		return true;
	}
}
