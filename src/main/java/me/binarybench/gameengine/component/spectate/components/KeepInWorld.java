package me.binarybench.gameengine.component.spectate.components;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.common.playerholder.events.PlayerAddEvent;
import me.binarybench.gameengine.component.ListenerComponent;
import me.binarybench.gameengine.component.spectate.SpectateComponent;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.function.Supplier;

/**
 * Created by Bench on 4/5/2016.
 */
public class KeepInWorld extends ListenerComponent {

    private PlayerHolder playerHolder;

    private Supplier<World> worldSupplier;

    public KeepInWorld(PlayerHolder playerHolder, Supplier<World> worldSupplier)
    {
        this.playerHolder = playerHolder;
        this.worldSupplier = worldSupplier;
    }

    @Override
    public void onEnable()
    {
        getPlayerHolder().forEach(this::checkPlayer);
    }

    @EventHandler
    public void onJoin(PlayerAddEvent event)
    {
        if (!event.getPlayerHolder().equals(getPlayerHolder()))
            return;

        checkPlayer(event.getPlayer());
    }

    public void checkPlayer(Player player)
    {
        World world = getWorldSupplier().get();

        if (world == null)
            return;

        if (!player.getWorld().equals(world))
        {
            player.teleport(world.getSpawnLocation());
        }
    }

    public PlayerHolder getPlayerHolder()
    {
        return playerHolder;
    }

    public Supplier<World> getWorldSupplier()
    {
        return worldSupplier;
    }
}
