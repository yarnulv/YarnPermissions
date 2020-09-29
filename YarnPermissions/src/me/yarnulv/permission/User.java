package me.yarnulv.permission;

import java.util.UUID;

public class User {
	
	private UUID uuid;
	private String name;
	private String[] permissions;
	
	public User(String uuid, String name, String[] permissions) {
		
		this.uuid = UUID.fromString(uuid);
		this.name = name;
		this.permissions = permissions;
		
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getPermissions() {
		return permissions;
	}
	
	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}
	
	public String toString() {
		String str = "";
		str += "UUID: " + uuid;
		str += "\nName: " + name;
		
		str += "\nPermissions:";
		for (String x : permissions) {
			str += "\n" + x;
		}
		
		
		return str + "\n";
	}

}
