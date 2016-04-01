package me.binarybench.gameengine.component.player;


import me.binarybench.gameengine.Main;
import me.binarybench.gameengine.component.player.events.PlayerComponentJoinEvent;
import me.binarybench.gameengine.component.player.events.PlayerComponentQuitEvent;
import me.binarybench.gameengine.component.player.events.PlayerComponentCanJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by BinaryBench on 3/17/2016.
 */
public class SimplePlayerComponent implements PlayerComponent, Listener {

    private ArrayList<Player> players = new ArrayList<>();

    public SimplePlayerComponent()
    {
        Main.registerEvents(this);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        removePlayer(event.getPlayer());
    }

    //Players joining & leaving
    @Override
    public boolean canJoin(Player player)
    {
        PlayerComponentCanJoinEvent event = new PlayerComponentCanJoinEvent(this, player);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }


    @Override
    public boolean addPlayer(Player player)
    {
        if (!test(player))
        {
            Bukkit.getPluginManager().callEvent(new PlayerComponentJoinEvent(this, player));
            players.add(player);
            return true;
        }
        return false;
    }

    @Override
    public boolean removePlayer(Player player)
    {
        if (test(player))
        {
            Bukkit.getPluginManager().callEvent(new PlayerComponentQuitEvent(this, player));
            players.remove(player);
            return true;
        }
        return false;
    }


    @Override
    public Collection<Player> getPlayers()
    {
        return this.players;
    }

}
