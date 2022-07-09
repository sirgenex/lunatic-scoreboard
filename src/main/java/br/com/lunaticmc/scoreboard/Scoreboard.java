package br.com.lunaticmc.scoreboard;

import br.com.lunaticmc.scoreboard.listener.ScoreboardListener;
import br.com.lunaticmc.scoreboard.scoreboard.ScoreboardController;
import br.com.lunaticmc.scoreboard.task.BackgroundTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Scoreboard extends JavaPlugin {

    @Getter private static Scoreboard instance;

    @Override
    public void onEnable() {
        getLogger().info("Starting plugin...");
        instance = this;

        getLogger().info("Loading config...");
        saveDefaultConfig();
        getLogger().info("Config loaded!");

        getLogger().info("Hooking into PlaceholderAPI...");
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            getLogger().info("PlaceholderAPI hooked successfully!");
        }else {
            getLogger().info("Failed to hook into PlaceholderAPI, disabling plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("Registering listeners...");
        Bukkit.getPluginManager().registerEvents(new ScoreboardListener(), this);
        getLogger().info("Listeners registered!");

        getLogger().info("Loading scoreboards...");
        ScoreboardController.getInstance().load(this);
        getLogger().info("Scoreboards loaded!");

        getLogger().info("Registering background task...");
        new BackgroundTask().runTaskTimerAsynchronously(this, 20L, 20L);
        getLogger().info("Background task registered successfully!");

        getLogger().info("Plugin started.");
    }

}
