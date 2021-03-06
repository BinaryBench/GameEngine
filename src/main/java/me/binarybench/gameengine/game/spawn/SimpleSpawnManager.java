package me.binarybench.gameengine.game.spawn;

import me.binarybench.gameengine.common.utils.FileUtil;
import me.binarybench.gameengine.common.utils.LocationUtil;
import me.binarybench.gameengine.common.utils.PlayerUtil;
import me.binarybench.gameengine.common.utils.ServerUtil;
import me.binarybench.gameengine.component.world.WorldManager;
import me.binarybench.gameengine.game.spawn.event.PlayerSpawnEvent;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bench on 3/29/2016.
 */
public class SimpleSpawnManager implements SpawnManager {

    private WorldManager worldManager;

    private List<Location> locations = new ArrayList<>();

    private int counter = 0;

    private GameMode gameMode;

    public SimpleSpawnManager(WorldManager worldManager)
    {
        this(worldManager, GameMode.SURVIVAL);
    }

    public SimpleSpawnManager(WorldManager worldManager, GameMode gameMode)
    {
        this.worldManager = worldManager;
        this.gameMode = gameMode;
        loadLocations(worldManager);
    }


    public void loadLocations(WorldManager worldManager)
    {
        File file = FileUtil.newFileIgnoreCase(worldManager.getConfigurationDirectory(), "mapdata.yml");
        if (!file.exists())
        {
            ServerUtil.shutdown("Could not find: " + file.getPath());
            return;
        }

        YamlConfiguration mapdata = YamlConfiguration.loadConfiguration(file);

        for (String stringLocation : mapdata.getStringList("SpawnPoints"))
        {
            Location l = LocationUtil.toWorldlessLocation(stringLocation);

            if (l != null)
                locations.add(l);
        }

        if (locations.isEmpty()) {
            ServerUtil.shutdown("There where no valid spawnpoints found for the map " + worldManager.getConfigurationDirectory().getName() + "!",
                    "Scotty did not know where to beam you down to!");
        }
    }


    @Override
    public Location getSpawn(Player player)
    {
        if (locations == null)
            ServerUtil.shutdown("No locations, (probably because the world wasn't loaded.)");

        Location loc = locations.get(counter);
        counter++;
        if (counter == locations.size())
            counter = 0;

        return new Location(getWorldManager().getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }


    @Override
    public Location respawn(PlayerSpawnEvent event)
    {
        Player player = event.getPlayer();

        player.setGameMode(getGameMode());
        PlayerUtil.resetPlayer(player);

        return SpawnManager.super.respawn(event);
    }

    public WorldManager getWorldManager()
    {
        return worldManager;
    }

    public GameMode getGameMode()
    {
        return gameMode;
    }
}
