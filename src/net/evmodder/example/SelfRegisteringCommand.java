package net.evmodder.example;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SelfRegisteringCommand implements TabExecutor{

	public SelfRegisteringCommand(JavaPlugin pl){
		final String name = getClass().getSimpleName().substring(7).toLowerCase();// "CommandPing" -> "Ping"
		final PluginCommand command = pl.getCommand(name);
		command.setExecutor(this);
		command.setTabCompleter(this);
	}

	@Override
	abstract public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args);

	@Override
	abstract public boolean onCommand(CommandSender sender, Command command, String label, String args[]);
}