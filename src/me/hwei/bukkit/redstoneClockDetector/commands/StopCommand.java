package me.hwei.bukkit.redstoneClockDetector.commands;

import org.bukkit.command.CommandSender;

import me.hwei.bukkit.redstoneClockDetector.RCDPlugin;
import me.hwei.bukkit.redstoneClockDetector.util.AbstractCommand;
import me.hwei.bukkit.redstoneClockDetector.util.IOutput;
import me.hwei.bukkit.redstoneClockDetector.util.OutputManager;
import me.hwei.bukkit.redstoneClockDetector.util.UsageException;

public class StopCommand extends AbstractCommand {

	public StopCommand(String usage, String perm, AbstractCommand[] children, RCDPlugin plugin)
			throws Exception {
		super(usage, perm, children);
		this.plugin = plugin;
	}
	protected RCDPlugin plugin;

	@Override
	protected boolean execute(CommandSender sender, MatchResult[] data)
			throws UsageException {
		IOutput toSender = OutputManager.GetInstance().toSender(sender);
		if(this.plugin.stop()) {
			toSender.output("Successfully stoped.");
		} else {
			toSender.output("Already stoped.");
		}
		return true;
	}

}
