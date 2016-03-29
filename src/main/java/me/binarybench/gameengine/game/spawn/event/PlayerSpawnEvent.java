package me.binarybench.gameengine.game.spawn.event;

import me.binarybench.gameengine.game.spawn.SpawnManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


/**
 * Created by Bench on 3/29/2016.
 */
public class PlayerSpawnEvent extends Event {

    private SpawnManager spawnManager;
    private Player player;

    private Location from;
    private Location to;


    public PlayerSpawnEvent(SpawnManager spawnManager, Player player, Location to)
    {
        this(spawnManager, player, player.getLocation(), to);
    }

    public PlayerSpawnEvent(SpawnManager spawnManager, Player player, Location from, Location to)
    {
        this.to = to;
        this.from = from;
        this.player = player;
        this.spawnManager = spawnManager;
    }

    public SpawnManager getSpawnManager()
    {
        return spawnManager;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Location getFrom()
    {
        return from;
    }

    public Location getTo()
    {
        return to;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }
    private static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

}
