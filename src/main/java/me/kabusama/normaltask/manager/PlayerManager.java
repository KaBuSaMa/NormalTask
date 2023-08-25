package me.kabusama.normaltask.manager;

import me.kabusama.normaltask.data.PlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerManager {

    public HashMap<Player, PlayerData> data = new HashMap<>();

    public void create(Player p) {
        data.put(p, new PlayerData(p));
    }

    public void remove(Player p) {
        data.get(p).getTask().saveData();
        data.remove(p);
    }

    public PlayerData getPlayer(Player p) {
        if (!data.containsKey(p))
            create(p);
        return data.get(p);
    }

}
