package me.yarnulv.permission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class UserPermissionsFile {
	private final File file;
	
	public UserPermissionsFile(File file) {
		this.file = file;
	}
	
	@SuppressWarnings("resource")
	public ArrayList<User> loadPermissionsFile() {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new ArrayList<>();
		}
		
		
		ArrayList<User> userList = new ArrayList<>();
		
		try {
			
			Scanner sc = new Scanner(file);
			
			if (!sc.hasNextLine()) {
				return new ArrayList<>();
			}
			
			String uuid, name;
			String[] permissions;
			
			String line = sc.nextLine();
			while (sc.hasNextLine()) {
				
				if (line.length() == 0) { //Blank lines are ignored
					line = sc.nextLine();
					continue;
				}
				
				if (!line.substring(0,2).equalsIgnoreCase("  ") && line.charAt(line.length()-1) == ':') {
					uuid = line.substring(0,line.length()-1); // uuid
					line = sc.nextLine();
					name = line.substring(8); //name
					line = sc.nextLine();
					String str = "";
					while (line.length() != 0 && line.substring(0,4).equalsIgnoreCase("  - ")) {
						str += line;
						if (sc.hasNextLine())
							line = sc.nextLine();
						else
							break;
					}
					permissions = str.split("[ -]+");
					
					String[] permadd = new String[permissions.length-1];
					for (int i = 0; i < permadd.length; i++) {
						permadd[i] = permissions[i+1];
					}
					userList.add(new User(uuid, name, permadd));
				}
			}
			
			sc.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return userList;
		
	}
	
	public boolean savePermissionsFile(ArrayList<User> userList) {
		
		try {
			FileWriter writer = new FileWriter(file);
			String toWrite = "";
			for (int i = 0; i < userList.size(); i++) {
				User user = userList.get(i);
				
				UUID uuid = user.getUUID();
				String name = user.getName();
				String[] permissions = user.getPermissions();
				
				toWrite += uuid + ":\n  " + "name: " + name + "\n";
				for (String x : permissions) {
					toWrite += "  - " + x + "\n";
				}
				
			}
			writer.write(toWrite);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
