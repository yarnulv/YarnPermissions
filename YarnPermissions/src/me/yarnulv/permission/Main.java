package me.yarnulv.permission;

import java.io.File;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public PlayerListener playerListener = new PlayerListener(this);
	public UserPermissionsFile upf;
	public static ArrayList<User> userList;
	
	@Override
	public void onEnable() {
		
		upf = new UserPermissionsFile(new File("user.permissions"));
		userList = upf.loadPermissionsFile();
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(playerListener, this);
		
		getCommand("perm").setExecutor(new PermCommand(this));
	}
	
	@Override
	public void onDisable() {
		
		if (upf.savePermissionsFile(userList) == false) {
			getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save user.permissions!");
		}
		
	}
	
	public void updateAttachment(Player player) {
		
		for (User x : Main.userList) {
			if (x.getName().equalsIgnoreCase(player.getName())) {
				PermissionAttachment attachment = player.addAttachment(this);
				for (String y : x.getPermissions()) {
					attachment.setPermission(y, true);
				}
				
			}
		}
	}

}
