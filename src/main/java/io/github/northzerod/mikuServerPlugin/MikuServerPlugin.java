package io.github.northzerod.mikuServerPlugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public final class MikuServerPlugin extends JavaPlugin {
    public static Scoreboard isUsedBack;
    public static Objective isUsedBackObj;

    @Override
    public void onEnable() {
        isUsedBack = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        isUsedBackObj = isUsedBack.registerNewObjective("isUsedBack", "dummy");

        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        this.getCommand("back").setExecutor(new BackCommand());

        MikuServerAdvancement mikuServerAdvancement = new MikuServerAdvancement();
        mikuServerAdvancement.run(this, 0, 20);
        this.getLogger().info("MikuServerPlugin 已启用");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MikuServerPlugin getInstance() {
        return (MikuServerPlugin) Bukkit.getPluginManager().getPlugin("MikuServerPlugin");
    }
}
