package net.evmodder.example.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.bukkit.ChatColor;

public class Ping extends SelfRegisteringCommand{
	private final JavaPlugin pl;

	public Ping(JavaPlugin pl){
		super(pl);
		this.pl = pl;
	}

	@Override public List<String> onTabComplete(CommandSender s, Command c, String a, String[] args){
		// Note: @null will default to a list of online player names
		return args.length <= 1 ? null : ImmutableList.of();
	}

	@Override public boolean onCommand(CommandSender sender, Command command, String label, String args[]){
		Player target;
		if(args.length > 0) target = pl.getServer().getPlayer(args[0]);
		else if(sender instanceof Player) target = (Player) sender;
		else{
			sender.sendMessage(ChatColor.RED+"Too few arguments!");
			return false;
		}
		if(target == null) sender.sendMessage(ChatColor.RED+"Could not find the specified player!");
		else if(sender.getName().equals(target.getName())) sender.sendMessage(ChatColor.GOLD+"Your ping: "+ChatColor.GREEN+target.getPing());
		else sender.sendMessage(target.getDisplayName()+ChatColor.GOLD+"'s ping: "+ChatColor.GREEN+target.getPing());
		return true;
	}
}