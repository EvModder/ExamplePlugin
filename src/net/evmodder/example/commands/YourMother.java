package net.evmodder.example.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.ChatColor;

public class YourMother extends SelfRegisteringCommand{
	private final JavaPlugin pl;

	public YourMother(JavaPlugin pl){
		super(pl);
		this.pl = pl;
	}

	@Override public List<String> onTabComplete(CommandSender s, Command c, String a, String[] args){
		// Note: @null will default to a list of online player names
		if(args.length <= 1) return null;
		if(args.length == 2) return Stream.of("420", "69").filter(num -> num.startsWith(args[1])).collect(Collectors.toList());
		return ImmutableList.of();
	}

	@Override public boolean onCommand(CommandSender sender, Command command, String label, String args[]){
		final String target;
		if(args.length > 0){
			final Player targetPlayer = pl.getServer().getPlayer(args[0]);
			if(targetPlayer != null) target = targetPlayer.getName();
			else{
				sender.sendMessage(ChatColor.RED+"Could not find the specified player");
				return false;
			}
		}
		else target = sender.getName();

		final boolean theirOwnMom = target.equals(sender.getName());

		if(theirOwnMom && !sender.hasPermission("example.yourmother.self")){
			sender.sendMessage(ChatColor.RED+"You do not have permission to do your own mom");
			return true;
		}
		if(!theirOwnMom && !sender.hasPermission("example.yourmother.others")){
			sender.sendMessage(ChatColor.RED+"You do not have permission to do other people's mothers");
			return true;
		}

		int count = 1;
		if(args.length > 1){
			try{count = Integer.parseInt(args[1]);}
			catch(NumberFormatException e){}
		}

		if(count != 1 && !sender.hasPermission("example.yourmother.multiple")){
			sender.sendMessage(ChatColor.RED+"You do not have the stamina to do a mom multiple times in a single command");
			return true;
		}

		final String whose = theirOwnMom ? "their" : target+"'s";
		final String countStr = count == 1 ? "" : " "+count+" times";
		pl.getServer().broadcastMessage(sender.getName()+" did "+whose+" mom"+countStr+".");
		return true;
	}
}