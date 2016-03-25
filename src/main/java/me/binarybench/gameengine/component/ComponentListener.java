package me.binarybench.gameengine.component;

import me.binarybench.gameengine.Main;
import org.bukkit.event.Listener;

/**
 * Created by BinaryBench on 3/17/2016.
 */
public class ComponentListener extends ComponentBase implements Listener {

    @Override
    public void onEnable()
    {
        Main.registerEvents(this);
    }

    @Override
    public void onDisable()
    {
        Main.unregisterEvents(this);
    }
}
