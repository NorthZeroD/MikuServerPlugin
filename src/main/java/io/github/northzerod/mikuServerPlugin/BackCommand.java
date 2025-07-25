package io.github.northzerod.mikuServerPlugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;
import org.jetbrains.annotations.NotNull;

public final class BackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 0) {
            if (sender instanceof Player player) {
                if (player.getLastDeathLocation() != null) {
                    Score score = MikuServerPlugin.isUsedBackObj.getScore(player.getUniqueId().toString());

                    if (score.getScore() == 1) {
                        player.sendMessage(Component.text("你已经返回过死亡点了!", NamedTextColor.YELLOW));
                        return true;
                    }

                    // 成功执行
                    player.teleport(player.getLastDeathLocation());
                    score.setScore(1);
                    return true;
                }
                player.sendMessage(Component.text("执行失败, player.getLastDeathLocation() == null", NamedTextColor.RED));
                return true;
            }
            sender.sendMessage(Component.text("执行失败, 命令发送者不是玩家", NamedTextColor.RED));
            return true;
        }
        sender.sendMessage(Component.text("执行失败, 语法错误", NamedTextColor.RED));
        return false;
    }
}
