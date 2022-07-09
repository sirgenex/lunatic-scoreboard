package br.com.lunaticmc.scoreboard.listener;

import br.com.lunaticmc.scoreboard.scoreboard.ScoreboardController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ScoreboardListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        ScoreboardController.getInstance().set(e.getPlayer());
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e){
        ScoreboardController.getInstance().set(e.getPlayer());
    }

}
