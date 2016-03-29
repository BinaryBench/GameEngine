package me.binarybench.gameengine.game.spawn;

import me.binarybench.gameengine.common.utils.PlayerUtil;
import me.binarybench.gameengine.game.spawn.event.PlayerSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Bench on 3/29/2016.
 */
public interface SpawnManager {

    Location getSpawn(Player player);

    default void respawn(Iterable<Player> players)
    {
        players.forEach(this::respawn);
    }

    default Location respawn(Player player)
    {
        return respawn(new PlayerSpawnEvent(this, player, getSpawn(player)));
    }

    default Location respawn(PlayerSpawnEvent event)
    {
        Player player = event.getPlayer();

        PlayerUtil.resetPlayer(event.getPlayer());

        player.teleport(event.getTo());

        Bukkit.getPluginManager().callEvent(event);

        return event.getTo();
    }

}
