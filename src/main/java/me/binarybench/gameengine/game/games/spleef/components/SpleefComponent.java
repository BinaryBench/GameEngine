package me.binarybench.gameengine.game.games.spleef.components;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.component.ListenerComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.function.Supplier;

/**
 * Created by Bench on 4/10/2016.
 */
public class SpleefComponent extends ListenerComponent {

    public static final int foodChange = 1;

    private PlayerHolder playerHolder;
    private Supplier<World> worldSupplier;

    public SpleefComponent(PlayerHolder playerHolder, Supplier<World> worldSupplier)
    {
        this.playerHolder = playerHolder;
        this.worldSupplier = worldSupplier;
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();


        if (!getPlayerHolder().test(player))
            return;

        World world = getWorldSupplier().get();

        if (world == null)
            return;


        if (!(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
            return;



        Block block = event.getClickedBlock();

        if (block.getType().equals(Material.TNT)) {
            TNTPrimed tnt = (TNTPrimed) world.spawnEntity(block.getLocation().add(0.5, 0.5, 0.5), EntityType.PRIMED_TNT);
            tnt.setFuseTicks(30);
            block.setType(Material.AIR);
            return;
        }

        if (player.getFoodLevel() + foodChange >= 20)
            player.setFoodLevel(20);
        else
            player.setFoodLevel(player.getFoodLevel() + foodChange);

        block.getLocation().getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());
        block.setType(Material.AIR);

    }


    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event)
    {
        if (event.isCancelled())
            return;

        World world = getWorldSupplier().get();

        if (world == null)
            return;


        if (!event.getLocation().getWorld().equals(world))
            return;

        event.setCancelled(true);

        double blockFlyChance = 0.3;

        for (Block block : event.blockList()) {

            if (Math.random() > blockFlyChance) {
                block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());
                block.setType(Material.AIR);
                continue;
            }
            if (block.getType().equals(Material.TNT)) {
                block.setType(Material.AIR);
                TNTPrimed tnt = (TNTPrimed) world.spawnEntity(block.getLocation().add(0.5, 0.5, 0.5), EntityType.PRIMED_TNT);

                tnt.setFuseTicks(5);

                tnt.setVelocity(bounceVector());
                block.setType(Material.AIR);
            }
            bounceBlock(block);
        }

        final double amount = 4;

        for (Player player : getPlayerHolder()) {

            Location first_location = event.getLocation();

            Location second_location = player.getLocation();

            Vector from = new Vector(first_location.getX(), first_location.getY(), first_location.getZ());

            Vector to = new Vector(second_location.getX(), second_location.getY(), second_location.getZ());

            Vector vector = to.subtract(from);

            double magnitude = amount / (Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2) + Math.pow(vector.getZ(), 2));

            if (magnitude <= 0.05)
                continue;

            vector = vector.multiply(magnitude);

            player.setVelocity(vector);
        }
    }

    @SuppressWarnings("deprecation")
    public void bounceBlock(Block block)
    {
        if (block == null)
            return;
        FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), block.getType(), block.getData());
        fallingBlock.setDropItem(false);
        fallingBlock.setVelocity(bounceVector());
        block.setType(Material.AIR);
    }

    public Vector bounceVector()
    {
        float x = (float) (Math.random() - 0.5);
        float y = (float) (Math.random() - 0.2);
        float z = (float) (Math.random() - 0.5);
        return new Vector(x, y, z);
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
