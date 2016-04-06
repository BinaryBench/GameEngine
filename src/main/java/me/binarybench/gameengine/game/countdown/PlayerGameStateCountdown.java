package me.binarybench.gameengine.game.countdown;


import me.binarybench.gameengine.Main;
import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.common.playerholder.events.PlayerAddEvent;
import me.binarybench.gameengine.common.playerholder.events.PlayerRemoveEvent;
import me.binarybench.gameengine.game.gamestate.GameState;
import me.binarybench.gameengine.game.gamestate.GameStateManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Bench on 3/31/2016.
 */
public class PlayerGameStateCountdown extends GameStateCountdown implements Listener {

    private int startThreshold;
    private int stopThreshold;

    public PlayerGameStateCountdown(ScheduledExecutorService scheduler, int startTime, GameStateManager gameStateManager, GameState gameStateToSet, PlayerHolder playerHolder, int startThreshold, int stopThreshold)
    {
        super(scheduler, startTime, gameStateManager, gameStateToSet, playerHolder);
        this.startThreshold = startThreshold;
        this.stopThreshold = stopThreshold;
    }

    public void checkCountdown()
    {
        this.checkCountdown(0);
    }

    public void checkCountdown(int offset)
    {
        int playerCount = getPlayerHolder().getPlayers().size() + offset;


        if (playerCount >= this.startThreshold)
        {
            if (!isRunning())
                restart();
        }
        else if (playerCount < this.stopThreshold)
        {
            if (isRunning())
                stop();
        }

    }

    @EventHandler
    public void playerAdd(PlayerAddEvent event)
    {
        if (event.getPlayerHolder() != getPlayerHolder())
            return;
        checkCountdown(1);
    }

    @EventHandler
    public void playerRemove(PlayerRemoveEvent event)
    {
        if (event.getPlayerHolder() != getPlayerHolder())
            return;
        checkCountdown(-1);
    }

    @Override
    public void onEnable()
    {
        Main.registerEvents(this);
        this.checkCountdown();
    }

    @Override
    public void onDisable()
    {
        Main.unregisterEvents(this);
        super.onDisable();
    }
}
