package me.binarybench.gameengine.game;

import me.binarybench.gameengine.Main;
import me.binarybench.gameengine.component.Component;
import me.binarybench.gameengine.game.events.GameEndEvent;
import me.binarybench.gameengine.game.events.GameStartEvent;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by Bench on 3/28/2016.
 */
public class FullGameComponentManager implements Listener {

    private Map<Component, Pair<EventPriority, EventPriority>> components = new HashMap<>();

    private GameComponent gameComponent;

    public FullGameComponentManager(GameComponent gameComponent, Component... components)
    {
        this(gameComponent, Arrays.asList(components));
    }

    public FullGameComponentManager(GameComponent gameComponent, Collection<Component> components)
    {
        this(gameComponent, createNormalMap(components));
    }

    public FullGameComponentManager(GameComponent gameComponent, Map<Component, Pair<EventPriority, EventPriority>> componentMap)
    {
        components.putAll(componentMap);
        Main.registerEvents(this);
    }




    //Start
    @EventHandler(priority = EventPriority.LOWEST)
    public void onStartLowest(GameStartEvent event)
    {
        if (event.getComponent() != getGameComponent())
            return;
        components.entrySet().forEach(entry -> {
            if (entry.getValue().getLeft().equals(EventPriority.LOWEST))
                entry.getKey().enable();
        });
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onStartLow(GameStartEvent event)
    {
        if (event.getComponent() != getGameComponent())
            return;
        components.entrySet().forEach(entry -> {
            if (entry.getValue().getLeft().equals(EventPriority.LOW))
                entry.getKey().enable();
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onStartNormal(GameStartEvent event)
    {
        if (event.getComponent() != getGameComponent())
            return;
        components.entrySet().forEach(entry -> {
            if (entry.getValue().getLeft().equals(EventPriority.NORMAL))
                entry.getKey().enable();
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onStartHigh(GameStartEvent event)
    {
        if (event.getComponent() != getGameComponent())
            return;
        components.entrySet().forEach(entry -> {
            if (entry.getValue().getLeft().equals(EventPriority.HIGH))
                entry.getKey().enable();
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onStartHighest(GameStartEvent event)
    {
        if (event.getComponent() != getGameComponent())
            return;
        components.entrySet().forEach(entry -> {
            if (entry.getValue().getLeft().equals(EventPriority.HIGHEST))
                entry.getKey().enable();
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStartMonitor(GameStartEvent event)
    {
        if (event.getComponent() != getGameComponent())
            return;
        components.entrySet().forEach(entry -> {
            if (entry.getValue().getLeft().equals(EventPriority.MONITOR))
                entry.getKey().enable();
        });
    }


    //End
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEndLowest(GameEndEvent event)
    {
        components.entrySet().forEach(entry -> {
            if (entry.getValue().getLeft().equals(EventPriority.LOWEST))
                entry.getKey().disable();
        });
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEndLow(GameEndEvent event)
    {
        components.entrySet().forEach(entry -> {
            if (entry.getValue().getLeft().equals(EventPriority.LOW))
                entry.getKey().disable();
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEndNormal(GameEndEvent event)
    {
        components.entrySet().forEach(entry -> {
            if (entry.getValue().getLeft().equals(EventPriority.NORMAL))
                entry.getKey().disable();
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEndHigh(GameEndEvent event)
    {
        components.entrySet().forEach(entry -> {
            if (entry.getValue().getLeft().equals(EventPriority.HIGH))
                entry.getKey().disable();
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEndHighest(GameEndEvent event)
    {
        components.entrySet().forEach(entry -> {
            if (entry.getValue().getLeft().equals(EventPriority.HIGHEST))
                entry.getKey().disable();
        });
    }

    //Unregistering Events here!
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEndMonitor(GameEndEvent event)
    {
        components.entrySet().forEach(entry -> {
            if (entry.getValue().getLeft().equals(EventPriority.MONITOR))
                entry.getKey().disable();
        });
        Main.unregisterEvents(this);
    }



    public void addComponent(@Nonnull Component component)
    {
        addComponent(component, EventPriority.NORMAL, EventPriority.NORMAL);
    }


    public void addComponent(@Nonnull Component component, @Nonnull EventPriority start, @Nonnull EventPriority end)
    {
        components.put(component, new ImmutablePair<>(start, end));
    }

    public GameComponent getGameComponent()
    {
        return gameComponent;
    }




    public static Map<Component, Pair<EventPriority, EventPriority>> createNormalMap(Collection<Component> components)
    {
        Map<Component, Pair<EventPriority, EventPriority>> componentPairMap = new HashMap<>();

        for (Component component : components)
        {
            componentPairMap.put(component, new ImmutablePair<>(EventPriority.NORMAL, EventPriority.NORMAL));
        }
        return componentPairMap;
    }
}
