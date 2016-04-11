package me.binarybench.gameengine.game.victorycondition;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.common.scheduler.SyncRunnable;
import me.binarybench.gameengine.component.BaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bench on 4/10/2016.
 */
public class TimeVictoryCondition extends BaseComponent implements SyncRunnable {

    private PlayerHolder broadcast;
    private Runnable whenDone;

    private ScheduledExecutorService executorService;
    private long delay;
    private TimeUnit timeUnit;

    private ScheduledFuture future;

    public TimeVictoryCondition(PlayerHolder broadcast, Runnable whenDone, ScheduledExecutorService executorService, long delay, TimeUnit timeUnit)
    {
        this.broadcast = broadcast;
        this.whenDone = whenDone;
        this.executorService = executorService;
        this.delay = delay;
        this.timeUnit = timeUnit;
    }

    @Override
    public void onEnable()
    {
        this.future = getExecutorService().schedule(this, getDelay(), getTimeUnit());
    }

    @Override
    public void onDisable()
    {
        future.cancel(true);
    }

    @Override
    public void syncRun()
    {
        getBroadcast().forEach(player -> {
            player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "GAME ENDED DUE TO TIME!");

            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 10, 1);
        });
        getWhenDone().run();
    }



    public long getDelay()
    {
        return delay;
    }

    public TimeUnit getTimeUnit()
    {
        return timeUnit;
    }

    public ScheduledExecutorService getExecutorService()
    {
        return executorService;
    }

    public PlayerHolder getBroadcast()
    {
        return broadcast;
    }

    public Runnable getWhenDone()
    {
        return whenDone;
    }
}
