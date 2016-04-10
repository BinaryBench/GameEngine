package me.binarybench.gameengine.component.spectate.components;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.component.BaseComponent;
import me.binarybench.gameengine.component.ListenerComponent;
import me.binarybench.gameengine.component.player.PlayerComponent;
import me.binarybench.gameengine.component.player.events.PlayerComponentJoinEvent;
import me.binarybench.gameengine.component.spectate.SpectateComponent;
import org.bukkit.event.EventHandler;

/**
 * Created by Bench on 4/1/2016.
 */
public class JoinSpectate extends BaseComponent {

    private SpectateComponent spectateComponent;

    public JoinSpectate(SpectateComponent spectateComponent)
    {
        this.spectateComponent = spectateComponent;
    }

    @Override
    public void onEnable()
    {
        getSpectateComponent().setJoinSpectate(true);
    }

    @Override
    public void onDisable()
    {
        getSpectateComponent().setJoinSpectate(false);
    }

    public SpectateComponent getSpectateComponent()
    {
        return spectateComponent;
    }

}
