package me.binarybench.gameengine.common.playerholder.events;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by BinaryBench on 3/20/2016.
 */
public class PlayerRemoveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private PlayerHolder playerHolder;
    private Player player;

    public PlayerRemoveEvent(PlayerHolder playerHolder, Player player)
    {
        this.playerHolder = playerHolder;
        this.player = player;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    public PlayerHolder getPlayerHolder()
    {
        return this.playerHolder;
    }

    public Player getPlayer()
    {
        return player;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }


}
