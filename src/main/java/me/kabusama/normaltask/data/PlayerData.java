package me.kabusama.normaltask.data;

import lombok.Data;
import me.kabusama.normaltask.enums.TaskState;
import me.kabusama.normaltask.task.EasyTask;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@Data
public class PlayerData {

    Player player;
    TaskData task;

    public PlayerData(Player p) {
        this.player = p;
        this.task = new TaskData(this);
    }

    public void setTask(EasyTask.Task task) {
        update(task, 0);
        player.sendMessage("§a你接受了 §6" + task.getName() + " §a任务!");
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 3, 3);
    }

    public void update(EasyTask.Task task, int progress) {
        this.task.update(task, progress);
    }

    public TaskState getState(EasyTask.Task task) {
        return this.task.getTaskState(task);
    }

    public int getProgress(EasyTask.Task task) {
        return this.task.getProgress(task);
    }

}
