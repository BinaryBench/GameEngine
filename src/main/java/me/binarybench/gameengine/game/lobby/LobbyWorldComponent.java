package me.binarybench.gameengine.game.lobby;

import me.binarybench.gameengine.Main;
import me.binarybench.gameengine.common.utils.*;
import me.binarybench.gameengine.component.ListenerComponent;
import me.binarybench.gameengine.component.world.WorldManager;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Bench on 3/27/2016.
 */
public class LobbyWorldComponent extends ListenerComponent implements WorldManager {

    public static final String LOBBY_WORLD_NAME = "LobbyWorld";

    private ScheduledExecutorService executorService;

    private File saveFile;
    // TODO make this arena specific
    private String name = "LobbyWorld";
    private World world;

    public LobbyWorldComponent(@Nonnull Object identifier, @Nonnull ScheduledExecutorService executorService)
    {
        this.executorService = executorService;

        //Kinda ehh
        this.name = LOBBY_WORLD_NAME + identifier.hashCode();


        //Sever_Folder/plugins/GameEngine/
        File worldsFolders = Main.getPlugin().getDataFolder();


        if (!worldsFolders.isDirectory())
        {
            ServerUtil.shutdown("Could not find plugin folder!");
            return;
        }

        saveFile = FileUtil.newFileIgnoreCase(worldsFolders, "Lobby", name);

        if (!WorldUtil.isWorld(saveFile))
        {
            ServerUtil.shutdown("World: " + saveFile.getPath() + " is not a world!");
        }
    }


    @Override
    public void onEnable()
    {

        world = WorldUtil.getWorld(getName());

        if (world == null)
             world = Main.createTemporaryWorld(getSaveFile(), getName());

        if (world != null)
            world.setAutoSave(false);

    }

    @Override
    public void onDisable()
    {
        //WorldUtil.deleteWorld(getWorld(), getExecutorService(), Main.getPlugin());
    }




    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        if (event.getBlock().getWorld().equals(getWorld()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        if (event.getBlock().getWorld().equals(getWorld()))
            event.setCancelled(true);
    }

    @EventHandler
    private void onExplode(EntityExplodeEvent event)
    {
        if (event.getEntity().getWorld().equals(getWorld()))
            event.setCancelled(true);
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
