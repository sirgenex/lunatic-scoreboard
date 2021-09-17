package br.com.lunaticmc.scoreboard.listener;

import br.com.lunaticmc.blocks.event.BlockChangeEvent;
import br.com.lunaticmc.economy.event.BalanceChangeEvent;
import br.com.lunaticmc.rankup.event.RankChangeEvent;
import br.com.lunaticmc.scoreboard.scoreboard.ScoreboardController;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBalanceChange(BalanceChangeEvent e){
        Player p = Bukkit.getPlayer(e.getPlayer());
        if(p == null) return;
        ScoreboardController.getInstance().set(p);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockChange(BlockChangeEvent e){
        Player p = Bukkit.getPlayer(e.getPlayer());
        if(p == null) return;
        ScoreboardController.getInstance().set(p);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRankChange(RankChangeEvent e){
        Player p = Bukkit.getPlayer(e.getPlayer());
        if(p == null) return;
        ScoreboardController.getInstance().set(p);
    }

}
