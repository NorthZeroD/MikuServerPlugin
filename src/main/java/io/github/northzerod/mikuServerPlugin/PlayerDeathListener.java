package io.github.northzerod.mikuServerPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Score;

public final class PlayerDeathListener implements Listener {
    @EventHandler
    public void playerDeath(PlayerDeathEvent event) {
        Score score = MikuServerPlugin.isUsedBackObj.getScore(event.getPlayer().getUniqueId().toString());
        score.setScore(0);
    }
}
