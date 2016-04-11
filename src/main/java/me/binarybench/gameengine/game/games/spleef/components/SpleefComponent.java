package me.binarybench.gameengine.game.games.spleef.components;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.component.ListenerComponent;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

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


    public PlayerHolder getPlayerHolder()
    {
        return playerHolder;
    }

    public Supplier<World> getWorldSupplier()
    {
        return worldSupplier;
    }
}
