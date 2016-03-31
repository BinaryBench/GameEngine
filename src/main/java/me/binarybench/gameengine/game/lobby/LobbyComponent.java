package me.binarybench.gameengine.game.lobby;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.common.playerholder.events.PlayerAddEvent;
import me.binarybench.gameengine.common.utils.PlayerUtil;
import me.binarybench.gameengine.common.utils.RandomUtil;
import me.binarybench.gameengine.component.ListenerComponent;
import me.binarybench.gameengine.component.player.PlayerComponent;
import me.binarybench.gameengine.game.world.WorldManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Created by Bench on 3/31/2016.
 */
public class LobbyComponent extends ListenerComponent {

    private PlayerHolder playerHolder;

    private Supplier<World> worldSupplier;

    public LobbyComponent(PlayerHolder playerHolder, Supplier<World> worldSupplier)
    {
        this.playerHolder = playerHolder;
        this.worldSupplier = worldSupplier;
    }

    @Override
    public void onEnable()
    {
        getPlayerHolder().forEach(this::spawnPlayer);
    }

    @Override
    public void onDisable()
    {

    }

    @EventHandler
    public void onJoin(PlayerAddEvent event)
    {
        if (event.getPlayerHolder() != getPlayerHolder())
            return;

        spawnPlayer(event.getPlayer());
    }


    public void spawnPlayer(Player player)
    {
        PlayerUtil.resetPlayer(player);

        player.teleport(getSpawn());
    }


    public Location getSpawn()
    {
        Random r = RandomUtil.getRandom();

        double x = RandomUtil.randomDouble(-2, 2);
        double y = 60;
        double z = RandomUtil.randomDouble(-2, 2);

        return new Location(getWorldSupplier().get(), x, y, z);
    }

    public Supplier<World> getWorldSupplier()
    {
        return worldSupplier;
    }

    public PlayerHolder getPlayerHolder()
    {
        return playerHolder;
    }
}
