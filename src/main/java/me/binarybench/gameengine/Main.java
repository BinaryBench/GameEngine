package me.binarybench.gameengine;

import me.binarybench.gameengine.common.utils.LocationUtil;
import me.binarybench.gameengine.common.utils.RandomUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/*
 * Created by BinaryBench on 3/17/2016.
 */
public class Main extends JavaPlugin implements Listener {

    public static void main(String[] args)
    {
        System.out.println(LocationUtil.toWorldlessLocation("0.5,57,-4.5"));
    }


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

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        if (arena.getPlayerComponent().canJoin(event.getPlayer()))
            arena.getPlayerComponent().addPlayer(event.getPlayer());
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