package me.binarybench.gameengine.game.gamestate;

import me.binarybench.gameengine.component.BaseComponent;
import me.binarybench.gameengine.game.events.GameStartEvent;
import me.binarybench.gameengine.Main;
import me.binarybench.gameengine.game.GameComponent;
import me.binarybench.gameengine.game.events.GameEndEvent;
import me.binarybench.gameengine.game.gamestate.events.GameStateChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * Created by BinaryBench on 3/20/2016.
 */
public class GameStateComponent extends BaseComponent implements Listener {

    private GameState gameState = GameState.RESTARTING;

    private Runnable onEnd;

    public GameStateComponent(Runnable onEnd)
    {
        this.onEnd = onEnd;
    }

    @Override
    public void onEnable()
    {
        this.setGameState(GameState.LOBBY);
    }

    @Override
    public void onDisable()
    {
        this.privateSetGameState(GameState.RESTARTING);
    }

    //Listeners
    @EventHandler
    public void onStart(GameStartEvent event)
    {

        this.setGameState(GameState.LOBBY);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEnd(GameEndEvent event)
    {
        privateSetGameState(GameState.RESTARTING);
    }


    public GameState getGameState()
    {
        return gameState;
    }

    public boolean setGameState(GameState toGameState)
    {
        if (toGameState == GameState.RESTARTING)
        {
            this.onEnd.run();
            return true;
        }
        return privateSetGameState(toGameState);
    }

    private boolean privateSetGameState(GameState toGameState)
    {
        if (toGameState == this.gameState)
            return false;

        GameState fromGameState = getGameState();

        this.gameState = toGameState;

        GameStateChangeEvent event = new GameStateChangeEvent(this, fromGameState, toGameState);

        Bukkit.getPluginManager().callEvent(event);
        return true;
    }

    public Runnable getRunnable(GameState toSet)
    {
        return () -> {setGameState(toSet);};
    }
}
