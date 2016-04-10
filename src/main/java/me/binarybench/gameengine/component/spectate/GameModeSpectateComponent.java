package me.binarybench.gameengine.component.spectate;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.common.utils.PlayerUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;


/**
 * Created by BinaryBench on 3/20/2016.
 */
public class GameModeSpectateComponent extends BaseSpectateComponent {

    public GameModeSpectateComponent(PlayerHolder playerHolder)
    {
        this(playerHolder, false);
    }

    public GameModeSpectateComponent(PlayerHolder playerHolder, boolean joinSpectate)
    {
        super(playerHolder, joinSpectate);
    }

    @Override
    public void onEnableSpectate(Player player)
    {
        PlayerUtil.resetPlayer(player);
        player.setGameMode(GameMode.SPECTATOR);
    }

    @Override
    public void onDisableSpectate(Player player)
    {

    }
}
