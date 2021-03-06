package me.binarybench.gameengine.game.lobby;

import me.binarybench.gameengine.Main;
import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.common.playerholder.events.PlayerAddEvent;
import me.binarybench.gameengine.common.utils.PlayerUtil;
import me.binarybench.gameengine.common.utils.RandomUtil;
import me.binarybench.gameengine.component.Component;
import me.binarybench.gameengine.component.ListenerComponent;
import me.binarybench.gameengine.component.simple.*;
import me.binarybench.gameengine.game.gamestate.GameState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Created by Bench on 3/31/2016.
 */
public class LobbyComponent extends ListenerComponent implements Runnable {


    private int id;

    private PlayerHolder playerHolder;

    private Supplier<World> worldSupplier;

    private List<Component> supComponents;

    public LobbyComponent(PlayerHolder playerHolder, Supplier<World> worldSupplier)
    {
        this.playerHolder = playerHolder;
        this.worldSupplier = worldSupplier;

        supComponents = Arrays.asList(
              new NoBlockBreak(playerHolder),
              new NoBlockPlace(playerHolder),
              new NoDropItem(playerHolder),
              new NoPickUpItem(playerHolder),
              new NoHunger(playerHolder)
        );
    }

    @Override
    public void onEnable()
    {
        getPlayerHolder().forEach(this::spawnPlayer);
        this.id = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), this, 2, 2).getTaskId();

        supComponents.forEach(Component::enable);
    }

    @Override
    public void onDisable()
    {
        Bukkit.getScheduler().cancelTask(this.id);

        supComponents.forEach(Component::disable);
    }


    @Override
    public void run()
    {
        for (Player player : getPlayerHolder())
        {
            Location loc = player.getLocation();
            if (loc.getBlockY() < 50)
            {
                spawnPlayer(player);
            }
        }
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
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(getSpawn());
    }


    public Location getSpawn()
    {
        double x = RandomUtil.randomDouble(-1, 2);
        double y = 60;
        double z = RandomUtil.randomDouble(-1, 2);

        return new Location(getWorld(), x, y, z);
    }

    public World getWorld()
    {
        World world = worldSupplier.get();

        if (world == null)
            System.err.println("NULL WORLD");

        return world;
    }

    public PlayerHolder getPlayerHolder()
    {
        return playerHolder;
    }
}
