package me.binarybench.gameengine;

import me.binarybench.gameengine.component.player.PlayerComponent;
import me.binarybench.gameengine.component.player.SimplePlayerComponent;
import me.binarybench.gameengine.game.RotationGameComponent;
import me.binarybench.gameengine.game.games.spleef.SpleefGame;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Bench on 3/31/2016.
 */
public class SimpleArena {
    private PlayerComponent playerComponent;

    public SimpleArena(ScheduledExecutorService scheduledExecutorService)
    {
        this.playerComponent = new SimplePlayerComponent();
        new RotationGameComponent(new SpleefGame(playerComponent, scheduledExecutorService));
    }

    public PlayerComponent getPlayerComponent()
    {
        return playerComponent;
    }

}
