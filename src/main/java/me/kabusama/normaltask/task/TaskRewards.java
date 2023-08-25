package me.kabusama.normaltask.task;

/**
 * 纯乱写，可以按自己的感觉写Rewards
 */
public class TaskRewards {

    public static String[] getRewards(EasyTask.Task task) {
        String[] str;
        switch (task) {
            case KILL:
                str = new String[]{
                        "§37,700§7大厅经验",
                        "§b250§7起床经验",
                        "§6500§7起床硬币"
                };
                break;
            case BROKEN_BED:
                str = new String[]{
                        "§315,400§7大厅经验",
                        "§b5,000§7起床经验",
                        "§610,000§7起床硬币"
                };
                break;
            default:
                str = null;
        }
        return str;
    }

}
