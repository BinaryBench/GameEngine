package me.binarybench.gameengine.game.games.runner.components;

import me.binarybench.gameengine.component.ComponentListener;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityChangeBlockEvent;

/**
 * Created by BinaryBench on 3/24/2016.
 */
public class FallingBlockKiller extends ComponentListener {
    private World world;

    public FallingBlockKiller(World world)
    {
        this.world = world;
    }

    @EventHandler
    public void EntityChangeBlockEvent(EntityChangeBlockEvent e)
    {
        if (e.getEntityType().equals(EntityType.FALLING_BLOCK))
        {

            if (!e.getBlock().getWorld().equals(world))
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
