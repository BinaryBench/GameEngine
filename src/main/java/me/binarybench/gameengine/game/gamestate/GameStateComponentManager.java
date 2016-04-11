package me.binarybench.gameengine.game.gamestate;

import me.binarybench.gameengine.common.utils.ListUtil;
import me.binarybench.gameengine.game.GameComponent;
import me.binarybench.gameengine.game.gamestate.events.GameStateChangeEvent;
import me.binarybench.gameengine.Main;
import me.binarybench.gameengine.component.Component;
import me.binarybench.gameengine.game.events.GameEndEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.*;

/**
 * Created by BinaryBench on 3/24/2016.
 */
public class GameStateComponentManager implements Listener {

    private GameStateManager gameStateManager;

    private GameComponent gameComponent;

    private Map<GameState, Set<Component>> components = new HashMap<>();

    public GameStateComponentManager(GameComponent gameComponent, GameStateManager gameStateManager)
    {
        this.gameStateManager = gameStateManager;
        this.gameComponent = gameComponent;

        for (GameState gameState : GameState.values())
        {
            components.put(gameState, new HashSet<>());
        }
        components.remove(GameState.RESTARTING);

        Main.registerEvents(this);
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event)
    {
        if (event.getComponent() != this.gameStateManager)
            return;


        //Set last
        Set<Component> lastComponents = this.components.get(event.getFromGameState());

        if (lastComponents == null)
            lastComponents = Collections.emptySet();


        //Set next
        Set<Component> nextComponents = this.components.get(event.getToGameState());

        if (nextComponents == null)
            nextComponents = Collections.emptySet();

        //Disable
        Set<Component> toDisable = new HashSet<>(lastComponents);
        toDisable.removeAll(nextComponents);
        toDisable.forEach(Component::disable);


        //Enable
        Set<Component> toEnable = new HashSet<>(nextComponents);
        toEnable.removeAll(lastComponents);
        toEnable.forEach(Component::enable);

    }



    @EventHandler
    public void onEndGame(GameEndEvent event)
    {
        if (event.getComponent() != this.gameComponent)
            return;

        for (Set<Component> set : this.components.values())
            set.forEach(Component::disable);

        Main.unregisterEvents(this);
    }





    //Add & Remove Component
    public boolean addComponent(Component component, GameState gameState, GameState... gameStates)
    {
        return addComponents(Collections.singleton(component), ListUtil.append(gameState, gameStates));
    }

    public boolean addComponent(Component component, GameState[] gameStates)
    {
        return addComponents(Collections.singleton(component), Arrays.asList(gameStates));
    }

    public boolean addComponents(Collection<Component> components, GameState gameState, GameState... gameStates)
    {
        return addComponents(components, ListUtil.append(gameState, gameStates));
    }

    public boolean addComponents(Collection<Component> components, GameState[] gameStates)
    {
        return  addComponents(components, Arrays.asList(gameStates));
    }

    public boolean addComponents(Collection<Component> componentsToAdd, Collection<GameState> gameStates)
    {
        boolean modified = false;

        for (GameState gameState : gameStates)
        {
            if (gameState.equals(GameState.RESTARTING))
                continue;

            if (components.get(gameState).addAll(componentsToAdd))
                modified = true;
        }
        return  modified;
    }

    public boolean addComponents(Map<GameState, Collection<Component>> map)
    {
        boolean modified = false;

        map.remove(GameState.RESTARTING);

        for (Map.Entry<GameState, Collection<Component>> entry : map.entrySet())
        {
            if (components.get(entry.getKey()).addAll(entry.getValue()))
                modified = true;
        }
        return modified;
    }

    public boolean removeComponent(Collection<Component> componentsToRemove)
    {
        boolean modified = false;

        for (Set<Component> set : components.values())
        {
            if (set.removeAll(componentsToRemove))
                modified = true;
        }

        return  modified;
    }


}
