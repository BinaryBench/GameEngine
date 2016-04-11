package me.binarybench.gameengine.component;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.component.player.PlayerComponent;
import me.binarybench.gameengine.component.simple.*;
import me.binarybench.gameengine.component.spectate.SpectateComponent;
import me.binarybench.gameengine.component.spectate.components.DeathSpectate;
import me.binarybench.gameengine.component.spectate.components.JoinSpectate;
import me.binarybench.gameengine.component.spectate.components.KeepInWorld;
import me.binarybench.gameengine.component.world.WorldManager;
import me.binarybench.gameengine.game.countdown.GameStateCountdown;
import me.binarybench.gameengine.game.countdown.PlayerGameStateCountdown;
import me.binarybench.gameengine.game.gamestate.GameState;
import me.binarybench.gameengine.game.gamestate.GameStateManager;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Bench on 4/11/2016.
 */
public class ComponentCreater {

    public static List<Component> getDefaultNonGame(PlayerHolder playerHolder)
    {
        return Arrays.asList(
                new NoBlockBreak(playerHolder),
                new NoBlockPlace(playerHolder),
                new NoDropItem(playerHolder),
                new NoPickUpItem(playerHolder),
                new NoHunger(playerHolder),
                new NoDamage(playerHolder),
                new NoBowShoot(playerHolder)
        );
    }

    public static Map<Component, Collection<GameState>> getDefaultSpectateComponents(SpectateComponent spectateComponent, WorldManager gameWorldManager)
    {

        Map<Component, Collection<GameState>> map = new HashMap<>();

        map.put(new KeepInWorld(spectateComponent.getSpectateHolder(), gameWorldManager), Arrays.asList(GameState.values()));
        map.put(new JoinSpectate(spectateComponent), Arrays.asList(GameState.PRE_GAME, GameState.IN_GAME, GameState.POST_GAME));
        map.put(new DeathSpectate(spectateComponent), Arrays.asList(GameState.PRE_GAME, GameState.IN_GAME, GameState.POST_GAME));

        return map;

    }

    public static Map<Component, Collection<GameState>> getDefaultCountdowns(PlayerComponent playerComponent, GameStateManager gameStateManager, ScheduledExecutorService scheduledExecutorService)
    {
        Map<Component, Collection<GameState>> map = new HashMap<>();

        map.put(new PlayerGameStateCountdown(scheduledExecutorService, 10, gameStateManager, GameState.PRE_GAME, playerComponent, 2, 1), Collections.singleton(GameState.LOBBY));

        map.put(new GameStateCountdown(scheduledExecutorService, 5, gameStateManager, GameState.IN_GAME, playerComponent), Collections.singleton(GameState.PRE_GAME));
        map.put(new GameStateCountdown(scheduledExecutorService, 5, gameStateManager, GameState.RESTARTING, playerComponent), Collections.singleton(GameState.POST_GAME));

        return map;

    }

}
