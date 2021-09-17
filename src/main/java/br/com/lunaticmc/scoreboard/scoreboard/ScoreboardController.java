package br.com.lunaticmc.scoreboard.scoreboard;

import br.com.lunaticmc.scoreboard.scoreboard.model.ScoreboardModel;
import br.com.lunaticmc.scoreboard.listener.ScoreboardListener;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreboardController {

    @Getter private static final ScoreboardController instance = new ScoreboardController();

    public Map<String, ScoreboardModel> cache = new HashMap<>();

    public void create(ScoreboardModel model) { cache.put(model.getWorld(), model); }

    public ScoreboardModel get(String world){ return cache.containsKey(world) ? cache.get(world) : cache.get("_other"); }

    public void load(Plugin plugin){
        Bukkit.getPluginManager().registerEvents(new ScoreboardListener(), plugin);
        plugin.getConfig().getConfigurationSection("worlds").getKeys(false).forEach(world -> {
            ArrayList<String> texts = new ArrayList<>();
            String name = plugin.getConfig().getString("worlds."+world+".name");
            World w = Bukkit.getWorld(world);
            if(w != null || world.equalsIgnoreCase("_other")) {
                ArrayList<String> path = new ArrayList<>(plugin.getConfig().getConfigurationSection("worlds." + world + ".scoreboard").getKeys(false));
                path.forEach(p -> {
                    if (plugin.getConfig().getString("worlds." + world + ".scoreboard." + p + ".text") != null) texts.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("worlds." + world + ".scoreboard." + p + ".text")));
                    else texts.add("spacer");
                });
                create(new ScoreboardModel(world, name, texts, path));
                plugin.getLogger().info("Loaded scoreboard at world ["+world+"] successfully!");
            }else{
                plugin.getLogger().severe("Failed to load scoreboard at world ["+world+"]: World doesn't exists!");
            }
        });
    }

    public void set(Player p){
        ScoreboardModel scoreboard = get(p.getWorld().getName());
        boolean newScore = p.getScoreboard().getObjective(p.getWorld().getName()) == null;
        Scoreboard sb = newScore ? Bukkit.getScoreboardManager().getNewScoreboard() : p.getScoreboard();
        Objective o = newScore ? sb.registerNewObjective(p.getWorld().getName(), p.getName()) : sb.getObjective(p.getWorld().getName());
        if(newScore) {
            o.setDisplayName(scoreboard.getName().replace("&", "§"));
            o.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        AtomicInteger spaces = new AtomicInteger();
        AtomicInteger teams = new AtomicInteger();
        final int[] texts = {0};
        AtomicInteger position = new AtomicInteger(scoreboard.getPath().size());
        scoreboard.getPath().forEach(path -> {
            teams.getAndIncrement();
            Score score;
            Team team = newScore ? sb.registerNewTeam(path) : sb.getTeam(path);
            StringBuilder teamBuilder = new StringBuilder();
            for(int i = 0; i < teams.get() ; i++) teamBuilder.append("§7");
            team.addEntry(teamBuilder.toString());
            if(scoreboard.getTexts().get(texts[0]).equalsIgnoreCase("spacer")){
                spaces.getAndIncrement();
                StringBuilder spaceBuilder = new StringBuilder();
                for(int i = 0 ; i < spaces.get() ; i++) spaceBuilder.append("§7");
                team.setPrefix(spaceBuilder.toString());
            }else{
                String prefix = PlaceholderAPI.setPlaceholders(p, scoreboard.getTexts().get(texts[0]))
                        .replace("{player}", p.getName());
                if(prefix.length() >= 13){
                    String suffix = ChatColor.getLastColors(prefix.substring(0, 13))+prefix.substring(13);
                    prefix = prefix.substring(0, 13);
                    team.setSuffix(suffix);
                }
                team.setPrefix(prefix);
            }
            if(newScore) {
                score = o.getScore(teamBuilder.toString());
                score.setScore(position.get());
            }
            texts[0] += 1;
            position.getAndDecrement();
        });
        if(newScore) p.setScoreboard(sb);
    }

}
