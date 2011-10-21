package me.hwei.bukkit.redstoneClockDetector.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.hwei.bukkit.redstoneClockDetector.RCDPlugin;
import me.hwei.bukkit.redstoneClockDetector.util.AbstractCommand;
import me.hwei.bukkit.redstoneClockDetector.util.IOutput;
import me.hwei.bukkit.redstoneClockDetector.util.OutputManager;
import me.hwei.bukkit.redstoneClockDetector.util.UsageException;

public class StartCommand extends AbstractCommand {

	public StartCommand(String usage, String perm, AbstractCommand[] children, RCDPlugin plugin, AbstractCommand listCommand)
			throws Exception {
		super(usage, perm, children);
		this.plugin = plugin;
		this.listCommand = listCommand;
	}
	protected RCDPlugin plugin;
	protected AbstractCommand listCommand;
	
	@Override
	protected boolean execute(CommandSender sender, MatchResult[] data)
			throws UsageException {
		Integer seconds = data[0].getInteger();
		if(seconds == null)
			return false;
		if(seconds <= 0)
			throw new UsageException(this.coloredUsage, "seconds number should be a positive integer.");
		CommandSender user = this.plugin.getUser();
		OutputManager outputManager = OutputManager.GetInstance();
		IOutput toSender = outputManager.toSender(sender);
		if(user != null) {
			toSender.output(String.format(
					ChatColor.GREEN.toString() + "%s " + ChatColor.WHITE +
					"has already started a scan.", user.getName()));
			return true;
		}
		IOutput toSenderPrefix = outputManager.prefix(outputManager.toSender(sender));
		this.plugin.start(sender, seconds, new ProgressReporter(toSenderPrefix, new FinishCallback(this.listCommand, sender)));
		toSender.output(String.format("Start a scan of %d seconds.", seconds));
		return true;
	}
	
	protected class ProgressReporter implements RCDPlugin.IProgressReporter {
		public ProgressReporter(IOutput toSender, FinishCallback finishCallback) {
			this.toSender = toSender;
			this.finishCallback = finishCallback;
		}
		@Override
		public void onProgress(int secondsRemain) {
			if(secondsRemain <= 0) {
				this.finishCallback.onFinish();
			} else if(secondsRemain <= 5) {
				this.toSender.output(String.format("Remain %d seconds.", secondsRemain));
			} else if(secondsRemain <= 60 && secondsRemain % 10 == 0) {
				this.toSender.output(String.format("Remain %d seconds.", secondsRemain));
			} else if(secondsRemain % 60 == 0) {
				this.toSender.output(String.format("Remain %d minutes.", secondsRemain / 60));
			}
		}
		protected IOutput toSender;
		protected FinishCallback finishCallback;
	}
	
	protected class FinishCallback {
		public FinishCallback(AbstractCommand listCommand, CommandSender sender) {
			this.listCommand = listCommand;
			this.sender = sender;
		}
		public void onFinish() {
			try {
				this.listCommand.execute(sender, new String[] {"list"});
			} catch (Exception e) {
			}
		}
		protected AbstractCommand listCommand;
		protected CommandSender sender;
	}
}
