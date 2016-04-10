package me.binarybench.gameengine.component.simple;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.common.playerholder.events.PlayerAddEvent;
import me.binarybench.gameengine.component.ListenerComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Created by Bench on 4/9/2016.
 */
public class NoHunger extends ListenerComponent {

    private PlayerHolder playerHolder;

    private int foodLevel;

    public NoHunger(PlayerHolder playerHolder)
    {
        this(playerHolder, 20);
    }

    public NoHunger(PlayerHolder playerHolder, int foodLevel)
    {
        this.playerHolder = playerHolder;
        this.foodLevel = foodLevel;

    }

    @Override
    public void onEnable()
    {
        getPlayerHolder().forEach(this::setHunger);
    }

    @Override
    public void onDisable()
    {
    }

    @EventHandler
    public void onAddPlayer(PlayerAddEvent event)
    {
        if (!event.getPlayerHolder().equals(getPlayerHolder()))
            return;
        setHunger(event.getPlayer());
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {
        if ((event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        if(!getPlayerHolder().test(player))
            return;

        event.setCancelled(true);
    }
    private void setHunger(Player player)
    {
        player.setFoodLevel(getFoodLevel());
        player.setSaturation(getFoodLevel());
    }


    public PlayerHolder getPlayerHolder()
    {
        return playerHolder;
    }

    public int getFoodLevel()
    {
        return foodLevel;
    }
}
