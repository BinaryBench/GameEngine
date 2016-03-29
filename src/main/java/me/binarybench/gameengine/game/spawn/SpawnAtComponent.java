package me.binarybench.gameengine.game.spawn;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.component.ComponentBase;

/**
 * Created by Bench on 3/29/2016.
 */
public class SpawnAtComponent extends ComponentBase {

    private SpawnManager spawnManager;

    private PlayerHolder playerHolder;

    public SpawnAtComponent(SpawnManager spawnManager, PlayerHolder playerHolder)
    {
        this.spawnManager = spawnManager;
        this.playerHolder = playerHolder;
    }

    @Override
    public void onEnable()
    {
        spawnManager.respawn(playerHolder);
    }
}
