package me.binarybench.gameengine.game.games.runner;

import me.binarybench.gameengine.component.ComponentCreater;
import me.binarybench.gameengine.component.player.PlayerComponent;
import me.binarybench.gameengine.component.simple.*;
import me.binarybench.gameengine.component.spectate.GameModeSpectateComponent;
import me.binarybench.gameengine.game.FullGameComponentManager;
import me.binarybench.gameengine.game.Game;
import me.binarybench.gameengine.game.GameComponent;
import me.binarybench.gameengine.component.simple.FallingBlockKiller;
import me.binarybench.gameengine.game.games.runner.components.RunnerComponent;
import me.binarybench.gameengine.game.gamestate.GameState;
import me.binarybench.gameengine.game.gamestate.GameStateComponent;
import me.binarybench.gameengine.game.gamestate.GameStateComponentManager;
import me.binarybench.gameengine.game.lobby.LobbyComponent;
import me.binarybench.gameengine.game.lobby.LobbyWorldComponent;
import me.binarybench.gameengine.game.spawn.SimpleSpawnManager;
import me.binarybench.gameengine.game.spawn.SpawnAtComponent;
import me.binarybench.gameengine.game.spawn.SpawnManager;
import me.binarybench.gameengine.game.victorycondition.LMSVictoryCondition;
import me.binarybench.gameengine.component.world.GameInfoComponent;
import me.binarybench.gameengine.component.world.SimpleWorldComponent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventPriority;

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

        GameStateComponent gameStateComponent = new GameStateComponent(gameComponent);
        SimpleWorldComponent worldManager = new SimpleWorldComponent(NAME, getScheduledExecutorService());
        SpawnManager spawnManager = new SimpleSpawnManager(worldManager);
        GameModeSpectateComponent spectateComponent = new GameModeSpectateComponent(getPlayerComponent());
        LobbyWorldComponent lobbyWorldComponent = new LobbyWorldComponent(gameComponent, getScheduledExecutorService());

        //Component Managers
        FullGameComponentManager fullGameComponentManager = new FullGameComponentManager(gameComponent);
        GameStateComponentManager gameStateComponentManager = new GameStateComponentManager(gameComponent, gameStateComponent);

        fullGameComponentManager.addComponent(new GameInfoComponent(worldManager, getPlayerComponent(),
                ChatColor.YELLOW + "Game: " + ChatColor.WHITE + "Runner",
                "",
                "Run around and try not fall!"));

        fullGameComponentManager.addComponent(worldManager, EventPriority.LOW, EventPriority.HIGHEST);

        //Probably will want this built into the world manager.
        fullGameComponentManager.addComponent(new WeatherComponent(worldManager));

        fullGameComponentManager.addComponent(spectateComponent);

        //LOBBY Components
        fullGameComponentManager.addComponent(lobbyWorldComponent, EventPriority.LOWEST, EventPriority.HIGH);
        gameStateComponentManager.add(new LobbyComponent(playerComponent, lobbyWorldComponent), GameState.LOBBY);

        //PRE_GAME/POST_GAME
        gameStateComponentManager.add(ComponentCreater.getDefaultNonGame(playerComponent), GameState.PRE_GAME, GameState.POST_GAME);

        //Spectate
        gameStateComponentManager.add(ComponentCreater.getDefaultSpectateComponents(spectateComponent, worldManager));

        //TODO probably a better way of doing spawning as it's pretty much always at PRE_GAME
        gameStateComponentManager.add(new SpawnAtComponent(spawnManager, playerComponent), GameState.PRE_GAME);

        //GAME
        gameStateComponentManager.add(new NoPvP(playerComponent), GameState.IN_GAME);
        gameStateComponentManager.add(new VoidKiller(spectateComponent.getNonSpectateHolder(), worldManager), GameState.PRE_GAME, GameState.IN_GAME, GameState.POST_GAME);

        //Game specific
        gameStateComponentManager.add(new RunnerComponent(spectateComponent.getNonSpectateHolder(), getScheduledExecutorService()), GameState.IN_GAME);
        gameStateComponentManager.add(new FallingBlockKiller(worldManager), GameState.IN_GAME, GameState.POST_GAME);

        //Countdowns
        gameStateComponentManager.add(ComponentCreater.getDefaultCountdowns(playerComponent, gameStateComponent, getScheduledExecutorService()));

        //Victory Condition
        gameStateComponentManager.add(
                new LMSVictoryCondition(
                        spectateComponent.getNonSpectateHolder(),
                        playerComponent,
                        gameStateComponent.getRunnable(GameState.POST_GAME)),
                GameState.PRE_GAME,
                GameState.IN_GAME);
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
