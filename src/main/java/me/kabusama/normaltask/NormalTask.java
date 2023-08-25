package me.kabusama.normaltask;

import lombok.Getter;
import lombok.SneakyThrows;
import me.kabusama.normaltask.database.Database;
import me.kabusama.normaltask.manager.PlayerManager;
import me.kabusama.normaltask.task.TaskInventory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author KaBuSaMa
 */
public final class NormalTask extends JavaPlugin {

    @Getter
    private static NormalTask instance;
    @Getter
    private PlayerManager playerManager;
    private Database dataDase;

    @Override
    public void onEnable() {
        instance = this;
        init();

        getCommand("task-gui").setExecutor((sender, command, s, args) -> {
            if (!(sender instanceof Player)) return true;
            TaskInventory task = new TaskInventory((Player) sender);
            task.open();
            return true;
        });

        getLogger().info("NormalTask enabled!");
    }

    void init() {
        playerManager = new PlayerManager();
        dataDase = new Database();
        createTable(String.format("%s INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "%s VARCHAR(64) CHARACTER SET utf8, " +
                "%s VARCHAR(16) CHARACTER SET utf8, %s TEXT"
                , "id", "uuid", "name", "task")); // id, uuid, name, task
    }

    @SneakyThrows
    void createTable(String value) {
        Connection conn = dataDase.getConnection();
        PreparedStatement pre = conn.prepareStatement("CREATE TABLE IF NOT EXISTS normal_task(" + value + ");");
        pre.executeUpdate();
        pre.close();
        conn.close();
    }

    public Database getDataDase() {
        return dataDase;
    }

    @Override
    public void onDisable() {
        instance = this;
        getLogger().info("NormalTask disabled!");
    }
}
