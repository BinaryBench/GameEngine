package me.binarybench.gameengine.common.scheduler;

import me.binarybench.gameengine.Main;
import org.bukkit.Bukkit;

/**
 * Created by BinaryBench on 3/22/2016.
 */
@FunctionalInterface
public interface SyncRunnable extends Runnable {

    @Override
    default void run()
    {
        Bukkit.getScheduler().runTask(Main.getPlugin(), this::syncRun);
    }

    void syncRun();
}
