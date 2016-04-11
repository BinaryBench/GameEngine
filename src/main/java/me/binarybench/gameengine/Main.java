package me.binarybench.gameengine;

import me.binarybench.gameengine.common.utils.LocationUtil;
import me.binarybench.gameengine.common.utils.WorldUtil;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/*
 * Created by BinaryBench on 3/17/2016.
 */
public class Main extends JavaPlugin implements Listener {

    public static void main(String[] args)
    {
        System.out.println(LocationUtil.toWorldlessLocation("0.5,57,-4.5"));

        /*
        TreeMap<Integer, String> map = new TreeMap<Integer, String>((o1, o2) -> {
            return 0 - o1.compareTo(o2);
        });

        map.put(1, "One");
        map.put(1, "Two");
        map.put(0, "Three");
        map.put(4, "Four");

        System.out.println(map.firstEntry().getValue());

        System.out.println(map);
        */

        List<String> strings = new ArrayList<>();


        strings.add("Aaron");
        strings.add("Sierra");

        strings.remove("Aaron");
        strings.add("Aaron");

        System.out.println(strings);

    }

    private List<World> temporaryWorlds = new ArrayList<>();

    private static Main plugin;

    private ScheduledExecutorService scheduledExecutorService;

    private SimpleArena arena;

    @Override
    public void onEnable()
    {
        plugin = this;
        scheduledExecutorService = Executors.newScheduledThreadPool(10);
        registerEvents(this);
        getDataFolder().mkdirs();
        this.arena = new SimpleArena(this.scheduledExecutorService);
    }

    public void onDisable()
    {
        for (World world : temporaryWorlds)
        {
            File folder = world.getWorldFolder();
            WorldUtil.unloadWorld(world, false);
            try
            {
                FileUtils.deleteDirectory(folder);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        if (arena.getPlayerComponent().canJoin(event.getPlayer()))
            arena.getPlayerComponent().addPlayer(event.getPlayer());
    }


    public static World createTemporaryWorld(File scrFile, String worldName)
    {
        World world = WorldUtil.createWorld(scrFile, worldName);

        if (world != null)
            getPlugin().temporaryWorlds.add(world);

        return world;
    }


    public static Main getPlugin()
    {
        return plugin;
    }

    public static void registerEvents(Listener listener)
    {
        Bukkit.getPluginManager().registerEvents(listener, getPlugin());
    }

    public static void unregisterEvents(Listener listener)
    {
        HandlerList.unregisterAll(listener);
    }




}