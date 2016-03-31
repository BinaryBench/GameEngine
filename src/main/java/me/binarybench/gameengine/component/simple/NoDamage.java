package me.binarybench.gameengine.component.simple;

import me.binarybench.gameengine.common.item.DefaultPredicate;
import me.binarybench.gameengine.component.ListenerComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.function.Predicate;

/**
 * Created by BinaryBench on 3/24/2016.
 */
public class NoDamage extends ListenerComponent {

    private Predicate<Player> playerPredicate;

    private Predicate<EntityDamageEvent> eventPredicate;

    public NoDamage(Predicate<Player> playerPredicate)
    {
        this(playerPredicate, new DefaultPredicate<>());
    }

    public NoDamage(Predicate<Player> playerPredicate, Predicate<EntityDamageEvent> eventPredicate)
    {
        this.playerPredicate = playerPredicate;
        this.eventPredicate = eventPredicate;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event)
    {
        if (event.getEntity() instanceof Player && playerPredicate.test((Player) event.getEntity()) && eventPredicate.test(event))
            event.setCancelled(true);
    }
}
