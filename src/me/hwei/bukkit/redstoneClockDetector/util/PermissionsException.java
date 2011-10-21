package me.hwei.bukkit.redstoneClockDetector.util;

public class PermissionsException extends Exception {

	public PermissionsException(String perms) {
		this.perms = perms;
	}
	
	public String getPerms() {
		return perms;
	}

	private String perms;
	
	private static final long serialVersionUID = 1L;
}
