package net.evmodder.example;

import java.io.InputStream;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import net.evmodder.example.listeners.*;
import net.evmodder.example.commands.*;

/**
* @author EvModder (evdoc at altcraft.net)
*/
public class ExamplePlugin extends JavaPlugin{

	//------------------ NOTE: I usually move config stuff into it's own class/interface ------------------
	private FileConfiguration config;
	@Override public FileConfiguration getConfig(){return config;}
	@Override public void saveConfig(){
		if(config != null && !FileIO.saveConfig(this, "config-"+getName()+".yml", config)){
			getLogger().severe("Error while saving plugin configuration file!");
		}
	}
	@Override public void reloadConfig(){
		final InputStream defaultConfig = getClass().getResourceAsStream("/config.yml");
		if(defaultConfig != null){
			config = FileIO.loadConfig(this, "config-"+getName()+".yml", defaultConfig, /*notifyIfNew=*/true);
		}
	}
	//-------------------------------------------------------------------------------------------------------

	@Override public final void onEnable(){
		reloadConfig();

		// Quick and dirty way to register a temp listener
		getServer().getPluginManager().registerEvents(new Listener(){
			@EventHandler public void onPlayerJoin(PlayerJoinEvent evt){
				evt.setJoinMessage("first");
				HandlerList.unregisterAll(this);
			}
		}, this);

		// "Proper" way. If I have LOTS of listeners or cmds to register I separate listener/cmd registration into their own functions
		final String rageQuitMsg = config.getString("rage-quit-message", "${NAME} ragequit the server");
		getServer().getPluginManager().registerEvents(new RageQuitListener(rageQuitMsg), this);

		// I personally like doing commands this way
		new CommandPing(this);
		new CommandYourMother(this);
	}

	@Override public final void onDisable(){
		getLogger().info("gasdghla;skdgjlkashdglkahsdgasgd");
	}
}