package me.binarybench.gameengine.component.spectate;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Created by BinaryBench on 3/20/2016.
 */
public interface SpectateComponent {

    boolean enableSpectate(Player player);

    boolean disableSpectate(Player player);

    default boolean isSpectating(Player player)
    {
        return getSpectateHolder().test(player);
    }

    default boolean isNotSpectating(Player player)
    {
        return getNonSpectateHolder().test(player);
    }

    default Collection<Player> getSpectators()
    {
        return getSpectateHolder().getPlayers();
    }

    default Collection<Player> getNonSpectators()
    {
        return getNonSpectateHolder().getPlayers();
    }

    PlayerHolder getSpectateHolder();

    PlayerHolder getNonSpectateHolder();

    PlayerHolder getDomainPlayerHolder();

    boolean isJoinSpectate();

    void setJoinSpectate(boolean joinSpectate);
}