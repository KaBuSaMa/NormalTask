package me.kabusama.normaltask.enums;

import lombok.Getter;

@Getter
public enum TimerType {
    DAILY("每日任务", 24L),
    WEEKLY("每周任务", 7*24L);

    String name;
    long timer;

    TimerType(String name, long timer) {
        this.name = name;
        this.timer = timer*3600L;
    }

}
