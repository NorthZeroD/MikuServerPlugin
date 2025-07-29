package io.github.northzerod.mikuServerPlugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public final class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String name = player.getName();

        DatabaseManager.executeUpdate("INSERT OR REPLACE INTO is_used_back (uuid, value) VALUES (?, 0)", uuid);

        MikuServerPlugin.LOGGER.info(name + "(" + uuid + ") 死亡在: " + player.getLocation());

    }
}
