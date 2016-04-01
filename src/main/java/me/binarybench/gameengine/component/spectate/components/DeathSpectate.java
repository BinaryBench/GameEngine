package me.binarybench.gameengine.component.spectate.components;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.component.ListenerComponent;
import me.binarybench.gameengine.component.spectate.SpectateComponent;
import me.binarybench.gameengine.game.spawn.event.PlayerSpawnEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Bench on 4/1/2016.
 */
public class DeathSpectate extends ListenerComponent {


    private SpectateComponent spectateComponent;


    public DeathSpectate(SpectateComponent spectateComponent)
    {
        this.spectateComponent = spectateComponent;
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity();

        if (!getPlayerHolder().test(player))
            return;

        getSpectateComponent().enableSpectate(player);
    }



    public PlayerHolder getPlayerHolder()
    {
        return getSpectateComponent().getNonSpectateHolder();
    }


    public SpectateComponent getSpectateComponent()
    {
        return spectateComponent;
    }
}

