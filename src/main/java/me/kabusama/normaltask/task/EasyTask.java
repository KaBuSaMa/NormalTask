package me.kabusama.normaltask.task;

import lombok.Getter;
import me.kabusama.normaltask.enums.TaskState;
import me.kabusama.normaltask.enums.TaskType;
import me.kabusama.normaltask.enums.TimerType;

@Getter
public class EasyTask {

    Task task;
    int progress;
    TaskState state = TaskState.NOT;

    public EasyTask(Task task) {
        init(task);
    }

    void init(Task task) {
        this.task = task;
    }

    public void update(int progress) {
        if (state == TaskState.NOT)
            state = TaskState.ACCEPTED;
        if (task.getGoal() == progress) {
            complete();
            return;
        }
        this.progress = this.progress+progress;
    }

    public void complete() {
        this.progress = task.getGoal();
        this.state = TaskState.COMPLETED;
    }

    @Getter
    public enum Task {
        KILL("猎杀行动", new String[]{
                "在起床战争中最终击杀15名玩家", ""
        },12, TaskType.FINAL_KILL, TimerType.DAILY, 15),
        BROKEN_BED("拆床公司", new String[]{
                "在起床战争中摧毁25张床", ""
        },14, TaskType.BED_BROKEN, TimerType.WEEKLY, 25);

        String name;
        String[] lore;
        int slot;
        TaskType taskType;
        TimerType timer;
        int goal;

        Task(String name, String[] lore, int slot, TaskType taskType, TimerType timer, int goal) {
            this.name = name;
            this.lore = lore;
            this.slot = slot;
            this.taskType = taskType;
            this.timer = timer;
            this.goal = goal;
        }

        public String getName() {
            return timer.getName() + ":" + name;
        }

    }

}
