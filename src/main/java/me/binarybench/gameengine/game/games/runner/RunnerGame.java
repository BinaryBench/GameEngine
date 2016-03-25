package me.binarybench.gameengine.game.games.runner;

import me.binarybench.gameengine.component.player.PlayerComponent;
import me.binarybench.gameengine.game.Game;
import me.binarybench.gameengine.game.GameComponent;
import me.binarybench.gameengine.game.gamestate.GameState;
import me.binarybench.gameengine.game.gamestate.GameStateComponent;
import me.binarybench.gameengine.game.gamestate.GameStateComponentManager;

/**
 * Created by BinaryBench on 3/24/2016.
 */
public class RunnerGame implements Game {

    private PlayerComponent playerComponent;

    public RunnerGame(PlayerComponent playerComponent)
    {
        this.playerComponent = playerComponent;
    }

    @Override
    public void start(GameComponent gameComponent)
    {

        GameStateComponent gameStateComponent = new GameStateComponent(gameComponent);


        GameStateComponentManager gameStateComponentManager = new GameStateComponentManager(gameComponent, gameStateComponent);



        gameStateComponentManager.addComponents(GameState.LOBBY, );








    }
}
