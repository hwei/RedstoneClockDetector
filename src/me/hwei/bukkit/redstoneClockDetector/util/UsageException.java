package me.hwei.bukkit.redstoneClockDetector.util;

public class UsageException extends Exception {

	public UsageException(String usage, String message) {
		this.usage = usage;
		this.message = message;
	}
	
	public String getUsage() {
		return usage;
	}

	public String getMessage() {
		return message;
	}

	private String usage;
	private String message;
	
	private static final long serialVersionUID = 1L;

}
