package me.binarybench.gameengine.game.games.runner;

import me.binarybench.gameengine.component.Component;
import me.binarybench.gameengine.component.player.PlayerComponent;
import me.binarybench.gameengine.component.simple.*;
import me.binarybench.gameengine.component.spectate.GameModeSpectateComponent;
import me.binarybench.gameengine.component.spectate.SpectateComponent;
import me.binarybench.gameengine.game.FullGameComponentManager;
import me.binarybench.gameengine.game.Game;
import me.binarybench.gameengine.game.GameComponent;
import me.binarybench.gameengine.game.countdown.GameStateCountdown;
import me.binarybench.gameengine.game.countdown.PlayerGameStateCountdown;
import me.binarybench.gameengine.game.games.runner.components.FallingBlockKiller;
import me.binarybench.gameengine.game.games.runner.components.RunnerComponent;
import me.binarybench.gameengine.game.gamestate.GameState;
import me.binarybench.gameengine.game.gamestate.GameStateManager;
import me.binarybench.gameengine.game.gamestate.GameStateComponentManager;
import me.binarybench.gameengine.game.lobby.LobbyComponent;
import me.binarybench.gameengine.game.lobby.LobbyWorldComponent;
import me.binarybench.gameengine.game.victorycondition.LMSVictoryCondition;
import me.binarybench.gameengine.game.world.GameInfoComponent;
import me.binarybench.gameengine.game.world.SimpleWorldManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventPriority;

import java.util.Arrays;
import java.util.List;
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



        LobbyWorldComponent lobbyWorldComponent = new LobbyWorldComponent(gameComponent, getScheduledExecutorService());


        FullGameComponentManager fullGameComponentManager = new FullGameComponentManager(gameComponent);


        //Full Game Component
        /*fullGameComponentManager.addComponent(new GameInfoComponent(worldManager, getPlayerComponent(),
                ChatColor.YELLOW + "Game:" + ChatColor.WHITE + "Runner",
                "",
                "Run around and try not fall!"));
        */
        fullGameComponentManager.addComponent(lobbyWorldComponent, EventPriority.LOWEST, EventPriority.HIGH);




        //GameState Components
        GameStateComponentManager gameStateComponentManager = new GameStateComponentManager(gameComponent, gameStateManager);

        List<Component> stopALLOFTHETHINGS = Arrays.asList(
                new NoBlockBreak(playerComponent),
                new NoBlockPlace(playerComponent),
                new NoDamage(playerComponent),
                new NoDropItem(playerComponent),
                new NoPickUpItem(playerComponent));

        //Lobby Component
        gameStateComponentManager.addComponent(new LobbyComponent(playerComponent, lobbyWorldComponent), GameState.LOBBY);

        //Stop Everything
        gameStateComponentManager.addComponents(Arrays.asList(GameState.LOBBY, GameState.PRE_GAME, GameState.IN_GAME, GameState.POST_GAME), stopALLOFTHETHINGS);

        //Game Specific
        gameStateComponentManager.addComponent(new RunnerComponent(spectateComponent.getNonSpectateHolder(), getScheduledExecutorService()), GameState.IN_GAME);
        gameStateComponentManager.addComponent(new FallingBlockKiller(worldManager), GameState.IN_GAME, GameState.POST_GAME);

        //Countdowns
        gameStateComponentManager.addComponent(new PlayerGameStateCountdown(getScheduledExecutorService(), 10, gameStateManager, GameState.PRE_GAME, playerComponent, 1, 1), GameState.LOBBY);
        gameStateComponentManager.addComponent(new GameStateCountdown(getScheduledExecutorService(), 10, gameStateManager, GameState.IN_GAME, playerComponent), GameState.PRE_GAME);

        gameStateComponentManager.addComponent(new GameStateCountdown(getScheduledExecutorService(), 10, gameStateManager, GameState.RESTARTING, playerComponent), GameState.POST_GAME);

        //Victory Condition
        gameStateComponentManager.addComponent(new LMSVictoryCondition(spectateComponent.getNonSpectateHolder(), 1, playerComponent, () -> {
            gameStateManager.setGameState(GameState.POST_GAME);
        }));

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
