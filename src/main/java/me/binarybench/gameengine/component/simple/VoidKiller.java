package me.binarybench.gameengine.component.simple;

import me.binarybench.gameengine.Main;
import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.common.utils.FileUtil;
import me.binarybench.gameengine.common.utils.PlayerUtil;
import me.binarybench.gameengine.common.utils.ServerUtil;
import me.binarybench.gameengine.component.BaseComponent;
import me.binarybench.gameengine.component.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * Created by Bench on 4/1/2016.
 */
public class VoidKiller extends BaseComponent implements Runnable {

    private static final int DEFAULT_HEIGHT = 0;

    private int id;

    private int height = DEFAULT_HEIGHT;

    private PlayerHolder playerHolder;
    private WorldManager worldManager;

    public VoidKiller(PlayerHolder playerHolder, WorldManager worldManager)
    {
        this.playerHolder = playerHolder;
        this.worldManager = worldManager;
        loadHeight();
    }

    private void loadHeight()
    {
        File file = FileUtil.newFileIgnoreCase(getWorldManager().getConfigurationDirectory(), "mapdata.yml");

        if (!file.exists())
        {
            ServerUtil.shutdown("Could not find: " + file.getPath());
            return;
        }

        YamlConfiguration mapdata = YamlConfiguration.loadConfiguration(file);

        this.height = mapdata.getInt("VoidLevel", DEFAULT_HEIGHT);

        System.err.println("height: " + this.height);

    }


    @Override
    public void onEnable()
    {
        this.id = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), this, 2, 2).getTaskId();
    }

    @Override
    public void onDisable()
    {
        Bukkit.getScheduler().cancelTask(this.id);
    }





    @Override
    public void run()
    {
        for (Player player : getPlayerHolder())
        {
            Location loc = player.getLocation();
            if (loc.getBlockY() < this.height)
            {
                System.err.println("Killing player!");
                PlayerUtil.killPlayer(player);
            }
        }
    }


    public PlayerHolder getPlayerHolder()
    {
        return playerHolder;
    }

    public WorldManager getWorldManager()
    {
        return worldManager;
    }
}
