package me.kabusama.normaltask.task;

import me.kabusama.normaltask.NormalTask;
import me.kabusama.normaltask.data.PlayerData;
import me.kabusama.normaltask.enums.TaskState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TaskInventory {

    Inventory inv;
    PlayerData pd;

    public TaskInventory(Player player) {
        inv = Bukkit.createInventory(null, 6*9, "§8任务大师");
        this.pd = NormalTask.getInstance().getPlayerManager().getPlayer(player);
    }

    public void open() {
        Arrays.stream(EasyTask.Task.values()).forEach(task -> {
            inv.setItem(task.getSlot(), getItem(task));
        });
        ItemStack is = new ItemStack(Material.BARRIER);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§c关闭");
        is.setItemMeta(im);
        inv.setItem(49, is);
    }

    ItemStack getItem(EasyTask.Task task) {
        ItemStack is = new ItemStack(Material.PAPER);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§a" + task.getName());
        String[] loreStr = task.getLore();
        TaskState state = pd.getState(task);
        if (state == TaskState.ACCEPTED)
            loreStr[0] = loreStr[0] + " §e(§b" + pd.getProgress(task) + "§e/" + task.getGoal() + "§e)"; //
        List<String> lore = Arrays.stream(loreStr).collect(Collectors.toList());
        String[] rewards = TaskRewards.getRewards(task);
        for (String reward : rewards) lore.add("§8" + reward);
        lore.add("");
        lore.add(state.getName());
        if (state != TaskState.NOT) {
            is.addEnchantment(Enchantment.KNOCKBACK, 1);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else
            is.removeEnchantment(Enchantment.KNOCKBACK);
        is.setItemMeta(im);
        return is;
    }

}
