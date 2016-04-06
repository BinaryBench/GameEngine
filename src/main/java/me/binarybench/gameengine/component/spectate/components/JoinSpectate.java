package me.binarybench.gameengine.component.spectate.components;

import me.binarybench.gameengine.component.ListenerComponent;
import me.binarybench.gameengine.component.player.PlayerComponent;
import me.binarybench.gameengine.component.player.events.PlayerComponentJoinEvent;
import me.binarybench.gameengine.component.spectate.SpectateComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Bench on 4/1/2016.
 */
public class JoinSpectate extends ListenerComponent {

    private SpectateComponent spectateComponent;
    private PlayerComponent playerComponent;

    public JoinSpectate(SpectateComponent spectateComponent, PlayerComponent playerComponent)
    {
        this.spectateComponent = spectateComponent;
        this.playerComponent = playerComponent;
    }


    @EventHandler
    public void onJoin(PlayerComponentJoinEvent event)
    {
        if (event.getPlayerHolder() != getPlayerComponent())
            return;
        getSpectateComponent().enableSpectate(event.getPlayer());
    }


    public SpectateComponent getSpectateComponent()
    {
        return spectateComponent;
    }

    public PlayerComponent getPlayerComponent()
    {
        return playerComponent;
    }
}
