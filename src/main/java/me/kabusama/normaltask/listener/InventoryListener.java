package me.kabusama.normaltask.listener;

import me.kabusama.normaltask.NormalTask;
import me.kabusama.normaltask.data.PlayerData;
import me.kabusama.normaltask.task.EasyTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player) && e.getClickedInventory() == null && e.getCurrentItem() == null)
            return;
        if (!e.getClickedInventory().getTitle().equals("§8任务大师")) return;
        e.setCancelled(true);
        Inventory click = e.getClickedInventory();
        PlayerData pd = NormalTask.getInstance().getPlayerManager().getPlayer((Player) e.getWhoClicked());
        switch (e.getCurrentItem().getItemMeta().getDisplayName()) {
            case "§a每日任务:猎杀行动": {
                pd.setTask(EasyTask.Task.KILL);
                pd.getPlayer().openInventory(click);
                break;
            }
            case "§a每周任务:拆床公司": {
                pd.setTask(EasyTask.Task.BROKEN_BED);
                pd.getPlayer().openInventory(click);
                break;
            }
            case "§c关闭": {
                pd.getPlayer().closeInventory();
                break;
            }
        }

    }

}
