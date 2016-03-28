package me.binarybench.gameengine.game.world;

import me.binarybench.gameengine.Main;
import me.binarybench.gameengine.common.utils.RandomUtil;
import me.binarybench.gameengine.common.utils.ServerUtil;
import me.binarybench.gameengine.common.utils.WorldUtil;
import me.binarybench.gameengine.component.ComponentBase;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Bench on 3/27/2016.
 */
public class SimpleWorldComponent extends ComponentBase implements WorldComponent {

    private ScheduledExecutorService executorService;

    private int id;
    private File saveFile;
    private String name;
    private World world;

    public SimpleWorldComponent(@Nonnull String name, @Nonnull ScheduledExecutorService executorService)
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

        File gameWorldsFolder = null;

        for (File subDir : worldsFolders.listFiles())
        {
            if (subDir.getName().equalsIgnoreCase(name))
            {
                gameWorldsFolder = subDir;
                break;
            }
        }

        if (gameWorldsFolder == null)
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

    }


    @Override
    public void onEnable()
    {
        WorldUtil.deleteWorld(getName(), getExecutorService(), Main.getPlugin(), () -> {
            this.world = WorldUtil.createWorld(getSaveFile(), getName());
        });
    }

    @Override
    public void onDisable()
    {
        WorldUtil.deleteWorld(getWorld(), getExecutorService(), Main.getPlugin());
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

    public ScheduledExecutorService getExecutorService()
    {
        return executorService;
    }
}
