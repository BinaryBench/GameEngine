package me.binarybench.gameengine.component.player;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import org.bukkit.entity.Player;

/**
 * Created by BinaryBench on 3/17/2016.
 */
public interface PlayerComponent extends PlayerHolder {

    boolean canJoin(Player player);

    boolean addPlayer(Player player);

    boolean removePlayer(Player player);

}
