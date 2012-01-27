package me.hwei.bukkit.redstoneClockDetector.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import me.hwei.bukkit.redstoneClockDetector.RCDPlugin;
import me.hwei.bukkit.redstoneClockDetector.util.AbstractCommand;
import me.hwei.bukkit.redstoneClockDetector.util.IOutput;
import me.hwei.bukkit.redstoneClockDetector.util.OutputManager;
import me.hwei.bukkit.redstoneClockDetector.util.UsageException;

public class StatusCommand extends AbstractCommand {

	public StatusCommand(String usage, String perm, AbstractCommand[] children, RCDPlugin plugin)
			throws Exception {
		super(usage, perm, children);
		this.plugin = plugin;
		PluginDescriptionFile des = plugin.getDescription();
		this.pluginInfo = String.format(
				"Version: " + ChatColor.YELLOW + "%s" +
						ChatColor.WHITE + ", Author: " +
						ChatColor.YELLOW + "%s",
						des.getVersion(),
						des.getAuthors().get(0));
	}
	
	protected RCDPlugin plugin;
	protected String pluginInfo;

	@Override
	protected boolean execute(CommandSender sender, MatchResult[] data)
			throws UsageException {
		IOutput toSender = OutputManager.GetInstance().toSender(sender);
		OutputManager.GetInstance().prefix(toSender).output(this.pluginInfo);
		CommandSender user = this.plugin.getUser();
		if(user != null) {
			toSender.output(String.format(
					ChatColor.GREEN.toString() + "%s " + ChatColor.WHITE +
					"has started a scan, remaining " +
					ChatColor.YELLOW + "%d " + ChatColor.WHITE + 
					"seconds to finish.",
					user.getName(),
					this.plugin.getSecondsRemain()));
		}
		
		return true;
	}

}
