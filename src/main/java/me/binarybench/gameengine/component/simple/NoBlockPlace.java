package me.binarybench.gameengine.component.simple;

import me.binarybench.gameengine.common.item.DefaultPredicate;
import me.binarybench.gameengine.common.utils.BlockUtilities;
import me.binarybench.gameengine.component.ComponentListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

/**
 * Created by BinaryBench on 3/24/2016.
 */
public class NoBlockPlace extends ComponentListener {

    private Predicate<Player> playerPredicate;

    private Predicate<ItemStack> itemPredicate;

    public NoBlockPlace(Predicate<Player> playerPredicate)
    {
        this(playerPredicate, new DefaultPredicate<>());
    }

    public NoBlockPlace(Predicate<Player> playerPredicate, Predicate<ItemStack> itemPredicate)
    {
        this.playerPredicate = playerPredicate;
        this.itemPredicate = itemPredicate;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event)
    {
        if (playerPredicate.test(event.getPlayer()) && itemPredicate.test(BlockUtilities.toItemStack(event.getBlock())))
            event.setCancelled(true);
    }
}
