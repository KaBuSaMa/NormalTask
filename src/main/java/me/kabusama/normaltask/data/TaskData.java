package me.kabusama.normaltask.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.SneakyThrows;
import me.kabusama.normaltask.NormalTask;
import me.kabusama.normaltask.enums.TaskState;
import me.kabusama.normaltask.task.EasyTask;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;

@Data
public class TaskData {

    Player player;
    HashMap<EasyTask.Task, EasyTask> tasks;

    public TaskData(PlayerData pd) {
        this.player = pd.getPlayer();
        if (hasData()) {
            tasks = new HashMap<>();
            Arrays.stream(EasyTask.Task.values()).forEach(this::addTask);
            initData();
        }
        loadData();
    }

    public int getProgress(EasyTask.Task task) {
        return tasks.get(task).getProgress();
    }

    public TaskState getTaskState(EasyTask.Task task) {
        return tasks.get(task).getState();
    }

    public void addTask(EasyTask.Task task) {
        tasks.put(task, new EasyTask(task));
    }

    public void update(EasyTask.Task task, int progress) {
        tasks.get(task).update(progress);
    }
    public void remTask(EasyTask.Task task) {
        tasks.remove(task);
        new BukkitRunnable() {
            @Override
            public void run() {
                tasks.put(task, new EasyTask(task));
            }
        }.runTaskLater(NormalTask.getInstance(),2L);
    }

    // SQL
    @SneakyThrows
    public void initData() {
        Gson gson = new GsonBuilder().create();
        String info = gson.toJson(tasks);
        System.out.println(info);
        Connection conn = NormalTask.getInstance().getDataDase().getConnection();
        PreparedStatement pre = conn.prepareStatement(String.format("INSERT INTO %s (%s) VALUES (%s);",
                "normal_task", "uuid, name, task", player.getUniqueId().toString() + ", " + player.getName() + ", " + info)
        );
        pre.executeUpdate();
        pre.close();
        conn.close();
    }

    @SneakyThrows
    public boolean hasData() {
        Connection conn = NormalTask.getInstance().getDataDase().getConnection();
        return conn.prepareStatement(
                String.format("SELECT * FROM %s WHERE %s = '%s';",
                        "normal_task", "uuid", player.getUniqueId().toString())
        ).execute();
    }

    @SneakyThrows
    public void loadData() {
        Gson gson = new GsonBuilder().create();
        Connection conn = NormalTask.getInstance().getDataDase().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM %s WHERE %s = '%s';",
                "normal_task", "uuid", player.getUniqueId().toString()));
        if (rs.next()) {
            String info = rs.getString("task");
            HashMap<EasyTask.Task, EasyTask> tasks = gson.fromJson(info, new TypeToken<HashMap<EasyTask.Task, EasyTask>>(){}.getType());
            this.tasks = tasks;
            check();
        }
    }

    @SneakyThrows
    public void saveData() {
        Gson gson = new GsonBuilder().create();
        String info = gson.toJson(tasks);
        System.out.println(info);
        Connection conn = NormalTask.getInstance().getDataDase().getConnection();
        PreparedStatement pre = conn.prepareStatement(String.format("UPDATE %s SET %s = %s WHERE %s = '%s';",
                "normal_task", "task", info, "uuid", player.getUniqueId().toString())
        );
        pre.executeUpdate();
        pre.close();
        conn.close();
    }

    void check() {
        Arrays.stream(EasyTask.Task.values()).forEach(task -> {
            if (inTimer(task)) {
                remTask(task);
            }
        });
    }

    boolean inTimer(EasyTask.Task task) {
        return System.currentTimeMillis()<getTimer(task);
    }

    long getTimer(EasyTask.Task task) {
        return task.getTimer().getTimer()*1000L;
    }

}
