package me.binarybench.gameengine.game;

import me.binarybench.gameengine.component.BaseComponent;
import me.binarybench.gameengine.component.Component;
import me.binarybench.gameengine.component.player.PlayerComponent;
import me.binarybench.gameengine.component.simple.WeatherComponent;
import me.binarybench.gameengine.component.spectate.GameModeSpectateComponent;
import me.binarybench.gameengine.component.spectate.SpectateComponent;
import me.binarybench.gameengine.component.world.GameInfoComponent;
import me.binarybench.gameengine.component.world.SimpleWorldComponent;
import me.binarybench.gameengine.component.world.WorldManager;
import me.binarybench.gameengine.game.gamestate.GameState;
import me.binarybench.gameengine.game.gamestate.GameStateComponentManager;
import me.binarybench.gameengine.game.gamestate.GameStateComponent;
import me.binarybench.gameengine.game.lobby.LobbyComponent;
import me.binarybench.gameengine.game.lobby.LobbyWorldComponent;
import me.binarybench.gameengine.game.spawn.SimpleSpawnManager;
import me.binarybench.gameengine.game.spawn.SpawnManager;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Bench on 4/15/2016.
 */
public abstract class BaseGame extends BaseComponent {

    private PlayerComponent playerComponent;
    private ScheduledExecutorService scheduledExecutorService;

    private Runnable onEnd;



    private GameStateComponent gameStateComponent;
    private GameStateComponentManager stateManager;
    private WorldManager worldManager;
    private SpawnManager spawnManager;

    private SpectateComponent spectateComponent;
    private LobbyWorldComponent lobbyWorldComponent;

    private List<Component> gameComponents;


    public BaseGame(PlayerComponent playerComponent, ScheduledExecutorService scheduledExecutorService, Runnable onEnd)
    {
        this.onEnd = onEnd;
        this.playerComponent = playerComponent;
        this.scheduledExecutorService = scheduledExecutorService;


        this.gameStateComponent = new GameStateComponent(onEnd);
        this.worldManager = new SimpleWorldComponent(getName(), getScheduledExecutorService());
        this.spawnManager = new SimpleSpawnManager(worldManager);
        this.spectateComponent = new GameModeSpectateComponent(getPlayerComponent());
        this.lobbyWorldComponent = new LobbyWorldComponent(playerComponent, getScheduledExecutorService());

        this.stateManager = new GameStateComponentManager(gameStateComponent);

        List<String> list = new ArrayList<>();
        list.add(ChatColor.YELLOW + "Game: " + ChatColor.WHITE + "Runner");
        list.add("");
        list.addAll(getDescription());

        gameComponents.add(
                new GameInfoComponent(worldManager, getPlayerComponent(),list.toArray(new String[0]))
        );

        gameComponents.add(
                new WeatherComponent(worldManager)
        );

        getStateManager().add(new LobbyComponent(playerComponent, lobbyWorldComponent), GameState.LOBBY);

    }

    @Override
    public void onEnable()
    {
        getLobbyWorldComponent().enable();

        if (getWorldManager() instanceof Component)
            ((Component) getWorldManager()).enable();

        if (getSpawnManager() instanceof Component)
            ((Component) getSpawnManager()).enable();

        if (getSpectateComponent() instanceof Component)
            ((Component) getSpectateComponent()).enable();

        getGameStateComponent().enable();
        getStateManager().enable();

        for (Component component : getGameComponents())
        {
            component.enable();
        }
    }

    @Override
    public void onDisable()
    {
        for (Component component : getGameComponents())
        {
            component.disable();
        }

        getLobbyWorldComponent().disable();

        getStateManager().disable();
        getGameStateComponent().disable();

        if (getSpectateComponent() instanceof Component)
            ((Component) getSpectateComponent()).disable();

        if (getSpawnManager() instanceof Component)
            ((Component) getSpawnManager()).disable();


        if (getWorldManager() instanceof Component)
            ((Component) getWorldManager()).disable();

    }



    public abstract String getName();

    public abstract List<String> getDescription();





    public Runnable getOnEnd()
    {
        return onEnd;
    }

    public GameStateComponent getGameStateComponent()
    {
        return gameStateComponent;
    }

    public GameStateComponentManager getStateManager()
    {
        return stateManager;
    }

    public WorldManager getWorldManager()
    {
        return worldManager;
    }

    public SpawnManager getSpawnManager()
    {
        return spawnManager;
    }

    public SpectateComponent getSpectateComponent()
    {
        return spectateComponent;
    }

    public LobbyWorldComponent getLobbyWorldComponent()
    {
        return lobbyWorldComponent;
    }

    public List<Component> getGameComponents()
    {
        return gameComponents;
    }

    public ScheduledExecutorService getScheduledExecutorService()
    {
        return scheduledExecutorService;
    }

    public PlayerComponent getPlayerComponent()
    {
        return playerComponent;
    }

    public void setSpawnManager(SpawnManager spawnManager)
    {
        this.spawnManager = spawnManager;
    }

    public void setSpectateComponent(SpectateComponent spectateComponent)
    {
        this.spectateComponent = spectateComponent;
    }
}
