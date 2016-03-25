package me.binarybench.gameengine.game.events;

import me.binarybench.gameengine.component.ComponentEvent;
import me.binarybench.gameengine.game.GameComponent;
import org.bukkit.event.HandlerList;

/**
 * Created by BinaryBench on 3/19/2016.
 */
public class GameStartEvent extends ComponentEvent<GameComponent> {

    private static final HandlerList handlers = new HandlerList();

    public GameStartEvent(GameComponent component)
    {
        super(component);
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
}