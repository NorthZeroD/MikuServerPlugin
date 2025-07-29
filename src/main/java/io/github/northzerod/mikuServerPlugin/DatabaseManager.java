package io.github.northzerod.mikuServerPlugin;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

import static io.github.northzerod.mikuServerPlugin.MikuServerPlugin.LOGGER;

public class DatabaseManager {
    private static final HikariDataSource dataSource;

    static {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:" + MikuServerPlugin.getInstance().getDataFolder().getAbsolutePath() + "/database.db");
        config.setDriverClassName("org.sqlite.JDBC");

        // SQLite 特定的优化配置
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    public static void createStatement(String sql) {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            LOGGER.info("数据库初始化完成");
        } catch (SQLException e) {
            LOGGER.severe("数据库初始化失败: " + e.getMessage());
        }
    }

    public static void executeUpdate(String sql, Object... params) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("数据库操作失败", e);
        }
    }

    public static <T> T query(String sql, ResultSetHandler<T> handler, Object... params) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                return handler.handle(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("数据库查询失败", e);
        }
    }

    @FunctionalInterface
    public interface ResultSetHandler<T> {
        T handle(ResultSet rs) throws SQLException;
    }
}
