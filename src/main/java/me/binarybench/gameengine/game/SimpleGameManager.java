package me.binarybench.gameengine.game;

import me.binarybench.gameengine.common.utils.ServerUtil;
import me.binarybench.gameengine.component.Component;
import me.binarybench.gameengine.component.player.PlayerComponent;
import me.binarybench.gameengine.game.games.runner.RunnerGame;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Bench on 4/15/2016.
 */
public class SimpleGameManager {

    private Component game;

    public SimpleGameManager(PlayerComponent playerComponent, ScheduledExecutorService scheduledExecutorService)
    {
        game = new RunnerGame(playerComponent, scheduledExecutorService, this::endGame);
        game.enable();
    }


    public void endGame()
    {
        game.disable();
        ServerUtil.shutdown("bye bye!");
    }
}
