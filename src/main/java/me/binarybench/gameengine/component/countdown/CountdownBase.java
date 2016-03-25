package me.binarybench.gameengine.component.countdown;

import me.binarybench.gameengine.common.scheduler.SyncRunnable;
import me.binarybench.gameengine.component.ComponentBase;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by BinaryBench on 3/23/2016.
 */
public abstract class CountdownBase extends ComponentBase implements SyncRunnable {

    private final int startTime;

    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> futureTask;
    private int counter;

    public CountdownBase(ScheduledExecutorService scheduler, int startTime)
    {
        this.startTime = startTime;
        this.counter = startTime;
        this.scheduler = scheduler;
    }

    public void restart()
    {
        stop();
        counter = startTime;
        start();
    }

    public boolean start()
    {
        if (futureTask != null || counter <= 0)
            return false;
        futureTask = scheduler.scheduleAtFixedRate(this, 1, 1, TimeUnit.SECONDS);
        this.onStart();
        return true;
    }

    public abstract void onStart();

    public boolean stop()
    {
        if (futureTask == null)
            return false;
        futureTask.cancel(true);
        futureTask = null;
        this.onStop();
        return true;
    }

    public abstract void onStop();

    @Override
    public void syncRun()
    {
        if (counter > 0)
            count(counter);
        else
        {
            this.onFinish();
            stop();
        }
        counter--;
    }

    public abstract void count(int counter);

    public abstract void onFinish();

    public int getCounter()
    {
        return counter;
    }

    public int getStartTime()
    {
        return startTime;
    }

    @Override
    public void onEnable()
    {
        start();
    }

    @Override
    public void onDisable()
    {
        stop();
    }
}
