package me.binarybench.gameengine.component.spectate.components;

import me.binarybench.gameengine.common.playerholder.events.PlayerAddEvent;
import me.binarybench.gameengine.component.ListenerComponent;
import me.binarybench.gameengine.component.spectate.SpectateComponent;
import me.binarybench.gameengine.component.spectate.events.EnableSpectateEvent;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.function.Supplier;

/**
 * Created by Bench on 4/5/2016.
 */
public class SpectateInWorld extends ListenerComponent {

    private SpectateComponent spectateComponent;

    private Supplier<World> worldSupplier;

    public SpectateInWorld(SpectateComponent spectateComponent, Supplier<World> worldSupplier)
    {
        this.spectateComponent = spectateComponent;
        this.worldSupplier = worldSupplier;
    }

    @EventHandler
    public void onSpectate(PlayerAddEvent event)
    {
        if (!event.getPlayerHolder().equals(getSpectateComponent().getSpectateHolder()))
            return;

        World world = getWorldSupplier().get();

        if (world == null)
            return;

        Player player = event.getPlayer();

        if (!player.getWorld().equals(world))
        {
            player.teleport(world.getSpawnLocation());
        }

    }

    public SpectateComponent getSpectateComponent()
    {
        return spectateComponent;
    }

    public Supplier<World> getWorldSupplier()
    {
        return worldSupplier;
    }
}
