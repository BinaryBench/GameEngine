package me.binarybench.gameengine.component.spectate.events;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.common.playerholder.events.PlayerRemoveEvent;
import org.bukkit.entity.Player;

/**
 * Created by BinaryBench on 3/20/2016.
 */
public class DisableSpectateEvent extends PlayerRemoveEvent {

    public DisableSpectateEvent(PlayerHolder playerHolder, Player player)
    {
        super(playerHolder, player);
    }

}
