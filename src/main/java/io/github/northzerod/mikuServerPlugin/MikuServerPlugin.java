package io.github.northzerod.mikuServerPlugin;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class MikuServerPlugin extends JavaPlugin {
    public static Logger LOGGER;
    private final String dbPath = getDataFolder().getAbsolutePath() + "/database.db";

    @Override
    public void onEnable() {
        LOGGER = this.getLogger();
        this.saveDefaultConfig();
//        initializeDatabase();

        DatabaseManager.createStatement(
                "CREATE TABLE IF NOT EXISTS is_used_back ("
                + "uuid TEXT PRIMARY KEY, "
                + "value INTEGER"
                + ");"
        );

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            // 命令注册
            commands.registrar().register(BackCommand.getNode());
            commands.registrar().register(GmCommand.getNode());
        });

        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);

        this.getLogger().info("MikuServerPlugin 已启用");
    }

    @Override
    public void onDisable() {
        LOGGER.info("MikuServerPlugin 已禁用");
    }

    public static MikuServerPlugin getInstance() {
        return (MikuServerPlugin) Bukkit.getPluginManager().getPlugin("MikuServerPlugin");
    }

//    private void initializeDatabase() {
//        try (Connection conn = getConnection();
//             Statement stmt = conn.createStatement()) {
//
//            stmt.execute(
//                    "CREATE TABLE IF NOT EXISTS players ("
//                            + "uuid TEXT PRIMARY KEY, "
//                            + "username TEXT NOT NULL, "
//                            + "is_used_back INTEGER"
//                            + ");"
//            );
//
//            getLogger().info("数据库初始化完成");
//        } catch (SQLException e) {
//            getLogger().severe("数据库初始化失败: " + e.getMessage());
//        }
//    }
//
//    public Connection getConnection() throws SQLException {
//        return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
//    }
}
