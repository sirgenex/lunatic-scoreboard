package br.com.lunaticmc.scoreboard.task;

import br.com.lunaticmc.scoreboard.scoreboard.ScoreboardController;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class BackgroundTask extends BukkitRunnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> ScoreboardController.getInstance().set(player));
    }

}
