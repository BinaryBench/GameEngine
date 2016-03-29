package me.binarybench.gameengine.game.world;

import me.binarybench.gameengine.Main;
import me.binarybench.gameengine.common.utils.FileUtil;
import me.binarybench.gameengine.common.utils.RandomUtil;
import me.binarybench.gameengine.common.utils.ServerUtil;
import me.binarybench.gameengine.common.utils.WorldUtil;
import me.binarybench.gameengine.game.events.GameEndEvent;
import me.binarybench.gameengine.game.events.GameStartEvent;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Bench on 3/27/2016.
 */
public class SimpleWorldManager implements WorldManager, Listener {

    private ScheduledExecutorService executorService;

    private int id;
    private File saveFile;
    private String name;
    private World world;

    public SimpleWorldManager(@Nonnull String name, @Nonnull ScheduledExecutorService executorService)
    {
        this.executorService = executorService;

        // TODO might want to do this nicer
        this.id = hashCode();

        //Sever_Folder/plugins/GameEngine/
        File worldsFolders = Main.getPlugin().getDataFolder();

        //
        if (!worldsFolders.exists() || !worldsFolders.isDirectory())
        {
            ServerUtil.shutdown("Could not find sever folder!");
            return;
        }

        File gameWorldsFolder = FileUtil.newFileIgnoreCase(worldsFolders, name);

        if (!gameWorldsFolder.exists())
        {
            ServerUtil.shutdown("No " + name + " world folders!");
            return;
        }

        if (!gameWorldsFolder.isDirectory())
        {
            ServerUtil.shutdown("No " + gameWorldsFolder.getPath() + " is not a directory!");
            return;
        }

        List<File> possibleWorlds = new ArrayList<>();

        for (File file : gameWorldsFolder.listFiles())
        {
            if (WorldUtil.isWorld(file))
            {
                possibleWorlds.add(file);
            }
        }

        if (possibleWorlds.isEmpty())
        {
            ServerUtil.shutdown("No worlds found for " + name + "!");
            return;
        }

        saveFile = RandomUtil.randomElement(possibleWorlds);

        this.name = saveFile.getName() + this.id;
        Main.registerEvents(this);
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onStart(GameStartEvent event)
    {
        WorldUtil.deleteWorld(getName(), getExecutorService(), Main.getPlugin(), () -> {
            this.world = WorldUtil.createWorld(getSaveFile(), getName());
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEnd(GameEndEvent event)
    {
        WorldUtil.deleteWorld(getWorld(), getExecutorService(), Main.getPlugin());
        Main.unregisterEvents(this);
    }




    @Override
    public World getWorld()
    {
        return world;
    }

    @Override
    public String getName()
    {
        return name;
    }

    public File getSaveFile()
    {
        return saveFile;
    }

    @Override
    public File getConfigurationDirectory()
    {
        return getSaveFile();
    }

    public ScheduledExecutorService getExecutorService()
    {
        return executorService;
    }
}
