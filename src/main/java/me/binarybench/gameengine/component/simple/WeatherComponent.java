package me.binarybench.gameengine.component.simple;

import me.binarybench.gameengine.common.utils.FileUtil;
import me.binarybench.gameengine.common.utils.ServerUtil;
import me.binarybench.gameengine.component.ListenerComponent;
import me.binarybench.gameengine.component.world.WorldManager;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherEvent;
import org.bukkit.event.world.WorldLoadEvent;

import javax.annotation.Nonnull;
import java.io.File;

/**
 * Created by Bench on 4/8/2016.
 */
public class WeatherComponent extends ListenerComponent {

    private WorldManager worldManager;

    private final boolean defaultHasStorm;

    private boolean hasStorm;

    public WeatherComponent(WorldManager worldManager)
    {
        this(worldManager, false);
    }

    public WeatherComponent(WorldManager worldManager, boolean hasStorm)
    {
        this.defaultHasStorm = hasStorm;
        this.worldManager = worldManager;
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event)
    {
        System.err.println("onWorldLoad! event: " + event.getWorld() + " Manager: " + getWorldManager().getName());
        if (!event.getWorld().getName().equals(getWorldManager().getName()))
            return;
        setWeather(event.getWorld());
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent event)
    {

        if (!event.getWorld().equals(getWorldManager().getWorld()))
            return;
        if (event.toWeatherState() != hasStorm)
            event.setCancelled(true);
    }

    private void setWeather(World world)
    {
        System.err.println("setWeather called!");
        if (world == null)
            return;

        File file = FileUtil.newFileIgnoreCase(getWorldManager().getConfigurationDirectory(), "mapdata.yml");

        if (!file.exists())
        {
            hasStorm = defaultHasStorm;
            System.err.println("No file");
        }
        else
        {
            System.err.println("Yes file");
            YamlConfiguration mapdata = YamlConfiguration.loadConfiguration(file);
            hasStorm = mapdata.getBoolean("Storm", defaultHasStorm);
        }

        System.err.println("hasStorm: " + hasStorm);

        world.setStorm(hasStorm);
        world.setThundering(hasStorm);
        world.setWeatherDuration(Integer.MAX_VALUE);
    }

    @Override
    public void onEnable()
    {
        setWeather(getWorldManager().getWorld());
    }

    @Override
    public void onDisable()
    {

    }

    public WorldManager getWorldManager()
    {
        return worldManager;
    }
}
