package me.binarybench.gameengine.game.gamestate.events;

import me.binarybench.gameengine.game.gamestate.GameState;
import me.binarybench.gameengine.game.gamestate.GameStateManager;
import me.binarybench.gameengine.component.ComponentEvent;
import org.bukkit.event.HandlerList;

/**
 * Created by BinaryBench on 3/20/2016.
 */
public class GameStateChangeEvent extends ComponentEvent<GameStateManager> {

    private static final HandlerList handlers = new HandlerList();
    private GameState fromGameState;
    private GameState toGameState;

    public GameStateChangeEvent(GameStateManager component, GameState fromGameState, GameState toGameState)
    {
        super(component);
        this.fromGameState = fromGameState;
        this.toGameState = toGameState;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    public GameState getFromGameState()
    {
        return fromGameState;
    }

    public GameState getToGameState()
    {
        return toGameState;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }
}
