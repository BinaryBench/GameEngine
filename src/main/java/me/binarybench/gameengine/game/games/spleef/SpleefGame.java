package me.binarybench.gameengine.game.games.spleef;

import me.binarybench.gameengine.component.player.PlayerComponent;
import me.binarybench.gameengine.component.simple.*;
import me.binarybench.gameengine.component.spectate.GameModeSpectateComponent;
import me.binarybench.gameengine.component.spectate.components.DeathSpectate;
import me.binarybench.gameengine.component.spectate.components.JoinSpectate;
import me.binarybench.gameengine.component.spectate.components.KeepInWorld;
import me.binarybench.gameengine.component.world.GameInfoComponent;
import me.binarybench.gameengine.component.world.SimpleWorldComponent;
import me.binarybench.gameengine.game.FullGameComponentManager;
import me.binarybench.gameengine.game.Game;
import me.binarybench.gameengine.game.GameComponent;
import me.binarybench.gameengine.game.countdown.GameStateCountdown;
import me.binarybench.gameengine.game.countdown.PlayerGameStateCountdown;
import me.binarybench.gameengine.component.simple.FallingBlockKiller;
import me.binarybench.gameengine.game.games.spleef.components.SpleefComponent;
import me.binarybench.gameengine.game.gamestate.GameState;
import me.binarybench.gameengine.game.gamestate.GameStateComponentManager;
import me.binarybench.gameengine.game.gamestate.GameStateManager;
import me.binarybench.gameengine.game.lobby.LobbyComponent;
import me.binarybench.gameengine.game.lobby.LobbyWorldComponent;
import me.binarybench.gameengine.game.spawn.SimpleSpawnManager;
import me.binarybench.gameengine.game.spawn.SpawnAtComponent;
import me.binarybench.gameengine.game.spawn.SpawnManager;
import me.binarybench.gameengine.game.victorycondition.LMSVictoryCondition;
import me.binarybench.gameengine.game.victorycondition.TimeVictoryCondition;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventPriority;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bench on 4/10/2016.
 */
public class SpleefGame implements Game {

    public static final String NAME = "Spleef";

    private PlayerComponent playerComponent;

    private ScheduledExecutorService scheduledExecutorService;

    public SpleefGame(PlayerComponent playerComponent, ScheduledExecutorService scheduledExecutorService)
    {
        this.playerComponent = playerComponent;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override
    public void start(GameComponent gameComponent)
    {

        GameStateManager gameStateManager = new GameStateManager(gameComponent);

        SimpleWorldComponent worldManager = new SimpleWorldComponent(NAME, getScheduledExecutorService());

        SpawnManager spawnManager = new SimpleSpawnManager(worldManager);

        GameModeSpectateComponent spectateComponent = new GameModeSpectateComponent(getPlayerComponent());

        LobbyWorldComponent lobbyWorldComponent = new LobbyWorldComponent(gameComponent, getScheduledExecutorService());

        FullGameComponentManager fullGameComponentManager = new FullGameComponentManager(gameComponent);

        //Full Game Component
        fullGameComponentManager.addComponent(new GameInfoComponent(worldManager, getPlayerComponent(),
                ChatColor.YELLOW + "Game: " + ChatColor.WHITE + "Runner",
                "",
                "Run around and try not fall!"));

        fullGameComponentManager.addComponent(worldManager, EventPriority.LOW, EventPriority.HIGHEST);
        fullGameComponentManager.addComponent(new WeatherComponent(worldManager));
        fullGameComponentManager.addComponent(lobbyWorldComponent, EventPriority.LOWEST, EventPriority.HIGH);

        fullGameComponentManager.addComponent(spectateComponent);

        fullGameComponentManager.addComponent(new KeepInWorld(spectateComponent, worldManager));


        //GameState Components
        GameStateComponentManager gameStateComponentManager = new GameStateComponentManager(gameComponent, gameStateManager);




        //Stop Everything
        gameStateComponentManager.add(new NoBlockBreak(playerComponent), GameState.values());
        gameStateComponentManager.add(new NoBlockPlace(playerComponent), GameState.values());
        gameStateComponentManager.add(new NoDropItem(playerComponent), GameState.values());
        gameStateComponentManager.add(new NoPickUpItem(playerComponent), GameState.values());

        gameStateComponentManager.add(new JoinSpectate(spectateComponent), GameState.PRE_GAME, GameState.IN_GAME, GameState.POST_GAME);

        gameStateComponentManager.add(new NoDamage(playerComponent), GameState.LOBBY, GameState.PRE_GAME, GameState.POST_GAME);
        gameStateComponentManager.add(new NoPvP(playerComponent), GameState.IN_GAME);

        gameStateComponentManager.add(new VoidKiller(spectateComponent.getNonSpectateHolder(), worldManager), GameState.PRE_GAME, GameState.IN_GAME);
        gameStateComponentManager.add(new SpawnAtComponent(spawnManager, playerComponent), GameState.PRE_GAME);

        gameStateComponentManager.add(new DeathSpectate(spectateComponent), GameState.PRE_GAME, GameState.IN_GAME, GameState.POST_GAME);




        //Lobby Component
        gameStateComponentManager.add(new LobbyComponent(playerComponent, lobbyWorldComponent), GameState.LOBBY);



        //Game Specific
        gameStateComponentManager.add(new SpleefComponent(spectateComponent.getNonSpectateHolder(), worldManager), GameState.IN_GAME);
        gameStateComponentManager.add(new FallingBlockKiller(worldManager), GameState.IN_GAME, GameState.POST_GAME);



        //Countdowns
        gameStateComponentManager.add(new PlayerGameStateCountdown(getScheduledExecutorService(), 10, gameStateManager, GameState.PRE_GAME, playerComponent, 2, 1), GameState.LOBBY);
        gameStateComponentManager.add(new GameStateCountdown(getScheduledExecutorService(), 5, gameStateManager, GameState.IN_GAME, playerComponent), GameState.PRE_GAME);

        gameStateComponentManager.add(new GameStateCountdown(getScheduledExecutorService(), 5, gameStateManager, GameState.RESTARTING, playerComponent), GameState.POST_GAME);

        //Victory Condition
        gameStateComponentManager.add(new LMSVictoryCondition(spectateComponent.getNonSpectateHolder(), 1, playerComponent, () -> {
            gameStateManager.setGameState(GameState.POST_GAME);
        }), GameState.PRE_GAME, GameState.IN_GAME);

        gameStateComponentManager.add(new TimeVictoryCondition(getPlayerComponent(), () -> {
            gameStateManager.setGameState(GameState.POST_GAME);
        }, getScheduledExecutorService(), 8, TimeUnit.MINUTES), GameState.IN_GAME);
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
