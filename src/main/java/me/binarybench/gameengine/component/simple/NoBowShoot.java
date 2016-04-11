package me.binarybench.gameengine.component.simple;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.component.ListenerComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;

/**
 * Created by Bench on 4/11/2016.
 */
public class NoBowShoot extends ListenerComponent {

    private PlayerHolder playerHolder;

    public NoBowShoot(PlayerHolder playerHolder)
    {
        this.playerHolder = playerHolder;
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event)
    {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        if (getPlayerHolder().test(player)) {
            event.setCancelled(true);
        }
    }

    public PlayerHolder getPlayerHolder()
    {
        return playerHolder;
    }
}
