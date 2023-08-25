package me.kabusama.normaltask.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;

import java.sql.Connection;

public class Database {

    HikariDataSource source;
    String[] info = new String[]{
            "task-hikari", "jdbc:mysql://%s/%s", "userName", "password"
    };

    public Database() {
        init();
    }

    void init() {
        HikariConfig config = new HikariConfig();
        config.setPoolName(info[0]);
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl(String.format(info[1], "host:port", "database"));
        config.setUsername(info[3]);
        config.setPassword(info[4]);
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(5000);
        this.source = new HikariDataSource(config);
    }

    @SneakyThrows
    public Connection getConnection() {
        return this.source.getConnection();
    }

}
