package me.binarybench.gameengine.component.simple;

import me.binarybench.gameengine.common.item.DefaultPredicate;
import me.binarybench.gameengine.component.ListenerComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

/**
 * Created by BinaryBench on 3/24/2016.
 */
public class NoDropItem extends ListenerComponent {

    private Predicate<Player> playerPredicate;

    private Predicate<ItemStack> itemPredicate;

    public NoDropItem(Predicate<Player> playerPredicate)
    {
        this(playerPredicate, new DefaultPredicate<>());
    }

    public NoDropItem(Predicate<Player> playerPredicate, Predicate<ItemStack> itemPredicate)
    {
        this.playerPredicate = playerPredicate;
        this.itemPredicate = itemPredicate;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event)
    {
        if (playerPredicate.test(event.getPlayer()) && itemPredicate.test(event.getItemDrop().getItemStack()))
            event.setCancelled(true);
    }
}

