package me.binarybench.gameengine.game.world;

import me.binarybench.gameengine.common.utils.WorldUtil;
import me.binarybench.gameengine.component.Component;
import org.bukkit.World;

import java.io.File;

/**
 * Created by Bench on 3/27/2016.
 */
public interface WorldComponent extends Component {

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

    default File getWorldFile()
    {
        if (getWorld() == null)
            return null;
        return getWorld().getWorldFolder();
    }
}
