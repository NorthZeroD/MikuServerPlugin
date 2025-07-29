package io.github.northzerod.mikuServerPlugin;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public final class GmCommand {
    public static LiteralCommandNode<CommandSourceStack> getNode() {
        return Commands.literal("gm")
                .then(Commands.argument("gamemode", ArgumentTypes.gameMode())
                        .executes(ctx -> {
                            final GameMode gamemode = ctx.getArgument("gamemode", GameMode.class);

                            if (ctx.getSource().getExecutor() instanceof Player player) {
                                player.setGameMode(gamemode);
                                player.sendRichMessage("<green>你的游戏模式已被设置为 <gamemode>",
                                        Placeholder.component("gamemode", Component.translatable(gamemode))
                                );
                                return Command.SINGLE_SUCCESS;
                            }

                            ctx.getSource().getSender().sendPlainMessage("执行失败: 此命令需要一个玩家");
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .requires(sender ->
                        sender.getSender().isOp() || sender.getSender().hasPermission("mikuserverplugin.command.gm")
                )
                .build();
    }
}
