package me.binarybench.gameengine.component.world;

import org.bukkit.World;

import java.io.File;
import java.util.function.Supplier;

/**
 * Created by Bench on 3/27/2016.
 */
public interface WorldManager extends Supplier<World> {

    /**
     *
     * @return the game world, null if it's not loaded.
     */
    World getWorld();

    /**
     *
     * @return The name of the game world, even if it's not loaded yet.
     */
    String getName();

    /**
     *
     * @return The Directory where configurations are stored, null if it hasn't been loaded yet.
     */
    File getConfigurationDirectory();

    @Override
    default World get()
    {
        return getWorld();
    }
}
