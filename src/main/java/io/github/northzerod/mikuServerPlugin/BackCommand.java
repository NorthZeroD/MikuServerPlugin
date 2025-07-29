package io.github.northzerod.mikuServerPlugin;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class BackCommand {
    public static LiteralCommandNode<CommandSourceStack> getNode() {
        LiteralArgumentBuilder<CommandSourceStack> back = Commands.literal("back")
                .executes(
                        ctx -> {
                            if (ctx.getSource().getExecutor() instanceof Player player) {
                                String uuid = player.getUniqueId().toString();
                                Location lastDeathLocation = player.getLastDeathLocation();
                                int isUsedBack = 0;
                                isUsedBack = DatabaseManager.query(
                                        "SELECT value FROM is_used_back WHERE uuid = ?",
                                        rs -> {
                                            if (rs.next()) {
                                                return rs.getInt("value");
                                            }
                                            return 1;
                                        },
                                        uuid
                                );


                                if (isUsedBack == 1) {
                                    player.sendRichMessage("<gold>你已经回到过上次死亡地点了");
                                    return Command.SINGLE_SUCCESS;
                                }

                                if (lastDeathLocation == null) {
                                    player.sendRichMessage("<red>执行失败: lastDeathLocation == null");
                                    return Command.SINGLE_SUCCESS;
                                }

                                player.teleport(lastDeathLocation);
                                DatabaseManager.executeUpdate("UPDATE is_used_back SET value = 1 WHERE uuid = ?", uuid);
                                player.sendRichMessage("<green>你已回到上次死亡地点");
                                return Command.SINGLE_SUCCESS;
                            }

                            ctx.getSource().getSender().sendPlainMessage("执行失败: 此命令需要一个玩家");
                            return Command.SINGLE_SUCCESS;
                        }
                )
                .requires(sender ->
                        sender.getSender().isOp() || sender.getSender().hasPermission("mikuserverplugin.command.back")
                );

        LiteralArgumentBuilder<CommandSourceStack> info = Commands.literal("info")
                .executes(
                        ctx -> {
                            if (ctx.getSource().getExecutor() instanceof Player player) {
                                Location lastDeathLocation = player.getLastDeathLocation();

                                if (lastDeathLocation == null) {
                                    player.sendRichMessage("<red>执行失败: lastDeathLocation == null");
                                    return Command.SINGLE_SUCCESS;
                                }

                                player.sendRichMessage("<green>" + player.getLastDeathLocation().toString());
                                return Command.SINGLE_SUCCESS;
                            }

                            ctx.getSource().getSender().sendPlainMessage("执行失败: 此命令需要一个玩家");
                            return Command.SINGLE_SUCCESS;
                        }
                )
                .requires(sender ->
                        sender.getSender().isOp() || sender.getSender().hasPermission("mikuserverplugin.command.back.info")
                );

        back.then(info);
        return back.build();
    }
}
