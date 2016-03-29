package me.binarybench.gameengine.game.games.runner;

import me.binarybench.gameengine.component.player.PlayerComponent;
import me.binarybench.gameengine.component.spectate.GameModeSpectateComponent;
import me.binarybench.gameengine.component.spectate.SpectateComponent;
import me.binarybench.gameengine.game.FullGameComponentManager;
import me.binarybench.gameengine.game.Game;
import me.binarybench.gameengine.game.GameComponent;
import me.binarybench.gameengine.game.games.runner.components.FallingBlockKiller;
import me.binarybench.gameengine.game.games.runner.components.RunnerComponent;
import me.binarybench.gameengine.game.gamestate.GameState;
import me.binarybench.gameengine.game.gamestate.GameStateManager;
import me.binarybench.gameengine.game.gamestate.GameStateComponentManager;
import me.binarybench.gameengine.game.world.GameInfoComponent;
import me.binarybench.gameengine.game.world.SimpleWorldManager;
import net.md_5.bungee.api.ChatColor;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by BinaryBench on 3/24/2016.
 */
public class RunnerGame implements Game {

    public static final String NAME = "Runner";

    private PlayerComponent playerComponent;

    private ScheduledExecutorService scheduledExecutorService;

    public RunnerGame(PlayerComponent playerComponent, ScheduledExecutorService scheduledExecutorService)
    {
        this.playerComponent = playerComponent;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override
    public void start(GameComponent gameComponent)
    {

        GameStateManager gameStateManager = new GameStateManager(gameComponent);

        SimpleWorldManager worldManager = new SimpleWorldManager(NAME, getScheduledExecutorService());

        SpectateComponent spectateComponent = new GameModeSpectateComponent(getPlayerComponent());

        FullGameComponentManager fullGameComponentManager = new FullGameComponentManager(gameComponent);
        GameStateComponentManager gameStateComponentManager = new GameStateComponentManager(gameComponent, gameStateManager);


        fullGameComponentManager.addComponent(new GameInfoComponent(worldManager, getPlayerComponent(),
                ChatColor.YELLOW + "Game:" + ChatColor.WHITE + "Runner",
                "",
                "Run around and try not fall!"));

        gameStateComponentManager.addComponent(new RunnerComponent(spectateComponent.getNonSpectateHolder(), getScheduledExecutorService()), GameState.IN_GAME);
        gameStateComponentManager.addComponent(new FallingBlockKiller(worldManager), GameState.IN_GAME, GameState.POST_GAME);








    }

    public ScheduledExecutorService getScheduledExecutorService()
    {
        return scheduledExecutorService;
    }

    public PlayerComponent getPlayerComponent()
    {
        return playerComponent;
    }
}
