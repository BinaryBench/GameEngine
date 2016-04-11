package me.binarybench.gameengine.component.simple;

import me.binarybench.gameengine.component.ListenerComponent;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import java.util.function.Supplier;

/**
 * Created by BinaryBench on 3/24/2016.
 */
public class FallingBlockKiller extends ListenerComponent {

    private Supplier<World> supplier;

    public FallingBlockKiller(Supplier<World> supplier)
    {
        this.supplier = supplier;
    }

    @EventHandler
    public void EntityChangeBlockEvent(EntityChangeBlockEvent e)
    {
        if (e.getEntityType().equals(EntityType.FALLING_BLOCK))
        {

            if (supplier.get() == null || e.getBlock().getWorld() != supplier.get())
                return;

            if (e.getEntity() instanceof FallingBlock)
            {
                FallingBlock block = (FallingBlock) e.getEntity();

                block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getMaterial());
            }
            e.getEntity().remove();
            e.setCancelled(true);
        }
    }
}
