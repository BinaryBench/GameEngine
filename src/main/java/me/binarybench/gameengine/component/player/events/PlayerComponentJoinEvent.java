package me.binarybench.gameengine.component.player.events;

import me.binarybench.gameengine.component.player.PlayerComponent;
import me.binarybench.gameengine.common.playerholder.events.PlayerAddEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Created by BinaryBench on 3/17/2016.
 */
public class PlayerComponentJoinEvent extends PlayerAddEvent {

    private static final HandlerList handlers = new HandlerList();

    public PlayerComponentJoinEvent(PlayerComponent playerComponent, Player player)
    {
        super(playerComponent, player);
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    @Override
    public PlayerComponent getPlayerHolder()
    {
        return (PlayerComponent) super.getPlayerHolder();
    }
}
