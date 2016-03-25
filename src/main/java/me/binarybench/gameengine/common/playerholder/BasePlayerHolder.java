package me.binarybench.gameengine.common.playerholder;

import me.binarybench.gameengine.common.playerholder.events.PlayerAddEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by BinaryBench on 3/20/2016.
 */
public class BasePlayerHolder implements PlayerHolder {

    private List<Player> players;

    public BasePlayerHolder()
    {
        this(new ArrayList<>());

    }

    public BasePlayerHolder(List<Player> players)
    {
        this.players = players;
    }

    @Override
    public Collection<Player> getPlayers()
    {
        return players;
    }

    public boolean addPlayer(Player player)
    {
        if (getPlayers().contains(player))
            return false;

        PlayerAddEvent event = new PlayerAddEvent(this, player);
        Bukkit.getPluginManager().callEvent(event);

        getPlayers().add(player);

        return true;
    }

    public boolean removePlayer(Player player)
    {
        if (!getPlayers().contains(player))
            return false;

        PlayerAddEvent event = new PlayerAddEvent(this, player);
        Bukkit.getPluginManager().callEvent(event);

        getPlayers().remove(player);

        return true;
    }
}
