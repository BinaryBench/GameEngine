package me.binarybench.gameengine.game.games.runner;

import me.binarybench.gameengine.component.ComponentCreater;
import me.binarybench.gameengine.component.player.PlayerComponent;
import me.binarybench.gameengine.component.simple.*;
import me.binarybench.gameengine.component.spectate.GameModeSpectateComponent;
import me.binarybench.gameengine.game.BaseGame;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by BinaryBench on 3/24/2016.
 */
public class RunnerGame extends BaseGame {


    public RunnerGame(PlayerComponent playerComponent, ScheduledExecutorService scheduledExecutorService, Runnable onEnd)
    {
        super(playerComponent, scheduledExecutorService, onEnd);

        //PRE_GAME/POST_GAME
        getStateManager().add(ComponentCreater.getDefaultNonGame(playerComponent), GameState.PRE_GAME, GameState.POST_GAME);

        //Spectate
        getStateManager().add(ComponentCreater.getDefaultSpectateComponents(getSpectateComponent(), getWorldManager()));

        //TODO probably a better way of doing spawning as it's pretty much always at PRE_GAME
        getStateManager().add(new SpawnAtComponent(getSpawnManager(), playerComponent), GameState.PRE_GAME);

        //GAME
        getStateManager().add(new NoPvP(playerComponent), GameState.IN_GAME);
        getStateManager().add(new VoidKiller(getSpectateComponent().getNonSpectateHolder(), getWorldManager()), GameState.PRE_GAME, GameState.IN_GAME, GameState.POST_GAME);

        //Game specific
        getStateManager().add(new RunnerComponent(getSpectateComponent().getNonSpectateHolder(), getScheduledExecutorService()), GameState.IN_GAME);
        getStateManager().add(new FallingBlockKiller(getWorldManager()), GameState.IN_GAME, GameState.POST_GAME);

        //Countdowns
        getStateManager().add(ComponentCreater.getDefaultCountdowns(playerComponent, getGameStateComponent(), getScheduledExecutorService()));

        //Victory Condition
        getStateManager().add(
                new LMSVictoryCondition(
                        getSpectateComponent().getNonSpectateHolder(),
                        playerComponent,
                        getGameStateComponent().getRunnable(GameState.POST_GAME)),
                GameState.PRE_GAME,
                GameState.IN_GAME);

    }

    @Override
    public String getName()
    {
        return "Runner";
    }

    @Override
    public List<String> getDescription()
    {
        return Collections.singletonList("Run Around and try not to fall!");
    }
}
