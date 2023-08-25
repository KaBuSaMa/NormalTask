package me.kabusama.normaltask.listener;

import me.kabusama.normaltask.NormalTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        NormalTask.getInstance().getPlayerManager().create(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        NormalTask.getInstance().getPlayerManager().remove(e.getPlayer());
    }

}
