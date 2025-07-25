package io.github.northzerod.mikuServerPlugin;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public final class MikuServerAdvancement {

    public final BukkitRunnable syncer = new BukkitRunnable() {
        @Override
        public void run() {

            World world = Bukkit.getWorlds().get(0); // 主世界
            long fullTime = world.getFullTime();
            Advancement adv;

            adv = Bukkit.getAdvancement(MikuServerAdvancements.root);
            for (Player player : Bukkit.getOnlinePlayers()) {
                AdvancementProgress progress = player.getAdvancementProgress(adv);
                if (progress.isDone()) {
                    continue;
                }
                for (String criterion : progress.getRemainingCriteria()) {
                    progress.awardCriteria(criterion);
                }
            }

            // 1000 day 0 tick ~ 1101 day 0 tick
            if (fullTime >= 2400_0000 && fullTime <= 2642_4000) {
                adv = Bukkit.getAdvancement(MikuServerAdvancements.day1000);
                for (Player player : Bukkit.getOnlinePlayers()) {
                    AdvancementProgress progress = player.getAdvancementProgress(adv);
                    if (progress.isDone()) {
                        continue;
                    }
                    for (String criterion : progress.getRemainingCriteria()) {
                        progress.awardCriteria(criterion);
                    }
                }
            }

            // 1145 day 0 tick ~ 1146 day 0 tick
            if (fullTime >= 2748_0000 && fullTime <= 2750_4000) {
                adv = Bukkit.getAdvancement(MikuServerAdvancements.day1145);
                for (Player player : Bukkit.getOnlinePlayers()) {
                    AdvancementProgress progress = player.getAdvancementProgress(adv);
                    if (progress.isDone()) {
                        continue;
                    }
                    for (String criterion : progress.getRemainingCriteria()) {
                        progress.awardCriteria(criterion);
                    }
                }
            }

            // 2000 day 0 tick ~ 2101 day 0 tick
            if (fullTime >= 4800_0000 && fullTime <= 5042_4000) {
                adv = Bukkit.getAdvancement(MikuServerAdvancements.day2000);
                for (Player player : Bukkit.getOnlinePlayers()) {
                    AdvancementProgress progress = player.getAdvancementProgress(adv);
                    if (progress.isDone()) {
                        continue;
                    }
                    for (String criterion : progress.getRemainingCriteria()) {
                        progress.awardCriteria(criterion);
                    }
                }
            }
        }
    };

    public BukkitTask run(Plugin plugin, long delay, long period) {
        return syncer.runTaskTimer(plugin, delay, period);
    }
}
