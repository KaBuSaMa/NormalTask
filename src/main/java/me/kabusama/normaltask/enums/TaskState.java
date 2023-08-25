package me.kabusama.normaltask.enums;

import lombok.Getter;

@Getter
public enum TaskState {
    NOT("§e点击接受这个任务"),
    ACCEPTED("§a你已经接受了这个任务!"),
    COMPLETED("§c你已经完成了这个任务!");

    String name;

    TaskState(String name) {
        this.name = name;
    }
}
