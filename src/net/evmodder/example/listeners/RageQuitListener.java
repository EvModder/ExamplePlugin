package net.evmodder.example.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class RageQuitListener implements Listener{
	final String RAGE_QUIT_MSG;

	public RageQuitListener(String rageQuitMsg){
		RAGE_QUIT_MSG = rageQuitMsg;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent evt){
//		if(evt.getPlayer().isDead() && evt.getPlayer().getGameMode() != GameMode.SPECTATOR){ // spectators might be dead by default i think
		if(evt.getPlayer().isDead()){
//			evt.setQuitMessage(RAGE_QUIT_MSG.replaceAll("(?i)\\$\\{NAME\\}", evt.getPlayer().getName())); // case-insensitive replace
			evt.setQuitMessage(RAGE_QUIT_MSG.replace("${NAME}", evt.getPlayer().getName()));
		}
	}
}
