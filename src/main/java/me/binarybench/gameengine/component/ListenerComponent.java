package me.binarybench.gameengine.component;

import me.binarybench.gameengine.Main;
import org.bukkit.event.Listener;

/**
 * Created by BinaryBench on 3/17/2016.
 */
public class ListenerComponent extends BaseComponent implements Listener {

    @Override
    public boolean enable()
    {
        if (super.enable())
        {
            Main.registerEvents(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean disable()
    {
        if (super.disable())
        {
            Main.unregisterEvents(this);
            return true;
        }
        return false;
    }
}
