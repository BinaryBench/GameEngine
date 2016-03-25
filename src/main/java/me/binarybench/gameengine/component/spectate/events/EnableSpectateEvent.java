package me.binarybench.gameengine.component.spectate.events;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.common.playerholder.events.PlayerAddEvent;
import org.bukkit.entity.Player;

/**
 * Created by BinaryBench on 3/20/2016.
 */
public class EnableSpectateEvent extends PlayerAddEvent {

    public EnableSpectateEvent(PlayerHolder playerHolder, Player player)
    {
        super(playerHolder, player);
    }

}
