package me.binarybench.gameengine.component;

import org.bukkit.event.Event;

/**
 * Created by BinaryBench on 3/17/2016.
 */
public abstract class ComponentEvent<T> extends Event {

    private T component;

    public ComponentEvent(T component)
    {
        this.component = component;
    }

    public T getComponent()
    {
        return component;
    }

    public boolean isComponent(T component)
    {
        return getComponent() == component;
    }


}
