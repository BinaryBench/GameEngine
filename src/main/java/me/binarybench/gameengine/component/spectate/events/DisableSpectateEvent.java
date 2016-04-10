package me.binarybench.gameengine.component.spectate.events;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.common.playerholder.events.PlayerRemoveEvent;
import me.binarybench.gameengine.component.spectate.SpectateComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;

/**
 * Created by BinaryBench on 3/20/2016.
 */
public class DisableSpectateEvent extends Event {

    private SpectateComponent spectateComponent;
    private Player player;

    public DisableSpectateEvent(SpectateComponent spectateComponent, Player player)
    {
        this.spectateComponent = spectateComponent;
        this.player = player;
    }

    public SpectateComponent getSpectateComponent()
    {
        return spectateComponent;
    }

    public Player getPlayer()
    {
        return player;
    }

    private static final HandlerList handlers = new HandlerList();
    public static HandlerList getHandlerList()
    {
        return handlers;
    }
    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }
}
