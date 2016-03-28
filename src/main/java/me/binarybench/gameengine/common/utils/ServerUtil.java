package me.binarybench.gameengine.common.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Bench on 3/28/2016.
 */
public class ServerUtil {

    public static void shutdown(String shutdownMessage)
    {
        shutdown(shutdownMessage, "Goodbye");
    }

    public static void shutdown(String shutdownMessage, String kickMessage)
    {
        for (Player player : Bukkit.getOnlinePlayers())
            player.kickPlayer(kickMessage);

        System.err.println(" ");
        System.err.println(shutdownMessage);
        System.err.println(" ");

        Bukkit.shutdown();
    }
}
