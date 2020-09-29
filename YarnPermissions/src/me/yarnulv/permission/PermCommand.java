package me.yarnulv.permission;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermCommand implements CommandExecutor {
	private final Main plugin;
	
	public PermCommand(Main plugin) {
		this.plugin = plugin;
	}

	//  /perm [add/rem/list] [player] [permission]
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("perm.use")) {
			if (args.length == 2) {
			
				if (args[0].equalsIgnoreCase("list")) {
				
					for (User x : Main.userList) {
						if (x.getName().equalsIgnoreCase(args[1])) {
							sender.sendMessage(ChatColor.GREEN + "The user, " + ChatColor.GOLD + x.getName() + ChatColor.GREEN + ", has the following permissions");
							for (String y : x.getPermissions()) {
								sender.sendMessage(ChatColor.AQUA + "- " + y);
							}
							return true;
						}
					}
					
					sender.sendMessage(ChatColor.RED + "The user, " + ChatColor.GOLD + args[1] + ChatColor.RED + ", does not have any permissions");
					return true;
				
				} else {
					sender.sendMessage(ChatColor.RED + "Invalid Argument!");
					return false;
				}
			
			} else if (args.length == 3) { //add and remove
				if (args[0].equalsIgnoreCase("add")) {
				
					for (User x : Main.userList) {
						if (x.getName().equalsIgnoreCase(args[1])) {
						
							String[] permissions = x.getPermissions();
							String[] newPermissions = new String[permissions.length+1];
						
							for (int i = 0; i < permissions.length; i++) {
								newPermissions[i] = permissions[i];
							}
						
							newPermissions[permissions.length] = args[2];
						
							x.setPermissions(newPermissions);
						
							Player player = plugin.getServer().getPlayer(x.getUUID());
							if (player != null && player.isOnline())
								plugin.updateAttachment(player);
						
							return true;
						}
					}
					@SuppressWarnings("deprecation")
					UUID uuid = plugin.getServer().getOfflinePlayer(args[1]).getUniqueId();
					String name = plugin.getServer().getOfflinePlayer(uuid).getName();
					String[] permissions = new String[1];
					permissions[0] = args[2];
					User user = new User(uuid.toString(), name, permissions);
					Main.userList.add(user);
				
					Player player = plugin.getServer().getPlayer(user.getUUID());
					if (player != null && player.isOnline())
						plugin.updateAttachment(player);
				
					return true;
				
				} else if (args[0].equalsIgnoreCase("rem")) {
				
					//TODO Add remove		
				
				} else {
					sender.sendMessage(ChatColor.RED + "Invalid Argument!");
					return false;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Missing Arguments!");
				return false;
			}
			
		} else
			sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
		
		return true;
	}

}
