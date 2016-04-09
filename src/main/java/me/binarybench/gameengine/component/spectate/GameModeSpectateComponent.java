package me.binarybench.gameengine.component.spectate;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.common.utils.PlayerUtil;
import me.binarybench.gameengine.component.Component;
import me.binarybench.gameengine.component.ListenerComponent;
import me.binarybench.gameengine.component.player.events.PlayerComponentQuitEvent;
import me.binarybench.gameengine.component.simple.NoBlockBreak;
import me.binarybench.gameengine.component.simple.NoBlockPlace;
import me.binarybench.gameengine.component.simple.NoDamage;
import me.binarybench.gameengine.component.simple.NoDropItem;
import me.binarybench.gameengine.component.spectate.events.DisableSpectateEvent;
import me.binarybench.gameengine.component.spectate.events.EnableSpectateEvent;
import me.binarybench.gameengine.component.player.PlayerComponent;
import me.binarybench.gameengine.common.playerholder.events.PlayerAddEvent;
import me.binarybench.gameengine.common.playerholder.events.PlayerRemoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by BinaryBench on 3/20/2016.
 */
public class GameModeSpectateComponent extends ListenerComponent implements SpectateComponent, PlayerHolder {

    private List<Component> subComponents;

    private PlayerComponent playerComponent;

    private List<Player> spectaters = new ArrayList<>();

    private NonSpectateHolder nonSpectateHolder;

    public GameModeSpectateComponent(PlayerComponent playerComponent)
    {
        this.playerComponent = playerComponent;
        this.nonSpectateHolder = new NonSpectateHolder();

        this.subComponents = Arrays.asList(
            new NoDamage(this),
            new NoDropItem(this),
            new NoBlockPlace(this),
            new NoBlockBreak(this)
        );

    }

    @Override
    public void onEnable()
    {
        subComponents.forEach(Component::enable);
    }

    @Override
    public void onDisable()
    {
        subComponents.forEach(Component::disable);
    }

    @Override
    public boolean enableSpectate(Player player)
    {
        if (spectaters.contains(player))
            return false;

        EnableSpectateEvent addEvent = new EnableSpectateEvent(getSpectateHolder(), player);
        PlayerRemoveEvent removeEvent = new PlayerRemoveEvent(getNonSpectateHolder(), player);

        Bukkit.getPluginManager().callEvent(removeEvent);
        Bukkit.getPluginManager().callEvent(addEvent);


        PlayerUtil.resetPlayer(player);
        player.setGameMode(GameMode.SPECTATOR);

        this.spectaters.add(player);

        return true;
    }

    @Override
    public boolean disableSpectate(Player player)
    {
        if (!spectaters.contains(player))
            return false;

        DisableSpectateEvent removeEvent = new DisableSpectateEvent(getSpectateHolder(), player);
        PlayerAddEvent addEvent = new PlayerAddEvent(getNonSpectateHolder(), player);

        Bukkit.getPluginManager().callEvent(removeEvent);
        Bukkit.getPluginManager().callEvent(addEvent);

        this.spectaters.remove(player);

        return true;
    }

    @EventHandler
    public void onLeave(PlayerComponentQuitEvent event)
    {
        if (isSpectating(event.getPlayer()))
            this.disableSpectate(event.getPlayer());
    }

    @Override
    public PlayerHolder getSpectateHolder()
    {
        return this;
    }

    @Override
    public PlayerHolder getNonSpectateHolder()
    {
        return this.nonSpectateHolder;
    }

    @Override
    public Collection<Player> getPlayers()
    {
        return this.spectaters;
    }

    class NonSpectateHolder implements PlayerHolder {
        @Override
        public Collection<Player> getPlayers()
        {
            List<Player> players = new ArrayList<Player>(playerComponent.getPlayers());
            players.removeAll(spectaters);
            return players;
        }

        @Override
        public boolean test(Player player)
        {
            return playerComponent.test(player) && !spectaters.contains(player);
        }
    }

}
