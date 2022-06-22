package net.evmodder.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class FileIO{
	public static YamlConfiguration loadConfig(JavaPlugin pl, String configName, InputStream defaultConfig, boolean notifyIfNew){
		if(!configName.endsWith(".yml")){
			pl.getLogger().severe("Invalid config file!");
			pl.getLogger().severe("Configuation files must end in .yml");
			return null;
		}
		File file = new File("./plugins/"+pl.getName()+"/"+configName);
		if(!file.exists() && defaultConfig != null){
			try{
				//Create Directory
				File dir = new File("./plugins/"+pl.getName()+"/");
				if(!dir.exists())dir.mkdir();

				//Read contents of defaultConfig
				BufferedReader reader = new BufferedReader(new InputStreamReader(defaultConfig));
				String line = reader.readLine();
				StringBuilder builder = new StringBuilder(line);
				while((line = reader.readLine()) != null) builder.append('\n').append(line);
				reader.close();

				//Create new config from contents of defaultConfig
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				writer.write(builder.toString()); writer.close();
			}
			catch(IOException ex){
				pl.getLogger().severe(ex.getStackTrace().toString());
				pl.getLogger().severe("Unable to locate a default config!");
				pl.getLogger().severe("Could not find /config.yml in plugin's .jar");
			}
			if(notifyIfNew) pl.getLogger().info("Could not locate configuration file!");
			if(notifyIfNew) pl.getLogger().info("Generating a new one with default settings.");
		}
		return YamlConfiguration.loadConfiguration(file);
	}
	public static boolean saveConfig(JavaPlugin pl, String configName, FileConfiguration config){
		final String dirName = "./plugins/"+pl.getName()+"/";
		try{
			if(!new File(dirName).exists()) new File(dirName).mkdir();
			config.save(dirName+configName);
		}
		catch(IOException ex){ex.printStackTrace(); return false;}
		return true;
	}
}