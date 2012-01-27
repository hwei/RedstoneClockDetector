package me.hwei.bukkit.redstoneClockDetector.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OutputManager {
	protected static OutputManager instance = null;
	public static OutputManager GetInstance() {
		return instance;
	}
	public static void Setup(String prefix, IOutput toConsole, IOutput toAll, IPlayerGetter playerGetter) {
		instance = new OutputManager(prefix, toConsole, toAll, playerGetter);
	}
	
	protected OutputManager(String prefix, IOutput toConsole, IOutput toAll, IPlayerGetter playerGetter) {
		this.prefix = prefix;
		this.toConsole = toConsole;
		this.toAll = toAll;
		this.playerGetter = playerGetter;
	}
	
	protected String prefix;
	protected IOutput toConsole;
	protected IOutput toAll;
	protected IPlayerGetter playerGetter;
	
	public IOutput toSender(CommandSender sender) {
		return new OutputToSender(sender);
	}
	public IOutput toSender(String playerName) {
		Player player = this.playerGetter.get(playerName);
		return player == null ? null : new OutputToSender(player);
	}
	public IOutput toConsole() {
		return this.toConsole;
	}
	public IOutput toAll() {
		return this.toAll;
	}
	public IOutput prefix(IOutput output) {
		return new OutputPrefix(output);
	}
	public IOutput prefix(String prefix, IOutput output) {
		return new OutputPrefix(prefix, output);
	}
	public IOutput combo(IOutput[] outputs) {
		return new OutputCombo(outputs);
	}
	public interface IPlayerGetter {
		public Player get(String name);
	}
	
	protected class OutputToSender implements IOutput {
		public OutputToSender(CommandSender sender) {
			this.sender = sender;
		}
		protected  CommandSender sender;
		@Override
		public void output(String message) {
			sender.sendMessage(message);
		}
	}
	protected class OutputPrefix implements IOutput {
		public OutputPrefix(String prefix, IOutput output) {
			this.prefix = prefix;
			this.output = output;
		}
		public OutputPrefix(IOutput output) {
			this.prefix = OutputManager.this.prefix;
			this.output = output;
		}
		protected String prefix;
		protected IOutput output;
		@Override
		public void output(String message) {
			if(this.output == null)
				return;
			String prefixedMessage = this.prefix + message;
			this.output.output(prefixedMessage);
		}
	}
	protected class OutputCombo implements IOutput {
		public OutputCombo(IOutput[] outputs) {
			this.outputs = outputs;
		}
		protected IOutput[] outputs;
		@Override
		public void output(String message) {
			for(IOutput output : this.outputs) {
				output.output(message);
			}
		}
		
	}
}
