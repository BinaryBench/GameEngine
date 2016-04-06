package me.binarybench.gameengine.game.world;

import me.binarybench.gameengine.common.playerholder.PlayerHolder;
import me.binarybench.gameengine.common.playerholder.events.PlayerAddEvent;
import me.binarybench.gameengine.common.utils.FileUtil;
import me.binarybench.gameengine.common.utils.ServerUtil;
import me.binarybench.gameengine.component.ListenerComponent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Bench on 3/29/2016.
 */
public class GameInfoComponent extends ListenerComponent {

    private WorldManager worldManager;
    private PlayerHolder playerHolder;
    private String gameInfo;
    private String mapInfo;

    public GameInfoComponent(WorldManager worldManager, PlayerHolder playerHolder, String... gameInfo)
    {
        this.worldManager = worldManager;
        this.playerHolder = playerHolder;
        this.gameInfo = appendLines(Arrays.asList(gameInfo));
        this.mapInfo = appendLines(FileUtil.loadTextFile(FileUtil.newFileIgnoreCase(getWorldManager().getConfigurationDirectory(), "mapinfo.txt")));
    }


    @Override
    public void onEnable()
    {
        ServerUtil.broadcast(getMessage(), getPlayerHolder());
        super.onEnable();
    }

    @EventHandler
    public void onJoin(PlayerAddEvent event)
    {
        if (event.getPlayerHolder() != playerHolder)
            return;
        event.getPlayer().sendMessage(getMessage());
    }

    public String getMessage()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.GREEN);
        sb.append("===== ===== ===== ===== ===== =====\n");

        if (gameInfo != null && !gameInfo.isEmpty())
        {
            sb.append(ChatColor.RESET);
            sb.append(gameInfo);
        }

        if (mapInfo != null && !mapInfo.isEmpty())
        {
            sb.append(" \n");
            sb.append(ChatColor.RESET);
            sb.append(mapInfo);

        }

        sb.append(ChatColor.GREEN);
        sb.append("\n===== ===== ===== ===== ===== =====");

        return sb.toString();
    }

    public static String appendLines(List<String> list)
    {
        if (list == null)
            return null;

        StringBuilder sb = new StringBuilder();

        for (String line : list)
        {
            sb.append(ChatColor.RESET);
            sb.append((line.equals("") ? " " : line));
            sb.append(" \n");
        }

        if (sb.length() == 0)
            return null;

        return ChatColor.translateAlternateColorCodes('&', sb.toString());
    }

    public WorldManager getWorldManager()
    {
        return worldManager;
    }

    public PlayerHolder getPlayerHolder()
    {
        return playerHolder;
    }
}
